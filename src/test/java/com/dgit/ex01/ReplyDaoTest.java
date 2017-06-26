package com.dgit.ex01;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dgit.domain.ReplyVO;
import com.dgit.persistence.ReplyDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class ReplyDaoTest {
	@Autowired
	private ReplyDao dao;
	
	//@Test
	public void listTest() throws Exception{
		dao.list(6);
	}
	
	//@Test
	public void  createTest() throws Exception{
		ReplyVO vo = new ReplyVO();
		vo.setBno(6);
		vo.setReplyer("강보미");
		vo.setReplytext("강보미가 남긴 댓글");
		
		dao.create(vo);
	}
	
	//@Test
	public void updateTest() throws Exception{
		List<ReplyVO> list = dao.list(6);
		ReplyVO vo = list.get(0);
		vo.setReplytext("강보미가 댓글 수정함");
		
		dao.update(vo);
	}
	
	//@Test
	public void deleteTest() throws Exception{
		dao.delete(1);
	}
}
