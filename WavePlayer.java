package qBert;

import java.util.*;
import sun.audio.*;

import java.io.*;

public class WavePlayer {
	private Map<String,String> sounds = new  HashMap<String,String>(); //map of wave files and their names
	
	public void add(String name,String filename){
		sounds.put(name, filename);
	}
	
	public void play(String name){
		try{
    		InputStream in = new FileInputStream(sounds.get(name));
    		// Create an AudioStream object from the input stream.
    		AudioStream as = new AudioStream(in);         
    		// Use the static class member "player" from class AudioPlayer to play clip.
    		AudioPlayer.player.start(as);  
    	}catch(Exception e){}

	}
}
