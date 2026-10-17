package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MIAttributeInstance;
import gg.vape.wrapper.Wrapper;
import java.util.Collection;
import java.util.UUID;

public class AttributeInstance extends Wrapper {
   public AttributeInstance(Object object) {
      super(object);
   }

   public AttributeModifier getModifier(UUID uUID) {
      return new AttributeModifier(MIAttributeInstance.x(vapeInstance.getMappings().Rv, this.I, uUID));
   }

   public void applyModifier(AttributeModifier attributeModifier) {
      MIAttributeInstance.b(vapeInstance.getMappings().Rv, this.I, attributeModifier.getObject());
   }

   public Collection I() {
      return (Collection)(ForgeVersion.MC_1_16_5.d()
         ? MIAttributeInstance.d(vapeInstance.getMappings().Rv, this.I)
         : MIAttributeInstance.h(vapeInstance.getMappings().Rv, this.I));
   }

   public double W() {
      return MIAttributeInstance.I(vapeInstance.getMappings().Rv, this.I);
   }

   public void I(double d) {
      MIAttributeInstance.W(vapeInstance.getMappings().Rv, this.I, d);
   }

   public void J() {
      MIAttributeInstance.V(vapeInstance.getMappings().Rv, this.I);
   }
}
