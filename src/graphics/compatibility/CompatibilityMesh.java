package graphics.compatibility;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;

import graphics.GLOperations;
import graphics.Mesh;

public class CompatibilityMesh implements Mesh {

	private int vao;
	private int vbo;
	
	private static float offset = 0.0f;

	public CompatibilityMesh()
	{
		//statically instantiate vao vbo and such for nowint vao;
		//init vao and vbo
		vbo = glGenBuffers();
		vao = glGenVertexArrays();

		glBindVertexArray(vao);
		
		float verts[] = {-1.0f + offset, -1.0f, -0.9f + offset, -0.8f, -0.8f + offset, -1.0f};
		offset += 0.2f;
		
		FloatBuffer vertexBuff = GLOperations.generateFloatBuffer(verts);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertexBuff , GL_STATIC_DRAW);

		
	}
	
	public int getVao() {
		return vao;
	}


	public int getVbo() {
		return vbo;
	}

	
	
}
