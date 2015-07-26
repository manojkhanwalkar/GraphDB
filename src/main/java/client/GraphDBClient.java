package client;

import graphdb.DBService;
import graphdb.GraphDB;
import org.springframework.web.client.RestTemplate;
import query.Request;
import query.Response;
import server.Server;

/**
 * Created by mkhanwalkar on 7/25/15.
 */
public class GraphDBClient {

    RestConnector connector ;   //TODO - pool of connectors to be created later .

    private GraphDBClient()
    {
        connector = new RestConnector();

    }

    static class Holder
    {
        static GraphDBClient factory = new GraphDBClient();
    }

    public static GraphDBClient getInstance()
    {
        return Holder.factory;

    }


    //TODO - add actual rest call here later
    public Response send(String dbName ,Request request) {

//        GraphDB db = ((DBService) Server.getService("DBService")).getDatabase(dbName);

  //      Response response = db.query(request);

    //    System.out.println(response);


        return null ;

    }




}
