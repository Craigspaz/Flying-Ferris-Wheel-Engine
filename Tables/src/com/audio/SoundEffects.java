package com.audio;

import org.newdawn.slick.openal.AudioImpl;

import com.graphics.Loader;

public class SoundEffects
{
	public static AudioImpl testEffect;
	public static AudioImpl	explosion;
	
	/**
	 * Initializes sound effects
	 */
	public SoundEffects()
	{
		testEffect = (AudioImpl) Loader.loadAudioWAV("Adventure_Meme");
		explosion = (AudioImpl) Loader.loadAudioWAV("Explosion_1-freesound-tommccann");
	}
}
