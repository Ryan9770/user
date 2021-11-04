<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>deeepbrow</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<style type="text/css">

main {
	box-sizing: border-box;
	min-height: 100vh;
}

#content {
	padding: 90px;
	line-height: 25px;
}

table {
	margin: 0px auto;
	border-top: 1px solid white;
	border-collapse: collapse
}

.tablepadd {
	border-bottom: 1px solid white;
}

.tablepadd2 {
	border-bottom: 1px solid gray;
}

.tablepadd th:nth-child(1) {
	padding-left: 20px;
}

.tablepadd th:nth-child(2) {
	padding-left: 350px;
	padding-right: 200px;
}

.tablepadd th:nth-child(3) {
	padding-left: 100px;
}

.tablepadd th:nth-child(4) {
	padding-left: 50px;
	padding-right: 20px;
}

.tablepadd2 td:nth-child(1) {
	padding-left: 20px;
}

.tablepadd2 td:nth-child(2) {
	padding-left: 350px;
	padding-right: 200px;
}

.tablepadd2 td:nth-child(3) {
	padding-left: 100px;
}

.tablepadd2 td:nth-child(4) {
	padding-left: 50px;
	padding-right: 20px;
}

.textstyle {
	border: 0 solid black;
	background-color: black;
	color: white;
	width: 100px;
}

input::placeholder {
  color: white;
}

select {
	border: none;
	background-color: black;
	color: white;
}

.second {
	width: 51%;
	padding-top: 50px;
}

.second div {
	
}

.third {
	padding-top: 100px;
	margin-right: 100px;
}
</style>
</head>
<body>

<header>
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>

<main>
<div class="container">
	<div id="content">
		<table class="table">
				<thead class="">
					<tr class="tablepadd">
						<th> No</th>
						<th>SUBJECT</th>
						<th>NAME</th>
						<th>DATE</th>
					</tr>
				</thead>
			<tbody class="test">
				<tr class="tablepadd2">
					<td>공지</td>
					<td>[필독] Notice</td>
					<td>딥브로우</td>
					<td>2021-11-02</td>
				</tr>
				
				<tr class="tablepadd2">
					<td>7</td>
					<td>[이벤트] 여름 휴가</td>
					<td>딥브로우</td>
					<td>2021-11-02</td>
				</tr>
			</tbody>
		</table>
		
		<div style="min-height: 50px;">
			<form action="">
				<div class="second">
						<div>
						<select>
							<option>전체</option>
						</select>
						<select>
							<option>제목 + 내용</option>
						</select>
						<input class="textstyle" type="text" placeholder=" &nbsp;&nbsp;&nbsp;&nbsp;내용 입력">
						<a style="font-weight: 50px;">SEARCH</a>
					</div>
				</div>
				<div class="third">
					<a href="#"><i>Previus</i></a>
					&nbsp;
					<a href="#">1</a>
					&nbsp;
					<a href="#"><i>Next</i></a>
				</div>
			</form>
		</div>
	</div>
</div>

</main>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>


</body>
</html>