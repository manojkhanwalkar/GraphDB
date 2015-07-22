package graphdb.persistence;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by mkhanwalkar on 7/21/15.
 */
public class Util {


    public static boolean checkIfExists(File f) {
        if (f==null)
            return false ;
        else
            return f.exists();

    }

    public static File getLatestFile(String name, String location) {

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
