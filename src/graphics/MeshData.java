package graphics;

import java.util.List;

import math.Vector2;
import math.Vector3;

public class MeshData 
{
	float[] vertices;
	float[] textures;
	float[] normals;
	
	public MeshData(List<Vector3> vertices, List<Vector2> textures, List<Vector3> normals, List<Vector3> faces)
	{
		this.vertices = vertexDataList3DToArray(vertices);

		
	}
	
	
	public static float[] vertexDataList3DToArray(List<Vector3> dataList)
	{
		float[] dataArray = new float[dataList.size() * 3];
		
		int j = 0;
		for(int i = 0; (i * 3 + 1) < dataArray.length; i++)
		{
			dataArray[i * 3] = dataList.get(j).x;
			dataArray[i * 3 + 1] = dataList.get(j).y;
			dataArray[i * 3 + 1] = dataList.get(j).z;
			j++;
		}
		
		return dataArray;
	}
	
	public static float[] vertexDataList2DToArray(List<Vector2> dataList)
	{
		float[] dataArray = new float[dataList.size() * 2];
		
		int j = 0;
		for(int i = 0; (i * 2 + 1) < dataArray.length; i++)
		{
			dataArray[i * 2] = dataList.get(j).x;
			dataArray[i * 2 + 1] = dataList.get(j).y;
			j++;
		}
		return dataArray;
	}
}
