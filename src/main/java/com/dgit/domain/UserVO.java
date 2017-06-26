package com.dgit.domain;

public class UserVO {
	private String uid; //사용자 아이디
	private String upw; //사용자 비밀번호
	private int upoint;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUpw() {
		return upw;
	}
	public void setUpw(String upw) {
		this.upw = upw;
	}
	public int getUpoint() {
		return upoint;
	}
	public void setUpoint(int upoint) {
		this.upoint = upoint;
	}
	
	@Override
	public String toString() {
		return "UserVO [uid=" + uid + ", upw=" + upw + ", upoint=" + upoint + "]";
	}

	
	

}
