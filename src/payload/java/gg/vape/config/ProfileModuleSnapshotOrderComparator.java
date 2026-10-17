package gg.vape.config;

import java.util.Comparator;

public class ProfileModuleSnapshotOrderComparator implements Comparator<ProfileModuleSnapshot> {
   public int compare(ProfileModuleSnapshot left, ProfileModuleSnapshot right) {
      return Integer.compare(right.getSortPriority(), left.getSortPriority());
   }
}
