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
	<title><fmt:message bundle="${loc}" key="label.admin_games"/></title>
	<style>
  		<jsp:include page="/WEB-INF/css/header.css" />
  		<jsp:include page="/WEB-INF/css/style.css" />
	</style>
</head>
<body>
    <header>
        <jsp:include page="/WEB-INF/jsp/user/header.jsp"/>
    </header><p/>

<div class="row">
	<div class="side">

		<div class="container" style="width: 100%; background-color: white;">
			<h3><fmt:message bundle="${loc}" key="label.search_game"/></h3>
			<form name="findGameById" action="find_game_by_id" method="get">
				<div class="row">
					<div class="col-75">
						<input type="search" placeholder="Id" name="gameId">
					</div>
				</div><p/>
				<div class="row">
					<input type="submit" value="<fmt:message bundle='${loc}' key='label.search'/>">
				</div>
			</form>
		</div><p/>

		<div class="container" style="width: 100%; background-color: white;">
			<h3><fmt:message bundle="${loc}" key="label.create_game"/></h3>
			<form name="createGameForm" action="controller" method="post">
				<input type="hidden" name="command" value="admin_create_game">
				<c:forEach var="horse" items="${horsesList}">
					<div class="row">
      					<div class="col-75">
							<input type="checkbox" name="horseId" value="${horse.id}">${horse.id}. ${horse.name}
						</div>
					</div>
				</c:forEach><p/>
				<div class="row">
					<input type="reset" value="<fmt:message bundle='${loc}' key='label.reset'/>">
					<input type="submit" value="<fmt:message bundle='${loc}' key='label.create'/>">
				</div>
			</form>
		</div>

		<c:out value="${gameCreateMessage}"/><p/>
		<c:remove var="gameCreateMessage" scope="session"/>
		<c:out value="${noHorseChecked}"/><p/>
		<c:remove var="noHorseChecked" scope="session"/>


		<div class="container" style="width: 100%; background-color: white;" >
			<h3><fmt:message bundle="${loc}" key="label.update_game"/></h3>
			<form name="updateGameForm" action="controller" method="post">
				<input type="hidden" name="command" value="admin_update_game">
				<div class="row">
      				<div class="col-25">
						<fmt:message bundle="${loc}" key="label.select_game"/>
					</div>
					<div class="col-75">
						<select name="gameId" style="width: 30%; background-color: #f2f2f2;">
							<c:forEach var="game" items="${gamesList}">
								<option value="${game.id}">${game.id}</option>
							</c:forEach>
						</select>
					</div>
				</div><p/>
				<div class="row">
					<table style="float: left; width: 100%">
						<tr>
							<th><fmt:message bundle="${loc}" key="label.participants_list_changed"/></th>
							<th><fmt:message bundle="${loc}" key="label.game_status_changed"/></th>
						</tr>
						<td style="text-align: left;">
							<c:forEach var="horse" items="${horsesList}">
							<div class="row">
      							<div class="col-75">
									<input type="checkbox" name="horseId" value="${horse.id}">${horse.id}. ${horse.name}
								</div>
							</div>
							</c:forEach>
						</td>
						<td style="text-align: left;">
							<div class="row">
      							<div class="col-75">
									<input type="radio" name="gamePlayed" value="false" checked><fmt:message bundle="${loc}" key="label.not_played"/>
									<input type="radio" name="gamePlayed" value="true"><fmt:message bundle="${loc}" key="label.played"/>
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
		</div><p/>

		<c:out value="${gameUpdateMessage}"/>
		<c:remove var="gameUpdateMessage" scope="session"/>
	
		<div class="container" style="width: 100%; background-color: white;">
			<h3><fmt:message bundle="${loc}" key="label.delete_game"/></h3>
			<form name="deleteGameForm" action="controller" method="post">
				<input type="hidden" name="command" value="admin_delete_game">
				<div class="row">
					<div class="col-75">
						<fmt:message bundle="${loc}" key="label.select_game"/>
						<select style="width: 30%; background-color: #f2f2f2;" name="gameId">
							<c:forEach var="game" items="${gamesList}">
								<option value="${game.id}">${game.id}</option>
							</c:forEach>
						</select>
					</div>
				</div><p/>
				<div class="row">
					<input type="submit" value="<fmt:message bundle='${loc}' key='label.delete'/>">
				</div>
			</form>
		</div><p/>

		<c:out value="${gameDeleteMessage}"/>
		<c:remove var="gameDeleteMessage" scope="session"/>

		<div class="container" style="width: 100%; background-color: white;">
			<h3><fmt:message bundle="${loc}" key="label.start_game"/></h3>
			<form name="startGameForm" action="controller" method="post">
				<input type="hidden" name="command" value="admin_start_game">
				<div class="row">
					<div class="col-75">
						<fmt:message bundle="${loc}" key="label.select_game"/>
						<select style="width: 30%; background-color: #f2f2f2;" name="gameId">
							<c:forEach var="game" items="${gamesList}">
								<option value="${game.id}">${game.id}</option>
							</c:forEach>
						</select>
					</div>
				</div><p/>
				<div class="row">
					<input type="submit" value="<fmt:message bundle='${loc}' key='label.start'/>">
				</div>
			</form>
		</div>
	</div>

	<div class="main">

		<c:if test="${gameResult != null}">
			<jsp:include page="/WEB-INF/jsp/tables/game-result-table.jsp"/>
		</c:if>

		<c:if test="${not empty gamesList}">
			<jsp:include page="/WEB-INF/jsp/tables/games-table.jsp"/>

			<div class="pagination">
        		<c:set var="pageId" value="${param.page}"/>
        		<a href="${pageContext.request.contextPath}/admin/admin-games/show_games?page=1">&laquo;</a>
        		<c:forEach var = "i" begin = "1" end = "${paddingSize}">
            		<c:choose>
                		<c:when test="${i == pageId}">
                    		<a class="active" href="${pageContext.request.contextPath}/admin/admin-games/show_games?page=${i}"><c:out value="${i}"/></a>  
                		</c:when>
                		<c:otherwise>
                    		<a href="${pageContext.request.contextPath}/admin/admin-games/show_games?page=${i}"><c:out value="${i}"/></a>
                		</c:otherwise>
            		</c:choose>
       			 </c:forEach>
        		<a href="${pageContext.request.contextPath}/admin/admin-games/show_games?page=${paddingSize}">&raquo;</a>
    		</div><p/>
    		<c:remove var="gameResult" scope="session"/>
		</c:if>

		<a style="color: tomato;" href="${pageContext.request.contextPath}/admin/admin-games/show_games_history?page=1"><fmt:message bundle="${loc}" key="label.games_history"/></a>
		<c:out value="${gamesTableMessage}"/><p/>
	</div>
</div>

</body>
<footer>
    <jsp:include page="/WEB-INF/jsp/user/footer.jsp"/>
</footer>
</html>