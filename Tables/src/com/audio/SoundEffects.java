package com.audio;

import org.newdawn.slick.openal.Audio;

import com.graphics.Loader;

public class SoundEffects
{
	public static Audio testEffect;
	
	public SoundEffects()
	{
		testEffect = Loader.loadAudio("Adventure_Meme");
	}
}
