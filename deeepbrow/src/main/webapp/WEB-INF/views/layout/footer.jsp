<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script>
	$(document).ready(function(){
		$('.goTop').click(function(){
	        $('html').animate({scrollTop : 0}, 300);
		});
	});
</script>

<a class="goTop"><img src="${pageContext.request.contextPath}/resource/images/up.png" style="width: 30px;"></a>
<div class="footerdiv">
	<div class="footers">
		CS 전용 카카오톡 채널 @딥브로우
		<br>
		MON - FRI / 12PM - 6PM
		<br>
		SAT.SUN.HOLIDAY OFF
		<br>
		CONTACT.jjcommaj@naver.com
	</div>
	
	<div class="footers">
		<a>HOME</a>
		<br>
		<a>이용약관</a>
		<br>
		<a>이용안내</a>
		<br>
		<a>개인정보처리방침</a>
	</div>
	
	<div class="footers">
		COMPANY. 딥브로우
		<br>
		OWNER.서정주 서예슬 BUSINESS NUMBER.285-07-00274
		<br>
		MAIL-ORDER LICENSE.2016-경기시흥-0011 [사업자정보확인]
		<br>
		ADDRESS.14912 (대야동, 트윈프라자) 경기도 시흥시 비둘기공원7길 45, B동 406호
		<br>
		Copyright ©딥브로우 All rights reserved.
	</div>
	

</div>
