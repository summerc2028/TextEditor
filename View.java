import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

public class View extends JFrame implements ActionListener {

    public JTextArea area;
    public JPanel tools;
    private final JButton newB, openB, saveB, saveAsB, copyB, pasteB, cutB,
            selectAllB, changeColor;

    private final JMenuItem newM, openM, saveM, saveAsM, quitM, copyM, pasteM,
            cutM, selectAllM, searchM;

    public final JComboBox fn, fnsz;
    private Controller controller;

    public final Highlighter highLighter;
    public final Highlighter.HighlightPainter painter;
    public boolean highlighted;

    public JFileChooser dialog;

    public View() {
        // area
        this.area = new JTextArea(20, 50);

        // button
        this.newB = new JButton("pic/new.gif");
        this.openB = new JButton("pic/open.gif");
        this.saveB = new JButton("pic/save.gif");
        this.saveAsB = new JButton("pic/saveAs.gif");
        this.copyB = new JButton("pic/copy.gif");
        this.pasteB = new JButton("pic/paste.gif");
        this.cutB = new JButton("pic/cut.gif");
        this.selectAllB = new JButton("pic/selectAll.gif");
        this.changeColor = new JButton("change color");

        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getAvailableFontFamilyNames();
        String[] fsizes = { "7", "8", "9", "10", "12", "20", "24", "36", "60",
                "72", "96" };
        this.fn = new JComboBox(fonts);
        this.fnsz = new JComboBox(fsizes);
        this.tools = new JPanel();
        JToolBar tool = new JToolBar();
        tool.add(this.newB);
        tool.add(this.openB);
        tool.add(this.saveB);
        tool.add(this.saveAsB);
        tool.add(this.copyB);
        tool.add(this.pasteB);
        tool.add(this.cutB);
        tool.add(this.selectAllB);
        // tool.add(this.changeColor);
        // this.add(tool, BorderLayout.NORTH);
        JToolBar tool2 = new JToolBar();
        tool2.add(this.changeColor);
        tool2.add(this.fn);
        tool2.add(this.fnsz);
        this.tools.add(tool, BorderLayout.NORTH);
        this.tools.add(tool2, BorderLayout.SOUTH);
        this.add(this.tools, BorderLayout.NORTH);

        this.newB.addActionListener(this);
        this.openB.addActionListener(this);
        this.saveB.addActionListener(this);
        this.saveAsB.addActionListener(this);
        this.copyB.addActionListener(this);
        this.pasteB.addActionListener(this);
        this.cutB.addActionListener(this);
        this.selectAllB.addActionListener(this);
        this.changeColor.addActionListener(this);
        this.fn.addActionListener(this);
        this.fnsz.addActionListener(this);

        // scroll pane
        JScrollPane scroll = new JScrollPane(this.area,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.add(scroll, BorderLayout.CENTER);

        // menu
        JMenuBar JMB = new JMenuBar();
        this.setJMenuBar(JMB);
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMB.add(file);
        JMB.add(edit);

        this.newM = new JMenuItem("New");
        file.add(this.newM);
        this.openM = new JMenuItem("Open");
        file.add(this.openM);
        this.saveM = new JMenuItem("Save");
        file.add(this.saveM);
        this.saveAsM = new JMenuItem("Save As");
        file.add(this.saveAsM);
        this.quitM = new JMenuItem("Quit");
        file.add(this.quitM);
        this.copyM = new JMenuItem("Copy");
        edit.add(this.copyM);
        this.pasteM = new JMenuItem("Paste");
        edit.add(this.pasteM);
        this.cutM = new JMenuItem("Cut");
        edit.add(this.cutM);
        this.selectAllM = new JMenuItem("SelectAll");
        edit.add(this.selectAllM);
        this.searchM = new JMenuItem("Search");
        edit.add(this.searchM);

        this.newM.addActionListener(this);
        this.openM.addActionListener(this);
        this.saveM.addActionListener(this);
        this.saveAsM.addActionListener(this);
        this.quitM.addActionListener(this);
        this.copyM.addActionListener(this);
        this.pasteM.addActionListener(this);
        this.cutM.addActionListener(this);
        this.selectAllM.addActionListener(this);
        this.searchM.addActionListener(this);

        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();

        // dialog
        this.dialog = new JFileChooser(System.getProperty("pic/user.dir"));

        // highlighter
        this.highLighter = new DefaultHighlighter();
        this.painter = new DefaultHighlighter.DefaultHighlightPainter(
                Color.LIGHT_GRAY);
        this.area.setHighlighter(this.highLighter);
        this.highlighted = false;
    }

    public void registerController(Controller c) {
        this.controller = c;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        Object source = e.getSource();

        if (source == this.newB || source == this.newM) {
            this.controller.newAction();
        } else if (source == this.openB || source == this.openM) {
            this.controller.openAction();
        } else if (source == this.saveB || source == this.saveM) {
            this.controller.saveAction();
        } else if (source == this.saveAsB || source == this.saveAsM) {
            this.controller.saveAsAction();
        } else if (source == this.quitM) {
            this.controller.quitAction();
        } else if (source == this.copyB || source == this.copyM) {
            this.controller.copyAction();
        } else if (source == this.pasteB || source == this.pasteM) {
            this.controller.pasteAction();
        } else if (source == this.cutB || source == this.cutM) {
            this.controller.cutAction();
        } else if (source == this.selectAllB || source == this.selectAllM) {
            this.controller.selectAllAction();
        } else if (source == this.changeColor) {
            this.controller.changeColorAction();
        } else if (source == this.searchM) {
            this.controller.searchAction();
        } else if (source == this.fn || source == this.fnsz) {
            this.controller.setFont();
        }

        this.setCursor(Cursor.getDefaultCursor());
    }

    public void renewTitle(String fileName) {
        this.setTitle(fileName);
    }

    public void findFileErrorMessage(String fileName) {
        JOptionPane.showMessageDialog(this,
                "Editor can't find the file called " + fileName);
    }

    public boolean saveFileConfirmation(String fileName) {
        return (JOptionPane.showConfirmDialog(this, "Would you like to save "
                + fileName + " ?", "Save", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
    }
}