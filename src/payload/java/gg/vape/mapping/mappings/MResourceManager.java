package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import java.util.Collection;

public class MResourceManager extends Mapping {
   private static final String GET_SELECTED_IDS_METHOD_NAME = "getSelectedIds";
   private final MappingMethod getSelectedIdsMethod;
   private static int packRepositoryControlFlowState;

   public MResourceManager() {
      this(getPackRepositoryControlFlowState());
   }

   private MResourceManager(int controlFlowState) {
      super(MappedClasses.FF);
      Class[] parameterTypes = new Class[0];
      Class<Collection> returnType = Collection.class;
      boolean methodPublic = true;
      String methodName = GET_SELECTED_IDS_METHOD_NAME;
      this.getSelectedIdsMethod = this.Y(methodName, methodPublic, returnType, parameterTypes);
   }

   public static int getPackRepositoryControlFlowState() {
      return packRepositoryControlFlowState;
   }

   public Object getSelectedIds(Object repositoryHandle) {
      return this.getSelectedIdsMethod.invokeObject(repositoryHandle);
   }

   public static int getControlFlowConstant() {
      int controlFlowState = getPackRepositoryControlFlowState();
      return 59;
   }

   public static void setPackRepositoryControlFlowState(int state) {
      packRepositoryControlFlowState = state;
   }

   static {
      setPackRepositoryControlFlowState(0);
   }
}
