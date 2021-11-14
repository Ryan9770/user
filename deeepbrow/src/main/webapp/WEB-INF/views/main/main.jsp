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
	height: 800px;
	background-image: url('${pageContext.request.contextPath}/resource/images/main.jpg');
	background-repeat : no-repeat;
    background-size : cover;
}

.grid {
	width: 450px;
	height: 610px;
	
	display: inline-block;
	margin: 50px 20px;
}

.img {
	width: 100%;
	height: 100%;
}
.maintxt{
	padding-top: 25px;
	font-size: 20px;
	font-style: italic;
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
	<div style="padding-top:40px;" class="mdpick">
		<a class="maintxt">MD's PICK <i class="fas fa-caret-down"></i></a>
	</div>
	<div class="maingrid">
		<div class="grid">
			<a href="${pageContext.request.contextPath}/product/article.do?pNo=31">
				<img class="img" src="${pageContext.request.contextPath}/uploads/product/2021111218471218554859147300.jpg">
			</a>
		</div>
		<div class="grid">
			<a href="${pageContext.request.contextPath}/product/article.do?pNo=29">
				<img class="img" src="${pageContext.request.contextPath}/uploads/product/2021111217502615146742060100.jpg">
			</a>
		</div>
		
		<div class="grid">
			<a href="${pageContext.request.contextPath}/product/article.do?pNo=38">
				<img class="img" src="${pageContext.request.contextPath}/uploads/product/2021111219003119353784234800.jpg">
			</a>
		</div>
	</div>

</div>
</main>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>
</body>
<script>
	$(document).ready(function(){
		$('.maintxt').click(function(){
			var offset = $('.maingrid').offset();
	        $('html').animate({scrollTop : offset.top}, 300);
		});
	});
</script>
</html>