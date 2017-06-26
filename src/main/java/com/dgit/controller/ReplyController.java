package com.dgit.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dgit.domain.Criteria;
import com.dgit.domain.PageMaker;
import com.dgit.domain.ReplyVO;
import com.dgit.service.BoardService;
import com.dgit.service.ReplyService;

@RestController // 뷰가 없고 data만 넘길 때
@RequestMapping("/replies")
public class ReplyController {
	private static final Logger logger = LoggerFactory.getLogger(ReplyController.class);

	@Inject
	private ReplyService service;
	
	@Inject
	private BoardService boardService;

	//@RequestBody => json형태로 받아옴 (주로 post, put일 때 사용)
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<String> addReply(@RequestBody ReplyVO vo){
		ResponseEntity<String> entity = null;
		
		try{
			service.addReply(vo);
			
			entity = new ResponseEntity<String>("insert SUCCESS", HttpStatus.OK); //200 : 에러아님
		}catch(Exception e){
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST); //400
		}
		return entity;
	}//addReply
	
	//@PathVariable => replies/all/1798에서 1798을 가져옴
	@RequestMapping(value = "/all/{bno}", method = RequestMethod.GET)
	public ResponseEntity<List<ReplyVO>> list(@PathVariable("bno") int bno) {
      ResponseEntity<List<ReplyVO>> entity = null;
      try {
         entity = new ResponseEntity<>(service.listReply(bno), HttpStatus.OK);
      } catch (Exception e) {
         e.printStackTrace();
         entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);// 404
      }
      return entity;
   }//list
	
	@RequestMapping(value="/{bno}/{page}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listPage(@PathVariable("bno") Integer bno, @PathVariable("page") Integer page){
		ResponseEntity<Map<String, Object>> entity = null;
		
		try {
			Criteria cri = new Criteria();
			cri.setPage(page);
			
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(cri);
			pageMaker.setTotalCount(service.count(bno));
			
			Map<String, Object> map = new HashMap<>();
			List<ReplyVO> list = service.listReplyPage(bno, cri);
			
			map.put("list", list);
			map.put("pageMaker", pageMaker);
			
			entity = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}//listPage
	
	@RequestMapping(value="/{rno}", method = RequestMethod.PUT)
	public ResponseEntity<String> update(@PathVariable("rno") Integer rno, @RequestBody ReplyVO vo){
		ResponseEntity<String> entity = null;
		vo.setRno(rno);
		
		try {
			service.modifyReply(vo);
			entity = new ResponseEntity<String>("update SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>("FAIL", HttpStatus.BAD_REQUEST);
		}
		return entity;
	}//update
	
	@RequestMapping(value="/{rno}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("rno") Integer rno){
		ResponseEntity<String> entity = null;
		try {
			service.removeReply(rno);
			
			entity = new ResponseEntity<>("delete SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
		}
		return entity;
	}//delete
}
