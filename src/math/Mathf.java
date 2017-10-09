package math;

public class Mathf 
{
	public static float Lerp(float start, float end, float factor)
	{
		return (1.0f - factor) * start + factor * end;
	}
	
	public static void PerlinNoise()
	{
		
	}
	
	public static float Clamp(float min, float max,float value) {
	    return Math.max(min, Math.min(max, value));
	}
}
