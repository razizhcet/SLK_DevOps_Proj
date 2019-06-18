<%--
  User: Imamsab Hulagur & Pawanchandra R Dhawaskar
  Date: 03/05/2019
  Time: 5:52 PM
  --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="author" content="Balasubramanyam Bodicherla">
<title>Home Page</title>
<!-- Bootstrap CSS -->
<%-- <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet"> --%>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<style type="text/css">
.myrow-container {
	margin: 20px;
}
</style>
</head>
<body class=".container-fluid">
	<div class="container myrow-container">
		<div class="panel panel-success">
			<div class="panel-heading">
				<h3 class="panel-title">
					<div align="left">
					<a href="getAllEmployees">Find Employee</a>
					</div>
					<div align="center">
						<a href="home">Home</a>
					</div>
					<div align="right">
						<a href="createEmployee">New Employee Registration ?</a>
					</div>
				</h3>
			</div>
		</div>
	</div>
</body>
</html>