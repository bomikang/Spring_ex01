package com.dgit.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dgit.domain.LoginDTO;
import com.dgit.domain.UserVO;
import com.dgit.interceptor.LoginInterceptor;
import com.dgit.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Inject
	private UserService service;
	
	@Autowired
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public void loginGet(@ModelAttribute("dto") LoginDTO dto){
		logger.info("login GET.........");
	}
	
	@RequestMapping(value="/loginPost", method=RequestMethod.POST)
	public void loginPost(LoginDTO dto, Model model) throws Exception{
		logger.info("login POST.........");
		
		UserVO vo = service.login(dto);
		
//		가입이 되어있는 회원인 지 확인
		if (vo == null) return;
		
		model.addAttribute("userVO", vo);
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logoutGet(HttpSession session) throws Exception{
		logger.info("logout GET.........");
		
		UserVO vo = (UserVO) session.getAttribute(LoginInterceptor.LOGIN);
		
		if (vo != null) {
			logger.info(vo.toString());
			session.removeAttribute(LoginInterceptor.LOGIN);
			session.invalidate();
		}
		return "redirect:/user/login";
	}
}
