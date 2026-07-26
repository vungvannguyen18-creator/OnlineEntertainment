<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<jsp:include page="/views/layout/header.jsp" />

<div class="container my-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <h2 class="text-center fw-bold mb-4">THÊM VIDEO MỚI</h2>
            
            <c:if test="${not empty error}">
                <div class="alert alert-danger fw-bold">${error}</div>
            </c:if>
            
            <div class="card shadow-sm border-warning">
                <div class="card-body" style="background-color: #fffaf0;">
                    <form action="${pageContext.request.contextPath}/channel/video-form${not empty formVideo.id ? '/edit' : ''}" method="POST" enctype="multipart/form-data">
                        
                        <div class="mb-3">
                            <label class="form-label fw-bold">MÃ YOUTUBE / MÃ VIDEO (*)</label>
                            <input type="text" class="form-control border-warning" name="id" value="${formVideo.id}" required placeholder="Nhập ID video (vd: dQw4w9WgXcQ)" ${not empty formVideo.id ? 'readonly' : ''}>
                        </div>
                        
                        <div class="mb-3">
                            <label class="form-label fw-bold">TIÊU ĐỀ VIDEO (*)</label>
                            <input type="text" class="form-control border-warning" name="title" value="${formVideo.title}" required placeholder="Nhập tiêu đề video">
                        </div>
                        
                        <div class="mb-3">
                            <label class="form-label fw-bold">DANH MỤC</label>
                            <select class="form-select border-warning" name="categoryId">
                                <c:forEach items="${categories}" var="cat">
                                    <option value="${cat.id}" ${formVideo.categoryId == cat.id ? 'selected' : ''}>${cat.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        
                        <div class="mb-3">
                            <label class="form-label fw-bold">ẢNH POSTER <c:if test="${empty formVideo.id}">(*)</c:if></label>
                            <input type="file" class="form-control border-warning" name="poster" accept="image/*" ${empty formVideo.id ? 'required' : ''}>
                            <small class="text-muted">Chọn ảnh đại diện cho video (tối đa 2MB). <c:if test="${not empty formVideo.id}">Bỏ trống nếu không muốn đổi ảnh.</c:if></small>
                        </div>
                        
                        <div class="mb-3">
                            <label class="form-label fw-bold">LƯỢT XEM KHỞI ĐẠO</label>
                            <input type="number" class="form-control border-warning" name="views" value="${formVideo.views != null ? formVideo.views : 0}">
                        </div>
                        
                        <div class="mb-3">
                            <label class="form-label fw-bold">TRẠNG THÁI</label>
                            <div>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input border-danger" type="radio" name="active" id="active" value="true" ${formVideo.active == true || formVideo.active == null ? 'checked' : ''}>
                                    <label class="form-check-label fw-bold" for="active">Công khai</label>
                                </div>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input border-danger" type="radio" name="active" id="inactive" value="false" ${formVideo.active == false && formVideo.active != null ? 'checked' : ''}>
                                    <label class="form-check-label fw-bold" for="inactive">Riêng tư (Nháp)</label>
                                </div>
                            </div>
                        </div>
                        
                        <div class="mb-4">
                            <label class="form-label fw-bold">MÔ TẢ</label>
                            <textarea class="form-control border-warning" name="description" rows="4" placeholder="Nhập nội dung mô tả...">${formVideo.description}</textarea>
                        </div>
                        
                        <div class="d-flex justify-content-end gap-2">
                            <a href="${pageContext.request.contextPath}/index" class="btn btn-outline-secondary fw-bold">Hủy</a>
                            <button type="submit" class="btn btn-danger fw-bold">Lưu Video</button>
                        </div>
                    </form>
                </div>
            </div>
            
        </div>
    </div>
</div>

<jsp:include page="/views/layout/footer.jsp" />
