import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;

import javax.swing.*;
import javax.swing.event.DocumentEvent.EventType;
import javax.swing.text.*;

public class Model {

	public String currentFile = "Untitled";
	public boolean changed;
	public String searchText;
	Deque<UndoUnit> undoStack;
	Deque<UndoUnit> redoStack;
	public String oldText;
	
	public Model()
	{
		currentFile = "Untitled";
		changed = false;
		undoStack = new ArrayDeque<UndoUnit>();
		redoStack = new ArrayDeque<UndoUnit>();
		oldText = "";
	}
	public void updateText(String s)
	{
		oldText = s;
	}
	
	public void updateUndo(EventType type, int pos, int len, String s)
	{
		UndoUnit u = new UndoUnit(type, pos, len, s);
		undoStack.push(u);
	}
	
	public void updateRedo(UndoUnit u)
	{
		redoStack.push(u);
	}
	
	public boolean hasUndo()
	{
		return (undoStack.size() > 0);
	}
	
	public boolean hasRedo()
	{
		return (redoStack.size() > 0);
	}
	
	public UndoUnit getLastestUndo()
	{
		return undoStack.pop();
	}
	
	public UndoUnit getLastestRedo()
	{
		return redoStack.pop();
	}
	
	public void initializeStacks()
	{
		undoStack.clear();
		redoStack.clear();
	}
}
