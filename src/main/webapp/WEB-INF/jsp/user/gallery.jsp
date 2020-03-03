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
    <title><fmt:message bundle="${loc}" key="label.gallery"/></title>
    <style>
        <jsp:include page="/WEB-INF/css/header.css" />
        <jsp:include page="/WEB-INF/css/style.css" />
    </style>
</head>
<body>

    <header>
        <jsp:include page="/WEB-INF/jsp/user/header.jsp"/>
    </header>

    <div class="row">
        <div class="column">
            <div class="case">
                <img alt="1.Jasper" src="${pageContext.request.contextPath}/images/1.Jasper.png" class="image"  style="width:100%">
                <div class="middle">
                    <div class="text">
                        <a style="color: white;" href="${pageContext.request.contextPath}/user/main/find_horse_by_id?horseId=1">1. Jasper</a>
                    </div>
                </div>
            </div>
            <div class="case">
                <img alt="2.Jasmine" src="${pageContext.request.contextPath}/images/2.Jasmine.jpg" class="image" style="width:100%">
                <div class="middle">
                    <div class="text">
                        <a style="color: white;" href="${pageContext.request.contextPath}/user/main/find_horse_by_id?horseId=2">2. Jasmine</a>
                    </div>
                </div>
            </div>
            
        </div>

        <div class="column">
            <div class="case">
                <img alt="3.Hurricane" src="${pageContext.request.contextPath}/images/3.Hurricane.jpg" class="image" style="width:100%">
                <div class="middle">
                    <div class="text">
                        <a style="color: white;" href="${pageContext.request.contextPath}/user/main/find_horse_by_id?horseId=3">3. Hurricane</a>
                    </div>
                </div>
            </div>
            <div class="case">
                <img alt="4.Eastern wind" src="${pageContext.request.contextPath}/images/4.Eastern wind.jpg" class="image" style="width:100%">
                <div class="middle">
                    <div class="text">
                        <a style="color: white;" href="${pageContext.request.contextPath}/user/main/find_horse_by_id?horseId=4">4. Eastern wind</a>
                    </div>
                </div>
            </div>
        </div>

        <div class="column">
            <div class="case">
                <img alt="5.Lightning" src="${pageContext.request.contextPath}/images/5.Lightning.jpg" class="image" style="width:100%">
                <div class="middle">
                    <div class="text">
                        <a style="color: white;" href="${pageContext.request.contextPath}/user/main/find_horse_by_id?horseId=5">5. Lightning</a>
                    </div>
                </div>
            </div>
            <div class="case">
                <img alt="6.Fire" src="${pageContext.request.contextPath}/images/6.Fire.jpg" class="image" style="width:100%">
                <div class="middle">
                    <div class="text">
                        <a style="color: white;" href="${pageContext.request.contextPath}/user/main/find_horse_by_id?horseId=6">6. Fire</a>
                    </div>
                </div>
            </div>
        </div>

        <div class="column">
            <div class="case">
                <img alt="7.Jumper" src="${pageContext.request.contextPath}/images/7.Jumper.jpg" class="image" style="width:100%">
                <div class="middle">
                    <div class="text">
                        <a style="color: white;" href="${pageContext.request.contextPath}/user/main/find_horse_by_id?horseId=7">7. Jumper</a>
                    </div>
                </div>
            </div>
            <div class="case">
                <img alt="Jack Pearson" src="${pageContext.request.contextPath}/images/Jack Pearson.jpg" class="image" style="width:100%">
                <div class="middle">
                    <div class="text">
                        <a style="color: white;" href="${pageContext.request.contextPath}/user/main/find_horse_by_id?horseId=15">15. Jack Pearson</a>
                    </div>
                </div>
            </div>
        </div>
    </div><p/>

    <a style=" color: tomato;" href="${pageContext.request.contextPath}/user/main/show_start_tables"><fmt:message bundle="${loc}" key="label.back"/></a>

    <footer>
        <jsp:include page="/WEB-INF/jsp/user/footer.jsp"/>
    </footer>
    
    
</body>

</html>