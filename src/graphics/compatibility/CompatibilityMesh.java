package graphics.compatibility;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import graphics.GLOperations;
import graphics.Shader;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class CompatibilityMesh{

	private int vao;
	
	private int elements;
	private int elementCount;
	
	
	private int positionVbo;
	private int normalVbo;
	private int texCoordVbo;
	
	private static int textureId;
	
	private int positionAttribute;
	private int normalAttribute;
	private int texCoordAttribute;
	
	public CompatibilityMesh(Shader shader)
	{
		
		this.positionAttribute = shader.getAttributes().get("position");
		this.normalAttribute = shader.getAttributes().get("normal");
		this.texCoordAttribute = shader.getAttributes().get("texCoord");
		
		if(textureId == 0)
		{
			textureId = GLOperations.loadTexture("temp/debug.png");
		}
		
		
		
		vao = glGenVertexArrays();
		positionVbo = glGenBuffers();
		normalVbo = glGenBuffers();
		texCoordVbo = glGenBuffers();
		elements = glGenBuffers();
		
		glBindVertexArray(vao);
		
		float verts[] = {
				-1.0f, -1.0f, 0.f,
				-1.0f, 1.0f, 0.f,
				1.0f, 1.0f, 0.f,
				1.0f, -1.0f, 0.f,
		};
		FloatBuffer vertexBuff = GLOperations.generateFloatBuffer(verts);
		glBindBuffer(GL_ARRAY_BUFFER, positionVbo);
        glBufferData(GL_ARRAY_BUFFER, vertexBuff , GL_STATIC_DRAW);
        
        float normals[] = {
				0.f, 0.0f, 1.f,
				0.f, 0.0f, 1.f,
				0.f, 0.0f, 1.f,
				0.f, 0.0f, 1.f,
		};
		FloatBuffer normalBuff = GLOperations.generateFloatBuffer(normals);
		glBindBuffer(GL_ARRAY_BUFFER, normalVbo);
        glBufferData(GL_ARRAY_BUFFER, normalBuff , GL_STATIC_DRAW);
        
        float texCoords[] = {
				0.0f, 0.0f,
				0.0f, 1.0f,
				1.0f, 1.0f,
				1.0f, 0.0f,
		};
        FloatBuffer texCoordBuff = GLOperations.generateFloatBuffer(texCoords);
        glBindBuffer(GL_ARRAY_BUFFER, texCoordVbo);
        glBufferData(GL_ARRAY_BUFFER, texCoordBuff, GL_STATIC_DRAW);
        
        
        //element buffer
        int elems[] = {0, 1, 2, 0, 2, 3};
		IntBuffer elementBuff = GLOperations.generateIntBuffer(elems);
		this.elementCount = elems.length;
		
		
		//bind and buffer data
		glBindBuffer(GL_ARRAY_BUFFER, positionVbo);
        glVertexAttribPointer( positionAttribute, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(positionAttribute);
		
        glBindBuffer(GL_ARRAY_BUFFER, normalVbo);
        glVertexAttribPointer( normalAttribute, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(normalAttribute);

        glBindBuffer(GL_ARRAY_BUFFER, texCoordVbo);
        glVertexAttribPointer( texCoordAttribute, 2, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(texCoordAttribute);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, elements);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuff, GL_STATIC_DRAW);

	}
	
	public void draw() {
	
		glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, 1);
		//bind
		glBindVertexArray(vao);

        glDrawElements(GL_TRIANGLES, elementCount, GL_UNSIGNED_INT, 0);

        glBindVertexArray(0);
	}
	
	public void delete() {
		
		glDeleteBuffers(this.elements);
		glDeleteBuffers(this.positionVbo);
		glDeleteBuffers(this.normalVbo);
		glDeleteBuffers(this.texCoordVbo);
		
		//TODO: figure out how to free the texture it's late and you should run.
		
		
		glDeleteVertexArrays(this.vao);
		
		glBindVertexArray(0);
		
	}

	
	
}
