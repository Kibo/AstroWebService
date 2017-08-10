package cz.kibo.astrology.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.kibo.api.astrology.domain.Coordinates;
import cz.kibo.astrology.service.exception.ValidationException;

public class PlanetRequest {

	private JSONObject data;
	private boolean hasError = false;
	private StringBuilder errors = new StringBuilder();
	
	private LocalDateTime event;
	private List<String> planets;
	private Coordinates coords;
	private String zodiac;
		
	public PlanetRequest(String body) {		
		this.data = new JSONObject( body );
					
		if( !isValid( this.data ) ) {			
			return;
		}	
			
		// event
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");			
		this.event  = LocalDateTime.parse(this.data.get("event").toString(), formatter);	
		
		
		// planets
		this.planets = new ArrayList<String>();			
		JSONArray planetsData = this.data.getJSONArray("planets");
		for (int i = 0, ln = planetsData.length() ; i < ln ; i++) {
			this.planets.add( planetsData.get(i).toString() );
		}
					
		// topo
		if( this.data.has("topo")) {			
			this.coords = new Coordinates( this.data.getJSONArray("topo").getInt(0) , this.data.getJSONArray("topo").getInt(1), this.data.getJSONArray("topo").getInt(2));
		}
				
		// zodiac
		if( this.data.has("zodiac")) {			
			this.zodiac = this.data.get("zodiac").toString(); 
		}				
	}
	
	
	public LocalDateTime getEvent() {
		return event;
	}
	
	
	public List<String> getPlanets() {
		return planets;
	}
	
	
	public Coordinates getCoordinates() {
		return coords;
	}
	
	public String getZodiac() {
		return this.zodiac;
	}

	public boolean hasError() {
		return this.hasError;
	}
	
	public String errors() {
		return errors.toString();
	}
		
	private boolean isValid( JSONObject body ) {
		
		if( !this.data.has("event")) {
			this.errors.append("Property event not found.");
			this.hasError = true;								
		}
		
		if( this.data.has("event") && this.data.get("event").toString().length() != 14) {
			this.errors.append("Property event is not in the correct format YYYYMMDDhhmmss.");
			this.hasError = true;
		}
							
		if( !this.data.has("planets")) {
			this.errors.append("Property planets not found.");
			this.hasError = true;
		}
		
		if( this.data.has("topo") ) {			
			if( this.data.getJSONArray("topo").length() != 3 ) {
				this.errors.append("Property topo is not in the correct format: [longitude, latitude, geoalt].");
				this.hasError = true;
			}			
		}
		
		return !this.hasError;		
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((errors == null) ? 0 : errors.hashCode());
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + ((planets == null) ? 0 : planets.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlanetRequest other = (PlanetRequest) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (errors == null) {
			if (other.errors != null)
				return false;
		} else if (!errors.equals(other.errors))
			return false;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		if (planets == null) {
			if (other.planets != null)
				return false;
		} else if (!planets.equals(other.planets))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PlanetRequest [data=" + data + "]";
	}
}
