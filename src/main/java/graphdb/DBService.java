package graphdb;

import query.Request;
import query.Response;
import server.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mkhanwalkar on 7/17/15.
 */
public class DBService implements Service {


    String location;
    List<String> dbNames;

    String name ;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getDbNames() {
        return dbNames;
    }

    public void setDbNames(List<String> dbNames) {
        this.dbNames = dbNames;
    }

    Map<String,GraphDB> databases = new HashMap<>();

    public Map<String, GraphDB> getDatabases() {
        return databases;
    }

    public void setDatabases(Map<String, GraphDB> databases) {
        this.databases = databases;
    }

    @Override
    public void init() {

        for (String name : dbNames) {

            GraphDB db = GraphDbFactory.getInstance().createDB(location+name);

            db.restore();

            databases.put(name,db);
        }


    }

    public GraphDB getDatabase(String name)
    {
        return databases.get(name);
    }

    private static NodeType getType(String s)
    {
        switch(s.charAt(0))
        {
            case 'D' :
                return NodeType.DP;
            case 'C' :
                return NodeType.Cookie;
            case 'A' :
                return NodeType.Account;
            case 'I' :
                return NodeType.IP;
            default:
                return NodeType.General;
        }
    }




    @Override
    public void destroy() {


        for (GraphDB db : databases.values())
            db.save();


    }

    @Override
    public void setName(String s) {

        name = s;

    }

    @Override
    public String getName() {
        return name;
    }
}
