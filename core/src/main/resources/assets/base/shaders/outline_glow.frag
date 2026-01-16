#version 330 core

uniform sampler2D u_Source;
uniform sampler2D u_Input;
uniform int u_Pass;
uniform int u_Width;

in vec2 v_VpPixelSize;
in vec2 v_VpTexCoord;
out vec4 o_Color;

int doubleWidth = u_Width * 2;

int decodeDistance(float alpha);
float encodeDistance(int dist);

void main() {
    if (u_Pass == 0) { /* x axis pass */
        vec3 color = vec3(0.0);
        int xDistance = 0;
        vec4 currentPixel = texture(u_Input, v_VpTexCoord);
        if (currentPixel.a == 0.0) {
            for (int i = -u_Width; i <= u_Width; i++) {
                vec4 inputPixel = texture(u_Input, v_VpTexCoord + vec2(float(i), 0.0) * v_VpPixelSize);
                int xDist = abs(i);
                if (inputPixel.a != 0.0 && (xDist < xDistance || xDistance == 0)) {
                    color = inputPixel.rgb;
                    xDistance = xDist;
                }
            }
        }

        if (xDistance != 0) {
            o_Color = vec4(color, encodeDistance(xDistance));
        } else {
            vec4 inputPixel = texture(u_Input, v_VpTexCoord);
            if (inputPixel.a != 0.0) {
                o_Color = vec4(inputPixel.rgb, encodeDistance(0));
            } else {
                o_Color = vec4(0.0); // AMD Mesa driver workaround
                discard;
            }
        }
    } else { /* y axis combining pass */
        vec3 color = vec3(0.0);
        float xyDistance = 0.0;
        vec4 currentPixel = texture(u_Source, v_VpTexCoord);
        if (currentPixel.a == 0.0 || decodeDistance(currentPixel.a) > 0) {
            for (int i = -u_Width; i <= u_Width; i++) {
                vec4 inputPixel = texture(u_Source, v_VpTexCoord + vec2(0.0, float(i)) * v_VpPixelSize);
                float xDist = float(decodeDistance(inputPixel.a));
                float yDist = float(abs(i));
                float xyDist = yDist;
                if (xDist > 0.0) {
                    xyDist = sqrt(xDist * xDist + yDist * yDist);
                }
                if (inputPixel.a != 0.0 && (xyDist < xyDistance || xyDistance == 0.0)) {
                    color = inputPixel.rgb;
                    xyDistance = xyDist;
                }
            }
        }

        float dist = abs(xyDistance);
        if (dist > 0.0) {
            float t = clamp(dist / u_Width, 0.0, 1.0);
            float alpha = 1.0 - smoothstep(0.0, 1.0, t);
            if (alpha <= 0.0) {
                discard;
            }
            o_Color = vec4(color, alpha);
        } else {
            discard;
        }
    }
}

int decodeDistance(float alpha) {
    if (alpha != 0.0) {
        return int(round(alpha * 255.0)) - doubleWidth - 1;
    } else {
        return 0;
    }
}

float encodeDistance(int dist) {
    return float(dist + doubleWidth + 1) / 255.0;
}
