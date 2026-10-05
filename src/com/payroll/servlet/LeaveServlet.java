package com.payroll.servlet;

import com.payroll.dao.LeaveDAO;
import com.payroll.model.Leave;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/LeaveServlet")
public class LeaveServlet extends HttpServlet {

    private final LeaveDAO leaveDAO = new LeaveDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "list";

        try {
            switch (action) {
                case "my":
                    showMyLeaves(req, resp);
                    break;

                case "approve":
                case "reject":
                    updateStatus(req, resp, action);
                    break;

                case "list":
                default:
                    listAll(req, resp);
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
            applyLeave(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listAll(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, ServletException, IOException {

        List<Leave> list = leaveDAO.getAllLeaves();
        req.setAttribute("leaveList", list);
        req.getRequestDispatcher("admin/leave.jsp").forward(req, resp);
    }

    private void showMyLeaves(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, ServletException, IOException {

        Integer empId = (Integer) req.getSession().getAttribute("employeeId");
        List<Leave> list = leaveDAO.getByEmployee(empId);
        req.setAttribute("leaveList", list);
        req.getRequestDispatcher("employee/applyLeave.jsp").forward(req, resp);
    }

    private void applyLeave(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException {

        Leave l = new Leave();
        l.setEmployeeId((Integer) req.getSession().getAttribute("employeeId"));
        l.setFromDate(LocalDate.parse(req.getParameter("fromDate")));
        l.setToDate(LocalDate.parse(req.getParameter("toDate")));
        l.setReason(req.getParameter("reason"));

        leaveDAO.applyLeave(l);

        req.getSession().setAttribute("msg", "Leave applied successfully.");
        resp.sendRedirect("LeaveServlet?action=my");
    }

    private void updateStatus(HttpServletRequest req, HttpServletResponse resp, String action)
            throws SQLException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        String status = action.equals("approve") ? "APPROVED" : "REJECTED";

        leaveDAO.updateStatus(id, status);
        req.getSession().setAttribute("msg", "Leave " + status.toLowerCase() + ".");
        resp.sendRedirect("LeaveServlet?action=list");
    }
}