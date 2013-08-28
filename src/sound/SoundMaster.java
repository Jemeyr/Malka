package sound;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioSystem;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

public class SoundMaster {

	private Map<String, Integer> loadedSounds;
	private Map<String, Long> hackyTimes;

	private Microphone microphone;

	
	public SoundMaster() {
		
		this.loadedSounds = new HashMap<String, Integer>();
		this.hackyTimes = new HashMap<String, Long>();
		
		
		try {
			AL.create(null, 15, 22050, true);
		} catch (Exception e) {
		}
		AL10.alGetError();// clear error bit
		
		this.microphone = Microphone.getMicrophone();
		
	}

	
	private void loadFile(String filename){
		IntBuffer buffer = BufferUtils.createIntBuffer(1);
		AL10.alGenBuffers(buffer);
		
		this.loadedSounds.put(filename, buffer.get(0));
		
		FileInputStream fin = null;
	    BufferedInputStream bin = null;
	    WaveData file = null;
	    try
	    {
	        fin = new FileInputStream(filename);
	        bin = new BufferedInputStream(fin);
	        file = WaveData.create(AudioSystem.getAudioInputStream(bin));
		    
	    }
	    catch(Exception e)
	    {}
	    
	    this.hackyTimes.put(filename, (long)(file.data.limit() * 100 / file.samplerate));
	    
	    
	    
		AL10.alBufferData(loadedSounds.get(filename), file.format, file.data, file.samplerate);
		file.dispose();
	}
	
	
	// Load a sound file to be played intermittently
	public void loadEffect() {

	}

	// load a sound file to be played continuously (looping?)
	public void loadMusic() {

	}
	
	public void loadSound(String filename){

		loadFile(filename);
	}

	public void unloadSound(String name) {
		//todo
	}


	protected Sound addSound(String key) {
		return new Sound(loadedSounds.get(key), hackyTimes.get(key));
	}

	public void removeSound(Sound sound) {

	}
	
	public Microphone getMicrophone(){
		return this.microphone;
	}
	
	public void exit(){
		AL.destroy();
	}
	
}
