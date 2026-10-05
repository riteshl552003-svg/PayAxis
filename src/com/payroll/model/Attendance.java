package com.payroll.model;

import java.time.LocalDate;

public class Attendance {
    private int id;
    private int employeeId;
    private String employeeName;   // for display join
    private LocalDate date;
    private String status;         // PRESENT / ABSENT / HALF_DAY / LEAVE

    // getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}