package graphics.compatibility;

import game.Game;
import graphics.Shader;
import graphics.compatibility.skeleton.Animation;
import graphics.compatibility.skeleton.Skeleton;

import java.util.HashMap;

import loader.ColladaLoader;

public class CompatibilityModelFactory {
	
	private Shader shader;
	
	private HashMap<String, CompatibilityMesh> loadedMeshes;
	
	public CompatibilityModelFactory(Shader shader)
	{
		this.shader = shader;
		this.loadedMeshes = new HashMap<String, CompatibilityMesh>();
	}
	
	
	public CompatibilityModel getModel(String filename)
	{
		if(!loadedMeshes.containsKey(filename))
		{
			loadModel(filename);	
		}

		return new CompatibilityModel(loadedMeshes.get(filename), this.shader);
	}
	
	public void loadModel(String filename)
	{
		if(!loadedMeshes.containsKey(filename))
		{

			HashMap<String, Object> modelData = ColladaLoader.load(filename);
			
			if(filename.equals("temp/skeletan.dae")){
				Game.skeleton = (Skeleton)modelData.get("skeleton");
				Game.animation = (Animation)modelData.get("animation");
			}
			
			CompatibilityMesh mesh = new CompatibilityMesh(filename, shader, modelData);
			loadedMeshes.put(filename, mesh);
	
		}
	}
	
	public void unloadModel(String filename)
	{
		loadedMeshes.get(filename).delete();
		loadedMeshes.remove(filename);
		
	}
	
	
	
}
