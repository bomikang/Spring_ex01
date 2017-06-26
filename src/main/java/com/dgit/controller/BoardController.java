package com.dgit.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.PageMaker;
import com.dgit.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	@Inject
	BoardService service;
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@RequestMapping(value="register", method=RequestMethod.GET)
	public void registerGet(){
		logger.info("register GET.....");
	}
	
	@RequestMapping(value="register", method=RequestMethod.POST)
	public String registerPost(BoardVO vo, RedirectAttributes rttr) throws Exception{
		logger.info("register POST.....");
		service.regist(vo);
		//addFlashAttribute는 한번 하고 사라짐
		rttr.addFlashAttribute("result", "success"); //alert 띄워지게 하기 위해
		
		return "redirect:/board/listAll";
	}
	
	@RequestMapping(value="listAll", method=RequestMethod.GET)
	public void listAll(Model model) throws Exception{
		logger.info("show all list.....");
		
		model.addAttribute("list", service.listAll());
	}
	
	@RequestMapping(value="listCri", method=RequestMethod.GET)
	public String listCri(Criteria cri, Model model) throws Exception{
		logger.info("show criteria list.....");
		
		model.addAttribute("list", service.listCriteria(cri));
		
		return "/board/listAll";
	}
	
	@RequestMapping(value="listPage", method=RequestMethod.GET)
	public String listPage(Criteria cri, Model model) throws Exception{
		logger.info("show page list.....");
		model.addAttribute("list", service.listCriteria(cri));
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.countPaging()); //게시물 전체 개수
		
		model.addAttribute("pageMaker", pageMaker);
		
		return "/board/listAll";
	}
	
	@RequestMapping(value="read", method=RequestMethod.GET)
	public void read(int bno, @ModelAttribute("cri") Criteria cri, Model model) throws Exception{
		logger.info("read content.....");
		
		service.countViewcnt(bno);
		
		model.addAttribute("boardVO", service.read(bno));
	}
	
	@RequestMapping(value="remove", method=RequestMethod.POST)
	public String remove(int bno, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) throws Exception{
		logger.info("remove content.....");
		service.remove(bno);
		rttr.addFlashAttribute("result", "success"); //alert 띄워지게 하기 위해
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		
		return "redirect:/board/listPage"; //?page=xxx&perPageNum=xxx
	}
	
	@RequestMapping(value="modify", method=RequestMethod.GET)
	public void modify(int bno, @ModelAttribute("cri") Criteria cri, Model model) throws Exception{
		logger.info("modify GET.....");
		model.addAttribute("boardVO", service.read(bno));
	}
	
	@RequestMapping(value="modify", method=RequestMethod.POST)
	public String modify(BoardVO vo, @ModelAttribute("cri") Criteria cri, Model model) throws Exception{
		logger.info("modify POST.....");
		BoardVO newBoard = service.read(vo.getBno());
		newBoard.setTitle(vo.getTitle());
		newBoard.setContent(vo.getContent());
		newBoard.setWriter(vo.getWriter());
		
		service.modify(newBoard);
		
		model.addAttribute("boardVO", service.read(newBoard.getBno()));
		return "/board/read";
	}
}
