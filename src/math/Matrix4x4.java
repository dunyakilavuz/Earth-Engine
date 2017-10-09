package math;

import java.util.Random;

import components.Camera;

public class Matrix4x4 
{
	float[][] matrix = new float[4][4];
	
	public Matrix4x4() 
	{
		
	}
	
	public static Matrix4x4 multiplicationMatrix4x4(Matrix4x4 A, Matrix4x4 B)
	{
		Matrix4x4 newMatrix = new Matrix4x4();
		
		for (int i = 0; i < 4; i++) 
		{ 
		    for (int j = 0; j < 4; j++)
		    { 
		        for (int k = 0; k < 4; k++) 
		        { 
		            newMatrix.matrix[i][j] += A.matrix[i][k] * B.matrix[k][j];
		        }
		    }
		}
		return newMatrix;
	}
	
	public static Matrix4x4 projectionMatrix(Camera camera)
	{
		Matrix4x4 newMatrix = new Matrix4x4();
		
		newMatrix.Vector4ToRow(0, new Vector4((float) (1.0f /(camera.aspectRatio * Math.tan(camera.fieldOfview/2))),0,0,0));
		newMatrix.Vector4ToRow(1, new Vector4(0,(float) (1 / Math.tan(camera.fieldOfview/2)),0,0));
		newMatrix.Vector4ToRow(2, new Vector4(0,0, 
				-(camera.farClipPlane + camera.nearClipPlane) / (camera.farClipPlane - camera.nearClipPlane), 
				-(2 * camera.farClipPlane * camera.nearClipPlane) / (camera.farClipPlane - camera.nearClipPlane)));
		newMatrix.Vector4ToRow(3, new Vector4(0,0,-1,0));
		
		return newMatrix;
	}
	
	public static Matrix4x4 viewMatrix(Camera camera)
	{
		Matrix4x4 newMatrix;
		newMatrix = Matrix4x4.multiplicationMatrix4x4(Matrix4x4.translationMatrix(camera.gameObject.transform.position),Matrix4x4.rotationMatrix(camera.gameObject.transform.rotation));
		return newMatrix;
	}
	
	public static Matrix4x4 identityMatrix()
	{
		Matrix4x4 newMatrix = new Matrix4x4();
		
		newMatrix.Vector4ToRow(0, new Vector4(1,0,0,0));
		newMatrix.Vector4ToRow(1, new Vector4(0,1,0,0));
		newMatrix.Vector4ToRow(2, new Vector4(0,0,1,0));
		newMatrix.Vector4ToRow(3, new Vector4(0,0,0,1));
		
		return newMatrix;
	}
	
	public static Matrix4x4 translationMatrix(Vector3 value)
	{
		Matrix4x4 newMatrix = new Matrix4x4();
		
		newMatrix.Vector4ToRow(0, new Vector4(1,0,0,value.x));
		newMatrix.Vector4ToRow(1, new Vector4(0,1,0,value.y));
		newMatrix.Vector4ToRow(2, new Vector4(0,0,1,value.z));
		newMatrix.Vector4ToRow(3, new Vector4(0,0,0,1));
		
		return newMatrix;
	}
	
	public static Matrix4x4 scaleMatrix(Vector3 value)
	{
		Matrix4x4 newMatrix = new Matrix4x4();
		
		newMatrix.Vector4ToRow(0, new Vector4(value.x,0,0,0));
		newMatrix.Vector4ToRow(1, new Vector4(0,value.y,0,0));
		newMatrix.Vector4ToRow(2, new Vector4(0,0,value.z,0));
		newMatrix.Vector4ToRow(3, new Vector4(0,0,0,1));
		
		return newMatrix;
	}
	
	public static Matrix4x4 rotationMatrix(Quaternion q)
	{
		return QuaternionMatrix(q);
	}
	
	public static Matrix4x4 QuaternionMatrix(Quaternion q)
	{
		Matrix4x4 newMatrix = new Matrix4x4();
		Matrix4x4 matrix1 = new Matrix4x4();
		Matrix4x4 matrix2 = new Matrix4x4();
		
		matrix1.Vector4ToRow(0, new Vector4(q.w,q.z,-q.y,q.x));
		matrix1.Vector4ToRow(1, new Vector4(-q.z,q.w,q.x,q.y));
		matrix1.Vector4ToRow(2, new Vector4(q.y,-q.x,q.w,q.z));
		matrix1.Vector4ToRow(3, new Vector4(-q.x,-q.y,-q.z,q.w));
		
		matrix2.Vector4ToRow(0, new Vector4(q.w,q.z,-q.y,-q.x));
		matrix2.Vector4ToRow(1, new Vector4(-q.z,q.w,q.x,-q.y));
		matrix2.Vector4ToRow(2, new Vector4(q.y,-q.x,q.w,-q.z));
		matrix2.Vector4ToRow(3, new Vector4(q.x,q.y,q.z,q.w));
		
		newMatrix = Matrix4x4.multiplicationMatrix4x4(matrix1, matrix2);
		
		return newMatrix;
	}
	
	public static Matrix4x4 Inverse(Matrix4x4 matrix)
	{
		return deserializeMatrix4x4(invert(serializeMatrix4x4(matrix)));
	}
	
	
	public void Vector4ToRow(int row, Vector4 vector)
	{
		matrix[row][0] = vector.x;
		matrix[row][1] = vector.y;
		matrix[row][2] = vector.z;
		matrix[row][3] = vector.w;
	}
	
	public void Vector4ToColumn(int column, Vector4 vector)
	{
		matrix[0][column] = vector.x;
		matrix[1][column] = vector.y;
		matrix[2][column] = vector.z;
		matrix[3][column] = vector.w;
	}
	
	public Vector4 RowToVector4(int row)
	{
		return new Vector4(matrix[row][0],matrix[row][1],matrix[row][2],matrix[row][3]);
	}
	
	public Vector4 ColumnToVector4(int column)
	{
		return new Vector4(matrix[0][column],matrix[1][column],matrix[2][column],matrix[3][column]);
	}
	
	public static float[] serializeMatrix4x4(Matrix4x4 matrix)
	{
		float[] serializedMatrix = new float[16];
		
		int counter = 0;
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				serializedMatrix[counter] = matrix.matrix[j][i];
				counter++;
			}
		}
		return serializedMatrix;
	}
	
	private static Matrix4x4 deserializeMatrix4x4(float[] serialized)
	{
		Matrix4x4 deserializedMatrix = new Matrix4x4();
		
		int counter = 0;
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				deserializedMatrix.matrix[j][i] = serialized[counter]; 
				counter++;
			}
		}
		return deserializedMatrix;
	}
	
	public void printMatrix()
	{
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				System.out.print("[" + matrix[i][j] + "]");
			}
			System.out.println("");
		}
	}

	  private static float[] invert(float[] matrix)
	  {
	    double[] tmp = new double[12];
	    double[] src = new double[16];
	    double[] dst = new double[16];  

	    // Transpose matrix
	    for (int i = 0; i < 4; i++) {
	      src[i +  0] = matrix[i*4 + 0];
	      src[i +  4] = matrix[i*4 + 1];
	      src[i +  8] = matrix[i*4 + 2];
	      src[i + 12] = matrix[i*4 + 3];
	    }

	    // Calculate pairs for first 8 elements (cofactors) 
	    tmp[0] = src[10] * src[15];
	    tmp[1] = src[11] * src[14];
	    tmp[2] = src[9]  * src[15];
	    tmp[3] = src[11] * src[13];
	    tmp[4] = src[9]  * src[14];
	    tmp[5] = src[10] * src[13];
	    tmp[6] = src[8]  * src[15];
	    tmp[7] = src[11] * src[12];
	    tmp[8] = src[8]  * src[14];
	    tmp[9] = src[10] * src[12];
	    tmp[10] = src[8] * src[13];
	    tmp[11] = src[9] * src[12];
	    
	    // Calculate first 8 elements (cofactors)
	    dst[0]  = tmp[0]*src[5] + tmp[3]*src[6] + tmp[4]*src[7];
	    dst[0] -= tmp[1]*src[5] + tmp[2]*src[6] + tmp[5]*src[7];
	    dst[1]  = tmp[1]*src[4] + tmp[6]*src[6] + tmp[9]*src[7];
	    dst[1] -= tmp[0]*src[4] + tmp[7]*src[6] + tmp[8]*src[7];
	    dst[2]  = tmp[2]*src[4] + tmp[7]*src[5] + tmp[10]*src[7];
	    dst[2] -= tmp[3]*src[4] + tmp[6]*src[5] + tmp[11]*src[7];
	    dst[3]  = tmp[5]*src[4] + tmp[8]*src[5] + tmp[11]*src[6];
	    dst[3] -= tmp[4]*src[4] + tmp[9]*src[5] + tmp[10]*src[6];
	    dst[4]  = tmp[1]*src[1] + tmp[2]*src[2] + tmp[5]*src[3];
	    dst[4] -= tmp[0]*src[1] + tmp[3]*src[2] + tmp[4]*src[3];
	    dst[5]  = tmp[0]*src[0] + tmp[7]*src[2] + tmp[8]*src[3];
	    dst[5] -= tmp[1]*src[0] + tmp[6]*src[2] + tmp[9]*src[3];
	    dst[6]  = tmp[3]*src[0] + tmp[6]*src[1] + tmp[11]*src[3];
	    dst[6] -= tmp[2]*src[0] + tmp[7]*src[1] + tmp[10]*src[3];
	    dst[7]  = tmp[4]*src[0] + tmp[9]*src[1] + tmp[10]*src[2];
	    dst[7] -= tmp[5]*src[0] + tmp[8]*src[1] + tmp[11]*src[2];
	    
	    // Calculate pairs for second 8 elements (cofactors)
	    tmp[0]  = src[2]*src[7];
	    tmp[1]  = src[3]*src[6];
	    tmp[2]  = src[1]*src[7];
	    tmp[3]  = src[3]*src[5];
	    tmp[4]  = src[1]*src[6];
	    tmp[5]  = src[2]*src[5];
	    tmp[6]  = src[0]*src[7];
	    tmp[7]  = src[3]*src[4];
	    tmp[8]  = src[0]*src[6];
	    tmp[9]  = src[2]*src[4];
	    tmp[10] = src[0]*src[5];
	    tmp[11] = src[1]*src[4];

	    // Calculate second 8 elements (cofactors)
	    dst[8]   = tmp[0] * src[13]  + tmp[3] * src[14]  + tmp[4] * src[15];
	    dst[8]  -= tmp[1] * src[13]  + tmp[2] * src[14]  + tmp[5] * src[15];
	    dst[9]   = tmp[1] * src[12]  + tmp[6] * src[14]  + tmp[9] * src[15];
	    dst[9]  -= tmp[0] * src[12]  + tmp[7] * src[14]  + tmp[8] * src[15];
	    dst[10]  = tmp[2] * src[12]  + tmp[7] * src[13]  + tmp[10]* src[15];
	    dst[10] -= tmp[3] * src[12]  + tmp[6] * src[13]  + tmp[11]* src[15];
	    dst[11]  = tmp[5] * src[12]  + tmp[8] * src[13]  + tmp[11]* src[14];
	    dst[11] -= tmp[4] * src[12]  + tmp[9] * src[13]  + tmp[10]* src[14];
	    dst[12]  = tmp[2] * src[10]  + tmp[5] * src[11]  + tmp[1] * src[9];
	    dst[12] -= tmp[4] * src[11]  + tmp[0] * src[9]   + tmp[3] * src[10];
	    dst[13]  = tmp[8] * src[11]  + tmp[0] * src[8]   + tmp[7] * src[10];
	    dst[13] -= tmp[6] * src[10]  + tmp[9] * src[11]  + tmp[1] * src[8];
	    dst[14]  = tmp[6] * src[9]   + tmp[11]* src[11]  + tmp[3] * src[8];
	    dst[14] -= tmp[10]* src[11 ] + tmp[2] * src[8]   + tmp[7] * src[9];
	    dst[15]  = tmp[10]* src[10]  + tmp[4] * src[8]   + tmp[9] * src[9];
	    dst[15] -= tmp[8] * src[9]   + tmp[11]* src[10]  + tmp[5] * src[8];

	    // Calculate determinant
	    double det = src[0]*dst[0] + src[1]*dst[1] + src[2]*dst[2] + src[3]*dst[3];
	    
	    // Calculate matrix inverse
	    float[] inverse = new float[16];
	    det = 1.0 / det;
	    for (int i = 0; i < 16; i++)
	      inverse[i] = (float) (dst[i] * det);
	    
	    return inverse;
	  }
	
	public void populateMatrix(float min, float max)
	{
		Random random = new Random();
		
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				float n = max - min + 1;
				float m = random.nextFloat() % n;
				float randomNum =  (min + m) * 10;
				matrix[i][j] = randomNum;
			}
		}
	}
}
