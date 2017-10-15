// main class should be like this

package test;

import components.Camera;
import components.GameObject;
import components.Light;
import components.Light.LightType;
import components.MeshRenderer;
import components.Rigidbody;
import engine.Engine;
import engine.EngineReferences;
import engine.Loader;
import engine.Scene;
import engine.Time;
import graphics.Color;
import graphics.Material;
import graphics.Mesh;
import graphics.MeshData;
import graphics.Texture;
import math.Quaternion;
import math.Vector3;

public class Test
{	
	public static void main(String args[]) throws Exception
	{
		EngineReferences.WindowWidth = 1400;
		EngineReferences.WindowHeight = 800;
		EngineReferences.WindowTitle = "Earth Engine";
		
		GameObject mainCamera = new GameObject();
		mainCamera.AddComponent(new Camera(mainCamera));
		EngineReferences.mainCamera = mainCamera;
		
		Scene scene = new Scene();
		EngineReferences.activeScene = scene;
		Engine engine = new Engine();

		MeshData earthMeshData = Loader.ImportOBJ(EngineReferences.enginePath + "/res/model/mesh/Earth.obj");
		Mesh earthMesh = new Mesh(earthMeshData);
		Material earthMaterial = new Material(new Texture(EngineReferences.enginePath + "/res/model/texture/earthTexture.png"));
		
		MeshData moonMeshData = Loader.ImportOBJ(EngineReferences.enginePath + "/res/model/mesh/Moon.obj");
		Mesh moonMesh = new Mesh(moonMeshData);
		Material moonMaterial = new Material(new Texture(EngineReferences.enginePath + "/res/model/texture/moonTexture.png"));
		
		GameObject light = new GameObject();
		light.name = "PointLight";
		light.transform.position = new Vector3(0,0,-1);
		light.AddComponent(new Light(light,LightType.PointLight,Color.white,1,1));
		
		
		GameObject earth = new GameObject();
		earth.name = "Earth";
		
		earth.AddComponent(new MeshRenderer(earthMesh));
		earth.GetComponent(MeshRenderer.class).material = earthMaterial;
		earth.AddComponent(new RotateAround(earth));
		
		GameObject moon = new GameObject();
		moon.name = "Moon";
		moon.AddComponent(new MeshRenderer(moonMesh));
		moon.GetComponent(MeshRenderer.class).material = moonMaterial;
		moon.transform.position = new Vector3(5,0,0);
		moon.transform.scale = new Vector3(0.3f,0.3f,0.3f);
		moon.AddComponent(new RotateAround(moon));

		scene.gameObjectList.add(light);
		scene.gameObjectList.add(earth);
		scene.gameObjectList.add(moon);
		
		engine.StartEngine();
	}
}