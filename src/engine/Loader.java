package engine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import graphics.Mesh;
import math.Vector2;
import math.Vector3;

public class Loader 
{
	public Loader()
	{
		
	}

	public static String ImportCode(String filePath) throws IOException
	{
		String line = "";
		List<String> lines;
		Path path = Paths.get(filePath);
		
		lines = Files.readAllLines(path);
		
		for (String thisLine : lines) 
		{
			line += thisLine;
			line += "\n";
	    }
		return line;
	}
	
	public static Mesh ImportOBJ(String filePath) throws Exception
	{
		List<Vector3> vertices = new ArrayList<Vector3>();
		List<Vector2> textures = new ArrayList<Vector2>();
		List<Vector3> normals = new ArrayList<Vector3>();
		List<Face> faces = new ArrayList<Face>();
		
		List<String> lines;
		Path path = Paths.get(filePath);
		lines = Files.readAllLines(path);

		for (String line : lines) 
		{
		    String[] tokens = line.split("\\s+");
		    switch (tokens[0]) {
		        case "v":
		            // Geometric vertex
		            Vector3 vec3f = new Vector3(
		                Float.parseFloat(tokens[1]),
		                Float.parseFloat(tokens[2]),
		                Float.parseFloat(tokens[3]));
		            vertices.add(vec3f);
		            break;
		        case "vt":
		            // Texture coordinate
		            Vector2 vec2f = new Vector2(
		                Float.parseFloat(tokens[1]),
		                Float.parseFloat(tokens[2]));
		            textures.add(vec2f);
		            break;
		        case "vn":
		            // Vertex normal
		            Vector3 vec3fNorm = new Vector3(
		                Float.parseFloat(tokens[1]),
		                Float.parseFloat(tokens[2]),
		                Float.parseFloat(tokens[3]));
		            normals.add(vec3fNorm);
		            break;
		        case "f":
		            Face face = new Face(tokens[1], tokens[2], tokens[3]);
		            faces.add(face);
		            break;
		        default:
		            // Ignore other lines
		            break;
		    }
		}
		return reorderLists(vertices, textures, normals, faces);
		
	}
	
	protected static class IdxGroup 
	{

	    public static final int NO_VALUE = -1;

	    public int idxPos;

	    public int idxTextCoord;

	    public int idxVecNormal;

	    public IdxGroup() {
	        idxPos = NO_VALUE;
	        idxTextCoord = NO_VALUE;
	        idxVecNormal = NO_VALUE;
	        }
	}
	
	protected static class Face
	{

	    /**
	     * List of idxGroup groups for a face triangle (3 vertices per face).
	    */
	    private IdxGroup[] idxGroups = new IdxGroup[3];

	    public Face(String v1, String v2, String v3) 
	    {
	        idxGroups = new IdxGroup[3];
	        // Parse the lines
	        idxGroups[0] = parseLine(v1);
	        idxGroups[1] = parseLine(v2);
	        idxGroups[2] = parseLine(v3);
	    }

	    private IdxGroup parseLine(String line) throws NumberFormatException 
	    {
	        IdxGroup idxGroup = new IdxGroup();

	        String[] lineTokens = line.split("/");
	        int length = lineTokens.length;
	        idxGroup.idxPos = Integer.parseInt(lineTokens[0]) - 1;
	        if (length > 1) {
	            // It can be empty if the obj does not define text coords
	            String textCoord = lineTokens[1];
	            idxGroup.idxTextCoord = textCoord.length() > 0 ? Integer.parseInt(textCoord) - 1 : IdxGroup.NO_VALUE;
	            if (length > 2) {
	                idxGroup.idxVecNormal = Integer.parseInt(lineTokens[2]) - 1;
	            }
	        }

	        return idxGroup;
	    }

	    public IdxGroup[] getFaceVertexIndices() 
	    {
	        return idxGroups;
	    }
	}
	
	private static Mesh reorderLists(List<Vector3> posList, List<Vector2> textCoordList,List<Vector3> normList, List<Face> facesList) throws Exception 
	{
		    List<Integer> indices = new ArrayList<Integer>();
		    // Create position array in the order it has been declared
		    float[] posArr = new float[posList.size() * 3];
		    int i = 0;
		    for (Vector3 pos : posList) 
		    {
		        posArr[i * 3] = pos.x;
		        posArr[i * 3 + 1] = pos.y;
		        posArr[i * 3 + 2] = pos.z;
		        i++;
		    }
		    float[] textCoordArr = new float[posList.size() * 2];
		    float[] normArr = new float[posList.size() * 3];

		    for (Face face : facesList) 
		    {
		        IdxGroup[] faceVertexIndices = face.getFaceVertexIndices();
		        for (IdxGroup indValue : faceVertexIndices)
		        {
		            processFaceVertex(indValue, textCoordList, normList,
		                indices, textCoordArr, normArr);
		        }
		    }
		    int[] indicesArr = new int[indices.size()];
		    indicesArr = indices.stream().mapToInt((Integer v) -> v).toArray();
		    Mesh mesh = new Mesh(posArr, textCoordArr, normArr, indicesArr);
		    return mesh;
		}

		private static void processFaceVertex(IdxGroup indices, List<Vector2> textCoordList, List<Vector3> normList, List<Integer> indicesList,
		    float[] texCoordArr, float[] normArr) 
		{

		    // Set index for vertex coordinates
		    int posIndex = indices.idxPos;
		    indicesList.add(posIndex);

		    // Reorder texture coordinates
		    if (indices.idxTextCoord >= 0) 
		    {
		        Vector2 textCoord = textCoordList.get(indices.idxTextCoord);
		        texCoordArr[posIndex * 2] = textCoord.x;
		        texCoordArr[posIndex * 2 + 1] = 1 - textCoord.y;
		    }
		    if (indices.idxVecNormal >= 0) 
		    {
		        // Reorder vector normals
		        Vector3 vecNorm = normList.get(indices.idxVecNormal);
		        normArr[posIndex * 3] = vecNorm.x;
		        normArr[posIndex * 3 + 1] = vecNorm.y;
		        normArr[posIndex * 3 + 2] = vecNorm.z;
		    }
		}
}