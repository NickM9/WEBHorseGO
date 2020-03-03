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
  		<jsp:include page="/WEB-INF/css/table.css" />
  		<jsp:include page="/WEB-INF/css/style.css" />
	</style>
</head>
<body>
 
  	<table>
  		<caption align="left"><h3><fmt:message bundle="${loc}" key="label.users_list"/></h3></caption>
		<tr>
			<th><fmt:message bundle="${loc}" key="label.user_number"/></th>
			<th><fmt:message bundle="${loc}" key="label.name"/></th>
			<th><fmt:message bundle="${loc}" key="label.surname"/></th>
			<th><fmt:message bundle="${loc}" key="label.login"/></th>
			<th><fmt:message bundle="${loc}" key="label.user_wallet"/></th>
			<th><fmt:message bundle="${loc}" key="label.role"/></th>
		</tr>
		<c:forEach var="user" items="${usersList}">
			<tr>
				<td><a style="color: black;" href="${pageContext.request.contextPath}/admin/admin-users/find_user_by_id?foundUserId=${user.id}"><c:out value="${user.id}"/></td>
				<td><c:out value="${user.name}"/></td>
				<td><c:out value="${user.surname}"/></td>
				<td><c:out value="${user.login}"/></td>
				<td><c:out value="${user.wallet}"/></td>
				<td><c:out value="${user.role}"/></td>
			</tr>
		</c:forEach>
	</table><p/>
	
	<c:out value="${usersTableMessage}"/><p/>
  
</body>
</html>