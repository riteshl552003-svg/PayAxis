<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="pageTitle" value="Employees" />
<jsp:include page="/WEB-INF/common/header.jsp" />
<jsp:include page="/WEB-INF/common/sidebar.jsp" />

<div class="main-content">

    <div class="topbar">
        <h5 class="mb-0 fw-bold">Employee Management</h5>
        <div>
            <a href="${pageContext.request.contextPath}/admin/addEmployee.jsp"
               class="btn btn-sm btn-primary">
                <i class="bi bi-plus-circle"></i> Add Employee
            </a>
        </div>
    </div>

    <c:if test="${not empty sessionScope.msg}">
        <div class="alert alert-success alert-auto-hide">${sessionScope.msg}</div>
        <c:remove var="msg" scope="session" />
    </c:if>

    <div class="card border-0 shadow-sm rounded-4">
        <div class="card-body">

            <div class="table-responsive">
                <table class="table table-hover align-middle">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Phone</th>
                            <th>Department</th>
                            <th>Designation</th>
                            <th>Basic Salary</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="e" items="${employees}">
                            <tr>
                                <td>${e.id}</td>
                                <td>${e.name}</td>
                                <td>${e.email}</td>
                                <td>${e.phone}</td>
                                <td>${e.department}</td>
                                <td>${e.designation}</td>
                                <td>₹ ${e.basicSalary}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/EmployeeServlet?action=edit&id=${e.id}"
                                       class="btn btn-sm btn-outline-primary">
                                        <i class="bi bi-pencil"></i>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/EmployeeServlet?action=delete&id=${e.id}"
                                       onclick="return confirmDelete('Delete ${e.name}?')"
                                       class="btn btn-sm btn-outline-danger">
                                        <i class="bi bi-trash"></i>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>

                        <c:if test="${empty employees}">
                            <tr>
                                <td colspan="8" class="text-center text-muted py-4">
                                    <i class="bi bi-inbox" style="font-size:2rem;"></i><br>
                                    No employees found. Add one to get started.
                                </td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>

        </div>
    </div>

</div>

<jsp:include page="/WEB-INF/common/footer.jsp" />