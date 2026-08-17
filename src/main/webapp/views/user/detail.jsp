<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="/views/layout/header.jsp" />
<div class="row mt-4 mb-5">
    
    <div class="col-lg-8">
        <div class="card">
            
            <div class="card-body p-0">
                <div class="ratio ratio-16x9 rounded-4 overflow-hidden bg-dark">
                    <c:choose>
                        <c:when test="${fn:contains(video.id, '.')}">
                            <video controls autoplay controlsList="nodownload noplaybackrate" disablePictureInPicture style="width: 100%; height: 100%; object-fit: contain;">
                                <source src="${pageContext.request.contextPath}/uploads/${video.id}" type="video/mp4">
                                Trình duyệt của bạn không hỗ trợ thẻ video.
                            </video>
                        </c:when>
                        <c:otherwise>
                            <iframe src="https://www.youtube.com/embed/${video.id}?autoplay=1" allowfullscreen allow="autoplay" style="border: none;"></iframe>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            
            
            <div class="card-body px-3 pt-3 pb-4">
                
                <h5 class="fw-bold text-dark mb-3" style="font-size: 1.25rem;">${video.title}</h5>
                
                
                <div class="d-flex flex-wrap justify-content-between align-items-center">
                    
                    <div class="d-flex align-items-center gap-3 mb-2 mb-md-0">
                        <c:if test="${not empty video.user}">
                            <a href="${pageContext.request.contextPath}/channel?id=${video.user.id}" class="d-flex align-items-center text-decoration-none">
                                <i class="fa-solid fa-user-circle fa-2x text-secondary me-2"></i>
                                <div class="d-flex flex-column lh-1">
                                    <span class="fw-bold text-dark" style="font-size: 16px;">${video.user.fullname}</span>
                                    <span class="text-muted mt-1" style="font-size: 12px;">${followerCount} người theo dõi</span>
                                </div>
                            </a>
                            <c:if test="${not empty sessionScope.user && sessionScope.user.id != video.user.id}">
                                <c:choose>
                                    <c:when test="${isFollowing}">
                                        <a href="${pageContext.request.contextPath}/follow?channelId=${video.user.id}" class="btn btn-sm rounded-pill fw-bold px-3 ms-2" style="background-color: #f2f2f2; color: #0f0f0f; border: 1px solid #d9d9d9;">
                                            <i class="fa-regular fa-bell"></i> Đang theo dõi
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="${pageContext.request.contextPath}/follow?channelId=${video.user.id}" class="btn btn-sm rounded-pill fw-bold px-3 ms-2" style="background-color: #0f0f0f; color: #fff;">
                                            Theo dõi
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </c:if>
                    </div>
                    
                    
                    <div class="d-flex align-items-center gap-2">
                        <div class="btn-group bg-light rounded-pill" style="background-color: #f2f2f2 !important;">
                            <a href="${pageContext.request.contextPath}/like?id=${video.id}" class="btn btn-sm rounded-pill fw-bold d-flex align-items-center gap-1 px-3" style="border: none; color: #0f0f0f;">
                                <i class="fa-regular fa-thumbs-up"></i> ${likeCount}
                            </a>
                        </div>
                        <a href="${pageContext.request.contextPath}/share?id=${video.id}" class="btn btn-sm rounded-pill fw-bold d-flex align-items-center gap-2 px-3" style="background-color: #f2f2f2; color: #0f0f0f; border: none;">
                            <i class="fa-solid fa-share"></i> Chia sẻ
                        </a>
                    </div>
                </div>
                
                
                <div class="mt-3 p-3 rounded-4" style="background-color: #f2f2f2;">
                    <div class="fw-bold mb-1" style="font-size: 14px;">${video.views} lượt xem</div>
                    <p class="card-text mb-0" style="font-size: 14px; white-space: pre-wrap; color: #0f0f0f;">${video.description}</p>
                </div>
                
                
                <div class="mt-5">
                    <h5 class="fw-bold mb-4" style="font-size: 18px;">${fn:length(comments)} Bình luận</h5>
                    
                    
                    <c:choose>
                        <c:when test="${empty sessionScope.user}">
                            <div class="alert alert-light border-0 px-0 d-flex align-items-center gap-2">
                                <i class="fa-solid fa-circle-info text-secondary"></i>
                                <span>Vui lòng <a href="${pageContext.request.contextPath}/login" class="fw-bold text-decoration-none">Đăng nhập</a> để bình luận.</span>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <form action="${pageContext.request.contextPath}/comment" method="POST" class="d-flex gap-3 mb-5">
                                <i class="fa-solid fa-user-circle fa-2x text-secondary mt-1"></i>
                                <div class="flex-grow-1">
                                    <input type="hidden" name="videoId" value="${video.id}">
                                    <textarea class="form-control border-0 border-bottom border-dark rounded-0 px-0 shadow-none" name="content" rows="1" placeholder="Viết bình luận..." style="background-color: transparent; resize: none; overflow: hidden;" oninput="this.style.height = ''; this.style.height = this.scrollHeight + 'px'"></textarea>
                                    <div class="d-flex justify-content-end mt-2">
                                        <button type="submit" class="btn btn-sm rounded-pill fw-bold px-3" style="background-color: #f2f2f2; color: #0f0f0f; border: none;">Bình luận</button>
                                    </div>
                                </div>
                            </form>
                        </c:otherwise>
                    </c:choose>
                    
                    
                    <div>
                        <c:choose>
                            <c:when test="${empty comments}">
                                <div class="text-center text-muted my-4">Chưa có bình luận nào. Hãy là người đầu tiên bình luận!</div>
                            </c:when>
                            <c:otherwise>
                                <jsp:useBean id="now" class="java.util.Date" />
                                <c:forEach items="${comments}" var="c">
                                    <c:if test="${empty c.parentId}">
                                        <div class="d-flex gap-3 mb-4">
                                            <i class="fa-solid fa-user-circle fa-2x text-secondary mt-1"></i>
                                            <div class="flex-grow-1">
                                                <div class="fw-bold" style="font-size: 13px;">
                                                    @${c.user.id} 
                                                    <span class="text-muted ms-1 fw-normal" style="font-size: 12px;">
                                                        <c:set var="diffMs" value="${now.time - c.commentDate.time}" />
                                                        <c:set var="diffSecs" value="${diffMs / 1000}" />
                                                        <c:choose>
                                                            <c:when test="${diffSecs < 60}">vừa xong</c:when>
                                                            <c:when test="${diffSecs < 3600}">${fn:substringBefore(diffSecs / 60, '.')} phút trước</c:when>
                                                            <c:when test="${diffSecs < 86400}">${fn:substringBefore(diffSecs / 3600, '.')} giờ trước</c:when>
                                                            <c:otherwise>${fn:substringBefore(diffSecs / 86400, '.')} ngày trước</c:otherwise>
                                                        </c:choose>
                                                    </span>
                                                </div>
                                                <p class="mb-0 text-dark mt-1" style="font-size: 14px; white-space: pre-wrap; line-height: 1.5;">${c.content}</p>
                                                
                                                
                                                <div class="d-flex align-items-center mt-2 gap-3" style="font-size: 12px; font-weight: 500;">
                                                    <a href="javascript:void(0)" class="text-decoration-none text-dark btn-like-comment d-flex align-items-center gap-1">
                                                        <i class="fa-regular fa-thumbs-up" style="font-size: 14px;"></i> <span class="like-count">0</span>
                                                    </a>
                                                    <a href="javascript:void(0)" class="text-decoration-none text-dark" onclick="toggleReplyForm('${c.id}')">
                                                        Phản hồi
                                                    </a>
                                                    <c:if test="${not empty sessionScope.user and (sessionScope.user.id == c.user.id or sessionScope.user.admin)}">
                                                        <a href="${pageContext.request.contextPath}/comment?action=delete&id=${c.id}&videoId=${video.id}" class="text-decoration-none text-danger" onclick="return confirm('Bạn có chắc muốn xóa bình luận này cùng với tất cả phản hồi của nó?');">
                                                            Xóa
                                                        </a>
                                                    </c:if>
                                                </div>
                                                
                                                
                                                <div id="reply-form-${c.id}" class="d-none mt-3">
                                                    <c:choose>
                                                        <c:when test="${empty sessionScope.user}">
                                                            <div class="text-muted" style="font-size: 13px;">Vui lòng <a href="${pageContext.request.contextPath}/login" class="text-decoration-none">Đăng nhập</a> để phản hồi.</div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <form action="${pageContext.request.contextPath}/comment" method="POST" class="d-flex gap-2">
                                                                <i class="fa-solid fa-user-circle text-secondary mt-1" style="font-size: 24px;"></i>
                                                                <div class="flex-grow-1">
                                                                    <input type="hidden" name="videoId" value="${video.id}">
                                                                    <input type="hidden" name="parentId" value="${c.id}">
                                                                    <textarea class="form-control border-0 border-bottom border-dark rounded-0 px-0 shadow-none" name="content" rows="1" style="background-color: transparent; resize: none; overflow: hidden; font-size: 14px;" oninput="this.style.height = ''; this.style.height = this.scrollHeight + 'px'">@${c.user.id} </textarea>
                                                                    <div class="d-flex justify-content-end mt-2 gap-2">
                                                                        <button type="button" class="btn btn-sm fw-bold px-3" style="color: #0f0f0f; border: none; background: transparent;" onclick="toggleReplyForm('${c.id}')">Hủy</button>
                                                                        <button type="submit" class="btn btn-sm rounded-pill fw-bold px-3" style="background-color: #065fd4; color: #fff; border: none;">Phản hồi</button>
                                                                    </div>
                                                                </div>
                                                            </form>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                                
                                                
                                                <div class="mt-3">
                                                    <c:forEach items="${comments}" var="r">
                                                        <c:if test="${r.parentId == c.id}">
                                                            <div class="d-flex gap-3 mb-3 mt-3">
                                                                <i class="fa-solid fa-user-circle fa-2x text-secondary mt-1"></i>
                                                                <div>
                                                                    <div class="fw-bold" style="font-size: 13px;">
                                                                        @${r.user.id} 
                                                                        <span class="text-muted ms-1 fw-normal" style="font-size: 12px;">
                                                                            <c:set var="rdiffMs" value="${now.time - r.commentDate.time}" />
                                                                            <c:set var="rdiffSecs" value="${rdiffMs / 1000}" />
                                                                            <c:choose>
                                                                                <c:when test="${rdiffSecs < 60}">vừa xong</c:when>
                                                                                <c:when test="${rdiffSecs < 3600}">${fn:substringBefore(rdiffSecs / 60, '.')} phút trước</c:when>
                                                                                <c:when test="${rdiffSecs < 86400}">${fn:substringBefore(rdiffSecs / 3600, '.')} giờ trước</c:when>
                                                                                <c:otherwise>${fn:substringBefore(rdiffSecs / 86400, '.')} ngày trước</c:otherwise>
                                                                            </c:choose>
                                                                        </span>
                                                                    </div>
                                                                    <p class="mb-0 text-dark mt-1" style="font-size: 14px; white-space: pre-wrap; line-height: 1.5;">${r.content}</p>
                                                                    
                                                                    
                                                                    <div class="d-flex align-items-center mt-2 gap-3" style="font-size: 12px; font-weight: 500;">
                                                                        <a href="javascript:void(0)" class="text-decoration-none text-dark btn-like-comment d-flex align-items-center gap-1">
                                                                            <i class="fa-regular fa-thumbs-up" style="font-size: 14px;"></i> <span class="like-count">0</span>
                                                                        </a>
                                                                        <a href="javascript:void(0)" class="text-decoration-none text-dark" onclick="toggleReplyForm('${c.id}', '${r.user.id}')">
                                                                            Phản hồi
                                                                        </a>
                                                                        <c:if test="${not empty sessionScope.user and (sessionScope.user.id == r.user.id or sessionScope.user.admin)}">
                                                                            <a href="${pageContext.request.contextPath}/comment?action=delete&id=${r.id}&videoId=${video.id}" class="text-decoration-none text-danger" onclick="return confirm('Bạn có chắc muốn xóa phản hồi này không?');">
                                                                                Xóa
                                                                            </a>
                                                                        </c:if>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </c:if>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    
    <div class="col-lg-4">
        <c:choose>
            <c:when test="${empty viewedVideos}">
                <div class="alert alert-info border-info fw-bold text-center">Chưa có lịch sử xem</div>
            </c:when>
            <c:otherwise>
                <c:forEach items="${viewedVideos}" var="vv">
                    <div class="d-flex mb-2 align-items-start">
                        
                        <div class="position-relative me-2" style="width: 160px; min-width: 160px;">
                            <a href="${pageContext.request.contextPath}/video?id=${vv.id}">
                                <c:choose>
                                    <c:when test="${fn:contains(vv.id, '.')}">
                                        <img src="${pageContext.request.contextPath}/uploads/${vv.poster}" class="img-fluid rounded-3 w-100" style="aspect-ratio: 16/9; object-fit: cover;" alt="Poster">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="https://img.youtube.com/vi/${vv.id}/maxresdefault.jpg" class="img-fluid rounded-3 w-100" style="aspect-ratio: 16/9; object-fit: cover;" alt="Poster">
                                    </c:otherwise>
                                </c:choose>
                            </a>
                        </div>
                        
                        <div>
                            <a href="${pageContext.request.contextPath}/video?id=${vv.id}" class="text-decoration-none text-dark fw-bold" style="font-size: 14px; line-height: 1.4; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; overflow: hidden;">
                                ${vv.title}
                            </a>
                            <div class="text-muted-custom mt-1" style="font-size: 12px;">${vv.user != null ? vv.user.fullname : 'Channel Name'}</div>
                            <div class="text-muted-custom" style="font-size: 12px;">
                                ${vv.views} lượt xem
                                <c:choose>
                                    <c:when test="${not empty vv.uploadDate}">
                                        <c:set var="diffMs" value="${now.time - vv.uploadDate.time}" />
                                        <c:set var="diffSecs" value="${diffMs / 1000}" />
                                        &bull; 
                                        <c:choose>
                                            <c:when test="${diffSecs < 60}">vừa xong</c:when>
                                            <c:when test="${diffSecs < 3600}">${fn:substringBefore(diffSecs / 60, '.')} phút trước</c:when>
                                            <c:when test="${diffSecs < 86400}">${fn:substringBefore(diffSecs / 3600, '.')} giờ trước</c:when>
                                            <c:otherwise>${fn:substringBefore(diffSecs / 86400, '.')} ngày trước</c:otherwise>
                                        </c:choose>
                                    </c:when>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<jsp:include page="/views/layout/footer.jsp" />


<script>
function toggleReplyForm(commentId, replyToUser) {
    const formContainer = document.getElementById('reply-form-' + commentId);
    if (formContainer.classList.contains('d-none') || replyToUser) {
        formContainer.classList.remove('d-none');
        const textarea = formContainer.querySelector('textarea');
        if (textarea) {
            if (replyToUser) {
                // If replying to a specific user inside the thread, append their handle
                if (!textarea.value.includes('@' + replyToUser)) {
                    textarea.value = textarea.value.trim() + ' @' + replyToUser + ' ';
                }
            }
            textarea.focus();
            textarea.selectionStart = textarea.selectionEnd = textarea.value.length;
        }
    } else {
        formContainer.classList.add('d-none');
    }
}

document.querySelectorAll('.btn-like-comment').forEach(function(btn) {
    btn.addEventListener('click', function() {
        let icon = this.querySelector('i');
        let countSpan = this.querySelector('.like-count');
        let currentCount = parseInt(countSpan.innerText);
        
        if (icon.classList.contains('fa-regular')) {
            // Like
            icon.classList.remove('fa-regular');
            icon.classList.add('fa-solid');
            this.classList.add('text-primary');
            countSpan.innerText = currentCount + 1;
        } else {
            // Unlike
            icon.classList.remove('fa-solid');
            icon.classList.add('fa-regular');
            this.classList.remove('text-primary');
            countSpan.innerText = currentCount - 1;
        }
    });
});
</script>
</body>
</html>

