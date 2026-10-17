package gg.vape.utils.render.glu;

import org.lwjgl.opengl.GL11;

public class GluSphere extends GluQuadric {
   public void draw(float radius, int slices, int stacks) {
      boolean normalsEnabled = this.normalMode != 100002;
      if (normalsEnabled) {
         float orientationSign = this.orientation == 100021 ? -1.0F : 1.0F;
         float stackAngleStep = (float) Math.PI / stacks;
         float sliceAngleStep = (float) (Math.PI * 2) / slices;
         if (this.drawStyle == 100012) {
            if (!this.textureCoordinatesEnabled) {
               GL11.glBegin(6);
               GL11.glNormal3f(0.0F, 0.0F, 1.0F);
               GL11.glVertex3f(0.0F, 0.0F, orientationSign * radius);

               for (int sliceIndex = 0; sliceIndex <= slices; sliceIndex++) {
                  float longitudeAngle = sliceIndex == slices ? 0.0F : sliceIndex * sliceAngleStep;
                  float xNormal = -this.sin(longitudeAngle) * this.sin(stackAngleStep);
                  float yNormal = this.cos(longitudeAngle) * this.sin(stackAngleStep);
                  float zNormal = orientationSign * this.cos(stackAngleStep);
                  GL11.glNormal3f(xNormal * orientationSign, yNormal * orientationSign, zNormal * orientationSign);
                  GL11.glVertex3f(xNormal * radius, yNormal * radius, zNormal * radius);
               }

               GL11.glEnd();
            }

            float textureUStep = 1.0F / slices;
            float textureVStep = 1.0F / stacks;
            float textureV = 1.0F;
            int endStack;
            int startStack;
            if (this.textureCoordinatesEnabled) {
               startStack = 0;
               endStack = stacks;
            } else {
               startStack = 1;
               endStack = stacks - 1;
            }

            for (int stackIndex = startStack; stackIndex < endStack; stackIndex++) {
               float latitudeAngle = stackIndex * stackAngleStep;
               GL11.glBegin(8);
               float textureU = 0.0F;

               for (int sliceIndex = 0; sliceIndex <= slices; sliceIndex++) {
                  float longitudeAngle = sliceIndex == slices ? 0.0F : sliceIndex * sliceAngleStep;
                  float xNormal = -this.sin(longitudeAngle) * this.sin(latitudeAngle);
                  float yNormal = this.cos(longitudeAngle) * this.sin(latitudeAngle);
                  float zNormal = orientationSign * this.cos(latitudeAngle);
                  GL11.glNormal3f(xNormal * orientationSign, yNormal * orientationSign, zNormal * orientationSign);
                  this.emitTextureCoordinate(textureU, textureV);
                  GL11.glVertex3f(xNormal * radius, yNormal * radius, zNormal * radius);
                  xNormal = -this.sin(longitudeAngle) * this.sin(latitudeAngle + stackAngleStep);
                  yNormal = this.cos(longitudeAngle) * this.sin(latitudeAngle + stackAngleStep);
                  zNormal = orientationSign * this.cos(latitudeAngle + stackAngleStep);
                  GL11.glNormal3f(xNormal * orientationSign, yNormal * orientationSign, zNormal * orientationSign);
                  this.emitTextureCoordinate(textureU, textureV - textureVStep);
                  textureU += textureUStep;
                  GL11.glVertex3f(xNormal * radius, yNormal * radius, zNormal * radius);
               }

               GL11.glEnd();
               textureV -= textureVStep;
            }

            if (!this.textureCoordinatesEnabled) {
               GL11.glBegin(6);
               GL11.glNormal3f(0.0F, 0.0F, -1.0F);
               GL11.glVertex3f(0.0F, 0.0F, -radius * orientationSign);
               float latitudeAngle = (float) Math.PI - stackAngleStep;
               float textureU = 1.0F;

               for (int sliceIndex = slices; sliceIndex >= 0; sliceIndex--) {
                  float longitudeAngle = sliceIndex == slices ? 0.0F : sliceIndex * sliceAngleStep;
                  float xNormal = -this.sin(longitudeAngle) * this.sin(latitudeAngle);
                  float yNormal = this.cos(longitudeAngle) * this.sin(latitudeAngle);
                  float zNormal = orientationSign * this.cos(latitudeAngle);
                  GL11.glNormal3f(xNormal * orientationSign, yNormal * orientationSign, zNormal * orientationSign);
                  textureU -= textureUStep;
                  GL11.glVertex3f(xNormal * radius, yNormal * radius, zNormal * radius);
               }

               GL11.glEnd();
            }
         } else if (this.drawStyle != 100011 && this.drawStyle != 100013) {
            if (this.drawStyle == 100010) {
               GL11.glBegin(0);
               GL11.glNormal3f(0.0F, 0.0F, orientationSign);
               GL11.glVertex3f(0.0F, 0.0F, radius);
               GL11.glNormal3f(0.0F, 0.0F, -orientationSign);
               GL11.glVertex3f(0.0F, 0.0F, -radius);

               for (int stackIndex = 1; stackIndex < stacks - 1; stackIndex++) {
                  float latitudeAngle = stackIndex * stackAngleStep;

                  for (int sliceIndex = 0; sliceIndex < slices; sliceIndex++) {
                     float longitudeAngle = sliceIndex * sliceAngleStep;
                     float xNormal = this.cos(longitudeAngle) * this.sin(latitudeAngle);
                     float yNormal = this.sin(longitudeAngle) * this.sin(latitudeAngle);
                     float zNormal = this.cos(latitudeAngle);
                     GL11.glNormal3f(xNormal * orientationSign, yNormal * orientationSign, zNormal * orientationSign);
                     GL11.glVertex3f(xNormal * radius, yNormal * radius, zNormal * radius);
                  }
               }

               GL11.glEnd();
            }
         } else {
            for (int stackIndex = 1; stackIndex < stacks; stackIndex++) {
               float latitudeAngle = stackIndex * stackAngleStep;
               GL11.glBegin(2);

               for (int sliceIndex = 0; sliceIndex < slices; sliceIndex++) {
                  float longitudeAngle = sliceIndex * sliceAngleStep;
                  float xNormal = this.cos(longitudeAngle) * this.sin(latitudeAngle);
                  float yNormal = this.sin(longitudeAngle) * this.sin(latitudeAngle);
                  float zNormal = this.cos(latitudeAngle);
                  GL11.glNormal3f(xNormal * orientationSign, yNormal * orientationSign, zNormal * orientationSign);
                  GL11.glVertex3f(xNormal * radius, yNormal * radius, zNormal * radius);
               }

               GL11.glEnd();
            }

            for (int sliceIndex = 0; sliceIndex < slices; sliceIndex++) {
               float longitudeAngle = sliceIndex * sliceAngleStep;
               GL11.glBegin(3);

               for (int var93 = 0; var93 <= stacks; var93++) {
                  float latitudeAngle = var93 * stackAngleStep;
                  float xNormal = this.cos(longitudeAngle) * this.sin(latitudeAngle);
                  float yNormal = this.sin(longitudeAngle) * this.sin(latitudeAngle);
                  float zNormal = this.cos(latitudeAngle);
                  GL11.glNormal3f(xNormal * orientationSign, yNormal * orientationSign, zNormal * orientationSign);
                  GL11.glVertex3f(xNormal * radius, yNormal * radius, zNormal * radius);
               }

               GL11.glEnd();
            }
         }
      } else {
         float orientationSign = this.orientation == 100021 ? -1.0F : 1.0F;
         float stackAngleStep = (float) Math.PI / stacks;
         float sliceAngleStep = (float) (Math.PI * 2) / slices;
         if (this.drawStyle == 100012) {
            if (!this.textureCoordinatesEnabled) {
               GL11.glBegin(6);
               GL11.glNormal3f(0.0F, 0.0F, 1.0F);
               GL11.glVertex3f(0.0F, 0.0F, orientationSign * radius);

               for (int sliceIndex = 0; sliceIndex <= slices; sliceIndex++) {
                  float longitudeAngle = sliceIndex == slices ? 0.0F : sliceIndex * sliceAngleStep;
                  float xNormal = -this.sin(longitudeAngle) * this.sin(stackAngleStep);
                  float yNormal = this.cos(longitudeAngle) * this.sin(stackAngleStep);
                  float zNormal = orientationSign * this.cos(stackAngleStep);
                  GL11.glVertex3f(xNormal * radius, yNormal * radius, zNormal * radius);
               }

               GL11.glEnd();
            }

            float textureUStepx = 1.0F / slices;
            float textureVStepx = 1.0F / stacks;
            float textureVx = 1.0F;
            int endStackx;
            int startStackx;
            if (this.textureCoordinatesEnabled) {
               startStackx = 0;
               endStackx = stacks;
            } else {
               startStackx = 1;
               endStackx = stacks - 1;
            }

            for (int stackIndex = startStackx; stackIndex < endStackx; stackIndex++) {
               float latitudeAngle = stackIndex * stackAngleStep;
               GL11.glBegin(8);
               float textureU = 0.0F;

               for (int sliceIndex = 0; sliceIndex <= slices; sliceIndex++) {
                  float longitudeAngle = sliceIndex == slices ? 0.0F : sliceIndex * sliceAngleStep;
                  float xNormal = -this.sin(longitudeAngle) * this.sin(latitudeAngle);
                  float yNormal = this.cos(longitudeAngle) * this.sin(latitudeAngle);
                  float zNormal = orientationSign * this.cos(latitudeAngle);
                  this.emitTextureCoordinate(textureU, textureVx);
                  GL11.glVertex3f(xNormal * radius, yNormal * radius, zNormal * radius);
                  xNormal = -this.sin(longitudeAngle) * this.sin(latitudeAngle + stackAngleStep);
                  yNormal = this.cos(longitudeAngle) * this.sin(latitudeAngle + stackAngleStep);
                  zNormal = orientationSign * this.cos(latitudeAngle + stackAngleStep);
                  this.emitTextureCoordinate(textureU, textureVx - textureVStepx);
                  textureU += textureUStepx;
                  GL11.glVertex3f(xNormal * radius, yNormal * radius, zNormal * radius);
               }

               GL11.glEnd();
               textureVx -= textureVStepx;
            }

            if (!this.textureCoordinatesEnabled) {
               GL11.glBegin(6);
               GL11.glNormal3f(0.0F, 0.0F, -1.0F);
               GL11.glVertex3f(0.0F, 0.0F, -radius * orientationSign);
               float latitudeAngle = (float) Math.PI - stackAngleStep;
               float textureU = 1.0F;

               for (int sliceIndex = slices; sliceIndex >= 0; sliceIndex--) {
                  float longitudeAngle = sliceIndex == slices ? 0.0F : sliceIndex * sliceAngleStep;
                  float xNormal = -this.sin(longitudeAngle) * this.sin(latitudeAngle);
                  float yNormal = this.cos(longitudeAngle) * this.sin(latitudeAngle);
                  float zNormal = orientationSign * this.cos(latitudeAngle);
                  textureU -= textureUStepx;
                  GL11.glVertex3f(xNormal * radius, yNormal * radius, zNormal * radius);
               }

               GL11.glEnd();
            }
         } else if (this.drawStyle != 100011 && this.drawStyle != 100013) {
            if (this.drawStyle == 100010) {
               GL11.glBegin(0);
               GL11.glVertex3f(0.0F, 0.0F, radius);
               GL11.glVertex3f(0.0F, 0.0F, -radius);

               for (int stackIndex = 1; stackIndex < stacks - 1; stackIndex++) {
                  float latitudeAngle = stackIndex * stackAngleStep;

                  for (int sliceIndex = 0; sliceIndex < slices; sliceIndex++) {
                     float longitudeAngle = sliceIndex * sliceAngleStep;
                     float xNormal = this.cos(longitudeAngle) * this.sin(latitudeAngle);
                     float yNormal = this.sin(longitudeAngle) * this.sin(latitudeAngle);
                     float zNormal = this.cos(latitudeAngle);
                     GL11.glVertex3f(xNormal * radius, yNormal * radius, zNormal * radius);
                  }
               }

               GL11.glEnd();
            }
         } else {
            for (int stackIndex = 1; stackIndex < stacks; stackIndex++) {
               float latitudeAngle = stackIndex * stackAngleStep;
               GL11.glBegin(2);

               for (int sliceIndex = 0; sliceIndex < slices; sliceIndex++) {
                  float longitudeAngle = sliceIndex * sliceAngleStep;
                  float xNormal = this.cos(longitudeAngle) * this.sin(latitudeAngle);
                  float yNormal = this.sin(longitudeAngle) * this.sin(latitudeAngle);
                  float zNormal = this.cos(latitudeAngle);
                  GL11.glVertex3f(xNormal * radius, yNormal * radius, zNormal * radius);
               }

               GL11.glEnd();
            }

            for (int sliceIndex = 0; sliceIndex < slices; sliceIndex++) {
               float longitudeAngle = sliceIndex * sliceAngleStep;
               GL11.glBegin(3);

               for (int var86 = 0; var86 <= stacks; var86++) {
                  float latitudeAngle = var86 * stackAngleStep;
                  float xNormal = this.cos(longitudeAngle) * this.sin(latitudeAngle);
                  float yNormal = this.sin(longitudeAngle) * this.sin(latitudeAngle);
                  float zNormal = this.cos(latitudeAngle);
                  GL11.glVertex3f(xNormal * radius, yNormal * radius, zNormal * radius);
               }

               GL11.glEnd();
            }
         }
      }
   }
}
