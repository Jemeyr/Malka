package sound;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Map;

import javax.sound.sampled.AudioSystem;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

public class SoundMaster {

	private Map<String, Sound> loadedSounds;
	private IntBuffer buffer;
	private int nextSoundIndex = 0;

	private boolean temp = true;

	public void play() {

		if (temp) {
			temp = false;
			startup();

		}

	}

	
	private void loadFile(String filename){
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
	    
	    
		
		AL10.alBufferData(buffer.get(nextSoundIndex++), file.format, file.data, file.samplerate);
		file.dispose();
	}
	
	private void startup() {
		try {
			AL.create(null, 15, 22050, true);
		} catch (Exception e) {
		}
		AL10.alGetError();// clear error bit
		
		
		this.buffer = BufferUtils.createIntBuffer(10);
		AL10.alGenBuffers(buffer);


		FloatBuffer position = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f });
		FloatBuffer velocity= BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f });

		// position, up vector of microphone
		FloatBuffer orientation = BufferUtils.createFloatBuffer(6).put(
				new float[] { 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f });
		


		position.flip();
		velocity.flip();

		orientation.flip();
		
		
		
		AL10.alListener(AL10.AL_POSITION, position);

		
		AL10.alListener(AL10.AL_VELOCITY, velocity);
		AL10.alListener(AL10.AL_ORIENTATION, orientation);
		
		
		loadFile("temp/conti.wav");
		
	}

	// Load a sound file to be played intermittently
	public void loadEffect() {

	}

	// load a sound file to be played continuously (looping?)
	public void loadMusic() {

	}

	public void unloadSound(String name) {
		this.loadedSounds.remove(name);
	}


	public Sound addSound(String key) {
		return new Sound(buffer.get(0));
	}

	public void removeSound(Sound sound) {

	}
	
}
