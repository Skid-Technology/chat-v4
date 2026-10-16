package gg.vape.utils.render.shader;

import org.lwjgl.opengl.GL20;

public class ArcShaderProgram extends ShaderProgram {
   private static final String VERTEX_SHADER_SOURCE = "#version 430 compatibility\nout vec2 f_Position;\nvoid main() {\nf_Position = gl_Vertex.xy;\ngl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\ngl_FrontColor = gl_Color;\n}\n";
   private static final String FRAGMENT_SHADER_SOURCE = "#version 430 compatibility\nprecision highp float;\nlayout(location = 0) uniform float u_Inner;\nlayout(location = 1) uniform float u_Radius;\nlayout(location = 2) uniform float u_Feather;\nlayout(location = 3) uniform vec2 u_CenterPos;\nlayout(location = 4) uniform float u_MiddleAngle;\nlayout(location = 5) uniform float u_SweepAngle;\nin vec2 f_Position;\nout vec4 fragColor;\nvoid main() {\nvec2 v = f_Position - u_CenterPos;\nfloat strokeRadius = u_Radius - u_Inner;\nfloat d1 = abs(length(v) - u_Radius) - strokeRadius;\nfloat a1 = smoothstep(-u_Feather, 0.0, d1);\nfloat c = cos(u_SweepAngle * 0.00872664626);\nfloat f = u_MiddleAngle * 0.01745329252;\nvec2 up = vec2(cos(f), sin(f));\nfloat d2 = dot(up, normalize(v)) - c;\nfloat w = u_Feather * fwidth(d2);\nfloat a2 = smoothstep(w * -1.0, w * 1.0, d2);\nfloat a = (1.0 - a1) * a2;\nfragColor = gl_Color * vec4(1.0, 1.0, 1.0, a);\n}\n";

   public ArcShaderProgram() {
      super(
         "#version 430 compatibility\nout vec2 f_Position;\nvoid main() {\nf_Position = gl_Vertex.xy;\ngl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\ngl_FrontColor = gl_Color;\n}\n",
         "#version 430 compatibility\nprecision highp float;\nlayout(location = 0) uniform float u_Inner;\nlayout(location = 1) uniform float u_Radius;\nlayout(location = 2) uniform float u_Feather;\nlayout(location = 3) uniform vec2 u_CenterPos;\nlayout(location = 4) uniform float u_MiddleAngle;\nlayout(location = 5) uniform float u_SweepAngle;\nin vec2 f_Position;\nout vec4 fragColor;\nvoid main() {\nvec2 v = f_Position - u_CenterPos;\nfloat strokeRadius = u_Radius - u_Inner;\nfloat d1 = abs(length(v) - u_Radius) - strokeRadius;\nfloat a1 = smoothstep(-u_Feather, 0.0, d1);\nfloat c = cos(u_SweepAngle * 0.00872664626);\nfloat f = u_MiddleAngle * 0.01745329252;\nvec2 up = vec2(cos(f), sin(f));\nfloat d2 = dot(up, normalize(v)) - c;\nfloat w = u_Feather * fwidth(d2);\nfloat a2 = smoothstep(w * -1.0, w * 1.0, d2);\nfloat a = (1.0 - a1) * a2;\nfragColor = gl_Color * vec4(1.0, 1.0, 1.0, a);\n}\n"
      );
   }

   public void setAngleRange(float startAngle, float sweepAngle) {
      if (sweepAngle != -360.0F) {
         sweepAngle %= 360.0F;
      }

      float middleAngle = startAngle % 360.0F + sweepAngle * 0.5F;
      GL20.glUniform1f(4, middleAngle);
      GL20.glUniform1f(5, sweepAngle);
   }

   public void setCenter(float x, float y) {
      GL20.glUniform2f(3, x, y);
   }

   public void setFeather(float feather) {
      GL20.glUniform1f(2, feather);
   }

   public void setRadiusAndThickness(float radius, float thickness) {
      radius--;
      GL20.glUniform1f(0, radius - thickness);
      GL20.glUniform1f(1, radius);
   }
}
