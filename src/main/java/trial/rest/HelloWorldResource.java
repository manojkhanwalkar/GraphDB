package trial.rest;

import com.google.common.base.Optional;
import com.codahale.metrics.annotation.Timed;
import graphdb.DBService;
import graphdb.GraphDB;
import query.Request;
import query.Response;
import server.Server;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {

    public HelloWorldResource() {
    }


 /*

    @Path(value = "/matchdevice")
    @Produces(MediaType.APPLICATION_JSON)
    public Response matchDevice(@Context HttpServletRequest hsReq, @Valid final MatchDeviceRequest request) throws Exception {
        final Boolean enableRestEndPoint = (Boolean) ApiConfig.INSTANCE.getValue(ApiConfig.ENABLE_MATCH_DEVICE);
  */

    @POST
    public Response sayHello(@Context HttpServletRequest hsReq, @Valid Request request) {


        GraphDB db = ((DBService) Server.getService("DBService")).getDatabase(request.getDbName());
        Response response;

        switch (request.getOperation())
        {
            case Query:
              response   = db.query(request);
              break ;
            default :
                response = null;

        }


        return response;
    }
}