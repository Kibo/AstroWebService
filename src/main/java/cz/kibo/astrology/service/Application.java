package cz.kibo.astrology.service;

import static spark.Spark.*;

import cz.kibo.api.astrology.builder.PlanetBuilder;
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
        		//throw new ValidationException(e.getMessage());
        		throw new ValidationException( req.body().toString() );


        	}catch(java.lang.IllegalArgumentException e) {
        		throw new ValidationException( e.getMessage() );
        	}        	
        });

        // Send cusps ephemeris
        post("/cusps", (req, res) -> {
        	
        	res.type("application/json");
            return "{\"cusps\":\"TODO\"}";
        });
        
        // Send transit ephemeris
        post("/transit", (req, res) -> {
        	
        	res.type("application/json");
            return "{\"transit\":\"TODO\"}";
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
