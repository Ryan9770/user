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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/layout.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/paginate.css" type="text/css">
<style type="text/css">
.table{
	width: 80%;
	margin: 0 auto;
	border-spacing: 0;
	border-collapse: collapse;
}

.title-body {
	padding: 50px 0; text-align: center;
}

.title-body .table-title {
	font-size: 25px;
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


a {
  text-decoration: none;
  cursor: pointer;
}

</style>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css">

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript">

function deleteMember(mId) {
	if(confirm("해당 회원을 삭제하시겠습니까?")) {
		var query = "mId="+mId;
		var url = "${pageContext.request.contextPath}/member/delete.do?" + query;
	    location.href = url;
	   }
	
function searchList() {
		var f = document.searchForm;
		f.submit();
	}
}

</script>
</head>

<header>
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>


<main>
<div class="container">
	<div class="title-body">
		<span class="table-title">회원 관리</span>
	</div>
	
	<table class="table table-list" style="text-align: center;">
		<tr>
			<th width="120" class="stockclass">No</th>			
			<th width="180" class="stockclass">이름</th>
			<th width="180" class="stockclass">아이디</th>				
			<th width="250" class="stockclass">이메일</th>	
			<th width="250" class="stockclass">전화번호</th>	
			<th width="250" class="stockclass">회원가입일</th>	
			<th width="120" class="stockclass">삭제</th>	
		</tr>
		
		<c:forEach var="dto" items="${list}">
			<tr>
				<td>${dto.listNum}</td>
				<td>${dto.mName}</td>
				<td>${dto.mId}</td>
				<td>${dto.mEmail}</td>
				<td>${dto.mTel}</td>
				<td>${dto.mReg_date}</td>
				<!-- <td><button type="button" class="btn" onclick="deleteMember();"><img src="${pageContext.request.contextPath}/resource/images/cancel.png" style="width: 20px"></button></td> -->				
				<td>
					<a href="javascript:deleteMember('${dto.mId}');">
								<img src="${pageContext.request.contextPath}/resource/images/cancel.png" style="width: 20px">
							</a>
				</td>
			</tr>
		</c:forEach>
	</table>

	<div class="page-box">
			${dataCount == 0 ? "등록된 게시물이 없습니다." : paging}
	</div>
		
	<table class="table">
			<tr>
				
				<td align="center">
					<form name="searchForm" action="${pageContext.request.contextPath}/member/list.do" method="post">
						<select name="condition" class="selectField">
							
							<option value="mName" ${condition=="mName"?"selected='selected'":"" }>이름</option>
							<option value="mId"  ${condition=="mId"?"selected='selected'":"" }>아이디</option>
						</select>
						<input type="text" name="keyword" value="${keyword}" class="boxTF">
						<button type="button" class="btn" onclick="searchList();">검색</button>
					</form>
				</td>
				
			</tr>
		</table>		
</div>
</main>


<body>
<header>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</header>
</body>
</html>