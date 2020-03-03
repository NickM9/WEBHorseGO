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
	<title><fmt:message bundle="${loc}" key="label.password_settings"/></title>
	<style>
  		<jsp:include page="/WEB-INF/css/header.css" />
  		<jsp:include page="/WEB-INF/css/style.css" />
	</style>
</head>
<body>
    <header>
        <jsp:include page="/WEB-INF/jsp/user/header.jsp"/>
    </header>

	<h3><fmt:message bundle="${loc}" key="label.password_settings"/></h3>
	
	<h4><fmt:message bundle="${loc}" key="label.change_password"/></h4>
	<div class="container">
		<form name="updateLoginForm" method="post" action="controller">
			<input type="hidden" name="command" value="update_password">
			<div class="row">
      			<div class="col-25">
					<input type="password" name="newPassword" placeholder="<fmt:message bundle='${loc}' key='label.new_password'/>" maxlength="45" required/>
				</div>
			</div>
			<div class="row">
      			<div class="col-25">
					<input type="password" name="newPasswordConfirm" placeholder="<fmt:message bundle='${loc}' key='label.confirm_new_password'/>" maxlength="45" required/>
				</div>
			</div>
			<div class="row">
      			<div class="col-25">
					<input type="password" name="password" placeholder="<fmt:message bundle='${loc}' key='label.current_password'/>" maxlength="45" required/>
				</div>
			</div><p/>
			<div class="row">
				<input type="reset" value="<fmt:message bundle='${loc}' key='label.reset'/>"/>
				<input type="submit" value="<fmt:message bundle='${loc}' key='label.change'/>"/>
			</div>
	</form>
	</div><p/>

	<c:out value="${updatePasswordMessage}"/>
	<c:remove var="updatePasswordMessage" scope="session"/>

	<a style="background-color: tomato;
                color: white;
                padding: 10px 20px;
                border-radius: 4px;
                text-align: center;
                text-decoration: none;
                display: inline-block;" 
         href="${pageContext.request.contextPath}/user/main/settings"><fmt:message bundle="${loc}" key="label.back"/></a>

</body>
<footer style="position: fixed; bottom: 0; width: 100%">
    <jsp:include page="/WEB-INF/jsp/user/footer.jsp"/>
</footer>
</html>