/*
 * Copyright, 2019, DI Hofmann Melanie - All Rights Reserved.
 */
package com.hofmann.melanie.spot;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

public class User implements Id, Time {
	private int userId;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)  
	@JsonSerialize(using = LocalDateTimeSerializer.class)  
	private LocalDateTime time;

	public User() {

	}

	public User(final int userId, final LocalDateTime time) {
		this.userId = userId;
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
		return userId;
	}

	@JsonSetter("userId")
	@Override
	public void setId(final int id) {
		this.userId = id;
	}

}
