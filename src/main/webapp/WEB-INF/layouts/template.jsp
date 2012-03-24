<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="utf-8" />
<title><decorator:title default="Spring Movies" /></title>
<meta name="description" content="spring mvc movies" />
<meta name="author" content="carlo micieli" />

<!-- mobile viewport optimisation -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet" href="<c:url value="/resources/css/site.css" />"
	type="text/css" media="screen" />
<!--[if lte IE 7]>
		<link rel="stylesheet" href="<c:url value="/resources/css/iehacks.min.css" />" type="text/css"/>
	<![endif]-->

<script type="text/javascript"
	src="<c:url value="/resources/js/jquery-1.7.1.js" />"></script>
<decorator:head />
</head>
<body>
	<div class="ym-wrapper">
		<div class="ym-wbox">
			<header>
				<h1>Spring Movies</h1>
			</header>
			<nav id="nav">
				<div class="ym-hlist">
					<ul>
						<li class="<decorator:getProperty property="home"/>">
							<a href="<c:url value="/home" />"><strong>Home</strong></a></li>
						<li><a href="#">You</a></li>
						<li><a href="<c:url value="/movies" />">Movies</a></li>
						<li><a href="#">Find a projection</a></li>
						<li><a href="#">Host a projection</a></li>
					</ul>

					<form class="ym-searchform">
						<sec:authorize access="authenticated" var="authenticated" />
						<c:choose>
							<c:when test="${authenticated}">
									Welcome <sec:authentication property="principal.username" />
									[<a href="<spring:url value="/logout" htmlEscape="true" />">Logout</a>]
								</c:when>
							<c:otherwise>
								<c:url var="loginUrl" value="/auth/login" />
									[<a href="${loginUrl}">Login</a>]
								</c:otherwise>
						</c:choose>
					</form>
				</div>
			</nav>

			<div id="main">
				<decorator:body />
			</div>
		</div>

		<footer role="contentinfo">
			<div class="ym-wrapper">
				<div class="ym-wbox">
					<div class="ym-grid linearize-level-2">
						<div class="ym-g66 ym-gl">
							<div class="ym-gbox-left">
								<p>
									© 2012 by Carlo Micieli, <a
										href="http://carlomicieli.github.com">http://carlomicieli.github.com</a><br>
									Code and Documentation licensed under <a
										href="http://creativecommons.org/licenses/by/2.0/">CC BY
										2.0</a>.
								</p>
								<p>
									<a href="#">about</a> | <a
										href="#">developers</a>
								</p>
							</div>
						</div>
						<div class="ym-g33 ym-gr">
							<div class="ym-gbox-right">
								<p>
									Layout based on <a href="http://www.yaml.de">YAML</a>
								</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</footer>
	</div>
</body>
</html>