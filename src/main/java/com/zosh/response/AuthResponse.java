package com.zosh.response;

public class AuthResponse {
	
	private String jwt;
	private String message;
	
	
	public AuthResponse() {
		super();
		this.jwt=jwt;
		this.message=message;
		// TODO Auto-generated constructor stub
	}


	public String getJwt() {
		return jwt;
	}


	public void setJwt(String jwt) {
		this.jwt = jwt;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	

}
