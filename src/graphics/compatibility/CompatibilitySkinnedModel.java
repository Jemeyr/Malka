package graphics.compatibility;

import static org.lwjgl.opengl.GL20.glUniformMatrix4;

import graphics.GLOperations;
import graphics.Model;
import graphics.Shader;
import graphics.compatibility.skeleton.Animation;
import graphics.compatibility.skeleton.Pose;
import graphics.compatibility.skeleton.Skeleton;

import java.nio.FloatBuffer;
import java.util.List;

import org.lwjgl.BufferUtils;

public class CompatibilitySkinnedModel extends CompatibilityModel{

	
	private Skeleton skeleton;
	private Pose pose;
	private long lastTime;
	
	private int inverseBindUniform;
	private int skeletonUniform;

	protected CompatibilitySkinnedModel(CompatibilityMesh mesh, Shader shader, Skeleton skeleton)
	{	
		super(mesh,shader);
		
		this.inverseBindUniform = shader.getUniforms().get("jointInvBinds");
		this.skeletonUniform = shader.getUniforms().get("joints");
		
		FloatBuffer invBuff = GLOperations.generateInverseBindFloatBuffer(skeleton);

		bufferUniformArray(invBuff, inverseBindUniform);
		
		
		FloatBuffer skelebuf = GLOperations.generatePoseFloatBuffer(skeleton);
		bufferUniformArray(skelebuf, skeletonUniform);
		
		
		
		this.skeleton = skeleton;
		lastTime = System.currentTimeMillis();
	}
	
	//buffers uniform arrays using contiguous graphics memory hack
	private void bufferUniformArray(FloatBuffer skelebuf, int uniform){
//		float somefloats[] = new float[16];
//		FloatBuffer tempBuf = BufferUtils.createFloatBuffer(16);
//		for(int i = 0; i < skelebuf.capacity()/16; i++){
//			tempBuf.clear();
//			skelebuf.get(somefloats, 0, 16);
//			tempBuf.put(somefloats);
//			tempBuf.rewind();
//			
//			glUniformMatrix4(uniform+i, true, tempBuf);
//			
//		}
		glUniformMatrix4(uniform, true, skelebuf);
	}
	
	
	@Override
	public void draw(long time) {
		
		FloatBuffer skelebuf = GLOperations.generatePoseFloatBuffer(skeleton);
		bufferUniformArray(skelebuf, skeletonUniform);
		
	
		//TODO: use animation stuff here
		List<Animation> animations = skeleton.animations;
		Animation first = animations.get(0);
		
		//TODO: actually draw it
		super.draw(time);
		
	}

	@Override
	public void addChild(Model model) {
		// TODO This should add it to a bone in particular! YEAH
		
	}

	@Override
	public void removeChild(Model model) {
		// TODO Do I need to do something here.
		
	}

}
