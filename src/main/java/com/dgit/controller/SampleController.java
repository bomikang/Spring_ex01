package com.dgit.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dgit.domain.BoardVO;

@RestController //뷰가 없고 data만 넘길 때
@RequestMapping("/sample/*")
public class SampleController{
	
	@RequestMapping("hello")
	public String sayHello(){
		return "Hello World";
	}
	
	@RequestMapping("boardVO")
	public BoardVO sendBoardVO(){
		BoardVO vo = new BoardVO();
		vo.setTitle("제이슨으로보낼거당");
		vo.setContent("그렇다");
		vo.setWriter("강보미");
		vo.setBno(1);
		vo.setViewcnt(10);
		vo.setRegdate(new Date());
		
		return vo;
	}
	
	@RequestMapping("sendMap")
	public Map<Integer, BoardVO> sendMap(){
		Map<Integer, BoardVO> map = new HashMap<Integer, BoardVO>();
		
		for (int i = 0; i < 10; i++) {
			BoardVO vo = new BoardVO();
			vo.setTitle("---제이슨으로보낼거당" + i + "---");
			vo.setContent("그렇다");
			vo.setWriter("강보미");
			vo.setBno(1);
			vo.setViewcnt(10);
			vo.setRegdate(new Date());
			
			map.put(i, vo);
		}
		return map;
	}
	
	@RequestMapping("sendError")
	public ResponseEntity<Void> sendListAuth(){
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);//400에러
	}
	
	@RequestMapping("sendErrorNot")
	public ResponseEntity<List<BoardVO>> sendListNot(){
		List<BoardVO> list = new ArrayList<BoardVO>();
		
		for (int i = 0; i < 10; i++) {
			BoardVO vo = new BoardVO();
			vo.setTitle("---제이슨으로보낼거당" + i + "---");
			vo.setContent("그렇다");
			vo.setWriter("강보미");
			vo.setBno(1);
			vo.setViewcnt(10);
			vo.setRegdate(new Date());
			
			list.add(vo);
		}
		return new ResponseEntity<List<BoardVO>>(list, HttpStatus.NOT_FOUND);//404에러
	}
}

