package gg.vape.unmap;

import gg.vape.value.ModeValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModeSelection extends PropertyContainer implements INamed {
   private final String name;
   private static int[] legacyState;
   private ModeValue owningMode;
   public static HashMap<ModeValue, List<ModeSelection>> selectionsByMode = new HashMap<>();

   public ModeSelection(String name) {
      this.name = name;
   }

   public String getSectionSignStrippedName() {
      return this.getName().replace("§", "");
   }

   @Override
   public String getName() {
      return this.name;
   }

   public String getSerializedName() {
      return this.toString();
   }

   public ModeValue getMode() {
      return this.owningMode;
   }

   public void attachToMode(ModeValue modeValue) {
      this.owningMode = modeValue;
      if (!selectionsByMode.containsKey(modeValue)) {
         selectionsByMode.put(modeValue, new ArrayList<>());
      }

      selectionsByMode.get(modeValue).add(this);
   }

   public static void setLegacyState(int[] state) {
      legacyState = state;
   }

   @Override
   public String toString() {
      return this.getSectionSignStrippedName();
   }

   public static ModeSelection findBySerializedName(ModeValue modeValue, String name) {
      for (ModeSelection selection : selectionsByMode.get(modeValue)) {
         if (selection.getSerializedName().equalsIgnoreCase(name)) {
            return selection;
         }
      }

      return null;
   }

   public boolean isSpecialSelection() {
      return false;
   }

   public static int[] getLegacyState() {
      return legacyState;
   }

   static {
      setLegacyState(null);
   }
}
