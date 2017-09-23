package com.graphics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import com.graphics.world.util.StringToInt;



/**
 * Shader class
 * Modeled after TheBennyBox on Youtube
 *
 */
public class Shader
{
	private int shaderProgram;
	private int vertexShader;
	private int fragmentShader;
	
	private ArrayList<StringToInt> uniforms;
	
	
	public Shader(String vertexShaderFilePath, String fragmentShaderFilePath)
	{
		shaderProgram = GL20.glCreateProgram();
		if(shaderProgram == 0)
		{
			System.err.println("Shader program creation failed...");
			System.exit(-1);
		}
		
		uniforms = new ArrayList<StringToInt>();
		
		System.out.println("Creating shaders...");
		String vertexShaderCode = loadShader(vertexShaderFilePath);
		String fragmentShaderCode = loadShader(fragmentShaderFilePath);

		System.out.println("Vertex Shader code: " + vertexShaderCode);
		System.out.println("Fragment Shader code: " + fragmentShaderCode);
		compile(vertexShaderCode,fragmentShaderCode);
	}
	
	public void compile(String vertexShaderCode, String fragmentShaderCode)
	{
		vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		GL20.glShaderSource(vertexShader,vertexShaderCode);
		GL20.glCompileShader(vertexShader);
		if(GL20.glGetShaderi(vertexShader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
		{
			System.err.println("ERROR compiling Vertex shader failed " + GL20.glGetShaderInfoLog(vertexShader,1024));
			System.exit(-1);
		}

		fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
		GL20.glShaderSource(fragmentShader,fragmentShaderCode);
		GL20.glCompileShader(fragmentShader);
		if(GL20.glGetShaderi(fragmentShader, GL20.GL_COMPILE_STATUS) == 0)
		{
			System.err.println("ERROR compiling Fragment shader failed " + GL20.glGetShaderInfoLog(fragmentShader,1024));
			System.exit(-1);
		}
		
		GL20.glAttachShader(shaderProgram, vertexShader);
		GL20.glAttachShader(shaderProgram, fragmentShader);
		GL20.glLinkProgram(shaderProgram);
		if(GL20.glGetProgrami(shaderProgram,GL20.GL_LINK_STATUS) != 1)
		{
			System.err.println("ERROR linking shaders to program..." + GL20.glGetProgramInfoLog(shaderProgram,1024));
			System.exit(-1);
		}
		GL20.glValidateProgram(shaderProgram);
		if(GL20.glGetProgrami(shaderProgram,GL20.GL_VALIDATE_STATUS) != 1)
		{
			System.err.println("ERROR validating link shaders to program..." + GL20.glGetProgramInfoLog(shaderProgram,1024));
			System.exit(-1);
		}
		System.out.println("Compiled shaders...");
	}
	
	public void addUniform(String uniform)
	{
		int uniformLocation = GL20.glGetUniformLocation(shaderProgram, uniform);
		
		if(uniformLocation == -1)
		{
			System.err.println("ERROR: Could not find uniform: " + uniform);
			System.exit(-1);
		}
		System.out.println("Adding uniform: " + uniform + " " + uniformLocation);
		uniforms.add(new StringToInt(uniform,uniformLocation));
	}
	
	public void setUniformi(String uniformName, int value)
	{
		GL20.glUniform1i(StringToInt.getAddressFromString(uniformName, uniforms), value);
	}
	
	public void setUniformf(String uniformName, float value)
	{
		int tmp = StringToInt.getAddressFromString(uniformName, uniforms);
		//System.out.println(tmp);
		GL20.glUniform1f(tmp, value);
	}
	
	public void setUniform3f(String uniformName, Vector3f value)
	{
		GL20.glUniform3f(StringToInt.getAddressFromString(uniformName, uniforms), value.getX(),value.getY(),value.getZ());
	}
	
	public void bindShader()
	{
		GL20.glUseProgram(shaderProgram);
	}
	
	public void unbindShader()
	{
		GL20.glUseProgram(0);
	}
	
	public void deleteShader()
	{
		GL20.glDeleteProgram(shaderProgram);
		GL20.glDeleteShader(vertexShader);
		GL20.glDeleteShader(fragmentShader);
	}
	
	private String loadShader(String shaderFilePath)
	{
		StringBuilder builder = new StringBuilder();
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(shaderFilePath));
			String line;
			while((line = reader.readLine()) != null)
			{
				builder.append(line).append('\n');
			}
			reader.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return builder.toString();
	}
}
