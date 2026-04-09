<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="lecture.title.prefix" text="講座："/> ${lecture.title}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">

    <a href="/" class="btn btn-secondary mb-3">← <spring:message code="btn.back.to.home" text="回到首頁"/></a>

    <h1>${lecture.title}</h1>
    <p class="lead">${lecture.summary}</p>

    <hr>

    <h3><spring:message code="lecture.notes" text="講座筆記"/></h3>
    <c:if test="${not empty materials}">
        <ul class="list-group">
            <c:forEach var="material" items="${materials}">
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    ${material.originalFileName}
                    <a href="/lecture/download/${material.id}" class="btn btn-primary btn-sm">
                        <spring:message code="btn.download" text="下載"/>
                    </a>
                </li>
            </c:forEach>
        </ul>
    </c:if>
    <c:if test="${empty materials}">
        <p class="text-muted"><spring:message code="lecture.no.materials" text="目前沒有上傳筆記"/></p>
    </c:if>

    <hr>

    <h3><spring:message code="lecture.comments" text="留言區"/></h3>

    <c:forEach var="comment" items="${comments}">
        <div class="card mb-3">
            <div class="card-body">
                <p>${comment.content}</p>
                <small class="text-muted">
                    By User ${comment.authorId} • ${comment.createdAt}
                </small>
            </div>
        </div>
    </c:forEach>

    <c:if test="${isLoggedIn}">
        <form action="/lecture/${lecture.id}/comment" method="post" class="mt-4">
            <div class="mb-3">
                <textarea name="content" class="form-control" rows="3" 
                          placeholder="<spring:message code="comment.placeholder" text="請輸入留言內容..."/>" 
                          required></textarea>
            </div>
            <button type="submit" class="btn btn-success">
                <spring:message code="btn.submit.comment" text="送出留言"/>
            </button>
        </form>
    </c:if>
    <c:if test="${not isLoggedIn}">
        <p class="text-danger">
            請先 <a href="/login"><spring:message code="btn.login" text="登入"/></a> 才能留言
        </p>
    </c:if>

</div>
</body>
</html>