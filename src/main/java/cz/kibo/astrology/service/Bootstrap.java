package cz.kibo.astrology.service;

import static spark.Spark.*;

/**
 * Standalone Spark runs on an embedded Jetty web server.
 * For other servers see cz.kibo.astrology.service.Application
 */
public class Bootstrap {
	
    private static final int PORT = System.getenv("OPENSHIFT_DIY_PORT") != null ? Integer.parseInt(System.getenv("OPENSHIFT_DIY_PORT")) : 8080;
 
    public static void main(String[] args) throws Exception {       
        port(PORT);        
        staticFiles.location("/public");
        
        new API();
    }
}
