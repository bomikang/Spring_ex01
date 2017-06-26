package com.dgit.domain;

public class Criteria {
	private int page; //현재 페이지 번호
	private int perPageNum; //페이지에 보일 게이물 개수
	
	public Criteria() {
		this.page = 1;
		this.perPageNum = 10;
	}
	//constructor

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		if (page <= 0) {
			this.page = 1;
			return;
		}
		this.page = page;
	}

	public int getPerPageNum() {
		return perPageNum;
	}

	public void setPerPageNum(int perPageNum) {
		if (perPageNum <= 0 || perPageNum > 100) {
			this.perPageNum = 10;
			return;
		}
		this.perPageNum = perPageNum;
	}
	//getter setter
	
	/*시작 게시물 인덱스*/
	public int getPageStart(){
		return (this.page - 1) * perPageNum;
	}
	//method

	@Override
	public String toString() {
		return "Criteria [page=" + page + ", perPageNum=" + perPageNum + "]";
	}
}
