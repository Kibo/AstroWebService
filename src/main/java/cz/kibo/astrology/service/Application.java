package cz.kibo.astrology.service;

import static spark.Spark.*;

import java.time.format.DateTimeFormatter;

import cz.kibo.api.astrology.builder.CuspBuilder;
import cz.kibo.api.astrology.builder.PlanetBuilder;
import cz.kibo.api.astrology.builder.TransitBuilder;
import cz.kibo.api.astrology.domain.Cusp;
import cz.kibo.api.astrology.domain.Planet;
import cz.kibo.astrology.service.exception.ValidationException;
import spark.servlet.SparkApplication;

public class Application implements SparkApplication {
	
    @Override
    public void init() {
    	
    	staticFiles.location("/public");
    	
        // Send planet ephemeris
        post("/planets", (req, res) -> {
        	
        	try {
        		PlanetRequest planetRequest = new PlanetRequest( req.body() );
        		
        		if( planetRequest.hasError() ) { 
            		throw new ValidationException( planetRequest.errors() );
            	}
        		
        		PlanetBuilder builder = new PlanetBuilder(  planetRequest.getEvent() );
        		builder.planet( String.join(",", planetRequest.getPlanets() ));
        		
        		if( planetRequest.getCoordinates() != null) {
    				builder.topo(
    					planetRequest.getCoordinates().getLongitude(), 
    					planetRequest.getCoordinates().getLatitude(), 
    					planetRequest.getCoordinates().getGeoalt());
    			}
        		
        		if( planetRequest.getZodiac() != null) {    				
        			builder.zodiac( planetRequest.getZodiac() );    				
    			}
        		
        		Planet planetEphemeris = builder.build();
        		
        		res.type("application/json");          
                return planetEphemeris.toJSON(); 
        		
        	
        	}catch( org.json.JSONException e) {
        		throw new ValidationException(e.getMessage());        		
        		
        	}catch(java.lang.IllegalArgumentException e) {
        		throw new ValidationException( e.getMessage() );
        	
        	} catch(java.time.format.DateTimeParseException e) {
        		throw new ValidationException( e.getMessage() );
        	}       	
        });

        // Send cusps ephemeris
        post("/cusps", (req, res) -> {
        	
        	try {
        		CuspRequest cuspRequest = new CuspRequest( req.body() );
        		
        		if( cuspRequest.hasError() ) { 
            		throw new ValidationException( cuspRequest.errors() );
            	}
        		
        		CuspBuilder builder = new CuspBuilder(  cuspRequest.getEvent() );
        		builder.houses( cuspRequest.getHouses() );
        		
        		if( cuspRequest.getCoordinates() != null) {
    				builder.topo(
    						cuspRequest.getCoordinates().getLongitude(), 
    						cuspRequest.getCoordinates().getLatitude(), 
    						cuspRequest.getCoordinates().getGeoalt());
    			}
        		
        		if( cuspRequest.getZodiac() != null) {    				
        			builder.zodiac( cuspRequest.getZodiac() );    				
    			}
        		
        		Cusp cuspEphemeris = builder.build();
        		        		        	
        		res.type("application/json");
        		return cuspEphemeris.toJSON(); 
        		
        	}catch( org.json.JSONException e) {
        		throw new ValidationException(e.getMessage());
        		
        	}catch(java.lang.IllegalArgumentException e) {
        		throw new ValidationException( e.getMessage() );
        	
        	} catch(java.time.format.DateTimeParseException e) {
        		throw new ValidationException( e.getMessage() );
        	}     	        	        	
        });
        
        // Send transit ephemeris
        post("/transit", (req, res) -> {
        	
        	try {
        		TransitRequest transitRequest = new TransitRequest( req.body() );
        		
        		if( transitRequest.hasError() ) { 
            		throw new ValidationException( transitRequest.errors() );
            	}
        		
        		TransitBuilder builder = new TransitBuilder(  transitRequest.getEvent() );
        		builder.planet( transitRequest.getPlanet());
        		
        		if( transitRequest.getToPlanet() != null) {
        			builder.toPlanet( transitRequest.getToPlanet());
        		}
        		
        		if( transitRequest.getToPoint() != null) {
        			builder.toPoint( transitRequest.getToPoint());
        		}
        		        	
        		builder.aspect( transitRequest.getAspect());
        		        		
        		if( transitRequest.getCoordinates() != null) {
    				builder.topo(
    						transitRequest.getCoordinates().getLongitude(), 
    						transitRequest.getCoordinates().getLatitude(), 
    						transitRequest.getCoordinates().getGeoalt());
    			}
        		
        		if( transitRequest.getZodiac() != null) {    				
        			builder.zodiac( transitRequest.getZodiac() );    				
    			}
        		        		        		        		        		        		      
        		res.type("application/json");        		
        		return "{\"transit\":\"" + builder.build().getDate().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "\"}";
        		
        	}catch( org.json.JSONException e) {
        		throw new ValidationException(e.getMessage());
        		
        	}catch(java.lang.IllegalArgumentException e) {
        		throw new ValidationException( e.getMessage() );
        	
        	} catch(java.time.format.DateTimeParseException e) {
        		throw new ValidationException( e.getMessage() );
        	} 
        });
                       
        
        exception(ValidationException.class, (exception, req, res) -> {
        	res.status(200);
        	res.type("application/json");        	
            res.body("{\"error\":\"" + exception.getMessage() + "\"}"); 
        });
                                       
        // code 404
        notFound((req, res) -> {
        	res.status(404);
            res.type("application/json");
            return "{\"message\":\"404 - Not Found\"}";
        });
                               
        // code 500
        internalServerError((req, res) -> {
        	res.status(500);
            res.type("application/json");
            return "{\"message\":\"500 - Server Error\"}";
        });
    }    	
}
