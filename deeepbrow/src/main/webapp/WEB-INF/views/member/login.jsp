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
main {
	box-sizing: border-box;
	height: 700px;
}

.title-body {
	padding: 50px 0; text-align: center;
}

.title-body .article-title {
	font-size: 20px; cursor: pointer;
}

.form-body {
	text-align: center;
}

.form-body .lbl {
	position: absolute; margin-left: 15px; margin-top: 15px;
}

.form-body .inputTF {
	width: 100%;
	height: 45px;
	padding: 5px;
	padding-left: 15px;
	border: none;
	border-bottom: 1px solid white;
	background: black;
	color: white;
}
.table{
	margin: 0 auto;
	width: 400px;
}
.msg-box {
	text-align: center; color: blue;
}
.btnConfirm {
	background-color:white;
	border:none;
	color:black;
	width: 50%;
	padding: 15px 0;
	border-radius: 30px;
	font-weight: 700; 
	font-size: 15px;
	cursor: pointer;
	vertical-align: baseline;
}

.btnConfirm:hover, .btnConfirm:active, .btnConfirm:focus {
	background-color: #e6e6e6;
	border-color: #adadad;
	color:#333;
}

</style>
<script type="text/javascript">
function bgLabel(obj, id) {
	if( ! obj.value ) {
		document.getElementById(id).style.display="";
	} else {
		document.getElementById(id).style.display="none";
	}
}

function inputsFocus( id ) {
	// 객체를 보이지 않게 숨긴다.
	document.getElementById(id).style.display="none";
}

function sendLogin() {
    var f = document.loginForm;

	var str = f.mId.value;
    if(!str) {
        alert("아이디를 입력하세요. ");
        f.mId.focus();
        return;
    }

    str = f.mPassword.value;
    if(!str) {
        alert("패스워드를 입력하세요. ");
        f.mPassword.focus();
        return;
    }

    f.action = "${pageContext.request.contextPath}/member/login_ok.do";
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
			<span class="article-title">로그인</span>
		</div>
	
		<div class="form-body">
			<form name="loginForm" method="post" action="">
				<table class="table">
					<tr align="center"> 
						<td> 
							<label for="mId" id="lblmId" class="lbl">아이디</label>
							<input type="text" name="mId" id="mId" class="inputTF" maxlength="15"
								tabindex="1"
								onfocus="inputsFocus('lblmId');"
								onblur="bgLabel(this, 'lblmId');">
						</td>
					</tr>
					<tr align="center"> 
					    <td>
							<label for="mPassword" id="lblmPassword" class="lbl">패스워드</label>
							<input type="password" name="mPassword" id="mPassword" class="inputTF" maxlength="20" 
								tabindex="2"
								onfocus="inputsFocus('lblmPassword');"
								onblur="bgLabel(this, 'lblmPassword');">
					    </td>
					</tr>
					<tr align="center"> 
					    <td>
							<button type="button" onclick="sendLogin();" class="btnConfirm">로그인</button>
					    </td>
					</tr>
					<tr align="center"> 
					    <td>
							<button type="button" onclick="location.href='${pageContext.request.contextPath }/member/member.do'" class="btnConfirm">회원가입</button>
					    </td>
					</tr>
					<tr align="center">
					    <td>
							<a href="${pageContext.request.contextPath}">아이디 찾기</a>&nbsp;|&nbsp; 
							<a href="${pageContext.request.contextPath}">비밀번호 찾기</a>
					    </td>
					</tr>
				</table>
				
				<table class="table">
					<tr>
						<td class="msg-box">${message}</td>
					</tr>
				</table>
			</form>           
		</div>

</div>

</main>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>


</body>
</html>