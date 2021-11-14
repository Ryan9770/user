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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/paginate.css" type="text/css">
<style type="text/css">
.title-body {
	padding: 50px 0; text-align: center;
}

.title-body .article-title {
	font-size: 20px; cursor: pointer;
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


.box{
	width: 700px;
	margin: 30px auto;
}

.table-stock {
	width: 80%;
	margin: 0 auto;
	border-spacing: 0;
	border-collapse: collapse;
	border-top: 2px solid white;
    border-bottom: 2px solid white;
    margin-bottom: 50px;
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
		var msg = "주문을 취소하시겠습니까? ";
		if(! confirm( msg )) {
			return false;
		}
		
		var url = "${pageContext.request.contextPath}/buy/delete.do";
		var orderNo = $(this).attr("data-orderNo");
		
		var query = "orderNo=" + orderNo;

		var fn = function(data) {
			var state = data.state;
			if(state === "true") {
				$tr.remove();
			} else if(state === "false") {
				alert("배송 중인 주문은 취소하실수 없습니다.");
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
		<span class="article-title">${sessionScope.member.userName}님의 주문내역</span>
	</div>
	<table class="table-stock paginated">
		<thead style="clear: both;">
			
			<tr>
				<th width="100" class="stockclass">주문 번호</th>
				<th width="150" class="stockclass">주문 일자</th>
				<th width="100" class="stockclass">주문 총금액</th>
				<th width="50" class="stockclass">배송료</th>
				<th width="100" class="stockclass">결제금액</th>
				<th width="150" class="stockclass">거래 완료 일자</th>
				<th width="100" class="stockclass">주문취소</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="vo" items="${list}">
			<tr class="row_tr">
				<td>${vo.orderno}</td>
				<td>${vo.order_date}</td>
				<td>${vo.whole_price}</td>
				<td>${vo.shipping_fee}</td>
				<td>${vo.pay_price}</td>
				<td>${vo.pay_date}</td>
				<td>
					<span class="btn-delete" data-orderNo="${vo.orderno}">주문취소</span>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="page-box">
		${dataCount == 0 ? "구매내역이 없습니다.": paging}
	</div>
</div>
</main>
<footer>
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>

</body>
</html>