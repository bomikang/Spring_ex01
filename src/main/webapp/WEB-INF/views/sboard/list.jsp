<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="../include/header.jsp" %>

	<section class="content">
		<div class="row">
			<div class="col-md-12">
				<div class="box">
					<div class="box-body">
						<select name="searchType">
							<!-- searchType에 따라 option을 선택되게 함 -->
							<option value="n" ${cri.searchType == null ? 'selected' : ''}>--</option>
							<option value="t" ${cri.searchType eq "t" ? 'selected' : ''}>Title</option> <!-- eq = equal (el 문법) -->
							<option value="c" ${cri.searchType eq "c" ? 'selected' : ''}>Contente</option>
							<option value="w" ${cri.searchType eq "w" ? 'selected' : ''}>Writer</option>
							<option value="tc" ${cri.searchType eq "tc" ? 'selected' : ''}>Title Or Content</option>
							<option value="cw" ${cri.searchType eq "cw" ? 'selected' : ''}>Content Or Writer</option>
							<option value="tcw" ${cri.searchType eq "tcw" ? 'selected' : ''}>Title Orl Content Or Writer</option>
						</select>
						<input type="text" name="keyword" id="keywordInput" value="${cri.keyword}" />
						<button id="searchBtn">Search</button><!-- 검색 버튼 -->
						<button id="newBtn">New Board</button><!-- 등록 버튼 -->
					</div>
				</div>
				<div class="box">
					<div class="box-header with-border">
						<h3 class="box-title">SEARCH LIST PAGE</h3>
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
									<td>
										<a href="${pageContext.request.contextPath}/sboard/read${pageMaker.makeSearch(pageMaker.cri.page)}&bno=${boardVO.bno}">
											${boardVO.title}[${boardVO.replycnt}]
										</a>
									</td>
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
									<li><a href="list${pageMaker.makeSearch(pageMaker.startPage-1)}">&laquo;</a></li>
								</c:if>
								
								<!-- 페이지 번호 버튼 -->
								<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="idx">
									<li ${pageMaker.cri.page == idx ? 'class="active"' : ''}><a href="list${pageMaker.makeSearch(idx)}">${idx}</a></li>
								</c:forEach>
								
								<!-- 다음버튼 -->
								<c:if test="${pageMaker.next}">
									<li><a href="list${pageMaker.makeSearch(pageMaker.endPage+1)}">&raquo;</a></li>
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
		
		$(function(){
			$("#searchBtn").click(function(){
				location.href = "list" + "${pageMaker.makeQuery(1)}"
								+ "&searchType=" + $("select").val()
								+ "&keyword=" + $("#keywordInput").val();
			});
			$("#newBtn").click(function(){
				location.href = "register" + "${pageMaker.makeQuery(1)}"
								+ "&searchType=" + $("select").val()
								+ "&keyword=" + $("#keywordInput").val();
			});
		});
	</script>

<%@ include file="../include/footer.jsp" %>