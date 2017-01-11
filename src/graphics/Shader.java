package graphics;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import math.Matrix4x4;
import math.Vector3;
import math.Vector4;

public class Shader
{
    private final int programId;
    private int vertexShaderId;
    private int fragmentShaderId;
    private Map<String, Integer> uniforms;

    public Shader() throws Exception
    {
        programId = GL20.glCreateProgram();
        if (programId == 0)
        {
            throw new Exception("Could not create Shader");
        }
        uniforms = new HashMap<>();
    }

    public void createVertexShader(String shaderCode) throws Exception 
    {
        vertexShaderId = createShader(shaderCode, GL20.GL_VERTEX_SHADER);
    }

    public void createFragmentShader(String shaderCode) throws Exception 
    {
        fragmentShaderId = createShader(shaderCode, GL20.GL_FRAGMENT_SHADER);
    }
    
    public void createUniform(String uniformName) throws Exception 
    {
        int uniformLocation = GL20.glGetUniformLocation(programId, uniformName);
        if (uniformLocation < 0) 
        {
            throw new Exception("Could not find uniform:" + uniformName);
        }
        uniforms.put(uniformName, uniformLocation);
    }
    
    public void setUniform(String uniformName, Matrix4x4 matrix) 
    {
        FloatBuffer fb = BufferUtils.createFloatBuffer(16);
        fb.put(Matrix4x4.serializeMatrix4x4(matrix));
        fb.flip();
        GL20.glUniformMatrix4fv(uniforms.get(uniformName), false, fb);
    }
    
    public void setUniform(String uniformName, int value) 
    {
        GL20.glUniform1i(uniforms.get(uniformName), value);
    }
    
    public void setUniform(String uniformName, Vector4 value) 
    {
    	GL20.glUniform4f(uniforms.get(uniformName), value.x, value.y, value.z, value.w );
    }

    protected int createShader(String shaderCode, int shaderType) throws Exception 
    {
        int shaderId = GL20.glCreateShader(shaderType);
        if (shaderId == 0)
        {
            throw new Exception("Error creating shader. Code: " + shaderId);
        }

        GL20.glShaderSource(shaderId, shaderCode);
        GL20.glCompileShader(shaderId);

        if (GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == 0) 
        {
            throw new Exception("Error compiling Shader code: " + GL20.glGetShaderInfoLog(shaderId, 1024));
        }

        GL20.glAttachShader(programId, shaderId);

        return shaderId;
    }

    public void Link() throws Exception
    {
        GL20.glLinkProgram(programId);
        if (GL20.glGetProgrami(programId, GL20.GL_LINK_STATUS) == 0) 
        {
            throw new Exception("Error linking Shader code: " + GL20.glGetProgramInfoLog(programId, 1024));
        }

        GL20.glValidateProgram(programId);
        if (GL20.glGetProgrami(programId, GL20.GL_VALIDATE_STATUS) == 0) 
        {
            System.err.println("Warning validating Shader code: " + GL20.glGetProgramInfoLog(programId, 1024));
        }

    }

    public void Bind() 
    {
    	GL20.glUseProgram(programId);
    }

    public void Unbind() 
    {
    	GL20.glUseProgram(0);
    }

    public void CleanUp() 
    {
        Unbind();
        if (programId != 0) 
        {
            if (vertexShaderId != 0) 
            {
            	GL20.glDetachShader(programId, vertexShaderId);
            }
            if (fragmentShaderId != 0) 
            {
            	GL20.glDetachShader(programId, fragmentShaderId);
            }
            GL20.glDeleteProgram(programId);
        }
    }
}