package server;

/**
 * Created by mkhanwalkar on 7/17/15.
 */
public interface Service {
    public void init()  ;
    public void start()  ;
    public void stop()    ;
    public void pause()    ;
    public void resume()    ;
    public void destroy()    ;
    public void setName(String s);
    public String getName();

}