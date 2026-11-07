package rest;

import com.google.common.base.Optional;
import com.codahale.metrics.annotation.Timed;
import graphdb.DBService;
import graphdb.GraphDB;
import graphdb.Node;
import query.Request;
import query.Response;
import server.Server;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

@Path("/graphdb")
@Produces(MediaType.APPLICATION_JSON)
public class GraphResource {

    public GraphResource() {
    }


 /*

    @Path(value = "/matchdevice")
    @Produces(MediaType.APPLICATION_JSON)
    public Response matchDevice(@Context HttpServletRequest hsReq, @Valid final MatchDeviceRequest request) throws Exception {
        final Boolean enableRestEndPoint = (Boolean) ApiConfig.INSTANCE.getValue(ApiConfig.ENABLE_MATCH_DEVICE);
  */

    @POST
    public Response sayHello(@Context HttpServletRequest hsReq, @Valid @NotNull Request request) {

        GraphDB db = ((DBService) Server.getService("DBService")).getDatabase(request.getDbName());
        if (db == null) {
            throw new NotFoundException("Unknown database");
        }
        Response response = null;

        switch (request.getOperation())
        {
            case Query:
                response = db.query(request);
                if (response == null) {
                    throw new NotFoundException("Unknown node");
                }
                break;
            case AddNode:
                Node n = db.createOrGetNode(request.getId());
                response = new Response();
                response.setNode(n);
                break;
            case AddRelation: {
                requireTarget(request);
                Node n1 = db.createOrGetNode(request.getId());
                Node n2 = db.createOrGetNode(request.getTgtId());
                db.addRelationship(n1, n2);
                break;
            }
            case DeleteRelation: {
                requireTarget(request);
                db.deleteRelationship(request.getId(), request.getTgtId());
                break;
            }
            case DeleteNode:
                db.deleteNode(request.getId());
                break;
            default:
                throw new BadRequestException("Unsupported operation");
        }

        return response;
    }

    private static void requireTarget(Request request) {
        if (request.getTgtId() == null) {
            throw new BadRequestException("tgtId is required for this operation");
        }
    }
}
