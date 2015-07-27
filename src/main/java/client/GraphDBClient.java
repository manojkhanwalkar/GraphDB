package client;

import graphdb.DBService;
import graphdb.GraphDB;
import org.springframework.web.client.RestTemplate;
import query.Request;
import query.Response;
import server.Server;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mkhanwalkar on 7/25/15.
 */
public class GraphDBClient {

    ThreadLocal<Map<String,RestConnector>> localConnector = new ThreadLocal<>();

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

    Map<String,Integer> ports = new HashMap<>();
    Map<String,String> hosts = new HashMap<>();

    public void addCluster(String name , int port)
    {
        ports.put(name,port);
    }

    public void addCluster(String name , String host)
    {
        hosts.put(name,host);
    }


    public Response send(String clusterName , Request request) {

        Map<String,RestConnector> connectors = localConnector.get();
        if (connectors==null)
        {
            connectors = new HashMap<>();
            localConnector.set(connectors);
        }

        RestConnector connector = connectors.get(clusterName);
        if (connector==null)
        {
            connector = new RestConnector(hosts.get(clusterName), ports.get(clusterName));
            connector.connect();
            connectors.put(clusterName,connector);

        }

        return connector.send(request);

    }




}
