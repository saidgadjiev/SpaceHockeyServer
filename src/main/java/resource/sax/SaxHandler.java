package resource.sax;

/**
 * Created by said on 30.10.15.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import resource.reflection.ReflectionHelper;

public class SaxHandler extends DefaultHandler {
    final private Logger logger = LogManager.getLogger(SaxHandler.class.getName());
    @SuppressWarnings({"FieldCanBeLocal", "NonConstantFieldWithUpperCaseName"})
    private static String CLASSNAME = "class";
    private String element = null;
    private Object object = null;

    @Override
    public void startDocument() throws SAXException {
    }

    @Override
    public void endDocument() throws SAXException {
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (!qName.equals(CLASSNAME))
            element = qName;
        else {
            String className = attributes.getValue(0);
            object = ReflectionHelper.createInstance(className);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        element = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (element != null) {
            String value = new String(ch, start, length);

            ReflectionHelper.setFieldValue(object, element, value);
        }
    }

    @Nullable
    public Object getObject() {
        return object;
    }
}