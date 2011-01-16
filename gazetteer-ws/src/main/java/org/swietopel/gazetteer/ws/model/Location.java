package org.swietopel.gazetteer.ws.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Piotr Swiecicki <swietopel@gmail.com>
 */
@XmlRootElement(name = "location")
public class Location {

    private boolean hasChildren;
    private String name;
    private String id;
    private Integer cacheItemNumber;

    public Location() {
    }

    /**
     * package private to restrict access to {@see LocationFactory} only.
     * @param hasChildren
     * @param name
     * @param id
     * @param cacheItemNumber
     */
    Location(boolean hasChildren, String name, String id, Integer cacheItemNumber) {
        this.hasChildren = hasChildren;
        this.name = name;
        this.id = id;
        this.cacheItemNumber = cacheItemNumber;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCacheItemNumber() {
        return cacheItemNumber;
    }

    public void setCacheItemNumber(Integer cacheItemNumber) {
        this.cacheItemNumber = cacheItemNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Location{" + "hasChildren=" + hasChildren + "name=" + name + "id=" + id + "cacheItemNumber=" + cacheItemNumber + '}';
    }
}
