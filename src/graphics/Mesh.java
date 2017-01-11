package graphics;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;


public class Mesh 
{	
	FloatBuffer vertexPositionBuffer;
	IntBuffer vertexIndexBuffer;
	FloatBuffer textureCoordsBuffer;
	FloatBuffer normalPositionBuffer;
	public Material material = new Material();
	
	int vaoID;
	
	int verticesPositionVboID;
	int verticesIndexVboID;
	int textureVboID;
	int normalPositionsVboID;
	
	int vertexCount;
	float[] vertexPositions;
	float[] textureCoords;
	float[] normalPositions;
	int[] vertexIndices;
	
	public Mesh(float[] positions, float[] textCoords, float[] normals, int[] indices) throws Exception
	{		
		vertexPositions = positions;
		textureCoords = textCoords;
		normalPositions = normals;
		vertexIndices = indices;
		
		vertexCount = vertexPositions.length * 3;
		verticesPositionVboID = GL15.glGenBuffers();
		textureVboID = GL15.glGenBuffers();
		normalPositionsVboID = GL15.glGenBuffers();
		verticesIndexVboID = GL15.glGenBuffers();
		
		vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);
		
		vertexPositionBuffer = BufferUtils.createFloatBuffer(vertexPositions.length);
		vertexPositionBuffer.put(vertexPositions).flip();
		
		vertexIndexBuffer = BufferUtils.createIntBuffer(vertexIndices.length);
		vertexIndexBuffer.put(vertexIndices).flip();
		
		textureCoordsBuffer = BufferUtils.createFloatBuffer(textureCoords.length);
		textureCoordsBuffer.put(textureCoords).flip();
		
		normalPositionBuffer = BufferUtils.createFloatBuffer(normalPositions.length);
		normalPositionBuffer.put(normalPositions).flip();
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, verticesIndexVboID);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, vertexIndexBuffer, GL15.GL_STATIC_DRAW);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, verticesPositionVboID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexPositionBuffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, textureVboID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, textureCoordsBuffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, normalPositionsVboID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, normalPositionBuffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, 0, 0);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
	}
	
	public void Render()
	{
		GL30.glBindVertexArray(vaoID);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);		
		GL20.glEnableVertexAttribArray(2);
		
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 0);
		GL11.glDrawElements(GL11.GL_TRIANGLES, vertexCount, GL11.GL_UNSIGNED_INT, 0);
		
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		
	    GL30.glBindVertexArray(0);
	}

}
