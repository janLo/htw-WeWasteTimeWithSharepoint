/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wewastetimewithsharepoint;

import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.xml.ws.BindingProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import wewastetimewithsharepoint.wsdl.GetListCollectionResponse.GetListCollectionResult;
import wewastetimewithsharepoint.wsdl.GetListResponse.GetListResult;
import wewastetimewithsharepoint.wsdl.Lists;
import wewastetimewithsharepoint.wsdl.ListsSoap;

/**
 *
 * @author jan
 */
public class TheUltimativeSharepointBloatConnector {

    private final String username;
    private final String password;
    private final static String hardcodedSharepointWebUrl = "http://ishareproject.informatik.htw-dresden.de:8080/Integration/_vti_bin/lists.asmx";
    private ListsSoap thePort = null;

    public TheUltimativeSharepointBloatConnector(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public List<String> getSomeLists() {
        GetListCollectionResult r = getAStrangeSoapPort().getListCollection();
        Object listResult = r.getContent().get(0);

        LinkedList<String> listsList = new LinkedList<String>();

        if ((listResult != null) && (listResult instanceof ElementNSImpl)) {

            ElementNSImpl node = (ElementNSImpl) listResult;
            Document document = node.getOwnerDocument();
            NodeList listNodes = document.getElementsByTagName("List");
            for (int i = 0; i < listNodes.getLength(); ++i) {
                Node element = listNodes.item(i);
                if (Integer.parseInt(element.getAttributes().getNamedItem("Author").getNodeValue()) > 1) {
                    listsList.add(element.getAttributes().getNamedItem("Title").getNodeValue());
                    //System.out.println(listNodes.item(i).getAttributes().getNamedItem("Title").getNodeValue());
                }
            }
        }
        return listsList;
    }

    public List<String> getListHeadings(String listName) {
        GetListResult r = getAStrangeSoapPort().getList(listName);
        Object listResult = r.getContent().get(0);

        ArrayList<String> headings = new ArrayList<String>();

        if ((listResult != null) && (listResult instanceof ElementNSImpl)) {

            ElementNSImpl node = (ElementNSImpl) listResult;
            Document document = node.getOwnerDocument();
            NodeList fieldNodes = document.getElementsByTagName("Field");

            for (int i = 0; i < fieldNodes.getLength(); ++i) {
                Node element = fieldNodes.item(i);
                if (checkAttr(element, "Hidden", false, true) && attrExist(element, "ColName")) {
                    if (!attrValue(element, "colName").startsWith("tp_")) {
                        headings.add(attrValue(element, "DisplayName"));
                    }
                }
            }
        }
        return headings;
    }

    // TODO: Make this private after testing
    public ListsSoap getAStrangeSoapPort() {
        if (null == thePort) {
            Lists service = new Lists();
            thePort = service.getListsSoap12();

            ((BindingProvider) thePort).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, password.trim());
            ((BindingProvider) thePort).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "HTWDD\\" + username);
            ((BindingProvider) thePort).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, hardcodedSharepointWebUrl);
        }
        return thePort;
    }

    private static boolean checkAttr(Node node, String name, boolean expected, boolean dflt) {
        if (!attrExist(node, name)) {
            return dflt;
        }
        String search = (expected ? "TRUE" : "FALSE");
        if (attrValue(node, name).equalsIgnoreCase(search)) {
            return true;
        }
        return false;
    }

    private static String attrValue(Node node, String name) {
        if (!attrExist(node, name)) {
            return null;
        }
        return node.getAttributes().getNamedItem(name).getNodeValue();
    }

    private static boolean attrExist(Node node, String name) {
        return null != node.getAttributes().getNamedItem(name);
    }
}
