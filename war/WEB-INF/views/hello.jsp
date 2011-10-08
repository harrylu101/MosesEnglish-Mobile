<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body>

	time now is ${model.now}

	<h3>Products</h3>
	<c:forEach items="${model.products}" var="prod">
		<c:out value="${prod.description}" />
		<i>$<c:out value="${prod.price}" /> </i>
		<br>
		<br>
	</c:forEach>
</body>
</html>