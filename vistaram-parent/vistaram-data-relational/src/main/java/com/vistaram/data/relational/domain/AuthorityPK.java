package com.vistaram.data.relational.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the authorities database table.
 * 
 */
@Embeddable
public class AuthorityPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private String username;

	private String authority;

	public AuthorityPK() {
	}
	public String getUsername() {
		return this.username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAuthority() {
		return this.authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AuthorityPK)) {
			return false;
		}
		AuthorityPK castOther = (AuthorityPK)other;
		return 
			this.username.equals(castOther.username)
			&& this.authority.equals(castOther.authority);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.username.hashCode();
		hash = hash * prime + this.authority.hashCode();
		
		return hash;
	}
}