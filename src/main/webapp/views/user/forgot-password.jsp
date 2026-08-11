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
                <h5 class="m-0 fw-bold text-dark" style="font-size: 18px;">QUÊN MẬT KHẨU</h5>
            </div>
            
            <form action="${pageContext.request.contextPath}/forgot-password" method="POST">
                <!-- Body -->
                <div class="card-body" style="padding: 20px;">
                    <div class="mb-3">
                        <label class="form-label fw-bold text-dark" style="font-size: 16px;">TÊN ĐĂNG NHẬP?</label>
                        <input type="text" class="form-control border-warning" name="id">
                    </div>
                    
                    <div class="mb-3">
                        <label class="form-label fw-bold text-dark" style="font-size: 16px;">ĐỊA CHỈ EMAIL?</label>
                        <input type="email" class="form-control border-warning" name="email">
                    </div>
                </div>
                
                <!-- Footer -->
                <div class="card-footer bg-light border-warning d-flex justify-content-end p-2">
                    <button type="submit" class="btn btn-warning fw-bold text-white px-4" 
                            style="background-color: #ff8c00; border-color: #ff8c00; font-size: 16px; border-radius: 4px; box-shadow: 1px 2px 3px rgba(0,0,0,0.2);">
                        Lấy Lại
                    </button>
                </div>
            </form>
        </div>
        
        <div class="text-center mt-4 fw-bold">
            <span style="color: #7f8c8d;">Nhớ ra rồi? </span>
            <a href="${pageContext.request.contextPath}/login" class="text-decoration-none text-primary">Đăng nhập</a>
        </div>
    </div>
</div>

<jsp:include page="/views/layout/footer.jsp" />
