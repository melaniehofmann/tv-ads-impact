/*
 * Copyright, 2019, DI Hofmann Melanie - All Rights Reserved.
 */
package com.hofmann.melanie.analytics;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.inject.Inject;
import com.hofmann.melanie.file.JsonMapper;
import com.hofmann.melanie.file.SimpleJsonReader;
import com.hofmann.melanie.spot.TvSpot;
import com.hofmann.melanie.spot.User;
import com.hofmann.melanie.utils.DateTimeUtils;

public class TvSpotService {
	private static final Logger LOG = LogManager.getLogger(TvSpotService.class);

	private static final String SPOT_INFORMATION_FILE = "new_users.json";
	private static final String SPOT_INFORMATION_LARGE_FILE = "new_users_large_set.json";

	// time period for analysis in milliseconds -> 10 min
	private static final int TIME_PERIOD = 600000;

	@Inject
	private SimpleJsonReader jsonReader;

	@Inject
	private JsonMapper jsonMapper;

	public void analyseTvSpots(boolean useLargeSet) {
		String setToUse = useLargeSet ? SPOT_INFORMATION_LARGE_FILE : SPOT_INFORMATION_FILE;
		
		LOG.info("Starting TV Spot Analysis for set: " + setToUse);
		
		JSONObject json = jsonReader.readFileAsJsonObject(setToUse);

		ArrayList<TvSpot> tvSpots = jsonMapper.mapObject((JSONArray) json.get("tvSpots"),
				TvSpot.class);
		ArrayList<User> users = jsonMapper.mapObject((JSONArray) json.get("newUsers"), User.class);

		TvSpot bestSpot = null;

		for (TvSpot tvSpot : tvSpots) {
			ArrayList<User> usersBeforeSpot = countUsersInTimeRange(getTimeRangeBeforeSpot(tvSpot),
					users);
			ArrayList<User> usersAfterSpot = countUsersInTimeRange(getTimeRangeAfterSpot(tvSpot),
					users);

			int newUsers = usersAfterSpot.size() - usersBeforeSpot.size();

			tvSpot.setNewUsers(newUsers);
			
			if (bestSpot == null || bestSpot.getNewUsers() < tvSpot.getNewUsers()) {
				bestSpot = tvSpot;
			}

			LOG.info(String.format("Spot %S: %d new users", tvSpot.getId(), newUsers));
		}

		if (bestSpot != null) {
			LOG.info(String.format("Spot that worked best with %d new users, is Spot %s",
					bestSpot.getNewUsers(), bestSpot.getId()));
		}
	}

	private ArrayList<User> countUsersInTimeRange(Range range, ArrayList<User> users) {
		return users.stream().filter(u -> DateTimeUtils.inTimeRange(range, u.getTime()))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	private Range getTimeRangeBeforeSpot(TvSpot tvSpot) {
		long timeTo = DateTimeUtils.dateTimeToMs(tvSpot.getTime());
		long timeFrom = timeTo - TIME_PERIOD;

		return new Range(timeFrom, timeTo);
	}

	private Range getTimeRangeAfterSpot(TvSpot tvSpot) {
		long timeFrom = DateTimeUtils.dateTimeToMs(tvSpot.getTime());
		long timeTo = DateTimeUtils.dateTimeToMs(tvSpot.getTime()) + TIME_PERIOD;

		return new Range(timeFrom, timeTo);
	}
}
