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
<script src="https://kit.fontawesome.com/42044ce0be.js" crossorigin="anonymous"></script>
<style type="text/css">
.quantityBtn{
	margin: 10px auto;
	color : #fff;
	background: none;
}

#result{
	 background: #fff; 
	 float: left; 
	 width: 20px; 
	 color: #000; 
	 margin-top: 10px;
	 margin-bottom: 10px;
	 margin-right: 15px;
}
</style>
<script type="text/javascript">
function count(type)  {
	 
	  var resultElement = document.getElementById('result');
	  
	  var number = resultElement.innerText;
	  

	  if(type === 'plus') {
	    number = parseInt(number) + 1;
	  }else if(type === 'minus' && number == 0){
		 return false;
	  }else if(type === 'minus')  {
	    number = parseInt(number) - 1;
	  }
	  

	  resultElement.innerText = number;
}

function cartOk() {
	
}

function cartOk() {
	
}

</script>
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
			<c:forEach var="dto" items="${listProduct}">
				<p class="txal-center"><img src="${pageContext.request.contextPath}/uploads/product/${dto.image_name}"></p>
			</c:forEach>
			</div>
			<div class="item2">
				<div class="prdDetail">
					<div class="prdInfo">
						<table>
							<tbody style="float: left;">
								<tr>
									<td><span class="prdName">상품명 : ${dto.pName}</span></td>
								</tr>
								<tr>
									<td><span class="prdPrice">가격 : ${dto.pPrice}</span></td>
								</tr>
								<tr>
									<td><span class="prdDesc">설명 : ${dto.pDesc}</span></td>
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
											<div id='result'>0</div>
		                                    <input class="quantityBtn" type='button' onclick='count("plus")' value='△'/>
		                                    <input class="quantityBtn" type='button' onclick='count("minus")' value='▽'/>
	                                	</span>
                            		</td>
	                            <td class="right">
									<div class="quantity_price"></div>
								</td>
                        	</tr></tbody>
							</tbody>
						</table>
					</div>
					<div class="totalPrice">
					0
					</div>
					<div class="btnArea">
	                    <button type="button" class="btn1" onclick="cartOk()">ADD CART</button>
	                    <button type="button" class="btn2" onclick="buyOk()">BUY NOW</button>
                	</div>
				</div>
			</div>
			<div style="position: static; width: 633.188px; height: 346px; display: inline-block; vertical-align: top; float: none;"></div>
		</div>
		
		<div class="review-box">
			<div class="review-top"><h3 style="float: left;">REVIEW</h3><button type="button" class="btn btnWrite" onclick="" style="float: right;">WRITE</button></div>
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