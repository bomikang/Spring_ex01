package com.dgit.service;

import com.dgit.domain.LoginDTO;
import com.dgit.domain.UserVO;

public interface UserService {
	UserVO login(LoginDTO dto) throws Exception;
}
