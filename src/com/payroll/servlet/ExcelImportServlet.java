package com.payroll.servlet;

import com.payroll.dao.EmployeeDAO;
import com.payroll.dao.UserDAO;
import com.payroll.model.Employee;
import com.payroll.util.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Iterator;

@WebServlet("/ExcelImportServlet")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024)
public class ExcelImportServlet extends HttpServlet {

    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if ("template".equals(action)) {
            downloadTemplate(resp);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/admin/importExcel.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Part filePart = req.getPart("file");
        if (filePart == null || filePart.getSize() == 0) {
            req.getSession().setAttribute("msg", "No file uploaded.");
            resp.sendRedirect(req.getContextPath() + "/admin/importExcel.jsp");
            return;
        }

        int imported = 0, skipped = 0, errors = 0;

        try (InputStream is = filePart.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            if (rows.hasNext()) rows.next(); // skip header

            while (rows.hasNext()) {
                Row row = rows.next();

                try {
                    String name = getCellString(row.getCell(0));
                    String email = getCellString(row.getCell(1));

                    if (name.isEmpty() || email.isEmpty()) {
                        skipped++;
                        continue;
                    }

                    if (employeeDAO.existsByEmail(email)) {
                        skipped++;
                        continue;
                    }

                    Employee e = new Employee();
                    e.setName(name);
                    e.setEmail(email);
                    e.setPhone(getCellString(row.getCell(2)));
                    e.setDepartment(getCellString(row.getCell(3)));
                    e.setDesignation(getCellString(row.getCell(4)));

                    String dojStr = getCellString(row.getCell(5));
                    e.setDoj(dojStr.isEmpty() ? LocalDate.now() : LocalDate.parse(dojStr));

                    double basic = getCellDouble(row.getCell(6));
                    e.setBasicSalary(basic);

                    int empId = employeeDAO.addEmployee(e);

                    // Auto-create login
                    if (empId > 0) {
                        String username = email.split("@")[0];
                        try {
                            userDAO.createUserForEmployee(username,
                                PasswordUtil.hash("emp123"), empId);
                        } catch (Exception ignored) {
                            // username already exists — skip
                        }
                    }

                    imported++;

                } catch (Exception ex) {
                    errors++;
                }
            }

        } catch (Exception e) {
            throw new ServletException("Excel import failed", e);
        }

        req.getSession().setAttribute("msg",
            "Import complete: " + imported + " added, " + skipped + " skipped, " +
            errors + " errors.");

        resp.sendRedirect(req.getContextPath() + "/EmployeeServlet?action=list");
    }

    // ---------- Download Template ----------
    private void downloadTemplate(HttpServletResponse resp) throws IOException {

        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("Employee Template");

            String[] cols = {"Name", "Email", "Phone", "Department",
                             "Designation", "DOJ (yyyy-MM-dd)", "Basic Salary"};

            CellStyle style = wb.createCellStyle();
            Font font = wb.createFont();
            font.setBold(true);
            style.setFont(font);

            Row header = sheet.createRow(0);
            for (int i = 0; i < cols.length; i++) {
                Cell c = header.createCell(i);
                c.setCellValue(cols[i]);
                c.setCellStyle(style);
                sheet.autoSizeColumn(i);
            }

            // Sample row
            Row sample = sheet.createRow(1);
            sample.createCell(0).setCellValue("John Doe");
            sample.createCell(1).setCellValue("john@example.com");
            sample.createCell(2).setCellValue("9876543210");
            sample.createCell(3).setCellValue("IT");
            sample.createCell(4).setCellValue("Developer");
            sample.createCell(5).setCellValue("2025-01-15");
            sample.createCell(6).setCellValue(35000);

            resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            resp.setHeader("Content-Disposition",
                "attachment; filename=employee_template.xlsx");
            wb.write(resp.getOutputStream());
        }
    }

    // ---------- Cell Helpers ----------
    private String getCellString(Cell cell) {
        if (cell == null) return "";
        try {
            if (cell.getCellType() == CellType.STRING) return cell.getStringCellValue().trim();
            if (cell.getCellType() == CellType.NUMERIC) return String.valueOf((long) cell.getNumericCellValue());
            if (cell.getCellType() == CellType.BOOLEAN) return String.valueOf(cell.getBooleanCellValue());
        } catch (Exception ignored) {}
        return "";
    }

    private double getCellDouble(Cell cell) {
        if (cell == null) return 0;
        try {
            if (cell.getCellType() == CellType.NUMERIC) return cell.getNumericCellValue();
            if (cell.getCellType() == CellType.STRING) return Double.parseDouble(cell.getStringCellValue().trim());
        } catch (Exception ignored) {}
        return 0;
    }
}