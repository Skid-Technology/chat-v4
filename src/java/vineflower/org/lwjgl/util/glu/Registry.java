package org.lwjgl.util.glu;

public class Registry extends Util {
   private static final String versionString = "1.3";
   private static final String extensionString = "GLU_EXT_nurbs_tessellator GLU_EXT_object_space_tess ";

   public static String gluGetString(int name) {
      if (name == 100800) {
         return "1.3";
      } else {
         return name == 100801 ? "GLU_EXT_nurbs_tessellator GLU_EXT_object_space_tess " : null;
      }
   }

   public static boolean gluCheckExtension(String extName, String extString) {
      return extString != null && extName != null ? extString.indexOf(extName) != -1 : false;
   }
}
