package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import java.util.Optional;

public class MRegistryLookup extends Mapping {
   private final MappingMethod lookupOrThrowMethod;
   private final MappingMethod getMethod;
   private final MappingMethod lookupMethod;

   public MRegistryLookup() {
      super(MappedClasses.A);
      Class[] lookupOrThrowParameterTypes = new Class[]{MappedClasses.qB};
      Class lookupOrThrowReturnType = MappedClasses.u2;
      boolean lookupOrThrowPublic = true;
      String lookupOrThrowMethodName = "lookupOrThrow";
      this.lookupOrThrowMethod = this.Y(lookupOrThrowMethodName, lookupOrThrowPublic, lookupOrThrowReturnType, lookupOrThrowParameterTypes);
      Class[] lookupParameterTypes = new Class[]{MappedClasses.qB};
      Class<Optional> lookupReturnType = Optional.class;
      boolean lookupPublic = true;
      String lookupMethodName = "lookup";
      this.lookupMethod = this.Y(lookupMethodName, lookupPublic, lookupReturnType, lookupParameterTypes);
      Class[] getParameterTypes = new Class[]{MappedClasses.qB};
      Class<Optional> getReturnType = Optional.class;
      boolean getPublic = true;
      String getMethodName = "get";
      this.getMethod = this.Y(getMethodName, getPublic, getReturnType, getParameterTypes);
      if (MRegistryAccess.getControlFlowMarker() == null) {
         GuiComponent.setLegacyComponentState(new GuiComponent[3]);
      }
   }

   public Optional<Object> lookup(Object providerHandle, Object resourceKeyHandle) {
      return (Optional<Object>)this.lookupMethod.invokeObject(providerHandle, resourceKeyHandle);
   }

   public Optional<Object> get(Object providerHandle, Object resourceKeyHandle) {
      return (Optional<Object>)this.getMethod.invokeObject(providerHandle, resourceKeyHandle);
   }

   public Object lookupOrThrow(Object providerHandle, Object resourceKeyHandle) {
      return this.lookupOrThrowMethod.invokeObject(providerHandle, resourceKeyHandle);
   }
}
