/*
 * Copyright, 2019, DI Hofmann Melanie - All Rights Reserved.
 */
package com.hofmann.melanie.file;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

public class JsonMapper {

	JsonMapper() {

	}

	public <T> ArrayList<T> mapObject(JSONArray jsonArray, Class<T> classToBind) {
		ObjectMapper objectMapper = new ObjectMapper();
		CollectionType type = objectMapper.getTypeFactory().constructCollectionType(List.class,
				classToBind);
		try {
			return objectMapper.readValue(jsonArray.toJSONString(), type);
		} catch (Exception e) {
			throw new JsonMappingException(
					"Couldn't map JSON for " + classToBind.getCanonicalName(), e);
		}
	}
}
