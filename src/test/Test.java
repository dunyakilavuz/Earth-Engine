// main class should be like this

package test;

import components.Camera;
import components.GameObject;
import components.MeshRenderer;
import components.Rigidbody;
import engine.Engine;
import engine.EngineReferences;
import engine.Loader;
import engine.Scene;
import engine.Time;
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
		
		GameObject earth = new GameObject();
		earth.name = "Earth";
		
		earth.AddComponent(new MeshRenderer(earthMesh));
		earth.GetComponent(MeshRenderer.class).material = earthMaterial;

		scene.gameObjectList.add(earth);
		
		engine.StartEngine();
	}
}