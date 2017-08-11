package cz.kibo.astrology.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.kibo.api.astrology.domain.Coordinates;
import cz.kibo.astrology.service.exception.ValidationException;

public abstract class ARequest {
	
	protected LocalDateTime parseEvent( JSONObject data) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");			
		return LocalDateTime.parse( data.get("event").toString(), formatter);
	}

	protected Coordinates parseTopo(JSONObject data) {
					
		if( data.has("topo")) {
			try {
				return new Coordinates( data.getJSONArray("topo").getInt(0) , data.getJSONArray("topo").getInt(1), data.getJSONArray("topo").getInt(2));
			}catch(org.json.JSONException e ) {
				throw new ValidationException( "'topo' parse error." );
			}
		}
					
		return null;		
	}

	protected String parseZodiac(JSONObject data) {
		
		// zodiac
		if( data.has("zodiac")) {			
			return data.get("zodiac").toString(); 
		}
		
		return null;
	}
	
	protected boolean commonValidator( JSONObject data, StringBuilder errors ) {
		
		boolean hasError = false;
		
		if( !data.has("event")) {
			errors.append("Property event not found.");
			hasError = true;								
		}
		
		if( data.has("event") && data.get("event").toString().length() != 14) {
			errors.append("Property event is not in the correct format YYYYMMDDhhmmss.");
			hasError = true;
		}
										
		if( data.has("topo") ) {			
			if( data.getJSONArray("topo").length() != 3 ) {
				errors.append("Property topo is not in the correct format: [longitude, latitude, geoalt].");
				hasError = true;
			}			
		}
		
		return hasError;					
	}
}