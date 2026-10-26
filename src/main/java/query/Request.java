package query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import graphdb.DBOperation;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Request
{
    public static final int MAX_ID_LENGTH = 256;

    @NotNull
    @Size(min = 1, max = MAX_ID_LENGTH)
    String dbName ;

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    @NotNull
    DBOperation operation;

    public DBOperation getOperation() {
        return operation;
    }

    public void setOperation(DBOperation operation) {
        this.operation = operation;
    }

    @NotNull
    @Size(min = 1, max = MAX_ID_LENGTH)
    String id ;

    @Size(min = 1, max = MAX_ID_LENGTH)
    String tgtId;
    //NodeType type ;


    public String getTgtId() {
        return tgtId;
    }

    public void setTgtId(String tgtId) {
        this.tgtId = tgtId;
    }

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
