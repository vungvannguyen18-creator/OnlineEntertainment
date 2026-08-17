<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Administration Tool</title>
    
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
        
        /* YouTube Studio-like Admin Navbar */
        .admin-navbar { 
            background-color: #ffffff !important;
            padding: 8px 16px;
            box-shadow: 0 1px 2px rgba(0,0,0,0.1);
            margin-bottom: 24px;
            border: none;
            border-bottom: 1px solid #e5e5e5;
            border-radius: 0;
            position: sticky;
            top: 0;
            z-index: 1000;
        }
        .admin-navbar:hover {
            box-shadow: 0 1px 2px rgba(0,0,0,0.1);
        }
        .admin-brand { 
            color: #0f0f0f !important; 
            font-weight: 700; 
            font-size: 20px; 
            letter-spacing: -0.5px;
            text-shadow: none; 
            display: flex;
            align-items: center;
            gap: 6px;
        }
        .admin-brand::before {
            content: "\f167";
            font-family: "Font Awesome 6 Brands";
            color: #ff0000;
            font-size: 24px;
        }
        .admin-brand:hover {
            transform: none;
        }
        .admin-link { 
            color: #606060 !important; 
            font-weight: 500; 
            text-transform: none;
            font-size: 14px;
            margin-left: 8px;
            padding: 8px 16px !important;
            border-radius: 18px;
            transition: all 0.2s;
        }
        .admin-link:hover { 
            color: #0f0f0f !important; 
            text-shadow: none; 
            background: #f2f2f2;
            transform: none;
        }
        
        /* Admin Dynamic Cards */
        .card { 
            border: 1px solid #e5e5e5 !important; 
            border-radius: 8px !important; 
            box-shadow: none !important; 
            background-color: #fff; 
        }
        .card:hover { 
            transform: none; 
            box-shadow: none !important; 
        }
        
        /* Admin Tabs & Form */
        .nav-tabs {
            border-bottom: 1px solid #e5e5e5;
        }
        .nav-tabs .nav-link { 
            color: #606060; 
            font-weight: 500; 
            border: none;
            padding: 12px 24px;
            border-radius: 0;
            transition: color 0.2s;
            margin-bottom: -1px;
        }
        .nav-tabs .nav-link:hover {
            background-color: transparent;
            color: #0f0f0f;
        }
        .nav-tabs .nav-link.active { 
            color: #0f0f0f; 
            font-weight: 500; 
            border-bottom: 3px solid #0f0f0f;
            background-color: transparent;
        }
        
        /* Admin Buttons */
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
        .btn-primary { background-color: #0f0f0f; color: #fff; border: none; }
        .btn-primary:hover { background-color: #272727; color: #fff; }
        .btn-danger { background-color: #cc0000; color: #fff; border: none; }
        .btn-danger:hover { background-color: #990000; color: #fff; }
        
        /* Admin Tables */
        .table {
            border-radius: 0;
            overflow: visible;
            box-shadow: none;
            margin-bottom: 0;
        }
        .table thead th {
            background-color: #ffffff;
            color: #606060;
            border-bottom: 1px solid #e5e5e5;
            padding: 12px 16px;
            font-weight: 500;
            font-size: 13px;
        }
        .table tbody td {
            vertical-align: middle;
            padding: 12px 16px;
            border-bottom: 1px solid #e5e5e5;
            font-size: 14px;
            color: #0f0f0f;
        }
        .table-hover tbody tr:hover {
            background-color: #f9f9f9;
            transform: none;
            transition: none;
        }
    </style>
</head>
<body>
<div class="container mt-4">
    
    <nav class="navbar navbar-expand-lg admin-navbar">
        <a class="navbar-brand admin-brand" href="${pageContext.request.contextPath}/admin/video">
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

