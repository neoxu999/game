<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
<script type="text/javascript" src="/game/resources/js/jquery-3.1.1.min.js"></script>
 
</head>
<body>
<h2> 
<% 
 String message = null;
 if (request.getAttribute("message") != null) {
	  message = (String) request.getAttribute("message");
 }
 if (message != null ) {
	 out.print(message);
 } else {
	 out.print("");
 }
%>
</h2>
	<form id="login" action="" method="GET">
		User name:<input type="text" name="username" /><br /><br /> 
		Password:<input type="password" name="password" /><br /><br /> 
		<button type="button" id="btnSignin">Signin</button><br /><br /><br /> 
		<button type="button" id="btnNewAccount">Create New Account</button>
	</form>
</body>
<script type="text/javascript">
	 
	
	/* if (message != null) {
		alert(message);
	} */
	
	$(document).ready(function(){
	    $("#btnSignin").click(function(){
	    	$("#login").attr("action", "${contextPath}/login/signin");
			$("#login").submit();
	    });
	    
	    $("#btnNewAccount").click(function(){
	    	$("#login").attr("action", "${contextPath}/login/newAccount");
			$("#login").submit();
	    });
	});
	 
</script>
</html>
