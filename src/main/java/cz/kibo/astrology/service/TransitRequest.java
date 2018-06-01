package cz.kibo.astrology.service;

import java.time.LocalDateTime;

import org.json.JSONObject;

import cz.kibo.api.astrology.domain.Coordinates;

public class TransitRequest extends ARequest{
	
	private JSONObject data;
	private boolean hasError = false;
	private StringBuilder errors = new StringBuilder();
	
	private LocalDateTime event;	
	private Coordinates coords;
	private String zodiac;
	private Double aspect = 0.0;
	private String planet;
	private String planet2;
	private Double point;
	private boolean backwards = false;
	
	public TransitRequest( String body ) {
					
		this.data = new JSONObject( body );
		
		System.out.println ( this.data );
		
		if( !isValid( this.data ) ) {			
			return;
		}	
					
		this.event  = super.parseEvent(this.data);
		this.coords = super.parseTopo( this.data );
		this.zodiac = super.parseZodiac( this.data );
		
		this.planet = data.get("planet").toString();
		
		if( data.has("aspect") ) {
			this.aspect = super.stringToDouble( data.get("aspect").toString() );
		}
			
		if( data.has("toPlanet") ) {
			this.planet2 = data.get("toPlanet").toString();
		}
		
		if( data.has("toPoint") ) {
			this.point = super.stringToDouble( data.get("toPoint").toString() );
		}
		
		if( data.has("backwards") ) {
			this.backwards = Boolean.parseBoolean( data.get("backwards").toString() );			
		}		
	}
			
	public LocalDateTime getEvent() {
		return event;
	}
	
	public Coordinates getCoordinates() {
		return coords;
	}

	public String getZodiac() {
		return zodiac;
	}
			
	public Double getAspect() {
		return aspect;
	}

	public String getPlanet() {
		return planet;
	}

	public String getToPlanet() {
		return planet2;
	}

	public Double getToPoint() {
		return point;
	}
		
	public boolean isBackwards() {
		return backwards;
	}

	public boolean hasError() {
		return this.hasError;
	}
	
	public String errors() {
		return errors.toString();
	}
	
	private boolean isValid( JSONObject body ) {
		
		this.hasError = super.commonValidator(body, this.errors);
		
		if( !this.data.has("planet")) {
			this.errors.append("Property 'planet' not found.");
			this.hasError = true;
		}
					
		if( !this.data.has("toPlanet") && !this.data.has("toPoint")) {
			this.errors.append("Properties 'toPlanet' or 'toPoint' not found.");
			this.hasError = true;
		}
		
		if( this.data.has("toPlanet") && this.data.has("toPoint")) {
			this.errors.append("Properties 'toPlanet' and 'toPoint' cannot be set together.");
			this.hasError = true;
		}
		
		return !this.hasError;		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aspect == null) ? 0 : aspect.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + ((planet == null) ? 0 : planet.hashCode());
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
		TransitRequest other = (TransitRequest) obj;
		if (aspect == null) {
			if (other.aspect != null)
				return false;
		} else if (!aspect.equals(other.aspect))
			return false;
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
		if (planet == null) {
			if (other.planet != null)
				return false;
		} else if (!planet.equals(other.planet))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TransitRequest [data=" + data + "]";
	}	
}
