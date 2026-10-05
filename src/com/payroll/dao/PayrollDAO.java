package com.payroll.dao;

import com.payroll.model.Employee;
import com.payroll.model.Payroll;
import com.payroll.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PayrollDAO {

    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private final AttendanceDAO attendanceDAO = new AttendanceDAO();

    /**
     * Generate payroll for all employees for a given month/year.
     * Returns number of employees processed.
     */
    public int generatePayrollForAll(int month, int year) throws SQLException {

        List<Employee> employees = employeeDAO.getAllEmployees();
        int count = 0;

        for (Employee e : employees) {
            int absentDays = attendanceDAO.countAbsentDays(e.getId(), month, year);
            int presentDays = attendanceDAO.countPresentDays(e.getId(), month, year);

            double basic = e.getBasicSalary();
            double hra   = basic * 0.20;
            double da    = basic * 0.10;
            double bonus = (presentDays >= 25) ? basic * 0.05 : 0;

            double gross = basic + hra + da + bonus;
            double pf    = basic * 0.12;
            double tax   = (gross > 50000) ? gross * 0.10
                         : (gross > 30000) ? gross * 0.05
                         : 0;
            double leaveDeduct = absentDays * (basic / 30);
            double net = gross - pf - tax - leaveDeduct;

            saveOrUpdate(e.getId(), month, year,
                basic, hra, da, bonus, pf, tax, leaveDeduct, net);
            count++;
        }
        return count;
    }

    private void saveOrUpdate(int employeeId, int month, int year,
        double basic, double hra, double da, double bonus,
        double pf, double tax, double leaveDeduct, double net)
        throws SQLException {

        String sql = "INSERT INTO payroll(employee_id, month, year, basic, hra, da, " +
                     "bonus, pf, tax, leave_deduction, net_salary, generated_on) " +
                     "VALUES(?,?,?,?,?,?,?,?,?,?,?,?) " +
                     "ON DUPLICATE KEY UPDATE basic=VALUES(basic), hra=VALUES(hra), " +
                     "da=VALUES(da), bonus=VALUES(bonus), pf=VALUES(pf), " +
                     "tax=VALUES(tax), leave_deduction=VALUES(leave_deduction), " +
                     "net_salary=VALUES(net_salary), generated_on=VALUES(generated_on)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employeeId);
            ps.setInt(2, month);
            ps.setInt(3, year);
            ps.setDouble(4, basic);
            ps.setDouble(5, hra);
            ps.setDouble(6, da);
            ps.setDouble(7, bonus);
            ps.setDouble(8, pf);
            ps.setDouble(9, tax);
            ps.setDouble(10, leaveDeduct);
            ps.setDouble(11, net);
            ps.setDate(12, Date.valueOf(LocalDate.now()));

            ps.executeUpdate();
        }
    }

    // Admin: payroll for a given month
    public List<Payroll> getPayrollByMonth(int month, int year) throws SQLException {
        List<Payroll> list = new ArrayList<>();

        String sql = "SELECT p.*, e.name AS emp_name, e.department " +
                     "FROM payroll p " +
                     "JOIN employees e ON e.id = p.employee_id " +
                     "WHERE p.month=? AND p.year=? " +
                     "ORDER BY e.name";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, month);
            ps.setInt(2, year);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }

    // Employee: own payslips
    public List<Payroll> getByEmployee(int employeeId) throws SQLException {
        List<Payroll> list = new ArrayList<>();

        String sql = "SELECT * FROM payroll WHERE employee_id=? " +
                     "ORDER BY year DESC, month DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employeeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }

    // Single payslip for employee + month
    public Payroll getPayslip(int employeeId, int month, int year)
            throws SQLException {

        String sql = "SELECT * FROM payroll " +
                     "WHERE employee_id=? AND month=? AND year=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employeeId);
            ps.setInt(2, month);
            ps.setInt(3, year);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    // Sum of net salary for a month (dashboard)
    public double getMonthlyPayrollTotal(int month, int year) throws SQLException {
        String sql = "SELECT COALESCE(SUM(net_salary),0) FROM payroll " +
                     "WHERE month=? AND year=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, month);
            ps.setInt(2, year);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDouble(1);
            }
        }
        return 0;
    }

    private Payroll mapRow(ResultSet rs) throws SQLException {
        Payroll p = new Payroll();
        p.setId(rs.getInt("id"));
        p.setEmployeeId(rs.getInt("employee_id"));

        try { p.setEmployeeName(rs.getString("emp_name")); } catch (SQLException ignored) {}
        try { p.setDepartment(rs.getString("department")); } catch (SQLException ignored) {}

        p.setMonth(rs.getInt("month"));
        p.setYear(rs.getInt("year"));
        p.setBasic(rs.getDouble("basic"));
        p.setHra(rs.getDouble("hra"));
        p.setDa(rs.getDouble("da"));
        p.setBonus(rs.getDouble("bonus"));
        p.setPf(rs.getDouble("pf"));
        p.setTax(rs.getDouble("tax"));
        p.setLeaveDeduction(rs.getDouble("leave_deduction"));
        p.setNetSalary(rs.getDouble("net_salary"));
        p.setGeneratedOn(rs.getDate("generated_on").toLocalDate());
        return p;
    }
}