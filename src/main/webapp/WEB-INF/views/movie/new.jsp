<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
	<head>
		<title>Movie: New</title>
	</head>
	<body>
		<c:url var="backListUrl" value="/movies" />
		[<a href="${backListUrl}">Back to list</a>]
		
    	<h1>Create movie</h1>
   
      	<c:url value="/movies" var="newMovieUrl" />
      	<form:form modelAttribute="movie" action="${newMovieUrl}" method="POST">
      		<fieldset>
				<p>
          			<label for="director">Director: </label><br>
          			<form:input path="director" />
          			<form:errors cssClass="error" path="director" />
        		</p>      		
       			<p>
          			<label for="title">Title: </label><br>
          			<form:input path="title" />
          			<form:errors cssClass="error" path="title" />
        		</p>
       			<p>
          			<label for="genre">Genre: </label><br>
          			<form:input path="genre" />
          			<form:errors cssClass="error" path="genre" />
        		</p>
       			<p>
          			<label for="rating">Rating: </label><br>
          			<form:input path="rating" />
          			<form:errors cssClass="error" path="rating" />
        		</p>        		
        		<button type="submit">Add</button>
      		</fieldset>
      	</form:form>
	</body>
</html>