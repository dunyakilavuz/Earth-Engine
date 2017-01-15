package components;

import engine.Time;
import math.Vector3;

public class Rigidbody extends Component
{
	public GameObject gameObject;
	
	public Vector3 velocity = Vector3.zero;
	public Vector3 acceleration = Vector3.zero;
	public Vector3 lastAcceleration = Vector3.zero;
	public Vector3 newAcceleration = Vector3.zero;
	public Vector3 averageAcceleration = Vector3.zero;
	public Vector3 force = Vector3.zero;
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
		gameObject.transform.position = 
				Vector3.Addition(
				Vector3.Addition(gameObject.transform.position, Vector3.Scale(velocity, Time.deltaTime)), 
				Vector3.Scale(lastAcceleration, (float) (1/2 * Math.pow(Time.deltaTime, 2))));
	}
	
	public void AddForce(float magnitude, Vector3 direction)
	{
		force = Vector3.Scale(direction, magnitude);
		lastAcceleration = acceleration;

		newAcceleration = Vector3.Scale(force, 1 / mass);
		averageAcceleration =  Vector3.Scale(Vector3.Addition(newAcceleration, lastAcceleration),0.5f);
		velocity = Vector3.Addition(velocity, Vector3.Scale(averageAcceleration, Time.deltaTime));
	}

}
