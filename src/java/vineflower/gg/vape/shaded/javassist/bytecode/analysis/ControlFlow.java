package gg.vape.shaded.javassist.bytecode.analysis;

import gg.vape.shaded.javassist.CtClass;
import gg.vape.shaded.javassist.CtMethod;
import gg.vape.shaded.javassist.bytecode.BadBytecode;
import gg.vape.shaded.javassist.bytecode.MethodInfo;
import gg.vape.shaded.javassist.bytecode.stackmap.BasicBlock;
import java.util.ArrayList;
import java.util.List;

public class ControlFlow {
   private CtClass clazz;
   private MethodInfo methodInfo;
   private ControlFlow.Block[] basicBlocks;
   private Frame[] frames;

   public ControlFlow(CtMethod method) throws BadBytecode {
      this(method.getDeclaringClass(), method.getMethodInfo2());
   }

   public ControlFlow(CtClass ctclazz, MethodInfo minfo) throws BadBytecode {
      this.clazz = ctclazz;
      this.methodInfo = minfo;
      this.frames = null;
      this.basicBlocks = (ControlFlow.Block[])(new BasicBlock.Maker() {
         @Override
         protected BasicBlock makeBlock(int pos) {
            return new ControlFlow.Block(pos, ControlFlow.this.methodInfo);
         }

         @Override
         protected BasicBlock[] makeArray(int size) {
            return new ControlFlow.Block[size];
         }
      }).make(minfo);
      if (this.basicBlocks == null) {
         this.basicBlocks = new ControlFlow.Block[0];
      }

      int size = this.basicBlocks.length;
      int[] counters = new int[size];

      for (int i = 0; i < size; i++) {
         ControlFlow.Block b = this.basicBlocks[i];
         b.index = i;
         b.entrances = new ControlFlow.Block[b.incomings()];
         counters[i] = 0;
      }

      for (int i = 0; i < size; i++) {
         ControlFlow.Block b = this.basicBlocks[i];

         for (int k = 0; k < b.exits(); k++) {
            ControlFlow.Block e = b.exit(k);
            e.entrances[counters[e.index]++] = b;
         }

         ControlFlow.Catcher[] catchers = b.catchers();

         for (int k = 0; k < catchers.length; k++) {
            ControlFlow.Block catchBlock = catchers[k].node;
            catchBlock.entrances[counters[catchBlock.index]++] = b;
         }
      }
   }

   public ControlFlow.Block[] basicBlocks() {
      return this.basicBlocks;
   }

   public Frame frameAt(int pos) throws BadBytecode {
      if (this.frames == null) {
         this.frames = new Analyzer().analyze(this.clazz, this.methodInfo);
      }

      return this.frames[pos];
   }

   public ControlFlow.Node[] dominatorTree() {
      int size = this.basicBlocks.length;
      if (size == 0) {
         return null;
      } else {
         ControlFlow.Node[] nodes = new ControlFlow.Node[size];
         boolean[] visited = new boolean[size];
         int[] distance = new int[size];

         for (int i = 0; i < size; i++) {
            nodes[i] = new ControlFlow.Node(this.basicBlocks[i]);
            visited[i] = false;
         }

         ControlFlow.Access access = new ControlFlow.Access(nodes) {
            @Override
            BasicBlock[] exits(ControlFlow.Node n) {
               return n.block.getExit();
            }

            @Override
            BasicBlock[] entrances(ControlFlow.Node n) {
               return n.block.entrances;
            }
         };
         nodes[0].makeDepth1stTree(null, visited, 0, distance, access);

         do {
            for (int i = 0; i < size; i++) {
               visited[i] = false;
            }
         } while (nodes[0].makeDominatorTree(visited, distance, access));

         ControlFlow.Node.setChildren(nodes);
         return nodes;
      }
   }

   public ControlFlow.Node[] postDominatorTree() {
      int size = this.basicBlocks.length;
      if (size == 0) {
         return null;
      } else {
         ControlFlow.Node[] nodes = new ControlFlow.Node[size];
         boolean[] visited = new boolean[size];
         int[] distance = new int[size];

         for (int i = 0; i < size; i++) {
            nodes[i] = new ControlFlow.Node(this.basicBlocks[i]);
            visited[i] = false;
         }

         ControlFlow.Access access = new ControlFlow.Access(nodes) {
            @Override
            BasicBlock[] exits(ControlFlow.Node n) {
               return n.block.entrances;
            }

            @Override
            BasicBlock[] entrances(ControlFlow.Node n) {
               return n.block.getExit();
            }
         };
         int counter = 0;

         for (int i = 0; i < size; i++) {
            if (nodes[i].block.exits() == 0) {
               counter = nodes[i].makeDepth1stTree(null, visited, counter, distance, access);
            }
         }

         boolean changed;
         do {
            for (int ix = 0; ix < size; ix++) {
               visited[ix] = false;
            }

            changed = false;

            for (int ix = 0; ix < size; ix++) {
               if (nodes[ix].block.exits() == 0 && nodes[ix].makeDominatorTree(visited, distance, access)) {
                  changed = true;
               }
            }
         } while (changed);

         ControlFlow.Node.setChildren(nodes);
         return nodes;
      }
   }

   abstract static class Access {
      ControlFlow.Node[] all;

      Access(ControlFlow.Node[] nodes) {
         this.all = nodes;
      }

      ControlFlow.Node node(BasicBlock b) {
         return this.all[((ControlFlow.Block)b).index];
      }

      abstract BasicBlock[] exits(ControlFlow.Node var1);

      abstract BasicBlock[] entrances(ControlFlow.Node var1);
   }

   public static class Block extends BasicBlock {
      public Object clientData = null;
      int index;
      MethodInfo method;
      ControlFlow.Block[] entrances;

      Block(int pos, MethodInfo minfo) {
         super(pos);
         this.method = minfo;
      }

      @Override
      protected void toString2(StringBuilder sbuf) {
         super.toString2(sbuf);
         sbuf.append(", incoming{");

         for (int i = 0; i < this.entrances.length; i++) {
            sbuf.append(this.entrances[i].position).append(", ");
         }

         sbuf.append('}');
      }

      BasicBlock[] getExit() {
         return this.exit;
      }

      public int index() {
         return this.index;
      }

      public int position() {
         return this.position;
      }

      public int length() {
         return this.length;
      }

      public int incomings() {
         return this.incoming;
      }

      public ControlFlow.Block incoming(int n) {
         return this.entrances[n];
      }

      public int exits() {
         return this.exit == null ? 0 : this.exit.length;
      }

      public ControlFlow.Block exit(int n) {
         return (ControlFlow.Block)this.exit[n];
      }

      public ControlFlow.Catcher[] catchers() {
         List<ControlFlow.Catcher> catchers = new ArrayList<>();

         for (BasicBlock.Catch c = this.toCatch; c != null; c = c.next) {
            catchers.add(new ControlFlow.Catcher(c));
         }

         return catchers.toArray(new ControlFlow.Catcher[catchers.size()]);
      }
   }

   public static class Catcher {
      private ControlFlow.Block node;
      private int typeIndex;

      Catcher(BasicBlock.Catch c) {
         this.node = (ControlFlow.Block)c.body;
         this.typeIndex = c.typeIndex;
      }

      public ControlFlow.Block block() {
         return this.node;
      }

      public String type() {
         return this.typeIndex == 0 ? "java.lang.Throwable" : this.node.method.getConstPool().getClassInfo(this.typeIndex);
      }
   }

   public static class Node {
      private ControlFlow.Block block;
      private ControlFlow.Node parent;
      private ControlFlow.Node[] children;

      Node(ControlFlow.Block b) {
         this.block = b;
         this.parent = null;
      }

      @Override
      public String toString() {
         StringBuilder sbuf = new StringBuilder();
         sbuf.append("Node[pos=").append(this.block().position());
         sbuf.append(", parent=");
         sbuf.append(this.parent == null ? "*" : Integer.toString(this.parent.block().position()));
         sbuf.append(", children{");

         for (int i = 0; i < this.children.length; i++) {
            sbuf.append(this.children[i].block().position()).append(", ");
         }

         sbuf.append("}]");
         return sbuf.toString();
      }

      public ControlFlow.Block block() {
         return this.block;
      }

      public ControlFlow.Node parent() {
         return this.parent;
      }

      public int children() {
         return this.children.length;
      }

      public ControlFlow.Node child(int n) {
         return this.children[n];
      }

      int makeDepth1stTree(ControlFlow.Node caller, boolean[] visited, int counter, int[] distance, ControlFlow.Access access) {
         int index = this.block.index;
         if (visited[index]) {
            return counter;
         } else {
            visited[index] = true;
            this.parent = caller;
            BasicBlock[] exits = access.exits(this);
            if (exits != null) {
               for (int i = 0; i < exits.length; i++) {
                  ControlFlow.Node n = access.node(exits[i]);
                  counter = n.makeDepth1stTree(this, visited, counter, distance, access);
               }
            }

            distance[index] = counter++;
            return counter;
         }
      }

      boolean makeDominatorTree(boolean[] visited, int[] distance, ControlFlow.Access access) {
         int index = this.block.index;
         if (visited[index]) {
            return false;
         } else {
            visited[index] = true;
            boolean changed = false;
            BasicBlock[] exits = access.exits(this);
            if (exits != null) {
               for (int i = 0; i < exits.length; i++) {
                  ControlFlow.Node n = access.node(exits[i]);
                  if (n.makeDominatorTree(visited, distance, access)) {
                     changed = true;
                  }
               }
            }

            BasicBlock[] entrances = access.entrances(this);
            if (entrances != null) {
               for (int ix = 0; ix < entrances.length; ix++) {
                  if (this.parent != null) {
                     ControlFlow.Node n = getAncestor(this.parent, access.node(entrances[ix]), distance);
                     if (n != this.parent) {
                        this.parent = n;
                        changed = true;
                     }
                  }
               }
            }

            return changed;
         }
      }

      private static ControlFlow.Node getAncestor(ControlFlow.Node n1, ControlFlow.Node n2, int[] distance) {
         while (n1 != n2) {
            if (distance[n1.block.index] < distance[n2.block.index]) {
               n1 = n1.parent;
            } else {
               n2 = n2.parent;
            }

            if (n1 == null || n2 == null) {
               return null;
            }
         }

         return n1;
      }

      private static void setChildren(ControlFlow.Node[] all) {
         int size = all.length;
         int[] nchildren = new int[size];

         for (int i = 0; i < size; i++) {
            nchildren[i] = 0;
         }

         for (int i = 0; i < size; i++) {
            ControlFlow.Node p = all[i].parent;
            if (p != null) {
               nchildren[p.block.index]++;
            }
         }

         for (int ix = 0; ix < size; ix++) {
            all[ix].children = new ControlFlow.Node[nchildren[ix]];
         }

         for (int ix = 0; ix < size; ix++) {
            nchildren[ix] = 0;
         }

         for (int ix = 0; ix < size; ix++) {
            ControlFlow.Node n = all[ix];
            ControlFlow.Node p = n.parent;
            if (p != null) {
               p.children[nchildren[p.block.index]++] = n;
            }
         }
      }
   }
}
