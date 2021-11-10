<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>spring</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/product.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/layout.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/paginate.css" type="text/css">
<script src="https://kit.fontawesome.com/42044ce0be.js" crossorigin="anonymous"></script>
<style type="text/css">

</style>
</head>
<body>

<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>
	
<main>
<div class="container">
	<div class="contents">
		<ul class="prdList grid-box">
		
			<c:forEach var="dto" items="${list}" varStatus="status">
			<li>
				<div class="prdImg">
					<a href="${articleUrl}&pNo=${dto.pNo}"><img src="${pageContext.request.contextPath}/uploads/product/${dto.image_name}"> </a>
				</div>
				<div class="description">
					<a href="${articleUrl}&pNo=${dto.pNo}"><span class="prdName"> 상품명 : </span><span> ${dto.pName} </span></a>
					<a href="${articleUrl}&pNo=${dto.pNo}"><span class="prdPrice"> 상품가격 : </span><span> ${dto.pPrice} </span></a>
				</div>
			</li>
			</c:forEach>
		
		</ul>	
	</div>
	<div class="page-box">
			${dataCount == 0 ? "등록된 게시물이 없습니다.": paging}
	</div>
</div>
</main>

<footer>
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>

</body>
</html>