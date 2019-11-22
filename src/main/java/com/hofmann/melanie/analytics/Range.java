/*
 * Copyright, 2019, DI Hofmann Melanie - All Rights Reserved.
 */
package com.hofmann.melanie.analytics;

public class Range {

	private long from;
	private long to;
	
	Range(long from, long to) {
		this.from = from;
		this.to = to;
	}
	
	public boolean isInRange(long value) {
		return value >= from && value <= to;
	}
}
