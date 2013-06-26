package graphics;

import graphics.compatibility.CompatibilityRenderMaster;
import graphics.current.CurrentRenderMaster;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GLContext;


public class RenderMasterFactory {

	private static RenderMaster renderMaster;

	
	public static RenderMaster getRenderMaster()
	{
		if(renderMaster != null)
		{
			return renderMaster;
		}
		
		try{
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.create();
		}
		catch (Exception e)
		{
			System.out.println(e);
			System.exit(-1);
		}
		
		if(GLContext.getCapabilities().OpenGL32)
		{
			renderMaster = new CurrentRenderMaster();
		}
		else
		{
			renderMaster = new CompatibilityRenderMaster();
		}

		return renderMaster;
	}

}
