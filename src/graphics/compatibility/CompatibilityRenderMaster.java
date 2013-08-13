package graphics.compatibility;


import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import graphics.Camera;
import graphics.Light;
import graphics.Model;
import graphics.RenderMaster;
import graphics.Shader;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

public class CompatibilityRenderMaster implements RenderMaster{
	
	CompatibilityModelFactory modelFactory;
	
	List<CompatibilityModel> models;
	Camera camera;
	Shader shader;
	
	public CompatibilityRenderMaster()
	{
        glEnable(GL_DEPTH_TEST);
		
		String fragmentShader = "temp/fragShader.txt";
		String vertexShader = "temp/vertShader.txt";
		
		shader = new CompatibilityShader(fragmentShader, vertexShader);
		
		
		modelFactory = new CompatibilityModelFactory(shader);
		
        models = new ArrayList<CompatibilityModel>();
        
        Model m = null;
        for(int i = 0; i < 80; i++)
        {
        	m = this.addModel("filename goes here");
        }
		
        m.setPosition(new Vector3f(0.0f,0.0f,7.5f));
		
		camera = new Camera(shader);
		
	}

	
	public void render() {
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		camera.setActive();
		for(CompatibilityModel mesh : models)
		{
			mesh.draw();
		}
        
        Display.update();
    }

	public Camera getCamera() {
		return this.camera;
	}
	
	@Override
	public Light addLight() {
		// TODO Auto-generated method stub
		return null;
	}

	public Light addMasterLight() {
		// TODO Auto-generated method stub
		return null;
	}

	public void loadMeshes(String[] filenames) {
		for(String file : filenames){
			modelFactory.loadModel(file);
		}
	}

	public void unloadMeshes(String[] filenames) {
		for(String file : filenames){
			modelFactory.unloadModel(file);
		}
	}

	public Model addModel(String filename) {
		CompatibilityModel m = modelFactory.getModel(filename);
		models.add(m);
		
		return (Model)m;
	}

}
