<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<title>DeeepBrow</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/layout.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">
<style type="text/css">

*{	padding: 0; margin: 0; box-sizing: border-box;}

.typeMember{
	margin-left: auto;
	margin-right: auto;
	border: 1px solid #d7d5d5;
}

.infomation{
	display: table;
    table-layout: fixed;
    padding: 10px 0;
    width: 100%;
    box-sizing: border-box;
}

.title{
	vertical-align: middle;
	display: table-cell;
    padding: 0 15px;
    width: 70px;
    text-align: center;
    font-weight: 700;
}

.description{
	display: table-cell;
    padding: 0 10px;
    width: auto;
    line-height: 1.5em;
    vertical-align: middle;
}

.description .mileage:after {
    content: "";
    display: block;
    clear: both;
}

div {
    display: block;
}

ul > li.mileage{
    display: block;
    list-style-type: disc;
    margin-block-start: 1em;
    margin-block-end: 1em;
    margin-inline-start: 0px;
    margin-inline-end: 0px;
    padding-inline-start: 40px;
}

li{
	list-style: none;
}

table{
	display : table;
	position: relative;
    margin: 10px 0 0;
    border-top: 1px solid #d7d5d5;
    border-right: 1px solid #FFF;
    border-left: 1px solid #FFF;
    border-bottom: 1px solid #d7d5d5;
    color: #fff;
    line-height: 1.5;
	width: 1500px;
    
    border-spacing: 0;
    border-collapse: collapse;
}

colgroup {
    display: table-column-group;
}

thead{
    display: table-header-group;
    vertical-align: middle;
    border-color: inherit;
}

tr {
    display: table-row;
    vertical-align: inherit;
    border-color: inherit;
}

.table th:first-child {
    border-left: 0;
}

input[type=radio], input[type=checkbox] {
    width: 13px;
    height: 13px;
    border: 0;
}

input, select, textarea {
    font-size: 100%;
    color: #fff;
    vertical-align: middle;
    border: 1px solid #dfdfdf;
}

table thead th {
    padding: 11px 0 10px;
    border-bottom: 1px solid #dfdfdf;
    color: #353535;
    vertical-align: middle;
    font-weight: normal;
    background: #fff;
    FONT-SIZE: 11PX;
}

th{
	display: table-cell;
	border: 0;
}

.table .right {
    text-align: right;
}

tfoot {
    display: table-footer-group;
    vertical-align: middle;
    border-color: inherit;
}

.table .center {
    text-align: center;
}

tbody {
    display: table-row-group;
    vertical-align: middle;
    border-color: inherit;
}

tr {
    display: table-row;
    vertical-align: inherit;
    border-color: inherit;
}

tbody td {
    border-color: #eee;
}

.table.typeList .center td, .table.typeList td.center {
    padding-left: 0;
    padding-right: 0;
}

.table.typeList td {
    padding: 15px 10px 14px;
}

.left {
    text-align: left;
}

.selectorder {
    margin: 0 0 40px;
}

.clearbutton {
    padding: 20px 0;
    text-align: center;
}

.clearbutton:after {
    display: block;
    content: "";
    clear: both;
}

.gLeft {
    padding: 20px 0;
    float: left;
    text-align: left;
}

.gRight {
    padding: 20px 0;
    float: right;
    text-align: right;
}

.basketDelete, .basketClear{
    display: inline-block;
    border: 1px solid #dfdfdf;
    padding: 2px 10px;
    transition: all 0.3s;
}
.total table {
    border-top: 1px solid #fff;
}

.total table th {
    height: 39px;
}

.total table td {
    height: 58px;
}

.table.typeList td {
    padding: 15px 10px 14px;
}

.gBorder tbody td {
    border-color: #eee;
}

.button .justify {
    position: relative;
}

.orderButton {
    margin: 10px 0 40px;
}

.button {
    padding: 20px 0;
    text-align: center;
}

.button:after {
    display: block;
    content: "";
    clear: both;
}

.basket_BuyAll .basket_Buyselect {
    display: inline-block;
}

.basket_BuyAll > a, .basket_Buyselect > a {

    padding: 8px 20px;
    display: block;
    border: 1px solid #fff;
    background: #000;
    color: #fff;
    transition: all 0.3s;
}
.qty {
    position: relative;
    display: inline-block;
    width: 50px;
    margin: 0 1px 0 0;
    text-align: left;
}

.qty input[type="text"] {
    width: 22px;
    height: 23px;
    padding: 0 0 0 5px;
    line-height: 25px;
    border: 1px solid #d4d8d9;
    border-radius: 0px 0 0 0px;
}

</style>
</head>
<body>

<header>
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>
<div class="container">
<div class="basket">
	<div class="typeMember">
		<div class="information">
            <h3 class="title">혜택정보</h3>
			<div class="description">
				<div class="member ">
					<p>{회원 이름 데이터}</p>
				</div>
                <ul class="mileage">
					<li>적립금 : <fmt:formatNumber value="" pattern="#,###"/>  원</li>
                </ul>
			</div>
		</div>
	</div>
	<div class="orderListArea typeList gBorder table">
        <table border="1" class="" style="width:1500px; ">
			<colgroup>
				<col style="width:27px">
				<col style="width:92px">
				<col style="width:auto">
				<col style="width:98px">
				<col style="width:75px">
				<col style="width:98px">
				<col style="width:98px">
				<col style="width:85px">
				<col style="width:98px">
			</colgroup>
			<thead>
				<tr>
					<th scope="col"><input type="checkbox" onclick=""></th>
                    <th scope="col">이미지</th>
                    <th scope="col">상품정보</th>
                    <th scope="col">판매가</th>
                    <th scope="col">수량</th>
                    <th scope="col" class="mileage">적립금</th>
                    <th scope="col">배송구분</th>
                    <th scope="col">배송비</th>
                    <th scope="col">합계</th> 
                </tr>
			</thead>
				<tfoot class="right">
					<tr>
						<td colspan="10">
							<span class="gLeft"></span> 
							상품구매금액 : <fmt:formatNumber value="" pattern="#,###"/>
							배송비 : <fmt:formatNumber value="" pattern="#,###"/>				 
							= 합계 : <span class="txt18"> <fmt:formatNumber value="" pattern="#,###"/> </span>
						</td>
                	</tr>
                </tfoot>
			<tbody class="center">
				<tr>
					<td>
						<input type="checkbox" id="" name="basket_product">
					</td>
                    <td class="thumbnail gClearLine">
                    	<a href="">
                    		<img src=""> <!-- 상품 사진 경로 -->
                    	</a>
                    </td>
                    <td class="center gClearLine">
                        <span class="name">
                        	<a href="" class="productName"> 상품명 </a>
                        </span>
					</td>
                    <td class="center">
                        <div class="price">
                        상품 가격
						</div>
                    </td>
                    <td>
						<span class="qty center">
							<input id="quantity" name="quantity" size="1" value="1" type="text">
							<a href="" onclick="" style="color: #fff;" class="up">▲</a><a href="" onclick="" style="color: #fff;" class="down">▼</a>
						</span>
                    </td>
                    <td>
                    	<div class="txtInfo">
                    	{배송구분}
                    	</div>
                    </td>
                    <td rowspan="1" class="">
						{배송료}
					</td>
                    <td class="center">
                   		{합계}
                    </td>
                </tr>
			</tbody>
		</table>
	</div>
	<div class="selectorder clearbutton ">
		<span class="gLeft">
            <a href="#" onclick="" class="basketDelete">체크 상품을 삭제</a>
        </span>
		<span class="gRight">
			<a href="#" onclick="" class="basketClear">장바구니 비우기</a>
        </span>
	</div>
	<div class="total">
		<table border="1">
			<colgroup>
				<col style="width:25%;">
				<col style="width:21%;">
				<col style="width:auto;">
			</colgroup>
			<thead>
				<tr>
					<th scope="col">총 상품금액</th>
					<th scope="col">총 배송비</th>
					<th scope="col">결제예정금액</th>
                </tr>
			</thead>
			<tbody class="center">
				<tr>
					<td>
						<div class="box txt16">
							<fmt:formatNumber value="" pattern="#,###"/> 
						</div>
					</td>
					<td>
                 		<div class="shippingfee">
							<span class="txt23"><fmt:formatNumber value="" pattern="#,###"/></span> <!-- 배송비 -->
						</div>
					</td>
					<td>
						<div>
							<span class="txt23"><fmt:formatNumber value="" pattern="#,###"/></span>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="button justify orderButton" style="width: 200px; margin: 0 auto;">
		<div class="basket_BuyAll" style="margin: 5px;">
			<a href="#" onclick="" class="">전체상품주문</a>
		</div>
		<div class="basket_Buyselect" style="margin: 5px;">
			<a href="#" onclick="" class="">선택상품주문</a>
		</div>
	</div>
</div>
</div>


<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>
</body>



</html>