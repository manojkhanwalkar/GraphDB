package query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import graphdb.Node;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class Response {

    Node node ;

    String relString;

    public String getRelString() {
        return relString;
    }

    public void setRelString(String relString) {
        this.relString = relString;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    @Override
    public String toString() {
        return "Response{" +
                "node=" + node +
                ", relString='" + relString + '\'' +
                '}';
    }
}
