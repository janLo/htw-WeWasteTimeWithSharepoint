/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wewastetimewithsharepoint;

import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author jan
 */
public class SharePointListModel extends AbstractTableModel {

    private final List<String> columnNames;

    public SharePointListModel(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public String getColumnName(int column) {
        return columnNames.get(column);
    }

    public int getRowCount() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getColumnCount() {
        return columnNames.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
