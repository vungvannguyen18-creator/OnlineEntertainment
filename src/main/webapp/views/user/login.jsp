<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!-- Nhúng Header -->
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
                <h5 class="m-0 fw-bold text-dark" style="font-size: 18px;">ĐĂNG NHẬP</h5>
            </div>
            
            <form action="${pageContext.request.contextPath}/login" method="POST">
                <!-- Body -->
                <div class="card-body" style="padding: 20px;">
                    <div class="mb-3">
                        <label class="form-label fw-bold text-dark" style="font-size: 16px;">TÊN ĐĂNG NHẬP?</label>
                        <input type="text" class="form-control border-warning" name="id" required
                               value="${saved_id != null ? saved_id : ''}">
                    </div>
                    
                    <div class="mb-3">
                        <label class="form-label fw-bold text-dark" style="font-size: 16px;">MẬT KHẨU?</label>
                        <div class="input-group">
                            <input type="password" class="form-control border-warning" name="password" id="loginPwd" required
                                   value="${saved_pwd != null ? saved_pwd : ''}">
                            <button class="btn btn-outline-warning" type="button" onclick="toggleLoginPwd()">
                                <i class="fa-regular fa-eye" id="iconLogin"></i>
                            </button>
                        </div>
                    </div>
                    
                    <div class="form-check">
                        <input class="form-check-input border-warning" type="checkbox" name="remember" id="rememberMe" 
                               ${not empty saved_id ? 'checked' : ''}>
                        <label class="form-check-label text-dark fw-bold" for="rememberMe" style="font-size: 15px;">
                            GHI NHỚ TÀI KHOẢN?
                        </label>
                    </div>
                </div>
                
                <!-- Footer -->
                <div class="card-footer bg-light border-warning d-flex justify-content-end p-2">
                    <button type="submit" class="btn btn-warning fw-bold text-white px-4" 
                            style="background-color: #ff8c00; border-color: #ff8c00; font-size: 16px; border-radius: 4px; box-shadow: 1px 2px 3px rgba(0,0,0,0.2);">
                        Đăng Nhập
                    </button>
                </div>
            </form>
        </div>
        
        <!-- Script cho chức năng ẩn hiện mật khẩu -->
        <script>
            function toggleLoginPwd() {
                var pwdInput = document.getElementById("loginPwd");
                var icon = document.getElementById("iconLogin");
                if (pwdInput.type === "password") {
                    pwdInput.type = "text";
                    icon.classList.remove("fa-eye");
                    icon.classList.add("fa-eye-slash");
                } else {
                    pwdInput.type = "password";
                    icon.classList.remove("fa-eye-slash");
                    icon.classList.add("fa-eye");
                }
            }
        </script>
        
        <div class="text-center mt-4 fw-bold">
            <a href="${pageContext.request.contextPath}/forgot-password" class="text-decoration-none text-danger me-2">Quên mật khẩu?</a> | 
            <a href="${pageContext.request.contextPath}/register" class="text-decoration-none text-success ms-2">Đăng ký mới</a>
        </div>
    </div>
</div>

<!-- Nhúng Footer -->
<jsp:include page="/views/layout/footer.jsp" />