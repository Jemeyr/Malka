
package graphics.compatibility;


import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import graphics.Camera;
import graphics.Light;
import graphics.Mesh;
import graphics.RenderMaster;
import graphics.Shader;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

public class CompatibilityRenderMaster implements RenderMaster{
	
	List<CompatibilityMesh> meshes;
	Camera camera;
	Shader shader;
	
	static Vector3f dist = new Vector3f(0.0f, 0.0f, -0.0f);
	
	public CompatibilityRenderMaster()
	{
        
		String fragmentShader = "temp/fragShader.txt";
		
		String vertexShader = "temp/vertShader.txt";
		
		
		shader = new CompatibilityShader(fragmentShader, vertexShader);
		
		if (shader == null)
		{
			System.out.println("nullshader");
		}
		if (shader.getAttributes() == null)
		{
			System.out.println("null attributes");
		}
		if (shader.getAttributes().get("position") == null)
		{
			System.out.println("no position");
		}
		
        meshes = new ArrayList<CompatibilityMesh>();
		meshes.add(new CompatibilityMesh(shader));
//		meshes.add(new CompatibilityMesh(shader));
//		meshes.add(new CompatibilityMesh(shader));
//		meshes.add(new CompatibilityMesh(shader));
//		meshes.add(new CompatibilityMesh(shader));
//		meshes.add(new CompatibilityMesh(shader));
		
		
		camera = new Camera(shader);
		
	}

	
	public void render() {
		camera.addPosition(dist);
		
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		camera.setActive();
		for(Mesh mesh : meshes)
		{
			mesh.draw();
		}
        
        Display.update();
    }

	@Override
	public Light addLight() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Light addMasterLight() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadMeshes(String[] filenames) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unloadMeshes(String[] filenames) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Mesh addMesh(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
