package engine;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import components.Camera;
import components.GameObject;
import components.MeshRenderer;
import graphics.Shader;
import math.Matrix4x4;
import math.Vector3;
import math.Vector4;

public class Scene 
{
	public List<GameObject> gameObjectList = new ArrayList<GameObject>();
	
	GameObject mainCamera;
	Shader shader;
	GameObject origin;
	
	String projectPath = System.getProperty("user.dir");
	
	public Scene(int windowWidth, int windowHeight, GameObject mainCamera) 
	{
		origin = new GameObject();
		this.mainCamera = mainCamera;
	}
	
	void Awake() throws Exception
	{
		shader = new Shader();
		shader.createVertexShader(Loader.ImportCode(projectPath + "/res/shaders/vertex.vs"));
		shader.createFragmentShader(Loader.ImportCode(projectPath + "/res/shaders/fragment.fs"));
		shader.Link();
		shader.createUniform("projectionMatrix");
		shader.createUniform("modelViewMatrix");
		shader.createUniform("texture_sampler");
		shader.createUniform("colour");
		shader.createUniform("useColour");
		
		for(GameObject object : gameObjectList)
		{
			object.Awake();
		}
		mainCamera.transform.position = new Vector3(0,3,10);
		mainCamera.Update();
	}
	
	void Start() throws Exception
	{
		for(GameObject object : gameObjectList)
		{
			object.Start();
		}
	}

	void Update()
	{
		shader.Bind();
		shader.setUniform("projectionMatrix", mainCamera.GetComponent(Camera.class).projectionMatrix);
		
		drawGrid();

		for(int i = 0; i < gameObjectList.size(); i++)
		{
			if(gameObjectList.get(i).GetComponent(MeshRenderer.class).material.texture != null)
			{
				shader.setUniform("texture_sampler", i);
				GL13.glActiveTexture(GL13.GL_TEXTURE0 + i);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, gameObjectList.get(i).GetComponent(MeshRenderer.class).material.texture.textureVboID);
			}		
			
			shader.setUniform("modelViewMatrix", Matrix4x4.multiplicationMatrix4x4(Matrix4x4.Inverse(mainCamera.GetComponent(Camera.class).viewMatrix), gameObjectList.get(i).transform.worldMatrix));
			shader.setUniform("colour", gameObjectList.get(i).GetComponent(MeshRenderer.class).material.color);
			
			if(gameObjectList.get(i).GetComponent(MeshRenderer.class).material.isTextured() == true)
			{
				shader.setUniform("useColour", 0);				
			}
			else
			{
				shader.setUniform("useColour", 1);				
			}
			gameObjectList.get(i).Update();
		}
		
		shader.setUniform("modelViewMatrix", Matrix4x4.multiplicationMatrix4x4(Matrix4x4.Inverse(mainCamera.GetComponent(Camera.class).viewMatrix), origin.transform.worldMatrix));
		if(gameObjectList.isEmpty())
		{
			shader.setUniform("colour", new Vector4(1,1,1,1));
			shader.setUniform("useColour", 1);
		}
		mainCamera.GetComponent(Camera.class).controlCamera();			
		shader.Unbind();			
	}
	
	void drawGrid()
	{
		GL11.glBegin(GL11.GL_LINES);
		for(int i = -10; i < 11; i++)
		{			
			GL11.glVertex3f(-10, 0, i);
			GL11.glVertex3f(10, 0, i);
			
			GL11.glVertex3f(i, 0, -10);
			GL11.glVertex3f(i, 0, 10);
		}
		GL11.glEnd();
	}
}
