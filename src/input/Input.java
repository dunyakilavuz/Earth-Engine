package input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import input.KeyboardHandler;
import input.MouseHandler;

public class Input 
{	
	public static KeyboardHandler Keyboard;
	public static MouseHandler Mouse;
	
	public static void Init(long window, GLFWKeyCallback keyCallBack, GLFWMouseButtonCallback mouseCallBack)
	{
		Keyboard = new KeyboardHandler();
		Mouse = new MouseHandler(window);
		
		GLFW.glfwSetKeyCallback(window, keyCallBack = Keyboard);
		GLFW.glfwSetMouseButtonCallback(window, mouseCallBack = Mouse);
		
		Reset();
	}
	
	public static void Reset()
	{
		Keyboard.resetKeyboard();
		Mouse.resetMouse();
	}

}
