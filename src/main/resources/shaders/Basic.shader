#shader vertex
#version 450 core

layout(location = 0) in vec4 position;
layout(location = 1) in vec4 c;
layout(location = 2) out vec4 color;

void main(){
    gl_Position = position;
    color = c;
}

#shader fragment
#version 450 core

layout(location = 0) out vec4 color;
layout(location = 2) in vec4 c;

void main(){
    color = c;
}