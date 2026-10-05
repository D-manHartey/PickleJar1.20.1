#version 150

uniform sampler2D DiffuseSampler;
uniform vec2 BlurDir;
uniform float Radius;

in vec2 texCoord;
in vec2 oneTexel;

out vec4 fragColor;

void main() {
    vec2 offset = oneTexel * BlurDir * max(Radius, 0.0);

    // A fixed nine-tap Gaussian blur.  The weights add to 1.0, so this
    // preserves normal brightness and alpha instead of adding a dark overlay.
    vec4 color = texture(DiffuseSampler, texCoord) * 0.227027;
    color += texture(DiffuseSampler, texCoord + offset * 1.384615) * 0.316216;
    color += texture(DiffuseSampler, texCoord - offset * 1.384615) * 0.316216;
    color += texture(DiffuseSampler, texCoord + offset * 3.230769) * 0.070270;
    color += texture(DiffuseSampler, texCoord - offset * 3.230769) * 0.070270;

    fragColor = color;
}