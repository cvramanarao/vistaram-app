package com.vistaram.relational.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the room_details database table.
 * 
 */
@Entity
@Table(name="room_details")
@NamedQuery(name="RoomDetail.findAll", query="SELECT r FROM RoomDetail r")
public class RoomDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="room_name", nullable=false, length=50)
	private String roomName;

	@Column(name="room_rate", nullable=false, length=20)
	private String roomRate;

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

	public String getRoomName() {
		return this.roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomRate() {
		return this.roomRate;
	}

	public void setRoomRate(String roomRate) {
		this.roomRate = roomRate;
	}

	public String getRoom_Type() {
		return this.room_Type;
	}

	public void setRoom_Type(String room_Type) {
		this.room_Type = room_Type;
	}

}