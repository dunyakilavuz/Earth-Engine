package components;

import math.Matrix4x4;
import math.Quaternion;
import math.Vector3;

public class Transform extends Component
{
	public Vector3 position = new Vector3(0,0,0);
	public Vector3 scale = new Vector3(1,1,1);
	public Quaternion rotation = Quaternion.identity();
	public Matrix4x4 worldMatrix = Matrix4x4.identityMatrix();

	public Transform() 
	{
		worldMatrix = Matrix4x4.multiplicationMatrix4x4(
				Matrix4x4.translationMatrix(position), 
				Matrix4x4.multiplicationMatrix4x4(
						Matrix4x4.scaleMatrix(scale), 
						Matrix4x4.rotationMatrix(rotation)));
	}
	
	public void Start() 
	{
		
	}
	
	public void Update()
	{
		worldMatrix = Matrix4x4.multiplicationMatrix4x4(
				Matrix4x4.translationMatrix(position), 
				Matrix4x4.multiplicationMatrix4x4(
						Matrix4x4.scaleMatrix(scale), 
						Matrix4x4.rotationMatrix(rotation)));
	}
	
	public void RotateAround(Vector3 point, Vector3 axis, float angle)
	{
		Vector3 direction = Vector3.Normalize(Vector3.Subtraction(position, point));
		float distance = Vector3.Magnitude(Vector3.Subtraction(position, point));
		
		direction = Vector3.MultiplyByQuaternion(Quaternion.AngleAxis(angle, Vector3.Negate(axis)),direction);

		position = Vector3.Scale(direction, distance);
	}

	public Vector3 right()
	{
		return Vector3.Normalize(new Vector3(worldMatrix.ColumnToVector4(0).x,worldMatrix.ColumnToVector4(0).y,worldMatrix.ColumnToVector4(0).z));
	}
	
	public Vector3 left()
	{
		return Vector3.Normalize(Vector3.Negate(new Vector3(worldMatrix.ColumnToVector4(0).x,worldMatrix.ColumnToVector4(0).y,worldMatrix.ColumnToVector4(0).z)));
	}
	
	public Vector3 up()
	{
		return Vector3.Normalize(new Vector3(worldMatrix.ColumnToVector4(1).x,worldMatrix.ColumnToVector4(1).y,worldMatrix.ColumnToVector4(1).z));	
	}
	
	public Vector3 down()
	{
		return Vector3.Normalize(Vector3.Negate(new Vector3(worldMatrix.ColumnToVector4(1).x,worldMatrix.ColumnToVector4(1).y,worldMatrix.ColumnToVector4(1).z)));
	}
	
	public Vector3 forward()
	{
		return Vector3.Normalize(new Vector3(worldMatrix.ColumnToVector4(2).x,worldMatrix.ColumnToVector4(2).y,worldMatrix.ColumnToVector4(2).z));	
	}
	
	public Vector3 back()
	{
		return Vector3.Normalize(Vector3.Negate(new Vector3(worldMatrix.ColumnToVector4(2).x,worldMatrix.ColumnToVector4(2).y,worldMatrix.ColumnToVector4(2).z)));
	}
}
