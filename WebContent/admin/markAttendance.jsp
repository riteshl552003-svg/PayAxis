<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<c:set var="pageTitle" value="Mark Attendance" />
<jsp:include page="/WEB-INF/common/header.jsp" />
<jsp:include page="/WEB-INF/common/sidebar.jsp" />

<div class="main-content">

    <div class="topbar">
        <h5 class="mb-0 fw-bold">Mark Attendance — <%= java.time.LocalDate.now() %></h5>
    </div>

    <form action="${pageContext.request.contextPath}/AttendanceServlet" method="post">
        <input type="hidden" name="action" value="save">

        <div class="card border-0 shadow-sm rounded-4">
            <div class="card-body table-responsive">
                <table class="table align-middle">
                    <thead>
                        <tr>
                            <th>ID</th>
                           