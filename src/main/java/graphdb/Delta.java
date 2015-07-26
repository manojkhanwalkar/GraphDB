package graphdb;

/**
 * Created by mkhanwalkar on 7/19/15.
 */
public class Delta {



    /*

    1. For add - Src Node information
    2. For delete Src Node id
    3. For add relationship - Src and Tgt Node id
    4. For delete relation ship - src and tgt node id
     */

    DBOperation operation ;
    String srcId;
    String tgtId;

    Node srcNode ;

    public DBOperation getOperation() {
        return operation;
    }

    public void setOperation(DBOperation operation) {
        this.operation = operation;
    }

    public String getSrcId() {
        return srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId;
    }

    public String getTgtId() {
        return tgtId;
    }

    public void setTgtId(String tgtId) {
        this.tgtId = tgtId;
    }

    public Node getSrcNode() {
        return srcNode;
    }

    public void setSrcNode(Node srcNode) {
        this.srcNode = srcNode;
    }
}
