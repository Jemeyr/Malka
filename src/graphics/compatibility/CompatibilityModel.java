package graphics.compatibility;

import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import graphics.GLOperations;
import graphics.Shader;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class CompatibilityModel{

	private CompatibilityMesh mesh;
	
	private float[] col;
	private int colorUniform;
	
	private Matrix4f model;
	private int modelUniform;
	
	
	
	private static float offset = 0.0f;

	protected CompatibilityModel(CompatibilityMesh mesh, Shader shader)
	{		
		this.colorUniform = shader.getUniforms().get("color");
		this.modelUniform = shader.getUniforms().get("model");

		
		this.mesh = mesh;
		
		this.col = new float[3];

		col[0] = (offset / 0.2f) % 4 * 0.25f;
		col[1] = (1 + offset / 0.2f) % 4 * 0.25f;
		col[2] = (2 + offset / 0.2f) % 4 * 0.25f;
		
		this.model = new Matrix4f();
		model.translate(new Vector3f(0.0f, 0.0f, -40 + offset));
		offset += 0.2f;
	}
	
	public void draw() {
		//set uniforms
		float temp = col[0];
		col[0] = col[0] * 0.9999f + col[1] * 0.0001f;
		col[1] = col[1] * 0.9999f + col[2] * 0.0001f;
		col[2] = col[2] * 0.9999f + temp * 0.0001f;
		
		glUniformMatrix4(modelUniform, false, GLOperations.generateFloatBuffer(model));		
		
		glUniform3f(colorUniform, col[0], col[1], col[2]);

		mesh.draw();
	}
	

	
	
}
