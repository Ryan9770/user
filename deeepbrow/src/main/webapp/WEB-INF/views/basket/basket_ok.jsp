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
		
		f.action = "${pageContext.request.contextPath}/basket/basket.do";
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
	<h3>장바구니 추가 완료</h3>
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