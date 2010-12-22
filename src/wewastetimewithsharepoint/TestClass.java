/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wewastetimewithsharepoint;

import com.sun.org.apache.xerces.internal.dom.ElementImpl;
import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;
import java.io.FileInputStream;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import wewastetimewithsharepoint.wsdl.GetListCollectionResponse.GetListCollectionResult;
import wewastetimewithsharepoint.wsdl.GetListItemsResponse.GetListItemsResult;
import wewastetimewithsharepoint.wsdl.GetListResponse.GetListResult;

/**
 *
 * @author jan
 */
public class TestClass {


    public static void main(String[] argv) throws XPathExpressionException, ParserConfigurationException {
                
        Properties p = new Properties();
        try {
            FileInputStream ins = new FileInputStream("pw.properties");
            p.load(ins);
        } catch (Exception e) {
        }
       
        
        String passwd = p.getProperty("passwd").trim();
        String user = "s64356";

        TheUltimativeSharepointBloatConnector con = new TheUltimativeSharepointBloatConnector(user, passwd);
       
        //GetListResult r = con.getAStrangeSoapPort().getList("FooBar");

        wewastetimewithsharepoint.wsdl.GetListItems.ViewFields vf =new wewastetimewithsharepoint.wsdl.GetListItems.ViewFields();

        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance ();
	DocumentBuilder db = dbf.newDocumentBuilder ();
	Document doc = db.newDocument ();

        Element root = doc.createElement("ViewFields");
        doc.appendChild(root);
        
        LinkedList<String> fields = new LinkedList<String>();
        fields.add("ID");
        fields.add("Title");
        
        for (String field : fields) {
            Element fieldElement = doc.createElement("FieldRef");
            Attr fieldAttrib = doc.createAttribute("Name");
            fieldAttrib.setNodeValue(field);
            root.appendChild(fieldElement);
        }

        vf.getContent().add(doc.getDocumentElement());

        GetListItemsResult r = con.getAStrangeSoapPort().getListItems("FooBar", "", null, vf, "", null, null);
        //GetListCollectionResult r = con.getAStrangeSoapPort().getListCollection();
        Object listResult = r.getContent().get(0);

        if ((listResult != null) && (listResult instanceof ElementNSImpl)) {

            ElementNSImpl node = (ElementNSImpl) listResult;
            Document document = node.getOwnerDocument();


           /* XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            NodeList fieldNodes = (NodeList) xpath.evaluate("List/Fields/Field", document, XPathConstants.NODESET);
*/
            NodeList fieldNodes = document.getElementsByTagName("z:row");


            for (int i = 0; i < fieldNodes.getLength(); ++i) {
                Node element = fieldNodes.item(i);

                NamedNodeMap attrMap = element.getAttributes();

                for (int j=0; j < attrMap.getLength(); ++j) {
                    Node item = attrMap.item(i);
                    //item.getNodeName()
                }

                /*if (checkAttr(element, "Hidden", false, true) && attrExist(element, "ColName")) {
                    //listsList.add(element.getAttributes().getNamedItem("DisplayName").getNodeValue());
                    System.out.println(
                            attrValue(element, "ColName") + " " +
                            attrValue(element, "DisplayName"));
                }*/

            }


            System.out.println("SharePoint Online Lists Web Service Response:" + TestClass.xmlToString(document));
        }

    }

















    private static  boolean checkAttr(Node node, String name, boolean expected, boolean dflt) {
        if (! attrExist(node, name)) {
            return dflt;
        }
        String search = (expected ? "TRUE" : "FALSE");
        if (attrValue(node, name).equalsIgnoreCase(search)) {
            return true;
        }
        return false;
    }

    private static String attrValue(Node node, String name) {
        if (! attrExist(node, name)) {
            return null;
        }
        return node.getAttributes().getNamedItem(name).getNodeValue();
    }

    private static boolean attrExist(Node node, String name) {
        return null != node.getAttributes().getNamedItem(name);
    }


    public static String xmlToString(Document docToString) {

        String returnString = "\n-------------- XML START --------------\n";

        try {
            //create string from xml tree
            //Output the XML
            //set up a transformer
            TransformerFactory transfac = TransformerFactory.newInstance();
            Transformer trans;
            trans = transfac.newTransformer();
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            StringWriter sw = new StringWriter();
            StreamResult streamResult = new StreamResult(sw);
            DOMSource source = new DOMSource(docToString);
            trans.transform(source, streamResult);
            String xmlString = sw.toString();
            //print the XML
            returnString = returnString + xmlString;
        } catch (TransformerException ex) {
            Logger.getLogger(TestClass.class.getName()).log(Level.SEVERE, null, ex);
        }

        returnString = returnString + "-------------- XML END --------------";

        return returnString;

    }
}
