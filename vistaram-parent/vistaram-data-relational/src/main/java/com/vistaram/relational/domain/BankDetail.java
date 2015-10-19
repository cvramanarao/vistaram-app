package com.vistaram.relational.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Bank_Details database table.
 * 
 */
@Entity
@Table(name="Bank_Details")
@NamedQuery(name="BankDetail.findAll", query="SELECT b FROM BankDetail b")
public class BankDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=120)
	private String account_Name;

	@Column(nullable=false)
	private int account_Number;

	@Column(nullable=false, length=150)
	private String bank_Name;

	@Column(name="guest_details_id", nullable=false)
	private int guestDetailsId;

	@Column(name="IFSC_CODE", nullable=false, length=11)
	private String ifscCode;

	public BankDetail() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccount_Name() {
		return this.account_Name;
	}

	public void setAccount_Name(String account_Name) {
		this.account_Name = account_Name;
	}

	public int getAccount_Number() {
		return this.account_Number;
	}

	public void setAccount_Number(int account_Number) {
		this.account_Number = account_Number;
	}

	public String getBank_Name() {
		return this.bank_Name;
	}

	public void setBank_Name(String bank_Name) {
		this.bank_Name = bank_Name;
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