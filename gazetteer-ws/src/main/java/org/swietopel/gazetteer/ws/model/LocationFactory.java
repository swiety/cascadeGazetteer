package org.swietopel.gazetteer.ws.model;

/**
 * @author Piotr Swiecicki <swietopel@gmail.com>
 */
public class LocationFactory {

    public static Location makeLeafLocation(final String name, final String id) {
        return new Location(false, name, id, null);
    }

    public static Location makeCascadeLocation(final String name, final String id, final int cacheItemNumber) {
        return new Location(true, name, id, Integer.valueOf(cacheItemNumber));
    }
}
