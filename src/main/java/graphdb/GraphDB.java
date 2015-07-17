package graphdb;

import org.codehaus.jackson.map.ObjectMapper;
import query.Request;
import query.Response;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mkhanwalkar on 7/15/15.
 */
public class GraphDB {


 String fileName;
protected GraphDB(String fileName)
{
    this.fileName = fileName;
}

    String nodeFileName;
    String relationFileName;

    List<Map<String,Node>> maps = new ArrayList<>(NodeType.values().length);

    private void init()
    {
        this.nodeFileName = fileName + ".node";
        this.relationFileName = fileName + ".relation";
        for (int i=0;i<NodeType.values().length;i++ )
        {
            Map<String,Node> map = new HashMap<>();
            maps.add(map);
        }

    }

    static ObjectMapper mapper = new ObjectMapper();


    public void save()
    {
       try {
            PrintWriter nodeWriter = new PrintWriter(nodeFileName);
           PrintWriter relationWriter = new PrintWriter(relationFileName);

            for (Map<String,Node> map : maps) {
                for ( Node n : map.values()) {
                    String s = mapper.writeValueAsString(n);
                    nodeWriter.write(s);
                    nodeWriter.write("\n");

                    for (Relationship rs : n.getRelationships())
                    {
                        RelationshipSerDeSer r = new RelationshipSerDeSer();
                        r.setSrcId(n.getId());
                        r.setSrcOrdinal(n.getType().ordinal());
                        r.setTgtId(rs.getTarget().getId());
                        r.setTgtOrdinal(rs.getTarget().getType().ordinal());

                        String s1 = mapper.writeValueAsString(r);
                        relationWriter.write(s1);
                        relationWriter.write("\n");
                    }
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
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(nodeFileName));

            String s = null ;
            while ((s=reader.readLine())!=null)
            {

                Node node = mapper.readValue(s, Node.class);
                maps.get(node.getType().ordinal()).put(node.getId(), node);
            }
            reader.close();

     //       System.out.println(maps);
        } catch ( IOException e) {
            e.printStackTrace();
        }

    }

    private void restoreRelations()
    {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(relationFileName));

            String s = null ;
            while ((s=reader.readLine())!=null)
            {

                RelationshipSerDeSer rs = mapper.readValue(s, RelationshipSerDeSer.class);
                Node n1 = maps.get(rs.getSrcOrdinal()).get(rs.getSrcId());
                Node n2 = maps.get(rs.getTgtOrdinal()).get(rs.getTgtId());

                addRelationship(n1,n2);
               // maps.get(node.getType().ordinal()).put(node.getId(), node);
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
        Node n = maps.get(request.getType().ordinal()).get(request.getId());
        if (n!=null)
        {
            Response response = new Response();
            response.setNode(n);
            return response ;
        }

        return null;
    }



    public Node createOrGetNode(NodeType type , String id) {

        Node n = maps.get(type.ordinal()).get(id);

        if (n==null) {
            n = new Node(type,id);
            maps.get(type.ordinal()).put(id, n);
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

   }
