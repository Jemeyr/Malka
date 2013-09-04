package graphics.compatibility;

import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import graphics.GLOperations;
import graphics.Model;
import graphics.Shader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;

public class CompatibilityModel implements Model{

	private CompatibilityMesh mesh;
	
	private CompatibilityModel parent;
	private List<CompatibilityModel> children;
	
	private float[] col;
	private int colorUniform;
	
	private Matrix4f model;
	private int modelUniform;
	
	private Vector3f position;
	private Quaternion rotation;
	
	private float bertwhichishisnickname = 0.0f;
	
	
	private static float offset = 0.0f;

	protected CompatibilityModel(CompatibilityMesh mesh, Shader shader)
	{	
		this.colorUniform = shader.getUniforms().get("color");
		this.modelUniform = shader.getUniforms().get("model");

		this.children = new ArrayList<CompatibilityModel>();
		
		this.mesh = mesh;
		
		this.col = new float[3];

		col[0] = (offset) % 4 * 0.25f;
		col[1] = (1 + offset) % 4 * 0.25f;
		col[2] = (2 + offset) % 4 * 0.25f;
		
		Random r = new Random();
		col[0] = r.nextFloat();
		col[1] = r.nextFloat();
		col[2] = r.nextFloat();
		
		this.position = new Vector3f();
		this.rotation = new Quaternion(1.0f, 0.0f, 0.0f, bertwhichishisnickname);
		
		
		
		
		this.model = new Matrix4f();
		calculateModelMatrix();
		
		offset += 1.0f;
	}
	
	public void draw() {
		glUniformMatrix4(modelUniform, false, GLOperations.generateFloatBuffer(model));		
		
		glUniform3f(colorUniform, col[0], col[1], col[2]);

		mesh.draw();

		bertwhichishisnickname += bertwhichishisnickname > 3.14f ? -6.28f : 0.01f;
		this.rotation = new Quaternion(0.0f, 0.0f, 1.0f, bertwhichishisnickname * bertwhichishisnickname);
		
		calculateModelMatrix();
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
		//rotate
		
		Quaternion rotNorm = new Quaternion();
		this.rotation.normalise(rotNorm);
		
		Matrix4f rotationMat = new Matrix4f();
		rotationMat.m00 = 1.0f - 2.0f*(rotNorm.y*rotNorm.y + rotNorm.z*rotNorm.z);
		rotationMat.m01 = 2.0f*(rotNorm.x*rotNorm.y - rotNorm.z*rotNorm.w);
		rotationMat.m02 = 2.0f*(rotNorm.x*rotNorm.z + rotNorm.y*rotNorm.w);
		rotationMat.m03 = 0.0f;
		
		rotationMat.m10 = 2.0f*(rotNorm.x*rotNorm.y + rotNorm.z*rotNorm.w);
		rotationMat.m11 = 1.0f - 2.0f*(rotNorm.x*rotNorm.x + rotNorm.z*rotNorm.z);
		rotationMat.m12 = 2.0f*(rotNorm.y*rotNorm.z - rotNorm.x*rotNorm.w);
		rotationMat.m13 = 0.0f;
		
		rotationMat.m20 = 2.0f*(rotNorm.x*rotNorm.z - rotNorm.y*rotNorm.w);
		rotationMat.m21 = 2.0f*(rotNorm.y*rotNorm.z + rotNorm.x*rotNorm.w);
		rotationMat.m22 = 1.0f - 2.0f*(rotNorm.x*rotNorm.x + rotNorm.y*rotNorm.y);
		rotationMat.m23 = 0.0f;
		
		rotationMat.m30 = 0.0f;
		rotationMat.m31 = 0.0f;
		rotationMat.m32 = 0.0f;
		rotationMat.m33 = 1.0f;
	
		
		//translate
		this.model.translate(this.position);
	
		//rotate
		Matrix4f.mul(this.model, rotationMat, this.model);
	}


	public void setRotation(Quaternion rotation) {
		this.rotation = rotation;
		calculateModelMatrix();
	}

	public void addRotation(Quaternion delta) {
		Quaternion.mul(this.rotation, delta, this.rotation);	
		calculateModelMatrix();
	}
	
	public void addChild(CompatibilityModel model){
		this.children.add(model);
		model.parent = this;
	}
	
	public void removeChild(CompatibilityModel model){
		this.children.remove(model);
		model.parent = null;
	}
	
}
