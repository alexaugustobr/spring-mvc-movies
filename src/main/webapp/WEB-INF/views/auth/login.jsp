<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
	<title><s:message code="login.title" /></title>
</head>
<body>
    <div class="container">
        <div class="content">
            <div class="row">
                <div class="login-form">
                    <h2><s:message code="login.title" /></h2>
                    <p>
                        <s:url var="signupUrl" value="/auth/signup"/>
                        New user? <a href="${signupUrl}">Sign up here</a> (FREE)
                    </p>

                    <c:if test="${param.login_error != null}">
                        <div class="alert alert-error">
                            <s:message code="login.error.message"></s:message>
                        </div>
                    </c:if>

                    <s:url var="loginUrl" value="/auth/authenticate"/>
                    <form action="${loginUrl}" method="POST">
                        <fieldset>
                            <div class="clearfix">
                                <input type="text" id="username" placeholder="Username"/>
                            </div>
                            <div class="clearfix">
                                <input type="password" id="password" placeholder="Password"/>
                            </div>
                            <button class="btn primary" type="submit">Sign in</button>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>