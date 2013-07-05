package graphics;

import static org.lwjgl.opengl.GL20.glUniform2f;

import org.lwjgl.util.vector.Vector2f;

public class Camera {

	private Vector2f pos;
	private int posUniform;
	
	public Camera(Shader shader)
	{
		this.pos = new Vector2f(0.0f, 0.0f);
		
		this.posUniform = shader.getUniforms().get("cameraPosition");
	}
	
	public void setActive()
	{
		glUniform2f(posUniform, pos.x, pos.y);
	}
	
	
	
}
