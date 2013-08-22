package sound;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.AudioSystem;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

public class SoundMaster {

	private Map<String, Effect> loadedEffects;
	private Map<String, Music> loadedMusic;

	private List<Effect> effects;
	private List<Music> music;

	private boolean temp = true;

	public void play() {

		if (temp) {
			temp = false;
			startup();

		}

	}

	private int i = 0;
	
	private void startup() {
		try {
			AL.create(null, 15, 22050, true);
		} catch (Exception e) {
		}
		AL10.alGetError();// clear error bit

		IntBuffer buffer = BufferUtils.createIntBuffer(1);
		IntBuffer source = BufferUtils.createIntBuffer(1);

		FloatBuffer posOrigin = BufferUtils.createFloatBuffer(3).put(
				new float[] { 0.0f, 0.0f, 0.0f });
		
		FloatBuffer listenOrigin = BufferUtils.createFloatBuffer(3).put(
				new float[] { 0.0f, 0.0f, 0.0f });

		FloatBuffer velocity = BufferUtils.createFloatBuffer(3).put(
				new float[] { 0.0f, 0.0f, 0.0f });

		

		// position, up vector of microphone
		FloatBuffer orientation = BufferUtils.createFloatBuffer(6).put(
				new float[] { 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f });
		

		AL10.alGenBuffers(buffer);

		posOrigin.flip();
		listenOrigin.flip();
		velocity.flip();
		orientation.flip();
		
		FileInputStream fin = null;
	    BufferedInputStream bin = null;
	    WaveData file = null;
	    try
	    {
	        fin = new FileInputStream("temp/conti.wav");
	        bin = new BufferedInputStream(fin);
	        file = WaveData.create(AudioSystem.getAudioInputStream(bin));
		    
	    }
	    catch(Exception e)
	    {}
	    
	    
		i++;
		
		
		
	//	WaveData file = WaveData.create("/home/jeremy/workspace/Malka/temp/laser.wav");
		AL10.alBufferData(buffer.get(0), file.format, file.data, file.samplerate);
		file.dispose();
		
		AL10.alGenSources(source);

		AL10.alSourcei(source.get(0), AL10.AL_BUFFER, buffer.get(0));
		AL10.alSourcef(source.get(0), AL10.AL_PITCH, 1.0f);
		AL10.alSource(source.get(0), AL10.AL_POSITION, posOrigin);
		
		AL10.alSource(source.get(0), AL10.AL_VELOCITY, velocity);
		
		
		AL10.alListener(AL10.AL_POSITION, listenOrigin);

		posOrigin = BufferUtils.createFloatBuffer(3).put(
				new float[] { 0.0f, 0.0f, 0.0f });
		posOrigin.flip();

		
		AL10.alListener(AL10.AL_VELOCITY, posOrigin);
		AL10.alListener(AL10.AL_ORIENTATION, orientation);

		AL10.alSourcePlay(source.get(0));

	}

	// Load a sound file to be played intermittently
	public void loadEffect() {

	}

	// load a sound file to be played continuously (looping?)
	public void loadMusic() {

	}

	public void unloadEffect(String name) {
		this.loadedEffects.remove(name);
	}

	public void unloadMusic(String name) {
		this.loadedMusic.remove(name);
	}

	// add an instance of an effect to be passed out and played whenever
	public Effect addEffect(String key) {
		return null;
	}

	// add an instance of a music
	public Music addMusic(String key) {
		return null;
	}

	public void removeEffect(Effect effect) {
		this.effects.remove(effect);
	}

	public void removeMusic(Music music) {
		this.music.remove(music);
	}

}
