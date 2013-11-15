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
		glUniformMatrix4(inverseBindUniform, true, invBuff);
		
		FloatBuffer skelebuf = GLOperations.generatePoseFloatBuffer(skeleton);
		glUniformMatrix4(skeletonUniform, true, skelebuf);
		
		
		this.skeleton = skeleton;
		lastTime = System.currentTimeMillis();
	}
	
	
	
	@Override
	public void draw(long time) {
		
		FloatBuffer skelebuf = GLOperations.generatePoseFloatBuffer(skeleton);
		glUniformMatrix4(skeletonUniform, true, skelebuf);
		
	
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
