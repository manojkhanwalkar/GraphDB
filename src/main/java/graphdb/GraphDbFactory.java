package graphdb;

/**
 * Created by mkhanwalkar on 7/15/15.
 */
public class GraphDbFactory {


    static class Holder
    {
        static GraphDbFactory factory = new GraphDbFactory();
    }

    public static GraphDbFactory getInstance()
    {
        return Holder.factory;

    }

    public GraphDB createDB(String location , String clusterName , String dbName)
    {
        return new GraphDB(location,clusterName,dbName);
    }


    private GraphDbFactory()
    {

    }



}
