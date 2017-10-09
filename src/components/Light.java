package components;

import engine.EngineReferences;
import graphics.Color;

public class Light extends Component
{
	public GameObject gameObject;
	public Color color;
	public float intensity;
	public LightType lightType;
	public Attenuation attenuation;
	public float radius;
	
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

	public void Start()
	{
		EngineReferences.activeScene.shader.setUniform("pointLight", this);
	}
	
	public void Update()
	{
		EngineReferences.activeScene.shader.setUniform("pointLight", this);
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
}