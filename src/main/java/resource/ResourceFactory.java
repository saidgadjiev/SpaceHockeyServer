package resource;

<<<<<<< HEAD
=======
/**
 * Created by said on 30.10.15.
 */

>>>>>>> RK3
import resource.properties.ParsePropertiesFile;
import resource.sax.ReadXMLFileSAX;
import utils.VFS.VFS;
import utils.VFS.VFSImpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by said on 30.10.15.
 */

/**
 * Created by said on 30.10.15.
 */
public class ResourceFactory {
    Map<String, Resource> resources = new HashMap<>();
    private static ResourceFactory resourceFactory = null;

    public static ResourceFactory getInstance() {
        if (resourceFactory == null) {
            resourceFactory = new ResourceFactory();
        }

        return resourceFactory;
    }

    public Resource loadResource(String resourcePath) {
        Resource resource;
        if (!resources.containsKey(resourcePath)) {
            if (resourcePath.substring(resourcePath.lastIndexOf(".") + 1).equals("properties")) {

                ParsePropertiesFile propertiesParser = new ParsePropertiesFile();

                propertiesParser.parse(resourcePath);

                resource = (Resource) propertiesParser.getObject();
            } else {
                resource = (Resource) ReadXMLFileSAX.readXML(resourcePath);
            }
            //resource.setCorrectState();
            resources.put(resourcePath, resource);
        }

        return resources.get(resourcePath);
    }

    public void loadAllResources(String resourceDirectory) {
        VFS vfs = new VFSImpl(resourceDirectory);
        Iterator<String> iterator = vfs.getIterator(resourceDirectory);

        while (iterator.hasNext()) {
            String resourcePath = iterator.next();
            loadResource(resourcePath);
        }
    }
}
