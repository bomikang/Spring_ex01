package com.dgit.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthInterceptor extends HandlerInterceptorAdapter{
	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.info("preHandler..........");
		
		/*session에 들어있는 login 정보를 받아서 존재하면 계속 진행 
		 * 존재하지않으면 로그인화면으로 이동*/
		HttpSession session = request.getSession();
		Object login = session.getAttribute(LoginInterceptor.LOGIN);
		
//		로그인이 안된 상태
		if (login == null) {
			logger.info("have to login");
			saveDest(request); //call method
			response.sendRedirect(request.getContextPath()+"/user/login"); //로그인 화면으로 이동
			return false;
		}
		return true;
	}
	
	/*Login화면 이동 전에 원래 이동하고자 하는 곳을 저장해 둔 후
	 * 로그인이 성공하면 그 곳으로 이동되도록 함*/
	private void saveDest(HttpServletRequest req){
		String uri = req.getRequestURI(); //command ex)ex01/sboard/register
		String query = req.getQueryString(); //매개변수
		
		if (query == null || query.equals("null")) {
			query = "";
		}else{
			query = "?" + query; //매개변수를 받아올 때 ?는 포함되지 않은채로 오기때문에
		}
		
		if (req.getMethod().equals("GET")) {
			logger.info("dest : "+(uri + query));
			
//			매개변수가 존재한다면 매개변수값까지 모두 기억하기
			req.getSession().setAttribute("dest", uri+query);
		}
	}
}
