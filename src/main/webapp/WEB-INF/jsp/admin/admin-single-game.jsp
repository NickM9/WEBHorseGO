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
	<title><fmt:message bundle="${loc}" key="label.game"/> ${gameId}</title>
	<style>
  		<jsp:include page="/WEB-INF/css/header.css" />
  		<jsp:include page="/WEB-INF/css/style.css" />
	</style>
</head>
<body>
    <header>
        <jsp:include page="/WEB-INF/jsp/user/header.jsp"/>
    </header>

	<c:if test="${game != null}">
		<jsp:include page="/WEB-INF/jsp/tables/single-game-table.jsp"/>
		<div class="container" style="width: 35%;" >
			<h3><fmt:message bundle="${loc}" key="label.update_game"/></h3>
			<form name="updateGameForm" action="controller" method="post">
				<input type="hidden" name="command" value="admin_update_game">
				<input type="hidden" name="gameId" value="${game.id}">

				<div class="row">
					<table style="float: left;">
						<tr>
							<th><fmt:message bundle="${loc}" key="label.participants_list_changed"/></th>
							<th><fmt:message bundle="${loc}" key="label.game_status_changed"/></th>
						</tr>
						<td style="text-align: left; background-color: white;">
							<c:forEach var="horse" items="${horsesList}">
							<div class="row">
      							<div class="col-75">
									<input type="checkbox" name="horseId" value="${horse.id}">${horse.id}. ${horse.name}
								</div>
							</div>
							</c:forEach>
						</td>
						<td style="text-align: left; background-color: white;">
							<div class="row">
      							<div class="col-75">
									<input type="radio" name="gamePlayed" value="false" checked><fmt:message bundle="${loc}" key="label.not_played"/><br/>
									<input type="radio" name="gamePlayed" value="true"><fmt:message bundle="${loc}" key="label.played"/><br/>
								</div>
							</div>
						</td>
					</table>
				</div><p/>
				<div class="row">
					<input type="reset" value="<fmt:message bundle='${loc}' key='label.reset'/>">
					<input type="submit" value="<fmt:message bundle='${loc}' key='label.update'/>">
				</div>
			</form>

			<div class="row">
				<form name="deleteGameForm" action="controller" method="post">
					<input type="hidden" name="command" value="admin_delete_game">
					<input type="hidden" name="gameId" value="${game.id}">
					<input type="submit" value="<fmt:message bundle='${loc}' key='label.delete'/>">
				</form>
			</div>
		</div>

		<c:out value="${gameUpdateMessage}"/>
		<c:out value="${gameDeleteMessage}"/>
	</c:if>

	<h1><c:out value="${singleGameMessage}"/></h1>

	<a style=" color: tomato;" href="${pageContext.request.contextPath}/admin/admin-games/show_games?page=1"><fmt:message bundle="${loc}" key="label.back"/></a><p/>

</body>
<footer>
    <jsp:include page="/WEB-INF/jsp/user/footer.jsp"/>
</footer>
</html>