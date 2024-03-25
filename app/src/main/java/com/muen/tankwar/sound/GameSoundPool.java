package com.muen.tankwar.sound;

import java.util.HashMap;


import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.muen.tankwar.R;
import com.muen.tankwar.tankfight.MainActivity;

public class GameSoundPool {
	private MainActivity mainActivity;
	private SoundPool soundPool;
	private HashMap<Integer,Integer> map;
	public GameSoundPool(MainActivity mainActivity){
		this.mainActivity = mainActivity;
		map = new HashMap<Integer,Integer>();
		soundPool = new SoundPool(8,AudioManager.STREAM_MUSIC,0);
	}
	public void initGameSound(){
		map.put(1, soundPool.load(mainActivity, R.raw.shoot, 1));
	
	}
	//播放音效
	public void playSound(int sound,int loop){
		AudioManager am = (AudioManager)mainActivity.getSystemService(Context.AUDIO_SERVICE);
		float stramVolumeCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
		float stramMaxVolumeCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
		float volume = stramVolumeCurrent/stramMaxVolumeCurrent;
		soundPool.play(map.get(sound), volume, volume, 1, loop, 1.0f);	
	}
}
