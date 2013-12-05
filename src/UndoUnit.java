import javax.swing.event.DocumentEvent.EventType;


public class UndoUnit {
	
	public EventType type;
	public int pos;
	public int length;
	public String change;
	
	public UndoUnit(EventType t, int p, int len, String s) {
		type = t;
		pos = p;
		change = s;
		length = len;
	}
	
	@Override
	public String toString()
	{
		return (type.toString() + ", " + pos + ", " + length + ", " + change);
	}
	
}
