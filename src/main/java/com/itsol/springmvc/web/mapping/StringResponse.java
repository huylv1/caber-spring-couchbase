package com.itsol.springmvc.web.mapping;

public class StringResponse {
	private String response;
    public StringResponse(String s) { 
       this.response = s;
    }
    
	/**
	 * @return the response
	 */
	public String getResponse() {
		return response;
	}
	
	/**
	 * @param response the response to set
	 */
	public void setResponse(String response) {
		this.response = response;
	}
}
