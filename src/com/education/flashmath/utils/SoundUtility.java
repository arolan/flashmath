package com.education.flashmath.utils;

import java.io.IOException;
import java.util.HashMap;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class SoundUtility {

	private static SoundPool soundPool;
	private static HashMap soundPoolMap;
	public static void initSounds(Context context) {
		soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 100);
		soundPoolMap = new HashMap(3);
	}

	public static void loadSounds(AssetFileDescriptor badSound, AssetFileDescriptor averageSound, AssetFileDescriptor excellentSound){
		
		// play sound with same right and left volume, with a priority of 1, 
		// zero repeats (i.e play once), and a playback rate of 1f
		soundPoolMap.put( "excellent_result", soundPool.load(excellentSound, 3) );
		soundPoolMap.put( "average_result", soundPool.load(averageSound, 2) );
		soundPoolMap.put( "bad_result", soundPool.load(badSound, 1) );
	}
	
	public static void playSound(Context context, int soundID) {
		float volume = 1.0f;
		Integer savedSoundID = 0;
		
		if (soundID == 1) {
			savedSoundID = (Integer) soundPoolMap.get("bad_result");
		} else if (soundID == 2) {
			savedSoundID = (Integer) soundPoolMap.get("average_result");
		} else if (soundID == 3) {
			savedSoundID = (Integer) soundPoolMap.get("excellent_result");
		}
		
		soundPool.play(savedSoundID, volume, volume, 1, 0, 1f);
	}


}
