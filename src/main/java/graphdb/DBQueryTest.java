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
public class DBQueryTest implements Service {



    @Override
    public void init() {


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
    public void start() {

        GraphDB db = ((DBService)Server.getService("DBService")).getDatabase("DB1");

        try {
            BufferedReader reader = new BufferedReader(new FileReader("/Users/mkhanwalkar/test/data/input.txt"));

            String s = null ;
            while ((s=reader.readLine()) != null)
            {
                //  System.out.println(s);
                String[] sA = s.split(",");
                Node dp = db.createOrGetNode(getType(sA[0]), sA[0]);
                dp.setName(sA[0]);
                for (int i=1;i<sA.length;i++)
                {
                    Node child = db.createOrGetNode(getType(sA[i]), sA[i]);
                    child.setName(sA[i]);
                    db.add(dp,child);

                }

            }

            Request request = new Request();
            request.setId("DP1");
            request.setType(NodeType.DP);
            Response response = db.query(request);

            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

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



    }

    @Override
    public void setName(String s) {

    }

    @Override
    public String getName() {
        return null;
    }
}
