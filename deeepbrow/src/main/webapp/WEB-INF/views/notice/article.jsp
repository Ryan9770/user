<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>deeepbrow</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<style type="text/css">
.title-body {
	padding: 50px 0; text-align: center;
}
.title-body .article-title {
	font-size: 20px;
}
.table{
	width: 80%;
	margin: 0 auto;
	border-spacing: 0;
	border-collapse: collapse;
}
.btn{
	border: none;
    background: white;
    width: 80px;
    height: 30px;
    border-radius: 30px;
    cursor: pointer;
    margin: 0 10px;
}
.btn:hover {
	background-color: #e6e6e6;
	border-color: #adadad;
	color:#333;
}

.table-article{
	border: 1px solid white;
	margin-bottom: 50px;
}
.left{
	text-align: left;
}
.text{
	padding-left: 30px;
}
</style>
<script type="text/javascript">
<c:if test="${sessionScope.member.userId=='admin'}">
function deleteNotice() {
    if(confirm("게시글을 삭제 하시 겠습니까 ? ")) {
        var query = "nNo=${dto.nNo}&${query}";
        var url = "${pageContext.request.contextPath}/notice/delete.do?" + query;
    	location.href = url;
    }
}
</c:if>
</script>
</head>
<body>

<header>
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>

<main>
<div class="container">
	<div class="title-body">
		<span class="article-title">공지사항</span>
	</div>
	<table class="table table-article">
		<tr>
			<td class="left">
				<span class="text" style="font-size: 18px;">${dto.nSubject}</span> | 관리자 | ${dto.nReg_date}
			</td>
		</tr>
		<tr>
			<td height="400">
				${dto.nContent}
			</td>
		</tr>
	</table>
	<table class="table table-article">
		<tr>
			<td class="left">
			<span class="text">
				이전글 :
				<c:if test="${not empty preReadDto}">
					<a href="${pageContext.request.contextPath}/notice/article.do?${query}&nNo=${preReadDto.nNo}">${preReadDto.nSubject}</a>
				</c:if>
			</span>
			</td>
		</tr>
		<tr>
			<td class="left">
			<span class="text">
				다음글 :
				<c:if test="${not empty nextReadDto}">
					<a href="${pageContext.request.contextPath}/notice/article.do?${query}&nNo=${nextReadDto.nNo}">${nextReadDto.nSubject}</a>
				</c:if>
			</span>
			</td>
		</tr>
	</table>
	
	<table class="table">
			<tr>
				<td align="left">
					<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/notice/list.do?${query}';">리스트</button>
				</td>
				<td width="50%" align="right">
					<c:choose>
						<c:when test="${sessionScope.member.userId=='admin'}">
							<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/notice/update.do?nNo=${dto.nNo}&page=${page}&rows=${rows}';">수정</button>
						</c:when>
						<c:otherwise>
							<button type="button" class="btn" disabled="disabled" style="display: none;">수정</button>
						</c:otherwise>
					</c:choose>
			    	
					<c:choose>
			    		<c:when test="${sessionScope.member.userId=='admin'}">
			    			<button type="button" class="btn" onclick="deleteNotice();">삭제</button>
			    		</c:when>
			    		<c:otherwise>
			    			<button type="button" class="btn" disabled="disabled" style="display: none;">삭제</button>
			    		</c:otherwise>
			    	</c:choose>
				</td>
			</tr>
		</table>
</div>
</main>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>


</body>
</html>