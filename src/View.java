import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;

public class View extends JFrame implements ActionListener {
	
	public JTextArea area;
	private final JButton newB, openB, saveB, saveAsB, copyB, pasteB, cutB, selectAllB;
	
	private final JMenuItem newM, openM, saveM, saveAsM, quitM, copyM, pasteM, cutM, selectAllM;
	private Controller controller;
	
    public final Highlighter highLighter;
    public final Highlighter.HighlightPainter painter;
    public boolean highlighted;
	
	public JFileChooser dialog;

	public View()
	{
		//area
		area = new JTextArea(20,50);
		
		// button
		newB = new JButton("pic/new.gif");
		openB = new JButton("pic/open.gif");
		saveB = new JButton("pic/save.gif");
		saveAsB = new JButton("pic/saveAs.gif");
		copyB = new JButton("pic/copy.gif");
		pasteB = new JButton("pic/paste.gif");
		cutB = new JButton("pic/cut.gif");
		selectAllB = new JButton("pic/selectAll.gif");
		
		JToolBar tool = new JToolBar();
		tool.add(newB);
		tool.add(openB);
		tool.add(saveB);
		tool.add(saveAsB);
		tool.add(copyB);
		tool.add(pasteB);
		tool.add(cutB);
		tool.add(selectAllB);
		add(tool, BorderLayout.NORTH);
		
		newB.addActionListener(this);
		openB.addActionListener(this);
		saveB.addActionListener(this);
		saveAsB.addActionListener(this);
		copyB.addActionListener(this);
		pasteB.addActionListener(this);
		cutB.addActionListener(this);
		selectAllB.addActionListener(this);
		
		// scroll pane
		JScrollPane scroll = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(scroll, BorderLayout.CENTER);
		
		
		// menu
		JMenuBar JMB = new JMenuBar();
		setJMenuBar(JMB);
		JMenu file = new JMenu("File");
		JMenu edit = new JMenu("Edit");
		JMB.add(file);
		JMB.add(edit);
		
		newM = new JMenuItem("New");
		file.add(newM);
		openM = new JMenuItem("Open");
		file.add(openM);
		saveM = new JMenuItem("Save");
		file.add(saveM);
		saveAsM = new JMenuItem("Save As");
		file.add(saveAsM);
		quitM = new JMenuItem("Quit");
		file.add(quitM);
		copyM = new JMenuItem("Copy");
		edit.add(copyM);
		pasteM = new JMenuItem("Paste");
		edit.add(pasteM);
		cutM = new JMenuItem("Cut");
		edit.add(cutM);
		selectAllM = new JMenuItem("SelectAll");
		edit.add(selectAllM);
		
		newM.addActionListener(this);
		openM.addActionListener(this);
		saveM.addActionListener(this);
		saveAsM.addActionListener(this);
		quitM.addActionListener(this);
		copyM.addActionListener(this);
		pasteM.addActionListener(this);
		cutM.addActionListener(this);
		selectAllM.addActionListener(this);
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		
		// dialog
		dialog = new JFileChooser(System.getProperty("pic/user.dir"));
		
		// highlighter
		highLighter = new DefaultHighlighter();
        painter = new DefaultHighlighter.DefaultHighlightPainter(Color.LIGHT_GRAY);
        area.setHighlighter(highLighter);
        highlighted = false;
	}
	
	public void registerController(Controller c)
	{
		controller = c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		
		Object source = e.getSource();
		
		if (source == this.newB || source == this.newM)
			controller.newAction();
		else if (source == this.openB || source == this.openM)
			controller.openAction();
		else if (source == this.saveB || source == this.saveM)
			controller.saveAction();
		else if (source == this.saveAsB || source == this.saveAsM)
			controller.saveAsAction();
		else if (source == this.quitM)
			controller.quitAction();
		else if (source == this.copyB || source == this.copyM)
			controller.copyAction();
		else if (source == this.pasteB || source == this.pasteM)
			controller.pasteAction();
		else if (source == this.cutB || source == this.cutM)
			controller.cutAction();
		else if (source == this.selectAllB || source == this.selectAllM)
			controller.selectAllAction();
		
		this.setCursor(Cursor.getDefaultCursor());
	}
	
	public void renewTitle(String fileName)
	{
		setTitle(fileName);
	}
	
	public void findFileErrorMessage(String fileName)
	{
		JOptionPane.showMessageDialog(this,"Editor can't find the file called "+ fileName);
	}
	
	public boolean saveFileConfirmation(String fileName)
	{
		return (JOptionPane.showConfirmDialog(this, "Would you like to save "+ fileName +" ?","Save",JOptionPane.YES_NO_OPTION)== JOptionPane.YES_OPTION);
	}
}
