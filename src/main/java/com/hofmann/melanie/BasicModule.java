/*
 * Copyright, 2019, DI Hofmann Melanie - All Rights Reserved.
 */
package com.hofmann.melanie;

import com.google.inject.AbstractModule;
import com.hofmann.melanie.analytics.TvSpotService;
import com.hofmann.melanie.file.JsonMapper;
import com.hofmann.melanie.file.SimpleJsonReader;

public class BasicModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(SimpleJsonReader.class);
		bind(TvSpotService.class);
		bind(JsonMapper.class);
	}

}
