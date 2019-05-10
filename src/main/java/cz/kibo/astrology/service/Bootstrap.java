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
        
        int maxThreads = 6;
        int minThreads = 6;
        int timeOutMillis = 30000;
        threadPool(maxThreads, minThreads, timeOutMillis);
        
        new API();
    }
}
