package graphics.compatibility.skeleton;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.util.vector.Matrix4f;

public class Skeleton {
	public static final String ROOT = "root";
	
	
	public Map<String, Bone> bones;
	public Bone root;
	
	public Skeleton(){
		this.bones = new HashMap<String,Bone>();
	
		Matrix4f identity=  new Matrix4f();
		identity.setIdentity();
		
		this.root = new Bone(ROOT, identity);
		this.bones.put(root.name, root);
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
