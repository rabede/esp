package wetterstation;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.sun.net.httpserver.HttpServer;

/**
 * Diese Klasse stellt einen REST-Server bereit
 * 
 * @author Michael Inden
 * 
 * Copyright 2016 by Michael Inden
 */
public class AuditingMeasureRESTServer 
{
    private static final String BASE_URI = "http://localhost:4444/rest/";
    private static final String[] PACKAGES = { "wetterstation" };
        
    public static void main(final String[] args) throws URISyntaxException, IOException
    {
        final MeasureManager measureManager = ManagerProvider.getMeasureManager();
        measureManager.populateFromCsv("resources/20170118.txt");
   
        final ResourceConfig rc = new ResourceConfig().packages(PACKAGES);
		final HttpServer server = JdkHttpServerFactory.
				                  createHttpServer(URI.create(BASE_URI), rc);		

        System.out.println("Measure-REST-Server is running at " + BASE_URI);
        System.in.read();
        server.stop(0);
    }
}
