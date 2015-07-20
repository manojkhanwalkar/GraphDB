package graphdb.persistence;

import graphdb.Delta;
import graphdb.Node;
import graphdb.Relationship;
import graphdb.RelationshipSerDeSer;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by mkhanwalkar on 7/19/15.
 */
public class DeltaWriter {

    static ObjectMapper mapper = new ObjectMapper();

   // long counter;
    String location ;
    String fileName ;
    PrintWriter writer;

    public DeltaWriter(String location, String fileName)
    {
        this.location = location ;
        this.fileName = fileName ;
        try {
            writer = new PrintWriter(location+fileName+System.currentTimeMillis());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    public void write(Delta delta)
    {
        try {
            String s1 = mapper.writeValueAsString(delta);
            writer.write(s1);
            writer.write("\n");

            // System.out.println(s1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void save() {
        writer.flush();
        writer.close();
    }
}
