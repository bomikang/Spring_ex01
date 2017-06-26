package com.dgit.persistence;

import java.util.List;

import com.dgit.domain.Criteria;
import com.dgit.domain.ReplyVO;

public interface ReplyDao {
	List<ReplyVO> list(Integer bno) throws Exception;
	void create(ReplyVO vo) throws Exception;
	void update(ReplyVO vo) throws Exception;
	void delete(Integer rno) throws Exception;
	List<ReplyVO> listPage(Integer bno, Criteria cri) throws Exception;
	int count(Integer bno) throws Exception;
	ReplyVO readReply(Integer rno) throws Exception;
}
