package gg.vape.runtime.model;

import java.util.List;

class DetachedStringTreeListEntry extends DetachedStringTreeEntry {
   final DetachedStringTreeNode listOwnerNode;
   List<String> values;

   DetachedStringTreeListEntry(DetachedStringTreeNode ownerNode) {
      super(ownerNode);
      this.listOwnerNode = ownerNode;
   }
}
