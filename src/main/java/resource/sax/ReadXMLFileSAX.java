package resource.sax;

import org.jetbrains.annotations.Nullable;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by said on 30.10.15.
 */
public class ReadXMLFileSAX {
    @Nullable
    public static Object readXML(String xmlFile) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            SaxHandler handler = new SaxHandler();
            //SaxHandler handler = new SaxHandler();
            saxParser.parse(xmlFile, handler);

            return handler.getObject();

        } catch (@SuppressWarnings("OverlyBroadCatchBlock") Exception e) {
            throw new SettingsFileNotFoundException(e);
        }
    }
}
