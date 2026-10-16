package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@Beta
@ThreadSafe
public class CycleDetectingLockFactory {
   private static final ConcurrentMap<Class<? extends Enum>, Map<? extends Enum, CycleDetectingLockFactory.LockGraphNode>> lockGraphNodesPerType = new MapMaker()
      .weakKeys()
      .makeMap();
   private static final Logger logger = Logger.getLogger(CycleDetectingLockFactory.class.getName());
   final CycleDetectingLockFactory.Policy policy;
   private static final ThreadLocal<ArrayList<CycleDetectingLockFactory.LockGraphNode>> acquiredLocks = new ThreadLocal<ArrayList<CycleDetectingLockFactory.LockGraphNode>>() {
      protected ArrayList<CycleDetectingLockFactory.LockGraphNode> initialValue() {
         return Lists.newArrayListWithCapacity(3);
      }
   };

   public static CycleDetectingLockFactory newInstance(CycleDetectingLockFactory.Policy policy) {
      return new CycleDetectingLockFactory(policy);
   }

   public ReentrantLock newReentrantLock(String lockName) {
      return this.newReentrantLock(lockName, false);
   }

   public ReentrantLock newReentrantLock(String lockName, boolean fair) {
      return (ReentrantLock)(this.policy == CycleDetectingLockFactory.Policies.DISABLED
         ? new ReentrantLock(fair)
         : new CycleDetectingLockFactory.CycleDetectingReentrantLock(new CycleDetectingLockFactory.LockGraphNode(lockName), fair));
   }

   public ReentrantReadWriteLock newReentrantReadWriteLock(String lockName) {
      return this.newReentrantReadWriteLock(lockName, false);
   }

   public ReentrantReadWriteLock newReentrantReadWriteLock(String lockName, boolean fair) {
      return (ReentrantReadWriteLock)(this.policy == CycleDetectingLockFactory.Policies.DISABLED
         ? new ReentrantReadWriteLock(fair)
         : new CycleDetectingLockFactory.CycleDetectingReentrantReadWriteLock(new CycleDetectingLockFactory.LockGraphNode(lockName), fair));
   }

   public static <E extends Enum<E>> CycleDetectingLockFactory.WithExplicitOrdering<E> newInstanceWithExplicitOrdering(
      Class<E> enumClass, CycleDetectingLockFactory.Policy policy
   ) {
      Preconditions.checkNotNull(enumClass);
      Preconditions.checkNotNull(policy);
      Map<E, CycleDetectingLockFactory.LockGraphNode> lockGraphNodes = (Map<E, CycleDetectingLockFactory.LockGraphNode>)getOrCreateNodes(enumClass);
      return new CycleDetectingLockFactory.WithExplicitOrdering<>(policy, lockGraphNodes);
   }

   private static Map<? extends Enum, CycleDetectingLockFactory.LockGraphNode> getOrCreateNodes(Class<? extends Enum> clazz) {
      Map<? extends Enum, CycleDetectingLockFactory.LockGraphNode> existing = lockGraphNodesPerType.get(clazz);
      if (existing != null) {
         return existing;
      } else {
         Map<? extends Enum, CycleDetectingLockFactory.LockGraphNode> created = createNodes(clazz);
         existing = lockGraphNodesPerType.putIfAbsent(clazz, created);
         return Objects.firstNonNull(existing, created);
      }
   }

   @VisibleForTesting
   static <E extends Enum<E>> Map<E, CycleDetectingLockFactory.LockGraphNode> createNodes(Class<E> clazz) {
      EnumMap<E, CycleDetectingLockFactory.LockGraphNode> map = Maps.newEnumMap(clazz);
      E[] keys = (E[])clazz.getEnumConstants();
      int numKeys = keys.length;
      ArrayList<CycleDetectingLockFactory.LockGraphNode> nodes = Lists.newArrayListWithCapacity(numKeys);

      for (E key : keys) {
         CycleDetectingLockFactory.LockGraphNode node = new CycleDetectingLockFactory.LockGraphNode(getLockName(key));
         nodes.add(node);
         map.put(key, node);
      }

      for (int i = 1; i < numKeys; i++) {
         nodes.get(i).checkAcquiredLocks(CycleDetectingLockFactory.Policies.THROW, nodes.subList(0, i));
      }

      for (int i = 0; i < numKeys - 1; i++) {
         nodes.get(i).checkAcquiredLocks(CycleDetectingLockFactory.Policies.DISABLED, nodes.subList(i + 1, numKeys));
      }

      return Collections.unmodifiableMap(map);
   }

   private static String getLockName(Enum<?> rank) {
      return rank.getDeclaringClass().getSimpleName() + "." + rank.name();
   }

   private CycleDetectingLockFactory(CycleDetectingLockFactory.Policy policy) {
      this.policy = Preconditions.checkNotNull(policy);
   }

   private void aboutToAcquire(CycleDetectingLockFactory.CycleDetectingLock lock) {
      if (!lock.isAcquiredByCurrentThread()) {
         ArrayList<CycleDetectingLockFactory.LockGraphNode> acquiredLockList = acquiredLocks.get();
         CycleDetectingLockFactory.LockGraphNode node = lock.getLockGraphNode();
         node.checkAcquiredLocks(this.policy, acquiredLockList);
         acquiredLockList.add(node);
      }
   }

   private void lockStateChanged(CycleDetectingLockFactory.CycleDetectingLock lock) {
      if (!lock.isAcquiredByCurrentThread()) {
         ArrayList<CycleDetectingLockFactory.LockGraphNode> acquiredLockList = acquiredLocks.get();
         CycleDetectingLockFactory.LockGraphNode node = lock.getLockGraphNode();

         for (int i = acquiredLockList.size() - 1; i >= 0; i--) {
            if (acquiredLockList.get(i) == node) {
               acquiredLockList.remove(i);
               break;
            }
         }
      }
   }

   private interface CycleDetectingLock {
      CycleDetectingLockFactory.LockGraphNode getLockGraphNode();

      boolean isAcquiredByCurrentThread();
   }

   final class CycleDetectingReentrantLock extends ReentrantLock implements CycleDetectingLockFactory.CycleDetectingLock {
      private final CycleDetectingLockFactory.LockGraphNode lockGraphNode;

      private CycleDetectingReentrantLock(CycleDetectingLockFactory.LockGraphNode lockGraphNode, boolean fair) {
         super(fair);
         this.lockGraphNode = Preconditions.checkNotNull(lockGraphNode);
      }

      @Override
      public CycleDetectingLockFactory.LockGraphNode getLockGraphNode() {
         return this.lockGraphNode;
      }

      @Override
      public boolean isAcquiredByCurrentThread() {
         return this.isHeldByCurrentThread();
      }

      @Override
      public void lock() {
         CycleDetectingLockFactory.this.aboutToAcquire(this);

         try {
            super.lock();
         } finally {
            CycleDetectingLockFactory.this.lockStateChanged(this);
         }
      }

      @Override
      public void lockInterruptibly() throws InterruptedException {
         CycleDetectingLockFactory.this.aboutToAcquire(this);

         try {
            super.lockInterruptibly();
         } finally {
            CycleDetectingLockFactory.this.lockStateChanged(this);
         }
      }

      @Override
      public boolean tryLock() {
         CycleDetectingLockFactory.this.aboutToAcquire(this);

         boolean var1;
         try {
            var1 = super.tryLock();
         } finally {
            CycleDetectingLockFactory.this.lockStateChanged(this);
         }

         return var1;
      }

      @Override
      public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
         CycleDetectingLockFactory.this.aboutToAcquire(this);

         boolean var4;
         try {
            var4 = super.tryLock(timeout, unit);
         } finally {
            CycleDetectingLockFactory.this.lockStateChanged(this);
         }

         return var4;
      }

      @Override
      public void unlock() {
         try {
            super.unlock();
         } finally {
            CycleDetectingLockFactory.this.lockStateChanged(this);
         }
      }
   }

   private class CycleDetectingReentrantReadLock extends ReadLock {
      final CycleDetectingLockFactory.CycleDetectingReentrantReadWriteLock readWriteLock;

      CycleDetectingReentrantReadLock(CycleDetectingLockFactory.CycleDetectingReentrantReadWriteLock readWriteLock) {
         super(readWriteLock);
         this.readWriteLock = readWriteLock;
      }

      @Override
      public void lock() {
         CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);

         try {
            super.lock();
         } finally {
            CycleDetectingLockFactory.this.lockStateChanged(this.readWriteLock);
         }
      }

      @Override
      public void lockInterruptibly() throws InterruptedException {
         CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);

         try {
            super.lockInterruptibly();
         } finally {
            CycleDetectingLockFactory.this.lockStateChanged(this.readWriteLock);
         }
      }

      @Override
      public boolean tryLock() {
         CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);

         boolean var1;
         try {
            var1 = super.tryLock();
         } finally {
            CycleDetectingLockFactory.this.lockStateChanged(this.readWriteLock);
         }

         return var1;
      }

      @Override
      public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
         CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);

         boolean var4;
         try {
            var4 = super.tryLock(timeout, unit);
         } finally {
            CycleDetectingLockFactory.this.lockStateChanged(this.readWriteLock);
         }

         return var4;
      }

      @Override
      public void unlock() {
         try {
            super.unlock();
         } finally {
            CycleDetectingLockFactory.this.lockStateChanged(this.readWriteLock);
         }
      }
   }

   final class CycleDetectingReentrantReadWriteLock extends ReentrantReadWriteLock implements CycleDetectingLockFactory.CycleDetectingLock {
      private final CycleDetectingLockFactory.CycleDetectingReentrantReadLock readLock = CycleDetectingLockFactory.this.new CycleDetectingReentrantReadLock(
         this
      );
      private final CycleDetectingLockFactory.CycleDetectingReentrantWriteLock writeLock = CycleDetectingLockFactory.this.new CycleDetectingReentrantWriteLock(
         this
      );
      private final CycleDetectingLockFactory.LockGraphNode lockGraphNode;

      private CycleDetectingReentrantReadWriteLock(CycleDetectingLockFactory.LockGraphNode lockGraphNode, boolean fair) {
         super(fair);
         this.lockGraphNode = Preconditions.checkNotNull(lockGraphNode);
      }

      @Override
      public ReadLock readLock() {
         return this.readLock;
      }

      @Override
      public WriteLock writeLock() {
         return this.writeLock;
      }

      @Override
      public CycleDetectingLockFactory.LockGraphNode getLockGraphNode() {
         return this.lockGraphNode;
      }

      @Override
      public boolean isAcquiredByCurrentThread() {
         return this.isWriteLockedByCurrentThread() || this.getReadHoldCount() > 0;
      }
   }

   private class CycleDetectingReentrantWriteLock extends WriteLock {
      final CycleDetectingLockFactory.CycleDetectingReentrantReadWriteLock readWriteLock;

      CycleDetectingReentrantWriteLock(CycleDetectingLockFactory.CycleDetectingReentrantReadWriteLock readWriteLock) {
         super(readWriteLock);
         this.readWriteLock = readWriteLock;
      }

      @Override
      public void lock() {
         CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);

         try {
            super.lock();
         } finally {
            CycleDetectingLockFactory.this.lockStateChanged(this.readWriteLock);
         }
      }

      @Override
      public void lockInterruptibly() throws InterruptedException {
         CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);

         try {
            super.lockInterruptibly();
         } finally {
            CycleDetectingLockFactory.this.lockStateChanged(this.readWriteLock);
         }
      }

      @Override
      public boolean tryLock() {
         CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);

         boolean var1;
         try {
            var1 = super.tryLock();
         } finally {
            CycleDetectingLockFactory.this.lockStateChanged(this.readWriteLock);
         }

         return var1;
      }

      @Override
      public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
         CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);

         boolean var4;
         try {
            var4 = super.tryLock(timeout, unit);
         } finally {
            CycleDetectingLockFactory.this.lockStateChanged(this.readWriteLock);
         }

         return var4;
      }

      @Override
      public void unlock() {
         try {
            super.unlock();
         } finally {
            CycleDetectingLockFactory.this.lockStateChanged(this.readWriteLock);
         }
      }
   }

   private static class ExampleStackTrace extends IllegalStateException {
      static final StackTraceElement[] EMPTY_STACK_TRACE = new StackTraceElement[0];
      static Set<String> EXCLUDED_CLASS_NAMES = ImmutableSet.of(
         CycleDetectingLockFactory.class.getName(),
         CycleDetectingLockFactory.ExampleStackTrace.class.getName(),
         CycleDetectingLockFactory.LockGraphNode.class.getName()
      );

      ExampleStackTrace(CycleDetectingLockFactory.LockGraphNode node1, CycleDetectingLockFactory.LockGraphNode node2) {
         super(node1.getLockName() + " -> " + node2.getLockName());
         StackTraceElement[] origStackTrace = this.getStackTrace();
         int i = 0;

         for (int n = origStackTrace.length; i < n; i++) {
            if (CycleDetectingLockFactory.WithExplicitOrdering.class.getName().equals(origStackTrace[i].getClassName())) {
               this.setStackTrace(EMPTY_STACK_TRACE);
               break;
            }

            if (!EXCLUDED_CLASS_NAMES.contains(origStackTrace[i].getClassName())) {
               this.setStackTrace(Arrays.copyOfRange(origStackTrace, i, n));
               break;
            }
         }
      }
   }

   private static class LockGraphNode {
      final Map<CycleDetectingLockFactory.LockGraphNode, CycleDetectingLockFactory.ExampleStackTrace> allowedPriorLocks = new MapMaker().weakKeys().makeMap();
      final Map<CycleDetectingLockFactory.LockGraphNode, CycleDetectingLockFactory.PotentialDeadlockException> disallowedPriorLocks = new MapMaker()
         .weakKeys()
         .makeMap();
      final String lockName;

      LockGraphNode(String lockName) {
         this.lockName = Preconditions.checkNotNull(lockName);
      }

      String getLockName() {
         return this.lockName;
      }

      void checkAcquiredLocks(CycleDetectingLockFactory.Policy policy, List<CycleDetectingLockFactory.LockGraphNode> acquiredLocks) {
         int i = 0;

         for (int size = acquiredLocks.size(); i < size; i++) {
            this.checkAcquiredLock(policy, acquiredLocks.get(i));
         }
      }

      void checkAcquiredLock(CycleDetectingLockFactory.Policy policy, CycleDetectingLockFactory.LockGraphNode acquiredLock) {
         Preconditions.checkState(this != acquiredLock, "Attempted to acquire multiple locks with the same rank " + acquiredLock.getLockName());
         if (!this.allowedPriorLocks.containsKey(acquiredLock)) {
            CycleDetectingLockFactory.PotentialDeadlockException previousDeadlockException = this.disallowedPriorLocks.get(acquiredLock);
            if (previousDeadlockException != null) {
               CycleDetectingLockFactory.PotentialDeadlockException exception = new CycleDetectingLockFactory.PotentialDeadlockException(
                  acquiredLock, this, previousDeadlockException.getConflictingStackTrace()
               );
               policy.handlePotentialDeadlock(exception);
            } else {
               Set<CycleDetectingLockFactory.LockGraphNode> seen = Sets.newIdentityHashSet();
               CycleDetectingLockFactory.ExampleStackTrace path = acquiredLock.findPathTo(this, seen);
               if (path == null) {
                  this.allowedPriorLocks.put(acquiredLock, new CycleDetectingLockFactory.ExampleStackTrace(acquiredLock, this));
               } else {
                  CycleDetectingLockFactory.PotentialDeadlockException exception = new CycleDetectingLockFactory.PotentialDeadlockException(
                     acquiredLock, this, path
                  );
                  this.disallowedPriorLocks.put(acquiredLock, exception);
                  policy.handlePotentialDeadlock(exception);
               }
            }
         }
      }

      @Nullable
      private CycleDetectingLockFactory.ExampleStackTrace findPathTo(
         CycleDetectingLockFactory.LockGraphNode node, Set<CycleDetectingLockFactory.LockGraphNode> seen
      ) {
         if (!seen.add(this)) {
            return null;
         } else {
            CycleDetectingLockFactory.ExampleStackTrace found = this.allowedPriorLocks.get(node);
            if (found != null) {
               return found;
            } else {
               for (Entry<CycleDetectingLockFactory.LockGraphNode, CycleDetectingLockFactory.ExampleStackTrace> entry : this.allowedPriorLocks.entrySet()) {
                  CycleDetectingLockFactory.LockGraphNode preAcquiredLock = entry.getKey();
                  found = preAcquiredLock.findPathTo(node, seen);
                  if (found != null) {
                     CycleDetectingLockFactory.ExampleStackTrace path = new CycleDetectingLockFactory.ExampleStackTrace(preAcquiredLock, this);
                     path.setStackTrace(entry.getValue().getStackTrace());
                     path.initCause(found);
                     return path;
                  }
               }

               return null;
            }
         }
      }
   }

   @Beta
   public static enum Policies implements CycleDetectingLockFactory.Policy {
      THROW {
         @Override
         public void handlePotentialDeadlock(CycleDetectingLockFactory.PotentialDeadlockException e) {
            throw e;
         }
      },
      WARN {
         @Override
         public void handlePotentialDeadlock(CycleDetectingLockFactory.PotentialDeadlockException e) {
            CycleDetectingLockFactory.logger.log(Level.SEVERE, "Detected potential deadlock", (Throwable)e);
         }
      },
      DISABLED {
         @Override
         public void handlePotentialDeadlock(CycleDetectingLockFactory.PotentialDeadlockException e) {
         }
      };

      private Policies() {
      }
   }

   @Beta
   @ThreadSafe
   public interface Policy {
      void handlePotentialDeadlock(CycleDetectingLockFactory.PotentialDeadlockException var1);
   }

   @Beta
   public static final class PotentialDeadlockException extends CycleDetectingLockFactory.ExampleStackTrace {
      private final CycleDetectingLockFactory.ExampleStackTrace conflictingStackTrace;

      private PotentialDeadlockException(
         CycleDetectingLockFactory.LockGraphNode node1,
         CycleDetectingLockFactory.LockGraphNode node2,
         CycleDetectingLockFactory.ExampleStackTrace conflictingStackTrace
      ) {
         super(node1, node2);
         this.conflictingStackTrace = conflictingStackTrace;
         this.initCause(conflictingStackTrace);
      }

      public CycleDetectingLockFactory.ExampleStackTrace getConflictingStackTrace() {
         return this.conflictingStackTrace;
      }

      @Override
      public String getMessage() {
         StringBuilder message = new StringBuilder(super.getMessage());

         for (Throwable t = this.conflictingStackTrace; t != null; t = t.getCause()) {
            message.append(", ").append(t.getMessage());
         }

         return message.toString();
      }
   }

   @Beta
   public static final class WithExplicitOrdering<E extends Enum<E>> extends CycleDetectingLockFactory {
      private final Map<E, CycleDetectingLockFactory.LockGraphNode> lockGraphNodes;

      @VisibleForTesting
      WithExplicitOrdering(CycleDetectingLockFactory.Policy policy, Map<E, CycleDetectingLockFactory.LockGraphNode> lockGraphNodes) {
         super(policy);
         this.lockGraphNodes = lockGraphNodes;
      }

      public ReentrantLock newReentrantLock(E rank) {
         return this.newReentrantLock(rank, false);
      }

      public ReentrantLock newReentrantLock(E rank, boolean fair) {
         return (ReentrantLock)(this.policy == CycleDetectingLockFactory.Policies.DISABLED
            ? new ReentrantLock(fair)
            : new CycleDetectingLockFactory.CycleDetectingReentrantLock(this.lockGraphNodes.get(rank), fair));
      }

      public ReentrantReadWriteLock newReentrantReadWriteLock(E rank) {
         return this.newReentrantReadWriteLock(rank, false);
      }

      public ReentrantReadWriteLock newReentrantReadWriteLock(E rank, boolean fair) {
         return (ReentrantReadWriteLock)(this.policy == CycleDetectingLockFactory.Policies.DISABLED
            ? new ReentrantReadWriteLock(fair)
            : new CycleDetectingLockFactory.CycleDetectingReentrantReadWriteLock(this.lockGraphNodes.get(rank), fair));
      }
   }
}
