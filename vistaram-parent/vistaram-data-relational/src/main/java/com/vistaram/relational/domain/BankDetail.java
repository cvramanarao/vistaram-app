package com.vistaram.relational.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the bank_details database table.
 * 
 */
@Entity
@Table(name="bank_details")
@NamedQuery(name="BankDetail.findAll", query="SELECT b FROM BankDetail b")
public class BankDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="account_name", nullable=false, length=120)
	private String accountName;

	@Column(name="account_number", nullable=false)
	private int accountNumber;

	@Column(name="bank_name", nullable=false, length=150)
	private String bankName;

	@Column(name="guest_details_id", nullable=false)
	private int guestDetailsId;

	@Column(name="ifsc_code", nullable=false, length=11)
	private String ifscCode;

	public BankDetail() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccountName() {
		return this.accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public int getAccountNumber() {
		return this.accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public int getGuestDetailsId() {
		return this.guestDetailsId;
	}

	public void setGuestDetailsId(int guestDetailsId) {
		this.guestDetailsId = guestDetailsId;
	}

	public String getIfscCode() {
		return this.ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

}