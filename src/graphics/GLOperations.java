package graphics;

import static org.lwjgl.opengl.GL20.GL_INFO_LOG_LENGTH;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glShaderSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class GLOperations {
	
	public static FloatBuffer generateFloatBuffer(float[] input){
		FloatBuffer fbuff = null;
		try{
			fbuff = BufferUtils.createFloatBuffer(input.length);
			fbuff.put(input);		
			fbuff.rewind();
		}
		catch (Exception e)
		{
			System.out.println(e);
			return null;
		}
		return fbuff;
	}
	
	public static IntBuffer generateIntBuffer(int[] input) {
		IntBuffer ibuff = null;
		try{
			ibuff = BufferUtils.createIntBuffer(input.length);
			ibuff.put(input);		
			ibuff.rewind();
		}
		catch (Exception e)
		{
			System.out.println(e);
			return null;
		}
		return ibuff;
	}
	
	public static String loadFile(String fileName)
	{
		String fileString = "";
		String line = "";

		try{
			BufferedReader in = new BufferedReader(new FileReader(fileName)); 
		
			while((line = in.readLine()) != null)
			{
				fileString += line + "\n";
			}
			in.close();
			
		}catch	(Exception e)
		{
			System.out.println("Shader: " + e);
			return null;
		}
		return fileString;
	}
	
	public static int loadShaderString(String fileName, int shaderType) throws Exception
	{
		
		int shader = -1;
		
		//load the shader from its source
		String shaderSource = new String(fileName);
		
		shader = glCreateShader(shaderType);
		
        glShaderSource(shader, shaderSource );
        glCompileShader(shader);

        //error check
        IntBuffer infoLogLength = BufferUtils.createIntBuffer(1);
        glGetShader(shader, GL_INFO_LOG_LENGTH, infoLogLength);
        ByteBuffer infoLog = BufferUtils.createByteBuffer(infoLogLength.get(0));
        infoLogLength.clear();
        glGetShaderInfoLog(shader, infoLogLength, infoLog);
        byte[] infoLogBytes = new byte[infoLogLength.get(0)];
        infoLog.get(infoLogBytes, 0, infoLogLength.get(0));
        String res = new String(infoLogBytes);
        
        if(res.contains("error"))
        {
        	System.out.println("Error loading " + fileName + "\n");
        	System.out.println("Hey, this means you should upgrade your opengl driver and perhaps your graphics card\n");
        	System.out.println(res);
        	throw new Exception();
        }
        
		return shader;
	}


	
	
	
	
}
