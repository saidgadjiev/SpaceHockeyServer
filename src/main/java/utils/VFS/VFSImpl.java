package utils.VFS;

import java.io.File;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by said on 10.11.15.
 */
public class VFSImpl implements VFS {
    private String dirName;

    public VFSImpl(String dirName) {
        this.dirName = dirName;
    }

    public Iterator<String> getIterator(String directory) {
        return new FileIterator(directory);
    }

    private class FileIterator implements Iterator<String> {
        private Queue<File> files = new LinkedList<>();

        public FileIterator(String directory) {
            //noinspection ConstantConditions
            Collections.addAll(files, (new File(directory)).listFiles());
        }

        @Override
        public boolean hasNext() {
            return !files.isEmpty();
        }

        @Override
        public String next() {
            File file = files.peek();

            if (file.isDirectory()) {
                //noinspection ConstantConditions
                Collections.addAll(files, file.listFiles());
            }

            return files.poll().getPath();
        }
    }
}