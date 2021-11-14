<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>spring</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/layout.css" type="text/css">
<style type="text/css">
.table-form td {
	padding: 7px 0;
}
.table-form p {
	line-height: 200%;
}
.table-form tr {
	border-bottom: 1px solid #fff;
}

.table-form tr > td:first-child {
	width: 200px; height:80px; text-align: center; background: none;
}
.table-form tr > td:nth-child(2) {
	padding-left: 10px;
	width: 500px;
}

.table-form .selectField{
	text-align: center;
	width: 100px;;
} 

.table-form input[type=text], .table-form input[type=file], .table-form textarea{
	width: 100%;
}

.table button{
	width: 100px;
	height: 50px;
	background: none;
	color: #fff;	
	border: none;
}

.table button:hover{
	cursor: pointer;
	border : 
	color: 
}

.img-box {
	max-width: 600px;
	padding: 5px;
	box-sizing: border-box;
	display: flex; /* 자손요소를 flexbox로 변경 */
	flex-direction: row; /* 정방향 수평나열 */
	flex-wrap: nowrap;
	overflow-x: auto;
}
.img-box img {
	width: 37px; height: 37px;
	margin-right: 5px;
	flex: 0 0 auto;
	cursor: pointer;
}

main{
	margin-bottom: 150px;
}

</style>
<script type="text/javascript">
function sendOk() {
    var f = document.addForm;
	var str;
	
	str = f.pCategory_code.value;
	if(!str){
		alert("상품의 카테고리를 입력해 주세요.")
		return;
	}
	
    str = f.pName.value.trim();
    if(!str) {
        alert("상품명을 입력하세요. ");
        f.pName.focus();
        return;
    }

    str = f.pPrice.value.trim();
    if(!str) {
        alert("상품가격을 입력하세요. ");
        f.pPrice.focus();
        return;
    }
    
    str = f.pStock.value.trim();
    if(!str) {
        alert("상품수량을 입력하세요. ");
        f.pStock.focus();
        return;
    }
    
    str = f.pDesc.value.trim();
    if(!str) {
        alert("상품설명을 입력하세요. ");
        f.pStock.focus();
        return;
    }
    
    var mode = "${mode}";
    if( (mode === "write") && (!f.selectFile.value) ) {
        alert("상품의 이미지 파일을 추가 하세요. ");
        f.selectFile.focus();
        return;
    }

    f.action = "${pageContext.request.contextPath}/product/${mode}_ok.do";
    f.submit();
}
<c:if test="${mode=='update'}">
function deleteFile(pNo) {
	if( !confirm("파일을 삭제하시겠습니까 ?") ) {
		return;
	}
	var url = "${pageContext.request.contextPath}/product/deleteImage.do?pNo=" + pNo + "&page=${page}";
	location.href = url;
}
</c:if>

</script>
</head>
<body>
<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>

<main>
	<div class="container" align="center">
	<form name="addForm" method="post" enctype="multipart/form-data">
		<table class="table table-border table-form">
			<tr>
				<td>카 테 고 리</td>
				<td>
				<c:choose>
					<c:when test="${mode=='write'}">
					<select name="pCategory_code">
						<option value="">선택</option>
					<c:forEach var="dto" items="${list}">
						<option value="${dto.pCategory_code}">${dto.pCategory_name}</option>
					</c:forEach>
					</select>
					</c:when>
					<c:otherwise>
					<select name="pCategory_code">
						<option value="${dto.pCategory_code}">${dto.pCategory_name}</option>
					</select>
					</c:otherwise>
				</c:choose>
					
				</td>
			</tr>
			
				<tr> 
					<td>상&nbsp;품&nbsp;명</td>
					<td> 
						<input type="text" name="pName" maxlength="100" class="boxTF" value="${dto.pName}">
					</td>
				</tr>
				
				<tr> 
					<td>상&nbsp;품&nbsp;가&nbsp;격</td>
					<td> 
						<p><input type="text" name="pPrice" maxlength="100" class="boxTF" value="${dto.pPrice}" pattern="\d*"></p>
					</td>
				</tr>
				<tr> 
					<td>수&nbsp;량</td>
					<td> 
						<p><input type="text" name="pStock" class="boxTA"  value="${dto.pStock}" pattern="\d*"></p>
					</td>
				</tr>
				<tr> 
					<td>상&nbsp;품&nbsp;설&nbsp;명</td>
					<td> 
						<textarea rows="5" name="pDesc" maxlength="500" class="boxTF" style="resize: none;"></textarea>
					</td>
				</tr>
				<tr> 
					<td>이미지</td>
					<td> 
						<input type="file" name="selectFile" accept="image/*" multiple="multiple" class="boxTF">
					</td>
				</tr>
				<c:if test="${mode=='update'}">
					<tr>
						<td>등록이미지</td>
						<td> 
							<div class="img-box">
								<c:forEach var="dto" items="${list}">
									<img src="${pageContext.request.contextPath}/uploads/product/${dto.image_name}"
										onclick="deleteFile('${dto.imageNo}');">
								</c:forEach>
							</div>
						</td>
					</tr>
				</c:if>
				
			</table>
				
			<table class="table">
				<tr> 
					<td >
						<button type="button" class="btn" onclick="sendOk();">${mode=='update'?'수정완료':'등록하기'}</button>
						<button type="reset" class="btn">다시입력</button>
						<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/product/list.do';">등록취소</button>
						<c:if test="${mode=='update'}">
							<input type="hidden" name="pNo" value="${dto.pNo}">
							<input type="hidden" name="image_name" value="${dto.image_name}">
							<input type="hidden" name="page" value="${page}">
						</c:if>
				</tr>
			</table>
	
		</form>
	</div>
</main>
<footer>
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>

</body>
</html>