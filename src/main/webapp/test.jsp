<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<style>
	#modDiv{
		width:300px;
		height:100px;
		background-color:gray;
		position:absolute;
		top:20%;
		left:20%;
		z-index:1000;
		padding:10px;
	}
</style>
<script>
	var bno = 1798;

	function getPageList(page){
		$.ajax({
			url:"replies/"+bno+"/"+page,
			type:"get",
			dataType:"json",
			success:function(data){
				console.log(data);
				
				var str = "";
				$(data.list).each(function(i, obj){
					str += "<li data-rno='"+ obj.rno +"'>"+ obj.rno +", <span>"+obj.replytext + "</span> <button class='delBtn'>삭제</button><button class='updBtn'>수정</button>";
					str += "</li>";		
				});
				
				/* 댓글 목록 */
				$("#list").html("<ul></ul>");
				$("#list ul").html(str);
				
				/* page번호 */
				var str2 = "";
				if(data.pageMaker.prev){
					str2 = "<a href='"+(data.pageMaker.startPage-1)+"'> << </a>";
				}
				
				for(var i = data.pageMaker.startPage; i <= data.pageMaker.endPage; i++){
					str2 += "<a href='"+i+"'> "+i+" </a>";					
				}
				
				if(data.pageMaker.next){
					str2 += "<a href='"+(data.pageMaker.endPage+1)+"'> >> </a>";
				}
				
				$("#pagenation").html(str2);
				
			}
		});
	}

	$(function(){
		$("#addBtn").click(function(){
			var user = $("#newReplyUser").val();
			var content = $("#newReplyContent").val();
			/* @RequestBody를 사용할 때 필요 한 것
				var sendData = JSON.stringify({bno:1798, replyer:user, replytext:content});
				"Content-Type":"application/json"
			*/
			var sendData = JSON.stringify({bno:bno, replyer:user, replytext:content}); //Json으로 만들어쥼 "{}"
			
			$.ajax({
				url:"replies",
				type:"post",
				data:sendData,
				dataType:"text", //돌려받는 data의 종류 : SUCCESS
				headers:{
					"Content-Type":"application/json"
				},
				success:function(result){
					/* 쌤이한 거 1:page번호*/
					getPageList(1);
				}
			});
		});
		
		//페이지 번호 눌렀을 때 발생
		$(document).on("click", "#pagenation a", function(e){
			e.preventDefault(); //a태그 링크 차단
			var pageNum = $(this).attr("href");
			
			getPageList(pageNum);
		});
		
		/* delete버튼 */
		$(document).on("click", ".delBtn", function(){
			var sendRno = $(this).parent("li").attr("data-rno");
			$.ajax({
				url:"replies/"+sendRno,
				type:"delete",
				dataType:"text", //delete SUCCESS
				success:function(result){
					alert(result);
					getPageList(1);
				}
			});
		});
		
		/* update버튼 */
		$(document).on("click", ".updBtn", function() {
			$("#modDiv").show(1000);
			
			var sendRno = $(this).parent("li").attr("data-rno");
			var replytext = $(this).parent("li").find("span").text();
			
			$("#modDiv .modal-title").text(sendRno);
			$("#modDiv input").val(replytext);
		});
		
		$("#modify").click(function(){
			var sendRno = $(".modal-title").text();
			var replytext = $("#modDiv input").val(); 
			$.ajax({
				url:"replies/"+sendRno,
				type:"put",
				data:JSON.stringify({replytext:replytext}),
				dataType:"text", //update SUCCESS
				headers:{
					"Content-type":"application/json"
				},
				success:function(result){
					alert(result);
					$("#modDiv").hide(500);
					getPageList(1);
				}
			});
		});
		
		$("#close").click(function(){
			$("#modDiv").hide(500);
		});
	});
</script>
</head>
<body>
	<h1>Ajax Test Page</h1>
	<div>
		작성자 : <input type="text" name="user"  id="newReplyUser"/><br />
		내용 : <input type="text" name="content" id="newReplyContent"/><br />
		<button id="addBtn">추가</button>
	</div>
	
	<!-- 쌤이한거 -->
	<div id="list"></div>
	<div id="pagenation"></div>
	
	<div id="modDiv" style="display:none;">
		<div class="modal-title"></div>
		<div>
			<input type="text" id="replytext"/>
		</div>
		<div>
			<button id="modify">수정하기</button>
			<button id="close">닫기</button>
		</div>
	</div>
</body>
</html>