package com.payroll.dao;

import com.payroll.model.User;
import com.payroll.util.DBConnection;

import java.sql.*;

public class UserDAO {

    /**
     * Validate login credentials.
     * Returns User object if valid, else null.
     */
    public User login(String username, String hashedPassword) throws SQLException {
        String sql = "SELECT id, username, role, employee_id FROM users " +
                     "WHERE username = ? AND password = ?";

        System.out.println("DEBUG Login: username=" + username);
        System.out.println("DEBUG Login: hash=" + hashedPassword);
        System.out.println("DEBUG SQL: " + sql);

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, hashedPassword);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setId(rs.getInt("id"));
                    u.setUsername(rs.getString("username"));
                    u.setRole(rs.getString("role"));

                    int empId = rs.getInt("employee_id");
                    u.setEmployeeId(rs.wasNull() ? null : empId);
                    return u;
                }
            }
        }
        return null;
    }

    public boolean createUserForEmployee(String username, String hashedPassword,
                                         int employeeId) throws SQLException {
        String sql = "INSERT INTO users(username, password, role, employee_id) " +
                     "VALUES(?, ?, 'EMPLOYEE', ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, hashedPassword);
            ps.setInt(3, employeeId);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteByEmployeeId(int employeeId) throws SQLException {
        String sql = "DELETE FROM users WHERE employee_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employeeId);
            return ps.executeUpdate() > 0;
        }
    }
}