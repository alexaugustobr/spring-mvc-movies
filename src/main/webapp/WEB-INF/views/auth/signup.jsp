<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
	<title><s:message code="signup.title.label"/></title>
</head>
<body>
    <h1><s:message code="signup.title.label"/></h1>

    <c:if test="${not empty message}">
        <div class="alert alert-${message.type}">
            <s:message code="${message.message}" text="${message.message}" arguments="${message.args}"/>
        </div>
    </c:if>

    <s:url var="signupUrl" value="/auth/signup"/>
    <form:form action="${signupUrl}" method="post" modelAttribute="mailUser" cssClass="form-horizontal">
        <s:bind path="mailUser.emailAddress">
            <div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
                <form:label path="emailAddress" cssClass="control-label">
                    <s:message code="signup.email.label" />:
                </form:label>
                <div class="controls">
                    <form:input path="emailAddress" cssClass="input-xlarge focused" required="required"/>
                    <form:errors path="emailAddress" element="span" cssClass="help-inline"/>
                </div>
            </div>
        </s:bind>

        <s:bind path="mailUser.displayName">
            <div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
                <form:label path="displayName" cssClass="control-label">
                    <s:message code="signup.displayName.label" />:
                </form:label>
                <div class="controls">
                    <form:input path="displayName" cssClass="input-xlarge focused" required="required"/>
                    <form:errors path="displayName" element="span" cssClass="help-inline"/>
                </div>
            </div>
        </s:bind>

        <s:bind path="mailUser.password">
            <div class="control-group${(not empty status.errorMessage) ? ' error' : ''}">
                <form:label path="password" cssClass="control-label">
                    <s:message code="signup.password.label" />:
                </form:label>
                <div class="controls">
                    <form:password path="password" cssClass="input-xlarge focused" required="required"/>
                    <form:errors path="password" element="span" cssClass="help-inline"/>
                </div>
            </div>
        </s:bind>

        <div class="form-actions">
            <form:button class="btn btn-primary" type="submit" name="_action_save">
                <i class="icon-user icon-white"></i>
                <s:message code="signup.submit.label" />
            </form:button>

            <form:button class="btn" type="reset" name="_action_reset">
                <i class="icon-repeat icon-black"></i>
                <s:message code="signup.reset.label" />
            </form:button>
        </div>
    </form:form>
</body>
</html>