package graphdb.persistence;

import graphdb.GraphDB;
import graphdb.Node;
import graphdb.Relationship;
import graphdb.RelationshipSerDeSer;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by mkhanwalkar on 7/19/15.
 */
public class SnapshotWriter {

    String location ;
   // String nodeFileName ;
   // String relationFileName ;
    GraphDB graphDB;

    static ObjectMapper mapper = new ObjectMapper();


    public SnapshotWriter(String location, GraphDB graphDB) {

        this.location = location ;
   //     this.nodeFileName = nodeFileName;
     //   this.relationFileName = relationFileName;
        this.graphDB = graphDB;


    }

    public   void save(String nodeFileName, String relationFileName) {
        try {

            nodeFileName = location+nodeFileName;
            relationFileName = location+relationFileName;

            PrintWriter nodeWriter = new PrintWriter(nodeFileName);
            PrintWriter relationWriter = new PrintWriter(relationFileName);

            for (Node n : graphDB.maps.values()) {
                String s = mapper.writeValueAsString(n);
                nodeWriter.write(s);
                nodeWriter.write("\n");

                for (Relationship rs : n.returnRelationship()) {
                    RelationshipSerDeSer r = new RelationshipSerDeSer();
                    r.setSrcId(n.getId());
                    //r.setSrcOrdinal(n.getType().ordinal());
                    r.setTgtId(rs.getTarget().getId());
                    // r.setTgtOrdinal(rs.getTarget().getType().ordinal());

                    String s1 = mapper.writeValueAsString(r);
                    relationWriter.write(s1);
                    relationWriter.write("\n");
                }
            }

            nodeWriter.close();
            relationWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println(maps);


    }




}
