package graphics;

import java.util.ArrayList;
import java.util.List;

import math.Vector2;
import math.Vector3;

public class MeshData 
{
	float[] vertices;
	float[] textures;
	float[] normals;
	int[] indices;
	
	
	public MeshData(List<Vector3> vertices, List<Vector2> textures, List<Vector3> normals, List<Face> faces)
	{
		List<Integer> indices = new ArrayList<Integer>();
		this.vertices = new float[vertices.size() * 3];
		this.textures = new float[vertices.size() * 2];
		this.normals = new float[vertices.size() * 3];
		
	    int i = 0;
	    for (Vector3 pos : vertices) 
	    {
	        this.vertices[i * 3] = pos.x;
	        this.vertices[i * 3 + 1] = pos.y;
	        this.vertices[i * 3 + 2] = pos.z;
	        i++;
	    }

		
		for(Face face : faces)
		{
			IndexGroup[] faceVertexIndices = face.getFaceVertexIndices();
	        for (IndexGroup indexValue : faceVertexIndices)
	        {
	        	int posIndex = indexValue.indexPos;
	        	indices.add(posIndex);
	        	
			    if (indexValue.indexTextCoord >= 0) 
			    {
			        Vector2 textCoord = textures.get(indexValue.indexTextCoord);
			        this.textures[posIndex * 2] = textCoord.x;
			        this.textures[posIndex * 2 + 1] = 1 - textCoord.y;
			    }
			    if (indexValue.indexVecNormal >= 0) 
			    {
			        // Reorder vector normals
			        Vector3 vecNorm = normals.get(indexValue.indexVecNormal);
			        this.normals[posIndex * 3] = vecNorm.x;
			        this.normals[posIndex * 3 + 1] = vecNorm.y;
			        this.normals[posIndex * 3 + 2] = vecNorm.z;
			    }
	        }
		}
		
		i = 0;
		this.indices = new int[indices.size()];
		for(Integer index : indices)
		{
			this.indices[i] = index;
			i++;
		}
	}
	
	public static class Face
	{
		IndexGroup[] indexGroups;
	    public Face(String v1, String v2, String v3) 
	    {
	        indexGroups = new IndexGroup[3];
	        // Parse the lines
	        indexGroups[0] = parseLine(v1);
	        indexGroups[1] = parseLine(v2);
	        indexGroups[2] = parseLine(v3);
	    }
	    private IndexGroup parseLine(String line) throws NumberFormatException 
	    {
	    	IndexGroup indexGroup = new IndexGroup();
	        String[] lineTokens = line.split("/");
	        int length = lineTokens.length;
	        indexGroup.indexPos = Integer.parseInt(lineTokens[0]) - 1;
	        if (length > 1) 
	        {
	            // It can be empty if the obj does not define text coords
	            String textCoord = lineTokens[1];
	            
	            if(textCoord.length() > 0)
	            {
	            	indexGroup.indexTextCoord = Integer.parseInt(textCoord) - 1;
	            }
	            else
	            {
	            	indexGroup.indexTextCoord = IndexGroup.NO_VALUE;
	            }
	            
	            if (length > 2) 
	            {
	                indexGroup.indexVecNormal = Integer.parseInt(lineTokens[2]) - 1;
	            }
	        }
	        return indexGroup;
	    }
	    
	    public IndexGroup[] getFaceVertexIndices()
	    {
	    	return indexGroups;
	    }
	    
	}
	
	protected static class IndexGroup 
	{
	    public static final int NO_VALUE = -1;
	    public int indexPos;
	    public int indexTextCoord;
	    public int indexVecNormal;

	    public IndexGroup() 
	    {
	        indexPos = NO_VALUE;
	        indexTextCoord = NO_VALUE;
	        indexVecNormal = NO_VALUE;
	    }
	}

	public float[] getVertices() 
	{
		return vertices;
	}

	public float[] getTextures() 
	{
		return textures;
	}

	public float[] getNormals() 
	{
		return normals;
	}

	public int[] getIndices() 
	{
		return indices;
	}
	
}
