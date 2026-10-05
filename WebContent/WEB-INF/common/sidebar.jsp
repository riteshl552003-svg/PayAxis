<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String role = (String) session.getAttribute("role");
    String currentPage = request.getServletPath();
%>

<div class="sidebar">
    <a href="#" class="brand">
        <i class="bi bi-cash-coin"></i> PayrollMS
    </a>

    <c:choose>
        <%-- ============ ADMIN MENU ============ --%>
        <c:when test="<%= \"ADMIN\".equals(role) %>">
            <a href="${pageContext.request.contextPath}/admin/dashboard.jsp"
               class="nav-link <%= currentPage.contains("dashboard") ? "active" : "" %>">
                <i class="bi bi-speedometer2"></i> Dashboard
            </a>

            <a href="${pageContext.request.contextPath}/EmployeeServlet?action=list"
               class="nav-link <%= currentPage.contains("employee") ? "active" : "" %>">
                <i class="bi bi-people"></i> Employees
            </a>

            <a href="${pageContext.request.contextPath}/AttendanceServlet?action=list"
               class="nav-link <%= currentPage.contains("attendance") ? "active" : "" %>">
                <i class="bi bi-calendar-check"></i> Attendance
            </a>

            <a href="${pageContext.request.contextPath}/LeaveServlet?action=list"
               class="nav-link <%= currentPage.contains("leave") ? "active" : "" %>">
                <i class="bi bi-envelope-paper"></i> Leave Requests
            </a>

            <a href="${pageContext.request.contextPath}/PayrollServlet?action=list"
               class="nav-link <%= currentPage.contains("payroll") ? "active" : "" %>">
                <i class="bi bi-cash-stack"></i> Payroll
            </a>

            <a href="${pageContext.request.contextPath}/admin/importExcel.jsp"
               class="nav-link <%= currentPage.contains("import") ? "active" : "" %>">
                <i class="bi bi-file-earmark-excel"></i> Import Excel
            </a>

            <a href="${pageContext.request.contextPath}/admin/reports.jsp"
               class="nav-link <%= currentPage.contains("reports") ? "active" : "" %>">
                <i class="bi bi-graph-up"></i> Reports
            </a>
        </c:when>

        <%-- ============ EMPLOYEE MENU ============ --%>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/employee/dashboard.jsp"
               class="nav-link <%= currentPage.contains("dashboard") ? "active" : "" %>">
                <i class="bi bi-speedometer2"></i> Dashboard
            </a>

            <a href="${pageContext.request.contextPath}/PayrollServlet?action=my"
               class="nav-link <%= currentPage.contains("Payslip") ? "active" : "" %>">
                <i class="bi bi-receipt"></i> My Payslips
            </a>

            <a href="${pageContext.request.contextPath}/AttendanceServlet?action=my"
               class="nav-link <%= currentPage.contains("Attendance") ? "active" : "" %>">
                <i class="bi bi-calendar-check"></i> My Attendance
            </a>

            <a href="${pageContext.request.contextPath}/employee/applyLeave.jsp"
               class="nav-link <%= currentPage.contains("apply") ? "active" : "" %>">
                <i class="bi bi-pencil-square"></i> Apply Leave
            </a>
        </c:otherwise>
    </c:choose>

    <a href="${pageContext.request.contextPath}/LogoutServlet"
       class="nav-link mt-4 text-danger">
        <i class="bi bi-box-arrow-right"></i> Logout
    </a>
</div>