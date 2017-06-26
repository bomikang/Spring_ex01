package com.dgit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgit.domain.Criteria;
import com.dgit.domain.ReplyVO;
import com.dgit.persistence.BoardDao;
import com.dgit.persistence.ReplyDao;

@Service
public class ReplyServiceImpl implements ReplyService{
	@Autowired
	private ReplyDao dao;
	@Autowired
	private BoardDao boardDao;

	@Override
	public List<ReplyVO> listReply(Integer bno) throws Exception {
		return dao.list(bno);
	}

	@Override
	@Transactional
	public void addReply(ReplyVO vo) throws Exception {
		//댓글 추가 후 댓글 수 + 1 증가
		dao.create(vo);
		boardDao.updateReplyCnt(vo.getBno(), 1);
	}

	@Override
	public void modifyReply(ReplyVO vo) throws Exception {
		dao.update(vo);
	}

	@Override
	@Transactional
	public void removeReply(Integer rno) throws Exception {
		//댓글삭제 후 댓글수 -1
		ReplyVO vo = dao.readReply(rno);
		Integer bno = vo.getBno();
		dao.delete(bno);
		
		boardDao.updateReplyCnt(bno, -1);
	}

	@Override
	public List<ReplyVO> listReplyPage(Integer bno, Criteria cri) throws Exception {
		return dao.listPage(bno, cri);
	}

	@Override
	public int count(Integer bno) throws Exception {
		return dao.count(bno);
	}

	@Override
	public ReplyVO readReply(Integer rno) throws Exception {
		return dao.readReply(rno);
	}

}
