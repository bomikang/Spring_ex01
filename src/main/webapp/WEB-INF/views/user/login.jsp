<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<section class="content">
	<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<div class="box">
				<div class="box-body">
					<form action="loginPost" role="form" method="post">
						<div class="form-group">
							<label for="">아이디</label>
							<input type="text" placeholder="Enter Id" name="uid" class="form-control"/>
						</div>
						<div class="form-group">
							<label for="">비밀번호</label>
							<input type="text" placeholder="Enter Password" name="upw" class="form-control"/>
						</div>
						<div class="form-group">
							<button type="submit" class="btn btn-primary">로그인</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</section>
<%@ include file="../include/footer.jsp" %>