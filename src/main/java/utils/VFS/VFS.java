package utils.VFS;

import java.util.Iterator;

/**
 * Created by said on 10.11.15.
 */
public interface VFS {

    Iterator<String> getIterator(String directory);
}
