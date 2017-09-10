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
		testEffect = (AudioImpl) Loader.loadAudio("Adventure_Meme");
		explosion = (AudioImpl) Loader.loadAudio("Explosion_1-freesound-tommccann");
	}
}
