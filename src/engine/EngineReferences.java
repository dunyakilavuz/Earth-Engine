package engine;

import components.GameObject;
import components.Light;
import graphics.Color;
import math.Vector3;

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
	public static Color clearColor = Color.black;
	/*Engine related properties end*/
	
	
	/*Camera related properties start*/
	public static float cameraSensitivity = 10f;
	public static float cameraMoveSpeed = 0.1f;
	/*Camera related properties end*/
	
	/*Light related properties start*/
	public static boolean lightExists = false;
	public static Vector3 ambientLight = new Vector3(0.3f,0.3f,0.3f);
	/*Light related properties end*/
	
}
