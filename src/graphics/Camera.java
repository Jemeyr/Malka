package graphics;

import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import static org.lwjgl.opengl.GL20.glUniform3f;

import java.nio.FloatBuffer;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

	private Vector3f pos;
	private Vector3f target;
	
	private Matrix4f view;
	private Matrix4f perspective;
	
	private FloatBuffer viewPerspective;
	private boolean dirty;
	
	private int posUniform;
	private int matUniform;
	
	private float fov = 90.f;
	
	
	public Camera(Shader shader)
	{
		this.pos = new Vector3f(0.0f, 0.0f, 6.0f);
		this.target = new Vector3f(0.0f, 0.0f, 0.0f);
		
		
		this.dirty = true;
		
		this.view = GLOperations.buildViewMatrix(pos, target);
		this.perspective = GLOperations.buildPerspectiveMatrix(fov, 1.33f, 0.1f, 1000f);
		
		//view, perspective apparently means perspective * view. AK does this too, dunno why
		this.viewPerspective = GLOperations.generateFloatBuffer(Matrix4f.mul(perspective, view, null)); 
	
		this.posUniform = shader.getUniforms().get("cameraPosition");
		this.matUniform = shader.getUniforms().get("viewPerspective");
		
	}
	
	public void setActive()
	{
		update();
	}
	
	public void setPosition(Vector3f newPos)
	{
		dirty = true;
		pos = newPos;
	}
	
	public void addPosition(Vector3f delta)
	{
		dirty = true;
		Vector3f.add(pos, delta, pos);
	}
	
	
	
	public void update()
	{
		if(dirty)
		{
			dirty = false;
			
			//put these in the gloperations
			this.view = getFuckingView(pos, target);
			
			this.perspective = GLOperations.buildPerspectiveMatrix(fov, 1.33f, 0.1f, 1000f);
			
			this.viewPerspective = GLOperations.generateFloatBuffer(Matrix4f.mul(perspective, view, null));
			
			glUniformMatrix4(matUniform, false, viewPerspective);
			glUniform3f(posUniform, pos.x, pos.y, pos.z);
		}
	}
	
	public Matrix4f getFuckingView(Vector3f camPos, Vector3f target)
	{
		System.out.println(" gogo cam pos " + camPos);
		
		Matrix4f result = new Matrix4f();
		result.setIdentity();
		
		Vector3f zaxis = Vector3f.sub(target, camPos, null);
		zaxis.normalise();
		
		Vector3f xaxis = Vector3f.cross(new Vector3f(0.0f, 1.0f, 0.0f), zaxis, null);
		xaxis.normalise();
		
		Vector3f yaxis = Vector3f.cross(zaxis, xaxis, null);
		yaxis.normalise();//fuck whatever
		
		System.out.println("dir: " + zaxis);
		
		Matrix4f orientation = new Matrix4f();
		
		orientation.m00 = xaxis.x;
		orientation.m01 = xaxis.y;
		orientation.m02 = xaxis.z;
		
		orientation.m10 = zaxis.x;
		orientation.m11 = zaxis.y;
		orientation.m12 = zaxis.z;
		
		orientation.m20 = yaxis.x;
		orientation.m21 = yaxis.y;
		orientation.m22 = yaxis.z;
		
		orientation.m33 = 1.0f;
		
		Matrix4f translation = new Matrix4f();
		translation.setIdentity();
		translation.m03 = -camPos.x;
		translation.m13 = -camPos.y;
		translation.m23 = -camPos.z;
		
		Matrix4f.mul(translation, orientation, result);
		
		return result;
	}
	
}
