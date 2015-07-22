package graphdb.persistence;

import graphdb.GraphDB;
import graphdb.Node;
import graphdb.RelationshipSerDeSer;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static graphdb.persistence.Util.checkIfExists;
import static graphdb.persistence.Util.getLatestFile;


/**
 * Created by mkhanwalkar on 7/19/15.
 */
public class DeltaReader {

    static ObjectMapper mapper = new ObjectMapper();


    String location ;
 //   String nodeFileName ;
 //   String relationFileName ;
    GraphDB graphDB;
    public DeltaReader(String location, GraphDB graphDB) {

        this.location = location ;
       // this.nodeFileName = nodeFileName;
        //this.relationFileName = relationFileName;
        this.graphDB = graphDB;

    }

    private void restoreNode(File nodeFile) {
        if (!checkIfExists(nodeFile))
            return;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(nodeFile));

            String s;
            while ((s = reader.readLine()) != null) {

                Node node = mapper.readValue(s, Node.class);
                graphDB.maps.put(node.getId(), node);
            }
            reader.close();

            //       System.out.println(maps);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void restoreRelations(File relationFileName) {
        if (!checkIfExists(relationFileName))
            return;

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(relationFileName));

            String s;
            while ((s = reader.readLine()) != null) {

                RelationshipSerDeSer rs = mapper.readValue(s, RelationshipSerDeSer.class);
                Node n1 = graphDB.maps.get(rs.getSrcId());
                Node n2 = graphDB.maps.get(rs.getTgtId());

                graphDB.addRelationship(n1, n2);
            }
            reader.close();

            //    System.out.println(maps);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    File latestNodeFile ;

    public File getLatestNodeFile()
    {
        return latestNodeFile;
    }



    public void restore() {
     //   graphDB.init();
        latestNodeFile = getLatestFile(".delta.",location);
 //       restoreNode(latestNodeFile);
  //      restoreRelations(getLatestFile(".relation.",location));
//
//        System.out.println(maps);

    }




}
