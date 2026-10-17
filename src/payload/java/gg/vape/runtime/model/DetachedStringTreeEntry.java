package gg.vape.runtime.model;

class DetachedStringTreeEntry {
   String value;
   final DetachedStringTreeNode ownerNode;

   DetachedStringTreeEntry(DetachedStringTreeNode ownerNode) {
      this.ownerNode = ownerNode;
   }
}
