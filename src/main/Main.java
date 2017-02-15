// main class should be like this

package main;

import components.Camera;
import components.GameObject;
import components.MeshRenderer;
import components.Rigidbody;
import engine.Engine;
import engine.Loader;
import engine.Scene;
import engine.Time;
import graphics.Material;
import graphics.Mesh;
import graphics.Texture;
import math.Quaternion;
import math.Vector3;

public class Main
{	
	public static void main(String args[]) throws Exception
	{
		int windowWidth = 1400;
		int windowHeight = 800;
		String title = "Earth Engine";
		
		GameObject mainCamera = new GameObject();
		mainCamera.AddComponent(new Camera(windowWidth, windowHeight,mainCamera));
		
		Scene scene = new Scene(windowWidth,windowHeight,mainCamera);
		Engine engine = new Engine(windowWidth,windowHeight,title,scene);
		
		String projectPath = System.getProperty("user.dir");
		
		Mesh earthMesh = Loader.ImportOBJ(projectPath + "/res/model/mesh/Earth.obj");
		
		Material earthMaterial = new Material(new Texture(projectPath + "/res/model/texture/earthTexture.png"));
		
		GameObject earth = new GameObject();
		earth.name = "Earth";
		
		earth.AddComponent(new MeshRenderer(earthMesh));
		earth.GetComponent(MeshRenderer.class).material = earthMaterial;

		scene.gameObjectList.add(earth);
		
		engine.StartEngine();
	}
}