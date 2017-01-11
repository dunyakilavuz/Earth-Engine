package main;

import components.Component;
import components.GameObject;
import components.Rigidbody;

import math.Vector3;

public class Orbit extends Component
{
	GameObject gameObject;
	
	float gravityMagnitude;
	Vector3 gravityDirection;
	
	public Orbit(GameObject parent) 
	{
		gameObject = parent;
	}

	public void Start() 
	{
		if(gameObject.name == "Moon")
		{
			gameObject.transform.scale = new Vector3(0.27f, 0.27f, 0.27f);
			gameObject.transform.position = new Vector3(5,0,0);
			gameObject.GetComponent(Rigidbody.class).velocity = new Vector3(0,0,5);
		}
	}

	public void Update() 
	{
		if(gameObject.name == "Moon")
		{
			gravityDirection = Vector3.Normalize(Vector3.Subtraction(new Vector3(0,0,0),gameObject.transform.position));
			//	gravityMagnitude = (float) (1 /  Vector3.Magnitude(Vector3.Subtraction(new Vector3(0,0,0),gameObject.transform.position)));
			gravityMagnitude = 1;
			gameObject.GetComponent(Rigidbody.class).AddForce(gravityMagnitude,gravityDirection);
		}
	}

}
