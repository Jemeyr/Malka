package sound;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.vector.Vector3f;

public class Microphone {

	//Singleton Class to do openal listener wrapping
	
	private static Microphone instance;

	protected static Microphone getMicrophone(){
		if(instance == null){
			return new Microphone();
		}
		return instance;
	}
	
	//actual class stuff starts here
	
	private Vector3f position;
	private Vector3f velocity;
	private Vector3f up;
	private Vector3f forward;
	
	
	
	private Microphone(){
		
		
		this.position = new Vector3f(0.0f, 0.0f, 0.0f);
		this.velocity= new Vector3f(0.0f, 0.0f, 0.0f);
		this.up = new Vector3f(0.0f, 1.0f, 0.0f);
		this.forward = new Vector3f(0.0f, 0.0f, 0.0f);
	}
	
	
	public void update(){
		
		FloatBuffer pos = BufferUtils.createFloatBuffer(3);
		FloatBuffer vel = BufferUtils.createFloatBuffer(3);
		// position, up vector of microphone
		FloatBuffer orientation = BufferUtils.createFloatBuffer(6).put(
				new float[] { forward.x, forward.y, forward.z, up.x, up.y, up.z});

		position.store(pos);
		velocity.store(vel);
		
		pos.flip();
		vel.flip();

		orientation.flip();


		AL10.alListener(AL10.AL_POSITION, pos);

		
		AL10.alListener(AL10.AL_VELOCITY, vel);
		AL10.alListener(AL10.AL_ORIENTATION, orientation);	
	
	}
	
	
}
