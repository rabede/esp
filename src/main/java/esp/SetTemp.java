package esp;

// Ursprünglich aus www.rasberry-pi-geek.de 3/16 von Martin Mohr 
// wiederum aufbauend auf http://openbook.rheinwerk-verlag.de/java7/1507_13_002.html 

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.sun.net.httpserver.HttpServer;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@Path("setTemp")
public class SetTemp {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/Temperatur";
	static final String USER = "user";
	static final String PASS = "user";

	private static final String BASE_URI = "http://localhost:8080/rest/";
	private static final String PACKAGES = "esp";

	Connection conn = null;
	Statement stmt = null;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String setTemp() {
		System.out.println("normal");
		return "Saved";
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("temp/{temp}")
	public String setTemp(@PathParam("temp") String temp) {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql;
			sql = "INSERT INTO Temperatur values (now()," + temp + ")";
			stmt.executeUpdate(sql);
			System.out.println(temp);
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Saved: " + temp;
	}

	public static void main(String[] args) throws Exception {

		final ResourceConfig rc = new ResourceConfig().packages(PACKAGES);
		final HttpServer server = JdkHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);

		// automatisch: server.start();
		System.out.println("Zum Beenden bitte [Eingabe] drücken");
		System.in.read();
		server.stop(0);
	}

	@GET
	@Path("xml")
	@Produces(MediaType.TEXT_XML)
	public ServerInfo serverinfo() {
		ServerInfo info = new ServerInfo();
		info.server = System.getProperty("os.name") + " " + System.getProperty("os.version");
		return info;
	}

	@GET
	@Path("json")
	@Produces(MediaType.APPLICATION_JSON)
	public ServerInfo json() {
		ServerInfo info = new ServerInfo();
		info.server = System.getProperty("os.name") + " " + System.getProperty("os.version");
		return info;
	}
}

@XmlRootElement
class ServerInfo {
	public String server;
}
