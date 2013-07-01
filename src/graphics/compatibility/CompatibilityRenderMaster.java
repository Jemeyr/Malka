
package graphics.compatibility;


import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import graphics.Light;
import graphics.Mesh;
import graphics.RenderMaster;
import graphics.Shader;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;

public class CompatibilityRenderMaster implements RenderMaster{
	
	List<CompatibilityMesh> meshes;
	
	Shader shader;
	
	
	
	public CompatibilityRenderMaster()
	{
		//glEnable(GL_DEPTH_TEST);
        
		String fragmentShader = 
				"#version 130\n" +
				"\n"+
				"uniform vec3 color;\n"+
				"void main(){\n\tgl_FragColor= vec4( color, 1.0 );\n}\n\n";
		
		String vertexShader = 
				"#version 130\n" +
				"attribute vec2 position;\n" +
				"void main(){\n\tgl_Position = vec4( position, 0.0, 1.0 );\n}\n\n";
		
		/*
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
        colorUniform = glGetUniformLocation( shaderProgram, "color");
        */
		
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
		
		int positionAttrib = shader.getAttributes().get("position");
		int colorUniform = shader.getUniforms().get("color");
		
		
        meshes = new ArrayList<CompatibilityMesh>();
		meshes.add(new CompatibilityMesh(positionAttrib, colorUniform));
		meshes.add(new CompatibilityMesh(positionAttrib, colorUniform));
		meshes.add(new CompatibilityMesh(positionAttrib, colorUniform));
		
        
	}

	
	public void render() {
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		for(Mesh mesh : meshes)
		{
			glBindVertexArray(mesh.getVao());
			mesh.setUniforms();
			
	        glDrawArrays(GL_TRIANGLES, 0, 3);
	
	        glBindVertexArray(0);
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
