#version 330

in vec2 outTexCoord;
in vec3 mvVertexNormal;
in vec3 mvVertexPos;

out vec4 fragColor;

struct Attenuation
{
	float constant;
	float linear;
	float exponent;
};

struct PointLight
{
	vec4 colour;
	vec3 position;
	float intensity;
	Attenuation att;
};

struct Material
{
	vec4 ambient;
	vec4 diffuse;
	vec4 specular;
	int hasTexture;
	float reflectance;
};

uniform sampler2D texture_sampler;
uniform vec3 ambientLight;
uniform float specularPower;
uniform Material material;
uniform PointLight pointLight;

uniform int emptyScene;
uniform int lightExists;

vec4 ambientC;
vec4 diffuseC;
vec4 specularC;

void setupColours(Material material, vec2 textCoord)
{
	if(material.hasTexture == 1)
	{
		ambientC = texture(texture_sampler, textCoord);
		diffuseC = ambientC;
		specularC = ambientC;
	}
	else
	{
		ambientC = material.ambient;
		diffuseC = material.diffuse;
		specularC = material.specular;
	}
}

vec4 calcPointLight(PointLight light, vec3 position, vec3 normal)
{
	vec4 diffuseColour = vec4(0,0,0,0);
	vec4 specColour = vec4(0,0,0,0);
	
	//Diffuse Light
	vec3 light_direction = light.position - position;
	vec3 to_light_source = normalize(light_direction);
	float diffuseFactor = max(dot(normal, to_light_source), 0.0);
	diffuseColour = diffuseC * light.colour * light.intensity * diffuseFactor;
	
	//Specular Light
	vec3 camera_direction = normalize(-position);
	vec3 from_light_source = -to_light_source;
	vec3 reflected_light = normalize(reflect(from_light_source, normal));
	float specularFactor = max(dot(camera_direction, reflected_light),0.0);
	specularFactor = pow(specularFactor, specularPower);
	specColour = specularC * specularFactor * material.reflectance * light.colour;
	
	//Attenuation
	float distance = length(light_direction);
	float attenuationInv = light.att.constant + light.att.linear * distance + light.att.exponent * distance * distance;
	return (diffuseColour + specColour) / attenuationInv;
	
}

void main()
{
	if(emptyScene == 1)
	{
		fragColor = vec4(0.5f,0.5f,0.5f,0.5f);
	}
	else if(lightExists == 0) 
	{
		fragColor = texture(texture_sampler, outTexCoord);
	}
	else
	{
		setupColours(material, outTexCoord);
		vec4 diffuseSpecularComp = calcPointLight(pointLight, mvVertexPos, mvVertexNormal);
		fragColor = ambientC * vec4(ambientLight, 1) + diffuseSpecularComp;
		
	}
}