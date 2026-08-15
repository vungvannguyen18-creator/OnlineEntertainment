<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<jsp:include page="/views/layout/admin_header.jsp" />

<!-- Tabs Giao diện -->
<ul class="nav nav-tabs mb-4" id="categoryTabs" role="tablist">
  <li class="nav-item" role="presentation">
    <button class="nav-link ${activeTab == 'edition' || empty activeTab ? 'active' : ''}" 
            id="edition-tab" data-bs-toggle="tab" data-bs-target="#edition" type="button" role="tab">
        CẬP NHẬT DANH MỤC
    </button>
  </li>
  <li class="nav-item" role="presentation">
    <button class="nav-link ${activeTab == 'list' ? 'active' : ''}" 
            id="list-tab" data-bs-toggle="tab" data-bs-target="#list" type="button" role="tab">
        DANH SÁCH DANH MỤC
    </button>
  </li>
</ul>

<div class="tab-content" id="categoryTabsContent">
  
  <!-- TAB: EDITION -->
  <div class="tab-pane fade ${activeTab == 'edition' || empty activeTab ? 'show active' : ''}" id="edition" role="tabpanel">
      
      <c:if test="${not empty message}">
          <div class="alert alert-success fw-bold">${message}</div>
      </c:if>
      <c:if test="${not empty error}">
          <div class="alert alert-danger fw-bold">${error}</div>
      </c:if>
      
      <div class="card border border-warning shadow-sm mb-4">
          <div class="card-body" style="background-color: #fffaf0; padding: 30px;">
              <form action="${pageContext.request.contextPath}/admin/category" method="POST">
                  
                  <div class="row">
                      <div class="col-md-6 mb-4">
                          <label class="form-label fw-bold">MÃ DANH MỤC?</label>
                          <input type="text" class="form-control border-warning ${isEdit ? 'bg-light' : ''}" name="id" value="${formCategory.id}" ${isEdit ? 'readonly' : ''} placeholder="Nhập mã danh mục (bằng số)">
                      </div>
                      <div class="col-md-6 mb-4">
                          <label class="form-label fw-bold">TÊN DANH MỤC?</label>
                          <input type="text" class="form-control border-warning" name="name" value="${formCategory.name}">
                      </div>
                  </div>
                  
                  <!-- Nút bấm -->
                  <div class="row border-top border-warning pt-3 mt-2">
                      <div class="col-12 d-flex justify-content-end gap-2">
                          <button type="submit" formaction="${pageContext.request.contextPath}/admin/category/create" class="btn btn-secondary fw-bold" ${isEdit ? 'disabled' : ''}>Thêm mới</button>
                          <button type="submit" formaction="${pageContext.request.contextPath}/admin/category/update" class="btn btn-secondary fw-bold" ${not isEdit ? 'disabled' : ''}>Cập nhật</button>
                          <button type="submit" formaction="${pageContext.request.contextPath}/admin/category/delete" class="btn btn-secondary fw-bold" ${not isEdit ? 'disabled' : ''} onclick="return confirm('Bạn có chắc muốn xóa danh mục này?');">Xóa</button>
                          <a href="${pageContext.request.contextPath}/admin/category" class="btn btn-secondary fw-bold">Làm mới</a>
                      </div>
                  </div>
              </form>
          </div>
      </div>
  </div>
  
  <!-- TAB: LIST -->
  <div class="tab-pane fade ${activeTab == 'list' ? 'show active' : ''}" id="list" role="tabpanel">
      <div class="card border border-warning shadow-sm">
          <div class="card-body p-0">
              <table class="table table-bordered table-striped m-0">
                  <thead style="background-color: #fffaf0;">
                      <tr>
                          <th>Mã Danh Mục</th>
                          <th>Tên Danh Mục</th>
                          <th class="text-center">Thao tác</th>
                      </tr>
                  </thead>
                  <tbody>
                      <c:forEach items="${categories}" var="c">
                          <tr>
                              <td class="fw-bold">${c.id}</td>
                              <td>${c.name}</td>
                              <td class="text-center">
                                  <a href="${pageContext.request.contextPath}/admin/category/edit?id=${c.id}" class="text-primary text-decoration-none fw-bold"><i class="fa-solid fa-pen-to-square"></i> Sửa</a>
                              </td>
                          </tr>
                      </c:forEach>
                  </tbody>
              </table>
          </div>
          <div class="card-footer bg-light d-flex justify-content-between align-items-center border-warning">
              <span class="fw-bold">Đang hiển thị ${categories.size()} danh mục (Trang ${currentPage + 1}/${totalPages}) - Tổng: ${totalCount}</span>
              <div class="btn-group">
                  <a href="${pageContext.request.contextPath}/admin/category?page=0" class="btn btn-secondary btn-sm fw-bold ${currentPage == 0 ? 'disabled' : ''}">|&lt;</a>
                  <a href="${pageContext.request.contextPath}/admin/category?page=${currentPage - 1}" class="btn btn-secondary btn-sm fw-bold ${currentPage == 0 ? 'disabled' : ''}">&lt;&lt;</a>
                  <a href="${pageContext.request.contextPath}/admin/category?page=${currentPage + 1}" class="btn btn-secondary btn-sm fw-bold ${currentPage == totalPages - 1 || totalPages == 0 ? 'disabled' : ''}">&gt;&gt;</a>
                  <a href="${pageContext.request.contextPath}/admin/category?page=${totalPages - 1}" class="btn btn-secondary btn-sm fw-bold ${currentPage == totalPages - 1 || totalPages == 0 ? 'disabled' : ''}">&gt;|</a>
              </div>
          </div>
      </div>
  </div>
  
</div>

<jsp:include page="/views/layout/admin_footer.jsp" />
