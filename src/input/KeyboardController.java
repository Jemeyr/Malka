package input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.input.Keyboard;

public class KeyboardController implements Controller{
	
	Map<String, Integer> controlMap;
	
	public KeyboardController(){
		this.controlMap = new HashMap<String, Integer>();

		try{
			BufferedReader br = new BufferedReader(new FileReader("temp/input.properties"));;
		
			String line;
			
			while( (line=br.readLine()) != null)
			{
				String[] tokens = line.split("=");
				String property = tokens[0];
				String value = tokens[1];
				
				int val;
				
				switch(value)
				{
				case "up": val = Keyboard.KEY_UP; break;
				case "down": val = Keyboard.KEY_DOWN; break;
				case "left": val = Keyboard.KEY_LEFT; break;
				case "right": val = Keyboard.KEY_RIGHT; break;
				case "left_shift": val = Keyboard.KEY_LSHIFT; break;
				case "left_ctrl": val = Keyboard.KEY_LCONTROL; break;
				case "right_shift": val = Keyboard.KEY_RSHIFT; break;
				case "right_ctrl": val = Keyboard.KEY_RCONTROL; break;
				case "space": val = Keyboard.KEY_SPACE; break;
				case "escape": val = Keyboard.KEY_ESCAPE; break;
				default:
					System.out.println("Error parsing " + value + " from properties file");
					br.close();
					return;
				}
				
				controlMap.put(property, val);	
			}
			
			br.close();
		}
		catch(Exception e){
			System.out.println("Error opening properties file");
		}
			
		
	}

	@Override
	public boolean isPressed(String key) {
		if(controlMap.containsKey(key))
		{
			return Keyboard.isKeyDown(controlMap.get(key));
		}
		
		System.out.println("Error, invalid key " + key + " requested, not in map");
		return false;
	}
	
	

}
