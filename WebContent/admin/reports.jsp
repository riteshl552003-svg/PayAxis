<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="pageTitle" value="Reports" />
<jsp:include page="/WEB-INF/common/header.jsp" />
<jsp:include page="/WEB-INF/common/sidebar.jsp" />

<div class="main-content">

    <div class="topbar">
        <h5 class="mb-0 fw-bold">Reports</h5>
    </div>

    <div class="row g-4">
        <div class="col-md-4">
            <div class="card border-0 shadow-sm rounded-4 h-100">
                <div class="card-body text-center p-4">
                    <i class="bi bi-people text-primary" style="font-size:3rem;"></i>
                    <h5 class="mt-3">Employee List</h5>
                    <p class="text-muted small">View and export all employees</p>
                    <a href="${pageContext.request.contextPath}/EmployeeServlet?action=list"
                       class="btn btn-primary">
                        <i class="bi bi-arrow-right"></i> Open
                    </a>
                </div>
            </div>
        </div>

        <div class="col-md-4">
            <div class="card border-0 shadow-sm rounded-4 h-100">
                <div class="card-body text-center p-4">
                    <i class="bi bi-cash-stack text-success" style="font-size:3rem;"></i>
                    <h5 class="mt-3">Payroll Report</h5>
                    <p class="text-muted small">Monthly salary report</p>
                    <a href="${pageContext.request.contextPath}/PayrollServlet?action=list"
                       class="btn btn-success">
                        <i class="bi bi-arrow-right"></i> Open
                    </a>
                </div>
            </div>
        </div>

        <div class="col-md-4">
            <div class="card border-0 shadow-sm rounded-4 h-100">
                <div class="card-body text-center p-4">
                    <i class="bi bi-calendar-check text-warning" style="font-size:3rem;"></i>
                    <h5 class="mt-3">Attendance Report</h5>
                    <p class="text-muted small">Monthly attendance sheet</p>
                    <a href="${pageContext.request.contextPath}/AttendanceServlet?action=list"
                       class="btn btn-warning">
                        <i class="bi bi-arrow-right"></i> Open
                    </a>
                </div>
            </div>
        </div>
    </div>

</div>

<jsp:include page="/WEB-INF/common/footer.jsp" />