/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wewastetimewithsharepoint;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author jan
 */
public class SharePointListModel extends AbstractTableModel {

    private final SPList list;

    public SharePointListModel(SPList list) {
        this.list = list;
    }

    @Override
    public String getColumnName(int column) {
        return list.getHeading(column);
    }

    public int getRowCount() {
        return list.getRowCount();
    }

    public int getColumnCount() {
        return list.getColumnCount();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return list.getRowItem(rowIndex).getFieldValue(columnIndex);
    }

}
