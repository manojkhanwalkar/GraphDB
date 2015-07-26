package query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import graphdb.DBOperation;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class Request
{

    DBOperation operation;

    public DBOperation getOperation() {
        return operation;
    }

    public void setOperation(DBOperation operation) {
        this.operation = operation;
    }

    String id ;
    //NodeType type ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /*public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }*/
}
