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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/article.css" type="text/css">
<style type="text/css">

</style>


</head>
<body>

<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>
	
<main>
<div class="container">
	<div class="contents">
		<div class="prd">
			<div class="item1">
				<p class="txal-center"><img src="${pageContext.request.contextPath}/resource/images/1.jpg"></p>
				<p class="txal-center"><img src="${pageContext.request.contextPath}/resource/images/2.jpg"></p>
				<p class="txal-center"><img src="${pageContext.request.contextPath}/resource/images/3.jpg"></p>
				<p class="txal-center"><img src="${pageContext.request.contextPath}/resource/images/4.jpg"></p>
				<p class="txal-center"><img src="${pageContext.request.contextPath}/resource/images/5.jpg"></p>
				<p class="txal-center"><img src="${pageContext.request.contextPath}/resource/images/6.jpg"></p>
			</div>
			<div class="item2">
				<div class="prdDetail">
					<div class="prdInfo">
						<table>
							<tbody style="float: left;">
								<tr>
									<td><span class="prdName">my dia flower and heart necklace (silver 925)</span></td>
								</tr>
								<tr>
									<td><span class="prdPrice">150,000</span></td>
								</tr>
							</tbody>
						</table>	
					</div>
					<div class="totalProduct">
						<table>          
							<tbody>
								<tr>
									<td>
	                                	<span class="quantity">
		                                    <input id="quantity" value="1" type="text">
		                                    <a href="#"><span>▲</span></a>
		                                    <a href="#"><span>▼</span></a>
	                                	</span>
                            		</td>
	                            <td class="right">
									<span class="quantity_price">150,000 원</span>
								</td>
                        	</tr></tbody>
							</tbody>
						</table>
					</div>
					<div class="totalPrice">
					
					</div>
					<div class="btnArea">
	                    <button type="button" class="btn1">ADD CART</button>
	                    <button type="button" class="btn2">BUY NOW</button>
                	</div>
				</div>
			</div>
			<div style="position: static; width: 633.188px; height: 346px; display: inline-block; vertical-align: top; float: none;"></div>
		</div>
		
		<div class="review-box">
			<div class="review-top"><h3 style="float: left;">REVIEW</h3><button type="button" class="btn btnWrite" style="float: right;">WRITE</button></div>
			<div class="review-board">리뷰가 없습니다</div>
		</div>
	</div>
</div>
</main>

<footer>
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>


</body>
</html>