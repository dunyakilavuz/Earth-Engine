package graphics;

public class Material
{
	public Color color;
	public Texture texture;
	
	public Material(Color color) 
	{
		this.color = color;
	}
	
	public Material(Texture texture)
	{
		this.color = Color.white;
		this.texture = texture;
	}
	
	public Material()
	{
		this.color = Color.white;
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
