package components;

import com.sun.org.apache.xml.internal.security.encryption.Reference;

import engine.EngineReferences;
import graphics.Color;
import math.Matrix4x4;
import math.Vector3;
import math.Vector4;

public class Light extends Component
{
	public GameObject gameObject;
	public Color color;
	public float intensity;
	public LightType lightType;
	public Attenuation attenuation;
	public float radius;
	public Vector3 relativePosition;
	
	public Light(GameObject parent, LightType lightType, Color color, float intensity,float radius)
	{
		gameObject= parent;
		this.lightType = lightType;
		this.color = color;
		this.intensity = intensity;
		this.radius = radius;
		attenuation = new Attenuation(1, 2.0f/radius, 1.0f/(radius * radius));
		EngineReferences.lightExists = true;
	}
	
	public Light(GameObject parent, LightType lightType, Color color, float intensity)
	{
		gameObject= parent;
		this.lightType = lightType;
		this.color = color;
		this.intensity = intensity;
		this.radius = 1;
		attenuation = new Attenuation(1, 2.0f/radius, 1.0f/(radius * radius));
		EngineReferences.lightExists = true;
	}
	
	public Light(GameObject parent, LightType lightType, Color color)
	{
		gameObject= parent;
		this.lightType = lightType;
		this.color = color;
		this.intensity = 1;
		this.radius = 1;
		attenuation = new Attenuation(1, 2.0f/radius, 1.0f/(radius * radius));
		EngineReferences.lightExists = true;
	}
	
	public Light(GameObject parent, LightType lightType)
	{
		gameObject= parent;
		this.lightType = lightType;
		this.color = Color.white;
		this.intensity = 1;
		this.radius = 1;
		attenuation = new Attenuation(1, 2.0f/radius, 1.0f/(radius * radius));
		EngineReferences.lightExists = true;
	}

	public void Start()
	{
		
	}
	
	public void Update()
	{
		CalcRelativePosition();
		SetUniform();
	}
	
	public enum LightType
	{
		PointLight,
		SpotLight,
		DirectionalLight
	}
	
	public static class Attenuation
	{
		public float constant;
		public float linear;
		public float exponent;
		
		public Attenuation(float constant, float linear, float exponent)
		{
			this.constant = 1;
            this.linear = 0;
            this.exponent = 0;
		}
	}

	void CalcRelativePosition()
	{
		Matrix4x4 temp = Matrix4x4.Inverse(Matrix4x4.multiplicationMatrix4x4(Matrix4x4.Inverse(EngineReferences.mainCamera.GetComponent(Camera.class).viewMatrix), gameObject.transform.worldMatrix));
		Vector4 aux = new Vector4(gameObject.transform.position,1);
		aux = Vector4.MultiplyByMatrix4x4(aux, temp);
		relativePosition = new Vector3(aux);
	}
	
	public void SetUniform()
	{
		EngineReferences.activeScene.shader.setUniform("pointLight", this);
	}
}