package com.payroll.servlet;

import com.payroll.dao.AttendanceDAO;
import com.payroll.dao.EmployeeDAO;
import com.payroll.model.Attendance;
import com.payroll.model.Employee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/AttendanceServlet")
public class AttendanceServlet extends HttpServlet {

    private final AttendanceDAO attendanceDAO = new AttendanceDAO();
    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "list";

        try {
            switch (action) {
                case "markForm":
                    showMarkForm(req, resp);
                    break;

                case "my":
                    showMyAttendance(req, resp);
                    break;

                case "list":
                default:
                    listAttendance(req, resp);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            saveAttendance(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listAttendance(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, ServletException, IOException {

        int month = parseInt(req.getParameter("month"), LocalDate.now().getMonthValue());
        int year  = parseInt(req.getParameter("year"), LocalDate.now().getYear());

        List<Attendance> list = attendanceDAO.getByMonth(month, year);
        req.setAttribute("attendanceList", list);
        req.setAttribute("month", month);
        req.setAttribute("year", year);

        req.getRequestDispatcher("admin/attendance.jsp").forward(req, resp);
    }

    private void showMarkForm(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, ServletException, IOException {

        List<Employee> employees = employeeDAO.getAllEmployees();
        req.setAttribute("employees", employees);
        req.getRequestDispatcher("admin/markAttendance.jsp").forward(req, resp);
    }

    private void showMyAttendance(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, ServletException, IOException {

        HttpSession session = req.getSession(false);
        Integer employeeId = (Integer) session.getAttribute("employeeId");

        if (employeeId == null) {
            resp.sendRedirect(req.getContextPath() + "/employee/dashboard.jsp");
            return;
        }

        int month = parseInt(req.getParameter("month"), LocalDate.now().getMonthValue());
        int year  = parseInt(req.getParameter("year"), LocalDate.now().getYear());

        List<Attendance> list = attendanceDAO.getByEmployee(employeeId, month, year);
        req.setAttribute("attendanceList", list);
        req.setAttribute("month", month);
        req.setAttribute("year", year);

        req.getRequestDispatcher("employee/myAttendance.jsp").forward(req, resp);
    }

    private void saveAttendance(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException {

        String[] empIds = req.getParameterValues("employeeId");

        if (empIds != null) {
            LocalDate today = LocalDate.now();
            for (String idStr : empIds) {
                int empId = Integer.parseInt(idStr);
                String status = req.getParameter("status_" + empId);
                if (status != null) {
                    attendanceDAO.markAttendance(empId, today, status);
                }
            }
        }

        req.getSession().setAttribute("msg", "Attendance marked for today.");
        resp.sendRedirect("AttendanceServlet?action=list");
    }

    private int parseInt(String s, int def) {
        try { return Integer.parseInt(s); } catch (Exception e) { return def; }
    }
}