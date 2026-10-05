package com.payroll.servlet;

import com.payroll.dao.AttendanceDAO;
import com.payroll.dao.EmployeeDAO;
import com.payroll.dao.PayrollDAO;
import com.payroll.model.Attendance;
import com.payroll.model.Employee;
import com.payroll.model.Payroll;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/ExcelExportServlet")
public class ExcelExportServlet extends HttpServlet {

    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private final PayrollDAO payrollDAO = new PayrollDAO();
    private final AttendanceDAO attendanceDAO = new AttendanceDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String type = req.getParameter("type");
        if (type == null) type = "employees";

        try {
            switch (type) {
                case "payroll":
                    exportPayroll(req, resp);
                    break;

                case "attendance":
                    exportAttendance(req, resp);
                    break;

                case "employees":
                default:
                    exportEmployees(req, resp);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    // ---------- Employees Export ----------
    private void exportEmployees(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException {

        List<Employee> list = employeeDAO.getAllEmployees();

        String[] headers = {"ID", "Name", "Email", "Phone", "Department",
                            "Designation", "DOJ", "Basic Salary"};

        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("Employees");

            writeHeader(wb, sheet, headers);

            int rowNum = 1;
            for (Employee e : list) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(e.getId());
                row.createCell(1).setCellValue(e.getName());
                row.createCell(2).setCellValue(e.getEmail());
                row.createCell(3).setCellValue(e.getPhone() == null ? "" : e.getPhone());
                row.createCell(4).setCellValue(e.getDepartment() == null ? "" : e.getDepartment());
                row.createCell(5).setCellValue(e.getDesignation() == null ? "" : e.getDesignation());
                row.createCell(6).setCellValue(e.getDoj() == null ? "" : e.getDoj().toString());
                row.createCell(7).setCellValue(e.getBasicSalary());
            }

            for (int i = 0; i < headers.length; i++) sheet.autoSizeColumn(i);

            sendExcel(resp, wb, "employees_" + LocalDate.now() + ".xlsx");
        }
    }

    // ---------- Payroll Export ----------
    private void exportPayroll(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException {

        int month = parseInt(req.getParameter("month"), LocalDate.now().getMonthValue());
        int year  = parseInt(req.getParameter("year"), LocalDate.now().getYear());

        List<Payroll> list = payrollDAO.getPayrollByMonth(month, year);

        String[] headers = {"Emp ID", "Name", "Department", "Basic", "HRA", "DA",
                            "Bonus", "PF", "Tax", "Leave Deduction", "Net Salary"};

        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("Payroll " + month + "-" + year);

            writeHeader(wb, sheet, headers);

            int rowNum = 1;
            for (Payroll p : list) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(p.getEmployeeId());
                row.createCell(1).setCellValue(p.getEmployeeName() == null ? "" : p.getEmployeeName());
                row.createCell(2).setCellValue(p.getDepartment() == null ? "" : p.getDepartment());
                row.createCell(3).setCellValue(p.getBasic());
                row.createCell(4).setCellValue(p.getHra());
                row.createCell(5).setCellValue(p.getDa());
                row.createCell(6).setCellValue(p.getBonus());
                row.createCell(7).setCellValue(p.getPf());
                row.createCell(8).setCellValue(p.getTax());
                row.createCell(9).setCellValue(p.getLeaveDeduction());
                row.createCell(10).setCellValue(p.getNetSalary());
            }

            for (int i = 0; i < headers.length; i++) sheet.autoSizeColumn(i);

            sendExcel(resp, wb, "payroll_" + month + "_" + year + ".xlsx");
        }
    }

    // ---------- Attendance Export ----------
    private void exportAttendance(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException {

        int month = parseInt(req.getParameter("month"), LocalDate.now().getMonthValue());
        int year  = parseInt(req.getParameter("year"), LocalDate.now().getYear());

        List<Attendance> list = attendanceDAO.getByMonth(month, year);

        String[] headers = {"Emp ID", "Employee Name", "Date", "Status"};

        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("Attendance " + month + "-" + year);

            writeHeader(wb, sheet, headers);

            int rowNum = 1;
            for (Attendance a : list) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(a.getEmployeeId());
                row.createCell(1).setCellValue(a.getEmployeeName() == null ? "" : a.getEmployeeName());
                row.createCell(2).setCellValue(a.getDate() == null ? "" : a.getDate().toString());
                row.createCell(3).setCellValue(a.getStatus());
            }

            for (int i = 0; i < headers.length; i++) sheet.autoSizeColumn(i);

            sendExcel(resp, wb, "attendance_" + month + "_" + year + ".xlsx");
        }
    }

    // ---------- Helpers ----------
    private void writeHeader(Workbook wb, Sheet sheet, String[] headers) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBold(true);
        style.setFont(font);

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(style);
        }
    }

    private void sendExcel(HttpServletResponse resp, Workbook wb, String filename)
            throws IOException {
        resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        resp.setHeader("Content-Disposition", "attachment; filename=" + filename);
        wb.write(resp.getOutputStream());
        wb.close();
    }

    private int parseInt(String s, int def) {
        try { return Integer.parseInt(s); } catch (Exception e) { return def; }
    }
}