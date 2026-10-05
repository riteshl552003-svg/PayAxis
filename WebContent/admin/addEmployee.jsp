<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="pageTitle" value="Add Employee" />
<jsp:include page="/WEB-INF/common/header.jsp" />
<jsp:include page="/WEB-INF/common/sidebar.jsp" />

<div class="main-content">

    <div class="topbar">
        <h5 class="mb-0 fw-bold">Add New Employee</h5>
        <a href="${pageContext.request.contextPath}/EmployeeServlet?action=list"
           class="btn btn-sm btn-outline-secondary">
            <i class="bi bi-arrow-left"></i> Back
        </a>
    </div>

    <div class="card border-0 shadow-sm rounded-4">
        <div class="card-body p-4">

            <form action="${pageContext.request.contextPath}/EmployeeServlet"
                  method="post">
                <input type="hidden" name="action" value="add">

                <div class="row g-3">
                    <div class="col-md-6">
                        <label class="form-label">Full Name *</label>
                        <input type="text" name="name" class="form-control" required>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Email *</label>
                        <input type="email" name="email" class="form-control" required>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Phone</label>
                        <input type="text" name="phone" class="form-control"
                               pattern="[0-9]{10}">
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Department</label>
                        <select name="department" class="form-select">
                            <option value="IT">IT</option>
                            <option value="HR">HR</option>
                            <option value="Finance">Finance</option>
                            <option value="Marketing">Marketing</option>
                            <option value="Sales">Sales</option>
                            <option value="Operations">Operations</option>
                        </select>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Designation</label>
                        <input type="text" name="designation" class="form-control">
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Date of Joining *</label>
                        <input type="date" name="doj" class="form-control" required>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Basic Salary (₹) *</label>
                        <input type="number" name="basicSalary" class="form-control"
                               step="0.01" required>
                    </div>
                </div>

                <div class="mt-4">
                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-check-circle"></i> Save Employee
                    </button>
                    <button type="reset" class="btn btn-outline-secondary">
                        Reset
                    </button>
                </div>

            </form>

        </div>
    </div>

</div>

<jsp:include page="/WEB-INF/common/footer.jsp" />