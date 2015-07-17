package query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import graphdb.NodeType;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class Request
{

    String id ;
    NodeType type ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }
}
