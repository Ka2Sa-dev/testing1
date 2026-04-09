<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="course.name"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">

    <div class="text-end mb-3">
        <a href="?lang=en" class="btn btn-outline-primary btn-sm">English</a>
        <a href="?lang=zh_HK" class="btn btn-outline-primary btn-sm">繁體中文 (香港)</a>
    </div>

    <h1 class="text-center"><spring:message code="course.name"/></h1>
    <p class="text-center lead"><spring:message code="course.description"/></p>

    <h2 class="mt-5"><spring:message code="header.lectures"/></h2>
    <div class="row">
        <c:forEach var="lecture" items="${lectures}">
            <div class="col-md-6 mb-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">${lecture.title}</h5>
                        <p class="card-text">${lecture.summary}</p>
                        <a href="/lecture/${lecture.id}" class="btn btn-primary">
                            <spring:message code="btn.view.details"/>
                        </a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <h2 class="mt-5"><spring:message code="header.polls"/></h2>
    <div class="row">
        <c:forEach var="poll" items="${polls}">
            <div class="col-md-6 mb-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">${poll.question}</h5>
                        <a href="/poll/${poll.id}" class="btn btn-success">
                            <spring:message code="btn.go.vote"/>
                        </a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>