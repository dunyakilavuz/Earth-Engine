package graphics;

public class Material
{
	public Color ambientColor;
	public Color diffuseColor;
	public Color specularColor;
	public float reflectance;	
	private Texture texture;
	
	public Material()
	{
		ambientColor = Color.white;
		diffuseColor = Color.white;
		specularColor = Color.white;
		texture = null;
		this.reflectance = 0;
	}
	
	public Material(Color color, float reflectance) 
	{
		ambientColor = color;
		diffuseColor = color;
		specularColor = color;
		texture = null;
		this.reflectance = reflectance;
	}
	
	public Material(Texture texture)
	{
		ambientColor = Color.white;
		diffuseColor = Color.white;
		specularColor = Color.white;
		this.texture = texture;
		this.reflectance = 0;
	}
	
	public Material(Texture texture, float reflectance)
	{
		ambientColor = Color.white;
		diffuseColor = Color.white;
		specularColor = Color.white;
		this.texture = texture;
		this.reflectance = reflectance;
	}
	
	public Material(Texture texture, float reflectance,Color ambient, Color diffuse, Color specular)
	{
		ambientColor = ambient;
		diffuseColor = diffuse;
		specularColor = specular;
		this.texture = texture;
		this.reflectance = reflectance;
	}
	
	
	public void setTexture(Texture texture)
	{
		this.texture = texture;
	}
	
	public Texture getTexture()
	{
		return texture;
	}
	
	public boolean isTextured()
	{
		if(texture == null)
			return false;
		else
			return true;
	}

}
