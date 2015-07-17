import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Created by mkhanwalkar on 7/16/15.
 */
public class DataCreator {

    public static void main(String[] args) {

        DataCreator creator = new DataCreator();
        creator.test();
    }

    int start = 10; int end = 100; int tot = 10;

    String fileName = "/Users/mkhanwalkar/test/data/input.txt";

    public void test()
    {
        Random random = new Random();

        try {
            PrintWriter nodeWriter = new PrintWriter(fileName);
            //DP12,IP1,CK1,A2
            for (int i=0;i<tot;i++) {
                int x = start + random.nextInt(end - start);
                nodeWriter.write("DP"+x+",");
                x = start + random.nextInt(end - start);
                nodeWriter.write( "IP"+x+",");
                x = start + random.nextInt(end - start);
                nodeWriter.write("CK"+x+",");
                x = start + random.nextInt(end - start);
                nodeWriter.write("A"+x+"\n");
            }
            nodeWriter.flush();
            nodeWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
