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

public class MemoryDisplay extends JList<HexLine> implements LineProcessor, FocusListener {
	private static final long serialVersionUID = 1L;

	DefaultListModel<HexLine> hexLineJListModel;
	HexLine currentRelativeAddress;

	public MemoryDisplay() {
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
		if (l.getType() == 0x04 && l.getSize() == 2) {
			currentRelativeAddress = l;
		} else if (l.getType() == 0x00) {
			// int address = l.getAddressRelativeTo(currentRelativeAddress);
			// TODO
		} else if (l.getType() == 0x01) {
			// TODO
			System.out.println("Unsupported line " + l);
		} else {
			System.out.println("Unsupported line " + l);
		}
	}

	private class HexLineCellRenderer implements ListCellRenderer<HexLine> {

		private DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

		@Override
		public Component getListCellRendererComponent(JList<? extends HexLine> list, HexLine value, int index,
				boolean isSelected, boolean cellHasFocus) {
			JLabel label = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected,
					cellHasFocus);
			// label.setText(buf.toString());
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
