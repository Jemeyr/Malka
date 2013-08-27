package sound;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.vector.Vector3f;

public class Emitter {
	//This can have multiple sources and provides position/velocity information for them
	
	private SoundMaster soundMaster;
	private List<Sound> sounds;
	
	private Vector3f position;
	private Vector3f velocity;
	
	public Emitter(SoundMaster soundMaster){
		this.sounds = new ArrayList<Sound>();
		this.soundMaster = soundMaster;

		this.position = new Vector3f(0.0f, 0.0f, 0.0f);
		this.velocity = new Vector3f(0.0f, 0.0f, 0.0f);
		
	}
	
	public Sound addSound(String key){
		Sound sound = soundMaster.addSound(key);
		this.sounds.add(sound);
		return sound;
	}
	
	public void removeSound(Sound sound){
		this.sounds.remove(sound);
		soundMaster.removeSound(sound);
	}
	

	public void update(){
		
		for(Sound sound : this.sounds){
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
