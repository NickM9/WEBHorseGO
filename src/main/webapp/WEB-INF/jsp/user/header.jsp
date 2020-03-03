<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tag" uri="/WEB-INF/tld/hello.tld" %>
<%@ page session="true" %>

<html lang="en">
	<head>
        <style>
            <jsp:include page="/WEB-INF/css/header.css" />
        </style>

        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <fmt:setLocale value="${sessionScope.local}"/>
        <fmt:setBundle basename="localization.local" var="loc"/>
	</head>
	<body>

        <div class="header">
            <h1><fmt:message bundle="${loc}" key="label.welcome_to"/> Horse<span>GO!</span></h1>
            <h3><tag:hello userName="${user.name}"/></h3>

        </div>
        
        <ul>
	       <li><a href="${pageContext.request.contextPath}/user/main/show_start_tables"><fmt:message bundle="${loc}" key="label.main"/></a></li>
            <li><a href="${pageContext.request.contextPath}/user/main/wallet"><fmt:message bundle="${loc}" key="label.wallet"/>(${user.wallet})</a></li>
            <li><a href="${pageContext.request.contextPath}/user/main/show_bets_history?page=1"><fmt:message bundle="${loc}" key="label.bets_history"/></a></li>
            <li><a href="${pageContext.request.contextPath}/user/gallery"><fmt:message bundle="${loc}" key="label.gallery"/></a></li>
        <c:if test="${sessionScope.user.role == 'ADMIN'}">
            <li class="dropdown">
                <a href="javascript:void(0)" class="dropbtn"><fmt:message bundle="${loc}" key="label.admin_page"/></a>
                <div class="dropdown-content">
                    <a href="${pageContext.request.contextPath}/admin/admin-games/show_games?page=1"><fmt:message bundle="${loc}" key="label.games_control"/></a>
                    <a href="${pageContext.request.contextPath}/admin/admin-horses/show_horses"><fmt:message bundle="${loc}" key="label.participant_control"/></a>
                    <a href="${pageContext.request.contextPath}/admin/admin-users/show_users?page=1"><fmt:message bundle="${loc}" key="label.user_control"/></a>
                </li>
            </div>
        </c:if>
        <c:if test="${sessionScope.user.role == 'BOOKMAKER'}">
            <li><a href="${pageContext.request.contextPath}/bookmaker/bookmaker-main/show_games?page=1"><fmt:message bundle="${loc}" key="label.bookmaker_page"/></a></li>
        </c:if>
        <li><a href="${pageContext.request.contextPath}/user/main/settings"><fmt:message bundle="${loc}" key="label.settings"/></a></li>

            <li class="right">
                <form name="updateLoginForm" method="post" action="controller">
                    <input type="hidden" name="command" value="log_out">
                    <input type="submit" value="<fmt:message bundle='${loc}' key='label.log_out'/>"/>
                </form>
            </li>
            <li class="right">
                <form action="controller" method="post">
                    <input type="hidden" name="command" value="change_language"/>
                    <input type="hidden" name="local" value="ru"/>
                    <input type="submit" value="<fmt:message bundle='${loc}' key='button.ru'/>"/>
                </form>
            </li>
            <li class="right">
                <form action="controller" method="post">
                    <input type="hidden" name="command" value="change_language"/>
                    <input type="hidden" name="local" value="en"/>
                    <input type="submit" value="<fmt:message bundle='${loc}' key='button.en'/>"/>
                </form>
            </li>
        </ul>
        
	</body>
</html>