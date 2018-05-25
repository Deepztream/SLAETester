#shader vertex
#version 450 core

layout(location = 0) in vec4 position;
layout(location = 1) in vec2 texcoord;
out vec2 v_texcoord;
uniform vec4 u_offset;

void main() {
	gl_Position = position;
	gl_Position.x += u_offset.x;
	gl_Position.y += u_offset.y;
	v_texcoord = texcoord;
}

#shader fragment
#version 450 core

in vec2 v_texcoord;
layout(location = 0) out vec4 colour;
//uniform vec4 u_colour;
uniform sampler2D u_texture;

void main() {
	colour = texture(u_texture, v_texcoord);
}