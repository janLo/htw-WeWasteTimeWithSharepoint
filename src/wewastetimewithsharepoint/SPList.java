/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wewastetimewithsharepoint;

import java.util.List;

/**
 *
 * @author jan
 */
public interface SPList {
    public List<String> getColumnHeadings();
    public String getHeading(int idx);
    public int getColumnCount();
    public int getRowCount();
    public SPListItem getRowItem(int idx);
    public SPListItem insertNewItem(int pos);
}
