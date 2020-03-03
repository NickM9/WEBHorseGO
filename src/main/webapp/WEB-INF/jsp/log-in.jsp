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
		<title><fmt:message bundle="${loc}" key="label.log_in"/></title>
		<style>
  			<jsp:include page="/WEB-INF/css/header.css" />
  			<jsp:include page="/WEB-INF/css/style.css" />
		</style>
	</head>
	<body>
    	<header>
    		<ul>
        		<li><a href="${pageContext.request.contextPath}"><fmt:message bundle="${loc}" key="label.home_page"/></a></li>
        	</ul>
    	</header>

		<h1><fmt:message bundle="${loc}" key="label.log_in_system"/></h1>
		<div class="container">
			<form name="loginForm" method="post" action="controller">
				<input type="hidden" name="command" value="log_in">
				<div class="row">
      				<div class="col-25">
						<fmt:message bundle="${loc}" key="label.login"/>
					</div>
					<div class="col-75">
		 				<input type="text" name="login" maxlength="45" required/>
		 			</div>
		 		</div>

				<div class="row">
      				<div class="col-25">
		 				<fmt:message bundle="${loc}" key="label.password"/>
		 			</div>
		 			<div class="col-75">
		    			<input type="password" name="password" maxlength="45" required/></p>
		    		</div>
		    	</div>
				<div class="row">
					<a style="color: tomato;" href="sign-up"><fmt:message bundle="${loc}" key='label.sign_up'/></a></br>
					<c:out value="${loginError}"/>
					<c:remove var="loginError" scope="session"/>
				</div>
				<div class="row">
					<input type="submit" value="<fmt:message bundle='${loc}' key='label.log_in'/>"/>
				</div>
			</form>
		</div>
	</body>
</html>