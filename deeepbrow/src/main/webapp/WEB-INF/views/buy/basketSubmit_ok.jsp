<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<style type="text/css">
.content {
	display: inline-block;
	vertical-align: middle;
	margin-top: 100px;
	width: 80%;
}

.returnButton {
	width: 100px;
	height: 30px;
	border-radius: 6px;
	font-weight: bold;
	background: white;
}
</style>

<script type="text/javascript">
	function goMain() {
		var f = document.buyend;
		
		f.action = "${pageContext.request.contextPath}/main.do";
		f.submit();
		
		return;
	}
</script>
</head>
<body>

<header>
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>

<main>
<form action="" name="buyend" method="post">
<div class="content">
	<h3>장바구니 주문이 완료 되었습니다.</h3>
	<br>
	<button type="button" class="returnButton" onclick="goMain();">돌아가기</button>
</div>
</form>
</main>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>
</body>
</html>