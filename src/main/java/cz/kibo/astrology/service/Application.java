package cz.kibo.astrology.service;

import static spark.Spark.*;
import spark.servlet.SparkApplication;

/**
 * To run Spark on another web server (instead of the embedded jetty server), 
 * an implementation of the interface spark.servlet.SparkApplication is needed.
 * You have to initialize your routes in the init() method, and the following filter might 
 * have to be configured in your web.xml
 */
public class Application implements SparkApplication{
	
	  @Override
	  public void init() {		  
		  staticFiles.location("/public");
		  new API();
	  }
}
