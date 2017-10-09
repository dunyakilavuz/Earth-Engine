package math;

public class Vector2
{
	public float x;
	public float y;
	
	public static Vector2 up = new Vector2(0, 1);
	public static Vector2 down = new Vector2(0, -1);
	public static Vector2 right = new Vector2(1, 0);
	public static Vector2 left = new Vector2(-1, 0);
	
	public Vector2(float x, float y) 
	{
		this.x = x;
		this.y = y;
	}

	
	public Vector2 Negate(Vector2 vector)
	{
		return new Vector2(-vector.x,-vector.y);
	}
	
	public Vector2 AsAngles(Vector2 vector)
	{
		return new Vector2((float) Math.toRadians(vector.x),(float) Math.toRadians(vector.y));
	}
	
	public Vector2 Normalize(Vector2 vector)
	{
		return new Vector2(vector.x /Magnitude(vector),vector.y / Magnitude(vector));
	}
	
	public Vector2 Scale(Vector2 vector, float scale)
	{
		return new Vector2(vector.x * scale, vector.y * scale);
	}
	
	public static Vector2 Addition(Vector2 A, Vector2 B)
	{
		return new Vector2(A.x + B.x, A.y + B.y);
	}
	
	public static Vector2 Subtraction(Vector2 A, Vector2 B)
	{
		return new Vector2(A.x -B.x, A.y - B.y);
	}
	
	public static Vector2 Multiplication(Vector2 A, Vector2 B)
	{
		return new Vector2(A.x * B.x, A.y * B.y);
	}
	
	public static float Magnitude(Vector2 vector)
	{
		return (float) Math.sqrt((vector.x * vector.x) + (vector.y * vector.y)) ;
	}
	
	public static void PrintElements(Vector2 vector)
	{
		System.out.println("(" + vector.x + "," + vector.y + "," + ")");
	}

	public String toString()
	{
		return "(" + x + "," + y + "," + ")";
	}
}
