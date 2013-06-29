package graphics.compatibility;


import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindFragDataLocation;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import graphics.GLOperations;
import graphics.Light;
import graphics.Mesh;
import graphics.RenderMaster;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;

public class CompatibilityRenderMaster implements RenderMaster{
	
	List<CompatibilityMesh> meshes;
	
	int shaderProgram;
	int fragShader;
	int vertShader;
	
	int positionAttrib;
	
	
	
	public CompatibilityRenderMaster()
	{
		glEnable(GL_DEPTH_TEST);
        
		meshes = new ArrayList<CompatibilityMesh>();
		meshes.add(new CompatibilityMesh());
		meshes.add(new CompatibilityMesh());
		meshes.add(new CompatibilityMesh());
		
		String fragmentShader = 
				"#version 130\n" +
				"\n"+
				"void main(){\n\tgl_FragColor= vec4( 1.0, 0.0, 1.0, 1.0 );\n}\n\n";
		
		String vertexShader = 
				"#version 130\n" +
				"attribute vec2 position;\n" +
				"void main(){\n\tgl_Position = vec4( position, 0.0, 1.0 );\n}\n\n";
		
        
        
    	try{
    		vertShader = GLOperations.loadShaderString(vertexShader, GL_VERTEX_SHADER);
    		fragShader = GLOperations.loadShaderString(fragmentShader, GL_FRAGMENT_SHADER);
    	} 
    	catch (Exception e)
        {
        	System.out.println("ERROR IN SHADER LOADING");
        	System.out.println(e);
        	//end program
        	Display.destroy();
        }
    	
        shaderProgram = glCreateProgram();
        
        glAttachShader(shaderProgram, vertShader);
        glAttachShader(shaderProgram, fragShader);
        
        glBindFragDataLocation( shaderProgram, 0, "outColor");
        
        glLinkProgram(shaderProgram);
        glUseProgram(shaderProgram);
        
        
        positionAttrib = glGetAttribLocation( shaderProgram, "position");
    	
	}

	
	public void render() {
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		for(Mesh mesh : meshes)
		{
			glBindVertexArray(mesh.getVao());
	        glBindBuffer(GL_ARRAY_BUFFER, mesh.getVbo());

	        glVertexAttribPointer( positionAttrib, 2, GL_FLOAT, false, 0, 0);
	        glEnableVertexAttribArray(positionAttrib);
	        
	        glDrawArrays(GL_TRIANGLES, 0, 3);
	
	        glBindVertexArray(0);
	        glBindBuffer(GL_ARRAY_BUFFER, 0);
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
