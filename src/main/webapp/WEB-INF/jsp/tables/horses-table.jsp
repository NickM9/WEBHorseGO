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
	<title><fmt:message bundle="${loc}" key="label.horses_table"/></title>
	<style>
  		<jsp:include page="/WEB-INF/css/table.css" />
  		<jsp:include page="/WEB-INF/css/style.css" />
	</style>
</head>
<body>
  
  	
	<table>
		<caption align="left"><h3><fmt:message bundle="${loc}" key="label.participants_list"/></h3></caption>
		<tr>
			<th><fmt:message bundle="${loc}" key="label.participant_number"/></th>
			<th><fmt:message bundle="${loc}" key="label.participant_name"/></th>
			<th><fmt:message bundle="${loc}" key="label.participant_games"/></th>
		</tr>
		<c:forEach var="elem" items="${horsesMap}">
		<c:set var="horse" scope="request" value="${elem.key}"/>
		<c:set var="horseGames" scope="request" value="${elem.value}"/>
			<tr>
				<td><a style="color: black;" href="${pageContext.request.contextPath}/user/main/find_horse_by_id?horseId=${horse.id}"><c:out value="${horse.id}"/></td>
				<td><c:out value="${horse.name}"/></td>
				<td>
					<c:forEach var="game" items="${horseGames}">
						<c:out value="${game}"/>
					</c:forEach>
				</td>
			</tr>
		</c:forEach>
	</table><p/>
  
</body>
</html>