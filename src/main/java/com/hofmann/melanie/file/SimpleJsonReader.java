/*
 * Copyright, 2019, DI Hofmann Melanie - All Rights Reserved.
 */
package com.hofmann.melanie.file;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SimpleJsonReader {

	SimpleJsonReader() {
		
	}
	
	public JSONObject readFileAsJsonObject(String path) throws InvalidFileException {
		try (FileReader fileReader = new FileReader(getClass().getClassLoader().getResource(path).getPath())) {
			JSONParser jsonParser = new JSONParser();
			Object jsonObject = jsonParser.parse(fileReader);
			
			return (JSONObject) jsonObject;
		} catch (IOException | ParseException e) {
			throw new InvalidFileException("Could not read file " + path, e);
		}
	}
}
