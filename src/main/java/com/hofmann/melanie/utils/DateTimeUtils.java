/*
 * Copyright, 2019, DI Hofmann Melanie - All Rights Reserved.
 */
package com.hofmann.melanie.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.hofmann.melanie.analytics.Range;

public class DateTimeUtils {

	public static long dateTimeToMs(final LocalDateTime dateTime) {
		return dateTime.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli();
	}

	public static boolean inTimeRange(final Range range, final LocalDateTime dateTime) {
		return range.isInRange(dateTimeToMs(dateTime));
	}
}
