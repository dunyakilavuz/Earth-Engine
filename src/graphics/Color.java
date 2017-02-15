package graphics;

import math.Vector4;

public class Color 
{
	public static Color white = new Color(new Vector4(1,1,1,1));
	public static Color grey = new Color(new Vector4(0.5f,0.5f,0.5f,0.5f));
	public static Color red = new Color(new Vector4(1,0,0,1));
	public static Color green = new Color(new Vector4(0,1,0,1));
	public static Color blue = new Color(new Vector4(0,0,1,1));

	Vector4 colorVector;
	
	public Color(Vector4 colorVector) 
	{
		this.colorVector = colorVector;
	}
	
	public Vector4 getColorVector()
	{
		return colorVector;
	}

}