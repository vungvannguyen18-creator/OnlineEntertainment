<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<jsp:include page="/views/layout/admin_header.jsp" />

<!-- Tabs Giao diện -->
<ul class="nav nav-tabs mb-4" id="videoTabs" role="tablist">
  <li class="nav-item" role="presentation">
    <button class="nav-link ${activeTab == 'videoEdition' || empty activeTab ? 'active' : ''}" 
            id="edition-tab" data-bs-toggle="tab" data-bs-target="#edition" type="button" role="tab">
        CẬP NHẬT VIDEO
    </button>
  </li>
  <li class="nav-item" role="presentation">
    <button class="nav-link ${activeTab == 'videoList' ? 'active' : ''}" 
            id="list-tab" data-bs-toggle="tab" data-bs-target="#list" type="button" role="tab">
        DANH SÁCH VIDEO
    </button>
  </li>
</ul>

<div class="tab-content" id="videoTabsContent">
  
  <!-- TAB: VIDEO EDITION -->
  <div class="tab-pane fade ${activeTab == 'videoEdition' || empty activeTab ? 'show active' : ''}" id="edition" role="tabpanel">
      
      <c:if test="${not empty message}">
          <div class="alert alert-success fw-bold">${message}</div>
      </c:if>
      <c:if test="${not empty error}">
          <div class="alert alert-danger fw-bold">${error}</div>
      </c:if>
      
      <div class="card border border-warning shadow-sm mb-4">
          <div class="card-body" style="background-color: #fffaf0;">
              <form action="${pageContext.request.contextPath}/admin/video" method="POST">
                  
                  <div class="row">
                      <!-- Cột Trái: POSTER -->
                      <div class="col-md-4 text-center">
                          <div class="border border-secondary bg-white d-flex align-items-center justify-content-center mb-3" style="height: 200px;">
                              <c:choose>
                                  <c:when test="${not empty formVideo.id}">
                                      <img src="https://img.youtube.com/vi/${formVideo.id}/maxresdefault.jpg" class="img-fluid" style="max-height: 100%;" alt="POSTER">
                                  </c:when>
                                  <c:otherwise>
                                      <span class="fw-bold text-muted fs-4">POSTER</span>
                                  </c:otherwise>
                              </c:choose>
                          </div>
                      </div>
                      
                      <!-- Cột Phải: Thông tin -->
                      <div class="col-md-8">
                          <div class="mb-3">
                              <label class="form-label fw-bold">MÃ YOUTUBE?</label>
                              <input type="text" class="form-control border-warning" name="id" value="${formVideo.id}" required <c:if test="${not empty formVideo.id}">readonly</c:if>>
                          </div>
                          <div class="mb-3">
                              <label class="form-label fw-bold">TIÊU ĐỀ VIDEO?</label>
                              <input type="text" class="form-control border-warning" name="title" value="${formVideo.title}" required>
                          </div>
                          <div class="mb-3">
                              <label class="form-label fw-bold">LƯỢT XEM?</label>
                              <input type="number" class="form-control border-warning" name="views" value="${formVideo.views != null ? formVideo.views : 0}" required>
                          </div>
                          <div class="mb-3">
                              <div class="form-check form-check-inline">
                                  <input class="form-check-input border-danger" type="radio" name="active" id="active" value="true" ${formVideo.active == true || formVideo.active == null ? 'checked' : ''}>
                                  <label class="form-check-label fw-bold" for="active">Hoạt động</label>
                              </div>
                              <div class="form-check form-check-inline">
                                  <input class="form-check-input border-danger" type="radio" name="active" id="inactive" value="false" ${formVideo.active == false ? 'checked' : ''}>
                                  <label class="form-check-label fw-bold" for="inactive">Tạm dừng</label>
                              </div>
                          </div>
                      </div>
                  </div>
                  
                  <!-- Mô tả -->
                  <div class="row mt-3">
                      <div class="col-12">
                          <label class="form-label fw-bold">MÔ TẢ NGẮN?</label>
                          <textarea class="form-control border-warning" name="description" rows="4">${formVideo.description}</textarea>
                      </div>
                  </div>
                  
                  <!-- Nút bấm -->
                  <div class="row mt-4">
                      <div class="col-12 d-flex justify-content-end gap-2">
                          <button type="submit" formaction="${pageContext.request.contextPath}/admin/video/create" class="btn btn-secondary fw-bold" ${not empty formVideo.id ? 'disabled' : ''}>Thêm mới</button>
                          <button type="submit" formaction="${pageContext.request.contextPath}/admin/video/update" class="btn btn-secondary fw-bold" ${empty formVideo.id ? 'disabled' : ''}>Cập nhật</button>
                          <button type="submit" formaction="${pageContext.request.contextPath}/admin/video/delete" class="btn btn-secondary fw-bold" ${empty formVideo.id ? 'disabled' : ''} onclick="return confirm('Bạn có chắc muốn xóa video này?');">Xóa</button>
                          <a href="${pageContext.request.contextPath}/admin/video" class="btn btn-secondary fw-bold">Làm mới</a>
                      </div>
                  </div>
              </form>
          </div>
      </div>
  </div>
  
  <!-- TAB: VIDEO LIST -->
  <div class="tab-pane fade ${activeTab == 'videoList' ? 'show active' : ''}" id="list" role="tabpanel">
      <div class="card border border-warning shadow-sm">
          <div class="card-body p-0">
              <table class="table table-bordered table-striped m-0">
                  <thead style="background-color: #fffaf0;">
                      <tr>
                          <th>Mã Youtube</th>
                          <th>Tiêu đề</th>
                          <th>Lượt xem</th>
                          <th>Trạng thái</th>
                          <th>Thao tác</th>
                      </tr>
                  </thead>
                  <tbody>
                      <c:forEach items="${videos}" var="v">
                          <tr>
                              <td>${v.id}</td>
                              <td>${v.title}</td>
                              <td>${v.views}</td>
                              <td><span class="badge ${v.active ? 'bg-success' : 'bg-danger'}">${v.active ? 'Đã duyệt (Công khai)' : 'Chờ duyệt / Nháp'}</span></td>
                              <td class="text-center">
                                  <a href="${pageContext.request.contextPath}/admin/video/edit?id=${v.id}" class="text-primary text-decoration-none fw-bold me-2"><i class="fa-solid fa-pen-to-square"></i> Sửa</a>
                                  <form action="${pageContext.request.contextPath}/admin/video" method="POST" class="d-inline-block m-0">
                                      <input type="hidden" name="id" value="${v.id}">
                                      <c:choose>
                                          <c:when test="${!v.active}">
                                              <button type="submit" formaction="${pageContext.request.contextPath}/admin/video/approve" class="btn btn-sm btn-success fw-bold" onclick="return confirm('Duyệt video này?');">Duyệt</button>
                                          </c:when>
                                          <c:otherwise>
                                              <button type="submit" formaction="${pageContext.request.contextPath}/admin/video/reject" class="btn btn-sm btn-danger fw-bold" onclick="return confirm('Hủy duyệt video này?');">Từ chối</button>
                                          </c:otherwise>
                                      </c:choose>
                                  </form>
                              </td>
                          </tr>
                      </c:forEach>
                  </tbody>
              </table>
          </div>
          <div class="card-footer bg-light d-flex justify-content-between align-items-center border-warning">
              <span class="fw-bold">${videos.size()} video</span>
              <div class="btn-group">
                  <!-- Tạm thời dùng nút giả lập phân trang vì đề bài yêu cầu giao diện có sẵn -->
                  <button type="button" class="btn btn-secondary btn-sm fw-bold">|&lt;</button>
                  <button type="button" class="btn btn-secondary btn-sm fw-bold">&lt;&lt;</button>
                  <button type="button" class="btn btn-secondary btn-sm fw-bold">&gt;&gt;</button>
                  <button type="button" class="btn btn-secondary btn-sm fw-bold">&gt;|</button>
              </div>
          </div>
      </div>
  </div>
  
</div>

<jsp:include page="/views/layout/admin_footer.jsp" />
