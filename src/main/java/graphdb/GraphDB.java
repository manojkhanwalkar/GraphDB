package graphdb;

import graphdb.persistence.DeltaReader;
import graphdb.persistence.DeltaWriter;
import graphdb.persistence.SnapshotReader;
import graphdb.persistence.SnapshotWriter;
import org.codehaus.jackson.map.ObjectMapper;
import query.Request;
import query.Response;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GraphDB {


    String dbName, location, clusterName;

    String completeLocation;

    SnapshotReader snapshotReader ;
    DeltaReader deltaReader;
    SnapshotWriter snapshotWriter;
    DeltaWriter deltaWriter;

    protected GraphDB(String location, String clusterName , String dbName) {
        this.dbName = dbName;
        this.location = location;
        this.clusterName = clusterName;

        this.completeLocation = location+clusterName+"/" + dbName +"/";
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    String nodeFileName;
    String relationFileName;

    public String getNodeFileName() {
        return nodeFileName;
    }

    public void setNodeFileName(String nodeFileName) {
        this.nodeFileName = nodeFileName;
    }

    public String getRelationFileName() {
        return relationFileName;
    }

    public void setRelationFileName(String relationFileName) {
        this.relationFileName = relationFileName;
    }

    File deltaFile ;


    final String snap = "snap";

   public Map<String, Node> maps = new HashMap<>();  // TODO - fix public access .

    boolean running = false ; // used to ensure that deltas are not written during recovery

    public void init() {
        this.nodeFileName =   "node";
        this.relationFileName =  "relation";

        snapshotReader = new SnapshotReader(completeLocation,this);
        snapshotWriter = new SnapshotWriter(completeLocation,this);
        deltaReader = new DeltaReader(completeLocation,this);

    }

    static ObjectMapper mapper = new ObjectMapper();

    public void save() {
       // save(nodeFileName, relationFileName);
        deltaWriter.save();
        snapshot();
        deltaWriter= new DeltaWriter(completeLocation, "delta");

    }



    public synchronized Response query(Request request) {

        {
            StringBuilder responseBuilder = new StringBuilder();
            Node n = maps.get(request.getId());
            Set<Relationship> relations = n.returnRelationship();
            relations.stream().forEach(r-> {
                Node tgt = r.getTarget();
                tgt.returnRelationship().forEach(t->{
                    responseBuilder.append(tgt.getId() + "  " + t.getTarget().getId() + ",");
                });
            });

            Response response = new Response();
            response.setRelString(responseBuilder.toString());
            return response;

        }

     /*   Node n = maps.get(request.getId());
        if (n != null) {
            response.setNode(n);
            response.setRelString(n.returnRelationship().toString());
            return response;
        }*/

    }


    public synchronized Node createOrGetNode(String id) {

        Node n = maps.get(id);

        if (n == null) {
            n = new Node(id);
            maps.put(id, n);
            if (running) {
                Delta d = new Delta();
                d.setOperation(DBOperation.AddNode);
                d.setSrcNode(n);
                deltaWriter.write(d);
            }

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
            if (running) {
                Delta d = new Delta();
                d.setOperation(DBOperation.DeleteRelation);
                d.setSrcId(n1.getId());
                d.setTgtId(n1.getId());
                deltaWriter.write(d);
            }

        }
    }

    public synchronized void deleteNode(String id) {

        final Node n = maps.remove(id);
        if (running) {
            Delta d = new Delta();
            d.setOperation(DBOperation.DeleteNode);
            d.setSrcNode(n);
            deltaWriter.write(d);
        }


        if (n != null) {
            n.returnRelationship().forEach(r -> {

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
    public synchronized void addRelationship(Node parent, Node child) {
        parent.addRelationship(child);
        child.addRelationship(parent);
        if (running) {
            Delta d = new Delta();
            d.setOperation(DBOperation.AddRelation);
            d.setSrcId(parent.getId());
            d.setTgtId(parent.getId());
            deltaWriter.write(d);
        }


    }

    public void print(String s) {
        System.out.println(s);
    }

    long counter = System.currentTimeMillis();

    public synchronized void snapshot() {
        deltaWriter.writeSnapShot(snapshotWriter,nodeFileName + snap + counter,relationFileName + snap + counter);

       // snapshotWriter.save(nodeFileName + snap + counter, relationFileName + snap + counter);
        counter++;
    }


    public void restore() {

        init();
        snapshotReader.restore();
        File nodeFile =  snapshotReader.getLatestNodeFile();
        deltaFile = deltaReader.getLatestNodeFile();
        deltaWriter = new DeltaWriter(completeLocation, "delta");

        if (deltaFile!=null && (nodeFile==null || deltaFile.lastModified() > nodeFile.lastModified()))
        {
            System.out.println("Delta needs to be processed");
            deltaReader.restore();
        }

        // check if latest delta is > greater than last snapshot and if so recover the delta . This should only happen if the system has crashed.

        running=true;
    }
}
