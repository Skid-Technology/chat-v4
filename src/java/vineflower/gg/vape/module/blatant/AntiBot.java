package gg.vape.module.blatant;

import gg.vape.Vape;
import gg.vape.config.ClientSettings;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventEntityJoinWorld;
import gg.vape.event.impl.EventPrePlayerTick;
import gg.vape.event.impl.EventPreTick;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.blatant.antibot.AntiBotBooleanValue;
import gg.vape.module.blatant.antibot.AntiBotEntityCache;
import gg.vape.module.blatant.antibot.AntiBotModeValue;
import gg.vape.module.blatant.antibot.AntiBotStateTracker;
import gg.vape.module.render.entity.RenderEntityContext;
import gg.vape.notification.NotificationType;
import gg.vape.unmap.ModeOption;
import gg.vape.unmap.ModeSelection;
import gg.vape.utils.BlockUtil;
import gg.vape.utils.MathUtil;
import gg.vape.utils.MutableColor;
import gg.vape.utils.RotationUtil;
import gg.vape.value.BooleanValue;
import gg.vape.value.ModeValue;
import gg.vape.wrapper.impl.Block;
import gg.vape.wrapper.impl.BlockPos;
import gg.vape.wrapper.impl.BlockState;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityOtherPlayerMP;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GameProfile;
import gg.vape.wrapper.impl.GuiPlayerTabOverlayBridge;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.NetHandlerPlayClientImpl;
import gg.vape.wrapper.impl.PlayerInfo;
import gg.vape.wrapper.impl.ScorePlayerTeam;
import gg.vape.wrapper.impl.Team;
import gg.vape.wrapper.impl.WorldClient;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import org.jetbrains.annotations.Nullable;

public class AntiBot extends Mod {
   private final ModeValue teamColorMode;
   private final Map<Integer, Integer> pingByEntityId;
   private final ModeOption darkBlueOption;
   private final AntiBotStateTracker stateTracker;
   private final ModeOption blueOption;
   private final ModeOption grayOption;
   private int botJoinCount = 0;
   private final BooleanValue recolorVisuals;
   private List<Object> playerInfoList;
   private final HashMap<ModeOption, Character> colorCharByOption = new HashMap<>();
   private Object lastWorld;
   private final ModeOption yellowOption;
   private final ModeOption greenOption;
   private final List<Object> trackedEntities;
   private final ModeOption darkPurpleOption;
   private final ModeOption darkAquaOption;
   private final ModeOption darkRedOption;
   private final BooleanValue antiBot;
   private final ModeOption redOption;
   private final AntiBotEntityCache entityCache;
   private final BooleanValue autoDetectColor;
   private final ModeOption purpleOption;
   private int tickCounter = 0;
   private final ModeOption goldOption;
   private static final char COLOR_NONE = 'ÿ';
   private final AntiBotBooleanValue detectedTeamColorValue;
   private final BooleanValue teamsByServer;
   private final ModeOption aquaOption;
   private final Map<Character, Color> colorByChar = new HashMap<>();
   private final BooleanValue teamsByColor;
   private final ModeOption darkGrayOption;
   private List<UUID> playerUuids;
   private final ModeOption darkGreenOption;
   private final Map<Integer, Integer> botScoreByEntityId;
   private final ModeOption blackOption;
   private final ModeOption whiteOption;

   @Nullable
   public MutableColor getEntityTeamColor(RenderEntityContext renderEntityContext) {
      return this.getEntityTeamColor(renderEntityContext, false);
   }

   @Nullable
   public MutableColor getEntityTeamColor(RenderEntityContext renderEntityContext, boolean bypassRecolorCheck) {
      return this.resolveEntityTeamColor(renderEntityContext, bypassRecolorCheck, false);
   }

   private void refreshPlayerInfo() {
      WorldClient world = Minecraft.theWorld();
      NetHandlerPlayClientImpl connection = Minecraft.thePlayer().sendQueue();
      if (!world.isNull() && !connection.isNull()) {
         this.playerInfoList = ForgeVersion.MC_1_20_6.d()
            ? Arrays.asList(connection.getPlayerInfoMap().stream().sorted(GuiPlayerTabOverlayBridge.T()).toArray())
            : GuiPlayerTabOverlayBridge.O().sortedCopy(connection.getPlayerInfoMap());
         ArrayList<UUID> uuids = new ArrayList<>();

         for (Object playerInfoObject : this.playerInfoList) {
            PlayerInfo playerInfo = new PlayerInfo(playerInfoObject);
            if (playerInfo.isNotNull()) {
               uuids.add(playerInfo.v().getUUID());
            }
         }

         this.playerUuids = uuids;
      }
   }

   public boolean isServerTeamFilteringEnabled() {
      return this.isEnabled() && this.teamsByServer.getEffectiveValue();
   }

   @EventHandler
   public void onEntityJoinWorld(EventEntityJoinWorld eventEntityJoinWorld) {
      if (this.isAntiBotEnabled()) {
         if (MappedClasses.Yl.isAssignableFrom(eventEntityJoinWorld.getEntity().getObject().getClass())) {
            this.botJoinCount++;
         }
      }
   }

   public char findColorCodeBeforeName(String playerName, String formattedName) {
      int nameIndex;
      if (formattedName.contains(ClientSettings.FORMAT_CODE) && (nameIndex = formattedName.indexOf(playerName)) > 0) {
         for (int index = nameIndex - 1; index >= 0; index--) {
            String character = String.valueOf(formattedName.charAt(index));
            if (character.equals(ClientSettings.FORMAT_CODE)) {
               char colorCode = formattedName.charAt(index + 1);
               if (colorCode <= 'f') {
                  return colorCode;
               }
            }
         }
      }

      return 'ÿ';
   }

   public boolean isVisualRecolorEnabled() {
      return this.isColorTeamFilteringEnabled() && this.recolorVisuals.getEffectiveValue();
   }

   private void applyColorComponents(char colorCode, float[] components) {
      switch (colorCode) {
         case '0':
            components[0] = 0.0F;
            components[1] = 0.0F;
            components[2] = 0.0F;
            break;
         case '1':
            components[0] = 0.0F;
            components[1] = 0.0F;
            components[2] = 0.66F;
            break;
         case '2':
            components[0] = 0.0F;
            components[1] = 0.66F;
            components[2] = 0.0F;
            break;
         case '3':
            components[0] = 0.0F;
            components[1] = 0.66F;
            components[2] = 0.66F;
            break;
         case '4':
            components[0] = 1.0F;
            components[1] = 0.0F;
            components[2] = 0.0F;
            break;
         case '5':
            components[0] = 0.66F;
            components[1] = 0.0F;
            components[2] = 0.66F;
            break;
         case '6':
            components[0] = 1.0F;
            components[1] = 0.66F;
            components[2] = 0.0F;
            break;
         case '7':
            components[0] = 0.66F;
            components[1] = 0.66F;
            components[2] = 0.66F;
            break;
         case '8':
            components[0] = 0.33F;
            components[1] = 0.33F;
            components[2] = 0.33F;
            break;
         case '9':
            components[0] = 0.33F;
            components[1] = 0.33F;
            components[2] = 1.0F;
         case ':':
         case ';':
         case '<':
         case '=':
         case '>':
         case '?':
         case '@':
         case 'A':
         case 'B':
         case 'C':
         case 'D':
         case 'E':
         case 'F':
         case 'G':
         case 'H':
         case 'I':
         case 'J':
         case 'K':
         case 'L':
         case 'M':
         case 'N':
         case 'O':
         case 'P':
         case 'Q':
         case 'R':
         case 'S':
         case 'T':
         case 'U':
         case 'V':
         case 'W':
         case 'X':
         case 'Y':
         case 'Z':
         case '[':
         case '\\':
         case ']':
         case '^':
         case '_':
         case '`':
         default:
            break;
         case 'a':
            components[0] = 0.33F;
            components[1] = 1.0F;
            components[2] = 0.33F;
            break;
         case 'b':
            components[0] = 0.33F;
            components[1] = 1.0F;
            components[2] = 1.0F;
            break;
         case 'c':
            components[0] = 1.0F;
            components[1] = 0.33F;
            components[2] = 0.33F;
            break;
         case 'd':
            components[0] = 1.0F;
            components[1] = 0.33F;
            components[2] = 1.0F;
            break;
         case 'e':
            components[0] = 1.0F;
            components[1] = 1.0F;
            components[2] = 0.33F;
            break;
         case 'f':
            components[0] = 1.0F;
            components[1] = 1.0F;
            components[2] = 1.0F;
      }
   }

   @EventHandler
   public void onTick(EventPrePlayerTick eventPrePlayerTick) {
      if (this.isAntiBotEnabled()) {
         this.updatePingCache();
         if (this.lastWorld == null || !eventPrePlayerTick.getWorld().getObject().equals(this.lastWorld)) {
            this.pingByEntityId.clear();
            this.botScoreByEntityId.clear();
            this.trackedEntities.clear();
            this.lastWorld = eventPrePlayerTick.getWorld().getObject();
         }

         WorldClient worldClient = eventPrePlayerTick.getWorld();
         if (this.tickCounter == 1) {
            try {
               Iterator<Entry<Integer, Integer>> entityScoreIterator = this.botScoreByEntityId.entrySet().iterator();

               while (entityScoreIterator.hasNext()) {
                  Entry<Integer, Integer> entityScoreEntry = entityScoreIterator.next();
                  Integer entityId = entityScoreEntry.getKey();
                  Entity entity = worldClient.V(entityId);
                  if (entity.isNull()) {
                     entityScoreIterator.remove();
                  }
               }

               List<?> loadedEntities = worldClient.z();
               Iterator<Object> trackedEntityIterator = this.trackedEntities.iterator();

               while (trackedEntityIterator.hasNext()) {
                  if (!loadedEntities.contains(trackedEntityIterator.next())) {
                     trackedEntityIterator.remove();
                  }
               }
            } catch (Exception var18) {
               Vape.logThrowable(var18);
            }
         }

         EntityPlayerSP localPlayer = Minecraft.thePlayer();
         boolean localPlayerHasOneMillisecondPing = this.getPing(localPlayer) == 1;

         for (Object entityObject : worldClient.X()) {
            EntityPlayer entityPlayer = new EntityPlayer(entityObject);
            if (!MappedClasses.z5.isAssignableFrom(entityObject.getClass())
               && (this.botScoreByEntityId.getOrDefault(entityPlayer.S(), 0) < 3000 || !entityPlayer.n$src$Z$fx7gig())) {
               if (localPlayer.l() > 250 && entityPlayer.l() <= 2 && !this.trackedEntities.contains(entityPlayer.getObject())) {
                  double deltaX = localPlayer.z() - entityPlayer.z();
                  double deltaY = localPlayer.N() - entityPlayer.h();
                  double deltaZ = localPlayer.h() - entityPlayer.h();
                  double distance = MathUtil.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
                  String displayName = entityPlayer.Q().getFormattedText();
                  boolean hasRedDisplayName = displayName.endsWith("§c" + entityPlayer.getName() + "§r");
                  if (Math.abs(distance) > 3.0 && localPlayer.l() > 260 && this.botJoinCount <= 12 && hasRedDisplayName) {
                     this.trackedEntities.add(entityPlayer.getObject());
                     this.botScoreByEntityId.put(entityPlayer.S(), -20);
                  }
               }

               if (entityPlayer.l() <= 150 && localPlayer.getDistanceToEntity(entityPlayer) < 50.0F && localPlayer.l() > 150) {
                  double motionX = entityPlayer.M() - entityPlayer.z();
                  double motionY = entityPlayer.W() - entityPlayer.N();
                  double motionZ = entityPlayer.m$src$D$fwnne5() - entityPlayer.h();
                  double motionSquared = motionX * motionX + motionY * motionY + motionZ * motionZ;
                  if (motionSquared > 2.0
                     && motionSquared < 400.0
                     && this.trackedEntities.contains(entityPlayer.getObject())
                     && (!localPlayer.J$src$Z$fdev5g() || localPlayer.C$src$Lgg_vape_wrapper_impl_ModelPlayer_$19uhx86().H())
                     && !entityPlayer.f$src$Z$fst3rk()) {
                     int botScore = this.botScoreByEntityId.getOrDefault(entityPlayer.S(), 0);
                     if (botScore > -50000
                        && !entityPlayer.J$src$Z$fdev5g()
                        && entityPlayer.Q().getFormattedText().contains("§c" + entityPlayer.getName() + "§r")
                        && localPlayer.getDistanceToEntity(entityPlayer) < 7.5) {
                        Vape.INSTANCE
                           .getNotificationManager()
                           .show("§cInvalid Player Spawn", entityPlayer.Q().getFormattedText() + " §fmay be a fake player!", NotificationType.WARNING, 5000L);
                        this.botScoreByEntityId.put(entityPlayer.S(), -999999);
                     }

                     this.botScoreByEntityId.put(entityPlayer.S(), Math.max(botScore - 50, -50));
                  }
               }

               if (!localPlayerHasOneMillisecondPing
                  || !this.playerUuids.contains(entityPlayer.X$src$Ljava_util_UUID_$1o5dyg6())
                  || this.getPing(entityPlayer) != 1
                  || !entityPlayer.n$src$Z$fx7gig()
                  || this.botScoreByEntityId.getOrDefault(entityPlayer.S(), 0) <= 1500) {
                  int blockX = (int)Math.floor(entityPlayer.z());
                  int blockY = (int)Math.floor(entityPlayer.N() + entityPlayer.X());
                  int blockZ = (int)Math.floor(entityPlayer.h());
                  BlockState blockState = null;
                  if (ForgeVersion.MC_1_12_2.d()) {
                     blockState = worldClient.getBlockState(BlockPos.create(blockX, blockY, blockZ));
                  }

                  boolean occupiesNonSolidBlock = !worldClient.getBlockByPos(blockX, blockY, blockZ).p(blockState);
                  double verticalMotion = entityPlayer.W() - entityPlayer.N();
                  double absoluteVerticalMotion = Math.abs(verticalMotion);
                  boolean hasSolidBlockBelow = this.isSolidBlockAt(worldClient, blockX, (int)(entityPlayer.N() - (verticalMotion < 0.05 ? 0.45 : 0.9)), blockZ);
                  if (occupiesNonSolidBlock && hasSolidBlockBelow) {
                     this.botScoreByEntityId
                        .put(
                           entityPlayer.S(),
                           this.botScoreByEntityId.getOrDefault(entityPlayer.S(), 0)
                              + (absoluteVerticalMotion < 0.05 ? (entityPlayer.J$src$Z$fdev5g() ? 1 : 3) : 1)
                        );
                  } else if (entityPlayer.c$src$I$15a9iwo() <= 0 && !entityPlayer.P() && !entityPlayer.f$src$Z$fst3rk() && absoluteVerticalMotion > 0.1
                     || entityPlayer.J$src$Z$fdev5g() && (absoluteVerticalMotion == 0.0 || absoluteVerticalMotion > 0.5)) {
                     this.botScoreByEntityId
                        .put(entityPlayer.S(), this.botScoreByEntityId.getOrDefault(entityPlayer.S(), 0) - (entityPlayer.J$src$Z$fdev5g() ? 4 : 1));
                  }
               }
            }
         }

         this.botJoinCount = 0;
      }
   }

   @Override
   public void onDisable() {
      this.pingByEntityId.clear();
      this.botScoreByEntityId.clear();
      this.trackedEntities.clear();
   }

   @Nullable
   public MutableColor resolveEntityTeamColor(RenderEntityContext renderEntityContext, boolean bypassRecolorCheck, boolean allowDisabled) {
      if (!allowDisabled && !this.isEnabled()) {
         return null;
      } else if (!bypassRecolorCheck && !this.isVisualRecolorEnabled()) {
         return null;
      } else if (ForgeVersion.MC_1_21_11.d()) {
         EntityPlayer entityPlayer = renderEntityContext.getEntityPlayer();
         Integer teamColor;
         return entityPlayer != null && (teamColor = this.entityCache.getTeamColor(entityPlayer)) != null ? new MutableColor(new Color(teamColor)) : null;
      } else {
         String playerName = renderEntityContext.getName();
         String formattedName = renderEntityContext.getTypeName();
         char colorCode;
         return formattedName.startsWith(ClientSettings.FORMAT_CODE) && (colorCode = this.findColorCodeBeforeName(playerName, formattedName)) != 255
            ? new MutableColor(this.colorForChar(colorCode))
            : null;
      }
   }

   public boolean isSolidBlockAt(WorldClient world, int x, int y, int z) {
      BlockPos blockPos = BlockPos.create(x, y, z);
      BlockState blockState = world.getBlockState(blockPos);
      if (blockState.isNull()) {
         return false;
      } else {
         Block block = blockState.getBlock();
         return block.isNull() ? false : block.p(blockState) || !BlockUtil.p(block);
      }
   }

   public boolean isValidTarget(Entity entity, boolean excludeInvisible) {
      if (entity.isNull()) {
         return false;
      } else {
         EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
         if (entity.isInstance(MappedClasses.zS)) {
            return false;
         } else if (entity.equals(entityPlayerSP)) {
            return false;
         } else if (!entity.isInstance(MappedClasses.zm)) {
            return false;
         } else if (ForgeVersion.MC_1_7_10.Y() && entity.isInstance(MappedClasses.FT)) {
            return false;
         } else {
            EntityLivingBase entityLivingBase = new EntityLivingBase(entity.getObject());
            if (entityLivingBase.w$src$F$15l9epb() <= 0.0F) {
               return false;
            } else if (excludeInvisible && RotationUtil.k(entityLivingBase)) {
               return false;
            } else if (Vape.INSTANCE.getFriendManager().isFriend(entityLivingBase)) {
               return false;
            } else {
               return this.isTeammate(entityPlayerSP, entity) ? false : !this.isBot(entity);
            }
         }
      }
   }

   public boolean isSupportedBySolidBlock(WorldClient world, double positionX, double positionY, double positionZ) {
      boolean isHalfBlockHeight = MathUtil.roundToScale(positionY - (int)positionY, 1) == 0.5;
      double sampleOffset = isHalfBlockHeight ? 0.0 : 0.1;
      BlockState state = world.getBlockState(BlockPos.D(positionX, positionY - sampleOffset, positionZ));
      if (state.isNull()) {
         return false;
      } else {
         Block block = state.getBlock();
         if (block.isNull()) {
            return false;
         } else if (isHalfBlockHeight) {
            return BlockUtil.p(block) ? true : this.isSolidBlockAt(world, (int)positionX, (int)positionY, (int)positionZ);
         } else {
            return block.p(state) && !BlockUtil.p(block) ? this.isSolidBlockAt(world, (int)positionX, (int)positionY - 1, (int)positionZ) : true;
         }
      }
   }

   private void updatePingCache() {
      if (this.tickCounter >= 20) {
         this.tickCounter = 0;
      }

      if (this.tickCounter++ == 0) {
         this.pingByEntityId.clear();
         this.refreshPlayerInfo();
         List worldPlayers = Minecraft.theWorld().X();

         for (Object playerInfoObject : this.playerInfoList) {
            if (playerInfoObject != null) {
               PlayerInfo playerInfo = new PlayerInfo(playerInfoObject);

               for (Object playerObject : worldPlayers) {
                  EntityPlayer player = new EntityPlayer(playerObject);
                  GameProfile profile = player.c$src$Lgg_vape_wrapper_impl_GameProfile_$ir8937();
                  if (profile.isNotNull() && profile.equals(playerInfo.v())) {
                     int ping = Math.max(playerInfo.z(), 0);
                     this.pingByEntityId.put(player.S(), ping);
                  }
               }
            }
         }
      }
   }

   public boolean isTeammate(@Nullable EntityPlayerSP localPlayer, Entity entity) {
      if (!this.isEnabled()) {
         return false;
      } else if (!this.isColorTeamFilteringEnabled() && !this.isServerTeamFilteringEnabled()) {
         return false;
      } else {
         EntityPlayerSP resolvedLocalPlayer = localPlayer != null ? localPlayer : Minecraft.thePlayer();
         if (entity.isInstance(MappedClasses.lG)) {
            EntityOtherPlayerMP otherPlayer = new EntityOtherPlayerMP(entity);
            if (this.isColorTeamFilteringEnabled()
               && (
                  ForgeVersion.MC_1_21_11.d()
                     ? this.entityCache.hasSameTeamColor(resolvedLocalPlayer, otherPlayer)
                     : this.stateTracker.matchesOption((ModeOption)this.teamColorMode.getValue(), otherPlayer)
               )) {
               return true;
            }

            if (this.isServerTeamFilteringEnabled()) {
               Team localTeam = this.getTeam(resolvedLocalPlayer);
               if (localTeam != null && !localTeam.isNull()) {
                  Team otherTeam = this.getTeam(otherPlayer);
                  if (otherTeam != null && !otherTeam.isNull()) {
                     if (localTeam.isInstance(MappedClasses.u6) && otherTeam.isInstance(MappedClasses.u6)) {
                        ScorePlayerTeam localScoreTeam = new ScorePlayerTeam(localTeam);
                        ScorePlayerTeam otherScoreTeam = new ScorePlayerTeam(otherTeam);
                        String localTeamName = localScoreTeam.getPrefix();
                        String otherTeamName = otherScoreTeam.getPrefix();
                        if (localTeamName.equals(otherTeamName)) {
                           return true;
                        }
                     }

                     return localTeam.isSameTeam(otherTeam);
                  }

                  return false;
               }

               return false;
            }
         }

         return false;
      }
   }

   public boolean isBot(Entity entity) {
      if (!this.isAntiBotEnabled()) {
         return false;
      } else if (!ClientSettings.IS_LEGACY_1_7) {
         return false;
      } else if (entity.isInstance(MappedClasses.z5)) {
         return false;
      } else {
         if (entity.isInstance(MappedClasses.Yl)) {
            EntityPlayer entityPlayer = new EntityPlayer(entity);
            if (this.getPing(Minecraft.thePlayer()) == 1) {
               int entityPing = this.getPing(entityPlayer);
               if (entityPing != -1) {
                  return entityPing != 1;
               }
            }

            String displayName = entityPlayer.Q().getFormattedText();
            if (this.botScoreByEntityId.getOrDefault(entityPlayer.S(), 0) < 15) {
               return true;
            }

            if (entityPlayer.w$src$Z$1iu64de()) {
               return true;
            }

            boolean isMissingFromPlayerList = !this.playerUuids.contains(entityPlayer.X$src$Ljava_util_UUID_$1o5dyg6());
            if (displayName.equals("§r" + entityPlayer.getName() + "§r") || displayName.equals(entityPlayer.getName() + "§r") || displayName.contains("[NPC]")) {
               return isMissingFromPlayerList;
            }
         }

         return false;
      }
   }

   public boolean isTeammate(Entity entity) {
      return this.isTeammate(null, entity);
   }

   public ModeValue getTeamColorMode() {
      return this.teamColorMode;
   }

   private int getPing(EntityPlayer entityPlayer) {
      return this.pingByEntityId.getOrDefault(entityPlayer.S(), -1);
   }

   public AntiBot() {
      super("TargetFilter", -28416, Category.UTILITY, "");
      this.teamsByServer = BooleanValue.create(
         this,
         "Teams by server",
         false,
         "Ignore players on your team designated by the server\n§cThis is not guaranteed to be accurate, as server teams are assigned by the server"
      );
      this.teamsByColor = BooleanValue.create(
         this,
         "Teams by color",
         false,
         "Ignore players with the selected name color\n§cThis is not guaranteed to be accurate - team colors depend on the server implementation"
      );
      this.recolorVisuals = BooleanValue.create(this, "Recolor visuals", false, "Changes colors of visuals(Tracers, ESP) to their according team color");
      this.autoDetectColor = BooleanValue.create(
         this,
         "Auto-Detect color",
         true,
         "Automatically detects your team color\n§cThis is not guaranteed to be accurate, this relies on the server giving you the same name color as your teammates"
      );
      this.antiBot = BooleanValue.create(this, "AntiBot", false, "Prevents modules from attacking bots");
      this.trackedEntities = new ArrayList<>();
      this.botScoreByEntityId = new HashMap<>();
      this.pingByEntityId = new HashMap<>();
      this.playerInfoList = new ArrayList<>();
      this.playerUuids = new ArrayList<>();
      this.greenOption = new ModeOption("§aGreen", 0.8);
      this.colorCharByOption.put(this.greenOption, 'a');
      this.darkGreenOption = new ModeOption("§2Dark Green", 0.8);
      this.colorCharByOption.put(this.darkGreenOption, '2');
      this.redOption = new ModeOption("§cRed", 0.8);
      this.colorCharByOption.put(this.redOption, 'c');
      this.darkRedOption = new ModeOption("§4Dark Red", 0.8);
      this.colorCharByOption.put(this.darkRedOption, '4');
      this.yellowOption = new ModeOption("§eYellow", 0.8);
      this.colorCharByOption.put(this.yellowOption, 'e');
      this.goldOption = new ModeOption("§6Gold", 0.8);
      this.colorCharByOption.put(this.goldOption, '6');
      this.blueOption = new ModeOption("§9Blue", 0.8);
      this.colorCharByOption.put(this.blueOption, '9');
      this.darkBlueOption = new ModeOption("§1Dark Blue", 0.8);
      this.colorCharByOption.put(this.darkBlueOption, '1');
      this.aquaOption = new ModeOption("§bAqua", 0.8);
      this.colorCharByOption.put(this.aquaOption, 'b');
      this.darkAquaOption = new ModeOption("§3Dark Aqua", 0.8);
      this.colorCharByOption.put(this.darkAquaOption, '3');
      this.purpleOption = new ModeOption("§dPurple", 0.8);
      this.colorCharByOption.put(this.purpleOption, 'd');
      this.darkPurpleOption = new ModeOption("§5Dark Purple", 0.8);
      this.colorCharByOption.put(this.darkPurpleOption, '5');
      this.grayOption = new ModeOption("§7Gray", 0.8);
      this.colorCharByOption.put(this.grayOption, '7');
      this.darkGrayOption = new ModeOption("§8Dark Gray", 0.8);
      this.colorCharByOption.put(this.darkGrayOption, '8');
      this.whiteOption = new ModeOption("§fWhite", 0.8);
      this.colorCharByOption.put(this.whiteOption, 'f');
      this.blackOption = new ModeOption("§0Black", 0.8);
      this.colorCharByOption.put(this.blackOption, '0');
      this.stateTracker = new AntiBotStateTracker(this.colorCharByOption);
      this.entityCache = new AntiBotEntityCache();
      this.teamColorMode = AntiBotModeValue.create(
         this,
         "Your team color",
         "Uses this color to determine your team",
         this.greenOption,
         this.darkRedOption,
         this.redOption,
         this.goldOption,
         this.yellowOption,
         this.darkGreenOption,
         this.greenOption,
         this.aquaOption,
         this.darkAquaOption,
         this.darkBlueOption,
         this.blueOption,
         this.purpleOption,
         this.darkPurpleOption,
         this.whiteOption,
         this.grayOption,
         this.darkGrayOption,
         this.blackOption
      );
      this.detectedTeamColorValue = AntiBotBooleanValue.createWithDescription(this, "Your team color", "Shows your detected team color", null);
      if (ForgeVersion.MC_1_21_11.d()) {
         this.teamsByColor.addDependentValues(this.recolorVisuals, this.detectedTeamColorValue);
      } else {
         this.teamsByColor.addDependentValues(this.recolorVisuals, this.autoDetectColor, this.teamColorMode);
      }

      this.addValue(this.teamsByServer, this.teamsByColor, this.recolorVisuals);
      this.U(this.autoDetectColor, ForgeVersion.MC_1_21_11.b());
      this.U(this.teamColorMode, ForgeVersion.MC_1_21_11.b());
      this.U(this.detectedTeamColorValue, ForgeVersion.MC_1_21_11.n());
      this.U(this.antiBot);
   }

   public AntiBotBooleanValue getDetectedTeamColorValue() {
      return this.detectedTeamColorValue;
   }

   public boolean isColorTeamFilteringEnabled() {
      return this.isEnabled() && this.teamsByColor.getEffectiveValue();
   }

   public boolean isAntiBotEnabled() {
      return this.isEnabled() && this.antiBot.getEffectiveValue();
   }

   @Nullable
   public Team getTeam(Entity entity) {
      if (!entity.isInstance(MappedClasses.Yl)) {
         return null;
      } else {
         EntityPlayer entityPlayer = new EntityPlayer(entity);
         Team team = entityPlayer.J$src$Lgg_vape_wrapper_impl_Team_$1jrmnx4();
         if (team.isNotNull()) {
            return team;
         } else {
            GameProfile gameProfile = entityPlayer.c$src$Lgg_vape_wrapper_impl_GameProfile_$ir8937();
            UUID playerUuid = gameProfile.getUUID();

            for (Object playerInfoObject : Minecraft.N().getPlayerInfoMap()) {
               PlayerInfo playerInfo = new PlayerInfo(playerInfoObject);
               GameProfile playerInfoProfile = playerInfo.v();
               ScorePlayerTeam scoreTeam;
               if (playerInfoProfile.getUUID().equals(playerUuid) && (scoreTeam = playerInfo.X()).isNotNull()) {
                  return scoreTeam;
               }
            }

            return null;
         }
      }
   }

   private Color colorForChar(char colorCode) {
      Color cachedColor = this.colorByChar.get(colorCode);
      if (cachedColor != null) {
         return cachedColor;
      } else {
         float[] components = new float[4];
         this.applyColorComponents(colorCode, components);
         Color resolvedColor = new Color((int)(components[0] * 255.0F), (int)(components[1] * 255.0F), (int)(components[2] * 255.0F));
         this.colorByChar.put(colorCode, resolvedColor);
         return resolvedColor;
      }
   }

   @EventHandler
   public void onTick(EventPreTick eventPreTick) {
      if (this.isEnabled() && this.teamsByColor.getEffectiveValue() && (this.autoDetectColor.getEffectiveValue() || ForgeVersion.MC_1_21_11.d())) {
         EntityPlayerSP entityPlayerSP = eventPreTick.getThePlayer();
         if (!entityPlayerSP.isNull()) {
            if (ForgeVersion.MC_1_21_11.d()) {
               Integer detectedColor = this.entityCache.getTeamColor(entityPlayerSP);
               if (detectedColor != null) {
                  this.detectedTeamColorValue.setPersistenceSuppressed(true);
                  this.detectedTeamColorValue.setValue(detectedColor);
                  this.detectedTeamColorValue.setPersistenceSuppressed(false);
               }
            } else {
               char detectedColorCode = this.stateTracker.detectColorCode(entityPlayerSP);
               ModeOption modeOption;
               if (detectedColorCode != 255 && (modeOption = this.stateTracker.findOptionByColorCode(detectedColorCode)) != null) {
                  this.teamColorMode.setPersistenceSuppressed(true);
                  this.teamColorMode.setValue((ModeSelection)modeOption);
                  this.teamColorMode.setPersistenceSuppressed(false);
               }
            }
         }
      }
   }
}
