<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Kênh của ${channelUser.fullname} - Online Entertainment</title>
</head>
<body>
<jsp:include page="/views/layout/header.jsp" />

<div class="container-fluid py-4">
    <div class="row mb-5">
        <div class="col-12 text-center">
            <i class="fa-solid fa-user-circle text-secondary mb-3" style="font-size: 8rem;"></i>
            <h2 class="fw-bold text-dark">${channelUser.fullname}</h2>
            <div class="text-muted mb-3" style="font-size: 1.1rem;">@${channelUser.id} &bull; ${followerCount} người theo dõi</div>
            
            <c:if test="${not empty sessionScope.user && sessionScope.user.id != channelUser.id}">
                <c:choose>
                    <c:when test="${isFollowing}">
                        <a href="${pageContext.request.contextPath}/follow?channelId=${channelUser.id}" class="btn rounded-pill fw-bold px-4 py-2" style="background-color: #f2f2f2; color: #0f0f0f; border: 1px solid #d9d9d9;">
                            <i class="fa-regular fa-bell"></i> Đang theo dõi
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/follow?channelId=${channelUser.id}" class="btn rounded-pill fw-bold px-4 py-2" style="background-color: #0f0f0f; color: #fff;">
                            Theo dõi
                        </a>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </div>
    </div>
    
    <hr class="mb-4">
    
    <h4 class="fw-bold mb-4">Video tải lên</h4>
    
    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4">
        <c:choose>
            <c:when test="${empty videos}">
                <div class="col-12">
                    <div class="alert alert-light text-center py-5">
                        Kênh này chưa có video nào.
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach items="${videos}" var="v">
                    <div class="col">
                        <div class="card h-100 border-0 bg-transparent">
                            <a href="${pageContext.request.contextPath}/video?id=${v.id}" class="text-decoration-none">
                                <div class="position-relative">
                                    <c:choose>
                                        <c:when test="${fn:contains(v.id, '.')}">
                                            <img src="${pageContext.request.contextPath}/uploads/${v.poster}" class="card-img-top rounded-4" style="aspect-ratio: 16/9; object-fit: cover;" alt="Poster">
                                        </c:when>
                                        <c:otherwise>
                                            <img src="https://img.youtube.com/vi/${v.id}/maxresdefault.jpg" class="card-img-top rounded-4" style="aspect-ratio: 16/9; object-fit: cover;" alt="Poster">
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </a>
                            <div class="card-body px-0 pt-2 pb-0 d-flex">
                                <div>
                                    <h6 class="card-title fw-bold text-dark mb-1" style="font-size: 16px; line-height: 1.4; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; overflow: hidden;">
                                        <a href="${pageContext.request.contextPath}/video?id=${v.id}" class="text-decoration-none text-dark">${v.title}</a>
                                    </h6>
                                    <div class="text-muted-custom" style="font-size: 14px;">
                                        ${v.views} lượt xem
                                    </div>
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
