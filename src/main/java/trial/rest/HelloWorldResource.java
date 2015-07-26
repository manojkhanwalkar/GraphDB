package trial.rest;

import com.google.common.base.Optional;
import com.codahale.metrics.annotation.Timed;
import query.Response;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {

    public HelloWorldResource() {
    }

    @GET
    @Timed
    public Response sayHello() {
      //  final String value = String.format(template, name.or(defaultName));
       // return new Saying(counter.incrementAndGet(), value);

        return new Response();
    }
}