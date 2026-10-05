<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="pageTitle" value="Attendance" />
<jsp:include page="/WEB-INF/common/header.jsp" />
<jsp:include page="/WEB-INF/common/sidebar.jsp" />

<div class="main-content">

    <div class="topbar">
        <h5 class="mb-0 fw-bold">Attendance</h5>
        <div>
            <a href="${pageContext.request.contextPath}/AttendanceServlet?action=markForm"
               class="btn btn-sm btn-primary">
                <i class="bi bi-calendar-plus"></i> Mark Today
            </a>
            <a href="${pageContext.request.contextPath}/ExcelExportServlet?type=attendance&month=${param.month}&year=${param.year}"
               class="btn btn-sm btn-success">
                <i class="bi bi-file-earmark-excel"></i> Export Excel
            </a>
        </div>
    </div>

    <div class="card border-0 shadow-sm rounded-4 mb-3">
        <div class="card-body">
            <form method="get"
                  action="${pageContext.request.contextPath}/AttendanceServlet"
                  class="row g-2 align-items-end">

                <input type="hidden" name="action" value="list">

                <div class="col-md-3">
                    <label class="form-label small">Month</label>
                    <select name="month" class="form-select">
                        <c:forEach begin="1" end="12" var="m">
                            <option value="${m}" ${param.month == m ? 'selected' : ''}>${m}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-md-3">
                    <label class="form-label small">Year</label>
                    <input type="number" name="year" class="form-control"
                           value="${param.year != null ? param.year : 2025}">
                </div>

                <div class="col-md-3">
                    <button class="btn btn-primary">
                        <i class="bi bi-search"></i> Filter
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
                        <th>Employee ID</th>
                        <th>Name</th>
                        <th>Date</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="a" items="${attendanceList}">
                        <tr>
                            <td>${a.employeeId}</td>
                            <td>${a.employeeName}</td>
                            <td>${a.date}</td>
                            <td>
                                <span class="badge
                                    <c:choose>
                                        <c:when test="${a.status == 'PRESENT'}">bg-success</c:when>
                                        <c:when test="${a.status == 'ABSENT'}">bg-danger</c:when>
                                        <c:when test="${a.status == 'HALF_DAY'}">bg-warning text-dark</c:when>
                                        <c:otherwise>bg-secondary</c:otherwise>
                                    </c:choose>">
                                    ${a.status}
                                </span>
                            </td>
                        </tr>
                    </c:forEach>

                    <c:if test="${empty attendanceList}">
                        <tr><td colspan="4" class="text-center text-muted py-4">No records</td></tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>

</div>

<jsp:include page="/WEB-INF/common/footer.jsp" />