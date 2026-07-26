<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<jsp:include page="/views/layout/header.jsp" />

<h3 class="text-danger fw-bold mb-4 border-bottom border-warning pb-2"><i class="fa-solid fa-heart"></i> VIDEO YÊU THÍCH CỦA BẠN</h3>

<c:choose>
    <c:when test="${empty videos}">
        <div class="alert alert-warning fw-bold text-center">
            Bạn chưa yêu thích video nào! <a href="${pageContext.request.contextPath}/home" class="alert-link">Về trang chủ khám phá thêm</a>.
        </div>
    </c:when>
    <c:otherwise>
        <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4 mb-5">
            <c:forEach items="${videos}" var="v">
                <div class="col">
                    <div class="card h-100 border border-success">
                        <!-- Poster click => detail -->
                        <a href="${pageContext.request.contextPath}/video?id=${v.id}">
                            <img src="https://img.youtube.com/vi/${v.id}/maxresdefault.jpg" class="card-img-top border-bottom border-success" alt="Poster">
                        </a>
                        
                        <div class="card-body p-2" style="background-color: #e8f5e9; border-bottom: 1px solid #e0e0e0;">
                            <h5 class="card-title text-truncate m-0 fw-bold" style="color: #000; font-size: 16px;">${v.title}</h5>
                        </div>
                        
                        <div class="card-footer bg-light border-0 d-flex justify-content-end p-2 gap-2">
                            <a href="${pageContext.request.contextPath}/like?id=${v.id}" class="btn btn-primary btn-sm fw-bold shadow-sm px-3" style="background-color: #4a90e2; border: none;">Unlike</a>
                            <a href="${pageContext.request.contextPath}/share?id=${v.id}" class="btn btn-warning btn-sm fw-bold text-white shadow-sm px-3" style="background-color: #ff8c00; border: none;">Share</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:otherwise>
</c:choose>

<jsp:include page="/views/layout/footer.jsp" />
