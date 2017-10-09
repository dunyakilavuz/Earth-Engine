package engine;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import components.Camera;
import components.GameObject;
import components.MeshRenderer;
import graphics.Color;
import graphics.Shader;
import math.Matrix4x4;
import math.Vector3;
import math.Vector4;

public class Scene 
{
	public List<GameObject> gameObjectList = new ArrayList<GameObject>();
	
	GameObject mainCamera = EngineReferences.mainCamera;
	public Shader shader;
	GameObject origin;
	float specularPower = 10;
	
	
	public Scene() 
	{
		origin = new GameObject();
	}
	
	void Awake() throws Exception
	{
		shader = new Shader();
		shader.createVertexShader(Loader.ImportCode(EngineReferences.enginePath + "/res/shaders/vertex.vs"));
		shader.createFragmentShader(Loader.ImportCode(EngineReferences.enginePath + "/res/shaders/fragment.fs"));
		shader.Link();
		shader.createUniform("projectionMatrix");
		shader.createUniform("modelViewMatrix");
		shader.createUniform("texture_sampler");
		shader.createUniform("emptyScene");
		shader.createUniform("lightExists");
		
		shader.createUniform("specularPower");
		shader.createUniform("ambientLight");
		shader.createMaterialUniform("material");
		shader.createPointLightUniform("pointLight");
		

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
		
		shader.setUniform("ambientLight", EngineReferences.ambientLight);
        shader.setUniform("specularPower", specularPower);
		
        shader.setUniform("emptyScene", 1);
        drawGrid();

		if(gameObjectList.isEmpty() == false)
		{
			shader.setUniform("emptyScene", 0);
			
			if(EngineReferences.lightExists == true)
			{
				shader.setUniform("lightExists", 1);
			}
			else
			{
				shader.setUniform("lightExists", 0);
			}

			for(int i = 0; i < gameObjectList.size(); i++)
			{				
				if(gameObjectList.get(i).GetComponent(MeshRenderer.class) != null && gameObjectList.get(i).GetComponent(MeshRenderer.class).material.isTextured() == true)
				{
					shader.setUniform("material", gameObjectList.get(i).GetComponent(MeshRenderer.class).material);					
					shader.setUniform("texture_sampler", i);
					GL13.glActiveTexture(GL13.GL_TEXTURE0 + i);
					GL11.glBindTexture(GL11.GL_TEXTURE_2D, gameObjectList.get(i).GetComponent(MeshRenderer.class).material.getTexture().textureVboID);
				}		
				
				shader.setUniform("modelViewMatrix", Matrix4x4.multiplicationMatrix4x4(Matrix4x4.Inverse(mainCamera.GetComponent(Camera.class).viewMatrix), gameObjectList.get(i).transform.worldMatrix));
				gameObjectList.get(i).Update();		
			}
		}

		shader.setUniform("modelViewMatrix", Matrix4x4.multiplicationMatrix4x4(Matrix4x4.Inverse(mainCamera.GetComponent(Camera.class).viewMatrix), origin.transform.worldMatrix));
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
