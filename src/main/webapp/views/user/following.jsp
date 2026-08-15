<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>

<jsp:include page="/views/layout/header.jsp" />

<h3 class="text-danger fw-bold mb-4 border-bottom border-warning pb-2"><i class="fa-solid fa-users"></i> KÊNH ĐANG THEO DÕI</h3>

<c:choose>
    <c:when test="${empty videos}">
        <div class="alert alert-warning fw-bold text-center">
            Các kênh bạn theo dõi chưa có video nào, hoặc bạn chưa theo dõi ai! <a href="${pageContext.request.contextPath}/home" class="alert-link">Về trang chủ khám phá thêm</a>.
        </div>
    </c:when>
    <c:otherwise>
        <jsp:useBean id="now" class="java.util.Date" />
        <div class="row g-4">
            <c:forEach items="${videos}" var="v">
                <div class="col-md-4">
                    <div class="card h-100 border-0 bg-transparent">
                        <!-- Thumbnail -->
                        <a href="${pageContext.request.contextPath}/video?id=${v.id}" class="text-decoration-none">
                            <div class="position-relative">
                                <c:choose>
                                    <c:when test="${fn:contains(v.id, '.')}">
                                        <img src="${pageContext.request.contextPath}/uploads/${v.poster}" class="img-fluid rounded-4 w-100" style="aspect-ratio: 16/9; object-fit: cover;" alt="Poster">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="https://img.youtube.com/vi/${v.id}/maxresdefault.jpg" class="img-fluid rounded-4 w-100" style="aspect-ratio: 16/9; object-fit: cover;" alt="Poster">
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </a>
                        
                        <!-- Video Info -->
                        <div class="card-body d-flex px-0 pt-2 pb-0">
                            <div class="me-3">
                                <a href="${pageContext.request.contextPath}/channel?id=${v.user.id}">
                                    <i class="fa-solid fa-user-circle fa-2x text-secondary mt-1"></i>
                                </a>
                            </div>
                            <div>
                                <h5 class="card-title m-0 fw-bold text-truncate" style="font-size: 16px; color: #0f0f0f; max-width: 250px;">
                                    <a href="${pageContext.request.contextPath}/video?id=${v.id}" class="text-decoration-none text-dark">${v.title}</a>
                                </h5>
                                <div class="text-muted-custom mt-1" style="font-size: 14px;">
                                    <a href="${pageContext.request.contextPath}/channel?id=${v.user.id}" class="text-decoration-none text-secondary">
                                        ${v.user != null ? v.user.fullname : 'Channel Name'}
                                    </a>
                                </div>
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
    </c:otherwise>
</c:choose>

<jsp:include page="/views/layout/footer.jsp" />
