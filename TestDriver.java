
public class TestDriver {
	public static void main(String[] arg)
	{
		View v = new View();
		Model m = new Model();
		Controller c = new Controller(v, m);
		v.registerController(c);
	}
}