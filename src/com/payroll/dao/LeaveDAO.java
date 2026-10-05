package com.payroll.dao;

import com.payroll.model.Leave;
import com.payroll.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeaveDAO {

    // Employee applies for leave
    public boolean applyLeave(Leave l) throws SQLException {
        String sql = "INSERT INTO leaves(employee_id, from_date, to_date, " +
                     "reason, status) VALUES(?,?,?,?, 'PENDING')";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, l.getEmployeeId());
            ps.setDate(2, Date.valueOf(l.getFromDate()));
            ps.setDate(3, Date.valueOf(l.getToDate()));
            ps.setString(4, l.getReason());

            return ps.executeUpdate() > 0;
        }
    }

    // Admin: all leave requests
    public List<Leave> getAllLeaves() throws SQLException {
        List<Leave> list = new ArrayList<>();

        String sql = "SELECT l.id, l.employee_id, e.name AS emp_name, " +
                     "       l.from_date, l.to_date, l.reason, l.status " +
                     "FROM leaves l " +
                     "JOIN employees e ON e.id = l.employee_id " +
                     "ORDER BY l.id DESC";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    // Employee: own leave list
    public List<Leave> getByEmployee(int employeeId) throws SQLException {
        List<Leave> list = new ArrayList<>();

        String sql = "SELECT id, employee_id, from_date, to_date, reason, status " +
                     "FROM leaves WHERE employee_id=? ORDER BY id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employeeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }

    // Admin approves/rejects
    public boolean updateStatus(int leaveId, String status) throws SQLException {
        String sql = "UPDATE leaves SET status=? WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, leaveId);
            return ps.executeUpdate() > 0;
        }
    }

    // Count pending (dashboard)
    public int countPending() throws SQLException {
        String sql = "SELECT COUNT(*) FROM leaves WHERE status='PENDING'";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    private Leave mapRow(ResultSet rs) throws SQLException {
        Leave l = new Leave();
        l.setId(rs.getInt("id"));
        l.setEmployeeId(rs.getInt("employee_id"));

        try { l.setEmployeeName(rs.getString("emp_name")); } catch (SQLException ignored) {}

        l.setFromDate(rs.getDate("from_date").toLocalDate());
        l.setToDate(rs.getDate("to_date").toLocalDate());
        l.setReason(rs.getString("reason"));
        l.setStatus(rs.getString("status"));
        return l;
    }
}