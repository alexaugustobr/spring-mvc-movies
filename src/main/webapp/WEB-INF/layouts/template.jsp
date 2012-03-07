<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title><decorator:title default="Spring Movies" /></title>
	<link rel="stylesheet" href="<c:url value="/resources/css/site.css" />" type="text/css" media="screen" />
    <script type="text/javascript" src="<c:url value="/resources/js/jquery-1.7.1.js" />"></script>	
    <decorator:head />
</head>

<body>
    <div class="page">

        <div id="header">
            <div id="title">
                <h1>Spring Movies</h1>
            </div>
              
            <div id="logindisplay">
            	<sec:authorize access="fullyAuthenticated">
            		<sec:authentication property="principal.username" />
                	[<a href="<spring:url value="/j_spring_security_logout" htmlEscape="true" />">Logout</a>]
                </sec:authorize>
                <sec:authorize access="not fullyAuthenticated">
					<c:url var="loginUrl" value="/auth/login" />
					[<a href="${loginUrl}">Login</a>]
                </sec:authorize>
            </div> 
            
            <div id="menucontainer">
                <ul id="menu">     
                    <li>
						<c:url var="homeUrl" value="/home" />
						<a href="${homeUrl}">Home</a>
					</li>         
                    <li>
						<c:url var="movieListUrl" value="/movies" />
						<a href="${movieListUrl}">Movies list</a>
					</li>
                    <li>
						<c:url var="aboutUrl" value="/about" />
						<a href="${aboutUrl}">About</a>
					</li>  
                </ul>
            </div>
        </div>

        <div id="main">
			<decorator:body />
            <div id="footer">
            </div>
        </div>
    </div>
</body>
</html>