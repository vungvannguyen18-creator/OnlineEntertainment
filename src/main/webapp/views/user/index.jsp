<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>

<!-- Nhúng Header -->
<jsp:include page="/views/layout/header.jsp" />

<div class="mb-4 pb-2" style="overflow-x: auto; white-space: nowrap;">
    <a href="${pageContext.request.contextPath}/home" 
       class="btn ${empty selectedCategory ? 'btn-dark' : 'btn-light'} rounded-pill px-3 py-1 me-2" style="font-size: 14px; font-weight: 500;">
        Tất cả
    </a>
    <c:forEach items="${applicationScope.globalCategories}" var="cat">
        <a href="${pageContext.request.contextPath}/home?category=${cat.id}" 
           class="btn ${selectedCategory == cat.id ? 'btn-dark' : 'btn-light'} rounded-pill px-3 py-1 me-2" style="font-size: 14px; font-weight: 500;">
            ${cat.name}
        </a>
    </c:forEach>
</div>

<c:choose>
    <c:when test="${not empty searchKeyword}">
        <h4 class="mb-4 fw-bold" style="color: #0f0f0f; font-size: 20px;">
            <i class="fa-solid fa-magnifying-glass me-2"></i>Kết quả tìm kiếm cho: "${searchKeyword}"
        </h4>
    </c:when>
    <c:otherwise>
        <h4 class="mb-4 fw-bold" style="color: #0f0f0f; font-size: 20px;">Video nổi bật</h4>
    </c:otherwise>
</c:choose>

<jsp:useBean id="now" class="java.util.Date" />
<div class="row g-4">
    <!-- Vòng lặp duyệt qua danh sách video lấy từ DB -->
    <c:forEach items="${videos}" var="v">
        <div class="col-md-4">
            <div class="card h-100 mb-2">
                <!-- URL ảnh Poster lấy ID video làm nền -->
                <div class="custom-image-wrapper">
                    <a href="${pageContext.request.contextPath}/video?id=${v.id}">
                        <c:choose>
                            <c:when test="${fn:contains(v.id, '.')}">
                                <img src="${pageContext.request.contextPath}/uploads/${v.poster}" class="card-img-top w-100" style="aspect-ratio: 16/9; object-fit: cover;" alt="Poster">
                            </c:when>
                            <c:otherwise>
                                <img src="https://img.youtube.com/vi/${v.id}/maxresdefault.jpg" class="card-img-top w-100" style="aspect-ratio: 16/9; object-fit: cover;" alt="Poster">
                            </c:otherwise>
                        </c:choose>
                    </a>
                </div>
                
                <div class="card-body d-flex mt-2">
                    <div class="me-3">
                        <i class="fa-solid fa-user-circle fa-2x text-secondary"></i>
                    </div>
                    <div>
                        <!-- In tên Tiêu đề Video -->
                        <h5 class="card-title m-0 fw-bold text-truncate" style="font-size: 16px; color: #0f0f0f; max-width: 250px;">
                            <a href="${pageContext.request.contextPath}/video?id=${v.id}" class="text-decoration-none text-dark">${v.title}</a>
                        </h5>
                        <div class="text-muted-custom mt-1" style="font-size: 14px;">${v.user != null ? v.user.fullname : 'Channel Name'}</div>
                        <div class="text-muted-custom" style="font-size: 14px;">
                            ${v.views} lượt xem 
                            <c:choose>
                                <c:when test="${not empty v.uploadDate}">
                                    <c:set var="diffMs" value="${now.time - v.uploadDate.time}" />
                                    <c:set var="diffSecs" value="${diffMs / 1000}" />
                                    &bull; 
                                    <c:choose>
                                        <c:when test="${diffSecs < 60}">vừa xong</c:when>
                                        <c:when test="${diffSecs < 3600}">${fn:substringBefore(diffSecs / 60, '.')} phút trước</c:when>
                                        <c:when test="${diffSecs < 86400}">${fn:substringBefore(diffSecs / 3600, '.')} giờ trước</c:when>
                                        <c:otherwise>${fn:substringBefore(diffSecs / 86400, '.')} ngày trước</c:otherwise>
                                    </c:choose>
                                </c:when>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<!-- Phân trang điều hướng (Yêu cầu của assignment) -->
<c:set var="catParam" value="${not empty selectedCategory ? '&category=' += selectedCategory : ''}" />
<c:set var="searchParam" value="${not empty searchKeyword ? '&search=' += searchKeyword : ''}" />
<div class="d-flex justify-content-center mt-4 mb-5">
    <nav>
        <ul class="pagination pagination-sm">
            <li class="page-item"><a class="page-link text-secondary bg-light fw-bold" href="${pageContext.request.contextPath}/home?page=0${catParam}${searchParam}">|&lt;</a></li>
            <li class="page-item"><a class="page-link text-secondary bg-light fw-bold ms-1" href="${pageContext.request.contextPath}/home?page=${currentPage - 1 < 0 ? 0 : currentPage - 1}${catParam}${searchParam}">&lt;&lt;</a></li>
            <li class="page-item"><a class="page-link text-secondary bg-light fw-bold ms-1" href="${pageContext.request.contextPath}/home?page=${currentPage + 1 >= totalPages ? totalPages - 1 : currentPage + 1}${catParam}${searchParam}">&gt;&gt;</a></li>
            <li class="page-item"><a class="page-link text-secondary bg-light fw-bold ms-1" href="${pageContext.request.contextPath}/home?page=${totalPages > 0 ? totalPages - 1 : 0}${catParam}${searchParam}">&gt;|</a></li>
        </ul>
    </nav>
</div>

<!-- Nhúng Footer -->
<jsp:include page="/views/layout/footer.jsp" />
