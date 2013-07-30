package graphics;

public interface RenderMaster {
	
	public void render();
	
	// add area light
	public Light addLight();
	
	// add master light
	public Light addMasterLight();
	
	//load the meshes 
	public void loadMeshes(String[] filenames);
	
	//unload all meshes
	public void unloadMeshes(String[] filenames);
	
	// add mesh
	public MeshData addMesh(String name);
}
