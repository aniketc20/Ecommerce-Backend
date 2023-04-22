<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<!DOCTYPE html>
<html lang="en">
    <head></head>
    <form:form action="authenticate" method="post" modelAttribute="user">
    	<form:input path="username" type="text" class="form-control"
        required="required" />
        <form:input path="password" type="text" class="form-control"
        required="required" />
        <form:button>Login</form:button>
    </form:form>
</html>
