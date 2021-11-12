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



<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/jquery/css/jquery-ui.min.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/jquery/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/jquery/js/jquery-ui.min.js"></script>

</head>
<body>

<header>
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>


<main>

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

$(function(){
	$(".productPick").click(function() {
		$(".popup-dialog").dialog({
			modal:true,
			width: 500,
			height: 500
		});
	});
});

$(function(){
	$(".modalSearch").click(function(){
		var keyword = $("#keyword").val().trim();
		if(! keyword) {
			$("#keyword").focus();
			return false;
		}
		keyword = encodeURIComponent(keyword);
		
		var url = "${pageContext.request.contextPath}/qna/search.do";
		var query = "keyword=" + keyword;
		
		var fn = function(data){
			$("#keyword").val("");
			
			var out = "";
			for(var idx=0; idx<data.list.length;idx++){
				var pNo = data.list[idx].pNo;
				var pName = data.list[idx].pName;
				
				out += "<span> 상품 번호 : "+pNo+"</span>&nbsp;&nbsp;";
				out += "<span> 상품 이름 : "+pName+"</span><br>";
				out += "<button type='button' class='btn'>선택</button>";
	
			}
			$(".searchList").append(out);
		};
		
		ajaxFun(url, "post", query, "json", fn);
	});
});
</script>
<script type="text/javascript">
function sendOk() {
    var f = document.noticeForm;
	var str;
	
    str = f.qSubject.value.trim();
    if(!str) {
        alert("제목을 입력하세요. ");
        f.qSubject.focus();
        return;
    }

    str = f.qContent.value.trim();
    if(!str) {
        alert("내용을 입력하세요. ");
        f.qContent.focus();
        return;
    }

    str = f.qCategory.value.trim();
    if(!str) {
        alert("문의내용을 선택하세요. ");
        f.qCategory.focus();
        return;
    }

    str = f.pNo.value.trim();
    if(!str) {
        alert("상품을 선택하세요. ");
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
					<input type="text" name="qSubject" maxlength="100" class="boxTF" value="${dto.qSubject}">
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
					<select name="qCategory" class="selectField" >
						<option value="" selected="selected" hidden="hidden">질문유형을 선택해 주세요</option>
						<option value="product"      ${qCategory=="product"?"selected='selected'":"" }>상품문의</option>
						<option value="delivery"  ${qCategory=="delivery"?"selected='selected'":"" }>배송문의</option>
						<option value="change"  ${qCategory=="change"?"selected='selected'":"" }>교환/반품/취소</option>
						<option value="etc"  ${qCategory=="etc"?"selected='selected'":"" }>기타문의</option>
					</select>
				</td>
			</tr>
				
			<tr> 
				<td>상품 번호</td>
				<td> 
					<button type="button" class="btn productPick">상품 선택</button>
				</td>
			</tr>
				
			<tr> 
				<td>내&nbsp;&nbsp;&nbsp;&nbsp;용</td>
				<td> 
					<textarea name="qContent" class="boxTA" style="height: 200px;">${dto.qContent}</textarea>
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
						<input type="hidden" name="qNo" value="${dto.qNo}">
						<input type="hidden" name="page" value="${page}">
					</c:if>
				</td>
			</tr>
		</table>
	</form>
</div>

<div class="popup-dialog" style="display: none;">
	<h3>상품 검색</h3>
	<div>
		<input type="text" id="keyword" class="boxTF">
		<button type="button" class="btn modalSearch">검색</button>
	</div>
	<div class="searchList">
	</div>
</div>

</main>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>

</body>
</html>