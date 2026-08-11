<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Administration Tool</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- FontAwesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <style>
        body { background-color: #f4f6f9; font-family: 'Inter', sans-serif; }
        .admin-navbar { 
            background: linear-gradient(to bottom, #2b2b2b, #000000); 
            border-radius: 8px; 
            padding: 10px 20px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.5);
            margin-bottom: 30px;
        }
        .admin-brand { 
            color: #ffd700 !important; 
            font-weight: 800; 
            font-size: 22px; 
            text-shadow: 1px 1px 2px rgba(255,255,255,0.2); 
        }
        .admin-link { 
            color: #8fce00 !important; 
            font-weight: bold; 
            text-transform: uppercase;
            margin-left: 15px;
        }
        .admin-link:hover { color: #a4e600 !important; text-shadow: 0 0 5px rgba(143,206,0,0.5); }
        .nav-tabs .nav-link { color: #dc3545; font-weight: bold; border: 1px solid #dee2e6; }
        .nav-tabs .nav-link.active { color: #dc3545; font-weight: bold; border-color: #dee2e6 #dee2e6 #fff; }
    </style>
</head>
<body>
<div class="container mt-4">
    <!-- Thanh Menu Admin -->
    <nav class="navbar navbar-expand-lg admin-navbar">
        <a class="navbar-brand admin-brand" href="${pageContext.request.contextPath}/admin/home">
            CÔNG CỤ QUẢN TRỊ
        </a>
        <button class="navbar-toggler bg-light" type="button" data-bs-toggle="collapse" data-bs-target="#adminNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        
        <div class="collapse navbar-collapse justify-content-end" id="adminNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link admin-link" href="${pageContext.request.contextPath}/home"><i class="fa-solid fa-house"></i> TRANG CHỦ</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link admin-link" href="${pageContext.request.contextPath}/admin/video"><i class="fa-solid fa-video"></i> VIDEO</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link admin-link" href="${pageContext.request.contextPath}/admin/category"><i class="fa-solid fa-tags"></i> DANH MỤC</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link admin-link" href="${pageContext.request.contextPath}/admin/user"><i class="fa-solid fa-users"></i> NGƯỜI DÙNG</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link admin-link" href="${pageContext.request.contextPath}/admin/report"><i class="fa-solid fa-chart-pie"></i> BÁO CÁO</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link admin-link" href="${pageContext.request.contextPath}/admin/channel-request"><i class="fa-solid fa-address-card"></i> DUYỆT KÊNH</a>
                </li>
                <li class="nav-item ms-3 border-start ps-3">
                    <a class="nav-link text-white fw-bold" href="${pageContext.request.contextPath}/logout"><i class="fa-solid fa-right-from-bracket"></i> Đăng xuất</a>
                </li>
            </ul>
        </div>
    </nav>
