package aethereal.render;

import java.nio.ByteBuffer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.GlTexture;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL43;
import org.lwjgl.system.MemoryUtil;

public final class RenderComponent191 {
   private static int program = 0;
   private static int vao = 0;
   private static int vbo = 0;
   private static int texId = 0;
   private static int texWidth = 0;
   private static int texHeight = 0;
   private static int helperFbo = 0;
   private static int uRectLoc = -1;
   private static int uScreenSizeLoc = -1;
   private static int uSizeLoc = -1;
   private static int uRadiusLoc = -1;
   private static int uTexLoc = -1;
   private static boolean initFailed = false;

   private RenderComponent191() {
   }

   private static void performAction_2671() {
      if (!initFailed && program == 0) {
         try {
            String string = "#version 150\nout vec2 vPos;\nout vec2 vTexCoord;\nuniform vec4 uRect;\nuniform vec2 uScreenSize;\nvoid main() {\n    vec2 pos[6] = vec2[](\n        vec2(0.0, 0.0),\n        vec2(1.0, 0.0),\n        vec2(1.0, 1.0),\n        vec2(0.0, 0.0),\n        vec2(1.0, 1.0),\n        vec2(0.0, 1.0)\n    );\n    vec2 p = pos[gl_VertexID];\n    vTexCoord = p;\n    vPos = p * uRect.zw;\n    vec2 pixelPos = uRect.xy + p * uRect.zw;\n    vec2 ndc = (pixelPos / uScreenSize) * 2.0 - 1.0;\n    gl_Position = vec4(ndc, 0.0, 1.0);\n}\n";
            String string1 = "#version 150\nin vec2 vPos;\nin vec2 vTexCoord;\nout vec4 fragColor;\nuniform sampler2D uTex;\nuniform vec2 uSize;\nuniform float uRadius;\n\nfloat roundedBoxSDF(vec2 p, vec2 b, float r) {\n    vec2 q = abs(p) - b + r;\n    return min(max(q.x, q.y), 0.0) + length(max(q, 0.0)) - r;\n}\n\nvoid main() {\n    vec2 halfSize = uSize * 0.5;\n    vec2 center = vPos - halfSize;\n    float r = min(uRadius, min(halfSize.x, halfSize.y));\n    float dist = roundedBoxSDF(center, halfSize, r);\n    float alpha = smoothstep(-0.5, 0.5, dist);\n    if (alpha <= 0.0) {\n        discard;\n    }\n    vec4 texColor = texture(uTex, vTexCoord);\n    fragColor = vec4(texColor.rgb, alpha);\n}\n";
            int i = GL20.glCreateShader(35633);
            GL20.glShaderSource(i, string);
            GL20.glCompileShader(i);
            if (GL20.glGetShaderi(i, 35713) == 0) {
               System.err.println("[NeneClient] PanelBlurUtil VS error: " + GL20.glGetShaderInfoLog(i));
               initFailed = true;
               return;
            }

            int j = GL20.glCreateShader(35632);
            GL20.glShaderSource(j, string1);
            GL20.glCompileShader(j);
            if (GL20.glGetShaderi(j, 35713) == 0) {
               System.err.println("[NeneClient] PanelBlurUtil FS error: " + GL20.glGetShaderInfoLog(j));
               initFailed = true;
               return;
            }

            program = GL20.glCreateProgram();
            GL20.glAttachShader(program, i);
            GL20.glAttachShader(program, j);
            GL20.glLinkProgram(program);
            if (GL20.glGetProgrami(program, 35714) == 0) {
               System.err.println("[NeneClient] PanelBlurUtil Program link error: " + GL20.glGetProgramInfoLog(program));
               initFailed = true;
               return;
            }

            GL20.glDeleteShader(i);
            GL20.glDeleteShader(j);
            uRectLoc = GL20.glGetUniformLocation(program, "uRect");
            uScreenSizeLoc = GL20.glGetUniformLocation(program, "uScreenSize");
            uSizeLoc = GL20.glGetUniformLocation(program, "uSize");
            uRadiusLoc = GL20.glGetUniformLocation(program, "uRadius");
            uTexLoc = GL20.glGetUniformLocation(program, "uTex");
            vao = GL30.glGenVertexArrays();
            vbo = GL15.glGenBuffers();
            GL30.glBindVertexArray(vao);
            GL15.glBindBuffer(34962, vbo);
            ByteBuffer byteBuffer = MemoryUtil.memAlloc(24);

            for (int k = 0; k < 6; k++) {
               byteBuffer.putFloat(0.0F);
            }

            byteBuffer.flip();
            GL15.glBufferData(34962, byteBuffer, 35044);
            MemoryUtil.memFree(byteBuffer);
            GL20.glEnableVertexAttribArray(0);
            GL20.glVertexAttribPointer(0, 1, 5126, false, 0, 0L);
            GL30.glBindVertexArray(0);
            texId = GL11.glGenTextures();
            helperFbo = GL30.glGenFramebuffers();
         } catch (Throwable throwable) {
            throwable.printStackTrace();
            initFailed = true;
         }
      }
   }

   public static void performAction_2672(MinecraftClient minecraftClient, float f, float g, float h, float i, float j) {
      if (minecraftClient != null && minecraftClient.gameRenderer != null && minecraftClient.getFramebuffer() != null) {
         performAction_2671();

         try {
            double d = minecraftClient.getWindow().getScaleFactor();
            int k = minecraftClient.getWindow().getFramebufferWidth();
            int l = minecraftClient.getWindow().getFramebufferHeight();
            int m = (int)Math.max(0L, Math.min(k, Math.round(f * d)));
            int n = (int)Math.max(0L, Math.min(l, Math.round(l - (g + i) * d)));
            int o = (int)Math.max(0L, Math.min(k - m, Math.round(h * d)));
            int p = (int)Math.max(0L, Math.min(l - n, Math.round(i * d)));
            if (o <= 0 || p <= 0) {
               return;
            }

            float q = j * (float)d;
            if (!(minecraftClient.getFramebuffer().getColorAttachment() instanceof GlTexture glTexture)) {
               return;
            }

            int r = glTexture.getGlId();
            if (r <= 0) {
               return;
            }

            int s = GL11.glGetInteger(36006);
            int t = GL11.glGetInteger(36010);
            int[] u = new int[4];
            GL11.glGetIntegerv(2978, u);

            try {
               if (!initFailed && program != 0 && texId != 0) {
                  if (o != texWidth || p != texHeight) {
                     texWidth = o;
                     texHeight = p;
                     GL13.glActiveTexture(33984);
                     GL11.glBindTexture(3553, texId);
                     GL11.glTexImage2D(3553, 0, 32856, texWidth, texHeight, 0, 6408, 5121, 0L);
                     GL11.glTexParameteri(3553, 10241, 9728);
                     GL11.glTexParameteri(3553, 10240, 9728);
                     GL11.glTexParameteri(3553, 10242, 33071);
                     GL11.glTexParameteri(3553, 10243, 33071);
                     GL11.glTexParameteri(3553, 33084, 0);
                     GL11.glTexParameteri(3553, 33085, 0);
                  }

                  boolean bl = false;

                  try {
                     GL43.glCopyImageSubData(r, 3553, 0, m, n, 0, texId, 3553, 0, 0, 0, 0, o, p, 1);
                     bl = true;
                  } catch (Throwable throwable) {
                  }

                  if (!bl && helperFbo != 0) {
                     GL30.glBindFramebuffer(36008, helperFbo);
                     GL30.glFramebufferTexture2D(36008, 36064, 3553, r, 0);
                     GL11.glReadBuffer(36064);
                     GL13.glActiveTexture(33984);
                     GL11.glBindTexture(3553, texId);
                     GL11.glCopyTexSubImage2D(3553, 0, 0, 0, m, n, o, p);
                  }
               }

               GL30.glBindFramebuffer(36160, 0);
               GL11.glEnable(3089);
               GL11.glScissor(m, n, o, p);
               minecraftClient.gameRenderer.renderBlur();
               GL11.glDisable(3089);
               if (!initFailed && program != 0 && texId != 0 && vao != 0 && helperFbo != 0) {
                  GL30.glBindFramebuffer(36160, helperFbo);
                  GL30.glFramebufferTexture2D(36160, 36064, 3553, r, 0);
                  GL11.glDrawBuffer(36064);
                  GL11.glViewport(0, 0, k, l);
                  GL20.glUseProgram(program);
                  GL20.glUniform4f(uRectLoc, m, n, o, p);
                  GL20.glUniform2f(uScreenSizeLoc, k, l);
                  GL20.glUniform2f(uSizeLoc, o, p);
                  GL20.glUniform1f(uRadiusLoc, q);
                  GL20.glUniform1i(uTexLoc, 0);
                  GL13.glActiveTexture(33984);
                  GL11.glBindTexture(3553, texId);
                  GL30.glBindVertexArray(vao);
                  boolean bl3 = GL11.glIsEnabled(2929);
                  boolean bl1 = GL11.glIsEnabled(2884);
                  boolean bl2 = GL11.glIsEnabled(3042);
                  GL11.glDisable(2929);
                  GL11.glDisable(2884);
                  GL11.glDisable(3089);
                  GL11.glDepthMask(false);
                  GL11.glEnable(3042);
                  GL14.glBlendFuncSeparate(770, 771, 0, 1);
                  GL11.glDrawArrays(4, 0, 6);
                  if (bl3) {
                     GL11.glEnable(2929);
                  }

                  if (bl1) {
                     GL11.glEnable(2884);
                  }

                  GL11.glDepthMask(true);
                  if (!bl2) {
                     GL11.glDisable(3042);
                  }

                  GL30.glBindVertexArray(0);
                  GL20.glUseProgram(0);
               }
            } finally {
               GL30.glBindFramebuffer(36009, s);
               GL30.glBindFramebuffer(36008, t);
               GL11.glViewport(u[0], u[1], u[2], u[3]);
            }
         } catch (Throwable throwable1) {
            throwable1.printStackTrace();
         }
      }
   }
}
