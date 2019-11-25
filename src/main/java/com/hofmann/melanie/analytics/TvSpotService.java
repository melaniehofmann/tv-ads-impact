/*
 * Copyright, 2019, DI Hofmann Melanie - All Rights Reserved.
 */
package com.hofmann.melanie.analytics;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.hofmann.melanie.file.JsonMapper;
import com.hofmann.melanie.file.SimpleJsonReader;
import com.hofmann.melanie.spot.TvSpot;
import com.hofmann.melanie.spot.User;
import com.hofmann.melanie.utils.DateTimeUtils;

public class TvSpotService {
	private static final String SPOT_INFORMATION_FILE = "new_users.json";
	private static final String SPOT_INFORMATION_LARGE_FILE = "new_users_large_set.json";

	// time period for analysis in milliseconds -> 10 min
	private static final int TIME_PERIOD = 600000;

	public void analyseTvSpots(final boolean useLargeSet) {
		String setToUse = useLargeSet ? SPOT_INFORMATION_LARGE_FILE : SPOT_INFORMATION_FILE;

		System.out.println("Starting TV Spot Analysis for set: " + setToUse);

		final SimpleJsonReader jsonReader = new SimpleJsonReader();
		final JsonMapper jsonMapper = new JsonMapper();
		
		JSONObject json = jsonReader.readFileAsJsonObject(setToUse);

		ArrayList<TvSpot> tvSpots = jsonMapper.mapObject((JSONArray) json.get("tvSpots"),
				TvSpot.class);
		ArrayList<User> users = jsonMapper.mapObject((JSONArray) json.get("newUsers"), User.class);

		tvSpots.parallelStream().forEach(t -> calculateTvSpotUsers(users, t));
		
		// Get best spot
		tvSpots.stream()
		.max(Comparator.comparing(TvSpot::getNewUsers))
		.ifPresent((t) -> System.out.println(String.format("Spot that worked best with %d new users, is Spot %s",
				t.getNewUsers(), t.getId())));
	}

	
	private void calculateTvSpotUsers(final ArrayList<User> users, final TvSpot tvSpot) {
		ArrayList<User> usersBeforeSpot = countUsersInTimeRange(getTimeRangeBeforeSpot(tvSpot),
				users);
		ArrayList<User> usersAfterSpot = countUsersInTimeRange(getTimeRangeAfterSpot(tvSpot),
				users);

		int newUsers = usersAfterSpot.size() - usersBeforeSpot.size();

		tvSpot.setNewUsers(newUsers);

		System.out.println(String.format("Spot %S: %d new users", tvSpot.getId(), newUsers));
	}
	
	private ArrayList<User> countUsersInTimeRange(final Range range, final ArrayList<User> users) {
		return users.parallelStream().filter(u -> DateTimeUtils.inTimeRange(range, u.getTime()))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	private Range getTimeRangeBeforeSpot(final TvSpot tvSpot) {
		long timeTo = DateTimeUtils.dateTimeToMs(tvSpot.getTime());
		long timeFrom = timeTo - TIME_PERIOD;

		return new Range(timeFrom, timeTo);
	}

	private Range getTimeRangeAfterSpot(final TvSpot tvSpot) {
		long timeFrom = DateTimeUtils.dateTimeToMs(tvSpot.getTime());
		long timeTo = DateTimeUtils.dateTimeToMs(tvSpot.getTime()) + TIME_PERIOD;

		return new Range(timeFrom, timeTo);
	}
}
