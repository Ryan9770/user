<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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

h1 {
	margin: 10px;
	font-weight: 700;
}

.box{
	width: 700px;
	margin: 30px auto;
}

.table-stock {
	width: 80%;
	margin: 0 auto;
	border-spacing: 0;
	border-collapse: collapse;
}


.table-stock th, .table-stock td{
	padding: 7px 0;
}

.table-stock thead tr:first-child{
	border-top: 2px solid #ccc;
	
	line-height: 60px;
	font-weight: 600;
}
.table-stock thead span{
	font-size: 10px;
	vertical-align: baseline;
	font-weight: 500;
}

.table-stock tr {
	height: 30px;
	border-bottom: 1px solid #ccc;
	text-align: center;
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
		<span class="article-title">배송관리</span>
	</div>
	<table class="table-stock paginated">
		
		<thead style="clear: both;">
			<tr>
				<th width="200" class="stockclass">주문번호</th>
				<th width="150" class="stockclass">이름</th>
				<th width="200" class="stockclass">전화번호</th>
				<th width="150" class="stockclass">우편번호</th>
				<th width="300" class="stockclass">기본주소</th>
				<th width="300" class="stockclass">상세주소</th>
				<th width="300" class="stockclass">배송메모</th>
				<th width="200" class="stockclass">송장번호</th>
			</tr>
		</thead>
		
		<tbody>
			<c:forEach var="dto" items="${list}">
				<tr>
					<td>${dto.orderNo}</td>
					<td>${dto.dName}</td>
					<td>${dto.dTel}</td>
					<td>${dto.dZipCode}</td>
					<td>${dto.dAddr1}</td>
					<td>${dto.dAddr2}</td>
					<td>${dto.del_memo}</td>
					<td>${dto.delNo}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
</main>
<footer>
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>

</body>
</html>