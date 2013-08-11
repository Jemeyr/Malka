package game;

import input.Control;
import input.Controller;
import input.KeyboardController;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import graphics.*;

public class Game {
	
	public static void main(String[] args)
	{
		RenderMaster renderMaster = RenderMasterFactory.getRenderMaster();
		
		Controller controller = new KeyboardController();
		
		Camera camera = renderMaster.getCamera();
		float rotation = 0.0f;
		float height = 0.0f;
		float lent = 15.0f;
		float fov = 90.0f;
		
		while(!Display.isCloseRequested())
		{
			//close if escape is hit
			if(controller.isPressed(Control.EXIT)){
				Display.destroy();
				break;
			}
			
			if(controller.isPressed(Control.LEFT))
			{
				rotation += 0.015f;	
			}else if(controller.isPressed(Control.RIGHT))
			{
				rotation -= 0.015f;
			}
			
			if(controller.isPressed(Control.UP))
			{
				height += 0.05f;	
			}else if(controller.isPressed(Control.DOWN))
			{
				height -= 0.05f;
			}
			
			if(controller.isPressed(Control.CONTROL))
			{
				fov *= 1.01f;
			}else if(controller.isPressed(Control.SHIFT))
			{
				fov *= 0.99f;
			}
			
			fov = fov > 180.0f ? 180.0f : fov <= 0.0f ? 0.0f : fov;
			
			camera.setPosition(new Vector3f(lent * (float)Math.sin(rotation), 
											height, 
											lent * (float)Math.cos(rotation)));
			
			camera.setFOV(fov);
			
			
			
			renderMaster.render();
			
			
		}
		
	}

}
