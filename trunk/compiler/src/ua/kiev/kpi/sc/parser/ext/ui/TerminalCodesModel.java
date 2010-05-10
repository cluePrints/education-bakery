package ua.kiev.kpi.sc.parser.ext.ui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class TerminalCodesModel extends AbstractTableModel{
	private ActionTableModel actionTableModel;
	private List<String> tokenIdxes;
	public TerminalCodesModel(ActionTableModel actionTableModel) {
		super();
		this.actionTableModel = actionTableModel;
		tokenIdxes = actionTableModel.getTokenNamesIndexed();
	}

	
	public int getColumnCount() {
		return 2;
	}

	
	public int getRowCount() {
		return actionTableModel.TERMINAL_COUNT+1;
	}

	
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return rowIndex;
		} else {
			return tokenIdxes.get(rowIndex);
		}
	}

}
