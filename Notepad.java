package TestOne;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.GlyphMetrics;
import java.awt.font.GlyphVector;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JCheckBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Choice;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JToggleButton;
import javax.swing.JSpinner;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class Notepad extends JFrame {
	private static boolean saved;

	private static JTextArea textArea;

	private static JMenuBar menuBar;
	private static JMenu fileMenu;
	private static JMenu editMenu;
	private static JMenuItem openMenuItem;
	private static JMenuItem saveMenuItem;
	private static JMenuItem closeMenuItem;
	private static JMenuItem cutMenuItem;
	private static JMenuItem copyMenuItem;
	private static JMenuItem pasteMenuItem;
	private JMenu mnHelp;
	private JMenuItem mntmAbout;
	private JMenuItem mntmNew;
	private JMenuItem mntmTime;
	private JMenu mnFormat;
	private JCheckBox checkBox;

	public Notepad() {
		setTitle("Tetradka");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(new Dimension(600, 400));
		initWidgets();
		setLayout();
		addWidgets();
		actionListerners();
		saved = false;
		windowListener();
	}

	private void initWidgets() {
		textArea = new JTextArea();
		textArea.setLineWrap(true);

		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		editMenu = new JMenu("Edit");

		openMenuItem = new JMenuItem("Open");
		saveMenuItem = new JMenuItem("Save");
		closeMenuItem = new JMenuItem("Close");

		cutMenuItem = new JMenuItem("Cut");
		copyMenuItem = new JMenuItem("Copy");
		pasteMenuItem = new JMenuItem("Paste");

		menuBar.add(fileMenu);
		menuBar.add(editMenu);

		mntmNew = new JMenuItem("New");

		fileMenu.add(mntmNew);

		fileMenu.add(openMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.add(closeMenuItem);

		editMenu.add(cutMenuItem);
		editMenu.add(copyMenuItem);
		editMenu.add(pasteMenuItem);

		mnFormat = new JMenu("Format");
		menuBar.add(mnFormat);

		checkBox = new JCheckBox("Word Wrap");
		mnFormat.add(checkBox);

		mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				JOptionPane.showMessageDialog(mntmAbout, "Created by Emil, Vasil", "Notepad",
						getDefaultCloseOperation());
			}
		});

		mntmTime = new JMenuItem("Time");
		mntmTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss MM/dd/YYYY");
				Date date = new Date();
				textArea.append(" " + dateFormat.format(date));
			}
		});
		mnHelp.add(mntmTime);
		mnHelp.add(mntmAbout);

	}

	private void setLayout() {
		getContentPane().setLayout(new BorderLayout());
	}

	private void addWidgets() {
		JScrollPane scrollPane = new JScrollPane(textArea);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		this.setJMenuBar(menuBar);
	}

	private void actionListerners() {

		mntmNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				textArea.setText("");

			}
		});

		// action listener for the "Open" button

		openMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				JFileChooser open = new JFileChooser();
				int option = open.showOpenDialog(null);
				if (option == JFileChooser.APPROVE_OPTION) {
					textArea.setText("");
					try {
						Scanner scan = new Scanner(new FileReader(open.getSelectedFile().getPath()));
						Notepad.this.setTitle(open.getSelectedFile().getName());
						while (scan.hasNext()) {
							textArea.append(scan.nextLine());
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		});

		// action listener for the "save" button

		saveMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				JFileChooser save = new JFileChooser();
				save.addChoosableFileFilter(new TxtFileFilter());
				int option = save.showSaveDialog(null);
				if (option == JFileChooser.APPROVE_OPTION) {
					String ext = "";
					String extension = save.getFileFilter().getDescription();
					if (extension.equals("*.txt")) {
						ext = ".txt";
					}
					try {
						BufferedWriter writer = new BufferedWriter(
								new FileWriter(save.getSelectedFile().getPath() + ext));
						writer.write(textArea.getText());
						writer.close();
						saved = true;
						Notepad.this.setTitle(save.getSelectedFile().getName());
					} catch (Exception exception) {
						System.out.println(exception.getMessage());
					}
				}
			}
		});

		// action listener for the "close" button

		closeMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if (saved) {
					System.exit(0);
				} else {
					showSaveDialog();
				}
			}
		});

		// action listener for the "Cut" button

		cutMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				textArea.cut();
			}
		});

		// action listener for the "copy" button

		copyMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				textArea.copy();
			}
		});

		// action listener for the "paste" button

		pasteMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				textArea.paste();
			}
		});
	}// end method actionListerners

	// user choose save or no before close the application
	private void windowListener() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				if (saved) {
					super.windowClosing(windowEvent);
				} else {
					showSaveDialog();
				}
			}
		});
	}

	private void showSaveDialog() {
		int choice = JOptionPane.showConfirmDialog(null,
				"You have not saved the file, are you sure you want to close the application?", "Save before Quitting",
				JOptionPane.YES_NO_OPTION);
		if (choice == 0) {
			System.exit(0);
		}
	}

}
