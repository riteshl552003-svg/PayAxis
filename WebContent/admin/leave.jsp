<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="pageTitle" value="Leave Requests" />
<jsp:include page="/WEB-INF/common/header.jsp" />
<jsp:include page="/WEB-INF/common/sidebar.jsp" />

<div class="main-content">

    <div class="topbar">
        <h5 class="mb-0 fw-bold">Leave Requests</h5>
    </div>

    <c:if test="${not empty sessionScope.msg}">
        <div class="alert alert-success alert-auto-hide">${sessionScope.msg}</div>
        <c:remove var="msg" scope="session" />
    </c:if>

    <div class="card border-0 shadow-sm rounded-4">
        <div class="card-body table-responsive">
            <table class="table table-hover align-middle">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Employee</th>
                        <th>From</th>
                        <th>To</th>
                        <th>Reason</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="l" items="${leaveList}">
                        <tr>
                            <td>${l.id}</td>
                            <td>${l.employeeName}</td>
                            <td>${l.fromDate}</td>
                            <td>${l.toDate}</td>
                            <td>${l.reason}</td>
                            <td>
                                <span class="badge
                                    <c:choose>
                                        <c:when test="${l.status == 'APPROVED'}">bg-success</c:when>
                                        <c:when test="${l.status == 'REJECTED'}">bg-danger</c:when>
                                        <c:otherwise>bg-warning text-dark</c:otherwise>
                                    </c:choose>">
                                    ${l.status}
                                </span>
                            </td>
                            <td>
                                <c:if test="${l.status == 'PENDING'}">
                                    <a href="${pageContext.request.contextPath}/LeaveServlet?action=approve&id=${l.id}"
                                       class="btn btn-sm btn-outline-success">
                                        <i class="bi bi-check"></i>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/LeaveServlet?action=reject&id=${l.id}"
                                       class="btn btn-sm btn-outline-danger">
                                        <i class="bi bi-x"></i>
                                    </a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>

                    <c:if test="${empty leaveList}">
                        <tr>
                            <td colspan="7" class="text-center text-muted py-4">
                                No leave requests.
                            </td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>

</div>

<jsp:include page="/WEB-INF/common/footer.jsp" />