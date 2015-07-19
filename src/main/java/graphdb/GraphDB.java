package graphdb;

import org.codehaus.jackson.map.ObjectMapper;
import query.Request;
import query.Response;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class GraphDB {


    String fileName, location;

    protected GraphDB(String location, String fileName) {
        this.fileName = fileName;
        this.location = location;
    }

    String nodeFileName;
    String relationFileName;

    final String snap = ".snap";

    Map<String, Node> maps = new HashMap<>();

    private void init() {
        this.nodeFileName =  fileName + ".node";
        this.relationFileName = fileName + ".relation";

    }

    static ObjectMapper mapper = new ObjectMapper();

    public void save() {
       // save(nodeFileName, relationFileName);
        snapshot();
    }


    private synchronized  void save(String nodeFileName, String relationFileName) {
        try {

            nodeFileName = location+nodeFileName;
            relationFileName = location+relationFileName;

            PrintWriter nodeWriter = new PrintWriter(nodeFileName);
            PrintWriter relationWriter = new PrintWriter(relationFileName);

            for (Node n : maps.values()) {
                String s = mapper.writeValueAsString(n);
                nodeWriter.write(s);
                nodeWriter.write("\n");

                for (Relationship rs : n.getRelationships()) {
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

    private void restoreNode(File nodeFile) {
        if (!checkIfExists(nodeFile))
            return;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(nodeFile));

            String s;
            while ((s = reader.readLine()) != null) {

                Node node = mapper.readValue(s, Node.class);
                maps.put(node.getId(), node);
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
                Node n1 = maps.get(rs.getSrcId());
                Node n2 = maps.get(rs.getTgtId());

                addRelationship(n1, n2);
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
        init();
        restoreNode(getLatestFile(".node."));
        restoreRelations(getLatestFile(".relation."));

//        System.out.println(maps);

    }

    public synchronized Response query(Request request) {
        Node n = maps.get(request.getId());
        if (n != null) {
            Response response = new Response();
            response.setNode(n);
            return response;
        }

        return null;
    }


    public synchronized Node createOrGetNode(String id) {

        Node n = maps.get(id);

        if (n == null) {
            n = new Node(id);
            maps.put(id, n);
        }


        return n;

    }

    public synchronized void deleteRelationship(String  id1, String id2)
    {
        Node n1 = maps.get(id1);
        Node n2 = maps.get(id2);

        if (n1!=null && n2!=null)
        {
            n1.removeRelationship(n2);
            n2.removeRelationship(n1);
        }
    }

    public synchronized void deleteNode(String id) {

        final Node n = maps.remove(id);

        /* from relationships get nodes , and for each of those nodes - delete this node from their relationship . */  //TODO

        if (n != null) {
            n.getRelationships().forEach(r -> {

                r.getTarget().removeRelationship(n);

            });
        }



    }


    // associations are added between parent and each child
    public synchronized void add(Node parent, Node... children) {
        for (Node node : children) {
            addRelationship(parent, node);

        }

    }

    // Node are assumed to exist and only relationships will be added .
    private void addRelationship(Node parent, Node child) {
        parent.addRelationship(child);
        child.addRelationship(parent);

    }

    public void print(String s) {
        System.out.println(s);
    }

    long counter = System.currentTimeMillis();

    public synchronized void snapshot() {

        save(nodeFileName + snap + counter, relationFileName + snap + counter);
        counter++;
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
