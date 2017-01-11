package components;

public class GameObject extends Component
{
	public String name;
	public Transform transform = new Transform();
	
	public GameObject() 
	{
		AddComponent(transform);
	}

	public void Awake() 
	{	
		if(isActive == true)
		{
			for(Component component : componentList)
			{
				component.Awake();
			}			
		}
	}
	
	public void Start() 
	{	
		if(isActive == true)
		{
			for(Component component : componentList)
			{
				component.Start();
			}			
		}
	}
	
	public void Update()
	{
		if(isActive == true)
		{
			for(Component component : componentList)
			{
				component.Update();
			}			
		}
	}


}
