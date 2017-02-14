<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Home</title>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
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
	 <form id="guessForm" action="${contextPath}/guess" method="GET">
		Enter Number:<input type="text" name="number" /><br /><br /> 
		<button type="button" id="btnGuess">Guess</button>
	</form>	
</body>
<script type="text/javascript">
	
	$(document).ready(function(){
	    $("#btnGuess").click(function(){
			$("#guessForm").submit();
	    });
	});
</script>
</html>
