package graphics.compatibility;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
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
import graphics.Shader;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.util.vector.Vector3f;

public class CompatibilityMesh implements Mesh {

	private int vao;
	
	private int elements;
	private int elementCount;
	
	
	private int positionVbo;
	
	private int colorUniform;
	
	private int positionAttribute;
	
	private Vector3f position;
	private int modelPositionUniform;
	
	private float[] col;
	
	
	private static float offset = 0.0f;

	public CompatibilityMesh(Shader shader)
	{
		
		this.colorUniform = shader.getUniforms().get("color");
		this.modelPositionUniform = shader.getUniforms().get("model");
		this.positionAttribute = shader.getAttributes().get("position");
		
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
		else if (offset < 0.7)
		{
			col[0] = 1.0f; col[1] = 1.0f; col[2] = 0.0f;
		}
		else if (offset < 0.9)
		{
			col[0] = 0.1f; col[1] = 0.9f; col[2] = 0.5f;
		}
		else
		{
			col[0] = 0.8f; col[1] = 0.3f; col[2] = 0.6f;
		}
		
		
		vao = glGenVertexArrays();
		positionVbo = glGenBuffers();
		elements = glGenBuffers();
		
		glBindVertexArray(vao);
		
		float verts[] = {
				-1.0f, -1.0f, 0.f,
				-1.0f, 1.0f, 0.f,
				1.0f, 1.0f, 0.f,
				1.0f, -1.0f, 0.f,
				
				0.f, 0.f, 0.f,
				0.f, 1.f, 0.f,
				1.f, 1.f, 0.f,
				1.f, 0.f, 0.f,
		};
		this.position = new Vector3f(0.0f, 0.0f, offset);
		offset += 0.2f;
		
		FloatBuffer vertexBuff = GLOperations.generateFloatBuffer(verts);
		
		
		int elems[] = {0, 1, 2, 0, 2, 3};// 4, 5, 6, 4, 6, 7};
		IntBuffer elementBuff = GLOperations.generateIntBuffer(elems);
		this.elementCount = elems.length;
		
        glBindBuffer(GL_ARRAY_BUFFER, positionVbo);
        glBufferData(GL_ARRAY_BUFFER, vertexBuff , GL_STATIC_DRAW);
        
        
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, elements);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuff, GL_STATIC_DRAW);

        glVertexAttribPointer( positionAttribute, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(positionAttribute);
		
        
	}
	
	public void draw() {
		//set uniforms
		glUniform3f(colorUniform, col[0], col[1], col[2]);
		
		glUniform3f(modelPositionUniform, position.x, position.y, position.z);
		
		//bind
		glBindVertexArray(vao);

        glDrawElements(GL_TRIANGLES, elementCount, GL_UNSIGNED_INT, 0);

        glBindVertexArray(0);
	}
	

	
	
}
