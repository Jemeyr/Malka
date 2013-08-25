package sound;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;

public class Sound {

	private int id;
	
	public Sound(int id){

		
		IntBuffer source = BufferUtils.createIntBuffer(1);
		
		AL10.alGenSources(source);
		this.id = source.get(0);

		
		FloatBuffer position = BufferUtils.createFloatBuffer(3).put(
				new float[] { 0.0f, 0.0f, 0.0f });

		FloatBuffer velocity = BufferUtils.createFloatBuffer(3).put(
				new float[] { 0.0f, 0.0f, 0.0f });



		position.flip();
		velocity.flip();
		

		AL10.alSourcei(this.id, AL10.AL_BUFFER, id);
		
		AL10.alSourcef(this.id, AL10.AL_PITCH, 1.0f);
		
		AL10.alSource(this.id, AL10.AL_POSITION, position);
		AL10.alSource(this.id, AL10.AL_VELOCITY, velocity);
		
	}
	
	//sounds can be started or stopped. Volume?
	public void start(){
		AL10.alSourcePlay(this.id);
		
	};
	public void stop(){};

}
