package sound;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;

public class Sound {

	private int id;
	private long length;
	private long startTime;
	private boolean isPlaying;
	
	public Sound(int id, long length){

		this.length = length;
		this.startTime = 0;
		this.isPlaying = false;
		
		IntBuffer source = BufferUtils.createIntBuffer(1);
		
		AL10.alGenSources(source);
		this.id = source.get(0);
		
		
		AL10.alSourcei(this.id, AL10.AL_BUFFER, id);
		
		AL10.alSourcef(this.id, AL10.AL_PITCH, 1.0f);
		
		
	}
	
	protected int getId(){
		return this.id;
	}
	
	//sounds can be started or stopped. Volume?
	public void start(){
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
		if(System.currentTimeMillis() > this.startTime + this.length)
		{
			this.stop();
		}
		
		return this.isPlaying;
	}
	
}
