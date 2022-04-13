package com.redhat.fuse.boosters.rest.http.pojos;

public class FundRequest {

	public FundRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	private String requestType;
	private String accountType;
	private Double amount;
	private String origin;
	private String destiny;
	
	
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestiny() {
		return destiny;
	}
	public void setDestiny(String destiny) {
		this.destiny = destiny;
	}
	public FundRequest(String requestType, String accountType, Double amount, String origin, String destiny) {
		super();
		this.requestType = requestType;
		this.accountType = accountType;
		this.amount = amount;
		this.origin = origin;
		this.destiny = destiny;
	}


}
