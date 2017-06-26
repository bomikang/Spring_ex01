package com.dgit.service;

import java.util.List;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.SearchCriteria;

public interface BoardService {
	void regist(BoardVO board) throws Exception;
	BoardVO read(Integer bno) throws Exception;
	void modify(BoardVO board) throws Exception;
	void remove(Integer bno) throws Exception;
	List<BoardVO> listAll() throws Exception;
	List<BoardVO> listCriteria(Criteria cri) throws Exception;
	int countPaging() throws Exception;
	void countViewcnt(int bno) throws Exception;
	
	/* 검색(sboard) */
	List<BoardVO> listSearch(SearchCriteria cri) throws Exception;
	int listSearchCount(SearchCriteria cri) throws Exception;
	
	void updateReplyCnt(Integer bno, int amount) throws Exception;
}
