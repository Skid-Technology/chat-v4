package gg.vape.mapping.mappings;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import java.util.Map;
import java.util.Map.Entry;

public class MFontManager extends Mapping {
   private MappingField v;
   private MappingMethod U;
   private MappingField q;
   private MappingField i;

   public Object x(Object object, String string) {
      if (object != null && string != null) {
         try {
            Map<?, ?> map = (Map<?, ?>)this.v.getObject(object);
            if (map != null) {
               for (Entry<?, ?> entry : map.entrySet()) {
                  Object k = entry.getKey();
                  if (k != null && k.toString().contains(string)) {
                     return entry.getValue();
                  }
               }
            }
         } catch (Exception var7) {
            Vape.debugLog("[FontManagerHook] Error getting FontSet for " + string + ": " + var7.getMessage());
         }

         return this.x(object);
      } else {
         return null;
      }
   }

   public MFontManager() {
      this(MFontSet.getFontSetControlFlowMarker());
   }

   private MFontManager(String string) {
      super(MappedClasses.lr);
      if (string != null) {
         if (MappedClasses.lr == null) {
            Vape.debugLog("[FontManagerHook] FontManagerClass is null - not available on this version");
         } else {
            Class<Map> clazz = Map.class;
            boolean bl = true;
            String string2 = "fontSets";
            this.v = this.J(string2, bl, clazz);
            Class clazz2 = MappedClasses.D9;
            boolean bl2 = true;
            String string3 = "missingFontSet";
            this.i = this.J(string3, bl2, clazz2);
            Class clazz3 = MappedClasses.Dt;
            boolean bl3 = true;
            String string4 = "textureManager";
            this.q = this.J(string4, bl3, clazz3);
            Class[] classArray = new Class[]{MappedClasses.zC};
            Class clazz4 = MappedClasses.D9;
            boolean bl4 = true;
            String string5 = "getFontSetRaw";
            this.U = this.Y(string5, bl4, clazz4, classArray);
            if (GuiComponent.getLegacyComponentState() == null) {
               MFontSet.setFontSetControlFlowMarker("qmD2Ub");
            }
         }
      } else {
         if (GuiComponent.getLegacyComponentState() == null) {
            MFontSet.setFontSetControlFlowMarker("qmD2Ub");
         }
      }
   }

   public Object u(Object object) {
      if (object == null) {
         return null;
      } else {
         try {
            Map<?, ?> map = (Map<?, ?>)this.v.getObject(object);
            if (map != null) {
               for (Entry<?, ?> entry : map.entrySet()) {
                  Object k = entry.getKey();
                  if (k != null && k.toString().contains("default")) {
                     return entry.getValue();
                  }
               }
            }
         } catch (Exception var6) {
            Vape.debugLog("[FontManagerHook] Error getting default FontSet: " + var6.getMessage());
         }

         return this.x(object);
      }
   }

   private static Exception a(Exception exception) {
      return exception;
   }

   public Object x(Object object) {
      return object != null && this.i != null ? this.i.getObject(object) : null;
   }
}
