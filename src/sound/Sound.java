package sound;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;

public class Sound {

	private int id;
	private long length;
	private long startTime;
	private boolean isPlaying;
	private float pitch;
	
	public Sound(int id, long length){

		this.length = length;
		this.startTime = 0;
		this.isPlaying = false;
		this.pitch = 1.25f;
		
		IntBuffer source = BufferUtils.createIntBuffer(1);
		
		AL10.alGenSources(source);
		this.id = source.get(0);
		
		
		AL10.alSourcei(this.id, AL10.AL_BUFFER, id);
		
		AL10.alSourcef(this.id, AL10.AL_PITCH, pitch);
		
		
	}
	
	protected int getId(){
		return this.id;
	}
	
	public void setPitch(float pitch){
		this.pitch = pitch;
		AL10.alSourcef(this.id, AL10.AL_PITCH, pitch);
	}
	
	public void play(float pitch){
		if(this.pitch != pitch){
			setPitch(pitch);
		}
		this.play();
	}
	
	//sounds can be started or stopped. Volume?
	protected void play(){
		AL10.alSourcePlay(this.id);
		this.startTime = System.currentTimeMillis();
		this.isPlaying = true;
	};
	
	public void stop(){
		AL10.alSourceStop(this.id);
		this.startTime = Long.MAX_VALUE;
		this.isPlaying = false;
	};

	public boolean isPlaying(){
		
		if(System.currentTimeMillis() > this.startTime + (long)((float)this.length * (1.0f/this.pitch)))
		{
			this.stop();
		}
		
		return this.isPlaying;
	}
	
	
	
}
