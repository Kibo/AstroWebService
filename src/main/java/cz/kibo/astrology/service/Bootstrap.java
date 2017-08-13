package cz.kibo.astrology.service;

import static spark.Spark.*;

public class Bootstrap {
	
    private static final int PORT = System.getenv("OPENSHIFT_DIY_PORT") != null ? Integer.parseInt(System.getenv("OPENSHIFT_DIY_PORT")) : 8080;
 
    public static void main(String[] args) throws Exception {       
        port(PORT);        
        staticFiles.location("/public");
        
        new API();
    }
}
