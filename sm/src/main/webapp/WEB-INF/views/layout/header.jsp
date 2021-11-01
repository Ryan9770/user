<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="header-top">
    <div class="header-left">
        <h1 class="logo"><a href="${pageContext.request.contextPath}/">SPRING<span>.</span></a></h1>
    </div>
    <div class="header-right">
        <div style="text-align: right;">
            <c:if test="${empty sessionScope.member}">
                <a href="${pageContext.request.contextPath}/member/login.do">로그인</a>
				&nbsp;|&nbsp;
                <a href="${pageContext.request.contextPath}/member/member.do">회원가입</a>
            </c:if>
            <c:if test="${not empty sessionScope.member}">
                <span style="color:blue;">${sessionScope.member.userName}</span>님
				&nbsp;|&nbsp;
				<a href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a>
				&nbsp;|&nbsp;
				<a href="${pageContext.request.contextPath}/member/pwd.do?mode=update">정보수정</a>
            </c:if>
		</div>
    </div>
</div>

<div class="menu">
    <ul class="nav">
        <li>
            <a href="#">회사소개</a>
            <ul>
                <li><a href="#">회사개요</a></li>
                <li><a href="#">회사연혁</a></li>
                <li><a href="#">사업분야</a></li>
                <li><a href="#">조직도</a></li>
                <li><a href="#">찾아오시는길</a></li>
            </ul>
        </li>
			
        <li>
            <a href="#">커뮤니티</a>
            <ul>
                <li><a href="#">방명록</a></li>
                <li><a href="#">게시판</a></li>
                <li><a href="#">자료실</a></li>
                <li><a href="#">포토갤러리</a></li>
            </ul>
        </li>

        <li>
            <a href="#">스터디룸</a>
            <ul>
                <li><a href="#" style="margin-left:70px; " onmouseover="this.style.marginLeft='150px';">강좌공지</a></li>
                <li><a href="#">프로그래밍 강좌</a></li>
                <li><a href="#">데이터베이스 강좌</a></li>
                <li><a href="#">리눅스 강좌</a></li>
            </ul>
        </li>

        <li>
            <a href="#">고객센터</a>
            <ul>
                <li><a href="#" style="margin-left:150px; " onmouseover="this.style.marginLeft='150px';">자주하는질문</a></li>
                <li><a href="#">공지사항</a></li>
                <li><a href="#">질문과 답변</a></li>
                <li><a href="#">주요일정</a></li>
                <li><a href="#">이벤트</a></li>
            </ul>
        </li>

        <c:if test="${not empty sessionScope.member}">
        <li>
            <a href="#">마이페이지</a>
            <ul>
                <li><a href="#" style="margin-left:230px; " onmouseover="this.style.marginLeft='230px';">정보확인</a></li>
                <li><a href="#">쪽지</a></li>
                <li><a href="#">일정관리</a></li>
                <li><a href="#">친구관리</a></li>
                <li><a href="#">사진첩</a></li>
                <li><a href="#">채팅</a></li>
            </ul>
        </li>
        </c:if>
			
        <li style="float: right;"><a href="#">전체보기</a></li>

    </ul>      
</div>

<div class="navigation">
	<div class="nav-bar">홈 &gt; 회사소개</div>
</div>