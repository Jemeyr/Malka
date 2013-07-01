package graphics.compatibility;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import graphics.GLOperations;
import graphics.Mesh;

import java.nio.FloatBuffer;

public class CompatibilityMesh implements Mesh {

	private int vao;
	private int positionVbo;
	private int colorUniform;
	private float[] col;
	
	
	private static float offset = 0.0f;

	public CompatibilityMesh(int positionAttrib, int colorUniform)
	{
		
		this.colorUniform = colorUniform;
		
		this.col = new float[3];
		
		if(offset < 0.1f)
		{
			col[0] = 1.0f; col[1] = 0.0f; col[2] = 0.0f;
		}
		else if (offset < 0.3f)
		{
			col[0] = 0.0f; col[1] = 1.0f; col[2] = 0.0f;
		}
		else if (offset < 0.5)
		{
			col[0] = 0.0f; col[1] = 0.0f; col[2] = 1.0f;
		}
		else
		{
			col[0] = 1.0f; col[1] = 1.0f; col[2] = 0.0f;
		}
		
		
		vao = glGenVertexArrays();
		positionVbo = glGenBuffers();

		glBindVertexArray(vao);

        
		
		float verts[] = {-1.0f + offset, -1.0f, -0.9f + offset, -0.8f, -0.8f + offset, -1.0f};
		offset += 0.2f;
		
		FloatBuffer vertexBuff = GLOperations.generateFloatBuffer(verts);

        glBindBuffer(GL_ARRAY_BUFFER, positionVbo);
        glBufferData(GL_ARRAY_BUFFER, vertexBuff , GL_STATIC_DRAW);

        glVertexAttribPointer( positionAttrib, 2, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(positionAttrib);
		
        
	}
	
	public int getVao() {
		return vao;
	}

	public void setUniforms() {
		glUniform3f(colorUniform, col[0], col[1], col[2]);
	}
	
	
}
