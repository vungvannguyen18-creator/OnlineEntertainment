<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<jsp:include page="/views/layout/admin_header.jsp" />

<!-- Tabs Giao diện -->
<ul class="nav nav-tabs mb-4" id="userTabs" role="tablist">
  <li class="nav-item" role="presentation">
    <button class="nav-link ${activeTab == 'userEdition' || empty activeTab ? 'active' : ''}" 
            id="edition-tab" data-bs-toggle="tab" data-bs-target="#edition" type="button" role="tab">
        CẬP NHẬT NGƯỜI DÙNG
    </button>
  </li>
  <li class="nav-item" role="presentation">
    <button class="nav-link ${activeTab == 'userList' ? 'active' : ''}" 
            id="list-tab" data-bs-toggle="tab" data-bs-target="#list" type="button" role="tab">
        DANH SÁCH NGƯỜI DÙNG
    </button>
  </li>
</ul>

<div class="tab-content" id="userTabsContent">
  
  <!-- TAB: USER EDITION -->
  <div class="tab-pane fade ${activeTab == 'userEdition' || empty activeTab ? 'show active' : ''}" id="edition" role="tabpanel">
      
      <c:if test="${not empty message}">
          <div class="alert alert-success fw-bold">${message}</div>
      </c:if>
      <c:if test="${not empty error}">
          <div class="alert alert-danger fw-bold">${error}</div>
      </c:if>
      
      <div class="card border border-warning shadow-sm mb-4">
          <div class="card-body" style="background-color: #fffaf0; padding: 30px;">
              <form action="${pageContext.request.contextPath}/admin/user" method="POST">
                  
                  <div class="row">
                      <div class="col-md-6 mb-4">
                          <label class="form-label fw-bold">TÊN ĐĂNG NHẬP?</label>
                          <input type="text" class="form-control border-warning ${isEdit ? 'bg-light' : ''}" name="id" value="${formUser.id}" ${isEdit ? 'readonly' : ''}>
                      </div>
                      <div class="col-md-6 mb-4">
                          <label class="form-label fw-bold">MẬT KHẨU?</label>
                          <input type="password" class="form-control border-warning" name="password" value="${formUser.password}">
                      </div>
                  </div>
                  
                  <div class="row">
                      <div class="col-md-6 mb-4">
                          <label class="form-label fw-bold">HỌ VÀ TÊN?</label>
                          <input type="text" class="form-control border-warning" name="fullname" value="${formUser.fullname}">
                      </div>
                      <div class="col-md-6 mb-4">
                          <label class="form-label fw-bold">ĐỊA CHỈ EMAIL?</label>
                          <input type="email" class="form-control border-warning" name="email" value="${formUser.email}">
                      </div>
                  </div>
                  
                  <!-- ROLE -->
                  <div class="row">
                      <div class="col-12 mb-3">
                          <label class="form-label fw-bold me-3">VAI TRÒ?</label>
                          <div class="form-check form-check-inline">
                              <input class="form-check-input border-danger" type="radio" name="role" id="roleAdmin" value="true" ${formUser.admin == true ? 'checked' : ''}>
                              <label class="form-check-label fw-bold" for="roleAdmin">Quản trị viên</label>
                          </div>
                          <div class="form-check form-check-inline">
                              <input class="form-check-input border-danger" type="radio" name="role" id="roleUser" value="false" ${formUser.admin == false || formUser.admin == null ? 'checked' : ''}>
                              <label class="form-check-label fw-bold" for="roleUser">Người dùng</label>
                          </div>
                      </div>
                  </div>
                  
                  <!-- Nút bấm -->
                  <div class="row border-top border-warning pt-3 mt-2">
                      <div class="col-12 d-flex justify-content-end gap-2">
                          <button type="submit" formaction="${pageContext.request.contextPath}/admin/user/create" class="btn btn-secondary fw-bold" ${isEdit ? 'disabled' : ''}>Thêm mới</button>
                          <button type="submit" formaction="${pageContext.request.contextPath}/admin/user/update" class="btn btn-secondary fw-bold" ${not isEdit ? 'disabled' : ''}>Cập nhật</button>
                          <button type="submit" formaction="${pageContext.request.contextPath}/admin/user/delete" class="btn btn-secondary fw-bold" ${not isEdit ? 'disabled' : ''} onclick="return confirm('Bạn có chắc muốn xóa tài khoản này?');">Xóa</button>
                          <a href="${pageContext.request.contextPath}/admin/user" class="btn btn-secondary fw-bold">Làm mới</a>
                      </div>
                  </div>
              </form>
          </div>
      </div>
  </div>
  
  <!-- TAB: USER LIST -->
  <div class="tab-pane fade ${activeTab == 'userList' ? 'show active' : ''}" id="list" role="tabpanel">
      <div class="card border border-warning shadow-sm">
          <div class="card-body p-0">
              <table class="table table-bordered table-striped m-0">
                  <thead style="background-color: #fffaf0;">
                      <tr>
                          <th>Tên đăng nhập</th>
                          <th>Họ và tên</th>
                          <th>Email</th>
                          <th>Vai trò</th>
                          <th>Trạng thái</th>
                          <th>Thao tác</th>
                      </tr>
                  </thead>
                  <tbody>
                      <c:forEach items="${users}" var="u">
                          <tr>
                              <td>${u.id}</td>
                              <td>${u.fullname}</td>
                              <td>${u.email}</td>
                              <td><span class="badge ${u.admin ? 'bg-danger' : 'bg-primary'}">${u.admin ? 'Quản trị viên' : 'Người dùng'}</span></td>
                              <td><span class="badge ${u.active ? 'bg-success' : 'bg-secondary'}">${u.active ? 'Hoạt động' : 'Bị Khóa'}</span></td>
                              <td class="text-center">
                                  <a href="${pageContext.request.contextPath}/admin/user/edit?id=${u.id}" class="text-primary text-decoration-none fw-bold me-2"><i class="fa-solid fa-pen-to-square"></i> Sửa</a>
                                  
                                  <form action="${pageContext.request.contextPath}/admin/user" method="POST" class="d-inline-block m-0">
                                      <input type="hidden" name="id" value="${u.id}">
                                      <c:choose>
                                          <c:when test="${u.active}">
                                              <button type="submit" formaction="${pageContext.request.contextPath}/admin/user/lock" class="btn btn-sm btn-outline-danger" onclick="return confirm('Khóa người dùng này?');">Khóa</button>
                                          </c:when>
                                          <c:otherwise>
                                              <button type="submit" formaction="${pageContext.request.contextPath}/admin/user/unlock" class="btn btn-sm btn-outline-success" onclick="return confirm('Mở khóa người dùng này?');">Mở khóa</button>
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
              <span class="fw-bold">Đang hiển thị ${users.size()} tài khoản (Trang ${currentPage + 1}/${totalPages}) - Tổng: ${totalCount}</span>
              <div class="btn-group">
                  <a href="${pageContext.request.contextPath}/admin/user?page=0" class="btn btn-secondary btn-sm fw-bold ${currentPage == 0 ? 'disabled' : ''}">|&lt;</a>
                  <a href="${pageContext.request.contextPath}/admin/user?page=${currentPage - 1}" class="btn btn-secondary btn-sm fw-bold ${currentPage == 0 ? 'disabled' : ''}">&lt;&lt;</a>
                  <a href="${pageContext.request.contextPath}/admin/user?page=${currentPage + 1}" class="btn btn-secondary btn-sm fw-bold ${currentPage == totalPages - 1 || totalPages == 0 ? 'disabled' : ''}">&gt;&gt;</a>
                  <a href="${pageContext.request.contextPath}/admin/user?page=${totalPages - 1}" class="btn btn-secondary btn-sm fw-bold ${currentPage == totalPages - 1 || totalPages == 0 ? 'disabled' : ''}">&gt;|</a>
              </div>
          </div>
      </div>
  </div>
  
</div>

<jsp:include page="/views/layout/admin_footer.jsp" />
