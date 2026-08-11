<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<jsp:include page="/views/layout/header.jsp" />

<div class="row justify-content-center mt-5 mb-5">
    <div class="col-md-8">
        <c:if test="${not empty error}">
            <div class="alert alert-danger fw-bold">${error}</div>
        </c:if>
        <c:if test="${not empty message}">
            <div class="alert alert-success fw-bold">${message}</div>
        </c:if>
        
        <div class="card border border-warning shadow-sm">
            <!-- Header -->
            <div class="card-header border-warning" style="background-color: #e8f5e9;">
                <h5 class="m-0 fw-bold text-dark" style="font-size: 18px;">ĐĂNG KÝ</h5>
            </div>
            
            <form action="${pageContext.request.contextPath}/register" method="POST">
                <!-- Body -->
                <div class="card-body" style="padding: 20px;">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label fw-bold text-dark" style="font-size: 16px;">TÊN ĐĂNG NHẬP?</label>
                            <input type="text" class="form-control border-warning" name="id" minlength="4" pattern="^[a-zA-Z0-9_]+$" title="Ít nhất 4 ký tự, không chứa khoảng trắng hoặc ký tự đặc biệt">
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label fw-bold text-dark" style="font-size: 16px;">MẬT KHẨU?</label>
                            <div class="input-group">
                                <input type="password" class="form-control border-warning" name="password" id="regPwd" minlength="6" title="Mật khẩu phải có ít nhất 6 ký tự">
                                <button class="btn btn-outline-warning" type="button" onclick="toggleRegPwd()">
                                    <i class="fa-regular fa-eye" id="iconReg"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                    
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label fw-bold text-dark" style="font-size: 16px;">HỌ VÀ TÊN?</label>
                            <input type="text" class="form-control border-warning" name="fullname" minlength="3">
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label fw-bold text-dark" style="font-size: 16px;">ĐỊA CHỈ EMAIL?</label>
                            <input type="email" class="form-control border-warning" name="email">
                        </div>
                    </div>
                </div>
                
                <!-- Footer -->
                <div class="card-footer bg-light border-warning d-flex justify-content-end p-2">
                    <button type="submit" class="btn btn-warning fw-bold text-white px-4" 
                            style="background-color: #ff8c00; border-color: #ff8c00; font-size: 16px; border-radius: 4px; box-shadow: 1px 2px 3px rgba(0,0,0,0.2);">
                        Đăng Ký
                    </button>
                </div>
            </form>
            
            <script>
                function toggleRegPwd() {
                    var pwdInput = document.getElementById("regPwd");
                    var icon = document.getElementById("iconReg");
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
                <a href="${pageContext.request.contextPath}/login" class="text-decoration-none text-primary">Đã có tài khoản? Đăng nhập</a>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/views/layout/footer.jsp" />
