package sound;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;

public class Sound {

	private int id;
	
	public Sound(int id){

		
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
		
	};
	
	public void stop(){};

}
