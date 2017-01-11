package input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class KeyboardHandler extends GLFWKeyCallback
{
	private  final int KEYBOARD_SIZE = 512;
	private int[] keyStates = new int[KEYBOARD_SIZE];
    private boolean[] activeKeys = new boolean[KEYBOARD_SIZE];
    private int NO_STATE = -1;
    
    protected KeyboardHandler()
    {
    	
    }
    
	protected void resetKeyboard()
	{
		for (int i = 0; i < keyStates.length; i++)
		{
			keyStates[i] = NO_STATE;
		}
	}


	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) 
	{
		activeKeys[key] = action != GLFW.GLFW_RELEASE;
        keyStates[key] = action;
	}
	
	public boolean KeyDown(int keycode) 
	{
        return activeKeys[keycode];
    }
	public boolean KeyPressed(int key)
    {
        return keyStates[key] == GLFW.GLFW_PRESS;
    }

    public boolean KeyReleased(int key)
    {
        return keyStates[key] == GLFW.GLFW_RELEASE;
    }

}
