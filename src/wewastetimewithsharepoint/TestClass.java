/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wewastetimewithsharepoint;

import java.io.FileInputStream;
import java.util.Properties;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceFeature;
import wewastetimewithsharepoint.wsdl.Lists;
import wewastetimewithsharepoint.wsdl.ListsSoap;

/**
 *
 * @author jan
 */
public class TestClass {


    public static void main(String[] argv) {
        Lists service = new Lists();
        ListsSoap port = service.getListsSoap();
        
        
        Properties p = new Properties();
        try {
        FileInputStream ins = new FileInputStream("pw.properties");
        p.load(ins);
        } catch (Exception e) {
            
        }
        System.out.println(p.getProperty("user"));
        
        //((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, p.getProperty("user"));
        ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, p.getProperty("passwd").trim());
        ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "HTWDD\\s64356");
       // ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "Bla");

        //port.getListItems("HTWDD\\s64356\\test", "", null, null, "3", null, "");
        ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://ishareproject.informatik.htw-dresden.de:8080/Integration/_vti_bin/lists.asmx");

        System.out.println(
                ((BindingProvider) port).getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY)
                );

        port.getListCollection();

       }
}
