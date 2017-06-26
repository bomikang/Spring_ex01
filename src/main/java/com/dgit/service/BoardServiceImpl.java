package com.dgit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.dgit.domain.BoardVO;
import com.dgit.domain.Criteria;
import com.dgit.domain.SearchCriteria;
import com.dgit.persistence.BoardDao;

@Service
public class BoardServiceImpl implements BoardService{
	@Autowired
	private BoardDao dao;
	
	@Transactional
	@Override
	public void regist(BoardVO board) throws Exception {
		dao.create(board);
		
//		첨부 파일 추가
		String[] files = board.getFiles();
		if (files == null) {
			return;
		}
		for (String fileName : files) {
			dao.addAttach(fileName);
		}
	}

//	@Transactional(isolation=Isolation.READ_COMMITTED) : 커밋된 data에 대해 일기 허용
	@Transactional
	@Override
	public BoardVO read(Integer bno) throws Exception {
		BoardVO vo = dao.read(bno);
		
//		이미지 파일을 각져와 뿌림
		List<String> files = dao.getAttach(bno);
		vo.setFiles(files.toArray(new String[files.size()]));
		return vo;
	}

	@Transactional
	@Override
	public void modify(BoardVO board) throws Exception {
		/*삭제된 이미지 처리*/
		String[] files = board.getFiles();
		int newFileIndex = 0;
		
		if (files != null) {
			List<String> getAttachList = dao.getAttach(board.getBno());
			
			for (int i = 0; i < getAttachList.size(); i++) {
				for (int j = 0; j < files.length; j++) {
//					같은 이름 있다면 그건 삭제할 파일
					if (files[j].equals(getAttachList.get(i))) {
						dao.removeEachAttach(files[j], board.getBno());
//						삭제할 파일 부터 배열에 넣었기 때문에 마지막 번째 인덱스의 다음번째가 새롭게 생성될 파일
						newFileIndex = j+1; 
					}
				}
			}
			for (int i = 0; i < files.length; i++) {
				if (i >= newFileIndex) {
					dao.addAttachWhenUpdate(files[i], board.getBno());
				}
			}
		}
		dao.update(board);
	}

	@Transactional
	@Override
	public void remove(Integer bno) throws Exception {
		dao.removeAttach(bno);
		dao.delete(bno);
	}

	@Override
	public List<BoardVO> listAll() throws Exception {
		return dao.listAll();
	}

	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		return dao.listCriteria(cri);
	}

	@Override
	public int countPaging() throws Exception {
		return dao.countPaging();
	}

	@Override
	public void countViewcnt(int bno) throws Exception {
		dao.countViewcnt(bno);
	}

	@Override
	public List<BoardVO> listSearch(SearchCriteria cri) throws Exception {
		return dao.listSearch(cri);
	}

	@Override
	public int listSearchCount(SearchCriteria cri) throws Exception {
		return dao.listSearchCount(cri);
	}

	@Override
	public void updateReplyCnt(Integer bno, int amount) throws Exception {
		dao.updateReplyCnt(bno, amount);
	}

}
