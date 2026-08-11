<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Online Entertainment</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- FontAwesome (Icons) -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <style>
        body { background-color: #f4f6f9; color: #333; font-family: 'Inter', sans-serif; }
        .navbar-custom { 
            background: linear-gradient(to bottom, #ffdb4d, #ffae00) !important; 
            border-radius: 8px; 
            box-shadow: 2px 4px 8px rgba(0,0,0,0.2); 
        }
        .navbar-brand { color: #d32f2f !important; font-weight: 800; text-shadow: 1px 1px 2px rgba(255,255,255,0.5); }
        .nav-link.custom-link { color: #1976d2 !important; font-weight: 700; transition: color 0.2s; }
        .nav-link.custom-link:hover { color: #0d47a1 !important; }
        .card { border: none; border-radius: 12px; overflow: hidden; transition: transform 0.3s; box-shadow: 0 4px 6px rgba(0,0,0,0.1); background-color: #fff; }
        .card:hover { transform: translateY(-5px); box-shadow: 0 10px 20px rgba(0,0,0,0.15); }
        .card-title { color: #2c3e50; font-weight: 700; }
        .text-muted-custom { color: #7f8c8d; }
        .btn-custom { border-radius: 8px; font-weight: 600; }
    </style>
</head>
<body>

<!-- Thanh Menu (Navbar) -->
<nav class="navbar navbar-expand-lg navbar-light mb-4 navbar-custom">
  <div class="container">
    <!-- Tên Logo -->
    <a class="navbar-brand fs-4" href="${pageContext.request.contextPath}/home">
        ONLINE ENTERTAINMENT
    </a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
      <span class="navbar-toggler-icon"></span>
    </button>
    
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav me-auto ms-4 gap-3">
        <!-- CHỈ HIỂN THỊ KHI ĐÃ ĐĂNG NHẬP -->
        <c:if test="${not empty sessionScope.user}">
            <li class="nav-item">
                <a class="nav-link custom-link" href="${pageContext.request.contextPath}/favorites">
                    <i class="fas fa-heart me-1 text-danger"></i> VIDEO YÊU THÍCH
                </a>
            </li>
        </c:if>
      </ul>
      <ul class="navbar-nav">
        <!-- TÀI KHOẢN Dropdown -->
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle custom-link" href="#" id="accountDropdown" role="button" data-bs-toggle="dropdown">
            TÀI KHOẢN
          </a>
          <ul class="dropdown-menu dropdown-menu-end">
            <!-- JSTL Kiểm tra Session (Biến user) -->
            <c:choose>
              <c:when test="${not empty sessionScope.user}">
                  <!-- Nếu ĐÃ đăng nhập -->
                  <li>
                      <span class="dropdown-item fw-bold text-success text-wrap" style="cursor: default; width: 250px;">
                          Xin chào, ${sessionScope.user.fullname != null ? sessionScope.user.fullname : (sessionScope.user.admin ? 'Quản trị viên' : 'Bạn')}
                      </span>
                  </li>
                  <li><hr class="dropdown-divider"></li>
                  <c:if test="${sessionScope.user.admin}">
                      <li><a class="dropdown-item fw-bold text-primary" href="${pageContext.request.contextPath}/admin/video">Trang Quản Trị</a></li>
                      <li><hr class="dropdown-divider"></li>
                  </c:if>
                  <li><a class="dropdown-item" href="${pageContext.request.contextPath}/change-password">Đổi mật khẩu</a></li>
                  <li><a class="dropdown-item" href="${pageContext.request.contextPath}/edit-profile">Cập nhật tài khoản</a></li>
                  <li><hr class="dropdown-divider"></li>
                  <li><a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/logout">Đăng xuất</a></li>
              </c:when>
              <c:otherwise>
                  <!-- Nếu CHƯA đăng nhập -->
                  <li><a class="dropdown-item" href="${pageContext.request.contextPath}/login">Đăng nhập</a></li>
                  <li><a class="dropdown-item" href="${pageContext.request.contextPath}/register">Đăng ký</a></li>
              </c:otherwise>
            </c:choose>
          </ul>
        </li>
      </ul>
    </div>
  </div>
</nav>
<!-- Phần ruột (container) sẽ để các file khác nhúng vào -->
<div class="container">
