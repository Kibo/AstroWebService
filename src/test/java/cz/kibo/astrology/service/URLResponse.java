package cz.kibo.astrology.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import spark.utils.IOUtils;

/**
 * Helper class for testing routes
 * 
 * @author Tomas Jurman
 */
public class URLResponse {
	
	// Embeded Jetty default port
	private static int PORT = 4567;
	
	private Map<String, List<String>> headers;
    private String body;
    private int status;
		
	public URLResponse( String requestMethod, String path, String body ) {
							
		try {
			URL url = new URL("http://localhost:" + PORT + path);		
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod(requestMethod);
	        
	        if(body != null) {
		        connection.setDoOutput(true);
		        OutputStream os = connection.getOutputStream();
		        os.write(body.getBytes("UTF-8"));
		        os.close();
	        }
	        	        
	        connection.connect();
	        	       	      
	        this.body = IOUtils.toString(connection.getInputStream());
	        this.status = connection.getResponseCode();
	        this.headers = connection.getHeaderFields();
        
		} catch (IOException e) {			
			e.printStackTrace();
		}              	
	}

	public Map<String, List<String>> getHeaders() {
		return headers;
	}

	public String getBody() {
		return body;
	}

	public int getStatus() {
		return status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((body == null) ? 0 : body.hashCode());
		result = prime * result + ((headers == null) ? 0 : headers.hashCode());
		result = prime * result + status;
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
		URLResponse other = (URLResponse) obj;
		if (body == null) {
			if (other.body != null)
				return false;
		} else if (!body.equals(other.body))
			return false;
		if (headers == null) {
			if (other.headers != null)
				return false;
		} else if (!headers.equals(other.headers))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "URLResponse [status=" + status + ", body=\" + body + \"]";
	}	
}
