<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

	<section class="content">
		<div class="row">
			<div class="col-md-12">
				<div class="box">
					<div class="box-header with-border">
						<h3 class="box-title">REGISTER PAGE</h3>
					</div>
					<div class="box-body">
						<form action="register" role="form" method="post" enctype="multipart/form-data">
							<div class="form-group">
								<label for="">Title</label>
								<input type="text" placeholder="Enter Title" name="title" class="form-control"/>
							</div>
							<div class="form-group">
								<label for="">Content</label>
								<textarea placeholder="Enter Content" name="content" class="form-control" rows="5"></textarea>
							</div>
							<div class="form-group">
								<label for="">Writer</label>
								<input type="text" placeholder="Enter Writer" name="writer" class="form-control"/>
							</div>
							<div class="form-group">
								<label for="">Image File</label>
								<input type="file" name="imagefiles" class="form-control" multiple="multiple"/>
							</div>
							<div class="form-group">
								<button type="submit" class="btn btn-primary">등록하기</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</section>
	
	

<%@ include file="../include/footer.jsp" %>