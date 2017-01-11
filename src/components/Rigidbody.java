package components;

import engine.Time;
import math.Vector3;

public class Rigidbody extends Component
{
	public GameObject gameObject;
	
	public Vector3 velocity = Vector3.zero;
	Vector3 netForce = Vector3.zero;
	public float mass = 1;
	
	public Rigidbody(GameObject gameObject)
	{
		this.gameObject = gameObject;
	}
	
	public void Start() 
	{

	}

	public void Update() 
	{
		gameObject.transform.position = Vector3.Addition(gameObject.transform.position, Vector3.Scale(velocity, Time.deltaTime));		//Velocity is translated to transform here.			
	}
	
	public void AddForce(float magnitude, Vector3 direction)
	{
		netForce = Vector3.Addition(netForce, Vector3.Scale(Vector3.Normalize(direction), magnitude));
		CalculateNetForce();
	}

	
	void CalculateNetForce()
	{
		if(mass > 0)
		{
			velocity = Vector3.Addition(velocity, Vector3.Scale(netForce, (1 / mass) * Time.deltaTime));
		}
	}
}
