package graphics;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class Texture 
{
	public int textureVboID;
	
	public Texture(String filePath) throws IOException
	{
		InputStream is = new FileInputStream(filePath);
		PNGDecoder decoder = new PNGDecoder(is);
		ByteBuffer textureAsByteBuffer = ByteBuffer.allocateDirect( 4 * decoder.getWidth() * decoder.getHeight());
		decoder.decode(textureAsByteBuffer, decoder.getWidth() * 4, Format.RGBA);
		textureAsByteBuffer.flip();
		
		textureVboID = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureVboID);
		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, textureAsByteBuffer);
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
	}

}
