<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="../include/header.jsp" %>

	<section class="content">
		<div class="row">
			<div class="col-md-12">
				<div class="box">
					<div class="box-header with-border">
						<h3 class="box-title">LIST ALL PAGE</h3>
					</div>
					<div class="box-body">
						<table class="table table-bordered">
							<tr>
								<th style="width:10px;">BNO</th>
								<th>TITLE</th>
								<th>WRITER</th>
								<th>REGDATE</th>
								<th style="width:40px;">VIEWCNT</th>
							</tr>
							<c:forEach var="boardVO" items="${list}" >
								<tr>
									<td>${boardVO.bno}</td>
									<!-- 함수 사용 -->
									<td><a href="${pageContext.request.contextPath}/board/read${pageMaker.makeQuery(pageMaker.cri.page)}&bno=${boardVO.bno}">${boardVO.title}</a></td>
									<td>${boardVO.writer}</td>
									<td><fmt:formatDate value="${boardVO.regdate}" pattern="yyyy-MM-dd HH:mm"/></td>
									<td><span class="badge bg-red">${boardVO.viewcnt}</span></td>
								</tr>
							</c:forEach>
						</table> 
					</div>
					<div class="box-footer">
						<div class="text-center">
							<ul class="pagination">
								<!-- 이전버튼 -->
								<c:if test="${pageMaker.prev}">
									<li><a href="listPage?page=${pageMaker.startPage-1}">&laquo;</a></li>
								</c:if>
								
								<!-- 페이지 번호 버튼 -->
								<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="idx">
									<li ${pageMaker.cri.page == idx ? 'class="active"' : ''}><a href="listPage${pageMaker.makeQuery(idx)}">${idx}</a></li>
								</c:forEach>
								
								<!-- 다음버튼 -->
								<c:if test="${pageMaker.next}">
									<li><a href="listPage?page=${pageMaker.endPage+1}">&raquo;</a></li>
								</c:if>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	
	<script>
		var result = "${result}";
		
		if(result == "success"){ 
			alert("처리가 완료되었습니다.");
		}
		
	</script>

<%@ include file="../include/footer.jsp" %>