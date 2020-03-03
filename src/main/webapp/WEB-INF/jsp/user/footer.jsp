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
        <style>
            <jsp:include page="/WEB-INF/css/header.css" />
        </style>
	</head>
	<body>
        <div class="header">
		  <fmt:message bundle="${loc}" key="label.footer_message"/>
        </div>
		<ul>
            <li><a href=""><fmt:message bundle="${loc}" key="label.about_us"/></a></li>
            <li><a href=""><fmt:message bundle="${loc}" key="label.contacts"/></a></li>
            <li><a href=""><fmt:message bundle="${loc}" key="label.help"/></a></li>
            <li><a href=""><fmt:message bundle="${loc}" key="label.rules"/></a></li>
        </ul>
	</body>
</html>