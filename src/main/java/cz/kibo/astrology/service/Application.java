package cz.kibo.astrology.service;

import static spark.Spark.*;
import spark.servlet.SparkApplication;

public class Application implements SparkApplication{
	
	  @Override
	  public void init() {		  
		  staticFiles.location("/public");
		  new API();
	  }
}
