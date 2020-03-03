<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="en">
<head>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><fmt:message bundle="${loc}" key="label.participant"/> ${horseId}</title>
    <style>
        <jsp:include page="/WEB-INF/css/header.css" />
        <jsp:include page="/WEB-INF/css/style.css" />
    </style>
</head>
<body>
    <header>
        <jsp:include page="/WEB-INF/jsp/user/header.jsp"/>
    </header>

    <c:if test="${horse != null}">

    	<jsp:include page="/WEB-INF/jsp/tables/single-horse-table.jsp"/>

        <c:if test="${not empty horseGames}">
            <c:set var="gamesList" scope="request" value="${horseGames}"/>
            <jsp:include page="/WEB-INF/jsp/tables/games-table.jsp"/>
        </c:if>
    	
	</c:if>

    <h1><c:out value="${singleHorseMessage}"/></h1>

    <a style="background-color: tomato;
                color: white;
                padding: 10px 20px;
                border-radius: 4px;
                text-align: center;
                text-decoration: none;
                display: inline-block;"
                href="${pageContext.request.contextPath}/user/main/show_start_tables"><fmt:message bundle="${loc}" key="label.back"/></a><p/>

</body>
<footer>
    <jsp:include page="/WEB-INF/jsp/user/footer.jsp"/>
</footer>
</html>