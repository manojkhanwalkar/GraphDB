package graphdb;

import java.util.*;

/**
 * Created by mkhanwalkar on 7/15/15.
 */
public class Node {

    String internalId; // TODO - to be used later .
    String id ;
  //  NodeType type ;
    String name ;
    Map<String,String> properties;

    Set<Relationship> relationships = new LinkedHashSet<>();

    public Node()
    {

    }


    protected Node( String id) {

      //  this.type = type;
        this.id = id;
    }


    public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public void addProperty(String name , String value) {
        properties.put(name,value);
    }

    public void removeProperty(String name)
    {
        properties.remove(name);
    }

    public void addRelationship(Node n)
    {
        relationships.add(new Relationship(n));
    }

    public void removeRelationship(Node n)
    {
        relationships.remove(new Relationship(n));
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", relationships=" + relationships +
                '}'+'\n';
    }

    public Set<Relationship> returnRelationship() {
        return relationships;
    }

    public void setRelationships(Set<Relationship> relationships) {
        this.relationships = relationships;
    }
}
