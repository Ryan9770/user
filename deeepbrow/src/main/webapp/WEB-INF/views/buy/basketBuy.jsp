<%@page import="com.member.SessionInfo"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	
	request.setCharacterEncoding("utf-8");
	
	//로그인 체크
	SessionInfo info = (SessionInfo) session.getAttribute("member");
	if (info == null) {
		response.sendRedirect("../member/login.do");
		return;
	}
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>deeepbrow</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<script type="text/javascript" src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://kit.fontawesome.com/42044ce0be.js" crossorigin="anonymous"></script>
<style type="text/css">

.content {
	display: inline-block;
	vertical-align: middle;
	margin-top: 100px;
	width: 80%;
}

.firsetBox {
	border: 1px solid;
    display: table;
    width: 100%;
    box-sizing: border-box;
    padding: 10px 0;
}

.firsetBox div:nth-child(1) {
	vertical-align: middle;
    display: table-cell;
}

.firsetBox div:nth-child(1) h3 {
	
}

.firsetBox div:nth-child(2) {
	display: table-cell;
    width: 85%;
    vertical-align: middle;
    line-height: 1.5em;
}

.memberbox {
	vertical-align: middle;
    display: table-cell;
    padding: 10px;
}

.cupon {
	border-top: 1px solid #e8e8e8;
	display: flex;
	padding: 10px;
	
}

.cupon li {
	text-decoration: none;
	list-style: none;
}

.secondtBox {
	margin-top: 30px;
    width: 100%;
    box-sizing: border-box;
    padding: 10px 0;
    display: block;
}

.secondtBox table {
	width: 100%;
	border-collapse: collapse;
	
}

.secondtBox table th, td {
	padding: 10px;
}

.secondtBox table thead tr {
	border-bottom: 1px solid;
	border-top: 1px solid;
}

.productimgSet {
	vertical-align: middle;
}

.productimg {
	max-width: 75px;
}

.secondtable tr {
	padding-top: 10px;
	border-bottom: 1px solid;
}

tfoot {
	border-bottom: 1px solid;
}

.deleteButton {
	clear: both;
	float: left;
	margin: 30px;
}

#asterisk {
	color: tomato;
	font-size: 10px;
}

.order {
	display: block;
	clear: both;
}

.ordertitle {
	margin-top: 50px;
	text-align: left;
}

.ordertitle h3 {
	display: inline-block;
	vertical-align: middle;
	font-size: 20px;
	font-weight: 700;
	margin-left: 10px;
}

.ordertitle p {
	text-align: right;
}

.ordertable {
	width: 100%;
	border-collapse: collapse;
	border-top: 1px solid darkslategray;
}
.ordertable td {
	text-align: left;
}

.ordertable tr {
	border-bottom: 1px solid darkslategray;
}

.ordertable th {
	border-right: 1px solid darkslategray;
}

.ordertable input[type="text"] {
	border-radius: 6px;
	height: 30px;
}

.ordertable textarea {
	border-radius: 6px;
}

.total {
	clear: both;
	display: block;
}

.total table {
	width: 100%;
	margin: auto;
	border-collapse: collapse;
}

.total thead {
	height: 70px;
	border-top: 1px solid;
	border-bottom: 1px solid;
}

.total tbody {
	height: 70px;
	border-top: 1px solid;
	border-bottom: 1px solid;
}

.total table th {
	padding: 0px 100px 0px 30px;
	
}

.total table td {
	padding: 0px 100px 0px 30px;
}

.total table thead th:nth-child(1) {
	width: 10%;
}

.total table thead th:nth-child(2) {
	width: auto;
}

.total table thead th:nth-child(3) {
	width: 20%;
}

.total table tbody td {
	font-size: 23px;
}

.total table tfoot tr {
	border-bottom: 1px solid;
}

.total table tfoot th {
	border-right: 1px solid;
	text-align: left;
	width: 20%;
	padding: 15px;
	
}

.total table tfoot td {
	text-align: left;
}

.total table tfoot td div {
	padding: 20px 0px 00px 0px;
}

.total table tfoot td ul {
	padding: 20px 0px 20px 0px;
}

.payinfo {
	display: flex;
	width: 100%;
	margin: 40px 0 10px 10px;
	text-align: left;
}

.payinfo h3 {
	width: 10%;
}

.pay {
	display: block;
	border: 1px solid;
}

.payfirstbox {
	float: left;
	width: 70%;
	height: 300px;
}

.payfirstbox span {
	padding-right: 15px;
}

.paysecondbox {
	border: 1px solid;
	float: right;
	width: 30%;
	height: 300px;
}

.paysecondbox h4 {
	text-align: right;
	padding: 20px;
}

.paysecondbox div:first-child p {
	font-size: 23px;
	text-align: right;
	padding-right: 20px;
	padding-bottom: 20px;
}

.paysecondButton {
	margin: 10px;
}

.paysecondButton button {
	width: 280px;
	height: 50px;
	background-color: white;
	font-size: 17px;
	font-weight: 700;
}

.pointSave {
	border: 1px solid;
	height: 30px;
	line-height: 25px;
	
}

.paysecondbox div ul {
	list-style: none;
	padding-top: 10px;
	line-height: 25px;
}

.info1 {
	clear: both;
	border: 1px solid;
	width: 100%;
	height: 200px;
	margin-top: 350px;
}

.info1 h3 {
	padding: 30px 0px 30px 30px;
	text-align: left;
}

.info1 div {
	padding: 10px 0px 30px 30px;
	line-height: 30px;
}

.info1 ul {
	list-style: none;
	text-align: left;
}

.info1 ul li {
	color: gray;
}

.info2 {
	border: 1px solid;
	width: 100%;
	min-height: 200px;
	margin-top: 50px;
}

.info2 h3 {
	padding: 30px 0px 30px 30px;
	text-align: left;
}

.info2 h4 {
	font-weight: 300;
	text-align: left;
}

.info2 div {
	padding: 10px 0px 30px 30px;
	line-height: 30px;
}

.info2 ul {
	list-style: none;
	text-align: left;
}

.info2 ul li {
	color: gray;
}

.microHome:hover {
	color: gold;
}

.zipButton {
	width: 77px;
	height: 30px;
	text-align: center;
	line-height: 28px;
	border-radius: 6px;
	font-weight: bold;
	background: white;
}

.addressButton {
	width: 90px;
	height: 30px;
	text-align: center;
	line-height: 28px;
	border-radius: 6px;
	font-weight: bold;
	background: white;
}

#PayBtn {
	border-radius: 6px;
}
</style>

<script src="https://nsp.pay.naver.com/sdk/js/naverpay.min.js"></script>
<script type="text/javascript">

function naverPay(){
	var oPay = Naver.Pay.create({
	    "mode" : "production", // development or production
	    "clientId": "u86j4ripEt8LRfPGzQ8" // clientId
	});
	
	//직접 만드신 네이버페이 결제버튼에 click Event를 할당하세요
	var elNaverPayBtn = document.getElementById("PayBtn");
	
	elNaverPayBtn.addEventListener("click", function() {
	  oPay.open({
	    "merchantUserKey": "deeepbrowKey2",
	    "merchantPayKey": "deeepbrowPayKey2",
	    "productName": "deeepbrow",
	    "totalPayAmount": "100",
	    "taxScopeAmount": "100",
	    "taxExScopeAmount": "0",
	    "returnUrl": "${pageContext.request.contextPath}/main.do"
	  });
	});
}

function cardPay() {
	var f = document.buyForm;
	
	var str;
	
	str1 = f.phone1.value;
	if( ! /^[0-9]{4}$/.test(str1) ) {
		alert("휴대폰 번호는 4자리만 가능합니다");
		f.phone1.focus();
		return;
	}
	
	str2 = f.phone2.value;
	if( ! /^[0-9]{4}$/.test(str2) ) {
		alert("휴대폰 번호는 4자리만 가능합니다");
		f.phone2.focus();
		return;
	}
	
	if(str1 == str2) {
		alert("휴대폰 번호는 같을 수 없습니다.");
		return;
	}
	
	f.action = "${pageContext.request.contextPath}/buy/basketSubmit_ok.do";
	f.submit();
	
	return;
}

function accountPay() {
	var f = document.buyForm;
	var str;
	
	str = f.phone1.value;
	if( /[0-9]{4}$/.test(str) ) {
		alert("휴대폰 번호는 4자리만 가능합니다");
		f.phone1.focus();
		return;
	}
	
	str = f.phone2.value;
	if( /[0-9]{4}$/.test(str) ) {
		alert("휴대폰 번호는 4자리만 가능합니다");
		f.phone2.focus();
		return;
	}
	
	
	f.action = "${pageContext.request.contextPath}/buy/basketSubmit_ok.do";
	f.submit();
	return;
}

function microHome() {
	var win = window.open("https://www.microsoft.com/ko-kr", '_blank');
    win.focus();
    return;
}



</script>

<script type="text/javascript">
$(function(){
	totalPrice();
	
	// 결제 방식 radio a 태그 함수
	$("#naverPayClick").click(function(){
		$(this).children().prop("checked", true);
		$("#PayBtn").attr("onclick", "naverPay();")
		$(".payfirstbox div:nth-child(2) div:nth-child(1)").css("display", "none");
		$(".payfirstbox div:nth-child(2) div:nth-child(2)").css("display", "block");
		$(".payfirstbox div:nth-child(2) div:nth-child(3)").css("display", "none");
	});
	
	$("#cardPayClick").click(function(){
		$(this).children().prop("checked", true);
		$("#PayBtn").attr("onclick", "cardPay();")
		$(".payfirstbox div:nth-child(2) div:nth-child(1)").css("display", "block");
		$(".payfirstbox div:nth-child(2) div:nth-child(2)").css("display", "none");
		$(".payfirstbox div:nth-child(2) div:nth-child(3)").css("display", "none");
	});
	
	$("#accountPayClick").click(function(){
		$(this).children().prop("checked", true);
		$("#PayBtn").attr("onclick", "accountPay();")
		$(".payfirstbox div:nth-child(2) div:nth-child(1)").css("display", "none");
		$(".payfirstbox div:nth-child(2) div:nth-child(2)").css("display", "none");
		$(".payfirstbox div:nth-child(2) div:nth-child(3)").css("display", "block");
		$(".payfirstbox div:nth-child(2) div:nth-child(3)").children().css("display", "block");
	});
	
	$("#chkAll").click(function(){ // 상품 리스트 체크박스 전부 체크
		$("input[name=nums]").prop("checked", $(this).is(":checked"));
	});
	
	$("#btnDeleteList").click(function(){ // 상품들 체크 박스로 삭제
		var cnt = $("input[name=nums]:checked").length;
		if(cnt == 0) {
			alert("삭제할 상품 목록을 선택 해주세요");
			return false;
		}
		
		var n = $("input[name=nums]:checked").map(function(){
			return $(this).val();
		}).get();
		console.log(n);
		
		if(n >= $("input[name=nums]").length) {
			alert("모든 상품을 삭제할순 없습니다.");
		}
		
		$("#basket_product").parent().parent().remove(); // 장바구니 리스트 하나 삭제
		
	});
	
	$("#emailSelect").change(function(){
		var v = $(this).val();
		$("#emailSelectText").val(v);
		
		if($("#emailSelectText").val() == "") {
			$("#emailSelectText").attr("readonly", false);
		} else {
			$("#emailSelectText").attr("readonly", true);
		}
		
		return;

	});
	
	$("#phonNum").change(function(){
		var v = $("#phonNum option:selected").val();
		$("#phonNumHidden").val(v);
	});

	
});

function totalPrice() {
	var total = 0;
	$("div .amt").each(function(){
		total += parseInt($(this).attr("data-price"));
		
	});
	
	$(".totalPrice").text(total + "원");
	$("#totalPrice").val(total);
}
</script>
</head>
<body>

<header>
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>

<main>
	<div class="container">
		<div class="content">
			<form action="" name="buyForm" method="post">
				<div class="firsetBox">
					<div>
						<h3>혜택 정보</h3>
					</div>
					
					<div>
						<div class="memberbox">
							<p>${sessionScope.member.userId }님은 [일반회원] 입니다.</p>
						</div>
						<ul class="cupon">
							<li>
								<a href="#">사용가능 적립금 : 9999999원</a>
							</li>
							<li>
								<p>&nbsp;&nbsp;/&nbsp;&nbsp;</p>
							</li>
							<li>
								<a href="#">쿠폰 : 90개</a>
							</li>
						</ul>
					</div>
					<div>
						<p>&nbsp;&nbsp;</p>
					</div>
				</div>
				<!-- 상단 혜택정보 -->

				<div class="secondtBox">
					<div>
						<table>
							<thead>
								<tr>
									<th>
										<input type="checkbox" id="chkAll" name="chkAll">
									</th>
									<th>이미지</th>
									<th>상품정보</th>
									<th>판매가</th>
									<th>수량</th>
									<th>적립금</th>
									<th>배송구분</th>
									<th>배송비</th>
									<th>합계</th>
								</tr>
							</thead>
							<tbody class="secondtable">
								<c:forEach var="dto" items="${list }">
									<tr>
										<td>
											<input type="checkbox" id="basket_product" name="nums" value="">
										</td>
										<td>
											<a href="#" class="productimgSet">
												<img class="productimg" alt="product img" src="${pageContext.request.contextPath}/uploads/product/${dto.img}">
											</a>
										</td>
										<td>
											<strong>
												<a href="#" id="pName">${dto.pname }</a>
												<input name="pNo" type="hidden" value="${dto.pNum }" data-pno="${dto.pNum }">
											</strong>
											<div>
												[옵션: 없음]
											</div>
										</td>
										<td>
											<div>
												<fmt:formatNumber value="${dto.onePrice }" type="number"/>
												<input type="hidden" name="onePrice" value="${dto.onePrice }">
												원
											</div>
										</td>
										<td>${dto.quantity }개
											<input type="hidden" name="quantity" value="${dto.quantity }">
										</td>
										<td>
											<span>2,000원</span>
										</td>
										<td>
											<div>
												기본배송
											</div>
										</td>
										<td>
											[무료]
										</td>
										<td>
											<div class="amt" data-price="${dto.price }">
												<fmt:formatNumber value="${dto.price }" type="number"/>
												원
											</div>
										</td>
									</tr>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="8">
										<input type="checkbox" style="display: none;">
									</td>
									<td>
										<span>[기본배송]</span>
										<span>상품 구매 금액 : <fmt:formatNumber value="${dto.price }" type="number"/></span>
										<span> + 배송비 : 0 (무료)</span>
										<span> = 합계 : </span>
										<span><fmt:formatNumber value="${dto.price }" type="number"/></span>
									</td>
								</tr>
							</tfoot>
						</table>
					</div>
				</div>
			<!-- 상품 정보 확인 -->
			
				<div style="float: left;">
					<ul>
						<li style="list-style: none; color: tomato; margin-left: 10px;">
							<i class="fas fa-exclamation"></i>
							상품의 옵션 및 수량 변경은 상품상세 또는 장바구니에서 가능합니다.
						</li>
					</ul>
				</div>
				
				<div class="deleteButton">
				<a href="#" id="btnDeleteList">
					<i class="fas fa-minus-square"></i>
					<span>선택한 상품을 삭제 합니다.</span>
					</a>
				</div>
				<!-- 경고문과 상품 삭제 영역 -->
				
				<div class="order">
					<div class="ordertitle">
						<h3>배송 정보</h3>
						<p>
							<i class="fas fa-asterisk" id="asterisk"></i>
							필수입력사항
						</p>
					</div>
					
					<div>
						<table class="ordertable">
							<tr>
								<th>배송지 선택</th>
								<td>
									<div>
										<input type="radio" name="selectShipment" checked="checked">
										<label>회원 정보와 동일</label>
										<input type="radio" name="selectShipment">
										<label>새로운 배송지</label>
										<a>
											<button type="button" class="addressButton">주소록 보기</button>
										</a>
									</div>
								</td>
							</tr>
							<tr>
								<th>받으시는 분 <i class="fas fa-asterisk" id="asterisk"></i></th>
								<td>
									<input type="text" value="${sessionScope.member.userName }">
								</td>
							</tr>
							<tr>
								<th>주소 <i class="fas fa-asterisk" id="asterisk"></i></th>
								<td>
									<input type="text" readonly="readonly" style="width: 100px;" id="zip" name="zip" value="${dto2.mZipcode}">
									<a>
										<button type="button" class="zipButton" onclick="daumPostcode();">우편번호</button>
									</a>
									<br><br>
									<input type="text" readonly="readonly" style="width: 500px;" id="addr1" name="addr1" value="${dto2.mAddr1}">
									<span>기본 주소</span>
									<br><br>
									<input type="text" style="width: 500px;" id="addr2" name="addr2" value="${dto2.mAddr2}">
									<span>나머지 주소</span>
								</td>
							</tr>
							<tr>
								<th>일반 전화</th>
								<td>
									<select>
										<option>02</option>
										<option>032</option>
									</select>
									-
									<input type="text">
									-
									<input type="text">
								</td>
							</tr>
							<tr>
								<th>휴대 전화 <i class="fas fa-asterisk" id="asterisk"></i></th>
								<td>
									<select id="phonNum">
										<option value="010">010</option>
										<option value="011">011</option>
									</select>
									<input type="hidden" id="phonNumHidden" name="phonNum" value="010">
									-
									<input type="text" name="phone1">
									-
									<input type="text" name="phone2">
								</td>
							</tr>
							<tr>
								<th>이메일 <i class="fas fa-asterisk" id="asterisk"></i></th>
								<td>
									<input type="text" name="email1">
									@
									<input type="text" id="emailSelectText" name="email2" readonly="readonly" value="naver.com">
									<select name="emailSelect" id="emailSelect">
										<option value="naver.com">naver.com</option>
										<option value="daum.net">daum.net</option>
										<option value="google.com">google.com</option>
										<option value="">직접 입력</option>
									</select>
									<p>
										이메일을 통해 주문처리과정을 보내드립니다.
										<br>
										이메일 주소란에는 반드시 수신가능한 이메일주소를 입력해 주세요.
									</p>
								</td>
							</tr>
							<tr>
								<th>배송 메시지</th>
								<td>
									<textarea rows="5" cols="70" name="memo"></textarea>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<!-- 배송 주문 창 -->
				
				<div style="margin: 40px 0 80px 10px; display: block;">
					<h3 style="float: left;">결제 예정 금액</h3>
				</div>
				
				<div class="total">
					<table>
						<thead>
							<tr>
								<th>총 주문 금액</th>
								<th>총 할인 + 부가결제 금액</th>
								<th>총 결제예정 금액</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>
									<div>
										<span class="totalPrice"></span>
										<input type="hidden" name="totalPrice" id="totalPrice" value="0">
										
									</div>
								</td>
								<td>
									<div>
										0
									</div>
								</td>
								<td>
									<div>
										= <span class="totalPrice"></span>
										<input type="hidden" name="wholePrice" value="">
									</div>
								</td>
							</tr>
						</tbody>
						<tfoot>
							<tr>
								<th>총 할인금액</th>
								<td>0</td>
							</tr>
							<tr>
								<th>총 부가결제금액</th>
								<td>0</td>
							</tr>
							<tr>
								<th>적립금</th>
								<td>
									<div>
										<input type="text">원 (총 사용가능 적립금 : 2,000원)
									</div>
									<ul>
										<li>적립금은 최소 1,000 이상일 때 결제가 가능합니다.</li>
										<li>최대 사용금액은 제한이 없습니다.</li>
										<li>1회 구매시 적립금 최대 사용금액은 2,000원입니다.</li>
										<li>적립금으로만 결제할 경우, 결제금액이 0으로 보여지는 것은 정상이며 [결제하기] 버튼을 누르면 주문이 완료됩니다.</li>
									</ul>
								</td>
							</tr>
						</tfoot>
					</table>
				</div>
				<!-- 결제 예정 금액, 적립금 -->
				
					<div class="payinfo">
						<h3>결제 수단</h3>
						<span>
							<input type="checkbox">
							<label>결제수단과 입력정보를 다음에도 사용</label>
						</span>
					</div>
					<!-- 결제 수단, 체크박스 -->
					
					<div class="pay">
						<div class="payfirstbox">
							<div style="border: 1px solid; height: 20%; line-height: 55px;">
								<span>
									<a id="naverPayClick">
										<input type="radio" checked="checked" name="payMethod"> 네이버 페이
									</a>
								</span>
								<span>
									<a id="cardPayClick">
										<input type="radio" name="payMethod"> 카드 결제
									</a>
								</span>
								<span>
									<a id="accountPayClick">
										<input type="radio" name="payMethod"> 무통장 입금
									</a>
								</span>
							</div>
							<div style="border: 1px solid; height: 80%;">
								<div style="line-height: 50px; padding-top: 50px; display: none;">
									<p>최소 결제 가능 금액은 결제금액에서 배송비를 제외한 금액입니다.</p>
									<p>소액 결제의 경우 PG사 정책에 따라 결제 금액 제한이 있을 수 있습니다.</p>
								</div>
								<div style="line-height: 60px; padding-top: 50px;">
									<p>네이버 페이 결제 입니다.</p>
									<p>결제창이 안나올시 결제 버튼을 한번 더 눌러주세요.</p>
								</div>
								<div style="display: none;">
									<div>
										<table style="padding: 30px 0px 30px 0px; margin: auto;">
											<tr>
												<th>입금자명</th>
												<td>
													<input type="text">
												</td>
											</tr>
											<tr>
												<th>입금은행</th>
												<td>
													<p>카카오뱅크 : 3333-03 / 예금주 : 딥브로우</p>
												</td>
											</tr>
										</table>
									</div>
									<div>
										<label style="padding-right: 10px;">현금 영수증 신청 &nbsp;
											<input type="radio" name="recipt" style="padding: 10px;">
										</label>
										
										<label style="padding-right: 10px;">신청 안함 &nbsp;
											<input type="radio" name="recipt" checked="checked">
										</label>
									</div>
								</div>
							</div>
						</div>
						<div class="paysecondbox">
							<div>
								<h4>최종 결제 금액</h4>
								<p><span class="totalPrice"></span></p>
								<div class="paysecondButton"><a><button type="button" id="PayBtn" onclick="naverPay();">결제하기</button></a></div>
								<div class="pointSave">
									<div>총 적립 예정 금액 : 2100원</div>
								</div>
								<div>
									<ul>
										<li>상품별 적립금 : 2100원</li>
										<li>회원 적립금 : 0원</li>
										<li>쿠폰 적립금 : 0원</li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				<!-- 결제창 -->
				
				<div class="info1">
					<h3>무이자 할부 이용안내</h3>
					<div>
						<ul>
							<li>무이자할부가 적용되지 않은 상품과 무이자할부가 가능한 상품을 동시에 구매할 경우 전체 주문 상품 금액에 대해 무이자할부가 적용되지 않습니다.</li>
							<li>무이자할부를 원하시는 경우 장바구니에서 무이자할부 상품만 선택하여 주문하여 주시기 바랍니다.</li>
						</ul>
					</div>
				</div>
				<!-- 무이자 할부 안내 -->
				
				<div class="info2">
					<h3>이용안내</h3>
					<div>
						<h4>WindowXP 서비스팩2를 설치하신후 결제가 정상적인 단계로 처리되지 않는경우, 아래의 절차에 따라 해결하시기 바랍니다.</h4>
						<br>
						<h4>안심클릭 결제모듈이 설치되지 않은 경우 ActiveX 수동설치</h4>
						<h4 style="cursor: pointer;" onclick="microHome();" class="microHome">Service Pack 2에 대한 Microsoft사의 상세안내</h4>
						<br>
						<h4>아래의 쇼핑몰일 경우에는 모든 브라우저 사용이 가능합니다.</h4>
						<br>
						<ul>
							<li>KG이니시스, KCP, LG U+를 사용하는 쇼핑몰일 경우</li>
							<li>결제가능브라우저 : 크롬,파이어폭스,사파리,오페라 브라우저에서 결제 가능</li>
							<li>(단, window os 사용자에 한하며 리눅스/mac os 사용자는 사용불가)</li>
							<li>최초 결제 시도시에는 플러그인을 추가 설치 후 반드시 브라우저 종료 후 재시작해야만 결제가 가능합니다.</li>
							<li>(무통장, 휴대폰결제 포함)</li>
						</ul>
						<br>
						<h4>세금계산서 발행 안내</h4>
						<br>
						<ul>
							<li>부가가치세법 제 54조에 의거하여 세금계산서는 배송완료일로부터 다음달 10일까지만 요청하실 수 있습니다.</li>
							<li>세금계산서는 사업자만 신청하실 수 있습니다.</li>
							<li>배송이 완료된 주문에 한하여 세금계산서 발행신청이 가능합니다.</li>
							<li>[세금계산서 신청]버튼을 눌러 세금계산서 신청양식을 작성한 후 팩스로 사업자등록증사본을 보내셔야 세금계산서 발생이 가능합니다.</li>
							<li>[세금계산서 인쇄]버튼을 누르면 발행된 세금계산서를 인쇄하실 수 있습니다.</li>
						</ul>
						<br>
						<h4>부가가치세법 변경에 따른 신용카드매출전표 및 세금계산서 변경안내</h4>
						<br>
						<ul>
							<li>변경된 부가가치세법에 의거, 2004.7.1 이후 신용카드로 결제하신 주문에 대해서는 세금계산서 발행이 불가하며</li>
							<li>신용카드매출전표로 부가가치세 신고를 하셔야 합니다.(부가가치세법 시행령 57조)</li>
							<li>상기 부가가치세법 변경내용에 따라 신용카드 이외의 결제건에 대해서만 세금계산서 발행이 가능함을 양지하여 주시기 바랍니다.</li>
						</ul>
						<br>
						<h4>현금영수증 이용안내</h4>
						<ul>
							<li>현금영수증은 1원 이상의 현금성거래(무통장입금, 실시간계좌이체, 에스크로, 예치금)에 대해 발행이 됩니다.</li>
							<li>현금영수증 발행 금액에는 배송비는 포함되고, 적립금사용액은 포함되지 않습니다.</li>
							<li>발행신청 기간제한 현금영수증은 입금확인일로 부터 48시간안에 발행을 해야 합니다.</li>
							<li>현금영수증 발행 취소의 경우는 시간 제한이 없습니다. (국세청의 정책에 따라 변경 될 수 있습니다.)</li>
							<li>현금영수증이나 세금계산서 중 하나만 발행 가능 합니다.</li>
						</ul>
					</div>
				</div>
				</form>
			</div>
			<!-- 이용안내 -->
		</div>	
</main>

<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
    function daumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var fullAddr = ''; // 최종 주소 변수
                var extraAddr = ''; // 조합형 주소 변수

                // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    fullAddr = data.roadAddress;

                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    fullAddr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
                if(data.userSelectedType === 'R'){
                    //법정동명이 있을 경우 추가한다.
                    if(data.bname !== ''){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있을 경우 추가한다.
                    if(data.buildingName !== ''){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                    fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('zip').value = data.zonecode; //5자리 새우편번호 사용
                document.getElementById('addr1').value = fullAddr;

                // 커서를 상세주소 필드로 이동한다.
                document.getElementById('addr2').focus();
            }
        }).open();
    }
</script>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>
</body>
</html>