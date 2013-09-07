package input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Keyboard;

public class KeyboardController implements Controller{
	
	Map<String, List<Integer>> controlMap;
	
	public KeyboardController(){
		this.controlMap = new HashMap<String, List<Integer>>();
		
		try{
			BufferedReader br = new BufferedReader(new FileReader("temp/input.properties"));
		
			String line;
			
			while( (line=br.readLine()) != null)
			{
				String[] tokens = line.split("=");
				String property = tokens[0];
				
				String[] values = tokens[1].split(",");
				List<Integer> keyCodes = new ArrayList<Integer>();
				for(String value : values){
				
					int val;
					
					switch(value)
					{
					case "up": val = Keyboard.KEY_UP; break;
					case "down": val = Keyboard.KEY_DOWN; break;
					case "left": val = Keyboard.KEY_LEFT; break;
					case "right": val = Keyboard.KEY_RIGHT; break;
					case "left_shift": val = Keyboard.KEY_LSHIFT; break;
					case "left_control": val = Keyboard.KEY_LCONTROL; break;
					case "right_shift": val = Keyboard.KEY_RSHIFT; break;
					case "right_control": val = Keyboard.KEY_RCONTROL; break;
					case "space": val = Keyboard.KEY_SPACE; break;
					case "escape": val = Keyboard.KEY_ESCAPE; break;
					case "w": val = Keyboard.KEY_W; break;
					case "a": val = Keyboard.KEY_A; break;
					case "s": val = Keyboard.KEY_S; break;
					case "d": val = Keyboard.KEY_D; break;
					
					default:
						System.out.println("Error parsing " + value + " from properties file");
						br.close();
						return;
					}
					keyCodes.add(val);
				}
				controlMap.put(property, keyCodes);	
			}
			
			br.close();
		}
		catch(Exception e){
			System.out.println("Error opening properties file");
		}
			
		
	}

	@Override
	public boolean isPressed(String mapKey) {
		if(controlMap.containsKey(mapKey))
		{
			for(Integer key : controlMap.get(mapKey)){
				if(Keyboard.isKeyDown(key))
				{
					return true;
				}
			}
			
			return false;
		}
		
		System.out.println("Error, invalid key " + mapKey + " requested, not in map");
		return false;
	}
	
	

}
