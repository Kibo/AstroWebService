package cz.kibo.astrology.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.kibo.api.astrology.domain.Coordinates;

public class CuspRequest extends ARequest{
	
	private JSONObject data;
	private boolean hasError = false;
	private StringBuilder errors = new StringBuilder();
	
	private LocalDateTime event;
	private String houses;
	private Coordinates coords;
	private String zodiac;
	
	public CuspRequest( String body) {
		
		this.data = new JSONObject( body );
		
		if( !isValid( this.data ) ) {			
			return;
		}
		
		this.event  = super.parseEvent(this.data);
		this.coords = super.parseTopo( this.data );
		this.zodiac = super.parseZodiac( this.data );
				
		// houses
		this.houses = this.data.get("houses").toString();										
	}
	
	public LocalDateTime getEvent() {
		return event;
	}
		
	public String getHouses() {
		return houses;
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

	private boolean isValid(JSONObject body) {
		
		this.hasError = super.commonValidator(body, this.errors);
		
		if( !data.has("topo")) {
			errors.append("Property 'topo' not found.");
			hasError = true;								
		}
							
		if( !this.data.has("houses")) {
			this.errors.append("Property 'houses' not found.");
			this.hasError = true;
		}
							
		return !this.hasError;	
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + ((houses == null) ? 0 : houses.hashCode());
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
		CuspRequest other = (CuspRequest) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		if (houses == null) {
			if (other.houses != null)
				return false;
		} else if (!houses.equals(other.houses))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CuspRequest [data=" + data + "]";
	}	
}