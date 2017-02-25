package com.graphics.world;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import com.graphics.Textures;
import com.graphics.world.projectile.Projectile;

public class Enemy extends Entity
{
	private boolean moveLeft = false;
	private boolean moveRight = false;
	private boolean isJumping = false;
	private boolean isUp = false;
	

	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	private int shootingDelay = 5;
	private int shootingCounter = 0;
	private boolean canShoot = true;
	private boolean shoot = false;
	private float shootAngle = 0;
	private float bulletSpeed = 16;
	
	public Enemy(Vector3f position, Texture texture, Vector2f size, Vector2f scale, Vector2f sizeOfSpriteOnSheet) {
		super(position, texture, size, scale, sizeOfSpriteOnSheet);
	}

	public Enemy(Vector3f position, Texture texture, Texture outlineTexture, Vector2f sizeOfTexture,
			int numberOfSpritesX, int numberOfSpritesY, Vector2f scale, Vector2f sizeOfSpriteOnSheet) {
		super(position, texture, outlineTexture, sizeOfTexture, numberOfSpritesX, numberOfSpritesY, scale, sizeOfSpriteOnSheet);
	}
	
	private void movement()
	{
		if(!moveLeft && !moveRight)
		{
			if (velocity.x > 0)
			{
				velocity.x -= DECEL_VALUE;
			} else if (velocity.x < 0)
			{
				velocity.x += DECEL_VALUE;
			}
			if (Math.abs(velocity.x) < DECEL_VALUE)
			{
				velocity.x = 0;
			}
			if (!left)
			{
				shootAngle = 0;
			} else
			{
				shootAngle = 180;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_UP))
			{
				shootAngle = 90;
			}
		}
		
		if(moveRight)
		{
			super.moveRight();
			if(velocity.x > 0)
			{
				shootAngle = 0;
				if (Keyboard.isKeyDown(Keyboard.KEY_UP))
				{
					shootAngle = 45;
				}
			}
		}
		
		if(moveLeft)
		{
			moveLeft();
			if(velocity.x < 0)
			{
				shootAngle = 180;
				if (Keyboard.isKeyDown(Keyboard.KEY_UP))
				{
					shootAngle = 135;
				}
			}
		}
		
		if(!isJumping)
		{
			canJump = true;
			jumping = false;
		}
		
		if(isJumping)
		{
			if (canJump)
			{
				jump();
				canJump = false;
			}
		}
		
		if(isUp)
		{
			if (!left)
				shootAngle = 45;
			else
				shootAngle = 135;
		}
		
		
		if(!shoot)
		{
			if (shootingCounter > shootingDelay)

			{
				shootingCounter = 0;
				canShoot = true;
			}
			else
			{
				shootingCounter++;
			}
		}
		
		if(shoot)
		{
			if (canShoot)
			{
				projectiles.add(new Projectile(new Vector3f(super.position.x, super.position.y, 0), Textures.playerLaser,
						new Vector2f(32, 256), 0, 1, new Vector2f(32, 32), new Vector2f(32, 32), shootAngle,
						bulletSpeed, velocity.x, velocity.y));
			}
			canShoot = false;
		}
		
		if (velocity.x > 0)

		{
			if (Math.abs(super.velocity.x) > MAX_SPEED_X)
			{
				super.numberOfSpritesX = 10;
				super.numberOfSpritesY = 2;
			} else
			{
				super.numberOfSpritesX = 10;
				super.numberOfSpritesY = 1;
			}
			left = false;
		}
		if (velocity.x < 0)

		{
			if (Math.abs(super.velocity.x) > MAX_SPEED_X)
			{
				super.numberOfSpritesX = 10;
				super.numberOfSpritesY = 2;
			} else
			{
				super.numberOfSpritesX = 10;
				super.numberOfSpritesY = 1;
			}
			left = true;
		}
		
		
		if (velocity.x == 0 && velocity.y == 0)

		{
			super.numberOfSpritesX = 0;
			super.numberOfSpritesY = 0;
			super.animateTime = 0;
			super.animSpriteFrameX = 0;
			super.animSpriteFrameY = 0;
		}

		if (Math.abs(velocity.y) < 10 && isInAir)

		{
			super.numberOfSpritesX = 0;
			super.numberOfSpritesY = 4;
			super.animateTime = 0;
			super.animSpriteFrameX = 0;
			super.animSpriteFrameY = 0;
			// System.out.println("Top of Jump");
		} else if (velocity.y < 0 && isInAir)

		{
			super.numberOfSpritesX = 0;
			super.numberOfSpritesY = 3;
			super.animateTime = 0;
			super.animSpriteFrameX = 0;
			super.animSpriteFrameY = 0;
		} else if (velocity.y > 0 && isInAir)

		{

			super.numberOfSpritesX = 0;
			super.numberOfSpritesY = 5;
			super.animateTime = 0;
			super.animSpriteFrameX = 0;
			super.animSpriteFrameY = 0;
		}

		
	}

	public void update(ArrayList<RectangleBox> colliders)
	{
		movement();
		super.update(colliders);
	}

	public boolean isMoveLeft() {
		return moveLeft;
	}

	public void setMoveLeft(boolean moveLeft) {
		this.moveLeft = moveLeft;
	}

	public boolean isMoveRight() {
		return moveRight;
	}

	public void setMoveRight(boolean moveRight) {
		this.moveRight = moveRight;
	}

	public boolean isJumping() {
		return isJumping;
	}

	public void setJumping(boolean isJumping) {
		this.isJumping = isJumping;
	}

	public boolean isUp() {
		return isUp;
	}

	public void setUp(boolean isUp) {
		this.isUp = isUp;
	}

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	/**
	 * Sets the projectiles the enemy is firing
	 * @param projectiles The projectiles the enemy is firing
	 */
	public void setProjectiles(ArrayList<Projectile> projectiles) {
		this.projectiles = projectiles;
	}

	/**
	 * Returns the shooting delay
	 * @return Returns the shooting delay
	 */
	public int getShootingDelay() {
		return shootingDelay;
	}

	/**
	 * Sets the shooting delay
	 * @param shootingDelay The delay between each projecitle
	 */
	public void setShootingDelay(int shootingDelay) {
		this.shootingDelay = shootingDelay;
	}

	/**
	 * Returns the shooting counter
	 * @return Returns the shooting counter
	 */
	public int getShootingCounter() {
		return shootingCounter;
	}

	/**
	 * Sets the shooting counter
	 * @param shootingCounter The new time in the counter
	 */
	public void setShootingCounter(int shootingCounter) {
		this.shootingCounter = shootingCounter;
	}

	/**
	 * Returns true if the enemy can shoot
	 * @return Returns if the enemy can shoot
	 */
	public boolean isCanShoot() {
		return canShoot;
	}

	/**
	 * Sets if the enemy is shooting
	 * @param canShoot The value to set shoot
	 */
	public void setCanShoot(boolean canShoot) {
		this.canShoot = canShoot;
	}

	/**
	 * Returns true if the enemy is shooting
	 * @return Returns true if the enemy is shooting
	 */
	public boolean isShoot() {
		return shoot;
	}

	/**
	 * Sets shoot which if true shoots and if false does not
	 * @param shoot Sets if shooting
	 */
	public void setShoot(boolean shoot) {
		this.shoot = shoot;
	}

	/**
	 * Returns the shoot angle in degrees
	 * @return Returns the shoot angle in degrees
	 */
	public float getShootAngle() {
		return shootAngle;
	}

	/**
	 * Sets shoot angle in degrees
	 * @param shootAngle The shoot angle in degreess
	 */
	public void setShootAngle(float shootAngle) {
		this.shootAngle = shootAngle;
	}

	/**
	 * Returns bullet speed
	 * @return Returns bullet speed
	 */
	public float getBulletSpeed() {
		return bulletSpeed;
	}

	/**
	 * Sets the bulletspeed
	 * @param bulletSpeed The speed of the bullet
	 */
	public void setBulletSpeed(float bulletSpeed) {
		this.bulletSpeed = bulletSpeed;
	}
	
}
