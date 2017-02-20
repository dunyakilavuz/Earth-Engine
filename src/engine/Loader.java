package engine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import graphics.Mesh;
import graphics.MeshData;
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
	
	public static MeshData ImportOBJ(String filePath) throws Exception
	{
		List<Vector3> vertices = new ArrayList<Vector3>();
		List<Vector2> textures = new ArrayList<Vector2>();
		List<Vector3> normals = new ArrayList<Vector3>();
		List<MeshData.Face> faces = new ArrayList<MeshData.Face>();
		
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
		        	MeshData.Face face = new MeshData.Face(tokens[1], tokens[2], tokens[3]);
		            faces.add(face);
		            break;
		        default:
		            // Ignore other lines
		            break;
		    }
		}
		return new MeshData(vertices,textures,normals,faces);
	}

}