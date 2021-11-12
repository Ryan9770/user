<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<c:forEach var="vo" items="${listQm}">
	<table class="table table-article">
		<tr>
			<td align="left">
				<span class="text">관리자 | ${vo.qmReg_date} | ${vo.qmNo}</span>
			</td>
			<td align="right">
				<c:if test="${sessionScope.member.userId=='admin'}">
					<a>
						<span class="deleteQm" data-qmNo='${vo.qmNo}' data-pageNo='${pageNo}' style="padding-right: 30px;">삭제</span>
					</a>
				</c:if>
			</td>
		</tr>
		<tr height="100px;">
			<td colspan="2">
				${vo.qmContent}
			</td>
		</tr>
	</table>
</c:forEach>

<div class="page-box">
	${paging}
</div>