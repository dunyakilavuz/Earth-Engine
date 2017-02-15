package engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import components.Camera;
import input.Input;

public class Engine 
{
	long window;
	int windowWidth;
	int windowHeight;
	String windowTitle;
	Scene activeScene;
	boolean isFullScreen = false;
	
	double fpsLastTime = GLFW.glfwGetTime();
	int framesPast = 0;
	double currentTime;
	
	GLFWVidMode vidmode;
	GLFWKeyCallback keyCallBack;
	GLFWMouseButtonCallback mouseCallBack;
	
	public Engine(int windowWidth,int windowHeight,String windowTitle,Scene scene) throws Exception
	{
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		this.windowTitle = windowTitle;
		activeScene = scene;
		
		GLFWErrorCallback.createPrint(System.err).set();
		if ( GLFW.glfwInit() == false )
		{
			throw new IllegalStateException("Unable to initialize GLFW");			
		}
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
		window = GLFW.glfwCreateWindow(windowWidth, windowHeight, windowTitle, 0, 0);
		if ( window == 0 )
		{
			throw new RuntimeException("Failed to create the GLFW window");			
		}
		Input.Init(window, keyCallBack,mouseCallBack);
		vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwSetWindowPos(window,(vidmode.width() - windowWidth) / 2,	(vidmode.height() - windowHeight) / 2);
		GLFW.glfwMakeContextCurrent(window);
		GLFW.glfwSwapInterval(1);
		GLFW.glfwShowWindow(window);
		GL.createCapabilities();
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_LIGHT0);
		
	}

	void Awake() throws Exception
	{
		activeScene.Awake();		
	}
	
	void Start() throws Exception
	{
		Time.Start();
		activeScene.Start();		
	}
	
	void Update()
	{
		Time.Update();
		activeScene.Update();	
		FPSCounter();
	//	MouseTeleporting();
	}
	
	void FPSCounter()
	{		
		 currentTime = GLFW.glfwGetTime();
	     framesPast++;
	     if ( currentTime - fpsLastTime >= 1.0 )
	     {
	         System.out.println("Currently rendering " + 1000.0f / (float)framesPast + " ms/frame.");
	         framesPast = 0;
	         fpsLastTime += 1.0;
	     }
	}
	
	void MouseTeleporting()
	{
		if(Input.Mouse.MouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_2) == true)
		{
			if(Input.Mouse.MousePosition().x >= windowWidth)
			{
				GLFW.glfwSetCursorPos(window, 0.01f, Input.Mouse.MousePosition().y);
			}
			else if(Input.Mouse.MousePosition().x <= 0)
			{
				GLFW.glfwSetCursorPos(window, windowWidth, Input.Mouse.MousePosition().y);
			}
			
			else if(Input.Mouse.MousePosition().y >= windowHeight)
			{
				GLFW.glfwSetCursorPos(window, Input.Mouse.MousePosition().x,0.01f);
			}
			else if(Input.Mouse.MousePosition().y <= 0)
			{
				GLFW.glfwSetCursorPos(window, Input.Mouse.MousePosition().x,windowHeight);
			}
		}
	}
	
	public void StartEngine() throws Exception
	{
		Awake();
		Start();			
		while (GLFW.glfwWindowShouldClose(window) == false) 
		{
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			Update();
			if(Input.Keyboard.KeyPressed(GLFW.GLFW_KEY_ESCAPE))
			{
				GLFW.glfwSetWindowShouldClose(window, true);	
			}
			
			Input.Reset();
			GLFW.glfwSwapBuffers(window);
			GLFW.glfwPollEvents();
		}
		StopEngine();
	}
	
	void StopEngine()
	{
		GLFW.glfwSetErrorCallback(null).free();
		GLFW.glfwDestroyWindow(window);
		GLFW.glfwTerminate();
	}
}
