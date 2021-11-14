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
* {
	margin: 0; padding: 0;
    box-sizing: border-box;
}

body {
	font-size: 14px;
	font-family: "Malgun Gothic", "맑은 고딕", NanumGothic, 나눔고딕, 돋움, sans-serif;
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
	font-size: 20px;
}


.box{
	width: 700px;
	margin: 30px auto;
}

.table-stock {
	width: 80%;
	margin: 0 auto;
	border-collapse: collapse;
	border-spacing: 0;
	margin-bottom: 100px;
}


.table-stock th, .table-stock td{
	padding: 7px 0;
}

.table-stock thead tr:first-child{
	border-top: 2px solid #ccc;
	background: none;
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
	width: 23%;
	float: left;
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

function ajaxFun(url, method, query, dataType, fn) {
	$.ajax({
		type:method,
		url:url,
		data:query,
		dataType:dataType,
		success:function(data) {
			fn(data);
		},
		beforeSend:function(jqXHR) {
			jqXHR.setRequestHeader("AJAX", true);
		},
		error:function(jqXHR) {
			if(jqXHR.status === 403) {
				login();
				return false;
			} else if(jqXHR.status === 405) {
				alert("접근을 허용하지 않습니다.");
				return false;
			}
	    	
			console.log(jqXHR.responseText);
		}
	});
}

// 상품 삭제 여부
$(function(){
	$(".btn-delete").click(function(){
		var $tr = $(this).closest("tr");
		var msg = "상품을 삭제하시겠습니까? ";
		if(! confirm( msg )) {
			return false;
		}
		
		var url = "${pageContext.request.contextPath}/product/delete.do";
		var pNo = $(this).attr("data-pNo");
		
		var query = "pNo=" + pNo;

		var fn = function(data) {
			var state = data.state;
			if(state === "true") {
				$tr.remove();
			} else if(state === "false") {
				alert("판매된 상품은 제거하실수 없습니다.");
				return false;
			}
		};
		
		ajaxFun(url, "post", query, "json", fn);
	});
});


</script>

</head>
<body>
<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>
<main>
<div class="container">
	<div class="title-body">
		<span class="article-title">재고관리</span>
	</div>
	<table class="table-stock paginated">
			<div><a class="add" href="${pageContext.request.contextPath}/product/write.do"><i class="fas fa-cart-plus"></i></a> </div>
		<thead style="clear: both;">
			<tr>
				<th width="100" class="stockclass">제품번호</th>
				<th width="150" class="stockclass">제품명</th>
				<th width="150" class="stockclass">제품등록일</th>
				<th width="100" class="stockclass">제품가격</th>
				<th width="100" class="stockclass">제품수량</th>
				<th width="400" class="stockclass">제품설명</th>
				<th width="100" class="stockclass">분류</th>
				<th width="100" class="stockclass">변경</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="vo" items="${list}">
			<tr class="row_tr">
				<td>${vo.pNo}</td>
				<td class="clickable" onclick="location.href='${pageContext.request.contextPath}/product/article.do?pNo=${vo.pNo}';">${vo.pName}</td>
				<td>${vo.pDate}</td>
				<td>${vo.pPrice}</td>
				<td>${vo.pStock}</td>
				<td>${vo.pDesc}</td>
				<td>${vo.pCategory_name}</td>
				<td><span class="btn-update" data-pNo="${vo.pNo}" onclick="location.href='${pageContext.request.contextPath}/product/update.do?pNo=${vo.pNo}';">수정</span> | 
				<span class="btn-delete" data-pNo="${vo.pNo}">삭제</span>
				</td>
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