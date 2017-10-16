package components;

import engine.EngineReferences;
import graphics.Color;
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
		calcRelativePosition();
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
			this.constant = constant;
            this.linear = linear;
            this.exponent = exponent;
		}
	}
	
	void calcRelativePosition()
	{
		Vector4 Vec4RelativePosition = Vector4.ToVector4(gameObject.transform.position, 1);
		relativePosition = Vector3.ToVector3(Vector4.MultiplyByMatrix4x4(Vec4RelativePosition, EngineReferences.mainCamera.GetComponent(Camera.class).viewMatrix));
	}
	
	public void SetUniform()
	{
		EngineReferences.activeScene.shader.setUniform("pointLight", this);
	}
}