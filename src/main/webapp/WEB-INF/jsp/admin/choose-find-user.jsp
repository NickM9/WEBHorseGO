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
  		<jsp:include page="/WEB-INF/css/style.css" />
	</style>
</head>
<body>

	<header>
        <jsp:include page="/WEB-INF/jsp/user/header.jsp"/>
    </header><p/>

    <h3><fmt:message bundle="${loc}" key="label.select_user"/></h3>
	<form name="findUserById" action="find_user_by_id" method="get">
		<c:forEach var="user" items="${foundUsers}">
			<input type="checkbox" name="foundUserId" value="${user.id}">User id - ${user.id}, ${user.name}, ${user.surname}, ${user.login}, ${user.wallet}, ${user.role}<br/>
		</c:forEach>
		<input type="submit" value="<fmt:message bundle='${loc}' key='label.search'/>">
	</form>

</body>
</html>