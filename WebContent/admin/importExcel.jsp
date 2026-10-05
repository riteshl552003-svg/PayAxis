<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="pageTitle" value="Import Employees" />
<jsp:include page="/WEB-INF/common/header.jsp" />
<jsp:include page="/WEB-INF/common/sidebar.jsp" />

<div class="main-content">

    <div class="topbar">
        <h5 class="mb-0 fw-bold">Import Employees from Excel</h5>
    </div>

    <c:if test="${not empty sessionScope.msg}">
        <div class="alert alert-success alert-auto-hide">${sessionScope.msg}</div>
        <c:remove var="msg" scope="session" />
    </c:if>

    <div class="card border-0 shadow-sm rounded-4">
        <div class="card-body p-4">

            <div class="alert alert-info">
                <strong>Instructions:</strong><br>
                1. Download the template below<br>
                2. Fill in employee details<br>
                3. Upload the file<br>
                <b>Required columns:</b> Name, Email, Phone, Department, Designation, DOJ (yyyy-MM-dd), Basic Salary
            </div>

            <a href="${pageContext.request.contextPath}/ExcelImportServlet?action=template"
               class="btn btn-outline-success mb-4">
                <i class="bi bi-download"></i> Download Excel Template
            </a>

            <hr>

            <form action="${pageContext.request.contextPath}/ExcelImportServlet"
                  method="post" enctype="multipart/form-data">

                <div class="mb-3">
                    <label class="form-label fw-bold">Select Excel File (.xlsx)</label>
                    <input type="file" name="file" class="form-control"
                           accept=".xlsx" required>
                </div>

                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-upload"></i> Upload & Import
                </button>

            </form>

        </div>
    </div>

</div>

<jsp:include page="/WEB-INF/common/footer.jsp" />