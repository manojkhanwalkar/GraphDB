package trial.rest;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class HelloWorldApplication extends Application<ExampleServiceConfiguration> {


  /*  public static void main(String[] args) throws Exception {
        new HelloWorldApplication().run("server", "/Users/mkhanwalkar/GraphDB/src/main/java/trial/rest/configuration.yml");
    }*/

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<ExampleServiceConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(ExampleServiceConfiguration configuration,
                    Environment environment) {
        // nothing to do yet

        final HelloWorldResource resource = new HelloWorldResource();
        environment.jersey().register(resource);
    }

}