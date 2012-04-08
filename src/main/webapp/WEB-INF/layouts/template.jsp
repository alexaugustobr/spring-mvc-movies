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
	<a href="http://github.com/CarloMicieli/spring-mvc-movies"><img style="position: absolute; top: 0; right: 0; border: 0;" src="https://a248.e.akamai.net/assets.github.com/img/e6bef7a091f5f3138b8cd40bc3e114258dd68ddf/687474703a2f2f73332e616d617a6f6e6177732e636f6d2f6769746875622f726962626f6e732f666f726b6d655f72696768745f7265645f6161303030302e706e67" alt="Fork me on GitHub"></a>
	<div class="ym-wrapper">
		<div class="ym-wbox">
			<header>
				<h1>Spring Movies</h1>
			</header>
			<nav id="nav">
				<div class="ym-hlist">
					<ul>
						<li class="<decorator:getProperty property="home"/>">
							<a href="<c:url value="/home" />"><strong><spring:message code="navigation.home"/></strong></a></li>
						<li><a href="<c:url value="/movies" />"><spring:message code="navigation.movies"/></a></li>
						<li><a href="<c:url value="/shows/new" />"><spring:message code="navigation.host.show"/></a></li>
						<li><a href="<c:url value="/movies" />"><spring:message code="navigation.find.show"/></a></li>
					</ul>

					<form class="ym-searchform">
						<sec:authorize access="authenticated" var="authenticated" />
						<c:choose>
							<c:when test="${authenticated}">
									<span class="highlight">Welcome <sec:authentication property="principal.username" /></span>
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
										href="http://www.apache.org/licenses/LICENSE-2.0.html">Apache 2.0 license</a>.
								</p>
								<p>
									<a href="<c:url value="/home/about" />">about</a> | <a href="#">developers</a>
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