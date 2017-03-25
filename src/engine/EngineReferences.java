package engine;

import components.GameObject;
import graphics.Color;

public class EngineReferences 
{
	/*Window properties start*/
	public static int WindowWidth;
	public static int WindowHeight;
	public static String WindowTitle;
	
	/*Window properties end*/
	
	/*Project properties start*/
	public static String enginePath = System.getProperty("user.dir");
	
	/*Project properties end*/
	
	/*Engine related properties start*/
	public static Scene activeScene;
	public static GameObject mainCamera;
	public static Color clearColor = Color.grey;
	/*Engine related properties end*/
	
	
	/*Camera related properties start*/
	public static float cameraSensitivity = 10f;
	public static float cameraMoveSpeed = 0.1f;
	/*Camera related properties end*/
}
