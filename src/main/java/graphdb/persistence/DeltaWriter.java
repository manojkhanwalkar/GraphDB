package graphdb.persistence;

import graphdb.Delta;
import graphdb.Node;
import graphdb.Relationship;
import graphdb.RelationshipSerDeSer;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by mkhanwalkar on 7/19/15.
 */
public class DeltaWriter {

    //TODO - drop out of while loop on exit after draining the queue .
    //TODO - sequence delta and snapshots correctly
    //TODO - after first snapshot delta is closed , have to open a new delta file .
    //TODO - there may be multiple delta from last snapshot - have to process all of them .

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
            writer = new PrintWriter(location+fileName+"."+System.currentTimeMillis());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Thread t = new Thread(()->{
            try {
                while (true) {
                    Delta delta = queue.take();
                    String s1 = mapper.writeValueAsString(delta);
                    writer.write(s1);
                    writer.write("\n");
                    writer.flush();
                }

                // System.out.println(s1);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

        });

        t.start();

    }

    BlockingQueue<Delta> queue = new ArrayBlockingQueue<Delta>(10000);



    public void write(Delta delta)
    {
        try {
            queue.put(delta);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void save() {
        writer.flush();
        writer.close();
    }
}
