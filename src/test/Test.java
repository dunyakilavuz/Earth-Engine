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
import math.Matrix4x4;
import math.Quaternion;
import math.Vector3;
import math.Vector4;

public class Test
{	
	public static void main(String args[]) throws Exception
	{
		GameObject mainCamera = new GameObject();
		mainCamera.AddComponent(new Camera(mainCamera));
		EngineReferences.mainCamera = mainCamera;
		
		Scene scene = new Scene();
		EngineReferences.activeScene = scene;
		Engine engine = new Engine();
		
		MeshData lightMeshData = Loader.ImportOBJ(EngineReferences.enginePath + "/res/model/mesh/Earth.obj");
		Mesh lightMesh = new Mesh(lightMeshData);
		Material lightMaterial = new Material(Color.white, 0);

		MeshData earthMeshData = Loader.ImportOBJ(EngineReferences.enginePath + "/res/model/mesh/Earth.obj");
		Mesh earthMesh = new Mesh(earthMeshData);
		Material earthMaterial = new Material(new Texture(EngineReferences.enginePath + "/res/model/texture/earthTexture.png"),0);
		
		MeshData moonMeshData = Loader.ImportOBJ(EngineReferences.enginePath + "/res/model/mesh/Moon.obj");
		Mesh moonMesh = new Mesh(moonMeshData);
		Material moonMaterial = new Material(new Texture(EngineReferences.enginePath + "/res/model/texture/moonTexture.png"),0);
		
		GameObject light = new GameObject();
		light.name = "PointLight";
		light.transform.position = new Vector3(10,0,0);
		light.transform.scale = new Vector3(0.1f,0.1f,0.1f);
		light.AddComponent(new Light(light,LightType.PointLight,Color.white,1,1));
//		light.AddComponent(new MeshRenderer(lightMesh));
//		light.GetComponent(MeshRenderer.class).material = lightMaterial;
		
		GameObject earth = new GameObject();
		earth.name = "Earth";
		
		earth.AddComponent(new MeshRenderer(earthMesh));
		earth.GetComponent(MeshRenderer.class).material = earthMaterial;
		earth.AddComponent(new RotateAround(earth));
		earth.transform.rotation = Quaternion.AngleAxis(0, Vector3.up);
		
		GameObject moon = new GameObject();
		moon.name = "Moon";
		moon.AddComponent(new MeshRenderer(moonMesh));
		moon.GetComponent(MeshRenderer.class).material = moonMaterial;
		moon.transform.position = new Vector3(3,0,0);
		moon.transform.scale = new Vector3(0.3f,0.3f,0.3f);
		moon.AddComponent(new RotateAround(moon));
		moon.transform.rotation = Quaternion.AngleAxis(30, Vector3.up);

		scene.gameObjectList.add(light);
		scene.gameObjectList.add(earth);
		scene.gameObjectList.add(moon);
		
		engine.StartEngine();
	}
}