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

<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link rel="stylesheet" href="${pageContext.request.contextPath}/dist/bootstrap5/css/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/dist/bootstrap5/icon/bootstrap-icons.css" type="text/css">

<link rel="stylesheet" href="${pageContext.request.contextPath}/dist/css/core.css" type="text/css">

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/dist/bootstrap5/js/bootstrap.bundle.min.js"></script>

<style type="text/css">

*{
	padding: 0;
	margin: 0;
}

.container123 {
	clear:both;

	width: 1500px;
	margin-left: 290px;
	margin-top: 50px;
	
}

.mainPhoto {
	width: 100%;
	height: 800px;
	
	/*
	background-image: url('${pageContext.request.contextPath}/resource/images/main.jpg');
	background-repeat : no-repeat;
    background-size : cover;
    */
}

.griddd {
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
<div class="container123">

    <div class="mainPhoto">
		<div id="carouselExampleControls" class="carousel slide" data-bs-ride="carousel">
  <div class="carousel-inner">
    <div class="carousel-item active">
      <img src="${pageContext.request.contextPath}/resource/images/main.jpg" class="d-block w-100" alt="...">
    </div>
    <div class="carousel-item">
      <img src="${pageContext.request.contextPath}/resource/images/main22.jpg" class="d-block w-100" alt="...">
    </div>
    <div class="carousel-item">
      <img src="${pageContext.request.contextPath}/resource/images/main33.jpg" class="d-block w-100" alt="...">
    </div>
  </div>
  <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Previous</span>
  </button>
  <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Next</span>
  </button>
</div>
	</div>
	
	
	
	
	<div style="padding-top:40px;" class="mdpick">
		<a class="maintxt">MD's PICK <i class="fas fa-caret-down"></i></a>
	</div>
	
	<div class="maingrid">
		<div class="griddd">
			<a href="${pageContext.request.contextPath}/product/article.do?pNo=31">
				<img class="img" src="${pageContext.request.contextPath}/uploads/product/2021111218471218554859147300.jpg">
			</a>
		</div>
		<div class="griddd">
			<a href="${pageContext.request.contextPath}/product/article.do?pNo=29">
				<img class="img" src="${pageContext.request.contextPath}/uploads/product/2021111217502615146742060100.jpg">
			</a>
		</div>
		
		<div class="griddd">
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