import graphdb.GraphDB;
import graphdb.GraphDbFactory;
import graphdb.Node;
import graphdb.NodeType;
import query.Request;
import query.Response;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by mkhanwalkar on 7/15/15.
 */
public class GraphDBTest {

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

    public static void main(String[] args) throws Exception {

        GraphDB db = GraphDbFactory.getInstance().createDB("/tmp/db");

        db.restore();


        BufferedReader reader = new BufferedReader(new FileReader("/Users/mkhanwalkar/test/data/input.txt"));

        String s = null ;
        while ((s=reader.readLine())!=null)
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


        db.save();




    }
}
