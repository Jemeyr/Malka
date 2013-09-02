package graphics;

import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;

public interface Model {
	//position/rotation
	//gpu info (texture, mesh)
	//parent?
	
	public void draw();
	
	public void setPosition(Vector3f position);
	
	public void addPosition(Vector3f delta);
	
	public void setRotation(Quaternion rotation);
	
	public void addRotation(Quaternion delta);
	
	
	
}
