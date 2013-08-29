package sound;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.vector.Vector3f;

public class Emitter {
	//This can have multiple sources and provides position/velocity information for them
	
	private SoundMaster soundMaster;
	private Map<String,List<Sound>> sounds;
	
	private Vector3f position;
	private Vector3f velocity;
	
	public Emitter(SoundMaster soundMaster){
		this.sounds = new HashMap<String,List<Sound>>();
		this.soundMaster = soundMaster;

		this.position = new Vector3f(0.0f, 0.0f, 0.0f);
		this.velocity = new Vector3f(0.0f, 0.0f, 0.0f);
		
	}
	
	public void playSound(String key){
		playSound(key, 1.0f);
	}
	
	public void playSound(String key, float pitch){
		
		//add a sound if there are none of that type
		if(this.sounds.get(key) == null){
			//create a list for this key, and a sound
			List<Sound> soundList = new ArrayList<Sound>();
			Sound sound = soundMaster.addSound(key);

			//put the sound in the list and the list in the map
			soundList.add(sound);
			this.sounds.put(key, soundList);
			
			//play the sound
			sound.play();
			

			System.out.println("Adding a sound. Soundcount for \""+key+"\": " + this.sounds.get(key).size());
			
			
		}
		else
		{
			//if there is an entry, then check if anyone in the list is playing
			List<Sound> soundList = this.sounds.get(key);
			boolean addSound = true;
			for(Sound sound : soundList){
				if(!sound.isPlaying())
				{
					System.out.println("reusing a sound");
					sound.play(pitch);
					addSound = false;
					break;
				}
			}
			
			if(addSound){

				System.out.println("Adding a sound. Soundcount for \""+key+"\": " + this.sounds.get(key).size());
				Sound sound = soundMaster.addSound(key);
				soundList.add(sound);

				//play the sound
				sound.play(pitch);	
			}
			
			
		}
		
		
		
	}
	
	

	public void update(){
		
		for(List<Sound> soundList : this.sounds.values()){
			for(Sound sound : soundList){
			
				FloatBuffer pos = BufferUtils.createFloatBuffer(3);
				FloatBuffer vel = BufferUtils.createFloatBuffer(3);
	
				position.store(pos);
				velocity.store(vel);
				
				pos.flip();
				vel.flip();
	
				AL10.alSource(sound.getId(), AL10.AL_POSITION, pos);
				AL10.alSource(sound.getId(), AL10.AL_VELOCITY, vel);
				
			}

		}
		
	
	}
	
	
	
	
	
}
