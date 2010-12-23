/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wewastetimewithsharepoint;

import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.BindingProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import wewastetimewithsharepoint.wsdl.GetListCollectionResponse.GetListCollectionResult;
import wewastetimewithsharepoint.wsdl.GetListItemsResponse.GetListItemsResult;
import wewastetimewithsharepoint.wsdl.GetListResponse.GetListResult;
import wewastetimewithsharepoint.wsdl.Lists;
import wewastetimewithsharepoint.wsdl.ListsSoap;
import wewastetimewithsharepoint.wsdl.UpdateListItemsResponse.UpdateListItemsResult;

/**
 * Its a SINGLETON!!
 *
 * @author jan
 */
public class TheUltimativeSharepointBloatConnector {

    private final String username;
    private final String password;
    private final static String hardcodedSharepointWebUrl = "http://ishareproject.informatik.htw-dresden.de:8080/Integration/_vti_bin/lists.asmx";
    private ListsSoap thePort = null;

    private static TheUltimativeSharepointBloatConnector instance = null;

    public static void initialize(String username, String password) {
        instance = new TheUltimativeSharepointBloatConnector(username, password);
    }

    public static TheUltimativeSharepointBloatConnector instance() {
        return instance;
    }

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
                }
            }
        }
        return listsList;
    }

    public SPList getList(String listName) {
        return new SPSoapList(this, listName);
    }


    private List<String> getListHeadings(String listName) {
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
                    if (!attrValue(element, "ColName").startsWith("tp_")) {
                        headings.add(attrValue(element, "Name"));
                    }
                }
            }
        }
        return headings;
    }

    private int getListItemCount(String listName) {
        GetListItemsResult r = getAStrangeSoapPort().getListItems(listName, "", null, null, "", null, null);
        Object listResult = r.getContent().get(0);

        ArrayList<String> headings = new ArrayList<String>();

        if ((listResult != null) && (listResult instanceof ElementNSImpl)) {

            ElementNSImpl node = (ElementNSImpl) listResult;
            Document document = node.getOwnerDocument();
            NodeList dataNodes = document.getElementsByTagName("rs:data");

            if (dataNodes.getLength() > 0) {
                return Integer.parseInt(attrValue(dataNodes.item(0), "ItemCount"));
            }
        }
        return -1;
    }

    private void getListItems(SPSoapList list) {
        GetListItemsResult r = getAStrangeSoapPort().getListItems(list.getName(), "", null, null, "", null, null);
        Object listResult = r.getContent().get(0);

        if ((listResult != null) && (listResult instanceof ElementNSImpl)) {
            ElementNSImpl node = (ElementNSImpl) listResult;
            Document document = node.getOwnerDocument();
            NodeList fieldNodes = document.getElementsByTagName("z:row");

            for (int i = 0; i < fieldNodes.getLength(); ++i) {
                Node element = fieldNodes.item(i);
                decomposeListItemEntry(element, list.createItem());
            }
        }
    }

    private SPSoapListItem decomposeListItemEntry(Node element, SPSoapListItem listItem) {
        NamedNodeMap attrMap = element.getAttributes();

        for (int j = 0; j < attrMap.getLength(); ++j) {
            Node item = attrMap.item(j);
            listItem.setExistingAttribute(item.getNodeName().replaceFirst("ows_", ""),
                    item.getNodeValue());
        }
        return listItem;
    }

    private Element createBatch() throws ParserConfigurationException{
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance ();
	DocumentBuilder db = dbf.newDocumentBuilder ();
	Document doc = db.newDocument ();

        Element root = doc.createElement("Batch");
        root.setAttribute("OnError", "Continue");
        root.setAttribute("ListVersion", "1");
        root.setAttribute("ViewName", "");

        doc.appendChild(root);

        return root;
    }

    private Element createListUpdateInsertOrDeleteMethod(Element batch, String type, int cnt) {
        Element methodElement = batch.getOwnerDocument().createElement("Method");
        methodElement.setAttribute("ID", Integer.toString(cnt));
        methodElement.setAttribute("Cmd", type);
        batch.appendChild(methodElement);
        return methodElement;
    }

    private Element createListUpdateInsertOrDeleteMethodField(Element method, String name, String value) {
        Document doc = method.getOwnerDocument();
        Element fieldElement = doc.createElement("Field");
        fieldElement.setAttribute("Name", name);
        fieldElement.appendChild(doc.createTextNode(value));
        method.appendChild(fieldElement);

        return fieldElement;
    }
    
    private void doTheCrudeListUpdateOnTheSharepointWebserviceAndHopeItSucceeded(SPSoapListItem itemToUpdate) {
        try {
            wewastetimewithsharepoint.wsdl.UpdateListItems.Updates vf = new wewastetimewithsharepoint.wsdl.UpdateListItems.Updates();
            vf.getContent().add(itemToUpdate.getAXMLUpdateBatch());
            UpdateListItemsResult r = getAStrangeSoapPort().updateListItems(itemToUpdate.getListName(), vf);

            Object listResult = r.getContent().get(0);

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(TheUltimativeSharepointBloatConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

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

    private class SPSoapList implements SPList {

        private final TheUltimativeSharepointBloatConnector connector;
        private final String listName;
        private List<String> headings;
        private List<SPSoapListItem> items = new LinkedList<SPSoapListItem>();
        private List<SPSoapListItem> deleted = new LinkedList<SPSoapListItem>();

        public SPSoapList(TheUltimativeSharepointBloatConnector connector, String listName) {
            this.connector = connector;
            this.listName = listName;

            loadList();
        }

        private void loadList() {
            headings = connector.getListHeadings(listName);
            connector.getListItems(this);
        }

        public List<String> getColumnHeadings() {
            return headings;
        }

        public int getColumnCount() {
            return headings.size();
        }

        public int getRowCount() {
            return items.size();
        }

        public SPListItem getRowItem(int idx) {
            return items.get(idx);
        }

        public SPListItem insertNewItem(int pos) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public String getHeading(int idx) {
            return headings.get(idx);
        }

        public String getName() {
            return listName;
        }

        public SPSoapListItem createItem() {
             SPSoapListItem newItem = new SPSoapListItem(connector, headings, listName);
             items.add(newItem);
             return newItem;
        }

        public void deleteItem(int idx) {
            deleted.add(items.get(idx));
            items.get(idx).motivateToSuicide();
            items.remove(idx);
        }

        public List<Integer> flush() {
            for (SPSoapListItem item : deleted) {
                item.flush();
            }
            deleted.clear();

            List<Integer> modRows = new LinkedList<Integer>();
            for (SPSoapListItem item : items) {
                if (item.isDirty()) {
                    modRows.add(items.indexOf(item));
                    item.flush();
                }
            }
            return modRows;
        }
    }

    /**
    <z:row ows_Attachments="0"
    ows_Blopp="dg"
    ows_Boing="sdhsdfh"
    
    ows_Created="2010-12-21 17:31:48"
    ows_Created_x0020_Date="1;#2010-12-21 17:31:48"
    
    ows_FSObjType="1;#0"
    ows_FileLeafRef="1;#1_.000"
    ows_FileRef="1;#Integration/Lists/FooBar/1_.000"
    ows_ID="1"
    ows_LinkTitle="sdg"
    ows_MetaInfo="1;#"
    ows_Modified="2010-12-21 17:31:48"
    ows_PermMask="0x7fffffffffffffff"
    ows_Title="sdg"
    ows_UniqueId="1;#{09222896-BD3A-42B6-961B-09B3C5ECBC10}"
    ows__Level="1"
    ows__ModerationStatus="0"
    ows_owshiddenversion="1"/> 
     */
    private class SPSoapListItem implements SPListItem {

        private final TheUltimativeSharepointBloatConnector connector;
        private final List<String> headings;
        private final HashMap<String, String> attributes;
        private final List<String> modified = new LinkedList<String>();
        private boolean isNew = true;
        private boolean delete = false;
        private final String listName;
        

        public SPSoapListItem(TheUltimativeSharepointBloatConnector connector, List<String> headings, String listName) {
            this.connector = connector;
            this.headings = headings;
            this.attributes = new HashMap<String, String>(headings.size() + 14);
            this.attributes.put("ID", "New");
            this.listName = listName;
        }

        public String getListName() {
            return listName;
        }

        private void setExistingAttribute(String key, String value) {
            this.isNew = false;
            attributes.put(key, value);
        }

        public String getFieldValue(int idx) {
            String columnName = headings.get(idx);
            return getFieldValue(columnName);
        }

        public String getFieldValue(String columnName) {
            String value = attributes.get(columnName);
            if (null == value) {
                return "(empty)";
            }
            return value;
        }
        
        public int getId() {
            if (attributes.get("ID").equals("New")) {
                return -1;
            }
            return Integer.parseInt(attributes.get("ID"));
        }

        private void motivateToSuicide() {
            this.delete = true;
        }

        public void modifyField(int column, String newValue) {
            String columnName = headings.get(column);
            modifyField(columnName, newValue);
        }

        public void modifyField(String column, String newValue) {
            modified.add(column);
            attributes.put(column, newValue);
        }

        public boolean isDirty() {
            return isNew || delete || (! modified.isEmpty());
        }

        public void flush() {
            if (isDirty()) {
                doTheCrudeListUpdateOnTheSharepointWebserviceAndHopeItSucceeded(this);
                modified.clear();
                if (isNew) {
                    isNew = false;
                }
            }
        }
        
        private Element getAXMLUpdateBatch() throws ParserConfigurationException {
            String command = "Update";
            if (isNew) {
                command = "New";
            } else {
                if (delete) {
                    command = "Delete";
                }
            }
            Element updateBatch = connector.createBatch();
            Element updateMethod = connector.createListUpdateInsertOrDeleteMethod(updateBatch, command, 1);
            connector.createListUpdateInsertOrDeleteMethodField(updateMethod, "ID", attributes.get("ID"));
            for (String col : modified) {
                connector.createListUpdateInsertOrDeleteMethodField(updateMethod, col, attributes.get(col));
            }
            return updateBatch;
        }

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
