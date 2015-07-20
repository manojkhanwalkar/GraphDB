package graphdb.persistence;

import graphdb.GraphDB;
import graphdb.Node;
import graphdb.RelationshipSerDeSer;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by mkhanwalkar on 7/19/15.
 */
public class SnapshotReader {

    static ObjectMapper mapper = new ObjectMapper();


    String location ;
 //   String nodeFileName ;
 //   String relationFileName ;
    GraphDB graphDB;
    public SnapshotReader(String location,GraphDB graphDB) {

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

    private boolean checkIfExists(File f) {
        if (f==null)
            return false ;
        else
            return f.exists();

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

    private File getLatestFile(String name) {

        File directory = new File(location);
        MyFileFilter filter = new MyFileFilter(name);
        File[] files = directory.listFiles(filter);

        if (files==null|| files.length==0)
            return null;

        Arrays.sort(files, new Comparator<File>() {
            public int compare(File f1, File f2) {
                return Long.compare(f1.lastModified(), f2.lastModified());
            }
        });

        return files[files.length-1];
    }


    public void restore() {
     //   graphDB.init();
        restoreNode(getLatestFile(".node."));
        restoreRelations(getLatestFile(".relation."));

//        System.out.println(maps);

    }

    static class MyFileFilter implements FilenameFilter {

        private final String name;

        public MyFileFilter(String name)
        {
            this.name = name ;
        }

        @Override
        public boolean accept(File directory, String fileName) {
            if (fileName.contains(name)) {
                return true;
            }
            return false;
        }
    }




}
