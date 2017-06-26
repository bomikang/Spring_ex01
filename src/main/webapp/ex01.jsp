<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
</head>
<body>

<ul id="replies"></ul>


<!-- {{#each.}}배열로 넣었을 때 각각을 출력하기 위해 필요 -->	
<!-- 
	<div>{{temp replydate}}</div> : registerHelper에서 리턴되는 값
 -->
<script id="template" type="text/x-handlebars-template">
	{{#each.}} 
	<li>
		<div>{{rno}}</div>
		<div>{{replytext}}</div>
		<div>{{temp replydate}}</div>
	</li>
	{{/each}}
</script>
<script>
	Handlebars.registerHelper("temp", function(t){
		var dateObj = new Date();
		var year = dateObj.getFullYear();
		var month = dateObj.getMonth()+1;
		var dayOfMonth = dateObj.getDate();
		var day = dateObj.getDay();
		var strDay = "";
		if(day == 1) strDay = "월";
		if(day == 2) strDay = "화";
		if(day == 3) strDay = "수";
		if(day == 4) strDay = "목";
		if(day == 5) strDay = "금";
		if(day == 6) strDay = "토";
		if(day == 6) strDay = "일";
		
		
		return year + "-" + month + "-" + dayOfMonth + "-" + strDay+"요일";
	});

	var source = $("#template").html(); //String 반환
	var te = Handlebars.compile(source); //함수 반환
	var data = [{rno:1, replytext:"1번 댓글", replydate:new Date()},
	            {rno:1, replytext:"2번 댓글", replydate:new Date()},
	            {rno:1, replytext:"3번 댓글", replydate:new Date()},
	            {rno:1, replytext:"4번 댓글", replydate:new Date()},
	            {rno:1, replytext:"5번 댓글", replydate:new Date()}]; //배열
	console.log(te);
	
	$("#replies").html(te(data));
</script>
</body>
</html>