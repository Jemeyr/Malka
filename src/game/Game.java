package game;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import graphics.*;

public class Game {
	
	public static void main(String[] args)
	{
		RenderMaster renderMaster = RenderMasterFactory.getRenderMaster();
		
		Camera camera = renderMaster.getCamera();
		float rotation = 0.0f;
		float height = 0.0f;
		float lent = 15.0f;
		
		while(!Display.isCloseRequested())
		{
			//close if escape is hit
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
				Display.destroy();
				break;
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_LEFT))
			{
				rotation += 0.015f;	
			}else if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
			{
				rotation -= 0.015f;
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_UP))
			{
				height += 0.05f;	
			}else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN))
			{
				height -= 0.05f;
			}
			
			
			camera.setPosition(new Vector3f(lent * (float)Math.sin(rotation), 
											height, 
											lent * (float)Math.cos(rotation)));
			
			
			
			renderMaster.render();
			
			
		}
		
	}

}
