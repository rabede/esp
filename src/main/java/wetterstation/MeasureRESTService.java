package wetterstation;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Diese Klasse definiert eine REST-Resource für Measures
 * 
 * @author Michael Inden
 * 
 *         Copyright 2016 by Michael Inden
 */
@Path("/measures")
public class MeasureRESTService {
	private final MeasureManager measureManager = ManagerProvider.getMeasureManager();

	@POST
	public Response createMeasure(@FormParam("day") final LocalDate day, @FormParam("time") final LocalTime time,
			@FormParam("temperature") final Float temperature, @FormParam("humidity") final int humidity,
			@FormParam("windspeed") final float windspeed, @FormParam("downfall") final float downfall,
			@FormParam("rain") final boolean rain) {
		if (temperature != null) {
			final Measure measure = new Measure(day, time, temperature, humidity, windspeed, downfall, rain);
			measureManager.add(measure);

			return Response.created(URI.create("/rest/measures/" + measure.getId())).build();
		}
		return Response.status(Response.Status.BAD_REQUEST).build();
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Measure> getMeasures() {
		return measureManager.getMeasures();
	}

	@GET
	@Path("/top10")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Measure> getTop10() {
		return measureManager.getMeasuresTopN(10);
	}

	@DELETE
	@Path("/{id}")
	public Response deleteMeasureById(@PathParam("id") final long id) {
		final Optional<Measure> optMeasure = measureManager.findBy(id);
		if (optMeasure.isPresent()) {
			measureManager.delete(optMeasure.get());

			return Response.noContent().build();
		}

		return Response.status(Response.Status.NOT_FOUND).build();
	}
}
