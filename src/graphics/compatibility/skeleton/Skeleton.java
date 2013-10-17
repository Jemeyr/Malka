package graphics.compatibility.skeleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Matrix4f;

public class Skeleton {
	public static final String ROOT = "root";
	
	public List<Animation> animations;
	public Map<String, Bone> bones;
	public Bone root;
	
	public Skeleton(){
		this.bones = new HashMap<String,Bone>();
		this.animations = new ArrayList<Animation>();
		
		Matrix4f identity=  new Matrix4f();
		identity.setIdentity();
		
		this.root = new Bone(ROOT, identity);
		this.bones.put(root.name, root);
	}
	
	public void addAnimation(Animation animation){
		this.animations.add(animation);
	}
	
	public void addRoot(String childName, Matrix4f offset){
		Bone bone = this.root.addChild(childName, offset);
		
		this.bones.put(childName, bone);
	}
	
	public void addChild(String parentName, String childName, Matrix4f offset){
		Bone bone = this.bones.get(parentName).addChild(childName, offset);
		this.bones.put(childName, bone);
	}
	
	

}
