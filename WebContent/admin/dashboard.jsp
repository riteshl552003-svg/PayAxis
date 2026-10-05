<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="pageTitle" value="Admin Dashboard" />
<jsp:include page="/WEB-INF/common/header.jsp" />
<jsp:include page="/WEB-INF/common/sidebar.jsp" />

<div class="main-content">

    <%-- Topbar --%>
    <div class="topbar">
        <h5 class="mb-0 fw-bold">Dashboard</h5>
        <div>
            <i class="bi bi-person-circle"></i>
            Welcome, <strong>${sessionScope.user}</strong>
        </div>
    </div>

    <%-- Success message --%>
    <c:if test="${not empty sessionScope.msg}">
        <div class="alert alert-success alert-auto-hide">
            ${sessionScope.msg}
        </div>
        <c:remove var="msg" scope="session" />
    </c:if>

    <%-- Stat Cards --%>
    <div class="row g-4">
        <div class="col-md-3">
            <div class="stat-card bg-gradient-blue">
                <p>Total Employees</p>
                <h3>${totalEmployees != null ? totalEmployees : 0}</h3>
                <i class="bi bi-people float-end" style="font-size:2rem;opacity:.4;"></i>
            </div>
        </div>

        <div class="col-md-3">
            <div class="stat-card bg-gradient-green">
                <p>Present Today</p>
                <h3>${presentToday != null ? presentToday : 0}</h3>
                <i class="bi bi-check-circle float-end" style="font-size:2rem;opacity:.4;"></i>
            </div>
        </div>

        <div class="col-md-3">
            <div class="stat-card bg-gradient-orange">
                <p>Pending Leaves</p>
                <h3>${pendingLeaves != null ? pendingLeaves : 0}</h3>
                <i class="bi bi-hourglass-split float-end" style="font-size:2rem;opacity:.4;"></i>
            </div>
        </div>

        <div class="col-md-3">
            <div class="stat-card bg-gradient-red">
                <p>Monthly Payroll (₹)</p>
                <h3>${monthlyPayroll != null ? monthlyPayroll : 0}</h3>
                <i class="bi bi-cash-stack float-end" style="font-size:2rem;opacity:.4;"></i>
            </div>
        </div>
    </div>

    <%-- Quick Actions --%>
    <div class="card mt-4 border-0 shadow-sm rounded-4">
        <div class="card-body">
            <h6 class="fw-bold mb-3">Quick Actions</h6>
            <a href="${pageContext.request.contextPath}/admin/addEmployee.jsp"
               class="btn btn-primary me-2">
                <i class="bi bi-person-plus"></i> Add Employee
            </a>
            <a href="${pageContext.request.contextPath}/PayrollServlet?action=generateForm"
               class="btn btn-success me-2">
                <i class="bi bi-cash"></i> Generate Payroll
            </a>
            <a href="${pageContext.request.contextPath}/admin/importExcel.jsp"
               class="btn btn-warning me-2">
                <i class="bi bi-file-earmark-excel"></i> Import Excel
            </a>
            <a href="${pageContext.request.contextPath}/AttendanceServlet?action=markForm"
               class="btn btn-info">
                <i class="bi bi-calendar-check"></i> Mark Attendance
            </a>
        </div>
    </div>

</div>

<jsp:include page="/WEB-INF/common/footer.jsp" />