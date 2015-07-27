package graphdb.persistence;

import graphdb.*;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static graphdb.persistence.Util.checkIfExists;
import static graphdb.persistence.Util.getLatestFile;


//TODO - handles only a single delta file .

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

    private void restoreDelta(File deltaFile) {
        if (!checkIfExists(deltaFile))
            return;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(deltaFile));

            String s;
            while ((s = reader.readLine()) != null) {

              Delta delta = mapper.readValue(s, Delta.class);
              switch (delta.getOperation())
              {
                  case AddNode :
                      graphDB.maps.put(delta.getSrcId(), delta.getSrcNode());
                      break ;
                  case DeleteNode :
                      graphDB.maps.remove(delta.getSrcId());
                      break;
                  case AddRelation :
                      Node n1 = graphDB.maps.get(delta.getSrcId());
                      Node n2 = graphDB.maps.get(delta.getTgtId());
                      graphDB.addRelationship(n1, n2);
                      break ;
                  case DeleteRelation :
                      graphDB.deleteRelationship(delta.getSrcId(),delta.getTgtId());
                      break;
                  default :
                      System.out.println("Invalid operation ");


              }
               // graphDB.maps.put(node.getId(), node);
            }
            reader.close();

            //       System.out.println(maps);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    File latestNodeFile ;

    public File getLatestNodeFile()
    {
        latestNodeFile = getLatestFile("delta",location);

        return latestNodeFile;
    }



    public void restore() {
     //   graphDB.init();
       restoreDelta(latestNodeFile);
  //      restoreRelations(getLatestFile(".relation.",location));
//
//        System.out.println(maps);

    }




}
