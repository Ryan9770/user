<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>deeepbrow</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/layout.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">
<style type="text/css">
.title-body {
	padding: 50px 0; text-align: center;
}

.title-body .article-title {
	font-size: 20px;
}

a {
  color: #fff;
  text-decoration: none;
  cursor: pointer;
}
a:active, a:hover {
	text-decoration: underline;
	color: #F28011;
}

.title-body {
	padding: 50px 0; text-align: center;
}

.title-body .article-title {
	font-size: 20px; cursor: pointer;
	font-weight: bold;
}

.orderNo {
	position: absolute;
	left: 680px;
	top: 150px;
}

.box{
	width: 700px;
	margin: 30px auto;
}

.table-stock {
	width: 50%;
	margin: 0 auto;
	padding: 50px 0;
	border-spacing: 0;
	border-collapse: collapse;
}

.table-stock td{
	padding: 7px 0;
}

.table-stock tr, .table-stock td {
	height: 30px;
	border: 1px solid #ccc;
	text-align: center;
}

.table-stock .table-column {
	font-weight: bold;
}

.table-stock tbody span {
	cursor: pointer;
}

.add{
	color: #fff;
	float: right;
}

i{
	width: 40px;
	height: 40px;
}

.clickable { cursor: pointer; }
.hover { color: darkblue; }

</style>

<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css">

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript">

</script>

</head>
<body>
<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>
<main>
<div class="container">
 	<div class="title-body">
      <span class="article-title">주문상세정보</span>
   </div>
   	<h3 class="orderNo"> 주문번호 : ${dto.orderNo} </h3>
   	
	<table class="table-stock paginated">
			
			<tr>
				<td class="table-column">주문일자</td>
				<td class="table-value">${dto.order_date}</td>
			</tr>
			
			<tr>
				<td class="table-column">결제일자</td>
				<td class="table-value">${dto.pay_date}</td>
			</tr>
			
			<tr>
				<td class="table-column">결제금액</td>
				<td class="table-value">${dto.pay_price}원</td>
			</tr>
			
			<tr>
				<td class="table-column">아이디</td>
				<td class="table-value">${dto.mId}</td>
			</tr>
			
			<tr>
				<td class="table-column">이름</td>
				<td class="table-value">${dto.dName}</td>
			</tr>
			
			<tr>
				<td class="table-column">전화번호</td>
				<td class="table-value">${dto.dTel}</td>
			</tr>
			
			<tr>
				<td class="table-column">우편번호</td>
				<td class="table-value">${dto.dZipCode}</td>
			</tr>
			
			<tr>
				<td class="table-column">기본주소</td>
				<td class="table-value">${dto.dAddr1}</td>
			</tr>
			
			<tr>
				<td class="table-column">상세주소</td>
				<td class="table-value">${dto.dAddr2}</td>
			</tr>
			
			<tr>
				<td class="table-column">배송메모</td>
				<td class="table-value">${dto.del_memo}</td>
			</tr>
			
			<tr>
				<td class="table-column">송장번호</td>
				<td class="table-value">${dto.delNo}</td>
			</tr>
			
			<tr>
				<td class="table-column">주문상태</td>
				<td class="table-value">${empty dto.ds_manage ? '배송준비중' : dto.ds_manage}</td>
			</tr>
	</table>
</div>

</main>
<footer>
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>

</body>
</html>