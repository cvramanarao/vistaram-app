package com.vistaram.data.relational.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String username;

	private Boolean enabled;

	private String password;

	//bi-directional many-to-one association to Authority
	@OneToMany(mappedBy="user")
	private List<Authority> authorities;

	//bi-directional one-to-one association to HotelDetail
	@OneToOne(mappedBy="user")
	private HotelDetail hotelDetail;

	public User() {
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Authority> getAuthorities() {
		return this.authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	public Authority addAuthority(Authority authority) {
		getAuthorities().add(authority);
		authority.setUser(this);

		return authority;
	}

	public Authority removeAuthority(Authority authority) {
		getAuthorities().remove(authority);
		authority.setUser(null);

		return authority;
	}

	public HotelDetail getHotelDetail() {
		return this.hotelDetail;
	}

	public void setHotelDetail(HotelDetail hotelDetail) {
		this.hotelDetail = hotelDetail;
	}

}