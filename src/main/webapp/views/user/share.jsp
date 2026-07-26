<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<jsp:include page="/views/layout/header.jsp" />

<div class="row justify-content-center mt-5 mb-5">
    <div class="col-md-6">
        <c:if test="${not empty error}">
            <div class="alert alert-danger fw-bold">${error}</div>
        </c:if>
        <c:if test="${not empty message}">
            <div class="alert alert-success fw-bold">${message}</div>
        </c:if>
        
        <div class="card border border-warning shadow-sm">
            <!-- Header -->
            <div class="card-header border-warning" style="background-color: #e8f5e9;">
                <h5 class="m-0 fw-bold text-dark" style="font-size: 18px;">SEND VIDEO TO YOUR FRIEND</h5>
            </div>
            
            <form action="${pageContext.request.contextPath}/share" method="POST">
                <!-- Body -->
                <div class="card-body" style="padding: 30px 20px;">
                    <input type="hidden" name="videoId" value="${video.id}">
                    <label class="form-label fw-bold text-dark" style="font-size: 16px;">YOUR FRIEND'S EMAIL?</label>
                    <input type="email" class="form-control border-warning" name="email" multiple required 
                           placeholder="Nhập email, cách nhau bằng dấu phẩy...">
                </div>
                
                <!-- Footer -->
                <div class="card-footer bg-light border-warning d-flex justify-content-end p-2">
                    <button type="submit" class="btn btn-warning fw-bold text-white px-4" 
                            style="background-color: #ff8c00; border-color: #ff8c00; font-size: 16px; border-radius: 4px; box-shadow: 1px 2px 3px rgba(0,0,0,0.2);">
                        Send
                    </button>
                </div>
            </form>
        </div>
        
        <div class="text-center mt-4 fw-bold">
            <a href="${pageContext.request.contextPath}/home" class="text-decoration-none text-primary">Quay lại Trang Chủ</a>
        </div>
    </div>
</div>

<jsp:include page="/views/layout/footer.jsp" />
