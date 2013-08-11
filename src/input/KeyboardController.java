package input;

import org.lwjgl.input.Keyboard;

public class KeyboardController implements Controller{

	@Override
	public boolean isPressed(Control control) {
		switch(control){
			case UP:
				return Keyboard.isKeyDown(Keyboard.KEY_UP);
			case DOWN:
				return Keyboard.isKeyDown(Keyboard.KEY_DOWN);
			case LEFT:
				return Keyboard.isKeyDown(Keyboard.KEY_LEFT);
			case RIGHT:
				return Keyboard.isKeyDown(Keyboard.KEY_RIGHT);
			case EXIT:
				return Keyboard.isKeyDown(Keyboard.KEY_ESCAPE);
		}

		return false;
	}
	
	

}
