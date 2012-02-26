<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><decorator:title default="Spring Trains" /></title>
	<link rel="stylesheet" href="<c:url value="/resources/css/main.css" />" type="text/css" media="screen" />
	<!--[if lt IE 8]>
	        <link rel="stylesheet" href="<c:url value="/resources/blueprint/ie.css" />" type="text/css" media="screen, projection" />
	<![endif]-->
	<script type="text/javascript" src="<c:url value="/resources/js/jquery-1.7.1.js" />"></script>
	<decorator:head />
</head>
<body>
<div id="page" class="container">
	<div id="header">
		Spring Movies
		<hr/>
	</div>
	<div id="content">
		<decorator:body />
	</div>
	<div id="footer">
		<hr/>
	</div>
</div>
</body>
</html>