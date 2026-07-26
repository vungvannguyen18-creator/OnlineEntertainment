<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<jsp:include page="/views/layout/header.jsp" />

<div class="container my-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="fw-bold m-0 text-uppercase">Danh sách video của kênh</h2>
        <a href="${pageContext.request.contextPath}/channel/video-form" class="btn btn-danger fw-bold">
            <i class="fa-solid fa-video me-2"></i>Thêm Video Mới
        </a>
    </div>

    <div class="card shadow-sm border-warning">
        <div class="card-body p-0">
            <table class="table table-bordered table-striped m-0">
                <thead style="background-color: #fffaf0;">
                    <tr>
                        <th class="text-center">Mã Video</th>
                        <th>Hình Ảnh</th>
                        <th>Tiêu Đề</th>
                        <th class="text-center">Lượt Xem</th>
                        <th class="text-center">Trạng Thái</th>
                        <th class="text-center">Thao Tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty videos}">
                            <tr>
                                <td colspan="6" class="text-center text-muted py-4">
                                    <i>Kênh của bạn chưa có video nào. Hãy bấm "Thêm Video Mới" để bắt đầu!</i>
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${videos}" var="v">
                                <tr class="align-middle">
                                    <td class="text-center fw-bold">${v.id}</td>
                                    <td class="text-center" style="width: 150px;">
                                        <c:choose>
                                            <c:when test="${not empty v.poster}">
                                                <img src="${pageContext.request.contextPath}/uploads/${v.poster}" alt="Poster" class="img-fluid rounded" style="max-height: 80px;" onerror="this.src='https://img.youtube.com/vi/${v.id}/mqdefault.jpg'">
                                            </c:when>
                                            <c:otherwise>
                                                <img src="https://img.youtube.com/vi/${v.id}/mqdefault.jpg" alt="Poster" class="img-fluid rounded" style="max-height: 80px;">
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <div class="fw-bold">${v.title}</div>
                                        <small class="text-muted text-truncate d-inline-block" style="max-width: 250px;">
                                            ${v.description}
                                        </small>
                                    </td>
                                    <td class="text-center">${v.views}</td>
                                    <td class="text-center">
                                        <span class="badge ${v.active ? 'bg-success' : 'bg-secondary'}">
                                            ${v.active ? 'Công khai' : 'Nháp'}
                                        </span>
                                    </td>
                                    <td class="text-center">
                                        <a href="${pageContext.request.contextPath}/channel/video-form/edit?id=${v.id}" class="btn btn-sm btn-outline-primary fw-bold">
                                            <i class="fa-solid fa-pen-to-square"></i> Sửa
                                        </a>
                                        <form action="${pageContext.request.contextPath}/channel/video-form/delete" method="POST" class="d-inline-block m-0 ms-1">
                                            <input type="hidden" name="id" value="${v.id}">
                                            <button type="submit" class="btn btn-sm btn-outline-danger fw-bold" onclick="return confirm('Bạn có chắc chắn muốn xóa video này?');">
                                                <i class="fa-solid fa-trash"></i> Xóa
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
</div>

<jsp:include page="/views/layout/footer.jsp" />
