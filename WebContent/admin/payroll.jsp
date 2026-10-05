<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="pageTitle" value="Payroll" />
<jsp:include page="/WEB-INF/common/header.jsp" />
<jsp:include page="/WEB-INF/common/sidebar.jsp" />

<div class="main-content">

    <div class="topbar">
        <h5 class="mb-0 fw-bold">Payroll</h5>
        <div>
            <a href="${pageContext.request.contextPath}/admin/generatePayroll.jsp"
               class="btn btn-sm btn-primary">
                <i class="bi bi-cash"></i> Generate Payroll
            </a>
        </div>
    </div>

    <c:if test="${not empty sessionScope.msg}">
        <div class="alert alert-success alert-auto-hide">${sessionScope.msg}</div>
        <c:remove var="msg" scope="session" />
    </c:if>

    <div class="card border-0 shadow-sm rounded-4 mb-3">
        <div class="card-body">
            <form method="get"
                  action="${pageContext.request.contextPath}/PayrollServlet"
                  class="row g-2 align-items-end">
                <input type="hidden" name="action" value="list">

                <div class="col-md-3">
                    <label class="form-label small">Month</label>
                    <select name="month" class="form-select">
                        <c:forEach begin="1" end="12" var="m">
                            <option value="${m}" ${month == m ? 'selected' : ''}>${m}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-md-3">
                    <label class="form-label small">Year</label>
                    <input type="number" name="year" class="form-control"
                           value="${year != null ? year : 2025}">
                </div>

                <div class="col-md-3">
                    <button class="btn btn-primary">
                        <i class="bi bi-search"></i> View
                    </button>
                </div>
            </form>
        </div>
    </div>

    <div class="card border-0 shadow-sm rounded-4">
        <div class="card-body table-responsive">
            <table class="table table-hover align-middle">
                <thead>
                    <tr>
                        <th>Employee</th>
                        <th>Department</th>
                        <th>Basic</th>
                        <th>HRA</th>
                        <th>DA</th>
                        <th>PF</th>
                        <th>Tax</th>
                        <th>Net Salary</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="p" items="${payrollList}">
                        <tr>
                            <td>${p.employeeName}</td>
                            <td>${p.department}</td>
                            <td>₹ ${p.basic}</td>
                            <td>₹ ${p.hra}</td>
                            <td>₹ ${p.da}</td>
                            <td>₹ ${p.pf}</td>
                            <td>₹ ${p.tax}</td>
                            <td><strong>₹ ${p.netSalary}</strong></td>
                        </tr>
                    </c:forEach>

                    <c:if test="${empty payrollList}">
                        <tr>
                            <td colspan="8" class="text-center text-muted py-4">
                                No payroll records for this month.
                            </td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>

</div>

<jsp:include page="/WEB-INF/common/footer.jsp" />