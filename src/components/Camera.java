package components;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import engine.Time;
import input.Input;
import math.Matrix4x4;
import math.Quaternion;
import math.Vector3;

public class Camera extends Component
{
	/*Camera Attributes Start*/
	public float aspectRatio;
	public float fieldOfview;
	public float farClipPlane;
	public float nearClipPlane;
	public Matrix4x4 projectionMatrix;
	public Matrix4x4 viewMatrix;
	/*Camera Attributes End*/
	
	private Vector3 mousePreviousPosition = new Vector3(0, 0, 0);
	private float sensitivity = 5f;
	private float moveSpeed = 0.1f;
	
	public Camera(int windowWidth, int windowHeight, GameObject parent)
	{
		gameObject = parent;
		aspectRatio = (float)windowWidth / (float)windowHeight;
		fieldOfview = (float) Math.toRadians(60.0f);
		farClipPlane = 1000f;
		nearClipPlane = 0.1f;
		projectionMatrix = Matrix4x4.projectionMatrix(this);
		viewMatrix = Matrix4x4.viewMatrix(this);
	}
	
	public void Start()
	{
		
	}
	
	public void Update()
	{
		gameObject.transform.Update();
		viewMatrix = Matrix4x4.viewMatrix(this);
	}
	
	public void controlCamera()
	{
		if(Input.Mouse.MouseButtonPressed(GLFW.GLFW_MOUSE_BUTTON_2))
		{
			mousePreviousPosition = Input.Mouse.MousePosition();
		}
		
		if(Input.Mouse.MouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_2))
		{
			Vector3 mouseDeltaPosition = Vector3.Subtraction(Input.Mouse.MousePosition(),mousePreviousPosition);
			Quaternion xAxisRotation = Quaternion.AngleAxis(mouseDeltaPosition.x * sensitivity * Time.deltaTime, Vector3.up);
			Quaternion yAxisRotation = Quaternion.AngleAxis(mouseDeltaPosition.y * sensitivity * Time.deltaTime, Vector3.right);
			gameObject.transform.rotation = Quaternion.MultiplicationQuaternion(
					Quaternion.MultiplicationQuaternion(yAxisRotation,gameObject.transform.rotation), xAxisRotation);
			Update();

			mousePreviousPosition = Input.Mouse.MousePosition();
		}

		if(Input.Mouse.MouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_2) && Input.Keyboard.KeyDown(GLFW.GLFW_KEY_W))
		{
			gameObject.transform.position = Vector3.Addition(gameObject.transform.position, Vector3.Scale(gameObject.transform.back(), moveSpeed));
		}
	
		if(Input.Mouse.MouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_2) && Input.Keyboard.KeyDown(GLFW.GLFW_KEY_A))
		{
			gameObject.transform.position = Vector3.Addition(gameObject.transform.position, Vector3.Scale(gameObject.transform.left(), moveSpeed));
		}
		
		if(Input.Mouse.MouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_2) && Input.Keyboard.KeyDown(GLFW.GLFW_KEY_S))
		{
			gameObject.transform.position = Vector3.Addition(gameObject.transform.position, Vector3.Scale(gameObject.transform.forward(), moveSpeed));
		}
		
		if(Input.Mouse.MouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_2) && Input.Keyboard.KeyDown(GLFW.GLFW_KEY_D))
		{
			gameObject.transform.position = Vector3.Addition(gameObject.transform.position, Vector3.Scale(gameObject.transform.right(), moveSpeed));
		}
		
		if(Input.Mouse.MouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_2) && Input.Keyboard.KeyDown(GLFW.GLFW_KEY_Q))
		{
			gameObject.transform.position = Vector3.Addition(gameObject.transform.position, Vector3.Scale(gameObject.transform.down(), moveSpeed));
		}
		
		if(Input.Mouse.MouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_2) && Input.Keyboard.KeyDown(GLFW.GLFW_KEY_E))
		{
			gameObject.transform.position = Vector3.Addition(gameObject.transform.position, Vector3.Scale(gameObject.transform.up(), moveSpeed));
		}
		Update();
	}
}
