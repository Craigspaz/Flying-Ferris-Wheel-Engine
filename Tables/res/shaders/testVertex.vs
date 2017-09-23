#version 330

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texCoord;

out vec2 texCoord0;

out float colorOffset;

uniform float uniformFloat;

void main()
{
	gl_Position = vec4(position, uniformFloat) ;
	texCoord0 = texCoord;
	colorOffset = uniformFloat;
}