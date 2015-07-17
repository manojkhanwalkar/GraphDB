package query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import graphdb.Node;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class Response {

    Node node ;


    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public String toString()
    {
        return node.toString();
    }

}
