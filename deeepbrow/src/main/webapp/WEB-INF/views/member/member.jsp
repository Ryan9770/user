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
	height: 1000px;
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
.table {
	width: 50%;
	margin: 0 auto;
	border-spacing: 0;
	border-collapse: collapse;
}
.table th, .table td {
	padding: 10px 0;
}

.table-border tr {
	border-bottom: 1px solid #ccc;
	line-height: 40px;
}
.table-border tr:first-child {
	border-top: 2px solid #ccc;
}
.table-form td {
	padding: 7px 0;
}
.table-form tr td:first-child{
	text-align: center;
	width: 120px;
	font-weight: 500;
}
.table-form tr td:nth-child(2) {
	text-align: left; padding-left: 10px; 
}

.boxTF {
	border: 1px solid white;
	padding: 5px 5px;
	background-color: #fff;
	border-radius: 4px;
	font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
	vertical-align: baseline;
}
.boxTF[readonly] {
	background-color:#eee;
}

.selectField {
	border: 1px solid #999;
	padding: 4px 5px;
	border-radius: 4px;
	font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
	vertical-align: baseline;
}

.help-block, .block {
	margin-top: 5px;
}
.msg-box {
	text-align: center; color: blue;
}
.btn{
	background-color:white;
	border:none;
	color:black;
	cursor: pointer;
	vertical-align: baseline;
}
.btn:hover, .btn:active, .btn:focus {
	background-color: #e6e6e6;
	border-color: #adadad;
	color:#333;
}
.in {
	width: 20%;
	padding: 10px 0;
	font-size: 13px;
	border-radius: 20px;
	margin-left: 5px;
}
.out {
	width: 20%;
	padding: 15px 0;
	font-weight: 700; 
	font-size: 15px;
	margin: 5px;
	border-radius: 30px;
}
</style>
<script type="text/javascript">
function memberOk() {
	var f = document.memberForm;
	var str;

	str = f.mId.value;
	if( !/^[a-z][a-z0-9_]{4,9}$/i.test(str) ) { 
		alert("아이디를 다시 입력 하세요. ");
		f.mId.focus();
		return;
	}

	str = f.mPassword.value;
	if( !/^(?=.*[a-z])(?=.*[!@#$%^*+=-]|.*[0-9]).{5,10}$/i.test(str) ) { 
		alert("패스워드를 다시 입력 하세요. ");
		f.mPassword.focus();
		return;
	}

	if( str != f.mPassword2.value ) {
        alert("패스워드가 일치하지 않습니다. ");
        f.mPassword.focus();
        return;
	}
	
    str = f.mName.value;
    if( !/^[가-힣]{2,5}$/.test(str) ) {
        alert("이름을 다시 입력하세요. ");
        f.mName.focus();
        return;
    }

    str = f.mBirth.value;
    if( !str ) {
        alert("생년월일를 입력하세요. ");
        f.mBirth.focus();
        return;
    }
    
    str = f.mTel1.value;
    if( !str ) {
        alert("전화번호를 입력하세요. ");
        f.mTel1.focus();
        return;
    }

    str = f.mTel2.value;
    if( !/^\d{3,4}$/.test(str) ) {
        alert("숫자만 가능합니다. ");
        f.mTel2.focus();
        return;
    }

    str = f.mTel3.value;
    if( !/^\d{4}$/.test(str) ) {
    	alert("숫자만 가능합니다. ");
        f.mTel3.focus();
        return;
    }
    
    str = f.mEmail1.value.trim();
    if( !str ) {
        alert("이메일을 입력하세요. ");
        f.mEmail1.focus();
        return;
    }

    str = f.mEmail2.value.trim();
    if( !str ) {
        alert("이메일을 입력하세요. ");
        f.mEmail2.focus();
        return;
    }

   	f.action = "${pageContext.request.contextPath}/member/${mode}_ok.do";
    f.submit();
}

function changeEmail() {
    var f = document.memberForm;
	    
    var str = f.selectEmail.value;
    if(str!="direct") {
        f.mEmail2.value=str; 
        f.mEmail2.readOnly = true;
        f.mEmail1.focus(); 
    }
    else {
        f.mEmail2.value="";
        f.mEmail2.readOnly = false;
        f.mEmail1.focus();
    }
}

function userIdCheck() {
	var mId = $("#mId").val();
	
	if( !/^[a-z][a-z0-9_]{4,9}$/i.test(mId) ) { 
		var str = "아이디는 5~10자 이내이며, 첫글자는 영문자로 시작합니다.";
		$("#mId").focus();
		$("#mId").parent().next(".help-block").html(str);
		return false;
	}
	
	var url = "${pageContext.request.contextPath}/member/userIdCheck.do";
	var query = "mId="+mId;
	
	$.ajax({
		type:"post",
		url:url,
		data:query,
		dataType:"json",
		success:function(data) {
			var passed = data.passed;
			
			if(passed === "true") {
				var str = "<span style='color:blue;font-weight:600;'>"+mId+"</span>아이디는 사용 가능합니다.";
				$("#mId").parent().next(".help-block").html(str);
				$("#mIdValid").val("true");
			} else {
				var str = "<span style='color:red;font-weight:600;'>"+mId+"</span>아이디는 사용 불가능합니다.";
				$("#mId").parent().next(".help-block").html(str);
				$("#mIdValid").val("false");
				$("#mId").val("");
				$("#mId").focus();
			}
		}
	});
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
			<span class="article-title">${title}</span>
		</div>
		
		<form name="memberForm" method="post">
		<table class="table table-border table-form">
			<tr>
				<td>아&nbsp;이&nbsp;디</td>
				<td>
					<p>
						<input type="text" name="mId" id="mId" maxlength="10" class="boxTF" 
							value="${dto.mId}" 
							style="width: 50%;" ${mode=="update" ? "readonly='readonly' ":""}
							onchange="userIdCheck()">
					</p>
					<p class="help-block">아이디는 5~10자 이내이며, 첫글자는 영문자로 시작해야 합니다.</p>
				</td>
			</tr>
		
			<tr>
				<td>패스워드</td>
				<td>
					<p>
						<input type="password" name="mPassword" class="boxTF" maxlength="10" style="width: 50%;">
					</p>
					<p class="help-block">패스워드는 5~10자 이내이며, 하나 이상의 숫자나 특수문자가 포함되어야 합니다.</p>
				</td>
			</tr>
		
			<tr>
				<td>패스워드 확인</td>
				<td>
					<p>
						<input type="password" name="mPassword2" class="boxTF" maxlength="10" style="width: 50%;">
					</p>
					<p class="help-block">패스워드를 한번 더 입력해주세요.</p>
				</td>
			</tr>
		
			<tr>
				<td>이&nbsp;&nbsp;&nbsp;&nbsp;름</td>
				<td>
					<input type="text" name="mName" maxlength="10" class="boxTF" value="${dto.mName}" style="width: 50%;" ${mode=="update" ? "readonly='readonly' ":""}>
				</td>
			</tr>
		
			<tr>
				<td>생년월일</td>
				<td>
					<input type="date" name="mBirth" class="boxTF" value="${dto.mBirth}" style="width: 50%;">
				</td>
			</tr>
		
			<tr>
				<td>이 메 일</td>
				<td>
					  <select name="selectEmail" class="selectField" onchange="changeEmail();">
							<option value="">선 택</option>
							<option value="naver.com"   ${dto.mEmail2=="naver.com" ? "selected='selected'" : ""}>네이버 메일</option>
							<option value="hanmail.net" ${dto.mEmail2=="hanmail.net" ? "selected='selected'" : ""}>한 메일</option>
							<option value="gmail.com"   ${dto.mEmail2=="gmail.com" ? "selected='selected'" : ""}>지 메일</option>
							<option value="hotmail.com" ${dto.mEmail2=="hotmail.com" ? "selected='selected'" : ""}>핫 메일</option>
							<option value="direct">직접입력</option>
					  </select>
					  <input type="text" name="mEmail1" maxlength="30" class="boxTF" value="${dto.mEmail1}" style="width: 33%;"> @ 
					  <input type="text" name="mEmail2" maxlength="30" class="boxTF" value="${dto.mEmail2}" style="width: 33%;" readonly="readonly">
				</td>
			</tr>
			
			<tr>
				<td>전화번호</td>
				<td>
					  <select name="mTel1" class="selectField">
							<option value="">선 택</option>
							<option value="010" ${dto.mTel1=="010" ? "selected='selected'" : ""}>010</option>
							<option value="02"  ${dto.mTel1=="02"  ? "selected='selected'" : ""}>02</option>
							<option value="031" ${dto.mTel1=="031" ? "selected='selected'" : ""}>031</option>
							<option value="032" ${dto.mTel1=="032" ? "selected='selected'" : ""}>032</option>
							<option value="033" ${dto.mTel1=="033" ? "selected='selected'" : ""}>033</option>
							<option value="041" ${dto.mTel1=="041" ? "selected='selected'" : ""}>041</option>
							<option value="042" ${dto.mTel1=="042" ? "selected='selected'" : ""}>042</option>
							<option value="043" ${dto.mTel1=="043" ? "selected='selected'" : ""}>043</option>
							<option value="044" ${dto.mTel1=="044" ? "selected='selected'" : ""}>044</option>
							<option value="051" ${dto.mTel1=="051" ? "selected='selected'" : ""}>051</option>
							<option value="052" ${dto.mTel1=="052" ? "selected='selected'" : ""}>052</option>
							<option value="053" ${dto.mTel1=="053" ? "selected='selected'" : ""}>053</option>
							<option value="054" ${dto.mTel1=="054" ? "selected='selected'" : ""}>054</option>
							<option value="055" ${dto.mTel1=="055" ? "selected='selected'" : ""}>055</option>
							<option value="061" ${dto.mTel1=="061" ? "selected='selected'" : ""}>061</option>
							<option value="062" ${dto.mTel1=="062" ? "selected='selected'" : ""}>062</option>
							<option value="063" ${dto.mTel1=="063" ? "selected='selected'" : ""}>063</option>
							<option value="064" ${dto.mTel1=="064" ? "selected='selected'" : ""}>064</option>
							<option value="070" ${dto.mTel1=="070" ? "selected='selected'" : ""}>070</option>
					  </select>
					  <input type="text" name="mTel2" maxlength="4" class="boxTF" value="${dto.mTel2}" style="width: 33%;"> -
					  <input type="text" name="mTel3" maxlength="4" class="boxTF" value="${dto.mTel3}" style="width: 33%;">
				</td>
			</tr>
		
			<tr>
				<td>우편번호</td>
				<td>
					<input type="text" name="mZipcode" id="mZipcode" maxlength="7" class="boxTF" value="${dto.mZipcode}" readonly="readonly" style="width: 50%;">
					<button type="button" class="btn in" onclick="daumPostcode();">우편번호검색</button>
				</td>
			</tr>
			
			<tr>
				<td valign="top">주&nbsp;&nbsp;&nbsp;&nbsp;소</td>
				<td>
					<p>
						<input type="text" name="mAddr1" id="mAddr1" maxlength="50" class="boxTF" value="${dto.mAddr1}" readonly="readonly" style="width: 96%;">
					</p>
					<p class="block">
						<input type="text" name="mAddr2" id="mAddr2" maxlength="50" class="boxTF" value="${dto.mAddr2}" style="width: 96%;">
					</p>
				</td>
			</tr>
			
		</table>
		
		<table class="table">
			<c:if test="${mode =='member'}">
				<tr>
					<td align="center">
						<span>
							<input type="checkbox" name="terms" value="1" checked="checked" onchange="form.btnOk.disabled = !checked">
							약관에 동의하시겠습니까 ?
						</span>
						<span><a href="">약관보기</a></span>
					</td>
				</tr>
			</c:if>
					
			<tr>
				<td align="center">
				    <button type="button" class="btn out" name="btnOk" onclick="memberOk();"> ${mode=="member"?"회원가입":"정보수정"} </button>
				    <button type="reset" class="btn out"> 다시입력 </button>
				    <button type="button" class="btn out" 
				    	onclick="javascript:location.href='${pageContext.request.contextPath}/';"> ${mode=="member"?"가입취소":"수정취소"} </button>
					 <input type="hidden" name="mIdValid" id="mIdValid" value="false">
				</td>
			</tr>
			
			<tr>
				<td align="center">
					<span class="msg-box">${message}</span>
				</td>
			</tr>
		</table>
		</form>
      
	
		      

</div>

</main>

<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
    function daumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var fullAddr = ''; // 최종 주소 변수
                var extraAddr = ''; // 조합형 주소 변수

                // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    fullAddr = data.roadAddress;

                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    fullAddr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
                if(data.userSelectedType === 'R'){
                    //법정동명이 있을 경우 추가한다.
                    if(data.bname !== ''){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있을 경우 추가한다.
                    if(data.buildingName !== ''){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                    fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('mZipcode').value = data.zonecode; //5자리 새우편번호 사용
                document.getElementById('mAddr1').value = fullAddr;

                // 커서를 상세주소 필드로 이동한다.
                document.getElementById('mAddr2').focus();
            }
        }).open();
    }
</script>


<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>


</body>
</html>