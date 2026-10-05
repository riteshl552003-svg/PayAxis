<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="pageTitle" value="Edit Employee" />
<jsp:include page="/WEB-INF/common/header.jsp" />
<jsp:include page="/WEB-INF/common/sidebar.jsp" />

<div class="main-content">

    <div class="topbar">
        <h5 class="mb-0 fw-bold">Edit Employee</h5>
        <a href="${pageContext.request.contextPath}/EmployeeServlet?action=list"
           class="btn btn-sm btn-outline-secondary">
            <i class="bi bi-arrow-left"></i> Back
        </a>
    </div>

    <div class="card border-0 shadow-sm rounded-4">
        <div class="card-body p-4">

            <form action="${pageContext.request.contextPath}/EmployeeServlet" method="post">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="id" value="${employee.id}">

                <div class="row g-3">
                    <div class="col-md-6">
                        <label class="form-label">Full Name *</label>
                        <input type="text" name="name" class="form-control"
                               value="${employee.name}" required>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Email *</label>
                        <input type="email" name="email" class="form-control"
                               value="${employee.email}" required>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Phone</label>
                        <input type="text" name="phone" class="form-control"
                               value="${employee.phone}">
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Department</label>
                        <input type="text" name="department" class="form-control"
                               value="${employee.department}">
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Designation</label>
                        <input type="text" name="designation" class="form-control"
                               value="${employee.designation}">
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Date of Joining</label>
                        <input type="date" name="doj" class="form-control"
                               value="${employee.doj}" required>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Basic Salary (₹)</label>
                        <input type="number" name="basicSalary" class="form-control"
                               value="${employee.basicSalary}" step="0.01" required>
                    </div>
                </div>

                <div class="mt-4">
                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-check-circle"></i> Update
                    </button>
                </div>

            </form>

        </div>
    </div>

</div>

<jsp:include page="/WEB-INF/common/footer.jsp" />