package com.payroll.dao;

import com.payroll.model.Attendance;
import com.payroll.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {

    // Mark or update attendance for one employee on a date
    public boolean markAttendance(int employeeId, LocalDate date, String status)
            throws SQLException {

        String sql = "INSERT INTO attendance(employee_id, att_date, status) " +
                     "VALUES(?,?,?) " +
                     "ON DUPLICATE KEY UPDATE status=VALUES(status)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employeeId);
            ps.setDate(2, Date.valueOf(date));
            ps.setString(3, status);

            return ps.executeUpdate() > 0;
        }
    }

    // List attendance for a month (admin view)
    public List<Attendance> getByMonth(int month, int year) throws SQLException {
        List<Attendance> list = new ArrayList<>();

        String sql = "SELECT a.id, a.employee_id, e.name AS emp_name, " +
                     "       a.att_date, a.status " +
                     "FROM attendance a " +
                     "JOIN employees e ON e.id = a.employee_id " +
                     "WHERE MONTH(a.att_date)=? AND YEAR(a.att_date)=? " +
                     "ORDER BY a.att_date DESC, e.name";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, month);
            ps.setInt(2, year);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Attendance a = new Attendance();
                    a.setId(rs.getInt("id"));
                    a.setEmployeeId(rs.getInt("employee_id"));
                    a.setEmployeeName(rs.getString("emp_name"));
                    a.setDate(rs.getDate("att_date").toLocalDate());
                    a.setStatus(rs.getString("status"));
                    list.add(a);
                }
            }
        }
        return list;
    }

    // Employee's own attendance for a month
    public List<Attendance> getByEmployee(int employeeId, int month, int year)
            throws SQLException {

        List<Attendance> list = new ArrayList<>();

        String sql = "SELECT id, employee_id, att_date, status " +
                     "FROM attendance " +
                     "WHERE employee_id=? AND MONTH(att_date)=? AND YEAR(att_date)=? " +
                     "ORDER BY att_date DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employeeId);
            ps.setInt(2, month);
            ps.setInt(3, year);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Attendance a = new Attendance();
                    a.setId(rs.getInt("id"));
                    a.setEmployeeId(rs.getInt("employee_id"));
                    a.setDate(rs.getDate("att_date").toLocalDate());
                    a.setStatus(rs.getString("status"));
                    list.add(a);
                }
            }
        }
        return list;
    }

    // For payroll: count absent days
    public int countAbsentDays(int employeeId, int month, int year)
            throws SQLException {

        String sql = "SELECT COUNT(*) FROM attendance " +
                     "WHERE employee_id=? AND status='ABSENT' " +
                     "AND MONTH(att_date)=? AND YEAR(att_date)=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employeeId);
            ps.setInt(2, month);
            ps.setInt(3, year);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    // For payroll: count present days
    public int countPresentDays(int employeeId, int month, int year)
            throws SQLException {

        String sql = "SELECT COUNT(*) FROM attendance " +
                     "WHERE employee_id=? AND status='PRESENT' " +
                     "AND MONTH(att_date)=? AND YEAR(att_date)=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employeeId);
            ps.setInt(2, month);
            ps.setInt(3, year);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    // Count present today (for dashboard)
    public int countPresentToday() throws SQLException {
        String sql = "SELECT COUNT(*) FROM attendance " +
                     "WHERE att_date=CURDATE() AND status='PRESENT'";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }
}