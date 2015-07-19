package graphdb;

import graphdb.*;
import query.Request;
import query.Response;
import server.Server;
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
public class DBClientService implements Service {


   private void addData()
   {
       GraphDB db = ((DBService)Server.getService("DBService")).getDatabase("db1");

       try {
            BufferedReader reader = new BufferedReader(new FileReader("/Users/mkhanwalkar/test/data/input.txt"));

            String s = null ;
            while ((s=reader.readLine()) != null)
            {
                //  System.out.println(s);
                String[] sA = s.split(",");
                Node dp = db.createOrGetNode( sA[0]);
                dp.setName(sA[0]);
                for (int i=1;i<sA.length;i++)
                {
                    Node child = db.createOrGetNode( sA[i]);
                    child.setName(sA[i]);
                    db.add(dp,child);

                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

       } catch (IOException e) {
            e.printStackTrace();
        }

   }

    private void query()
    {
        GraphDB db = ((DBService)Server.getService("DBService")).getDatabase("db1");

//        db.deleteNode("DP60");

        //db.deleteRelationship("DP54","IP77");

        Request request = new Request();
        request.setId("DP54");
        Response response = db.query(request);

        System.out.println(response);

    }




    @Override
    public void start() {

      //  addData();

        query();


    }

 }
