/*
 * Copyright, 2019, DI Hofmann Melanie - All Rights Reserved.
 */
package com.hofmann.melanie;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.hofmann.melanie.analytics.TvSpotService;

public class Application {
	private static final Logger LOG = LogManager.getLogger(Application.class);

	public static void main(String[] args) {
		LOG.info("Starting Coding Challange: Impact of TV ads. By DI Melanie Hofmann");
		long startMilliseconds = System.currentTimeMillis();
		
		Injector injector = Guice.createInjector(new BasicModule());
		TvSpotService tvSpotService = injector.getInstance(TvSpotService.class);
		
		boolean largeSet = args.length > 0 && args[0].equals("true");
				
		tvSpotService.analyseTvSpots(largeSet);
		long endMilliseconds = System.currentTimeMillis();

		LOG.info(String.format("Analysis took %d ms.", endMilliseconds - startMilliseconds));

		LOG.info("Closing Application.");

	}

}
