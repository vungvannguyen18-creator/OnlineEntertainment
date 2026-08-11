<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!-- Nhúng Header -->
<jsp:include page="/views/layout/header.jsp" />

<div class="mb-4 pb-2" style="overflow-x: auto; white-space: nowrap; border-bottom: 1px solid #eee;">
    <a href="${pageContext.request.contextPath}/home" 
       class="btn ${empty selectedCategory ? 'btn-dark' : 'btn-light border'} rounded-pill px-3 fw-bold me-2">
        Tất cả Video
    </a>
    <c:forEach items="${applicationScope.globalCategories}" var="cat">
        <a href="${pageContext.request.contextPath}/home?category=${cat.id}" 
           class="btn ${selectedCategory == cat.id ? 'btn-dark' : 'btn-light border'} rounded-pill px-3 fw-bold me-2">
            ${cat.name}
        </a>
    </c:forEach>
</div>

<h3 class="mb-4 fw-bold" style="color: #d32f2f;"><i class="fa-brands fa-youtube"></i> Video Nổi Bật</h3>

<div class="row g-4">
    <!-- Vòng lặp duyệt qua danh sách video lấy từ DB -->
    <c:forEach items="${videos}" var="v">
        <div class="col-md-4">
            <div class="card h-100" style="border: 1px solid #e0e0e0; border-radius: 0;">
                <!-- URL ảnh Poster lấy ID video làm nền -->
                <div style="border: 1px solid #f39c12; margin: 10px; padding: 2px;">
                    <a href="${pageContext.request.contextPath}/video?id=${v.id}">
                        <img src="https://img.youtube.com/vi/${v.id}/maxresdefault.jpg" class="card-img-top" alt="Poster" style="border-radius: 0;">
                    </a>
                </div>
                
                <div class="card-body p-2" style="background-color: #e8f5e9; border-top: 1px solid #e0e0e0; border-bottom: 1px solid #e0e0e0;">
                    <!-- In tên Tiêu đề Video -->
                    <h5 class="card-title text-truncate m-0 fw-bold" style="color: #000; font-size: 16px;">${v.title}</h5>
                </div>
                
                <div class="card-footer bg-white border-0 d-flex justify-content-between p-2 align-items-center">
                    <span class="badge bg-secondary"><i class="fa-solid fa-eye"></i> ${v.views}</span>
                    <div class="gap-2 d-flex">
                        <a href="${pageContext.request.contextPath}/like?id=${v.id}" class="btn btn-success btn-sm fw-bold" style="border-radius: 4px; box-shadow: 1px 2px 3px rgba(0,0,0,0.2);">Like</a>
                        <a href="${pageContext.request.contextPath}/share?id=${v.id}" class="btn btn-warning btn-sm fw-bold text-white" style="background-color: #ff8c00; border-color: #ff8c00; border-radius: 4px; box-shadow: 1px 2px 3px rgba(0,0,0,0.2);">Share</a>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<!-- Phân trang điều hướng (Yêu cầu của assignment) -->
<c:set var="catParam" value="${not empty selectedCategory ? '&category=' += selectedCategory : ''}" />
<div class="d-flex justify-content-center mt-4 mb-5">
    <nav>
        <ul class="pagination pagination-sm">
            <li class="page-item"><a class="page-link text-secondary bg-light fw-bold" href="${pageContext.request.contextPath}/home?page=0${catParam}">|&lt;</a></li>
            <li class="page-item"><a class="page-link text-secondary bg-light fw-bold ms-1" href="${pageContext.request.contextPath}/home?page=${currentPage - 1 < 0 ? 0 : currentPage - 1}${catParam}">&lt;&lt;</a></li>
            <li class="page-item"><a class="page-link text-secondary bg-light fw-bold ms-1" href="${pageContext.request.contextPath}/home?page=${currentPage + 1 >= totalPages ? totalPages - 1 : currentPage + 1}${catParam}">&gt;&gt;</a></li>
            <li class="page-item"><a class="page-link text-secondary bg-light fw-bold ms-1" href="${pageContext.request.contextPath}/home?page=${totalPages - 1}${catParam}">&gt;|</a></li>
        </ul>
    </nav>
</div>

<!-- Nhúng Footer -->
<jsp:include page="/views/layout/footer.jsp" />
