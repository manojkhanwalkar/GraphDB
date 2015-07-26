package graphdb;

import server.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RestService implements Service {



    String name ;



    @Override
    public void init() {


    }

    @Override
    public void destroy() {




    }

    @Override
    public void setName(String s) {

        name = s;

    }

    @Override
    public String getName() {
        return name;
    }
}
