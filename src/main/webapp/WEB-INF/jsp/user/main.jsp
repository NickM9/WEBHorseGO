<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="en">
<head>
	<fmt:setLocale value="${sessionScope.local}"/>
	<fmt:setBundle basename="localization.local" var="loc"/>
	<style>
  		<jsp:include page="/WEB-INF/css/header.css" />
  		<jsp:include page="/WEB-INF/css/style.css" />
	</style>

	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title><fmt:message bundle="${loc}" key="label.main"/></title>
</head>
<body>
    <header>
        <jsp:include page="/WEB-INF/jsp/user/header.jsp"/>
    </header><p/>

<div class="row">
	<div class="side">

    	<div class="container" style="width: 100%; background-color: white;">
			<h3><fmt:message bundle="${loc}" key="label.wallet_balance"/>: ${user.wallet}<br/></h3>
			<div class="row">
				<div class="col-75">
					<a style="background-color: tomato;
  								color: white;
  								padding: 10px 20px;
  								border-radius: 4px;
  								text-align: center;
  								text-decoration: none;
  								display: inline-block;" 
  								href="wallet"><fmt:message bundle="${loc}" key="label.increase_amount"/></a>
  				</div>
  				<div class="col-75">

					<a style="background-color: tomato;
  								color: white;
  								padding: 10px 20px;
  								border-radius: 4px;
  								text-align: center;
  								text-decoration: none;
  								display: inline-block;" 
								href="wallet"><fmt:message bundle="${loc}" key="label.decrease_wallet"/></a>
				</div>
			</div>
		</div><p/>

		<div class="container" style="width: 100%; background-color: white;">
			<h3><fmt:message bundle="${loc}" key="label.search_game"/></h3>
			<form name="findGameById" action="find_game_by_id" method="get">
				<div class="row">
					<div class="col-75">
						<input type="search" placeholder="<fmt:message bundle='${loc}' key='label.game_id'/>" name="gameId" required>
					</div>
				</div><p/>
				<div class="row">
					<input type="submit" value="<fmt:message bundle='${loc}' key='label.search'/>">
				</div>
			</form>
	

			<form name="findHorseById" action="find_horse_by_id" method="get">
				<h3><fmt:message bundle="${loc}" key="label.search_participant"/></h3>
				<div class="row">
					<div class="col-75">
						<input type="search" placeholder="<fmt:message bundle='${loc}' key='label.horse_id'/>" name="horseId" required>
					</div>
				</div><p/>
				<div class="row">
					<input type="submit" value="<fmt:message bundle='${loc}' key='label.search'/>">
				</div>
			</form>
		</div><p/>
		<a style="color: tomato;" href="${pageContext.request.contextPath}/user/main/show_games_history?page=1"><fmt:message bundle="${loc}" key="label.games_history"/></a>
	</div>

	<div class="main">
		<jsp:include page="/WEB-INF/jsp/tables/horses-table.jsp"/>
		<jsp:include page="/WEB-INF/jsp/tables/games-table.jsp"/>

		<c:out value="${betCreateMessage}"/><p/>
		<c:remove var="betCreateMessage" scope="session"/>

		<div class="container" style="width: 30%;">
			<h3><fmt:message bundle="${loc}" key="label.create_bet"/></h3>
			<form name="createGameBetForm" action="find_game_by_id" method="get">
				<fmt:message bundle="${loc}" key="label.select_game"/>
				<div class="row">
					<div class="col-75">
						<select style="width: 70%;" name="gameId">
							<c:forEach var="game" items="${gamesList}">
								<option value="${game.id}">${game.id}</option>
							</c:forEach>
						</select>
					</div>
				</div><p/>
				<div class="row">
					<input type="submit" value="<fmt:message bundle='${loc}' key='label.create_bet'/>"/>
				</div>
			</form>
		</div><p/>
	</div>
</div>


    <footer>
        <jsp:include page="/WEB-INF/jsp/user/footer.jsp"/>
    </footer>
</body>

</html>