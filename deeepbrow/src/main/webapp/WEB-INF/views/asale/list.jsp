<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>deeepbrow</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<style type="text/css">
.title-body {
	padding: 50px 0; text-align: center;
}

.title-body .article-title {
	font-size: 20px; cursor: pointer;
}
.sales-table{
	width: 80%;
	margin: 0 auto;
	border-spacing: 0;
	border-collapse: collapse;
	border-top: 2px solid white;
    border-bottom: 2px solid white;
    margin-bottom: 50px;
}

.sales-table tr{
	line-height: 30px;
	border-bottom: 1px solid white;
}
.sales-table tr:first-child, .sales-table tr:last-child{
	line-height: 60px;
	font-weight: 600;
}

</style>
</head>
<body>

<header>
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>

<main>
	<div class="container">
		<div class="title-body">
			<span class="article-title">판매 현황</span>
		</div>
		<table class="sales-table">
			<tr>
				<td>제품 번호</td>
				<td>제품 이름</td>
				<td>제품 이미지</td>
				<td>제품 가격</td>
				<td>팔린 갯수</td>
				<td>총합계</td>
			</tr>
			<tr>
				<td>1</td>
				<td>ㅇㅇㅇ</td>
				<td>사진</td>
				<td>10,000</td>
				<td>4</td>
				<td>40,000 원</td>
			</tr>
			<tr>
				<td>1</td>
				<td>ㅇㅇㅇ</td>
				<td>사진</td>
				<td>10,000</td>
				<td>4</td>
				<td>40,000 원</td>
			</tr>
			
			<tr>
				<td>총 매출액</td>
				<td colspan="4">----원</td>
			</tr>
		</table>
	</div>
</main>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>

</body>
</html>