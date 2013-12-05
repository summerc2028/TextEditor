import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentEvent.EventType;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class Controller {
    private final View view;
    private final Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        view.area.getDocument().addDocumentListener(new MyDocumentListener());
    }

    public void newAction() {
        if (!this.model.currentFile.equals("Untitled")) {
            this.saveFile(this.model.currentFile);
        } else {
            this.saveFileAs();
        }

        this.model.currentFile = "Untitled";
        this.view.area.setText("");
    }

    public void openAction() {
        this.saveOld();
        if (this.view.dialog.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            this.readInFile(this.view.dialog.getSelectedFile()
                    .getAbsolutePath());
        }
    }

    public void saveAction() {
        if (!this.model.currentFile.equals("Untitled")) {
            this.saveFile(this.model.currentFile);
        } else {
            this.saveFileAs();
        }
    }

    public void saveAsAction() {
        this.saveFileAs();
    }

    public void quitAction() {
        System.exit(0);
        System.out.println("quit");
    }

    public void copyAction() {
        this.view.area.copy();
    }

    public void pasteAction() {
        this.view.area.paste();
    }

    public void cutAction() {
        this.view.area.cut();
    }

    public void selectAllAction() {
        this.view.area.selectAll();
    }

    public void changeColorAction() {
        this.view.area.setForeground(JColorChooser.showDialog(this.view.area,
                "Choose Color", Color.black));
    }

    public void setFont() {
        this.view.area
                .setFont(new Font((String) this.view.fn.getSelectedItem(),
                        Font.PLAIN, Integer.parseInt(this.view.fnsz
                                .getSelectedItem().toString())));
    }

    public void searchAction() {
        new MyJDialog();
    }
    
    public void undo() {
    	if (!model.hasUndo()) return;
    	UndoUnit u = model.getLastestUndo();
    	model.updateRedo(u);
    	if (u.type.equals(EventType.REMOVE)){
        	view.area.insert(u.change, u.pos);
        	model.getLastestUndo();
    	}
    	else if (u.type.equals(EventType.INSERT)) {
    		String newText = model.oldText.substring(0, u.pos) + (model.oldText.substring(u.pos + u.length, model.oldText.length()));
    		view.area.setText(newText);
        	model.getLastestUndo();
        	if (newText.length() > 0) model.getLastestUndo();
    	}
    	try {
			model.updateText(view.area.getDocument().getText(0, view.area.getDocument().getLength()));
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		
    }
    
    public void redo() {

    	System.out.println(model.redoStack);
    	if (!model.hasRedo()) return;
    	UndoUnit u = model.getLastestRedo();
    	if (u.type.equals(EventType.REMOVE)){
    		String newText = model.oldText.substring(0, u.pos) + (model.oldText.substring(u.pos + u.length, model.oldText.length()));
    		view.area.setText(newText);
    	}
    	else if (u.type.equals(EventType.INSERT)) {
        	view.area.insert(u.change, u.pos);
    	}
    	try {
			model.updateText(view.area.getDocument().getText(0, view.area.getDocument().getLength()));
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
    }

    private void saveFileAs() {
        if (this.view.dialog.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            this.saveFile(this.view.dialog.getSelectedFile().getAbsolutePath());
        }
    }

    private void saveOld() {
        if (this.model.changed) {
            if (this.view.saveFileConfirmation(this.model.currentFile)) {
                this.saveFile(this.model.currentFile);
            }
        }
    }

    private void readInFile(String fileName) {
        try {
            FileReader r = new FileReader(fileName);
            this.view.area.read(r, null);
            r.close();
            this.model.currentFile = fileName;
            this.view.renewTitle(this.model.currentFile);
            this.model.changed = false;
        } catch (IOException e) {
            Toolkit.getDefaultToolkit().beep();
            this.view.findFileErrorMessage(this.model.currentFile);
        }
    }

    private void saveFile(String fileName) {
        try {
            FileWriter w = new FileWriter(fileName);
            this.view.area.write(w);
            w.close();
            this.model.currentFile = fileName;
            this.view.setTitle(this.model.currentFile);
            this.model.changed = false;
        } catch (IOException e) {
        }
    }
    
    class MyDocumentListener implements DocumentListener {
        private void updateUndoStack(DocumentEvent e) {
        	if (e.getType().equals(EventType.INSERT)) {
        		try {
        			model.updateUndo(e.getType(), e.getOffset(), e.getLength(), e.getDocument().getText(e.getOffset(), e.getLength()));
        		} catch (BadLocationException e1) {
        			e1.printStackTrace();
        		}
        	}
        	else if (e.getType().equals(EventType.REMOVE)) {
        		model.updateUndo(e.getType(), e.getOffset(), e.getLength(), model.oldText.substring(e.getOffset(), e.getLength() + e.getOffset()));
        	}
        	try {
				model.updateText(e.getDocument().getText(0, e.getDocument().getLength()));
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
        }
		@Override
		public void changedUpdate(DocumentEvent e) {
			updateUndoStack(e);
		}
		@Override
		public void insertUpdate(DocumentEvent e) {
			updateUndoStack(e);
		}
		@Override
		public void removeUpdate(DocumentEvent e) {
			updateUndoStack(e);
		}
    } 
    class MyJDialog extends JDialog implements ActionListener {
        private final JTextField jte1;
        private final JButton jbu = new JButton("Find next");
        private String s;
        private final String y = Controller.this.view.area.getText();
        private int index = -1;

        public MyJDialog() {
            this.setBounds(600, 300, 450, 70);
            JPanel j = new JPanel();
            j.setLayout(new GridLayout(1, 3));
            this.add(j, 0);
            j.add(new JLabel("Find content  :"), 0);
            this.jte1 = new JTextField();
            j.add(this.jte1, 1);
            this.jte1.addCaretListener(new CaretListener() {
                @Override
                public void caretUpdate(CaretEvent g) {
                    MyJDialog.this.s = MyJDialog.this.jte1.getText();
                }

            });
            j.add(this.jbu, 2);
            this.jbu.addActionListener(this);
            this.setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == this.jbu) {
                if (this.s == null) {
                    JOptionPane.showMessageDialog(this, "Error with input.");
                } else if (this.s != null) {
                    if (this.y == null) {
                        JOptionPane.showMessageDialog(this,
                                "Text area is empty.");
                    } else {
                        if (this.index == -1) {
                            this.index = this.y.indexOf(this.s);
                            Controller.this.view.area.select(this.index,
                                    this.index + this.s.length());
                        } else {
                            this.index = this.y.indexOf(this.s, this.index
                                    + this.s.length());
                            if (this.index - this.s.length() >= -2) {
                                Controller.this.view.area.select(this.index,
                                        this.index + this.s.length());
                            }
                        }
                    }
                }
            }
        }
    }
}
