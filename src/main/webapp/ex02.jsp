<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
<style>
	table td{
		border:1px solid black;
	}
</style>
</head>
<body>

<div id="table"></div>


<!-- {{#each.}}배열로 넣었을 때 각각을 출력하기 위해 필요 -->	
<script id="template" type="text/x-handlebars-template">
	<h1>{{major}}</h1>
	<table>
		{{#users}}
		<tr>
			<td>{{name}}</td>
			<td>{{id}}</td>
			<td>{{email}}</td>
		</tr>
		{{/users}}
	</table>
</script>
<script>
	/* 함수 */
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
	var data = {major:"컴퓨터과",
				users:[{name:"강보미", id:"bomi1", email:"bomi1@naver.com"},
				       {name:"강보미2", id:"bomi2", email:"bomi2@naver.com"},
				       {name:"강보미3", id:"bomi3", email:"bomi3@naver.com"},
				       {name:"강보미4", id:"bomi4", email:"bomi4@naver.com"},
				       {name:"강보미5", id:"bomi5", email:"bomi5@naver.com"}]};
	console.log(te);
	
	$("#table").html(te(data));
</script>
</body>
</html>