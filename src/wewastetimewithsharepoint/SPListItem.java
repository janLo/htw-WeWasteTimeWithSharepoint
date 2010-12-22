/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wewastetimewithsharepoint;

/**
 *
 * @author jan
 */
public interface SPListItem {
    public String getFieldValue(int idx);
    public String getFieldValue(String columnName);
}
