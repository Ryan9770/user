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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/paginate.css" type="text/css">
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
.table-list{
	border-top: 2px solid white;
    border-bottom: 2px solid white;
    margin-bottom: 50px;
}

.table-list tr{
	line-height: 30px;
	border-bottom: 1px solid white;
}
.table-list tr:first-child{
	line-height: 60px;
	font-weight: 600;
}
.no {
	width: 5%;
}
.product {
	width: 20%;
}
.question_ct{
	width: 10%;
}
.subject{
	width: 50%;
}
.name{
	width: 5%;
}
.date{
	width: 10%;
}
.table-list td:nth-child(2) {
	text-align: left;
	padding-left: 20px;
}
.table-list td:nth-child(4) {
	text-align: left;
	padding-left: 20px;
}
.btn{
	border: none;
    background: white;
    width: 80px;
    height: 30px;
    border-radius: 30px;
    cursor: pointer;
}
.btn:hover {
	background-color: #e6e6e6;
	border-color: #adadad;
	color:#333;
}
#condition{
	background: black;
	border: none;
	color: white;
}
#keyword{
	background: black;
	border: none;
	border-bottom: 1px solid white;
	color: white;
}
</style>
<script type="text/javascript">
function searchQna() {
	var f = document.searchQnaForm;
	f.submit();
}
</script>
</head>
<body>

<header>
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>

<main>
<div class="container">
	<div class="title-body">
		<span class="article-title">QnA</span>
	</div>
	<table class="table" style="text-align: left;">
		<tr>
		<td>
			ALL - ${dataCount} (${page}/${total_page} PAGE)
		</td>
		</tr>
	</table>
	<form name="noticelistForm">
		<table class="table table-list">
			<tr>
				<th class="no">No</th>
				<th class="product">PRODUCT</th>
				<th class="question_ct">CATEGORY</th>
				<th class="subject" colspan="2">SUBJECT</th>
				<th class="name">NAME</th>
				<th class="date">DATE</th>
			</tr>
			<c:forEach items="${list}" var="dto">
			<tr>
				<td>${dto.listNum}</td>
				<td>
					<img src="${pageContext.request.contextPath}/uploads/product/${dto.imageFilename}" width="30px" style="vertical-align: middle;"> ${dto.pName}
				</td>
				<td>
					<c:choose>
						<c:when test="${dto.qCategory=='product'}">상품문의</c:when>
						<c:when test="${dto.qCategory=='delivery'}">배송문의</c:when>
						<c:when test="${dto.qCategory=='change'}">교환/반품/취소</c:when>
						<c:when test="${dto.qCategory=='etc'}">기타문의</c:when>
					</c:choose>
				</td>
				<td>
					<a href="${articleUrl}&qNo=${dto.qNo}">${dto.qSubject}</a>
				</td>
				<td width="5%">
					<c:if test="${dto.replyCount!=0}"> <i class="far fa-check-circle"></i> </c:if>
					<c:if test="${dto.replyCount==0}"> <i class="far fa-circle"></i> </c:if>
				</td>
				<td>${dto.mId}</td>
				<td>${dto.qReg_date}</td>
			</tr>
			</c:forEach>
			
		</table>
	</form>
	<div class="page-box">
		${dataCount == 0 ? "등록된 게시물이 없습니다." : paging}
	</div>
			
	<table class="table">
			<tr>
				<td align="left">
					<form name="searchQnaForm" action="${pageContext.request.contextPath}/qna/list.do" method="post">
						<select name="condition" class="selectField">
							<option value="all"      ${condition=="all"?"selected='selected'":"" }>제목+내용</option>
							<option value="reg_date"  ${condition=="reg_date"?"selected='selected'":"" }>등록일</option>
							<option value="subject"  ${condition=="subject"?"selected='selected'":"" }>제목</option>
							<option value="content"  ${condition=="content"?"selected='selected'":"" }>내용</option>
						</select>
						<input type="text" name="keyword" value="${keyword}" class="boxTF">
						<input type="hidden" name="rows" value="${rows}">
						<button type="button" class="btn" onclick="searchQna();">검색</button>
					</form>
				</td>
				<td align="right" width="100">
					<c:if test="${sessionScope.member.userId != 'admin'}">
						<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/qna/write.do?rows=${rows}';">글올리기</button>
					</c:if>
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