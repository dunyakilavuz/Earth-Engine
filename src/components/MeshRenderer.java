package components;

import graphics.Material;
import graphics.Mesh;

public class MeshRenderer extends Component
{
	public Mesh mesh;
	public Material material;

	public MeshRenderer(Mesh mesh) 
	{
		this.mesh = mesh;
		material = new Material();
	}
	
	public MeshRenderer(Mesh mesh, Material material) 
	{
		this.mesh = mesh;
		this.material = material;
	}
	

	public void Start() 
	{
		
	}
	
	public void Update()
	{
		if(isActive == true)
		{
			mesh.Render();
		}
	}
}
