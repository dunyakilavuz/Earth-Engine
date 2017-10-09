package test;

import components.Component;
import components.GameObject;
import math.Quaternion;
import math.Vector3;

public class RotateAround extends Component
{
	GameObject gameObject;
	
	public RotateAround(GameObject parent)
	{
		gameObject = parent;
	}
	
	
	public void Start()
	{
		
	}

	public void Update()
	{
		gameObject.transform.RotateAround(Vector3.zero, Vector3.up, 0.1f);
	}

}
