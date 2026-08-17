<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Online Entertainment</title>
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap');
        body { 
            background-color: #f9f9f9; 
            color: #0f0f0f; 
            font-family: 'Roboto', Arial, sans-serif; 
            -webkit-font-smoothing: antialiased;
        }
        
        /* YouTube-like Navbar */
        .navbar-custom { 
            background-color: #ffffff !important; 
            padding: 8px 16px;
            margin-bottom: 24px !important;
            margin-top: 0;
            border-radius: 0;
            position: sticky;
            top: 0;
            z-index: 1000;
        }
        .navbar-brand { 
            color: #0f0f0f !important; 
            font-weight: 700; 
            letter-spacing: -0.5px;
            display: flex;
            align-items: center;
            gap: 5px;
            font-size: 20px;
        }
        .navbar-brand::before {
            content: "\f167";
            font-family: "Font Awesome 6 Brands";
            color: #ff0000;
            font-size: 24px;
        }
        
        .nav-link.custom-link { 
            color: #0f0f0f !important; 
            font-weight: 500; 
            font-size: 14px;
            padding: 8px 15px !important;
            border-radius: 18px;
            background-color: #f2f2f2;
            transition: background-color 0.2s;
        }
        .nav-link.custom-link:hover { 
            background-color: #e5e5e5;
        }
        
        /* YouTube-like Cards */
        .card { 
            border: none !important; 
            background-color: transparent !important;
            border-radius: 0 !important;
            box-shadow: none !important;
        }
        .card:hover {
            transform: none !important;
            box-shadow: none !important;
        }
        .card-img-top {
            border-radius: 12px !important;
            transition: border-radius 0.2s ease;
        }
        .card:hover .card-img-top {
            border-radius: 0 !important;
            transform: none !important;
        }
        
        .card-title { color: #0f0f0f; font-weight: 500; line-height: 1.4; font-size: 16px; margin-bottom: 4px !important; }
        .text-muted-custom { color: #606060; }
        
        /* Utilities & Buttons */
        .btn { 
            border-radius: 18px !important; 
            font-weight: 500; 
            text-transform: none;
            font-size: 14px;
            padding: 6px 16px;
            box-shadow: none !important;
        }
        .btn:hover {
            transform: none !important;
            box-shadow: none !important;
        }
        .btn-dark {
            background-color: #0f0f0f !important;
            color: #fff !important;
            border: none;
        }
        .btn-light {
            background-color: #f2f2f2 !important;
            color: #0f0f0f !important;
            border: none !important;
        }
        .btn-light:hover { background-color: #e5e5e5 !important; }
        
        /* Modern Inputs */
        .form-control {
            border-radius: 18px;
            border: 1px solid #ccc;
            padding: 8px 16px;
            box-shadow: inset 0 1px 2px #eee;
        }
        .form-control:focus {
            border-color: #1c62b9;
            box-shadow: inset 0 1px 2px #eee;
        }
        
        /* Soft Dropdown */
        .dropdown-menu {
            border: none;
            border-radius: 12px;
            box-shadow: 0 4px 32px 0 rgba(0,0,0,0.1);
            padding: 8px 0;
            animation: none;
        }
        .dropdown-item {
            padding: 8px 16px;
            font-weight: 400;
            font-size: 14px;
            color: #0f0f0f;
            border-radius: 0;
        }
        .dropdown-item:hover {
            background-color: #f2f2f2;
            color: #0f0f0f;
            transform: none;
        }
        
        /* Override generic inline styles for index.jsp */
        .custom-image-wrapper {
            overflow: hidden;
            border-radius: 12px;
            padding: 0 !important;
        }
        .card-body {
            padding: 12px 0 0 0 !important;
            background-color: transparent !important;
        }
        .card-footer {
            padding: 4px 0 0 0 !important;
            background-color: transparent !important;
        }
    </style>
</head>
<body>


<nav class="navbar navbar-expand-lg navbar-light mb-4 navbar-custom">
  <div class="container">
    
    <a class="navbar-brand fs-4" href="${pageContext.request.contextPath}/home">
        ONLINE ENTERTAINMENT
    </a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
      <span class="navbar-toggler-icon"></span>
    </button>
    
    <div class="collapse navbar-collapse d-flex justify-content-between align-items-center" id="navbarNav">
      <ul class="navbar-nav ms-4 gap-3">
        
        <c:if test="${not empty sessionScope.user}">
            <li class="nav-item">
                <a class="nav-link custom-link" href="${pageContext.request.contextPath}/favorites">
                    <i class="fas fa-heart me-1 text-danger"></i> VIDEO YÊU THÍCH
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link custom-link" href="${pageContext.request.contextPath}/following">
                    <i class="fa-solid fa-users text-primary me-1"></i> KÊNH ĐĂNG KÝ
                </a>
            </li>
        </c:if>
      </ul>
      
      
      <form action="${pageContext.request.contextPath}/home" method="GET" class="d-flex mx-auto" style="max-width: 600px; width: 100%; padding: 0 20px;">
          <div class="input-group" style="height: 40px;">
              <input type="text" name="search" class="form-control shadow-none" placeholder="Tìm kiếm" value="${searchKeyword != null ? searchKeyword : ''}" style="border-radius: 20px 0 0 20px; border: 1px solid #ccc; font-size: 16px; padding-left: 16px;">
              <button class="btn btn-outline-secondary" type="submit" style="border-radius: 0 20px 20px 0; border: 1px solid #ccc; border-left: none; background-color: #f8f8f8; padding: 0 20px; box-shadow: none !important;">
                  <i class="fa-solid fa-magnifying-glass" style="color: #0f0f0f;"></i>
              </button>
          </div>
      </form>
      <ul class="navbar-nav">
        
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle custom-link" href="#" id="accountDropdown" role="button" data-bs-toggle="dropdown">
            TÀI KHOẢN
          </a>
          <ul class="dropdown-menu dropdown-menu-end">
            
            <c:choose>
              <c:when test="${not empty sessionScope.user}">
                  
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

<div class="container">

