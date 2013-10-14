package game;

import graphics.Camera;
import graphics.Model;
import graphics.RenderMaster;
import graphics.RenderMasterFactory;
import graphics.compatibility.CompatibilityModel;
import graphics.compatibility.skeleton.Animation;
import graphics.compatibility.skeleton.Bone;
import graphics.compatibility.skeleton.Pose;
import graphics.compatibility.skeleton.Skeleton;
import input.Controller;
import input.KeyboardController;

import java.util.HashMap;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import sound.Emitter;
import sound.SoundMaster;

public class Game {

	//hacks for demoing stuff to myself
	public static Skeleton skeleton;
	public static Animation animation;
	
	public static HashMap<Bone, Model> awwsure = new HashMap<Bone, Model>();
	
	public static void pose(Bone bone, int alpha, int beta, float amount){
		
		for(Bone b : bone.children){
			List<Pose> poses = animation.getPoses(b.name);
			Matrix4f am = poses.get(alpha).getTransform();
			Matrix4f bm = poses.get(beta).getTransform();
			
			Matrix4f m = new Matrix4f();
			//LERPING MATRICES COMPONENT-WISE IS GREAT! AW YEAH
			m.m00 = am.m00 * amount + bm.m00 * (1.0f - amount);	m.m01 = am.m01 * amount + bm.m01 * (1.0f - amount);	m.m02 = am.m02 * amount + bm.m02 * (1.0f - amount);	m.m03 = am.m03 * amount + bm.m03 * (1.0f - amount);
			m.m10 = am.m10 * amount + bm.m10 * (1.0f - amount);	m.m11 = am.m11 * amount + bm.m11 * (1.0f - amount);	m.m12 = am.m12 * amount + bm.m12 * (1.0f - amount);	m.m13 = am.m13 * amount + bm.m13 * (1.0f - amount);
			m.m20 = am.m20 * amount + bm.m20 * (1.0f - amount);	m.m21 = am.m21 * amount + bm.m21 * (1.0f - amount);	m.m22 = am.m22 * amount + bm.m22 * (1.0f - amount);	m.m23 = am.m23 * amount + bm.m23 * (1.0f - amount);
			m.m30 = am.m30 * amount + bm.m30 * (1.0f - amount);	m.m31 = am.m31 * amount + bm.m31 * (1.0f - amount);	m.m32 = am.m32 * amount + bm.m32 * (1.0f - amount);	m.m33 = am.m33 * amount + bm.m33 * (1.0f - amount);
			
			Model mod = awwsure.get(b);
			mod.hackSetModelMatrix(m);
			pose(b, alpha, beta, amount);
			
		}
	}
	
	public static void addSubmodels(Bone bone, RenderMaster renderMaster, Model parent){
		
		for(Bone b : bone.children){
			Model child = renderMaster.addModel("whatever");
			parent.addChild(child);
			//set transform
			awwsure.put(b, child);
			
			List<Pose> poses = animation.getPoses(b.name);
			Matrix4f m = poses.get(0).getTransform();
			
			//Matrix4f m = b.transform;
			
			
			child.hackSetModelMatrix(m);
			
			float[] green = {0.0f, 1.0f, 0.0f, 1.0f};
			float[] red = {1.0f, 0.0f, 0.0f, 1.0f};
			float[] blue = {0.0f, 0.0f, 1.0f, 1.0f};
			float[] cyan= {0.0f, 1.0f, 1.0f, 1.0f};
			float[] magenta = {1.0f, 0.0f, 1.0f, 1.0f};
			float[] yellow = {1.0f, 1.0f, 0.0f, 1.0f};
			float[] white = {1.0f, 1.0f, 1.0f, 1.0f};
			float[] grey = {0.5f, 0.5f, 0.5f, 1.0f};
			
			
			
			
			if(b.name.matches("[Bb]ody.*")){
				((CompatibilityModel)child).col = green;
			}
			else if (b.name.matches("[Hh]ip.*")){
				((CompatibilityModel)child).col = blue;
			}
			else if (b.name.matches("[Ss]houlder.*"))
			{
				((CompatibilityModel)child).col = cyan;
			}
			else if (b.name.matches("[Hh]ead.*")){
				((CompatibilityModel)child).col = yellow;
			}
			else if (b.name.matches(".*[Aa]rm.*")){
				((CompatibilityModel)child).col = magenta;
			}
			else if (b.name.matches(".*([Tt]high).*")){
				((CompatibilityModel)child).col = white;
			}
			else if (b.name.matches(".*([Ss]hin).*")){
				((CompatibilityModel)child).col = grey;
			}
			
			else
			{
				((CompatibilityModel)child).col = red;
			}
			
			
			addSubmodels(b, renderMaster, child);
			
		}
		
		return;
	}
	
	
	public static void main(String[] args)
	{

		
		RenderMaster renderMaster = RenderMasterFactory.getRenderMaster();

		SoundMaster soundMaster = new SoundMaster();
		soundMaster.loadSound("temp/conti.wav");
		soundMaster.loadSound("temp/conti2.wav");
		
		
		Emitter emitter = new Emitter(soundMaster);
		emitter.update();
		
		Controller controller = new KeyboardController();
		
		Camera camera = renderMaster.getCamera();
		float rotation = 0.0f;
		float height = 0.0f;
		float lent = 15.0f;
		float fov = 90.0f;
		
		Model root = renderMaster.addModel("whatever");
		Quaternion q = new Quaternion();
		q.setFromAxisAngle(new Vector4f(1.0f, 0.0f, 0.0f, -(float)Math.PI/2.0f));
		root.addPosition(new Vector3f(0.0f, -5.0f, 0.0f));
		root.addRotation(q);
		
		
		
		addSubmodels(skeleton.root, renderMaster, root);
		
		float someamount = 1.0f;
		
		
		
		
		while(!Display.isCloseRequested())
		{
			//close if escape is hit
			if(controller.isPressed("QUIT")){
				Display.destroy();
				break;
			}
			
			if(controller.isPressed("RIGHT"))
			{
				rotation += 0.015f;	
			}else if(controller.isPressed("LEFT"))
			{
				rotation -= 0.015f;
			}
			
			if(controller.isPressed("MOVEUP"))
			{
				height += 0.05f;	

				
			}else if(controller.isPressed("MOVEDOWN"))
			{
				height -= 0.05f;
			}
			
			if(controller.isPressed("PLAY")){
				emitter.stopAll();
			}
			
			if(controller.isPressed("DOLLYOUT"))
			{
				fov *= 1.01f;
			    emitter.playSound("temp/conti.wav",fov/90.0f);
			}
			else if(controller.isPressed("DOLLYIN"))
			{
				fov *= 0.99f;
			    emitter.playSound("temp/conti.wav",fov/90.0f);
			}
			
			
			
			if(controller.isPressed("OBJUP"))
			{
				someamount += someamount >=1.0f ? 0.0f : 0.01f;
				pose(skeleton.root, 0, 1, someamount);
				//root.addPosition(new Vector3f(0.0f, 0.0f, 0.05f));
			}
			else if(controller.isPressed("OBJDOWN"))
			{
				someamount -= someamount <= 0.0f ? 0.0f : 0.01f;
				pose(skeleton.root, 0, 1, someamount);
				//root.addPosition(new Vector3f(0.0f, 0.0f, -0.05f));
			}
			
			if(controller.isPressed("OBJLEFT"))
			{
				root.addPosition(new Vector3f(-0.05f, 0.0f, 0.0f));
			}
			else if(controller.isPressed("OBJRIGHT"))
			{
				root.addPosition(new Vector3f(0.05f, 0.0f, 0.0f));
			}
			
			fov = fov > 180.0f ? 180.0f : fov <= 0.0f ? 0.0f : fov;
			
			lent = 15f / (float)Math.atan(fov * 0.0349066);
			
			camera.setPosition(new Vector3f(lent * (float)Math.sin(rotation), 
											height, 
											lent * (float)Math.cos(rotation)));
			
			camera.setFOV(fov);
			
			
			
			renderMaster.render();
			
		}

		soundMaster.exit();
		
	}

}
