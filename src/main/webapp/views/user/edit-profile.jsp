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
                <h5 class="m-0 fw-bold text-dark" style="font-size: 18px;">CẬP NHẬT TÀI KHOẢN</h5>
            </div>
            
            <form action="${pageContext.request.contextPath}/edit-profile" method="POST">
                <!-- Body -->
                <div class="card-body" style="padding: 20px;">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label fw-bold text-dark" style="font-size: 16px;">TÊN ĐĂNG NHẬP?</label>
                            <input type="text" class="form-control border-warning" name="id" value="${sessionScope.user.id}" readonly>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label fw-bold text-dark" style="font-size: 16px;">MẬT KHẨU?</label>
                            <input type="password" class="form-control border-warning" name="password" value="******" readonly>
                        </div>
                    </div>
                    
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label fw-bold text-dark" style="font-size: 16px;">HỌ VÀ TÊN?</label>
                            <input type="text" class="form-control border-warning" name="fullname" value="${sessionScope.user.fullname}" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label fw-bold text-dark" style="font-size: 16px;">ĐỊA CHỈ EMAIL?</label>
                            <input type="email" class="form-control border-warning" name="email" value="${sessionScope.user.email}" required>
                        </div>
                    </div>
                </div>
                
                <!-- Footer -->
                <div class="card-footer bg-light border-warning d-flex justify-content-end p-2">
                    <button type="submit" class="btn btn-warning fw-bold text-white px-4" 
                            style="background-color: #ff8c00; border-color: #ff8c00; font-size: 16px; border-radius: 4px; box-shadow: 1px 2px 3px rgba(0,0,0,0.2);">
                        Cập Nhật
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="row justify-content-center mb-5">
    <div class="col-md-8">
        <div class="card border border-warning shadow-sm">
            <div class="card-header border-warning" style="background-color: #fff3e0;">
                <h5 class="m-0 fw-bold text-dark" style="font-size: 18px;">ĐĂNG KÝ KÊNH (CHANNEL)</h5>
            </div>
            <div class="card-body" style="padding: 20px;">
                <p>Bạn muốn tự đăng tải video của riêng mình lên hệ thống? Hãy đăng ký để trở thành Creator ngay hôm nay!</p>
                
                <c:choose>
                    <c:when test="${not empty channelRequest}">
                        <c:if test="${channelRequest.status == 'PENDING'}">
                            <div class="alert alert-info fw-bold m-0"><i class="fa-solid fa-clock me-2"></i> Yêu cầu của bạn đang được duyệt!</div>
                        </c:if>
                        <c:if test="${channelRequest.status == 'APPROVED'}">
                            <div class="alert alert-success fw-bold m-0"><i class="fa-solid fa-check me-2"></i> Kênh của bạn đã được duyệt. Bạn đã có thể đăng video!</div>
                            <div class="mt-3">
                                <a href="${pageContext.request.contextPath}/channel/videos" class="btn btn-success fw-bold">Quản lý kênh ngay</a>
                            </div>
                        </c:if>
                        <c:if test="${channelRequest.status == 'REJECTED'}">
                            <div class="alert alert-danger fw-bold m-0"><i class="fa-solid fa-xmark me-2"></i> Yêu cầu đăng ký kênh của bạn đã bị từ chối!</div>
                            <form action="${pageContext.request.contextPath}/channel/request" method="POST" class="mt-3">
                                <button type="submit" class="btn btn-warning fw-bold">Gửi lại yêu cầu mới</button>
                            </form>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <form action="${pageContext.request.contextPath}/channel/request" method="POST">
                            <button type="submit" class="btn btn-danger fw-bold">Gửi yêu cầu đăng ký kênh</button>
                        </form>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/views/layout/footer.jsp" />
