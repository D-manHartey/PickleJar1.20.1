#version 150

uniform sampler2D DiffuseSampler;
uniform vec2 OutSize;
uniform vec2 BlurDirection;
uniform float BlurStrength;

in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec2 pixelOffset = (BlurDirection / OutSize) * BlurStrength;

    vec4 color = texture(DiffuseSampler, texCoord) * 0.40;
    color += texture(DiffuseSampler, texCoord + pixelOffset) * 0.30;
    color += texture(DiffuseSampler, texCoord - pixelOffset) * 0.30;

    // Preserve the main framebuffer's alpha for the normal Minecraft blit blend.
    fragColor = color;
}