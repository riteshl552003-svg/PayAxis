package com.payroll.model;

import java.time.LocalDate;

public class Payroll {
    private int id;
    private int employeeId;
    private String employeeName;   // for display
    private String department;     // for display
    private int month;
    private int year;
    private double basic;
    private double hra;
    private double da;
    private double bonus;
    private double pf;
    private double tax;
    private double leaveDeduction;
    private double netSalary;
    private LocalDate generatedOn;

    // getters & setters (generate via IDE: Alt+Insert)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public int getMonth() { return month; }
    public void setMonth(int month) { this.month = month; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public double getBasic() { return basic; }
    public void setBasic(double basic) { this.basic = basic; }

    public double getHra() { return hra; }
    public void setHra(double hra) { this.hra = hra; }

    public double getDa() { return da; }
    public void setDa(double da) { this.da = da; }

    public double getBonus() { return bonus; }
    public void setBonus(double bonus) { this.bonus = bonus; }

    public double getPf() { return pf; }
    public void setPf(double pf) { this.pf = pf; }

    public double getTax() { return tax; }
    public void setTax(double tax) { this.tax = tax; }

    public double getLeaveDeduction() { return leaveDeduction; }
    public void setLeaveDeduction(double leaveDeduction) { this.leaveDeduction = leaveDeduction; }

    public double getNetSalary() { return netSalary; }
    public void setNetSalary(double netSalary) { this.netSalary = netSalary; }

    public LocalDate getGeneratedOn() { return generatedOn; }
    public void setGeneratedOn(LocalDate generatedOn) { this.generatedOn = generatedOn; }
}