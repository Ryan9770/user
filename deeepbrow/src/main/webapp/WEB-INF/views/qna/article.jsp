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
.article-td td {
	border-bottom: 1px solid white;
}
.left{
	text-align: left;
}
.text{
	padding-left: 30px;
}
.qm-form td:first-child {
	width: 90%;
}
</style>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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

function deleteQna() {
    if(confirm("게시글을 삭제 하시 겠습니까 ? ")) {
        var query = "qNo=${dto.qNo}&${query}";
        var url = "${pageContext.request.contextPath}/qna/delete.do?" + query;
    	location.href = url;
    }
}



$(function(){
	listPage(1);
});

function listPage(page) {
	var url = "${pageContext.request.contextPath}/qna/listQm.do";
	var query = "qNo=${dto.qNo}&pageNo="+page;
	var selector = "#listQm";
	
	var fn = function(data){
		$(selector).html(data);
	};
	ajaxFun(url, "get", query, "html", fn);
}
$(function(){
	$(".btnSendQm").click(function(){
		var qNo = "${dto.qNo}";
		var $tb = $(this).closest("table");
		var qmContent = $tb.find("textarea").val().trim();
		if(! qmContent) {
			$tb.find("textarea").focus();
			return false;
		}
		qmContent = encodeURIComponent(qmContent);
		
		var url = "${pageContext.request.contextPath}/qna/insertQm.do";
		var query = "qNo=" + qNo + "&qmContent=" + qmContent;
		
		var fn = function(data){
			$tb.find("textarea").val("");
			
			var state = data.state;
			if(state === "true") {
				listPage(1);
			} else if(state === "false") {
				alert("답변을 달지 못했습니다.");
			}
		};
		
		ajaxFun(url, "post", query, "json", fn);
	});
});

$(function(){
	$("body").on("click", ".deleteQm", function(){
		if(! confirm("답변을 삭제하시겠습니까 ? ")) {
		    return false;
		}
		
		var qmNo = $(this).attr("data-qmNo");
		var page = $(this).attr("data-pageNo");
		
		var url = "${pageContext.request.contextPath}/qna/deleteQm.do";
		var query = "qmNo="+qmNo;
		
		var fn = function(data){
			listPage(page);
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
		<span class="article-title">QnA</span>
	</div>
	<table class="table table-article article-td">
		<tr>
			<td class="left">
				<span class="text" style="font-size: 18px;">${dto.qSubject}</span> | ${dto.mId} | ${dto.qReg_date}
			</td>
		</tr>
		<tr>
			<td class="left">
				<span class="text" >문의 내용 |
					<c:choose>
						<c:when test="${dto.qCategory=='product'}">상품문의</c:when>
						<c:when test="${dto.qCategory=='delivery'}">배송문의</c:when>
						<c:when test="${dto.qCategory=='change'}">교환/반품/취소</c:when>
						<c:when test="${dto.qCategory=='etc'}">기타문의</c:when>
					</c:choose>
				</span>
			</td>
		</tr>
		<tr>
			<td class="left">
				<span class="text" >상품 정보 | ${dto.pName}</span>
			</td>
		</tr>
		<tr>
			<td height="400">
				${dto.qContent}
			</td>
		</tr>
	</table>
	<table class="table table-article">
		<tr>
			<td class="left">
			<span class="text">
				이전글 :
				<c:if test="${not empty preReadDto}">
					<a href="${pageContext.request.contextPath}/qna/article.do?${query}&qNo=${preReadDto.qNo}">${preReadDto.qSubject}</a>
				</c:if>
			</span>
			</td>
		</tr>
		<tr>
			<td class="left">
			<span class="text">
				다음글 :
				<c:if test="${not empty nextReadDto}">
					<a href="${pageContext.request.contextPath}/qna/article.do?${query}&qNo=${nextReadDto.qNo}">${nextReadDto.qSubject}</a>
				</c:if>
			</span>
			</td>
		</tr>
	</table>
	
	<table class="table">
			<tr>
				<td align="left">
					<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/qna/list.do?${query}';">리스트</button>
				</td>
				<td width="50%" align="right">
					<c:choose>
						<c:when test="${sessionScope.member.userId==dto.mId}">
							<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/qna/update.do?qNo=${dto.qNo}&page=${page}&rows=${rows}';">수정</button>
							<button type="button" class="btn" onclick="deleteQna();">삭제</button>
						</c:when>
						<c:otherwise>
							<button type="button" class="btn" disabled="disabled" style="display: none;">수정</button>
							<button type="button" class="btn" disabled="disabled" style="display: none;">삭제</button>
						</c:otherwise>
					</c:choose>
			    	
					<c:choose>
			    		<c:when test="${sessionScope.member.userId=='admin'}">
			    			<button type="button" class="btn" onclick="deleteQna();">삭제</button>
			    		</c:when>
			    		<c:otherwise>
			    			<button type="button" class="btn" disabled="disabled" style="display: none;">삭제</button>
			    		</c:otherwise>
			    	</c:choose>
				</td>
			</tr>
		</table>
		
		<c:if test="${sessionScope.member.userId=='admin'}">
			<div class="qnamanage">
				<form name="qmForm" method="post">
					<div class="title-body">
						<span class="article-title">ANSWER</span>
					</div>
					<table class="table qm-form">
						<tr>
							<td>
								<textarea name="content" style="width: 100%; height: 100px;"></textarea>
							</td>
						   <td align='right'>
						        <button type='button' class='btn btnSendQm'>답변 등록</button>
						    </td>
						</tr>
					</table>	
				</form>
			</div>
		</c:if>
				
		<div id="listQm" style="padding-top: 50px;">
		</div>
				
</div>
</main>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>


</body>
</html>