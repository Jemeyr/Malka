package graphics.compatibility;

import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import graphics.GLOperations;
import graphics.Model;
import graphics.Shader;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class CompatibilityModel implements Model{

	private CompatibilityMesh mesh;
	
	private float[] col;
	private int colorUniform;
	
	private Matrix4f model;
	private int modelUniform;
	
	private Vector3f position;
	
	
	
	private static float offset = 0.0f;

	protected CompatibilityModel(CompatibilityMesh mesh, Shader shader)
	{		
		this.colorUniform = shader.getUniforms().get("color");
		this.modelUniform = shader.getUniforms().get("model");

		
		this.mesh = mesh;
		
		this.col = new float[3];

		col[0] = (offset) % 4 * 0.25f;
		col[1] = (1 + offset) % 4 * 0.25f;
		col[2] = (2 + offset) % 4 * 0.25f;
		
		this.position = new Vector3f();//((int)offset)%2==1?2.0f:-2.0f, 0.0f, -60 + offset * 1.5f);
		
		this.model = new Matrix4f();
		calculateModelMatrix();

		
		offset += 1.0f;
	}
	
	public void draw() {
		glUniformMatrix4(modelUniform, false, GLOperations.generateFloatBuffer(model));		
		
		glUniform3f(colorUniform, col[0], col[1], col[2]);

		mesh.draw();
	}
	
	public void setPosition(Vector3f newPosition){
		this.position = newPosition;
		calculateModelMatrix();
	}
	
	public void addPosition(Vector3f delta){
		this.position = Vector3f.add(this.position, delta, null);
		calculateModelMatrix();
	}
	
	private void calculateModelMatrix(){
		this.model.setIdentity();
		this.model.translate(this.position);
		
	}

	
	
}
