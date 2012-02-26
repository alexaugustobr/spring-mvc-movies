<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<html>
<head>
<title>Movies List</title>
</head>
<body>
	<h1>Movie list</h1>

	<table>
		<thead>
			<tr>
				<th>Director</th>
				<th>Title</th>
				<th>Genre</th>
				<th>Rating</th>
				<th></th>
				<th></th>
			</tr>
		</thead>
		<c:forEach var="movie" items="${movies}">
			<tr>
				<td>${movie.director}</td>
				<td>${movie.title}</td>
				<td>${movie.genre}</td>
				<td>${movie.rating}</td>
				<td><spring:url var="editUrl" value="/movies/{movieId}/edit">
						<spring:param name="movieId" value="${movie.id}" />
					</spring:url> <form:form modelAttribute="movie" action="${editUrl}" method="GET">
						<button type="submit">Edit</button>
					</form:form></td>
				<td><spring:url var="deleteUrl" value="/movies/{movieId}">
						<spring:param name="movieId" value="${movie.id}" />
					</spring:url> <form:form modelAttribute="movie" action="${deleteUrl}"
						method="DELETE">
						<button type="submit">Delete</button>
					</form:form></td>
			</tr>
		</c:forEach>
	</table>

	<hr />
	<div>
		<p>
			<c:url var="addUrl" value="/movies/new" />
		<h4>
			<a href="${addUrl}">Add</a>
		</h4>
		</p>
	</div>
</body>
</html>