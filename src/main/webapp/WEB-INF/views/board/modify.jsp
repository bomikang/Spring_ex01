<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

	<section class="content">
		<div class="row">
			<div class="col-md-12">
				<div class="box">
					<div class="box-header with-border">
						<h3 class="box-title">MODIFY CONTENT PAGE</h3>
					</div>
					<div class="box-body">
						<form action="modify" method="post" id="form1">
							<div class="form-group">
								<label for="">Title</label>
								<input type="text" class="form-control"  value="${boardVO.title}" name="title"/>
							</div>
							<div class="form-group">
								<label for="">Writer</label>
								<input type="text" class="form-control"  value="${boardVO.writer}" name="writer"/>
							</div>
							<div class="form-group">
								<label for="">Content</label>
								<textarea rows="3" class="form-control" name="content">${boardVO.content}</textarea>
							</div>
							<div class="box-footer">
								<input type="hidden" name="bno" value="${boardVO.bno}"/>
								<input type="hidden" name="page" value="${cri.page}" />
								<input type="hidden" name="perPageNum" value="${cri.perPageNum}" />
								<button class="btn btn-success" type="submit">수정완료</button>
								<button class="btn btn-danger">수정취소</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</section>
	
	<script>
		$(function(){
			$(".btn-danger").click(function(){
				$("#form1").attr("action", "${pageContext.request.contextPath}/board/listPage");
				$("#form1").attr("method", "get");
				$("#form1").submit();
			});
		});
	</script>

<%@ include file="../include/footer.jsp" %>