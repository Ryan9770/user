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
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<title>deeepbrow</title>
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
	<p>장바구니 추가 완료</p>
</div>
</div>


<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>
</body>



</html>