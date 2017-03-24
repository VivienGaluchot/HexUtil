package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.filechooser.FileFilter;

import data.HexFile;

public class HexUtilGui implements ActionListener {

	private static String ACTION_OPEN = "action_open";
	private static String ACTION_QUIT = "action_quit";

	private JFrame mainFrame;
	private HexListDisplay hexDisplayPanel;

	public static void main(String[] args) {
		HexUtilGui x = new HexUtilGui();
		x.loadFile(new File("test.hex"));
	}

	public HexUtilGui() {
		mainFrame = new JFrame();
		mainFrame.setTitle("HexUtil");

		mainFrame.setLayout(new GridBagLayout());

		// MenuBar
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Fichier");

		JMenuItem item = new JMenuItem("Ouvrir");
		item.setActionCommand(ACTION_OPEN);
		item.addActionListener(this);
		menu.add(item);

		item = new JMenuItem("Quitter");
		item.setActionCommand(ACTION_QUIT);
		item.addActionListener(this);
		menu.add(item);

		menuBar.add(menu);
		mainFrame.setJMenuBar(menuBar);

		// MapPanem
		JPanel panel = new JPanel();
		panel.setFocusable(true);
		panel.setMinimumSize(new Dimension(150, 150));
		panel.setPreferredSize(new Dimension(300, 300));
		panel.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				panel.requestFocusInWindow();
			}
		});

		// DisplayPanel
		hexDisplayPanel = new HexListDisplay();
		JScrollPane scroll = new JScrollPane(hexDisplayPanel);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panel, scroll);
		mainFrame.add(split, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		split.setDividerLocation(800 - 300);
		split.setResizeWeight(1);

		mainFrame.setSize(new Dimension(800, 600));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainFrame.setVisible(true);
		mainFrame.setLocationRelativeTo(null);
	}

	public void loadFile(File file) {
		HexFile hf = new HexFile();
		hf.addProcessor(hexDisplayPanel);
		hexDisplayPanel.clearLines();
		hf.processFile(file.getAbsolutePath());
	}

	// Actions

	private static File lastOpennedPath = null;

	public void action_open() {
		JFileChooser fc = new JFileChooser();
		if (lastOpennedPath != null)
			fc.setSelectedFile(lastOpennedPath);
		fc.addChoosableFileFilter(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.getName().toLowerCase().endsWith(".hex");
			}

			@Override
			public String getDescription() {
				return "Fichier .HEX (Intel)";
			}
		});
		int r = fc.showOpenDialog(mainFrame);
		if (r == JFileChooser.APPROVE_OPTION) {
			lastOpennedPath = fc.getSelectedFile();
			loadFile(lastOpennedPath);
		}
	}

	public void action_quit() {
		System.exit(0);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String ae = arg0.getActionCommand();
		if (ae.compareTo(ACTION_OPEN) == 0)
			action_open();
		else if (ae.compareTo(ACTION_QUIT) == 0)
			action_quit();
		else
			System.out.println("Unknown action : " + ae);
	}
}
