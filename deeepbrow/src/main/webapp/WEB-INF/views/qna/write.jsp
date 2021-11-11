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
.table input[type=text], .table textarea {
	width: 80%;
}
.table td:nth-child(2) {
	text-align: left;
}
</style>

<style type="text/css">
/* 모달대화상자 */
.ui-widget-header { /* 타이틀바 */
	background: none;
	border: none;
	border-bottom: 1px solid #ccc;
	border-radius: 0;
}
.ui-dialog .ui-dialog-title {
	padding-top: 5px; padding-bottom: 5px;
}
.ui-widget-content { /* 내용 */
   /* border: none; */
   border-color: #ccc; 
}
</style>


</head>
<body>

<header>
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>
<main>

<script type="text/javascript">
$(function(){
	$(".productPick").click(function() {
		$(".modal-dialog").dialog({
			modal:true,
			width:600,
			height:500
		});
	});
});

</script>
<script type="text/javascript">
function sendOk() {
    var f = document.noticeForm;
	var str;
	
    str = f.nSubject.value.trim();
    if(!str) {
        alert("제목을 입력하세요. ");
        f.nSubject.focus();
        return;
    }

    str = f.nContent.value.trim();
    if(!str) {
        alert("내용을 입력하세요. ");
        f.nContent.focus();
        return;
    }

    f.action = "${pageContext.request.contextPath}/notice/${mode}_ok.do";
    f.submit();
}
</script>

<div class="container">
	<div class="title-body">
			<span class="article-title">QnA</span>
	</div>
	<form name="noticeForm" method="post">
		<table class="table table-list">
			<tr>
				<td>제&nbsp;&nbsp;&nbsp;&nbsp;목</td>
				<td> 
					<input type="text" name="nSubject" maxlength="100" class="boxTF" value="${dto.qSubject}">
				</td>
			</tr>

			<tr> 
				<td>작성자</td>
				<td> 
					<p>${sessionScope.member.userName}</p>
				</td>
			</tr>
			
			<tr> 
				<td>문의 카테고리</td>
				<td> 
					<select name="question" class="selectField" >
						<option value="" selected="selected" hidden="hidden">질문유형을 선택해 주세요</option>
						<option value="product"      ${question=="product"?"selected='selected'":"" }>상품 문의</option>
						<option value="delivery"  ${question=="delivery"?"selected='selected'":"" }>배송 문의</option>
						<option value="change"  ${question=="change"?"selected='selected'":"" }>교환/반품/취소</option>
						<option value="etc"  ${question=="etc"?"selected='selected'":"" }>기타</option>
					</select>
				</td>
			</tr>
				
			<tr> 
				<td>상품 번호</td>
				<td> 
					<button class="btn productPick">상품 선택</button>
				</td>
			</tr>
				
			<tr> 
				<td>내&nbsp;&nbsp;&nbsp;&nbsp;용</td>
				<td> 
					<textarea name="nContent" class="boxTA" style="height: 200px;">${dto.qContent}</textarea>
				</td>
			</tr>
		</table>
		
		<table class="table">
			<tr> 
				<td align="center">
					<button type="button" class="btn" onclick="sendOk();">${mode=='update'?'수정완료':'등록하기'}</button>
					<button type="reset" class="btn">다시입력</button>
					<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/qna/list.do?rows=${rows}';">${mode=='update'?'수정취소':'등록취소'}</button>
					<input type="hidden" name="rows" value="${rows}">
					<c:if test="${mode=='update'}">
						<input type="hidden" name="nNo" value="${dto.nNo}">
						<input type="hidden" name="page" value="${page}">
					</c:if>
				</td>
			</tr>
		</table>
	</form>
</div>

<div class="modal-dialog" style="display: none;">
	<p>내용</p>
</div>

</main>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>

</body>
</html>