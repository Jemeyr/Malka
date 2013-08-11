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
			case SHIFT:
				return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
			case CONTROL:
				return Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
					
			case EXIT:
				return Keyboard.isKeyDown(Keyboard.KEY_ESCAPE);
		}

		return false;
	}
	
	

}
