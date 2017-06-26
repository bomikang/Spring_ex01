<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

	<section class="content">
		<div class="row">
			<div class="col-md-12">
				<div class="box">
					<div class="box-header with-border">
						<h3 class="box-title">READ CONTENT PAGE</h3>
					</div>
					<div class="box-body">
						<form action="" method="post" id="form1">
							<input type="hidden" value="${boardVO.bno}" name="bno" />
							<!-- 글을 읽고 나서 첫번째 페이지가 아닌 바로 이전 페이지로 돌아가도록 -->
							<input type="hidden" name="page" value="${cri.page}" />
							<input type="hidden" name="perPageNum" value="${cri.perPageNum}" />
						</form>
						<div class="form-group">
							<label for="">Title</label>
							<input type="text" readonly="readonly" class="form-control"  value="${boardVO.title}"/>
						</div>
						<div class="form-group">
							<label for="">Writer</label>
							<input type="text" readonly="readonly" class="form-control"  value="${boardVO.writer}"/>
						</div>
						
						<div class="form-group">
							<label for="">Content</label>
							<textarea rows="3" readonly="readonly" class="form-control" >${boardVO.content}</textarea>
						</div>
						<div class="box-footer">
							<button class="btn btn-success">수정하기</button>
							<button class="btn btn-danger">삭제하기</button>
							<button class="btn btn-default">돌아가기</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	
	<script>
		$(function(){
			//돌아가기
			$(".btn-default").click(function(){
				$("#form1").attr("action", "${pageContext.request.contextPath}/board/listPage");
				$("#form1").attr("method", "get");
				$("#form1").submit();
			});
			
			//삭제
			$(".btn-danger").click(function(){
				$("#form1").attr("action", "${pageContext.request.contextPath}/board/remove");
				$("#form1").submit();
			});
			//수정
			$(".btn-success").click(function(){
				$("#form1").attr("action", "${pageContext.request.contextPath}/board/modify");
				$("#form1").attr("method", "get");
				$("#form1").submit();
			});
		});
	</script>

<%@ include file="../include/footer.jsp" %>