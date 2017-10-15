package components;

import java.util.ArrayList;
import java.util.List;

public abstract class Component 
{	
	boolean isActive = true;
	List<Component> componentList = new ArrayList<Component>();
	public GameObject gameObject;
	
	public Component() 
	{
		
	}
	
	public void Awake()
	{
		
	}
	public abstract void Start();
	public abstract void Update();
	
	
	public void AddComponent(Component component)
	{
		if(componentList.contains(component) == false)
		{
			componentList.add(component);	
		}
	}
	
	public <T extends Component>  T GetComponent(Class<T> type)
	{
		for(int i = 0; i < componentList.size(); i++)
		{
			if(componentList.get(i).getClass().getSimpleName().equals(type.getSimpleName()))
			{
				return type.cast(componentList.get(i));
			}
		}
//		System.out.println("No such component is attached.");
		return null;
	}
}
