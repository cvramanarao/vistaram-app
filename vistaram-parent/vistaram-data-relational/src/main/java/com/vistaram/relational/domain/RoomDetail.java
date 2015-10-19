package com.vistaram.relational.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Room_Details database table.
 * 
 */
@Entity
@Table(name="Room_Details")
@NamedQuery(name="RoomDetail.findAll", query="SELECT r FROM RoomDetail r")
public class RoomDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=50)
	private String room_Name;

	@Column(nullable=false, length=20)
	private String room_Rate;

	@Column(nullable=false, length=30)
	private String room_Type;

	public RoomDetail() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoom_Name() {
		return this.room_Name;
	}

	public void setRoom_Name(String room_Name) {
		this.room_Name = room_Name;
	}

	public String getRoom_Rate() {
		return this.room_Rate;
	}

	public void setRoom_Rate(String room_Rate) {
		this.room_Rate = room_Rate;
	}

	public String getRoom_Type() {
		return this.room_Type;
	}

	public void setRoom_Type(String room_Type) {
		this.room_Type = room_Type;
	}

}