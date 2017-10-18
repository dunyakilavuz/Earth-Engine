package math;

public class Vector3
{
	public float x;
	public float y;
	public float z;
	
	public static Vector3 up = new Vector3(0,1,0);
	public static Vector3 down = new Vector3(0,-1,0);
	public static Vector3 right = new Vector3(1,0,0);
	public static Vector3 left = new Vector3(-1,0,0);
	public static Vector3 forward = new Vector3(0,0,1);
	public static Vector3 back = new Vector3(0,0,-1);
	public static Vector3 zero = new Vector3(0,0,0);
	public static Vector3 one = new Vector3(1,1,1);
	
	public Vector3(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3(Vector4 vector)
	{
		this.x = vector.x;
		this.y = vector.y;
		this.z = vector.z;
	}
	
	public static Vector3 Negate(Vector3 vector)
	{
		return new Vector3(-vector.x,-vector.y,-vector.z);
	}
	
	public static Vector3 AsAngles(Vector3 vector)
	{
		return new Vector3((float) Math.toRadians(vector.x),(float) Math.toRadians(vector.y),(float) Math.toRadians(vector.z));
	}
	
	public static Vector3 Normalize(Vector3 vector)
	{
		if(Magnitude(vector) != 0)
		{
			return new Vector3(vector.x /Magnitude(vector),vector.y / Magnitude(vector),vector.z / Magnitude(vector));			
		}
		else
		{
			return new Vector3(0,0,0);
		}
	}
	
	public static Vector3 Scale(Vector3 vector, float scale)
	{
		return new Vector3(vector.x * scale, vector.y * scale, vector.z * scale);
	}
	
	public static Vector3 Addition(Vector3 A, Vector3 B)
	{
		return new Vector3(A.x + B.x, A.y + B.y, A.z + B.z);
	}
	
	public static Vector3 Subtraction(Vector3 A, Vector3 B)
	{
		return new Vector3(A.x - B.x, A.y - B.y, A.z - B.z);	
	}
	
	public static Vector3 Multiplication(Vector3 A, Vector3 B)
	{
		return new Vector3(A.x * B.x, A.y * B.y, A.z * B.z);
	}
	
	 public static Vector3 MultiplyByQuaternion(Quaternion q, Vector3 vector)
	 {
	     float num = q.x * 2f;
	     float num2 = q.y * 2f;
	     float num3 = q.z * 2f;
	     float num4 = q.x * num;
	     float num5 = q.y * num2;
	     float num6 = q.z * num3;
	     float num7 = q.x * num2;
	     float num8 = q.x * num3;
	     float num9 = q.y * num3;
	     float num10 = q.w * num;
	     float num11 = q.w * num2;
	     float num12 = q.w * num3;
	     Vector3 result = new Vector3(
	    		 (1f - (num5 + num6)) * vector.x + (num7 - num12) * vector.y + (num8 + num11) * vector.z,
	    		 (num7 + num12) * vector.x + (1f - (num4 + num6)) * vector.y + (num9 - num10) * vector.z,
	    		 (num8 - num11) * vector.x + (num9 + num10) * vector.y + (1f - (num4 + num5)) * vector.z);
	     
	     return result;
	 }
	
	public static float Magnitude(Vector3 vector)
	{
		return (float) Math.sqrt((vector.x * vector.x) + (vector.y * vector.y) + (vector.z * vector.z)) ;
	}
	
	public static void PrintElements(Vector3 vector)
	{
		System.out.println("(" + vector.x + "," + vector.y + "," + vector.z + ")");
	}
	
	public static Vector3 Cross(Vector3 lhs, Vector3 rhs)
	{
		return new Vector3(lhs.y * rhs.z - lhs.z * rhs.y,lhs.z * rhs.x - lhs.x * rhs.z,lhs.x * rhs.y - lhs.y * rhs.x);
	}
	
	public String toString()
	{
		return "(" + x + "," + y + "," + z + ")";
	}

}
