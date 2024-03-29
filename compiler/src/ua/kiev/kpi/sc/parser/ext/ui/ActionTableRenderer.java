package ua.kiev.kpi.sc.parser.ext.ui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class ActionTableRenderer extends DefaultTableCellRenderer{
	private static final Color COLOR_NUMBER = new Color(239, 239, 239);
	private static final Color COLOR_SHIFT = new Color(187, 255, 187);
	private static final Color COLOR_REDUCE = new Color(128, 128, 255);
	private static final Color COLOR_ERROR = new Color(255, 128, 128);

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
				row, column);
		Color newColor = Color.WHITE;
		if (value instanceof String) {
			String str = (String) value;
			if (str.startsWith(ActionTableModel.PREFFIX_ERROR)) {
				newColor = COLOR_ERROR;
			}
			if (str.startsWith(ActionTableModel.PREFFIX_REDUCE)) {
				newColor = COLOR_REDUCE;
			}
			if (str.startsWith(ActionTableModel.PREFFIX_SHIFT)) {
				newColor = COLOR_SHIFT;
			}
		} else if (value instanceof Integer){
			newColor = COLOR_NUMBER;
		}
		if (isSelected) {
			newColor = newColor.darker();
			this.setBorder(new LineBorder(newColor.darker()));
		}
		setBackground(newColor);
		return comp;
	}
}
