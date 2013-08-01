
package graphics.compatibility;


import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import graphics.Camera;
import graphics.Light;
import graphics.MeshData;
import graphics.RenderMaster;
import graphics.Shader;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

public class CompatibilityRenderMaster implements RenderMaster{
	
	List<CompatibilityModel> meshes;
	Camera camera;
	Shader shader;
	
	static float rotation = 0.0f;
	static float height = 0.0f;
	
	public CompatibilityRenderMaster()
	{
        glEnable(GL_DEPTH_TEST);
		
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
		
		CompatibilityModelFactory modelFactory = new CompatibilityModelFactory(shader);
		
        meshes = new ArrayList<CompatibilityModel>();
        
        for(int i = 0; i < 800; i++)
        {
        	meshes.add(modelFactory.getModel("Filename goes here when this loads files"));	
        }
		
		
		camera = new Camera(shader);
		
	}

	
	public void render() {
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT))
		{
			rotation += 0.02f;	
		}else if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
		{
			rotation -= 0.02f;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_UP))
		{
			height += 0.05f;	
		}else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN))
		{
			height -= 0.05f;
		}
		
		
		float lent = 50.0f;
		camera.setPosition(new Vector3f(lent * (float)Math.sin(rotation), height, lent * (float)Math.cos(rotation)));
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		camera.setActive();
		for(CompatibilityModel mesh : meshes)
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
	public MeshData addMesh(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
