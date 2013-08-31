package sound;

public class SoundData {
	private int id;
	private long length;
	
	protected SoundData (int id, long length){
		this.id = id;
		this.length = length;
	}
	
	protected int getId(){
		return id;
	}
	
	protected long getLength(){
		return length;
	}
	

}
