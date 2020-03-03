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
	<title><fmt:message bundle="${loc}" key="label.admin_users"/></title>
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
    		<h3><fmt:message bundle="${loc}" key="label.search_user_by_id"/></h3>
			<form name="findUserById" action="find_user_by_id" method="get">
				<div class="row">
            		<div class="col-75">
						<input type="search" placeholder="Id" name="foundUserId">
					</div>
				</div><p/>
				<div class="row">
					<input type="submit" value="<fmt:message bundle='${loc}' key='label.search'/>">
				</div>
			</form>
		</div><p/>

		<div class="container" style="width: 100%; background-color: white;">
			<h3><fmt:message bundle="${loc}" key="label.search_user_by_login"/></h3>
			<form name="findUserByLogin" action="find_user_by_login" method="get">
				<div class="row">
            		<div class="col-75">
						<input type="search" placeholder="<fmt:message bundle='${loc}' key='label.login'/>" name="foundUserLogin">
					</div>
				</div>
				<div class="row">
					<input type="submit" value="<fmt:message bundle='${loc}' key='label.search'/>">
				</div>
			</form>
		</div><p/>

		<div class="container" style="width: 100%; background-color: white;">
			<h3><fmt:message bundle="${loc}" key="label.search_user_by_full_name"/></h3>
			<form name="findUserByName" action="find_user_by_name_and_surname" method="get">
				<div class="row">
            		<div class="col-75">
						<input type="search" placeholder="<fmt:message bundle='${loc}' key='label.name'/>" name="foundUserName">
					</div>
					<div class="col-75">
						<input type="search" placeholder="<fmt:message bundle='${loc}' key='label.surname'/>" name="foundUserSurname">
					</div>
				</div>
				<div class="row">
					<input type="submit" value="<fmt:message bundle='${loc}' key='label.search'/>">
				</div>
			</form>
		</div><p/>

		<div class="container" style="width: 100%; background-color: white;">
			<h3><fmt:message bundle="${loc}" key="label.create_user"/></h3>
			<form name="createUserForm" action="controller" method="post">
				<input type="hidden" name="command" value="admin_create_user">
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
						<input type="password" placeholder="<fmt:message bundle='${loc}' key='label.password'/>" name="password" required/>
					</div>
					<div class="col-75">
						<input type="number" placeholder="<fmt:message bundle='${loc}' key='label.user_wallet'/>" name="wallet" min=0 step="0.01" required>
					</div>
				</div><p/>
				<div class="row">
            		<div class="col-75">
						<c:forEach var="role" items="${roleList}">
							<input type="radio" name="role" value="${role}" checked>${role}</input>
						</c:forEach>
					</div>
				</div><p/>
				<div class="row">
					<input type="reset" value="<fmt:message bundle='${loc}' key='label.reset'/>">
					<input type="submit" value="<fmt:message bundle='${loc}' key='label.create'/>">
				</div>
			</form>
		</div>
	</div>

	<c:out value="${createUserMessage}"/>
	<c:remove var="createUserMessage" scope="session"/>
	<c:out value="${deleteUserMessage}"/><p/>
	<c:remove var="deleteUserMessage" scope="session"/>

	<div class="main">
		<jsp:include page="/WEB-INF/jsp/admin/users-table.jsp"/>

		<div class="pagination">
        		<c:set var="pageId" value="${param.page}"/>
        		<a href="${pageContext.request.contextPath}/admin/admin-users/show_users?page=1">&laquo;</a>
        		<c:forEach var = "i" begin = "1" end = "${paddingSize}">
            		<c:choose>
                		<c:when test="${i == pageId}">
                    		<a class="active" href="${pageContext.request.contextPath}/admin/admin-users/show_users?page=${i}"><c:out value="${i}"/></a>  
                		</c:when>
                		<c:otherwise>
                    		<a href="${pageContext.request.contextPath}/admin/admin-users/show_users?page=${i}"><c:out value="${i}"/></a>
                		</c:otherwise>
            		</c:choose>
       			 </c:forEach>
        		<a href="${pageContext.request.contextPath}/admin/admin-users/show_users?page=${paddingSize}">&raquo;</a>
    		</div><p/>

	</div>
</div>
</body>
<footer>
    <jsp:include page="/WEB-INF/jsp/user/footer.jsp"/>
</footer>
</html>