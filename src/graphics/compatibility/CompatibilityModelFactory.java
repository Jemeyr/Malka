package graphics.compatibility;

import game.Game;
import graphics.Shader;
import graphics.compatibility.skeleton.Animation;
import graphics.compatibility.skeleton.Skeleton;

import java.util.HashMap;

import loader.ColladaLoader;

public class CompatibilityModelFactory {
	
	private Shader staticShader;
	private Shader skinnedShader;
	
	private HashMap<String, CompatibilityMesh> loadedMeshes;
	
	public CompatibilityModelFactory(Shader staticShader, Shader skinnedShader)
	{
		this.staticShader = staticShader;
		this.skinnedShader = skinnedShader;
		this.loadedMeshes = new HashMap<String, CompatibilityMesh>();
	}
	
	
	public CompatibilityModel getModel(String filename)
	{
		if(!loadedMeshes.containsKey(filename))
		{
			loadModel(filename);	
		}

		return new CompatibilityModel(loadedMeshes.get(filename), this.staticShader);
	}
	
	public void loadModel(String filename)
	{
		if(!loadedMeshes.containsKey(filename))
		{

			HashMap<String, Object> modelData = ColladaLoader.load(filename);
			
			//HACK
			if(filename.equals("temp/skeletan.dae")){
				Game.skeleton = (Skeleton)modelData.get("skeleton");
				Game.animation = (Animation)modelData.get("animation");
			}
			
			//TODO: load vertex weights and decide if they belong with skeleton or mesh. Probably mesh
			
			CompatibilityMesh mesh = new CompatibilityMesh(filename, staticShader, modelData);
			loadedMeshes.put(filename, mesh);
	
		}
	}
	
	public void unloadModel(String filename)
	{
		loadedMeshes.get(filename).delete();
		loadedMeshes.remove(filename);
		
	}
	
	
	
}
