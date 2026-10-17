package gg.vape.unmap;

import java.util.concurrent.CopyOnWriteArrayList;

class BendableBindList extends CopyOnWriteArrayList<Integer> {
   final Bendable owner;

   BendableBindList(Bendable owner) {
      this.owner = owner;
   }

   public boolean add(Integer bindId) {
      return super.add(bindId);
   }
}
