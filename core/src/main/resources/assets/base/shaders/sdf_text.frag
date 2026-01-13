#version 400 core
#define DF_PX_RANGE 6
#define STYLE_BOLD_BIT 2u

uniform vec4 u_ColorModifier;
uniform sampler2D u_Textures[16];

in vec2 v_TexCoord;
flat in uint v_TextureIndex;
flat in uint v_FontSize;
flat in vec4 v_TextColor;
flat in vec4 v_OutlineColor;
flat in uint v_StyleFlags;
out vec4 o_Color;

float median(float r, float g, float b);

void main() {
    vec3 msd = texture(u_Textures[v_TextureIndex], v_TexCoord).rgb;
    float dist = median(msd.r, msd.g, msd.b);
    vec2 unitRange = vec2(float(DF_PX_RANGE)) / vec2(textureSize(u_Textures[v_TextureIndex], 0));
    vec2 screenTexSize = 1.0 / fwidth(v_TexCoord);
    float screenPxRange = max(dot(unitRange, screenTexSize), 1.0);
    float screenPxDistance = screenPxRange * (dist - 0.5);
    float alpha = clamp(screenPxDistance + 0.5, 0.0, 1.0);
    o_Color = vec4(v_TextColor.rgb, v_TextColor.a * alpha);

    float width = fwidth(dist);
    if (v_OutlineColor.a != 0) {
        o_Color = mix(v_OutlineColor, v_TextColor, alpha);
        float center = clamp(0.51 - float(v_FontSize) / 32.0 / 10.0, 0.05, 0.5);
        float outlineAlpha = smoothstep(max(center - width, 0.0), min(center + width, 1.0), dist);
        o_Color = vec4(o_Color.rgb, o_Color.a * outlineAlpha);
    }

    if ((v_StyleFlags & STYLE_BOLD_BIT) != 0) {
        float center = clamp(0.5 - float(v_FontSize) / 64.0 / 10.0, 0.05, 0.5);
        float boldAlpha = smoothstep(max(center - width, 0.0), min(center + width, 1.0), dist);
        o_Color = vec4(v_TextColor.rgb, v_TextColor.a * boldAlpha);
    }

    o_Color *= u_ColorModifier;
    if (o_Color.a == 0.0) {
        discard;
    }
}

float median(float r, float g, float b) {
    return max(min(r, g), min(max(r, g), b));
}