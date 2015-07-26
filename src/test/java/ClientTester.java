import client.GraphDBClient;
import graphdb.DBOperation;
import org.codehaus.jackson.map.ObjectMapper;
import query.Request;
import query.Response;

/**
 * Created by mkhanwalkar on 7/26/15.
 */
public class ClientTester {

    public static void main(String[] args)  throws Exception {
        GraphDBClient client = GraphDBClient.getInstance();
        Request request = new Request();
        request.setId("DP1");
        request.setOperation(DBOperation.Query);

       //  ObjectMapper mapper = new ObjectMapper();

       // String s = mapper.writeValueAsString(request);

        //System.out.println(s);

//
        Response response = client.send("db1",request);

        System.out.println(response);

    }

}
