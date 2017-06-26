package com.dgit.persistence;

import java.util.List;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.SearchCriteria;

public interface BoardDao {
	void create(BoardVO vo) throws Exception;
	BoardVO read(Integer bno) throws Exception;
	void update(BoardVO vo) throws Exception;
	void delete(Integer bno) throws Exception;
	List<BoardVO> listAll() throws Exception;
	List<BoardVO> listPage(int page) throws Exception;
	List<BoardVO> listCriteria(Criteria cri) throws Exception;
	int countPaging() throws Exception;
	void countViewcnt(int bno) throws Exception;
	
	/* 검색(sboard) */
	List<BoardVO> listSearch(SearchCriteria cri) throws Exception;
	int listSearchCount(SearchCriteria cri) throws Exception;
	
	void updateReplyCnt(Integer bno, int amount) throws Exception;
	
	/*파일 넣기*/
	void addAttach(String fullName) throws Exception;
	void addAttachWhenUpdate(String fullName, Integer bno) throws Exception;
	
	/*파일받기*/
	List<String> getAttach(Integer bno) throws Exception;
	
	/*파일삭제*/
	void removeAttach(Integer bno) throws Exception;
	void removeEachAttach(String fullName, Integer bno) throws Exception;
}
