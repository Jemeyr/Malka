package graphics.compatibility;

import graphics.Model;
import graphics.Shader;
import graphics.compatibility.skeleton.Animation;
import graphics.compatibility.skeleton.Pose;
import graphics.compatibility.skeleton.Skeleton;

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
		
		
		
		//TODO: Uniform for inverse bind matrices onces
		//TODO: Uniform for joint pose every frame
		//TODO: Some function to create the right floats to upload to the uniform
		
		
		this.skeleton = skeleton;
		lastTime = System.currentTimeMillis();
	}
	
	
	@Override
	public void draw(long time) {
		//TODO poses here
		
		

		List<Animation> animations = skeleton.animations;
		Animation first = animations.get(0);
		System.out.println("first: " + first);
		
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
