package math;

public class Quaternion 
{
	float w;
	float x;
	float y;
	float z;

	public Quaternion(float w, float x,float y, float z) 
	{
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void normalized()
	{
		float lenght = (float) Math.sqrt(w*w + x*x + y*y + z*z);
		w /= lenght;
		x /= lenght;
		y /= lenght;
		z /= lenght;
	}
	
	public static Quaternion identity()
	{
		return new Quaternion(1,0,0,0);
	}
	
	public static Quaternion AngleAxis(float angle, Vector3 axis)
	{		
		angle = (float) Math.toRadians(angle);
		float w = (float) Math.cos(angle / 2);
		float x = (float) (axis.x * Math.sin(angle / 2));
		float y = (float) (axis.y * Math.sin(angle / 2));
		float z = (float) (axis.z * Math.sin(angle / 2));
		return new Quaternion(w,x,y,z);
	}
	
	public static Quaternion MultiplicationQuaternion(Quaternion a, Quaternion b)
	{
		Normalize(a);
		Normalize(b);
		final float newX = a.w * b.x + a.x * b.w + a.y * b.z - a.z * b.y;
		final float newY = a.w * b.y + a.y * b.w + a.z * b.x - a.x * b.z;
		final float newZ = a.w * b.z + a.z * b.w + a.x * b.y - a.y * b.x;
		final float newW = a.w * b.w - a.x * b.x - a.y * b.y - a.z * b.z;
		return new Quaternion(newW, newX, newY, newZ);
	}
	
	public static Quaternion Normalize(Quaternion q)
	{
		float lenght;
		Quaternion newQuaternion = q;
		lenght = (float) Math.sqrt(
				newQuaternion.w * newQuaternion.w 
				+ newQuaternion.x * newQuaternion.x 
				+ newQuaternion.y * newQuaternion.y 
				+ newQuaternion.z * newQuaternion.z);
		
		if(lenght == 1)
		{
			return newQuaternion;
		}
		else
		{
			newQuaternion.w /= lenght;
			newQuaternion.x /= lenght;
			newQuaternion.y /= lenght;
			newQuaternion.z /= lenght;
		}
		return newQuaternion;
	}
	
	public static Vector3 QuaternionToEuler(Quaternion q)
	{
		int pole;
		float x;
		float y;
		float z;
		
		if(q.w * q.x * q.y * q.z > 0.499f)
			pole = 1;
		else if(q.w * q.x * q.y * q.z < -0.499)
			pole = -1;
		else
			pole = 0;
		
		if(pole == 0)
		{
			x = (float) Math.toDegrees(Math.atan2(2f * (q.y * q.w + q.x * q.z), 1f - 2f * (q.y * q.y + q.x * q.x)));
			y =  (float) Math.toDegrees(Math.asin(Clamp(2f * (q.w * q.x - q.z * q.y), -1f, 1f)));
			z = (float) Math.toDegrees(Math.atan2(2f * (q.w * q.z + q.y * q.x), 1f - 2f * (q.x * q.x + q.z * q.z)));			
		}
		else
		{
			x = (float) Math.toDegrees(0);
			y = (float) Math.toDegrees((pole * Math.PI * 0.5f));
			z = (float) Math.toDegrees(pole * 2f * Math.atan2(q.y, q.w));
		}
		
		return new Vector3(x,y,z);
	}
	
	public static Quaternion EulerToQuaternion(Vector3 vector)
	{
		float hr = vector.z * 0.5f;
		float shr = (float)Math.sin(hr);
		float chr = (float)Math.cos(hr);
		float hp = vector.y * 0.5f;
		float shp = (float)Math.sin(hp);
		float chp = (float)Math.cos(hp);
		float hy = vector.x * 0.5f;
		float shy = (float)Math.sin(hy);
		float chy = (float)Math.cos(hy);
		float chy_shp = chy * shp;
		float shy_chp = shy * chp;
		float chy_chp = chy * chp;
		float shy_shp = shy * shp;
		
		return new Quaternion((chy_chp * chr) + (shy_shp * shr),(shy_chp * chr) - (chy_shp * shr),(chy_shp * chr) + (shy_chp * shr),(chy_chp * shr) - (shy_shp * chr));
	}
	
	public static Quaternion Slerp(Quaternion start, Quaternion end,float factor)
	{
		float d = start.x * end.x + start.y * end.y + start.z * end.z + start.w * end.w;
		float absDot = d < 0.f ? -d : d;

		float scale0 = 1f - factor;
		float scale1 = factor;

		if ((1 - absDot) > 0.1) 
		{
			float angle = (float)Math.acos(absDot);
			float invSinTheta = 1f / (float)Math.sin(angle);
			scale0 = ((float)Math.sin((1f - factor) * angle) * invSinTheta);
			scale1 = ((float)Math.sin((factor * angle)) * invSinTheta);
		}
		if (d < 0.f) 
			scale1 = -scale1;
		
		Quaternion interpolated = new Quaternion((scale0 * start.w) + (scale1 * end.w), (scale0 * start.x) + (scale1 * end.x), (scale0 * start.y) + (scale1 * end.y), (scale0 * start.z) + (scale1 * end.z));
		return interpolated;
	}
	
	private static float Clamp(float val, float min, float max) {
	    return Math.max(min, Math.min(max, val));
	}
	
}
