package sound;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.AudioSystem;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

public class SoundMaster {

	private Map<String, SoundData> loadedSounds;
	private Map<String, List<Sound>> instances;

	private Microphone microphone;

	
	public SoundMaster() {
		
		this.loadedSounds = new HashMap<String, SoundData>();
		this.instances = new HashMap<String, List<Sound>>();
		
		
		try {
			AL.create(null, 15, 22050, true);
		} catch (Exception e) {
		}
		AL10.alGetError();// clear error bit
		
		this.microphone = Microphone.getMicrophone();
		
	}

	
	private void loadFile(String filename){
		if(loadedSounds.containsKey(filename)){
			return;
		}
		
		IntBuffer buffer = BufferUtils.createIntBuffer(1);
		AL10.alGenBuffers(buffer);
		
		int fileId = buffer.get(0);
		
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
	    
	    SoundData soundData = new SoundData(fileId, (long)(file.data.limit() * 100 / file.samplerate));
	    
	    this.loadedSounds.put(filename, soundData);
		
	    
	    
		AL10.alBufferData(loadedSounds.get(filename).getId(), file.format, file.data, file.samplerate);
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

	
	protected Sound playSound(String key){
		return playSound(key, 1.0f);
	}

	protected Sound playSound(String key, float pitch) {

		Sound sound = null;
		
		if(this.instances.get(key) == null){
			//create a list for this key, and a sound
			List<Sound> soundList = new ArrayList<Sound>();
			sound = new Sound(loadedSounds.get(key));

			//put the sound in the list and the list in the map
			soundList.add(sound);
			this.instances.put(key, soundList);
			
			System.out.println("Adding a sound. Soundcount for \""+key+"\": " + this.instances.get(key).size());
		}
		else
		{
			//if there is an entry, then check if anyone in the list is playing
			List<Sound> soundList = this.instances.get(key);
			boolean addSound = true;
			for(Sound currSound : soundList){
				if(!currSound.isPlaying())
				{
					System.out.println("reusing a sound");
					currSound.play(pitch);
					addSound = false;
					sound = currSound;
					break;
				}
			}
			
			if(addSound){

				System.out.println("Adding a sound. Soundcount for \""+key+"\": " + this.instances.get(key).size());
				sound = new Sound(loadedSounds.get(key));
				soundList.add(sound);

				//play the sound
				sound.play(pitch);	
			}
		}

		return sound;
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
