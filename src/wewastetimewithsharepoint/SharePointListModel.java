/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wewastetimewithsharepoint;

import java.util.List;
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

    @Override
    public boolean isCellEditable(int row, int col) {
        return true;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        if (!getValueAt(row, col).equals(value)) {
            list.getRowItem(row).modifyField(col, (String)value);
            fireTableCellUpdated(row, col);
        }
    }

    public void addRow() {
        list.createItem();
        fireTableRowsInserted(list.getRowCount() - 1, list.getRowCount() - 1);
    }

    public void dropRow(int row) {
        list.deleteItem(row);
        fireTableRowsDeleted(row, row);
    }

    public void syncWithTheRestOfTheWorld(){
        List<Integer> possiblyChanged_WhoKnows = list.flush();

        for (int i : possiblyChanged_WhoKnows) {
            fireTableRowsUpdated(i, i);
        }
    }
}
