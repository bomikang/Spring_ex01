package com.dgit.ex01;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dgit.domain.LoginDTO;
import com.dgit.persistence.UserDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class UserDaoTest {
	@Autowired
	private UserDao dao;
	
	@Test
	public void loginTest() throws Exception{
		LoginDTO dto = new LoginDTO();
		dto.setUid("bomi");
		dto.setUpw("bomi");
		dao.login(dto);
	}
	
}
