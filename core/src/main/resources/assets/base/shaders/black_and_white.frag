#version 330 core

uniform sampler2D u_Source;
uniform sampler2D u_Input;
uniform int u_Pass;
uniform int u_Radius;
uniform float u_Sigma;


in vec2 v_VpPixelSize;
in vec2 v_VpTexCoord;
out vec4 o_Color;

void main() {
    o_Color = texture(u_Input, v_VpTexCoord);
    if (o_Color.a == 0.0) {
    	discard;
    }

    float grayscale = (o_Color.r + o_Color.g + o_Color.b) / 3.0f;
    o_Color = vec4(grayscale, grayscale, grayscale, o_Color.a);
}