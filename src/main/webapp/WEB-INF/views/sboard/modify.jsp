<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../include/header.jsp" %>
<style>
div#image-group{
	position: relative;
}
div#image-group .dropbox{
	display:inline;
}
div#image-group .images{
	margin:5px;
}
div#image-group button.del{
	position:absolute;
	margin-left:-33px;
	margin-top:75px;
}
</style>
	<section class="content">
		<div class="row">
			<div class="col-md-12">
				<div class="box">
					<div class="box-header with-border">
						<h3 class="box-title">MODIFY CONTENT PAGE</h3>
					</div>
					<div class="box-body">
						<form action="modify" method="post" id="form1" enctype="multipart/form-data">
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
							<!-- 이미지 파일 -->
							<div class="form-group" id="image-group">
								<input type="file" name="imagefiles" multiple="multiple" id="addImage"/>
								<c:forEach  var="item" items="${fileNames}">
									<div class="dropbox">
										<img src="displayFile?fileName=${item}" class="images" alt="${item}"/>
										<button class="del">X</button>
									</div>
								</c:forEach>
							</div>
							<div id="imageFileHidden"><!-- 히든으로 넘어갈 삭제된 파일명 -->
								<!-- <input type="hidden" name="files" class="imageFiles" value=""/> -->
							</div>
							<div class="box-footer">
								<input type="hidden" name="bno" value="${boardVO.bno}"/>
								<input type="hidden" name="page" value="${cri.page}" />
								<input type="hidden" name="perPageNum" value="${cri.perPageNum}" />
								<input type="hidden" name="searchType" value="${cri.searchType}"/>
								<input type="hidden" name="keyword" value="${cri.keyword}"/>
								<button class="btn btn-success" type="submit">수정완료</button>
								<button class="btn btn-danger">수정취소</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</section>

<!-- ----------------------------------------------------script---------------------------------------------------- -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>	
	<script>
		$(function(){
			$(".btn-danger").click(function(){
				$("#form1").attr("action", "read");
				$("#form1").attr("method", "get");
				location.href = "${pageContext.request.contextPath}/sboard/read${pageMaker.makeSearch(pageMaker.cri.page)}&bno=${boardVO.bno}";
			});
		});
		
		/* X버튼을 클릭하면 각 이미지가 화면에서 사라지도록 */
		$(document).on("click", "button.del", function(e){
			e.preventDefault();
			var filename = $(this).parent(".dropbox").find("img").attr("alt");
			
			var input = "<input type='hidden' name='files' class='imageFiles' value='"+filename+"'/>";
			$("#imageFileHidden").append(input);
			
			$(this).parent(".dropbox").remove();
		});
	</script>

<%@ include file="../include/footer.jsp" %>