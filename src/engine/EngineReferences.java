package engine;

import components.GameObject;
import components.Light;
import graphics.Color;
import graphics.Shader;
import math.Vector3;

public class EngineReferences 
{
	/*Window properties start*/
	public static int WindowWidth = 1400;
	public static int WindowHeight = 800;
	public static String WindowTitle = "Earth Engine";
	
	/*Window properties end*/
	
	/*Project properties start*/
	public static String enginePath = System.getProperty("user.dir");
	
	/*Project properties end*/
	
	/*Engine related properties start*/
	public static Scene activeScene;
	public static GameObject mainCamera;
	public static Color clearColor = Color.black;
	public static Shader shader;
	/*Engine related properties end*/
	
	
	/*Camera related properties start*/
	public static float cameraSensitivity = 10f;
	public static float cameraMoveSpeed = 0.1f;
	/*Camera related properties end*/
	
	/*Light related properties start*/
	public static boolean lightExists = false;
	public static float ambientLightIntensity = 0.05f;
	public static Vector3 ambientLight = new Vector3(ambientLightIntensity,ambientLightIntensity,ambientLightIntensity);
	/*Light related properties end*/
	
}
