<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.pageNavi {
		display:flex;
		margin: 0 auto;
		justify-content: center;
	}
	.pageNavi li {
		margin-top: 20px;
		width: 40px;
	}
	.pageNavi a {
		text-align: center;
	}

</style>

<link rel="stylesheet" href="/resources/css/deliverySeller/manageZcdOrder.css">

<!-- bootstrap css -->
<link rel="stylesheet" href="/resources/css/deliverySeller/bootstrap.css">
<!-- jQuery -->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.3.1.js"></script>
<!-- bootstrap jQuery -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/js/bootstrap.bundle.min.js"></script>

</head>
<body>
<div class="s-wrapper">
	<jsp:include page="/WEB-INF/views/common/deliveryHeader.jsp" />
	<div class="main_field">
			<div class="main_content2" >
			<div class="side_nav">
	        		<div class="my_info">
	        			<div class="my_info_div">
	        				<span class="my_info_span">환영합니다</span>
	        			</div>
	        			<div class="user_div">
	        				<div class="user_div_div">
	        					<div class="user_div_content">
	        						<span class="user_div_name">${sessionScope.m.memberName }<span class="user_div_nim">님</span></span>
	        					</div>
	        				</div>
	        			</div>
	        		</div>
        			<div class="side_nav_content">
	        			<div class="side_nav_div"><span class="side_nav_span"><a href="/manageMarketFrm.do">매장관리</a></span></div>
	        			<div class="side_nav_div"><span class="side_nav_span"><a href="/manageMenuFrm.do">메뉴관리</a></span></div>
	        			<div class="side_nav_div side_nav_div_selected"><span class="side_nav_span side_nav_span_selected"><a href="/manageZcdOrderFrm.do">주문접수</a></span></div>
	        			<div class="side_nav_div"><span class="side_nav_span"><a href="/manageZcdReviewFrm.do">리뷰관리</a></span></div>       			
        			</div>
        		</div>
        		
        		<div class="content_div">
        			<div class="content_title">주문접수</div>
        			<div class="content_div_area">
        				<div class="content_div_content">
        					<div class="nav nav-tabs" style="border-bottom: 1px solid rgb(221, 221, 221); margin-bottom: 10px;">
        						<c:choose>
        							<c:when test="${orderState eq '접수대기' }">
        								<div class="nav-item"><a class="nav-link active" href="/manageZcdOrder.do?reqPage=1&storeNo=${storeNo }&orderState=접수대기">접수대기</a></div>
        								<div class="nav-item"><a class="nav-link" href="/manageZcdOrder.do?reqPage=1&storeNo=${storeNo }&orderState=처리중">처리중</a></div>
	        							<div class="nav-item"><a class="nav-link" href="/manageZcdOrder.do?reqPage=1&storeNo=${storeNo }&orderState=완료">완료</a></div>
        							</c:when>
        							<c:when test="${orderState eq '처리중' }">
        								<div class="nav-item"><a class="nav-link" href="/manageZcdOrder.do?reqPage=1&storeNo=${storeNo }&orderState=접수대기">접수대기</a></div>
        								<div class="nav-item"><a class="nav-link active" href="/manageZcdOrder.do?reqPage=1&storeNo=${storeNo }&orderState=처리중">처리중</a></div>
	        							<div class="nav-item"><a class="nav-link" href="/manageZcdOrder.do?reqPage=1&storeNo=${storeNo }&orderState=완료">완료</a></div>
        							</c:when>
        							<c:when test="${orderState eq '완료' }">
			        					<div class="nav-item"><a class="nav-link" href="/manageZcdOrder.do?reqPage=1&storeNo=${storeNo }&orderState=접수대기">접수대기</a></div>
        								<div class="nav-item"><a class="nav-link" href="/manageZcdOrder.do?reqPage=1&storeNo=${storeNo }&orderState=처리중">처리중</a></div>
			        					<div class="nav-item"><a class="nav-link active" href="/manageZcdOrder.do?reqPage=1&storeNo=${storeNo }&orderState=완료">완료</a></div>        							
        							</c:when>
        						</c:choose>
        					</div>
        				
        					<div class="content_div_info">
        						<div class="content_div_opt1 overflow-text">주문번호</div>
        						<div class="content_div_opt2">주문일자</div>
        						<div class="content_div_opt3">배송지</div>
        						<div class="content_div_opt4">주문상태</div>
        					</div>
        					
							<c:if test="${not empty zop.list }">
	       						<table class="content_div_table">
									<c:forEach items="${zop.list }" var="mo">
										<c:choose>
											<c:when test="${mo.orderState eq '접수대기' }">
												<tr class="table_tr_height">
													<td><div class="content_div_opt1">${mo.orderNo }</div></td>
													<td><div class="content_div_opt2">${mo.orderTime }</div></td>
													<td><div class="content_div_opt3 overflow-text">${mo.orderAddress } ${mo.orderAddress2 }</div></td>
													<td><div class="content_div_opt4"><a href="/orderReceipt.do?orderNo=${mo.orderNo }" style="color: #9ac6e8;">접수</a></div></td>
												</tr>
											</c:when>
											<c:when test="${mo.orderState eq '처리중' }">
												<tr class="table_tr_height">
													<td><div class="content_div_opt1">${mo.orderNo }</div></td>
													<td><div class="content_div_opt2">${mo.orderTime }</div></td>
													<td><div class="content_div_opt3 overflow-text">${mo.orderAddress } ${mo.orderAddress2 }</div></td>
													<td><div class="content_div_opt4"><a href="/deliveryReceipt.do?orderNo=${mo.orderNo }" style="color: #9ac6e8;">배달접수</a></div></td>
												</tr>	
											</c:when>
											<c:when test="${mo.orderState eq '완료' }">
												<tr class="table_tr_height">
													<td><div class="content_div_opt1">${mo.orderNo }</div></td>
													<td><div class="content_div_opt2">${mo.orderTime }</div></td>
													<td><div class="content_div_opt3 overflow-text">${mo.orderAddress } ${mo.orderAddress2 }</div></td>
													<td><div class="content_div_opt4">완료</div></td>
												</tr>	
											</c:when>
										</c:choose>
										
									</c:forEach>
								</table>
								<div class="pageNavi">${zop.pageNavi }</div>
							</c:if>
		
							
							<!-- 
							<div class="tab-pane fade" id="b">
        						<table class="content_div_table">
									<c:if test="${not empty zop.list }">
										<c:forEach items="${zop.list }" var="mo">
											<c:if test="${mo.orderState eq '완료' }">
												<tr class="table_tr_height">
													<td><div class="content_div_opt1">${mo.orderNo }</div></td>
													<td><div class="content_div_opt2">${mo.orderTime }</div></td>
													<td><div class="content_div_opt3 overflow-text">${mo.orderAddress } ${mo.orderAddress2 }</div></td>
													<td><div class="content_div_opt4"><a href="#" style="color: #9ac6e8;">완료</a></div></td>
												</tr>											
											</c:if>
										</c:forEach>
									</c:if>
								</table>
			
								<div class="pageNavi">${zop.pageNavi }</div>
							</div>
							 -->
        				</div>
        			</div>
        		</div>
    		</div> <!-- main content -->
        </div> <!-- main field -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</div>
</body>
</html>