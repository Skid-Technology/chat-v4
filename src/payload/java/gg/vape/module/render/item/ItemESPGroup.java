package gg.vape.module.render.item;

import gg.vape.module.render.ItemESP;
import gg.vape.wrapper.impl.EntityItem;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ItemStack;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItemESPGroup {
   public double currentZ;
   public double previousX;
   public double previousY;
   public double currentX;
   public double previousZ;
   private final List<Object> itemEntities;
   private final List<ItemStack> stackedItems;
   public double currentY;
   private final ItemESP parent;

   public List<Object> getEntityHandles() {
      return this.itemEntities;
   }

   public void addItem(EntityItem entityItem) {
      if (this.itemEntities.isEmpty()) {
         this.currentX = entityItem.z();
         this.currentY = entityItem.N();
         this.currentZ = entityItem.h();
         this.previousX = entityItem.M();
         this.previousY = entityItem.W();
         this.previousZ = entityItem.m$src$D$fwnne5();
      } else {
         int count = this.itemEntities.size();
         this.currentX = (this.currentX * count + entityItem.z()) / (count + 1);
         this.currentY = (this.currentY * count + entityItem.N()) / (count + 1);
         this.currentZ = (this.currentZ * count + entityItem.h()) / (count + 1);
      }

      this.itemEntities.add(entityItem.getObject());
      this.rebuildStacks();
   }

   public boolean isEmpty() {
      return this.itemEntities.isEmpty();
   }

   public void merge(ItemESPGroup other) {
      for (Object entityHandle : other.itemEntities) {
         if (!this.itemEntities.contains(entityHandle)) {
            this.itemEntities.add(entityHandle);
         }
      }

      double sumX = 0.0;
      double sumY = 0.0;
      double sumZ = 0.0;

      for (Object entityHandlex : this.itemEntities) {
         EntityItem entityItem = new EntityItem(entityHandlex);
         sumX += entityItem.z();
         sumY += entityItem.N();
         sumZ += entityItem.h();
      }

      int count = this.itemEntities.size();
      this.currentX = sumX / count;
      this.currentY = sumY / count;
      this.currentZ = sumZ / count;
      this.rebuildStacks();
   }

   public void rebuildStacks() {
      this.stackedItems.clear();

      for (Object entityHandle : this.itemEntities) {
         EntityItem entityItem = new EntityItem(entityHandle);
         ItemStack itemStack = entityItem.getItemStack();
         if (itemStack.isNotNull()) {
            if (this.parent.isGroupingEnabled()) {
               boolean merged = false;

               for (ItemStack existingStack : this.stackedItems) {
                  if (existingStack.e(itemStack)) {
                     existingStack.Y(existingStack.t() + itemStack.t());
                     merged = true;
                     break;
                  }
               }

               if (!merged) {
                  ItemStack copyStack = ItemStack.S(itemStack.getItem());
                  copyStack.i(itemStack.l());
                  copyStack.s(itemStack.L());
                  copyStack.Y(itemStack.t());
                  this.stackedItems.add(copyStack);
               }
            } else {
               ItemStack singleStack = ItemStack.S(itemStack.getItem());
               singleStack.i(itemStack.l());
               singleStack.s(itemStack.L());
               singleStack.Y(itemStack.t());
               this.stackedItems.add(singleStack);
            }
         }
      }
   }

   public List<ItemStack> getStacks() {
      return this.stackedItems;
   }

   public ItemESPGroup(ItemESP parent, EntityItem entityItem) {
      this.parent = parent;
      this.itemEntities = new ArrayList<>();
      this.stackedItems = new ArrayList<>();
      this.currentX = entityItem.z();
      this.currentY = entityItem.N();
      this.currentZ = entityItem.h();
      this.previousX = this.currentX;
      this.previousY = this.currentY;
      this.previousZ = this.currentZ;
      this.addItem(entityItem);
   }

   public Object getRepresentativeHandle() {
      return this.itemEntities.isEmpty() ? null : this.itemEntities.get(0);
   }

   public void update(List<Object> worldEntities, EntityPlayerSP player) {
      this.previousX = this.currentX;
      this.previousY = this.currentY;
      this.previousZ = this.currentZ;
      EntityItem representative = new EntityItem(this.getRepresentativeHandle());
      double distance = player.getDistanceToEntity(representative);
      double maxRadius = Math.max(1.5, distance / 5.0);
      Iterator<Object> iterator = this.itemEntities.iterator();

      while (iterator.hasNext()) {
         Object entityHandle = iterator.next();
         if (!worldEntities.contains(entityHandle)) {
            iterator.remove();
         } else {
            EntityItem entityItem = new EntityItem(entityHandle);
            double deltaX = entityItem.z() - this.currentX;
            double deltaZ;
            double deltaY;
            if (Math.sqrt(deltaX * deltaX + (deltaY = entityItem.N() - this.currentY) * deltaY + (deltaZ = entityItem.h() - this.currentZ) * deltaZ)
               > maxRadius) {
               iterator.remove();
            }
         }
      }

      if (!this.itemEntities.isEmpty()) {
         double sumX = 0.0;
         double sumY = 0.0;
         double sumZ = 0.0;

         for (Object entityHandle : this.itemEntities) {
            EntityItem entityItem = new EntityItem(entityHandle);
            sumX += entityItem.z();
            sumY += entityItem.N();
            sumZ += entityItem.h();
         }

         int count = this.itemEntities.size();
         this.currentX = sumX / count;
         this.currentY = sumY / count;
         this.currentZ = sumZ / count;
      }

      this.rebuildStacks();
   }
}
