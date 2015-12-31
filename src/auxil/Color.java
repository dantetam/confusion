package auxil;

public class Color {

	public float r,g,b; //0-255
	
	public Color(float x, float y, float z)
	{
		r = x; g = y; b = z;
	}
	
	public void set(float gray)
	{
		r = gray; g = gray; b = gray;
	}
	
}
