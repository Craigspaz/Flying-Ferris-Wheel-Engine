package com.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.lwjgl.util.vector.Vector2f;

import com.graphics.world.Camera;
import com.graphics.world.DialogBox;

public class GameCodeTester
{
	
	@Test
	public void testCameraUpdate()
	{
		Camera camera = new Camera(new Vector2f(0,0),new Vector2f(800,600));
		camera.update();
		assertEquals(0,(int)camera.getPosition().getX());
		assertEquals(0,(int)camera.getPosition().getY());
	}
	
	@Test (expected=NullPointerException.class,timeout=2000)
	public void testCameraCreation()
	{
		Camera camera = new Camera(null,null);
	}
	
	@Test (expected=NullPointerException.class,timeout=2000)
	public void testCameraCreation1()
	{
		Camera camera = new Camera(null, new Vector2f(32,32));
	}
	
	@Test (expected=NullPointerException.class,timeout=2000)
	public void testCameraCreation2()
	{
		Camera camera = new Camera(new Vector2f(32,32), null);
	}
	
	@Test
	public void testCameraMoveRight()
	{
		Camera camera = new Camera(new Vector2f(0,0),new Vector2f(800,600));
		camera.moveRight();
		assertEquals(camera.getMovementSpeed(),camera.getPosition().getX(),0.00001);
	}
	
	@Test
	public void testCameraMoveLeft()
	{
		Camera camera = new Camera(new Vector2f(0,0),new Vector2f(800,600));
		camera.moveLeft();
		assertEquals(-camera.getMovementSpeed(),camera.getPosition().getX(),0.00001);
	}
	
	@Test
	public void testCameraMoveUp()
	{
		Camera camera = new Camera(new Vector2f(0,0),new Vector2f(800,600));
		camera.moveUp();
		assertEquals(-camera.getMovementSpeed(),camera.getPosition().getY(),0.00001);
	}
	
	@Test
	public void testCameraMoveDown()
	{
		Camera camera = new Camera(new Vector2f(0,0),new Vector2f(800,600));
		camera.moveDown();
		assertEquals(camera.getMovementSpeed(),camera.getPosition().getY(),0.00001);
	}
	
	@Test
	public void testDialogBoxActivate()
	{
		DialogBox box = new DialogBox(new String[]{"test"},"Test Name",0,0);
		box.activate();
		assertTrue(box.active());
	}
}
