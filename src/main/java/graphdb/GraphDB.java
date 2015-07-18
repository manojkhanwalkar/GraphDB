package graphdb;

import org.codehaus.jackson.map.ObjectMapper;
import query.Request;
import query.Response;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GraphDB {


 String fileName;
protected GraphDB(String fileName)
{
    this.fileName = fileName;
}

    String nodeFileName;
    String relationFileName;

    final String snap = ".snap";

    Map<String,Node> maps = new  HashMap<>();

    private void init()
    {
        this.nodeFileName = fileName + ".node";
        this.relationFileName = fileName + ".relation";

    }

    static ObjectMapper mapper = new ObjectMapper();

    public void save()
    {
        save(nodeFileName,relationFileName);
    }


    private void save(String nodeFileName , String relationFileName)
    {
       try {
            PrintWriter nodeWriter = new PrintWriter(nodeFileName);
           PrintWriter relationWriter = new PrintWriter(relationFileName);

                for ( Node n : maps.values()) {
                    String s = mapper.writeValueAsString(n);
                    nodeWriter.write(s);
                    nodeWriter.write("\n");

                    for (Relationship rs : n.getRelationships())
                    {
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

    private void restoreNode()
    {
        if (!checkIfExists(nodeFileName))
            return ;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(nodeFileName));

            String s  ;
            while ((s=reader.readLine())!=null)
            {

                Node node = mapper.readValue(s, Node.class);
                maps.put(node.getId(), node);
            }
            reader.close();

     //       System.out.println(maps);
        } catch ( IOException e) {
            e.printStackTrace();
        }

    }

    private boolean checkIfExists(String fileName)
    {
        File f = new File(fileName);
        return f.exists();

    }

    private void restoreRelations()
    {
        if (!checkIfExists(relationFileName))
            return ;

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(relationFileName));

            String s  ;
            while ((s=reader.readLine())!=null)
            {

                RelationshipSerDeSer rs = mapper.readValue(s, RelationshipSerDeSer.class);
                Node n1 = maps.get(rs.getSrcId());
                Node n2 = maps.get(rs.getTgtId());

                addRelationship(n1,n2);
            }
            reader.close();

        //    System.out.println(maps);
        } catch ( IOException e) {
            e.printStackTrace();
        }

    }


    public void restore()
    {
        init();
        restoreNode();
        restoreRelations();

//        System.out.println(maps);

    }

    public Response query(Request request)
    {
        Node n = maps.get(request.getId());
        if (n!=null)
        {
            Response response = new Response();
            response.setNode(n);
            return response ;
        }

        return null;
    }



    public Node createOrGetNode( String id) {

        Node n = maps.get(id);

        if (n==null) {
            n = new Node(id);
            maps.put(id, n);
        }


        return n;

    }


    // associations are added between parent and each child
    public void add(Node parent, Node... children)
    {
        for (Node node : children)
        {
            addRelationship(parent,node);

        }

    }

    // Node are assumed to exist and only relationships will be added .
    private void addRelationship(Node parent, Node child)
    {
        parent.addRelationship(child);
        child.addRelationship(parent);

    }

    public void print(String s)
    {
        System.out.println(s);
    }

    int counter=0;

    public  void snapshot() {

        save(nodeFileName+snap+counter,relationFileName+snap+counter);
        counter++;
    }
}
