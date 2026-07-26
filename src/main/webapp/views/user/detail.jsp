<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="/views/layout/header.jsp" />
<div class="row mt-4 mb-5">
    <!-- Cột bên trái: Chi tiết Video (Giống y hệt bản thiết kế) -->
    <div class="col-lg-8">
        <div class="card border border-warning">
            <!-- Video -->
            <div class="card-body p-2">
                <div class="ratio ratio-16x9">
                    <iframe src="https://www.youtube.com/embed/${video.id}?autoplay=1" allowfullscreen allow="autoplay" style="border: 1px solid #ff8c00;"></iframe>
                </div>
            </div>
            
            <!-- Tiêu đề Video -->
            <div class="card-header bg-success-subtle border-warning fw-bold text-dark" style="background-color: #e8f5e9;">
                ${video.title}
            </div>
            
            <!-- Mô tả -->
            <div class="card-body" style="min-height: 120px;">
                <p class="card-text fw-bold">DESCRIPTION</p>
                <p class="card-text">${video.description}</p>
            </div>
            
            <!-- Footer: Nút Like, Share -->
            <div class="card-footer bg-light border-warning d-flex justify-content-end gap-2 p-2">
                <a href="${pageContext.request.contextPath}/like?id=${video.id}" class="btn btn-primary btn-sm fw-bold px-3 shadow-sm" style="background-color: #4a90e2; border: none;">Like</a>
                <a href="${pageContext.request.contextPath}/share?id=${video.id}" class="btn btn-warning btn-sm fw-bold text-white px-3 shadow-sm" style="background-color: #ff8c00; border: none;">Share</a>
            </div>
        </div>
    </div>
    
    <!-- Cột bên phải: Danh sách Video đã xem (Lấy từ Cookie) -->
    <div class="col-lg-4">
        <c:choose>
            <c:when test="${empty viewedVideos}">
                <div class="alert alert-info border-info fw-bold text-center">Chưa có lịch sử xem</div>
            </c:when>
            <c:otherwise>
                <c:forEach items="${viewedVideos}" var="vv">
                    <div class="card mb-2 border border-success">
                        <div class="row g-0 align-items-center">
                            <!-- Poster -->
                            <div class="col-4">
                                <a href="${pageContext.request.contextPath}/video?id=${vv.id}">
                                    <img src="https://img.youtube.com/vi/${vv.id}/maxresdefault.jpg" class="img-fluid rounded-start border-end border-success p-1" alt="Poster">
                                </a>
                            </div>
                            <!-- Tiêu đề -->
                            <div class="col-8">
                                <div class="card-body p-2">
                                    <a href="${pageContext.request.contextPath}/video?id=${vv.id}" class="text-decoration-none text-dark fw-bold text-decoration-underline" style="font-size: 14px;">
                                        ${vv.title}
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<jsp:include page="/views/layout/footer.jsp" />
</body>
</html>