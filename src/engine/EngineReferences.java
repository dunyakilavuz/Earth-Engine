package engine;

import components.GameObject;

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
	/*Engine related properties end*/
}
