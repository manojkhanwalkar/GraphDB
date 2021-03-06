package graphdb;

import graphdb.*;
import org.codehaus.jackson.map.ObjectMapper;
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


            }

       } catch (IOException e) {
            e.printStackTrace();
        }

   }

    String name ;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void start() {

   //     addData();



    }

 }
