package engine;

import org.lwjgl.glfw.GLFW;

public class Time 
{
	static float lastFrame;
	public static float deltaTime;
	
	public Time() 
	{
		
	}
	
	public static float time()
	{
		return (float) GLFW.glfwGetTime();
	}
	
	public static void  calculateDeltaTime()
	{
		float time = time();
		deltaTime = time - lastFrame;
	    lastFrame = time;
	}
	
	public static void Start()
	{
		
	}
	
	public static void Update()
	{
		 calculateDeltaTime();
	}
}
