package com.fpoly.oe.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fpoly.oe.dao.VideoDAO;
import com.fpoly.oe.entities.Video;

@WebServlet({ "/admin/video", "/admin/video/create", "/admin/video/update", "/admin/video/delete", "/admin/video/edit",
        "/admin/video/approve", "/admin/video/reject" })
@jakarta.servlet.annotation.MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024
        * 100, maxRequestSize = 1024 * 1024 * 110)
public class AdminVideoController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        VideoDAO dao = new VideoDAO();
        Video formVideo = new Video();

        String activeTab = "videoList";

        if (uri.contains("/edit")) {
            String id = req.getParameter("id");
            if (id != null) {
                formVideo = dao.findById(id);
                activeTab = "videoEdition";
            }
        }

        loadPagination(req, dao);

        req.setAttribute("formVideo", formVideo);
        req.setAttribute("activeTab", activeTab);

        req.getRequestDispatcher("/views/admin/video.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        VideoDAO dao = new VideoDAO();

        try {
            Video video = new Video();

            if (uri.contains("/approve") || uri.contains("/reject")) {
                String videoId = req.getParameter("id");
                Video targetVideo = dao.findById(videoId);
                if (targetVideo != null) {
                    targetVideo.setActive(uri.contains("/approve"));
                    dao.update(targetVideo);
                    req.setAttribute("message", uri.contains("/approve") ? "Đã duyệt video!" : "Đã từ chối video!");
                }
                req.setAttribute("activeTab", "videoList");
            } else {
                video.setId(req.getParameter("id"));
                video.setTitle(req.getParameter("title"));

                String viewsStr = req.getParameter("views");
                if (viewsStr != null && !viewsStr.isEmpty()) {
                    video.setViews(Integer.parseInt(viewsStr));
                } else {
                    video.setViews(0);
                }

                video.setDescription(req.getParameter("description"));
                String activeStr = req.getParameter("active");
                video.setActive("true".equals(activeStr));

                String uploadPath = req.getServletContext().getRealPath("") + java.io.File.separator + "uploads";

                if (uri.contains("/create")) {
                    jakarta.servlet.http.Part videoPart = req.getPart("video");
                    if (videoPart != null && videoPart.getSize() > 0) {
                        String originalFileName = java.nio.file.Paths.get(videoPart.getSubmittedFileName())
                                .getFileName().toString();
                        String ext = "";
                        int i = originalFileName.lastIndexOf('.');
                        if (i >= 0)
                            ext = originalFileName.substring(i);
                        String newVideoId = java.util.UUID.randomUUID().toString().substring(0, 8) + ext;
                        if (ext.isEmpty())
                            newVideoId += ".mp4";

                        java.io.File uploadDir = new java.io.File(uploadPath);
                        if (!uploadDir.exists())
                            uploadDir.mkdir();
                        videoPart.write(uploadPath + java.io.File.separator + newVideoId);
                        video.setId(newVideoId);
                    }
                } else {
                    jakarta.servlet.http.Part videoPart = req.getPart("video");
                    if (videoPart != null && videoPart.getSize() > 0 && video.getId() != null) {
                        java.io.File uploadDir = new java.io.File(uploadPath);
                        if (!uploadDir.exists())
                            uploadDir.mkdir();
                        videoPart.write(uploadPath + java.io.File.separator + video.getId());
                    }
                }

                jakarta.servlet.http.Part posterPart = null;
                try {
                    posterPart = req.getPart("poster");
                } catch (Exception e) {
                }

                String fileName = null;
                if (posterPart != null && posterPart.getSize() > 0) {
                    fileName = java.nio.file.Paths.get(posterPart.getSubmittedFileName()).getFileName().toString();
                    java.io.File uploadDir = new java.io.File(uploadPath);
                    if (!uploadDir.exists())
                        uploadDir.mkdir();
                    posterPart.write(uploadPath + java.io.File.separator + fileName);
                    video.setPoster(fileName);
                } else if (uri.contains("/create")) {
                    video.setPoster(video.getId());
                } else {
                    Video existing = dao.findById(video.getId());
                    if (existing != null)
                        video.setPoster(existing.getPoster());
                }

                com.fpoly.oe.entities.User adminUser = (com.fpoly.oe.entities.User) req.getSession()
                        .getAttribute("user");
                if (uri.contains("/create")) {
                    video.setUser(adminUser);
                } else if (uri.contains("/update")) {
                    Video existing = dao.findById(video.getId());
                    if (existing != null) {
                        video.setUser(existing.getUser());
                        video.setCategory(existing.getCategory());
                        video.setUploadDate(existing.getUploadDate());
                    }
                }

                if (!uri.contains("/delete")) {
                    String errorMsg = null;
                    jakarta.servlet.http.Part valVideoPart = req.getPart("video");
                    jakarta.servlet.http.Part valPosterPart = null;
                    try {
                        valPosterPart = req.getPart("poster");
                    } catch (Exception e) {
                    }

                    if (uri.contains("/create") && (valVideoPart == null || valVideoPart.getSize() == 0)) {
                        errorMsg = "Vui lòng chọn file video!";
                    } else if (video.getTitle() == null || video.getTitle().trim().isEmpty()) {
                        errorMsg = "Vui lòng nhập tiêu đề video!";
                    } else if (uri.contains("/create") && (valPosterPart == null || valPosterPart.getSize() == 0)) {
                        errorMsg = "Vui lòng chọn ảnh poster!";
                    }

                    if (errorMsg != null) {
                        if (uri.contains("/create")) {
                            video.setId(null);
                        }
                        req.setAttribute("error", errorMsg);
                        req.setAttribute("activeTab", "videoEdition");
                        req.setAttribute("formVideo", video);
                        loadPagination(req, dao);
                        req.getRequestDispatcher("/views/admin/video.jsp").forward(req, resp);
                        return;
                    }
                }

                String activeTab = "videoEdition";

                if (uri.contains("/create")) {
                    dao.create(video);
                    req.setAttribute("message", "Thêm video thành công!");
                    video = new Video();
                    activeTab = "videoList";
                } else if (uri.contains("/update")) {
                    dao.update(video);
                    req.setAttribute("message", "Cập nhật video thành công!");
                    video = new Video();
                    activeTab = "videoList";
                } else if (uri.contains("/delete")) {
                    dao.delete(video.getId());
                    req.setAttribute("message", "Xóa video thành công!");
                    video = new Video();
                    activeTab = "videoList";
                }

                req.setAttribute("formVideo", video);
                req.setAttribute("activeTab", activeTab);
            }

        } catch (Exception e) {
            e.printStackTrace();
            String errorMsg = "Lỗi thao tác dữ liệu: " + e.getMessage();
            if (uri.contains("/delete")) {
                errorMsg = "Không thể xóa video này vì đang có dữ liệu liên quan (Lượt thích, Chia sẻ...). Vui lòng vô hiệu hóa thay vì xóa!";
            }
            req.setAttribute("error", errorMsg);
            req.setAttribute("activeTab", "videoEdition");
        }

        loadPagination(req, dao);
        req.getRequestDispatcher("/views/admin/video.jsp").forward(req, resp);
    }

    private void loadPagination(HttpServletRequest req, VideoDAO dao) {
        int page = 0;
        String pageStr = req.getParameter("page");
        if (pageStr != null) {
            try {
                page = Integer.parseInt(pageStr);
            } catch (Exception e) {
            }
        }
        int pageSize = 10;
        long totalVideos = dao.countAllVideos();
        int totalPages = (int) Math.ceil((double) totalVideos / pageSize);
        if (page < 0)
            page = 0;
        if (page >= totalPages && totalPages > 0)
            page = totalPages - 1;

        List<Video> videos = dao.findAll(page, pageSize);
        req.setAttribute("videos", videos);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
    }
}
