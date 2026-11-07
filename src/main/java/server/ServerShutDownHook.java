package server;

import logger.Logger;

public class ServerShutDownHook implements Runnable {

    Server server;
    public ServerShutDownHook(Server s)
    {
        server=s;
    }

    public void run()
    {

        if (server.getList() == null)
            return; // initialisation failed, nothing to stop

        server.stop();

        server.destroy();
    }
}