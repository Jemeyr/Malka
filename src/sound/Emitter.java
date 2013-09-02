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
	
	public void playSound(String key){
		playSound(key, 1.0f);
	}
	
	public void playSound(String key, float pitch){
		Sound sound = soundMaster.playSound(key, pitch);
		
		this.sounds.add(sound);
		
		updateStatus(sound);
	}
	
	
	//what a bad name for a method. 
	private void updateStatus(Sound sound){
		FloatBuffer pos = BufferUtils.createFloatBuffer(3);
		FloatBuffer vel = BufferUtils.createFloatBuffer(3);
		position.store(pos);
		velocity.store(vel);
		
		
		pos.flip();
		vel.flip();

		AL10.alSource(sound.getId(), AL10.AL_POSITION, pos);
		AL10.alSource(sound.getId(), AL10.AL_VELOCITY, vel);
		
	}

	public void stopAll(){
		for(Sound sound : this.sounds){
			sound.stop();
		}
	}
	
	public void update(){
		for(Sound sound : this.sounds){
			updateStatus(sound);
		}
	}
	
	
	
	
	
}
