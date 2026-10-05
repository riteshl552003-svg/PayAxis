package com.payroll.servlet;

import com.payroll.dao.EmployeeDAO;
import com.payroll.dao.UserDAO;
import com.payroll.model.Employee;
import com.payroll.util.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {

    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private final UserDAO userDAO = new UserDAO();

    // ---------- GET: list / edit ----------
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "list";

        try {
            switch (action) {
                case "delete":
                    deleteEmployee(req, resp);
                    break;

                case "edit":
                    showEditForm(req, resp);
                    break;

                case "list":
                default:
                    listEmployees(req, resp);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    // ---------- POST: add / update ----------
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        try {
            if ("add".equals(action)) {
                addEmployee(req, resp);
            } else if ("update".equals(action)) {
                updateEmployee(req, resp);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    // -------- Helpers --------

    private void listEmployees(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, ServletException, IOException {

        List<Employee> list = employeeDAO.getAllEmployees();
        req.setAttribute("employees", list);
        req.getRequestDispatcher("admin/employees.jsp").forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        Employee e = employeeDAO.getEmployeeById(id);

        if (e == null) {
            resp.sendRedirect("EmployeeServlet?action=list");
            return;
        }

        req.setAttribute("employee", e);
        req.getRequestDispatcher("admin/editEmployee.jsp").forward(req, resp);
    }

    private void addEmployee(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException {

        Employee e = new Employee();
        e.setName(req.getParameter("name"));
        e.setEmail(req.getParameter("email"));
        e.setPhone(req.getParameter("phone"));
        e.setDepartment(req.getParameter("department"));
        e.setDesignation(req.getParameter("designation"));
        e.setDoj(LocalDate.parse(req.getParameter("doj")));
        e.setBasicSalary(Double.parseDouble(req.getParameter("basicSalary")));

        int empId = employeeDAO.addEmployee(e);

        // Auto-create login: username = email prefix, password = "emp123"
        if (empId > 0) {
            String username = e.getEmail().split("@")[0];
            String hashedPwd = PasswordUtil.hash("emp123");

            try {
                userDAO.createUserForEmployee(username, hashedPwd, empId);
            } catch (SQLException ignored) {
                // username might already exist — skip silently
            }
        }

        req.getSession().setAttribute("msg",
            "Employee added. Login: " + e.getEmail().split("@")[0] + " / emp123");

        resp.sendRedirect("EmployeeServlet?action=list");
    }

    private void updateEmployee(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException {

        Employee e = new Employee();
        e.setId(Integer.parseInt(req.getParameter("id")));
        e.setName(req.getParameter("name"));
        e.setEmail(req.getParameter("email"));
        e.setPhone(req.getParameter("phone"));
        e.setDepartment(req.getParameter("department"));
        e.setDesignation(req.getParameter("designation"));
        e.setDoj(LocalDate.parse(req.getParameter("doj")));
        e.setBasicSalary(Double.parseDouble(req.getParameter("basicSalary")));

        employeeDAO.updateEmployee(e);
        req.getSession().setAttribute("msg", "Employee updated successfully.");
        resp.sendRedirect("EmployeeServlet?action=list");
    }

    private void deleteEmployee(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));

        // Delete user account first (FK)
        userDAO.deleteByEmployeeId(id);
        employeeDAO.deleteEmployee(id);

        req.getSession().setAttribute("msg", "Employee deleted.");
        resp.sendRedirect("EmployeeServlet?action=list");
    }
}