package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.Locale;

public class MTextFormatting extends Mapping {
   private MappingMethod getColorMethod;
   private MappingMethod getNameMethod;
   private MappingMethod getByNameMethod;

   public Integer getColor(Object formatting) {
      if (formatting == null) {
         return null;
      } else if (!ForgeVersion.MC_26_2.d()) {
         return (Integer)this.getColorMethod.invokeObject(formatting);
      } else {
         int n3 = ((Enum)formatting).ordinal();
         int[] nArray = new int[]{
            0, 170, 43520, 43690, 11141120, 11141290, 16755200, 11184810, 5592405, 5592575, 5635925, 5636095, 16733525, 16733695, 16777045, 16777215
         };
         return n3 >= 0 && n3 < nArray.length ? nArray[n3] : null;
      }
   }

   public MTextFormatting() {
      this(BlockData.W());
   }

   private MTextFormatting(String[] stringArray) {
      super(MappedClasses.l5);
      if (ForgeVersion.MC_1_21_4.d() && ForgeVersion.MC_26_2.v()) {
         this.getNameMethod = this.Y("getName", true, String.class);
         this.getColorMethod = this.Y("getColor", true, Integer.class);
         this.getByNameMethod = this.registerStaticMethod("getByName", true, MappedClasses.l5, String.class);
      } else if (ForgeVersion.MC_1_21_4.v()) {
         this.getByNameMethod = this.registerStaticMethod("getByName", true, MappedClasses.l5, String.class);
      }
   }

   public Object getByName(String name) {
      if (name == null) {
         return null;
      } else if (ForgeVersion.MC_26_2.d()) {
         String string2 = name.toUpperCase(Locale.ROOT).replace(' ', '_').replace('-', '_');

         for (Object t3 : MappedClasses.l5.getEnumConstants()) {
            if (((Enum)t3).name().equals(string2)) {
               return t3;
            }
         }

         return null;
      } else {
         return this.getByNameMethod.invokeObject(null, name);
      }
   }

   public String getName(Object formatting) {
      if (formatting == null) {
         return null;
      } else {
         return ForgeVersion.MC_26_2.d() ? ((Enum)formatting).name().toLowerCase(Locale.ROOT) : (String)this.getNameMethod.invokeObject(formatting);
      }
   }
}
