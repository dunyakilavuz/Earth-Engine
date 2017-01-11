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
		
		Mesh earthMesh = Loader.ImportOBJ(".\\res\\model\\mesh\\Earth.obj");
		Mesh moonMesh = Loader.ImportOBJ(".\\res\\model\\mesh\\Moon.obj");
		
		Material moonMaterial = new Material(new Texture(".\\res\\model\\texture\\moonTexture.png"));
		Material earthMaterial = new Material(new Texture(".\\res\\model\\texture\\earthTexture.png"));
		
		GameObject earth = new GameObject();
		earth.name = "Earth";
		GameObject moon = new GameObject();
		moon.name = "Moon";
		
		earth.AddComponent(new MeshRenderer(earthMesh));
		earth.GetComponent(MeshRenderer.class).material = earthMaterial;
		
		moon.AddComponent(new MeshRenderer(moonMesh));
		moon.GetComponent(MeshRenderer.class).material = moonMaterial;
		
		moon.AddComponent(new Rigidbody(moon));
		
		earth.AddComponent(new Orbit(earth));
		moon.AddComponent(new Orbit(moon));
		
		scene.gameObjectList.add(earth);
		scene.gameObjectList.add(moon);
		engine.StartEngine();
	}
}