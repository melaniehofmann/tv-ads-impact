/*
 * Copyright, 2019, DI Hofmann Melanie - All Rights Reserved.
 */
package com.hofmann.melanie.spot;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

public class TvSpot implements Id, Time {
	private int spotId;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)  
	@JsonSerialize(using = LocalDateTimeSerializer.class)  
	private LocalDateTime time;

	private int newUsers;
	
	public TvSpot() {
		
	}
	
	public TvSpot(int spotId, LocalDateTime time) {
		this.spotId = spotId;
		this.time = time;
	}

	@Override
	public LocalDateTime getTime() {
		return time;
	}

	@Override
	public void setTime(final LocalDateTime time) {
		this.time = time;
	}

	@Override
	public int getId() {
		return spotId;
	}

	@JsonSetter("spotId")
	@Override
	public void setId(int id) {
		this.spotId = id;
	}

	public int getNewUsers() {
		return newUsers;
	}

	public void setNewUsers(int newUsers) {
		this.newUsers = newUsers;
	}
}
