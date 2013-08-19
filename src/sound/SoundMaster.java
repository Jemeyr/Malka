package sound;

import java.util.List;
import java.util.Map;

public class SoundMaster {
	
	private Map<String, Effect> loadedEffects;
	private Map<String, Music> loadedMusic;
	
	private List<Effect> effects;
	private List<Music> music;
	
	
	public void play(){
		
	}
	
	
	//Load a sound file to be played intermittently
	public void loadEffect(){
		
	}
	
	//load a sound file to be played continuously (looping?)
	public void loadMusic(){

	}
	
	public void unloadEffect(String name){
		this.loadedEffects.remove(name);
	}
	
	public void unloadMusic(String name){
		this.loadedMusic.remove(name);
	}
	
	//add an instance of an effect to be passed out and played whenever
	public Effect addEffect(String key){
		return null;
	}
	
	//add an instance of a music
	public Music addMusic(String key){
		return null;
	}
	
	public void removeEffect(Effect effect){
		this.effects.remove(effect);
	}

	public void removeMusic(Music music){
		this.music.remove(music);
	}
	
}
