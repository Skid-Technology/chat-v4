package gg.vape.mapping;

@Deprecated
public class DeprecatedNoopMappingTask extends JavassistMappingTask {
   @Override
   public void transform() {
   }

   public DeprecatedNoopMappingTask() {
      super(MappedClasses.DC);
   }
}
