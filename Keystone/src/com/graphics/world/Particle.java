package com.graphics.world;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

import com.graphics.GFX;

public class Particle 
{
	private Vector2f position;
	private Vector2f size;
	private Vector2f velocity;
	private Texture spriteSheet;
	private float animateTimer;
	private float animateTime = 2;
	private float animateSpeed = 2.0f;
	private int numFramesX;
	private int numFramesY;
	private int animFrameX;
	private int animFrameY;
	private boolean flip;
	private Vector2f sizeOfSpriteOnSpriteSheet;
	private Vector2f sizeOfSpriteSheet;
	private boolean isDead = false;
	private boolean loop;
	
	public Particle(Vector2f position, Vector2f size, Texture spriteSheet,int numFramesX, int numFramesY,boolean flip,Vector2f sizeOfSpriteOnSpriteSheet, Vector2f sizeOfSpriteSheet, boolean loop)
	{
		this.position = position;
		this.size = size;
		this.spriteSheet = spriteSheet;
		this.sizeOfSpriteOnSpriteSheet = sizeOfSpriteOnSpriteSheet;
		this.sizeOfSpriteSheet = sizeOfSpriteSheet;
		this.numFramesX = numFramesX;
		this.numFramesY = numFramesY;
		animateTimer = 0.0f;
		velocity = new Vector2f(0,0);
		animFrameX = 0;
		animFrameY = 0;
		this.flip = flip;
		this.loop = loop;
	}
	
	public void update()
	{
		if (animateTimer >= animateTime)
		{
			animFrameX++;
			if (animFrameX >= numFramesX)
			{
				animFrameX = 0;
				if(!loop)
				{
					isDead = true;
				}
			}
			animateTimer = 0.0f;
		}
		else
		{
			animateTimer += animateSpeed;
		}
		System.out.println(animFrameX);
		System.out.println(animateTimer);
	}
	
	public void render()
	{
		Vector2f offset = new Vector2f(((float) (sizeOfSpriteOnSpriteSheet.x * animFrameX)) / sizeOfSpriteSheet.x, (float)(sizeOfSpriteOnSpriteSheet.y * numFramesY)/sizeOfSpriteSheet.y);
		Vector2f sizey = new Vector2f((float) (sizeOfSpriteOnSpriteSheet.x / sizeOfSpriteSheet.x), (float)(sizeOfSpriteOnSpriteSheet.y / sizeOfSpriteSheet.y));
		if(flip)
		{
			GFX.drawSpriteFromSpriteSheetInverse(size.x, size.y, position.x, position.y, spriteSheet,offset,sizey);
		}
		else
		{
			GFX.drawSpriteFromSpriteSheet(size.x, size.y, position.x, position.y, spriteSheet,offset,sizey);
		}
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public Vector2f getSize() {
		return size;
	}

	public void setSize(Vector2f size) {
		this.size = size;
	}

	public Vector2f getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2f velocity) {
		this.velocity = velocity;
	}

	public Texture getSpriteSheet() {
		return spriteSheet;
	}

	public void setSpriteSheet(Texture spriteSheet) {
		this.spriteSheet = spriteSheet;
	}

	public float getAnimateTimer() {
		return animateTimer;
	}

	public void setAnimateTimer(float animateTimer) {
		this.animateTimer = animateTimer;
	}

	public float getAnimateTime() {
		return animateTime;
	}

	public void setAnimateTime(float animateTime) {
		this.animateTime = animateTime;
	}

	public int getNumFramesX() {
		return numFramesX;
	}

	public void setNumFramesX(int numFramesX) {
		this.numFramesX = numFramesX;
	}

	public int getNumFramesY() {
		return numFramesY;
	}

	public void setNumFramesY(int numFramesY) {
		this.numFramesY = numFramesY;
	}

	public int getAnimFrameX() {
		return animFrameX;
	}

	public void setAnimFrameX(int animFrameX) {
		this.animFrameX = animFrameX;
	}

	public int getAnimFrameY() {
		return animFrameY;
	}

	public void setAnimFrameY(int animFrameY) {
		this.animFrameY = animFrameY;
	}

	public boolean isFlip() {
		return flip;
	}

	public void setFlip(boolean flip) {
		this.flip = flip;
	}

	public Vector2f getSizeOfSpriteOnSpriteSheet() {
		return sizeOfSpriteOnSpriteSheet;
	}

	public void setSizeOfSpriteOnSpriteSheet(Vector2f sizeOfSpriteOnSpriteSheet) {
		this.sizeOfSpriteOnSpriteSheet = sizeOfSpriteOnSpriteSheet;
	}

	public Vector2f getSizeOfSpriteSheet() {
		return sizeOfSpriteSheet;
	}

	public void setSizeOfSpriteSheet(Vector2f sizeOfSpriteSheet) {
		this.sizeOfSpriteSheet = sizeOfSpriteSheet;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	public boolean isLoop() {
		return loop;
	}

	public void setLoop(boolean loop) {
		this.loop = loop;
	}
	
	
}
