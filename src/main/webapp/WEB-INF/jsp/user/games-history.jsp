<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><fmt:message bundle="${loc}" key="label.games_history"/></title>
    <style>
        <jsp:include page="/WEB-INF/css/header.css" />
        <jsp:include page="/WEB-INF/css/style.css" />
    </style>
</head>
<body>
    <header>
        <jsp:include page="/WEB-INF/jsp/user/header.jsp"/>
    </header>

    <jsp:include page="/WEB-INF/jsp/tables/games-table.jsp"/>

    <div class="pagination">
        <c:set var="pageId" value="${param.page}"/>
        <a href="${pageContext.request.contextPath}/user/main/show_games_history?page=1">&laquo;</a>
        <c:forEach var = "i" begin = "1" end = "${paddingSize}">
            <c:choose>
                <c:when test="${i == pageId}">
                    <a class="active" href="${pageContext.request.contextPath}/user/main/show_games_history?page=${i}"><c:out value="${i}"/></a>  
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/user/main/show_games_history?page=${i}"><c:out value="${i}"/></a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <a href="${pageContext.request.contextPath}/user/main/show_games_history?page=${paddingSize}">&raquo;</a>
    </div><p/>

    <c:if test="${sessionScope.user.role == 'ADMIN'}">
        <a style="color: tomato;" href="${pageContext.request.contextPath}/admin/admin-games/show_games?page=1"><fmt:message bundle="${loc}" key="label.back"/></a>
    </c:if>

    <c:if test="${sessionScope.user.role == 'BOOKMAKER'}">
        <a style="color: tomato;" href="${pageContext.request.contextPath}/bookmaker/bookmaker-main/show_games?page=1"><fmt:message bundle="${loc}" key="label.back"/></a>
    </c:if>

    <c:if test="${sessionScope.user.role == 'USER'}">
        <a style="background-color: tomato;
                color: white;
                padding: 10px 20px;
                border-radius: 4px;
                text-align: center;
                text-decoration: none;
                display: inline-block;" 
                href="${pageContext.request.contextPath}/user/main/show_start_tables?page=1"><fmt:message bundle="${loc}" key="label.back"/></a>
    </c:if>

	
</body>
<footer>
    <jsp:include page="/WEB-INF/jsp/user/footer.jsp"/>
</footer>
</html>