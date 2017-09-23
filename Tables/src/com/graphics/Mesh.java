package com.graphics;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import com.util.Utils;


/**
 * Modeled off of TheBennyBox on Youtube
 */
public class Mesh
{
	private int vbo;
	private int ibo;
	private int size;
	
	public Mesh()
	{
		vbo = GL15.glGenBuffers();
		ibo = GL15.glGenBuffers();
		size = 0;
	}
	
	public void addVertices(Vertex[] vertices, int[] indices)
	{
		size = indices.length;
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, Utils.createFlippedBuffer(vertices), GL15.GL_STATIC_DRAW);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER,Utils.createFlippedBuffer(indices),GL15.GL_STATIC_DRAW);
	}

	
	public void draw()
	{
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, Vertex.SIZE * 4, 0);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, Vertex.SIZE * 4, 12);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL11.glDrawElements(GL11.GL_QUADS, size, GL11.GL_UNSIGNED_INT,0);

		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(0);
	}
}
