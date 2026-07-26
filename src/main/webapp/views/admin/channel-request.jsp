<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>

<jsp:include page="/views/layout/admin_header.jsp" />

<div class="d-flex justify-content-between align-items-center mb-4">
    <h3 class="fw-bold m-0 text-uppercase">DUYỆT YÊU CẦU TẠO KÊNH</h3>
</div>

<c:if test="${not empty message}">
    <div class="alert alert-success fw-bold">${message}</div>
</c:if>
<c:if test="${not empty error}">
    <div class="alert alert-danger fw-bold">${error}</div>
</c:if>

<div class="card border border-warning shadow-sm">
    <div class="card-body p-0">
        <table class="table table-bordered table-striped m-0">
            <thead style="background-color: #fffaf0;">
                <tr>
                    <th class="text-center">Mã Yêu Cầu</th>
                    <th>Người Dùng (Tài khoản)</th>
                    <th>Email liên hệ</th>
                    <th class="text-center">Ngày Yêu Cầu</th>
                    <th class="text-center">Trạng Thái</th>
                    <th class="text-center">Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty channelRequests}">
                        <tr>
                            <td colspan="6" class="text-center text-muted py-4">
                                <i>Không có yêu cầu tạo kênh nào đang chờ duyệt.</i>
                            </td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${channelRequests}" var="req">
                            <tr class="align-middle">
                                <td class="text-center fw-bold">${req.id}</td>
                                <td>
                                    <div class="fw-bold">${req.user.fullname}</div>
                                    <small class="text-muted">ID: ${req.user.id}</small>
                                </td>
                                <td>${req.user.email}</td>
                                <td class="text-center">
                                    <fmt:formatDate value="${req.requestDate}" pattern="dd/MM/yyyy HH:mm"/>
                                </td>
                                <td class="text-center">
                                    <span class="badge bg-warning text-dark">CHỜ DUYỆT</span>
                                </td>
                                <td class="text-center">
                                    <form action="${pageContext.request.contextPath}/admin/channel-request" method="POST" class="d-inline-block m-0">
                                        <input type="hidden" name="id" value="${req.id}">
                                        <button type="submit" formaction="${pageContext.request.contextPath}/admin/channel-request/approve" class="btn btn-sm btn-success fw-bold me-1" onclick="return confirm('Xác nhận DUYỆT yêu cầu này?');">
                                            <i class="fa-solid fa-check"></i> Duyệt
                                        </button>
                                        <button type="submit" formaction="${pageContext.request.contextPath}/admin/channel-request/reject" class="btn btn-sm btn-danger fw-bold" onclick="return confirm('Xác nhận TỪ CHỐI yêu cầu này?');">
                                            <i class="fa-solid fa-xmark"></i> Từ chối
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="/views/layout/admin_footer.jsp" />
