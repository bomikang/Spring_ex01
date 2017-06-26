package com.dgit.persistence;

import com.dgit.domain.LoginDTO;
import com.dgit.domain.UserVO;

public interface UserDao {
	UserVO login(LoginDTO dto) throws Exception;
}
