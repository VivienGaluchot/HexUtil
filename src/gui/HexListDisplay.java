package gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import data.HexLine;
import data.LineProcessor;
import data.Tools;

public class HexListDisplay extends JList<HexLine> implements LineProcessor, FocusListener {
	private static final long serialVersionUID = 1L;

	DefaultListModel<HexLine> hexLineJListModel;

	public HexListDisplay() {
		super();
		addFocusListener(this);

		hexLineJListModel = new DefaultListModel<>();
		setModel(hexLineJListModel);
		setCellRenderer(new HexLineCellRenderer());
	}

	public void addLine(HexLine l) {
		hexLineJListModel.addElement(l);
		repaint();
	}

	public void clearLines() {
		hexLineJListModel.removeAllElements();
	}

	@Override
	public void processLine(HexLine l) {
		addLine(l);
	}

	private class HexLineCellRenderer implements ListCellRenderer<HexLine> {

		private DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

		@Override
		public Component getListCellRendererComponent(JList<? extends HexLine> list, HexLine value, int index,
				boolean isSelected, boolean cellHasFocus) {
			StringBuffer buf = new StringBuffer();

			int maxNb = (int) Math.log10(list.getModel().getSize()) + 1;
			buf.append(String.format("<html><FONT COLOR=\"#888888\">%0" + maxNb + "d</FONT> ", index));
			buf.append("<FONT STYLE=\"background:#FFFFCC\">:</FONT>");
			buf.append(String.format("<FONT STYLE=\"background:#CCFFCC\">%02X</FONT>", value.getSize()));
			buf.append(String.format("<FONT STYLE=\"background:#CCCCFF\">%04X</FONT>", value.getAddress()));
			buf.append(String.format("<FONT STYLE=\"background:#FFCCCC\">%02X</FONT>", value.getType()));
			buf.append("<FONT STYLE=\"background:#CCFFFF\">" + Tools.hexToString(value.getData()) + "</FONT>");
			if(value.isValid())
				buf.append(String.format("<FONT STYLE=\"background:#CCCCCC\">%02X</FONT></html>", value.getChecksum()));
			else
				buf.append(String.format("<FONT STYLE=\"background:#550000; color:#FF0000\"><S>%02X</S></FONT></html>", value.getChecksum()));
			JLabel label = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected,
					cellHasFocus);
			label.setText(buf.toString());
			label.setFont(new Font("Consolas", Font.PLAIN, 13));
			return label;
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		this.clearSelection();
	}

}
