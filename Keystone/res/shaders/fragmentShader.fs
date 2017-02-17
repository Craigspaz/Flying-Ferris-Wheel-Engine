#version 400 core

uniform sampler2D sprite;

void main(void)
{
	vec2 position;
	position.x = gl_FragCoord.x;
	position.y = gl_FragCoord.y;
	vec4 color = texture2D(sprite,position);
	if(color.x == 91/255 && color.y == 185/255 && color.z == 151/255)
	{
		discard;
	}
	gl_FragColor = vec4(1,0,0,1);
}