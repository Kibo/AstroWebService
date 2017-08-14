package cz.kibo.astrology.service;

import static junit.framework.TestCase.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import spark.Spark;

public class CuspTest {
	
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
		
		URLResponse res = new URLResponse("POST", API.API_CONTEXT +"/cusps", reguestJSON);
		
		assertEquals(200, res.getStatus());
		assertEquals( "application/json", res.getHeaders().get("Content-Type").get(0));		
		assertEquals("{\"error\":\"A JSONObject text must begin with '{' at 1 [character 2 line 1]\"}", res.getBody());	
	}
	
	@Test
    public void cuspsEmptyTopo(){
		String reguestJSON = "{\"event\":\"20120710160000\", \"houses\":\"Placidus\"}";
		
		URLResponse res = new URLResponse("POST", API.API_CONTEXT +"/cusps", reguestJSON);
					
		assertEquals(200, res.getStatus());
		assertEquals( "application/json", res.getHeaders().get("Content-Type").get(0));		
		assertEquals("{\"error\":\"Property 'topo' not found.\"}", res.getBody());					
	}
	
	@Test
    public void cuspsEmptyHouses(){
		String reguestJSON = "{\"event\":\"20120710160000\", \"topo\":[1.2,2.3,3.3]}";
		
		URLResponse res = new URLResponse("POST", API.API_CONTEXT +"/cusps", reguestJSON);
					
		assertEquals(200, res.getStatus());
		assertEquals( "application/json", res.getHeaders().get("Content-Type").get(0));		
		assertEquals("{\"error\":\"Property 'houses' not found.\"}", res.getBody());					
	}
	
	@Test
    public void cuspsIncorrectHouses(){
		String reguestJSON = "{\"event\":\"20120710160000\", \"topo\":[1.2,2.3,3.3], \"houses\":\"Pla\"}";
		
		URLResponse res = new URLResponse("POST", API.API_CONTEXT +"/cusps", reguestJSON);
					
		assertEquals(200, res.getStatus());
		assertEquals( "application/json", res.getHeaders().get("Content-Type").get(0));		
		assertEquals("{\"error\":\"Unknown houses system: Pla\"}", res.getBody());					
	}
	
	@Test
    public void cuspsRequest(){
		String reguestJSON = "{\"event\":\"20120710160000\", \"topo\":[1.2,2.3,3.3], \"houses\":\"Placidus\"}";
		
		URLResponse res = new URLResponse("POST", API.API_CONTEXT +"/cusps", reguestJSON);
					
		assertEquals(200, res.getStatus());
		assertEquals( "application/json", res.getHeaders().get("Content-Type").get(0));		
		assertEquals("{\"cusps\":[260.0062934597336,287.9382970404474,317.34673266050316,349.099841001655,21.511940393694886,51.956436006094464,80.00629345973363,107.93829704044742,137.34673266050316,169.09984100165502,201.51194039369489,231.95643600609446]}", res.getBody());					
	}
	
	@Test
    public void cuspsRequestSidereal(){
		String reguestJSON = "{\"event\":\"20120710160000\", \"topo\":[1.2,2.3,3.3], \"houses\":\"Placidus\", \"zodiac\":\"Krishnamurti\"}";
		
		URLResponse res = new URLResponse("POST", API.API_CONTEXT +"/cusps", reguestJSON);
					
		assertEquals(200, res.getStatus());
		assertEquals( "application/json", res.getHeaders().get("Content-Type").get(0));		
		assertEquals("{\"cusps\":[236.0663904234602,263.99839400417403,293.4068296242298,325.15993796538163,357.5720373574215,28.01653296982105,56.06639042346021,83.99839400417402,113.40682962422976,145.1599379653816,177.57203735742146,208.01653296982104]}", res.getBody());					
	}
	
	@Test
    public void cuspsRequestBadSidereal(){
		String reguestJSON = "{\"event\":\"20120710160000\", \"topo\":[1.2,2.3,3.3], \"houses\":\"Placidus\", \"zodiac\":\"Krishnamurto\"}";
		
		URLResponse res = new URLResponse("POST", API.API_CONTEXT +"/cusps", reguestJSON);
					
		assertEquals(200, res.getStatus());
		assertEquals( "application/json", res.getHeaders().get("Content-Type").get(0));		
		assertEquals("{\"error\":\"Unknown sidereal mode: Krishnamurto\"}", res.getBody());					
	}

}
