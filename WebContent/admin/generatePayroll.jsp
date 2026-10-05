<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="pageTitle" value="Generate Payroll" />
<jsp:include page="/WEB-INF/common/header.jsp" />
<jsp:include page="/WEB-INF/common/sidebar.jsp" />

<div class="main-content">

    <div class="topbar">
        <h5 class="mb-0 fw-bold">Generate Payroll</h5>
        <a href="${pageContext.request.contextPath}/PayrollServlet?action=list"
           class="btn btn-sm btn-outline-secondary">
            <i class="bi bi-arrow-left"></i> Back
        </a>
    </div>

    <div class="card border-0 shadow-sm rounded-4">
        <div class="card-body p-4">

            <form action="${pageContext.request.contextPath}/PayrollServlet" method="post">
                <input type="hidden" name="action" value="generate">

                <div class="row g-3">
                    <div class="col-md-4">
                        <label class="form-label">Month</label>
                        <select name="month" class="form-select" required>
                            <option value="1">January</option>
                            <option value="2">February</option>
                            <option value="3">March</option>
                            <option value="4">April</option>
                            <option value="5">May</option>
                            <option value="6">June</option>
                            <option value="7">July</option>
                            <option value="8">August</option>
                            <option value="9">September</option>
                            <option value="10">October</option>
                            <option value="11">November</option>
                            <option value="12">December</option>
                        </select>
                    </div>

                    <div class="col-md-4">
                        <label class="form-label">Year</label>
                        <input type="number" name="year" class="form-control"
                               value="2025" required>
                    </div>
                </div>

                <div class="alert alert-info mt-4">
                    <strong>Info:</strong> Payroll will be calculated for <b>all employees</b>
                    based on attendance. If payroll already exists for the month,
                    it will be updated.
                </div>

                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-cash"></i> Generate Payroll
                </button>

            </form>

        </div>
    </div>

</div>

<jsp:include page="/WEB-INF/common/footer.jsp" />