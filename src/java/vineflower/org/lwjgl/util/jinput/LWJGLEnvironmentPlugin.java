package org.lwjgl.util.jinput;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.util.plugins.Plugin;

public class LWJGLEnvironmentPlugin extends ControllerEnvironment implements Plugin {
   private final Controller[] controllers = new Controller[]{new LWJGLKeyboard(), new LWJGLMouse()};

   @Override
   public Controller[] getControllers() {
      return this.controllers;
   }

   @Override
   public boolean isSupported() {
      return true;
   }
}
