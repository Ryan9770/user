<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>deeepbrow</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<style type="text/css">

*{
	padding: 0;
	margin: 0;
}

.mainPhoto {
	width: 100%;
	height: 500px;
	background-image: url('${pageContext.request.contextPath}/resource/images/main.jpg');
	background-repeat : no-repeat;
    background-size : cover;
}

.grid {
	width: 450px;
	height: 350px;
	
	display: inline-block;
	margin: 50px 20px;
}

.img {
	width: 100%;
	height: 100%;
}

</style>
</head>
<body>

<header>
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>
	
<main>
<div class="container">

    <div class="mainPhoto">
		<a href="#"></a>
	</div>
	
	<div class="maingrid">
		<div class="grid">
			<a href="#">
				<img class="img" alt="grid1" src="${pageContext.request.contextPath}/resource/images/main.jpg">
			</a>
		</div>
		
		<div class="grid">
			<a href="#">
				<img class="img" alt="grid1" src="${pageContext.request.contextPath}/resource/images/main.jpg">
			</a>
		</div>
		
		<div class="grid">
			<a href="#">
				<img class="img" alt="grid1" src="${pageContext.request.contextPath}/resource/images/main.jpg">
			</a>
		</div>
	</div>

</div>
</main>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>
</body>
</html>