package graphics;

import math.Vector3;
import math.Vector4;

public class Material
{
	public Vector4 color;
	public Texture texture;
	
	public Material(Vector4 color) 
	{
		this.color = color;
	}
	
	public Material(Texture texture)
	{
		this.color = new Vector4(1, 1, 1, 1);
		this.texture = texture;
	}
	
	public Material()
	{
		this.color = new Vector4(1, 1, 1, 1);
	}
	
	public void setTexture(Texture texture)
	{
		this.texture = texture;
	}
	
	public boolean isTextured()
	{
		if(texture == null)
			return false;
		else
			return true;
	}

}
