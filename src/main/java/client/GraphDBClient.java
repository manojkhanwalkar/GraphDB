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

    ThreadLocal<RestConnector> localConnector = new ThreadLocal<>();

    private GraphDBClient()
    {

    }

    static class Holder
    {
        static GraphDBClient factory = new GraphDBClient();
    }

    public static GraphDBClient getInstance()
    {
        return Holder.factory;

    }



    public Response send(Request request) {

        RestConnector connector = localConnector.get();
        if (connector==null)
        {
            connector = new RestConnector();
            connector.connect();
            localConnector.set(connector);

        }

        return connector.send(request);

    }




}
