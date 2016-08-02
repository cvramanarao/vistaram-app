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
	private String username;

	private byte enabled;

	private String password;

	//bi-directional many-to-one association to Authority
	@OneToMany(mappedBy="user")
	private List<Authority> authorities;

	//bi-directional many-to-one association to HotelDetail
	@OneToMany(mappedBy="user")
	private List<HotelDetail> hotelDetails;

	public User() {
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public byte getEnabled() {
		return this.enabled;
	}

	public void setEnabled(byte enabled) {
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

	public List<HotelDetail> getHotelDetails() {
		return this.hotelDetails;
	}

	public void setHotelDetails(List<HotelDetail> hotelDetails) {
		this.hotelDetails = hotelDetails;
	}

	public HotelDetail addHotelDetail(HotelDetail hotelDetail) {
		getHotelDetails().add(hotelDetail);
		hotelDetail.setUser(this);

		return hotelDetail;
	}

	public HotelDetail removeHotelDetail(HotelDetail hotelDetail) {
		getHotelDetails().remove(hotelDetail);
		hotelDetail.setUser(null);

		return hotelDetail;
	}

}