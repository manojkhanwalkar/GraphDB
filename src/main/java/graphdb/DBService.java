package graphdb;

import query.Request;
import query.Response;
import server.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mkhanwalkar on 7/17/15.
 */
public class DBService implements Service {


    String location;
    List<String> dbNames;

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

    @Override
    public void init() {

        for (String name : dbNames) {

            GraphDB db = GraphDbFactory.getInstance().createDB(location+name);

            db.restore();

            databases.put(name,db);
        }


    }

    @Override
    public void start() {

        Request request = new Request();
        request.setId("DP1");
        request.setType(NodeType.DP);
        Response response = databases.get("db").query(request);

        System.out.println(response);

    }

    @Override
    public void stop() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

        for (GraphDB db : databases.values())
            db.save();


    }

    @Override
    public void setName(String s) {

    }

    @Override
    public String getName() {
        return null;
    }
}
