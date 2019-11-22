/*
 * Copyright, 2019, DI Hofmann Melanie - All Rights Reserved.
 */
package com.hofmann.melanie;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.hofmann.melanie.analytics.TvSpotService;

public class Application {

	public static void main(String[] args) {
		System.out.println("Starting Coding Challange: Impact of TV ads. By DI Melanie Hofmann");
		long startMilliseconds = System.currentTimeMillis();

		Injector injector = Guice.createInjector(new BasicModule());
		TvSpotService tvSpotService = injector.getInstance(TvSpotService.class);

		boolean largeSet = args.length > 0 && args[0].equals("true");

		tvSpotService.analyseTvSpots(largeSet);
		long endMilliseconds = System.currentTimeMillis();

		System.out.println(
				String.format("Analysis took %d ms.", endMilliseconds - startMilliseconds));

		System.out.println("Closing Application.");

	}

}
