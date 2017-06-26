<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../include/header.jsp" %>
<style>
div#image-group{
	position: relative;
}
div#image-group .images{
	margin:5px;
}
</style>

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
							<input type="hidden" name="searchType" value="${cri.searchType}"/>
							<input type="hidden" name="keyword" value="${cri.keyword}"/>
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
						
						<!-- 이미지 파일 -->
						<div class="form-group" id="image-group">
							<c:forEach  var="item" items="${fileNames}">
								<img src="displayFile?fileName=${item}" class="images" alt="${item}"/>
							</c:forEach>
						</div>
						
						<div class="box-footer">
							<button class="btn btn-success" id="modifyBtn">수정하기</button>
							<button class="btn btn-danger" id="removeBtn">삭제하기</button>
							<button class="btn btn-default"id="goListBtn">돌아가기</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<script>
			$(function(){
				//돌아가기
				$("#goListBtn").click(function(){
					$("#form1").attr("action", "${pageContext.request.contextPath}/sboard/list");
					$("#form1").attr("method", "get");
					$("#form1").submit();
				});
				
				//삭제
				$("#removeBtn").click(function(){
					$("#form1").attr("action", "${pageContext.request.contextPath}/sboard/remove");
					$("#form1").submit();
				});
				//수정
				$("#modifyBtn").click(function(){
					$("#form1").attr("action", "${pageContext.request.contextPath}/sboard/modify");
					$("#form1").attr("method", "get");
					$("#form1").submit();
				});
			});
		</script>
		
		<!-- 댓글------------------------------------------------------ -->
		
		<div class="row">
			<div class="col-md-12">
				<div class="box">
					<div class="box-header">
						<h3 class="box-title">댓글 달기</h3>
					</div>
					<div class="box-body">
						<label for="">작성자</label>
						<input type="text" placeholder="작성자아이디" id="newReplyWriter" class="form-control" />
						<label for="">댓글내용</label>
						<input type="text" placeholder="내용" id="newReplyText" class="form-control" />
					</div>
					<div class="box-footer">
						<button class="btn btn-primary" id="replyAddBtn" >등록</button>
					</div>
				</div>
				<ul class="timeline">
					<li class="time-label" id="repliesDiv">
						<span class="bg-green">댓글목록</span>
					</li>
					
				</ul>
				<div class="box-footer">
					<div class="text-center">
						<ul class="pagination">
							<!-- 페이지 번호 버튼 -->
							
						</ul>
					</div>
				</div>
			</div>
			
			<!-- 수정버튼 클릭시 띄워지게 되는 팝업창 -->
			<div id="modifyModal" class="modal modal-primary fade" role="dialog">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title"></h4>
						</div>
						<div class="modal-body">
							<p><input type="text" id="replytext" class="form-control" /></p>
						</div>
						<div class="modal-footer">
							<button class="btn btn-info" id="replyModBtn">수정</button>
							<button class="btn btn-danger" id="replyDelBtn">삭제</button>
							<button class="btn btn-default" data-dismiss="modal">닫기</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
	<script id="template" type="text/x-handlebars-template">
		{{#each.}}
			<li class="replyLi" data-rno={{rno}}>
				<i class="fa fa-comments bg-blue"></i>
				<div class="timeline-item">
					<span class="time">
						<i class="fa fa-clock-o"></i>{{prettifyDate regdate}}
					</span>
					<h3 class="timeline-header"><strong>{{rno}}</strong> - {{replyer}}</h3>
					<div class="timeline-body">{{replytext}}</div>
					<div class="timeline-footer">
						<a class="btn btn-primary btn-xs showModalForMod" data-toggle="modal" data-target="#modifyModal">수정</a>
					</div>
				</div>
			</li>
		{{/each}}
	</script>
	
	<script>
		Handlebars.registerHelper("prettifyDate", function(t){
			var dateObj = new Date();
			var year = dateObj.getFullYear();
			var month = dateObj.getMonth()+1;
			var dayOfMonth = dateObj.getDate();			
			
			return year + "-" + month + "-" + dayOfMonth;
		});
	
		var bno = ${boardVO.bno}; //게시판 번호
		var nowPageNum = 1; //현재 페이지번호
		var replycnt; //댓글개수
		
		//댓글목록보기
		$(function(){
			replycnt = ${boardVO.replycnt};
			bno = ${boardVO.bno};
			getPageList(1);	
		});
		
		function getPageList(page){
			$.ajax({
				/* ex01/sboard/replies/1798/1 : 매핑에러 따라서 ex01/replies갈수있도록 pageContext추가 */
				//url:"replies/"+bno+"/"+page,
				url:"${pageContext.request.contextPath}/replies/"+bno+"/"+page,
				type:"get",
				dataType:"json",
				success:function(data){
					console.log(data);
					
					$(".timeline").html("");
					$(".timeline").html("<li class='time-label' id='repliesDiv'><span class='bg-green'>댓글목록 ["+replycnt+"]</span></li>");
					
					var source = $("#template").html();
					var te = Handlebars.compile(source);
					var html = te(data.list); //배열만 집어넣어서 뽑아내기
					
					$("#repliesDiv").after(html); //repliesDiv바로 다음
					
					var pageNums = "";
					
					//이전버튼
					if(data.pageMaker.prev){
						pageNums = "<a href='"+(data.pageMaker.startPage-1)+"'> << </a>";
					}
					
					//페이지버튼
					for (var i = data.pageMaker.startPage; i <= data.pageMaker.endPage ; i++) {
						pageNums += "<a href='"+ i +"'> "+ i +" </a>";
					}
					
					//이후버튼
					if(data.pageMaker.next){
						pageNums += "<a href='"+(data.pageMaker.endPage+1)+"'> >> </a>";
					}
					
					$(".pagination").html(pageNums);
				}
			});
		}
		
		//페이지 번호 눌렀을 때 발생
		$(document).on("click", ".pagination a", function(e){
			e.preventDefault(); //a태그 링크 차단
			var pageNum = $(this).attr("href");

			nowPageNum = pageNum; //현재페이지번호 전달
			
			getPageList(pageNum);
		});
		
		
		//댓글등록
		$("#replyAddBtn").click(function(){
			var user = $("#newReplyWriter").val();
			var content = $("#newReplyText").val();
			var sendData = JSON.stringify({bno:bno, replyer:user, replytext:content}); //Json으로 만들어쥼 "{}"
			
			$.ajax({
				url:"${pageContext.request.contextPath}/replies",
				type:"post",
				data:sendData,
				dataType:"text",
				headers:{
					"Content-Type":"application/json"
				},
				success:function(result){
					alert(result);
					replycnt++; //댓글개수 1 증가
					getPageList(1);
				}
			});
		});
		
		//댓글 수정 모달을 띄우면 값을 각져옴
		$(document).on("click", ".replyLi .showModalForMod", function(){
			var reply = $(this).parents(".replyLi");
			
			var rno = reply.attr("data-rno");
			$(".modal-title").text(rno);
			
			var replycontent = reply.find(".timeline-body").html();
			$("#replytext").val(replycontent);
			
		});
		
		//댓글 수정 버튼 클릭
		$("#replyModBtn").click(function(){
			var rno = $(this).parents("#modifyModal").find(".modal-title").text();
			var content = $(this).parents("#modifyModal").find("#replytext").val();
			var sendData = JSON.stringify({bno:bno, replyer:"", replytext:content});
			
			$.ajax({
				url:"${pageContext.request.contextPath}/replies/" + rno,
				type:"put",
				data:sendData,
				dataType:"text",
				headers:{
					"Content-Type":"application/json"
				},
				success:function(result){
					alert(result);
					$("#modifyModal").modal("hide"); //모달숨기기
					
					getPageList(nowPageNum);
				}
			});
		});
		
		//댓글 삭제 버튼 클릭
		$("#replyDelBtn").click(function(){
			var rno = $(this).parents("#modifyModal").find(".modal-title").text();

			$.ajax({
				url:"${pageContext.request.contextPath}/replies/" + rno,
				type:"delete",
				dataType:"text",
				success:function(result){
					alert(result);
					$("#modifyModal").modal("hide");
					
					replycnt--;
					getPageList(nowPageNum);
				}
			});
		});
	</script>
	

<%@ include file="../include/footer.jsp" %>