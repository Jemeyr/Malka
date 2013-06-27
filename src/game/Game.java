package game;

import graphics.*;

public class Game {
	
	public static void main(String[] args)
	{
		RenderMaster renderMaster = RenderMasterFactory.getRenderMaster();
		
		for(int i = 0; i < 10000; i++)
		{
			renderMaster.render();
		}
		
	}

}
