import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;

public class Model {

	public String currentFile = "Untitled";
	public boolean changed;
	private String buffer;
	private boolean insert;
	private boolean updated;
	public String searchText;
	
	public Model()
	{
		currentFile = "Untitled";
		changed = false;
		
		// Undo
		buffer = "";
		insert = true;
		updated = true;
	}
	
	public void UpdateBuffer(char c)
	{
		if (updated)
		{
			buffer = "" + c;
			updated = false;
		}
		else
			buffer = buffer + c;
	}
}
