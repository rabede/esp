package esp;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@Path("setTemp")
public class SetTemp {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/Temperatur";
	static final String USER = "user";
	static final String PASS = "user";
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
		HttpServer server = HttpServerFactory.create("http://localhost:8080/rest");
		server.start();
		System.out.println("Zum Beenden bitte [Eingabe] drücken");
		System.in.read();
		server.stop(0);
	}
}
