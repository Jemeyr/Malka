package graphics.compatibility;

import graphics.Shader;

import java.util.HashMap;

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
			CompatibilityMesh mesh = new CompatibilityMesh(shader);
			loadedMeshes.put(filename, mesh);
		}

		return new CompatibilityModel(loadedMeshes.get(filename), this.shader);
	}
	
	
	
}
