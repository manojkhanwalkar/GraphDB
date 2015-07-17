package graphdb;

/**
 * Created by mkhanwalkar on 7/15/15.
 */
public class Relationship {

    Node target;

    public Relationship(Node target) {
        this.target = target;
    }

    public Node getTarget() {
        return target;
    }

    public void setTarget(Node target) {
        this.target = target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Relationship that = (Relationship) o;

        if (!target.equals(that.target)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return target.hashCode();
    }


    @Override
    public String toString() {
        return "Relationship{" +
                "target=" + target.getName() +
                '}';
    }
}
