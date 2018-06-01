package cz.kibo.astrology.service;

import static junit.framework.TestCase.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import spark.Spark;

public class TransitTest {
	
	@BeforeClass
	public static void beforeClass() {
		API newRoutes = new API();
        newRoutes.setupEndpoints();
		
		Spark.awaitInitialization();
	}
		 
	@AfterClass
	public static void afterClass() {
	    Spark.stop();
	}
		
	@Test
	public void corruptedJSON(){
		String reguestJSON = "}";
		
		URLResponse res = new URLResponse("POST", API.API_CONTEXT +"/transit", reguestJSON);
		
		assertEquals(200, res.getStatus());
		assertEquals( "application/json", res.getHeaders().get("Content-Type").get(0));		
		assertEquals("{\"error\":\"A JSONObject text must begin with '{' at 1 [character 2 line 1]\"}", res.getBody());	
	}
	
	@Test
	public void emptyParams(){
		String reguestJSON = "{\"event\":\"20120710160000\"}";
		
		URLResponse res = new URLResponse("POST", API.API_CONTEXT +"/transit", reguestJSON);
		
		assertEquals(200, res.getStatus());
		assertEquals( "application/json", res.getHeaders().get("Content-Type").get(0));		
		assertEquals("{\"error\":\"Property 'planet' not found.Properties 'toPlanet' or 'toPoint' not found.\"}", res.getBody());	
	}
	
	@Test
	public void emptyPlanet(){
		String reguestJSON = "{\"event\":\"20120710160000\", \"aspect\":120.0, \"toPlanet\":\"Moon\" }";
		
		URLResponse res = new URLResponse("POST", API.API_CONTEXT +"/transit", reguestJSON);
		
		assertEquals(200, res.getStatus());
		assertEquals( "application/json", res.getHeaders().get("Content-Type").get(0));		
		assertEquals("{\"error\":\"Property 'planet' not found.\"}", res.getBody());	
	}
	
	@Test
	public void emptyToPlanetAndToPoint(){
		String reguestJSON = "{\"event\":\"20120710160000\", \"planet\":\"Sun\" }";
		
		URLResponse res = new URLResponse("POST", API.API_CONTEXT +"/transit", reguestJSON);
		
		assertEquals(200, res.getStatus());
		assertEquals( "application/json", res.getHeaders().get("Content-Type").get(0));		
		assertEquals("{\"error\":\"Properties 'toPlanet' or 'toPoint' not found.\"}", res.getBody());	
	}
	
	@Test
	public void setToPlanetAndToPoint(){
		String reguestJSON = "{\"event\":\"20120710160000\", \"planet\":\"Sun\", \"toPlanet\":\"Moon\", \"toPoint\":120.0 }";
		
		URLResponse res = new URLResponse("POST", API.API_CONTEXT +"/transit", reguestJSON);
		
		assertEquals(200, res.getStatus());
		assertEquals( "application/json", res.getHeaders().get("Content-Type").get(0));		
		assertEquals("{\"error\":\"Properties 'toPlanet' and 'toPoint' cannot be set together.\"}", res.getBody());	
	}
	
	@Test
	public void reguestToPlanet(){
		String reguestJSON = "{\"event\":\"20120710160000\", \"planet\":\"Sun\", \"toPlanet\":\"Moon\" }";
		
		URLResponse res = new URLResponse("POST", API.API_CONTEXT +"/transit", reguestJSON);
		
		assertEquals(200, res.getStatus());
		assertEquals( "application/json", res.getHeaders().get("Content-Type").get(0));		
		assertEquals("{\"transit\":\"20120719042402\"}", res.getBody());	
	}
	
	@Test
	public void reguestToPoint(){
		String reguestJSON = "{\"event\":\"20120710160000\", \"planet\":\"Sun\", \"toPoint\": 120.0 }";
		
		URLResponse res = new URLResponse("POST", API.API_CONTEXT +"/transit", reguestJSON);
		
		assertEquals(200, res.getStatus());
		assertEquals( "application/json", res.getHeaders().get("Content-Type").get(0));		
		assertEquals("{\"transit\":\"20120722100052\"}", res.getBody());	
	}
	
	@Test
	public void reguestToPoint2(){
		String reguestJSON = "{\"event\":\"20120710160000\", \"planet\":\"Sun\", \"toPoint\": 120.0, \"backwards\": false }";
		
		URLResponse res = new URLResponse("POST", API.API_CONTEXT +"/transit", reguestJSON);
		
		assertEquals(200, res.getStatus());
		assertEquals( "application/json", res.getHeaders().get("Content-Type").get(0));		
		assertEquals("{\"transit\":\"20120722100052\"}", res.getBody());	
	}
	
	@Test
	public void reguestToPointBackwards(){
		String reguestJSON = "{\"event\":\"20120710160000\", \"planet\":\"Sun\", \"toPoint\": 180.0, \"backwards\": true }";
		
		URLResponse res = new URLResponse("POST", API.API_CONTEXT +"/transit", reguestJSON);
		
		assertEquals(200, res.getStatus());
		assertEquals( "application/json", res.getHeaders().get("Content-Type").get(0));		
		assertEquals("{\"transit\":\"20110923090437\"}", res.getBody());	
	}
	
	@Test
	public void reguestToPointTopo(){
		String reguestJSON = "{\"event\":\"20120710160000\", \"planet\":\"Sun\", \"toPoint\": 120.0, \"topo\":[48.8559107, 16.0542676, 286]}";
		
		URLResponse res = new URLResponse("POST", API.API_CONTEXT +"/transit", reguestJSON);
		
		assertEquals(200, res.getStatus());
		assertEquals( "application/json", res.getHeaders().get("Content-Type").get(0));		
		assertEquals("{\"transit\":\"20120722100147\"}", res.getBody());	
	}
	
	@Test
	public void reguestToPointSidereal(){
		String reguestJSON = "{\"event\":\"20120710160000\", \"planet\":\"Sun\", \"toPoint\": 120.0, \"zodiac\":\"Krishnamurti\"}";
		
		URLResponse res = new URLResponse("POST", API.API_CONTEXT +"/transit", reguestJSON);
		
		assertEquals(200, res.getStatus());
		assertEquals( "application/json", res.getHeaders().get("Content-Type").get(0));		
		assertEquals("{\"transit\":\"20120816100448\"}", res.getBody());	
	}
	
	@Test
	public void nonExistingRequest(){
		String reguestJSON = "{\"event\":\"20120710160000\", \"planet\":\"Sun\", \"toPlanet\":\"Mercury\", \"aspect\":180.0}";
		
		URLResponse res = new URLResponse("POST", API.API_CONTEXT +"/transit", reguestJSON);
		
		assertEquals(200, res.getStatus());
		assertEquals( "application/json", res.getHeaders().get("Content-Type").get(0));		
		assertEquals("{\"error\":\"Calculation failed with return code -1:\n" + "SwissEph file 'sepl_30.se1' not found in the paths of: '/data/ephemeris', \"}", res.getBody());	
	}

}
