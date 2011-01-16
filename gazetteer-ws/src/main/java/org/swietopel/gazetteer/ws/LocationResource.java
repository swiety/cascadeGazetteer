package org.swietopel.gazetteer.ws;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.swietopel.gazetteer.ws.model.Location;
import org.swietopel.gazetteer.ws.model.LocationFactory;

/**
 * REST Web Service
 *
 * @author Piotr Swiecicki <swietopel@gmail.com>
 */
@Path("location")
public class LocationResource {

    @Context
    private UriInfo context;
    private Random random = new Random();

    /** Creates a new instance of LocationResource */
    public LocationResource() {
    }

    /**
     * Retrieves representation of an instance of org.swietopel.gazetteer.ws.LocationResource
     * @return an instance of org.swietopel.gazetteer.ws.model.Location
     */
    @GET
    @Produces("application/json")
    @Path("search/{searchString}")
    public List<Location> searchJson(@PathParam("searchString") final String searchString) {
        final int size = 3 + random.nextInt(3);
        final List<Location> ll = new ArrayList<Location>(size);
        for (int i = 0; i < size; i++) {
            final Location loc;
            final String locName = "Location from search [" + searchString + "] #" + i;
            if (random.nextBoolean()) {
                loc = LocationFactory.makeLeafLocation(locName, Integer.toHexString(locName.hashCode()));
            } else {
                loc = LocationFactory.makeCascadeLocation(locName, Integer.toHexString(locName.hashCode()), i);
            }
            ll.add(loc);
        }
        delay();
        return ll;
    }

    /**
     * Retrieves representation of an instance of org.swietopel.gazetteer.ws.LocationResource
     * @return an instance of org.swietopel.gazetteer.ws.model.Location
     */
    @GET
    @Produces("application/json")
    @Path("cascade/{cacheId}/{itemNo}")
    public List<Location> cascadeJson(@PathParam("cacheId") final String cacheId,
            @PathParam("itemNo") final int itemNo) {
        final int size = 3 + random.nextInt(3);
        final List<Location> ll = new ArrayList<Location>(size);
        for (int i = 0; i < size; i++) {
            final Location loc;
            final String locName = "Location cascaded [" + cacheId + "|" + itemNo + "] #" + i;
            if (random.nextBoolean()) {
                loc = LocationFactory.makeLeafLocation(locName, Integer.toHexString(locName.hashCode()));
            } else {
                loc = LocationFactory.makeCascadeLocation(locName, Integer.toHexString(locName.hashCode()), i);
            }
            ll.add(loc);
        }
        delay();
        return ll;
    }

    @SuppressWarnings("CallToThreadDumpStack")
    private void delay() {
        try {
            Thread.sleep(1000L + random.nextInt(1000));
        } catch (InterruptedException ex) {
            System.err.println("Sleep interrupted");
            ex.printStackTrace();
        }
    }
}
