package graphics.compatibility;

import graphics.Model;
import graphics.Shader;
import graphics.compatibility.skeleton.Animation;
import graphics.compatibility.skeleton.Skeleton;

import java.util.List;

public class CompatibilitySkinnedModel extends CompatibilityModel{

	
	private Skeleton skeleton;
	

	protected CompatibilitySkinnedModel(CompatibilityMesh mesh, Shader shader, Skeleton skeleton)
	{	
		super(mesh,shader);
		
		this.skeleton = skeleton;
		
	}
	
	
	@Override
	public void draw() {
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
