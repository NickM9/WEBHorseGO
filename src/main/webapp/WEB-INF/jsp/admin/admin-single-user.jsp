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
	<title><fmt:message bundle="${loc}" key="label.user"/> ${foundUserId}</title>
	<style>
  		<jsp:include page="/WEB-INF/css/table.css" />
  		<jsp:include page="/WEB-INF/css/style.css" />
	</style>
</head>
<body>
    <header>
        <jsp:include page="/WEB-INF/jsp/user/header.jsp"/>
    </header>

	<c:if test="${foundUser != null}">
  		<table>
  			<caption align="left"><h3><fmt:message bundle="${loc}" key="label.user"/> № ${foundUser.id}</h3></caption>
			<tr>
				<th><fmt:message bundle="${loc}" key="label.user_number"/></th>
				<th><fmt:message bundle="${loc}" key="label.name"/></th>
				<th><fmt:message bundle="${loc}" key="label.surname"/></th>
				<th><fmt:message bundle="${loc}" key="label.login"/></th>
				<th><fmt:message bundle="${loc}" key="label.user_wallet"/></th>
				<th><fmt:message bundle="${loc}" key="label.role"/></th>
			</tr>
			<tr>
				<td><c:out value="${foundUser.id}"/></td>
				<td><c:out value="${foundUser.name}"/></td>
				<td><c:out value="${foundUser.surname}"/></td>
				<td><c:out value="${foundUser.login}"/></td>
				<td><c:out value="${foundUser.wallet}"/></td>
				<td><c:out value="${foundUser.role}"/></td>
			</tr>
		</table><p/>

		<div class="container" style="width: 25%;">
			<h3><fmt:message bundle="${loc}" key="label.update_user"/></h3>
			<form name="createUserForm" action="controller" method="post">
				<input type="hidden" name="command" value="admin_update_user">
				<input type="hidden" name="userId" value="${foundUser.id}">
				<div class="row">
            		<div class="col-75">
						<input type="text" placeholder="<fmt:message bundle='${loc}' key='label.name'/>" name="name" required/>
					</div>
					<div class="col-75">
						<input type="text" placeholder="<fmt:message bundle='${loc}' key='label.surname'/>" name="surname" required/>
					</div>
					<div class="col-75">
						<input type="text" placeholder="<fmt:message bundle='${loc}' key='label.login'/>" name="login" required/>
					</div>
					<div class="col-75">
						<input type="number" placeholder="<fmt:message bundle='${loc}' key='label.user_wallet'/>" name="wallet" min=0 step="0.01" required>
					</div>
				</div>
				<div class="row">
            		<div class="col-75">
						<c:forEach var="role" items="${roleList}">
							<input type="radio" name="role" value="${role}" checked>${role}</input>
						</c:forEach>
					</div>
				</div><p/>
				<div class="row">
					<input type="reset" value="<fmt:message bundle='${loc}' key='label.reset'/>">
					<input type="submit" value="<fmt:message bundle='${loc}' key='label.update'/>">
				</div>
			</form>

			<c:out value="${updateUserMessage}"/><p/>
			<c:remove var="updateUserMessage" scope="session"/>

			<div class="row">
				<form name="deleteUserForm" action="controller" method="post">
        			<input type="hidden" name="command" value="admin_delete_user">
        			<input type="hidden" name="userId" value="${foundUser.id}">
        			<input type="submit" value="<fmt:message bundle='${loc}' key='label.delete'/>">
    			</form>
    		</div>
    	</div>
	</c:if>

	<c:if test="${not empty betHistoryMap}">
			<jsp:include page="/WEB-INF/jsp/tables/bet-history-table.jsp"/>
	</c:if>

	
	<h1><c:out value="${singleUserMessage}"/></h1>

	<a style=" color: tomato;" href="${pageContext.request.contextPath}/admin/admin-users/show_users?page=1"><fmt:message bundle="${loc}" key="label.back"/></a><p/>

</body>
<footer>
    <jsp:include page="/WEB-INF/jsp/user/footer.jsp"/>
</footer>
</html>