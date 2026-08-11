<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>

<jsp:include page="/views/layout/admin_header.jsp" />

<h4 class="fw-bold mb-4" style="color: #495057;">BÁO CÁO - THỐNG KÊ</h4>

<!-- Tabs Giao diện -->
<ul class="nav nav-tabs mb-4" id="reportTabs" role="tablist">
  <li class="nav-item" role="presentation">
    <button class="nav-link ${activeTab == 'favorites' ? 'active' : ''}" 
            id="favorites-tab" data-bs-toggle="tab" data-bs-target="#favorites" type="button" role="tab"
            onclick="history.replaceState(null, null, '?tab=favorites')">
        LƯỢT THÍCH
    </button>
  </li>
  <li class="nav-item" role="presentation">
    <button class="nav-link ${activeTab == 'favUsers' ? 'active' : ''}" 
            id="favUsers-tab" data-bs-toggle="tab" data-bs-target="#favUsers" type="button" role="tab"
            onclick="history.replaceState(null, null, '?tab=favUsers')">
        NGƯỜI DÙNG YÊU THÍCH
    </button>
  </li>
  <li class="nav-item" role="presentation">
    <button class="nav-link ${activeTab == 'sharedFriends' ? 'active' : ''}" 
            id="sharedFriends-tab" data-bs-toggle="tab" data-bs-target="#sharedFriends" type="button" role="tab"
            onclick="history.replaceState(null, null, '?tab=sharedFriends')">
        LỊCH SỬ CHIA SẺ
    </button>
  </li>
</ul>

<div class="tab-content" id="reportTabsContent">
  
  <!-- TAB 1: FAVORITES -->
  <div class="tab-pane fade ${activeTab == 'favorites' ? 'show active' : ''}" id="favorites" role="tabpanel">
      <div class="card border border-warning shadow-sm">
          <div class="card-body p-0">
              <table class="table table-bordered table-striped m-0">
                  <thead style="background-color: #fffaf0;">
                      <tr>
                          <th>Tiêu đề Video</th>
                          <th>Số lượt thích</th>
                          <th>Ngày thích mới nhất</th>
                          <th>Ngày thích cũ nhất</th>
                      </tr>
                  </thead>
                  <tbody>
                      <c:forEach items="${favoritesData}" var="row">
                          <tr>
                              <td>${row[0]}</td>
                              <td>${row[1]}</td>
                              <td><fmt:formatDate value="${row[2]}" pattern="dd/MM/yyyy" /></td>
                              <td><fmt:formatDate value="${row[3]}" pattern="dd/MM/yyyy" /></td>
                          </tr>
                      </c:forEach>
                      <c:if test="${empty favoritesData}">
                          <tr><td colspan="4" class="text-center">Không có dữ liệu</td></tr>
                      </c:if>
                  </tbody>
              </table>
          </div>
      </div>
  </div>
  
  <!-- TAB 2: FAVORITE USERS -->
  <div class="tab-pane fade ${activeTab == 'favUsers' ? 'show active' : ''}" id="favUsers" role="tabpanel">
      <div class="card border border-warning shadow-sm">
          <div class="card-body bg-light py-2 border-bottom border-warning">
              <form action="${pageContext.request.contextPath}/admin/report" method="GET" class="d-flex align-items-center">
                  <input type="hidden" name="tab" value="favUsers">
                  <label class="fw-bold me-3">Chọn Video:</label>
                  <select name="favVid" class="form-select border-warning w-50" onchange="this.form.submit()">
                      <c:forEach items="${favVideos}" var="v">
                          <option value="${v.id}" ${v.id == selectedFavVid ? 'selected' : ''}>${v.title}</option>
                      </c:forEach>
                  </select>
              </form>
          </div>
          <div class="card-body p-0">
              <table class="table table-bordered table-striped m-0">
                  <thead style="background-color: #fffaf0;">
                      <tr>
                          <th>Tên đăng nhập</th>
                          <th>Họ và tên</th>
                          <th>Email</th>
                          <th>Ngày yêu thích</th>
                      </tr>
                  </thead>
                  <tbody>
                      <c:forEach items="${favUsersData}" var="row">
                          <tr>
                              <td>${row[0]}</td>
                              <td>${row[1]}</td>
                              <td>${row[2]}</td>
                              <td><fmt:formatDate value="${row[3]}" pattern="dd/MM/yyyy" /></td>
                          </tr>
                      </c:forEach>
                      <c:if test="${empty favUsersData}">
                          <tr><td colspan="4" class="text-center">Chưa có người dùng nào thích video này</td></tr>
                      </c:if>
                  </tbody>
              </table>
          </div>
      </div>
  </div>
  
  <!-- TAB 3: SHARED FRIENDS -->
  <div class="tab-pane fade ${activeTab == 'sharedFriends' ? 'show active' : ''}" id="sharedFriends" role="tabpanel">
      <div class="card border border-warning shadow-sm">
          <div class="card-body bg-light py-2 border-bottom border-warning">
              <form action="${pageContext.request.contextPath}/admin/report" method="GET" class="d-flex align-items-center">
                  <input type="hidden" name="tab" value="sharedFriends">
                  <label class="fw-bold me-3">Chọn Video:</label>
                  <select name="shareVid" class="form-select border-warning w-50" onchange="this.form.submit()">
                      <c:forEach items="${shareVideos}" var="v">
                          <option value="${v.id}" ${v.id == selectedShareVid ? 'selected' : ''}>${v.title}</option>
                      </c:forEach>
                  </select>
              </form>
          </div>
          <div class="card-body p-0">
              <table class="table table-bordered table-striped m-0">
                  <thead style="background-color: #fffaf0;">
                      <tr>
                          <th>Người gửi</th>
                          <th>Email người gửi</th>
                          <th>Email người nhận</th>
                          <th>Ngày chia sẻ</th>
                      </tr>
                  </thead>
                  <tbody>
                      <c:forEach items="${sharedFriendsData}" var="row">
                          <tr>
                              <td>${row[0]}</td>
                              <td>${row[1]}</td>
                              <td>${row[2]}</td>
                              <td><fmt:formatDate value="${row[3]}" pattern="dd/MM/yyyy" /></td>
                          </tr>
                      </c:forEach>
                      <c:if test="${empty sharedFriendsData}">
                          <tr><td colspan="4" class="text-center">Video này chưa được chia sẻ cho ai</td></tr>
                      </c:if>
                  </tbody>
              </table>
          </div>
      </div>
  </div>
  
</div>

<jsp:include page="/views/layout/admin_footer.jsp" />
