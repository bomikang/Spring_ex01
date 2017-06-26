package com.dgit.service;

import java.util.List;

import com.dgit.domain.Criteria;
import com.dgit.domain.ReplyVO;

public interface ReplyService {
	List<ReplyVO> listReply(Integer bno) throws Exception;
	void addReply(ReplyVO vo) throws Exception;
	void modifyReply(ReplyVO vo) throws Exception;
	void removeReply(Integer rno) throws Exception;
	List<ReplyVO> listReplyPage(Integer bno, Criteria cri) throws Exception;
	int count(Integer bno) throws Exception;
	ReplyVO readReply(Integer rno) throws Exception;
}
