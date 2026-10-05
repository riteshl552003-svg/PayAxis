package com.payroll.servlet;

import com.payroll.dao.PayrollDAO;
import com.payroll.model.Payroll;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/PayrollServlet")
public class PayrollServlet extends HttpServlet {

    private final PayrollDAO payrollDAO = new PayrollDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "list";

        try {
            switch (action) {
                case "generateForm":
                    req.getRequestDispatcher("admin/generatePayroll.jsp").forward(req, resp);
                    break;

                case "my":
                    showMyPayslips(req, resp);
                    break;

                case "list":
                default:
                    listPayroll(req, resp);
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
            generatePayroll(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listPayroll(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, ServletException, IOException {

        int month = parseInt(req.getParameter("month"), LocalDate.now().getMonthValue());
        int year  = parseInt(req.getParameter("year"), LocalDate.now().getYear());

        List<Payroll> list = payrollDAO.getPayrollByMonth(month, year);
        req.setAttribute("payrollList", list);
        req.setAttribute("month", month);
        req.setAttribute("year", year);

        req.getRequestDispatcher("admin/payroll.jsp").forward(req, resp);
    }

    private void showMyPayslips(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, ServletException, IOException {

        Integer empId = (Integer) req.getSession().getAttribute("employeeId");
        List<Payroll> list = payrollDAO.getByEmployee(empId);
        req.setAttribute("payrollList", list);
        req.getRequestDispatcher("employee/myPayslips.jsp").forward(req, resp);
    }

    private void generatePayroll(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException {

        int month = Integer.parseInt(req.getParameter("month"));
        int year  = Integer.parseInt(req.getParameter("year"));

        int count = payrollDAO.generatePayrollForAll(month, year);

        req.getSession().setAttribute("msg",
            "Payroll generated for " + count + " employees (" + month + "/" + year + ").");

        resp.sendRedirect("PayrollServlet?action=list&month=" + month + "&year=" + year);
    }

    private int parseInt(String s, int def) {
        try { return Integer.parseInt(s); } catch (Exception e) { return def; }
    }
}