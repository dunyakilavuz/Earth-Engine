package input;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import math.Vector3;

public class MouseHandler extends GLFWMouseButtonCallback
{
	private final int MOUSE_SIZE = 16;
	private int[] mouseButtonStates = new int[MOUSE_SIZE];
	private boolean[] activeMouseButtons = new boolean[MOUSE_SIZE];
	private long lastMouseNS = 0;
	private long mouseDoubleClickPeriodNS = 1000000000 / 5;
	private int NO_STATE = -1;
	private long window;
	
	private DoubleBuffer xBuffer;
	private DoubleBuffer yBuffer;
	
	protected MouseHandler(long window)
	{
		this.window = window;
	}

	protected void resetMouse()
    {
        for (int i = 0; i < mouseButtonStates.length; i++)
        {
            mouseButtonStates[i] = NO_STATE;
        }

        long now = System.nanoTime();

        if (now - lastMouseNS > mouseDoubleClickPeriodNS)
            lastMouseNS = 0;
    }

	@Override
	public void invoke(long window, int button, int action, int mods) 
	{
		activeMouseButtons[button] = action != GLFW.GLFW_RELEASE;
        mouseButtonStates[button] = action;
	}
	
	 public boolean MouseButtonDown(int button)
	 {
		 return activeMouseButtons[button];
	 }

	 public boolean MouseButtonPressed(int button)
	 {
	     return mouseButtonStates[button] == GLFW.GLFW_PRESS;
	 }

	 public boolean MouseButtonReleased(int button)
	 {
	     boolean flag = mouseButtonStates[button] == GLFW.GLFW_RELEASE;

	     if (flag)
	         lastMouseNS = System.nanoTime();

	     return flag;
	 }

	 public boolean MouseButtonDoubleClicked(int button)
	 {
	     long last = lastMouseNS;
	     boolean flag = MouseButtonReleased(button);

	     long now = System.nanoTime();

	     if (flag && now - last < mouseDoubleClickPeriodNS)
	     {
	         lastMouseNS = 0;
	         return true;
	     }
	     return false;
	 }

	 public Vector3 MousePosition()
	 {
		 xBuffer = BufferUtils.createDoubleBuffer(1);
		 yBuffer = BufferUtils.createDoubleBuffer(1);
		 GLFW.glfwGetCursorPos(window, xBuffer, yBuffer);
		 double x = xBuffer.get(0);
		 double y = yBuffer.get(0);
		 xBuffer = null;
		 yBuffer = null;
		 
		 return new Vector3((float)x,(float)y,0);
	 }
}
