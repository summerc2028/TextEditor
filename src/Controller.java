import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;

public class Controller {
	private View view;
	private Model model;
	
	public Controller(View view, Model model)
	{
		this.view = view;
		this.model = model;
	}
	
	public void newAction()
	{
		if(!model.currentFile.equals("Untitled"))
			saveFile(model.currentFile);
		else{
			saveFileAs();
		}

		model.currentFile="Untitled";
		view.area.setText("");
	}
	public void openAction()
	{
		saveOld();
		if(view.dialog.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
			readInFile(view.dialog.getSelectedFile().getAbsolutePath());
		}
	}
	
	public void saveAction()
	{
		if(!model.currentFile.equals("Untitled"))
			saveFile(model.currentFile);
		else
			saveFileAs();
	}
	
	public void saveAsAction()
	{
		saveFileAs();
	}
	
	public void quitAction()
	{
		System.exit(0);
		System.out.println("quit");
	}
	
	public void copyAction()
	{
		view.area.copy();
	}
	
	public void pasteAction()
	{
		view.area.paste();
	}
	
	public void cutAction()
	{
		view.area.cut();
	}
	
	public void selectAllAction()
	{
		view.area.selectAll();
	}
	
	private void saveFileAs() {
		if(view.dialog.showSaveDialog(null)==JFileChooser.APPROVE_OPTION)
			saveFile(view.dialog.getSelectedFile().getAbsolutePath());
	}
	
	private void saveOld() {
		if(model.changed) {
			if(view.saveFileConfirmation(model.currentFile))
				saveFile(model.currentFile);
		}
	}
	
	private void readInFile(String fileName) {
		try {
			FileReader r = new FileReader(fileName);
			view.area.read(r,null);
			r.close();
			model.currentFile = fileName;
			view.renewTitle(model.currentFile);
			model.changed = false;
		}
		catch(IOException e) {
			Toolkit.getDefaultToolkit().beep();
			view.findFileErrorMessage(model.currentFile);
		}
	}
	
	private void saveFile(String fileName) {
		try {
			FileWriter w = new FileWriter(fileName);
			view.area.write(w);
			w.close();
			model.currentFile = fileName;
			view.setTitle(model.currentFile);
			model.changed = false;
		}
		catch(IOException e) {
		}
	}
}
