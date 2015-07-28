import client.GraphDBClient;
import graphdb.DBOperation;
import graphdb.DBService;
import graphdb.GraphDB;
import graphdb.Node;
import org.codehaus.jackson.map.ObjectMapper;
import query.Request;
import query.Response;
import server.Server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by mkhanwalkar on 7/26/15.
 */
public class ClientTester {

    //TODO - multiple databases
    //TODO - different operation types


    public static void main(String[] args)  throws Exception {

        String clusterName = "cluster1";
        String clusterName1 = "cluster2";

 /*       for (int j=0;j<1;j++) {

            Thread t = new Thread(()-> {


            for (int i = 0; i < 1; i++) {
                GraphDBClient client = GraphDBClient.getInstance();
                Request request = new Request();
                request.setId("DP5");
                request.setOperation(DBOperation.Query);
                request.setDbName("db2");

                //  ObjectMapper mapper = new ObjectMapper();

                // String s = mapper.writeValueAsString(request);

                //System.out.println(s);

//
                Response response = client.send(request);

                System.out.println(response);
            }
            });

            t.start();

        }*/

       // GraphDB db = ((DBService) Server.getService("DBService")).getDatabase("db1");
        GraphDBClient client = GraphDBClient.getInstance();

        client.addCluster(clusterName,10005);
        client.addCluster(clusterName,"localhost");
        client.addCluster(clusterName1,10015);
        client.addCluster(clusterName1,"localhost");


        for (int j=0;j<1;j++) {

            String clusterToUse ;
            if (j%2==0)
                clusterToUse = clusterName;
            else
                clusterToUse = clusterName1;

            try {
                BufferedReader reader = new BufferedReader(new FileReader("/Users/mkhanwalkar/test/data/input.txt"));

                String s = null;
                while ((s = reader.readLine()) != null) {
                    //  System.out.println(s);
                    String[] sA = s.split(",");

                    Request request = new Request();
                    request.setId(sA[0]);
                    // request.setName(sA[0]);
                    request.setOperation(DBOperation.AddNode);
                    request.setDbName("db1");
                    Response response = client.send(clusterToUse, request);
                    System.out.println(response);

                    for (int i = 1; i < sA.length; i++) {
                        //                 Node child = db.createOrGetNode( sA[i]);
                        Request child = new Request();
                        child.setId(sA[i]);
                        // child.setName(sA[i]);
                        child.setOperation(DBOperation.AddNode);
                        child.setDbName("db1");
                        response = client.send(clusterToUse, child);
                        System.out.println(response);

                        Request relation = new Request();
                        relation.setId(sA[0]);
                        relation.setTgtId(sA[i]);
                        relation.setOperation(DBOperation.AddRelation);
                        relation.setDbName("db1");
                        response = client.send(clusterToUse, relation);
                        System.out.println(response);

                    }


                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }



    }

}
