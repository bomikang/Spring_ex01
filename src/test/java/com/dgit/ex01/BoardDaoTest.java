package com.dgit.ex01;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.persistence.BoardDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class BoardDaoTest {
	@Autowired
	private BoardDao dao;
	
	//@Test
	public void createTest() throws Exception{
		BoardVO vo = new BoardVO();
		vo.setTitle("test03");
		vo.setContent("test03");
		vo.setWriter("test03");
		
		dao.create(vo);
	}
	
	//@Test
	public void readTest() throws Exception{
		dao.read(1);
	}
	
	//@Test
	public void updateTest() throws Exception {
		BoardVO vo = dao.read(1);
		vo.setTitle("test01수정");
		vo.setContent("test01수정");
		System.out.println("time-"+vo.getRegdate());
		
		dao.update(vo);
	}
	
	//@Test
	public void deleteTest() throws Exception {
		dao.delete(1);
	}
	
	//@Test
	public void listAllTest() throws Exception{
		dao.listAll();
	}
	
	//@Test
	public void listPageTest() throws Exception{
		dao.listPage(1);
	}
	
	//@Test
	public void listCriteriaTest() throws Exception{
		Criteria cri = new Criteria();
		cri.setPage(3);
		cri.setPerPageNum(30);
		
		dao.listCriteria(cri);
	}
	
	//@Test
	public void countPagingTest() throws Exception{
		dao.countPaging();
	}
	
	@Test
	public void countViewcntTest() throws Exception{
		dao.countViewcnt(6);
	}
}
