package org.lwjgl.util.input;

import org.lwjgl.input.Controller;

public class ControllerAdapter implements Controller {
   @Override
   public String getName() {
      return "Dummy Controller";
   }

   @Override
   public int getIndex() {
      return 0;
   }

   @Override
   public int getButtonCount() {
      return 0;
   }

   @Override
   public String getButtonName(int index) {
      return "button n/a";
   }

   @Override
   public boolean isButtonPressed(int index) {
      return false;
   }

   @Override
   public void poll() {
   }

   @Override
   public float getPovX() {
      return 0.0F;
   }

   @Override
   public float getPovY() {
      return 0.0F;
   }

   @Override
   public float getDeadZone(int index) {
      return 0.0F;
   }

   @Override
   public void setDeadZone(int index, float zone) {
   }

   @Override
   public int getAxisCount() {
      return 0;
   }

   @Override
   public String getAxisName(int index) {
      return "axis n/a";
   }

   @Override
   public float getAxisValue(int index) {
      return 0.0F;
   }

   @Override
   public float getXAxisValue() {
      return 0.0F;
   }

   @Override
   public float getXAxisDeadZone() {
      return 0.0F;
   }

   @Override
   public void setXAxisDeadZone(float zone) {
   }

   @Override
   public float getYAxisValue() {
      return 0.0F;
   }

   @Override
   public float getYAxisDeadZone() {
      return 0.0F;
   }

   @Override
   public void setYAxisDeadZone(float zone) {
   }

   @Override
   public float getZAxisValue() {
      return 0.0F;
   }

   @Override
   public float getZAxisDeadZone() {
      return 0.0F;
   }

   @Override
   public void setZAxisDeadZone(float zone) {
   }

   @Override
   public float getRXAxisValue() {
      return 0.0F;
   }

   @Override
   public float getRXAxisDeadZone() {
      return 0.0F;
   }

   @Override
   public void setRXAxisDeadZone(float zone) {
   }

   @Override
   public float getRYAxisValue() {
      return 0.0F;
   }

   @Override
   public float getRYAxisDeadZone() {
      return 0.0F;
   }

   @Override
   public void setRYAxisDeadZone(float zone) {
   }

   @Override
   public float getRZAxisValue() {
      return 0.0F;
   }

   @Override
   public float getRZAxisDeadZone() {
      return 0.0F;
   }

   @Override
   public void setRZAxisDeadZone(float zone) {
   }

   @Override
   public int getRumblerCount() {
      return 0;
   }

   @Override
   public String getRumblerName(int index) {
      return "rumber n/a";
   }

   @Override
   public void setRumblerStrength(int index, float strength) {
   }
}
