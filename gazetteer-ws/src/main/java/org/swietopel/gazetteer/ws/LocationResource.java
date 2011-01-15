package org.swietopel.gazetteer.ws;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.swietopel.gazetteer.ws.model.Location;

/**
 * REST Web Service
 *
 * @author Piotr Swiecicki <swietopel@gmail.com>
 */
@Path("location")
public class LocationResource {

    @Context
    private UriInfo context;

    /** Creates a new instance of LocationResource */
    public LocationResource() {
    }

    /**
     * Retrieves representation of an instance of org.swietopel.gazetteer.ws.LocationResource
     * @return an instance of org.swietopel.gazetteer.ws.model.Location
     */
    @GET
    @Produces("application/json")
    @Path("{searchString}")
    public List<Location> searchJson(@PathParam("searchString") final String searchString) {
        final Random r = new Random();
        final int size = 5 + r.nextInt(10);
        final List<Location> ll = new ArrayList<Location>(size);
        for (int i = 0; i < size; i++) {
            final Location l = new Location(r.nextBoolean(), "Location from search [" + searchString + "] #" + i, "cache[" + searchString + "]id", i);
            ll.add(l);
        }
        return ll;
    }
}
