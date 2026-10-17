package gg.vape;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import gg.vape.account.AccountInfo;
import gg.vape.account.AccountInfoResponse;
import gg.vape.account.LicenseManager;
import gg.vape.api.ApiAccessTokenProvider;
import gg.vape.api.ApiResponse;
import gg.vape.api.ApiServices;
import gg.vape.asm.helper.DescUtils;
import gg.vape.combat.AttackPacketTimingTracker;
import gg.vape.combat.AttackStrengthTracker;
import gg.vape.config.ClientSettings;
import gg.vape.config.ConfigJsonUtils;
import gg.vape.config.ModuleProfileMetadataCodec;
import gg.vape.config.Profile;
import gg.vape.config.PublicProfileSettings;
import gg.vape.event.ClientListenerBootstrap;
import gg.vape.event.EventBus;
import gg.vape.event.EventNameFormatRewriteService;
import gg.vape.event.IEvent;
import gg.vape.event.impl.EventRenderWorldPassExecutorDrain;
import gg.vape.event.listener.ClientSettingsEventForwarder;
import gg.vape.event.listener.EventTimingOverlayListener;
import gg.vape.event.listener.VapeClientEventListener;
import gg.vape.event.listener.VapeLifecycleEventListener;
import gg.vape.event.listener.VapeShutdownEventListener;
import gg.vape.event.listener.WorldChangeEventDispatcher;
import gg.vape.friend.FriendAliasEventListener;
import gg.vape.friend.OnlineFriendActivityListener;
import gg.vape.friend.ping.PingManager;
import gg.vape.friend.ui.OnlineActivityPanelOptions;
import gg.vape.input.InputEventDispatcher;
import gg.vape.lifecycle.ClientDirectoryCleanupCallback;
import gg.vape.lifecycle.VapeErrorLogger;
import gg.vape.manager.MacroManager;
import gg.vape.manager.ModManager;
import gg.vape.manager.SearchManager;
import gg.vape.manager.ValueManager;
import gg.vape.manager.client.EnemyManager;
import gg.vape.manager.client.FriendManager;
import gg.vape.manager.client.IndependentSettingsManager;
import gg.vape.manager.client.OnlineConnectionManager;
import gg.vape.manager.client.OnlineFriendManager;
import gg.vape.manager.client.OnlineManager;
import gg.vape.manager.client.ProfilesManager;
import gg.vape.manager.client.PublicProfileManager;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapper;
import gg.vape.mapping.MappingProfileSnapshotRegistry;
import gg.vape.mapping.PrimaryMappingTaskSet;
import gg.vape.mapping.runtime.RuntimeNameMappingRegistry;
import gg.vape.module.blatant.AntiBot;
import gg.vape.module.macro.MacroEventListener;
import gg.vape.module.render.entity.RenderEntityContextCacheListener;
import gg.vape.module.render.nametags.NameTagsRenderStateTracker;
import gg.vape.module.utility.inventory.ItemStackSemanticResolver;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterPresetRegistry;
import gg.vape.module.utility.inventory.cleaner.ui.InventoryCleanerProfileValueRefreshListener;
import gg.vape.movement.PlayerMovementTaskManager;
import gg.vape.notification.NotificationManager;
import gg.vape.notification.NotificationSoundPlayer;
import gg.vape.rotation.RotationManager;
import gg.vape.runtime.NativeBridge;
import gg.vape.status.NativePresenceUpdater;
import gg.vape.sync.SyncThread;
import gg.vape.tutorial.TutorialManager;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.value.FloatingValueDropdownLayer;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.font.FontManager;
import gg.vape.ui.font.FontOption;
import gg.vape.ui.font.FontSelector;
import gg.vape.unmap.BendableInputDispatcher;
import gg.vape.unmap.GLUtils;
import gg.vape.unmap.ItemHelper;
import gg.vape.utils.AttackCooldownUtil;
import gg.vape.utils.Base64Util;
import gg.vape.utils.TimerUtil;
import gg.vape.utils.network.PacketDispatchGuard;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.utils.render.OpenGlDeviceInfo;
import gg.vape.utils.render.RenderBatchManager;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.PotionRegistry;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Vape {
   private static int opaqueState;
   public static Vape INSTANCE;
   public static boolean mappingsLoaded = false;
   public boolean enabled;
   public static boolean renderReady = false;
   public static final boolean DEBUG = false;
   public static final boolean DEV = false;
   public static final String VERSION = "4.22";
   static TimerUtil loadTimer = new TimerUtil();
   private boolean forgeAbsent;
   private boolean mappingsRemapped;
   private boolean vanillaMinecraftPresentCache;
   private boolean renderInitialized;
   private boolean vanillaMinecraftChecked;
   private boolean pendingTickAction;
   private boolean unclassifiedFlag463;
   private FriendManager friendManager;
   private FontManager fontManager;
   private ItemHelper itemHelper;
   private ProfilesManager profilesManager;
   private ClientListenerBootstrap clientListenerBootstrap;
   private PublicProfileSettings publicProfileSettings;
   private InventoryFilterPresetRegistry inventoryFilterPresetRegistry;
   private NotificationSoundPlayer notificationSoundPlayer;
   private GLUtils glUtils;
   private SearchManager searchManager;
   private EnemyManager enemyManager;
   private PublicProfileManager publicProfileManager;
   private AccountInfo accountInfo;
   private MacroManager macroManager;
   private String cachedAllData;
   private OnlineFriendManager onlineFriendManager;
   private ModManager modManager;
   private LicenseManager licenseManager;
   private IndependentSettingsManager independentSettingsManager;
   private Boolean isLabyModCache;
   private PrimaryMappingTaskSet primaryMappingTaskSet;
   private OnlineManager onlineManager;
   private ValueManager valueManager;
   private FontSelector fontSelector;
   private Mapper mapper;
   private NotificationManager notificationManager;
   private ItemStackSemanticResolver itemStackSemanticResolver;
   private ClientSettings clientSettings;
   private NativePresenceUpdater nativePresenceUpdater;
   private Boolean isFabricPresentCache;
   private ModuleProfileMetadataCodec moduleProfileMetadataCodec;
   private Object directoryCleanupCallback;
   private SyncThread syncThread;
   private TutorialManager tutorialManager;
   private static Date buildDate = new Date(1710640988922L);
   private static int[] supportedVersionIds = new int[]{13, 15, 23, 35, 36, 50, 61, 100, 110};

   public boolean isOnlineConnected() {
      return OnlineConnectionManager.INSTANCE.getGlobalSettingsController().isFirstRun();
   }

   public boolean isMappingsRemapped() {
      return this.mappingsRemapped;
   }

   public void loadConfigData(JsonObject configRoot, boolean useNewOtherDataKey) {
      JsonArray friendsArray = ConfigJsonUtils.getJsonArray(configRoot, "friends");
      if (friendsArray != null) {
         this.friendManager.loadFriends(friendsArray);
      }

      if (ConfigJsonUtils.getJsonObject(configRoot, "profiles") != null) {
         JsonObject profilesData = configRoot.get("profiles").getAsJsonObject();
         this.profilesManager.loadJson(profilesData);
      } else {
         debugLog("profilesData is NULL!");
      }

      JsonArray otherData = ConfigJsonUtils.getJsonArray(configRoot, useNewOtherDataKey ? "otherData" : "otherdata");
      if (otherData != null && otherData.size() > 0) {
         this.independentSettingsManager.loadIndependentSettings(otherData);
      } else {
         debugLog("otherData is NULL!");
      }

      INSTANCE.getProfilesManager().updatePublicProfileLinks();
   }

   public MacroManager getMacrosManager() {
      return this.macroManager;
   }

   public void loadMappings() {
      this.traceStep(18);
      int opaqueSeed = getOpaqueState();
      MappedClasses.p();
      mappingsLoaded = true;
      if (opaqueSeed != 0) {
         this.traceStep(19);
         if (this.forgeAbsent && ForgeVersion.MC_26_1.v()) {
            NativeBridge.fs();
            MappedClasses.p();
            NativeBridge.rsc();
         }

         MappingProfileSnapshotRegistry.X();
         RuntimeNameMappingRegistry.initializeRegistry();
         this.traceStep(20);
         this.mapper = new Mapper();
         this.mapper.loadMappings();
         this.traceStep(21);
         MappingProfileSnapshotRegistry.y();
         if (this.isForgeRemapActive()) {
            NativeBridge.fs();
            Mapper.RF.clear();
            this.mappingsRemapped = true;
            this.mapper = new Mapper();
            this.mapper.loadMappings();
         } else {
            this.mappingsRemapped = false;
         }

         MappingProfileSnapshotRegistry.h();
         this.traceStep(22);
         NativeBridge.su(Minecraft.Q$src$Lgg_vape_account_MinecraftSessionWrapper_$1ftnn3u().getUsername());
         if (GuiComponent.getLegacyComponentState() == null) {
            int var3 = opaqueSeed + 1;
            setOpaqueState(var3);
         }
      } else {
         this.traceStep(19);
         this.mapper = new Mapper();
         this.mapper.loadMappings();
         this.traceStep(21);
         MappingProfileSnapshotRegistry.y();
         this.mappingsRemapped = false;
         MappingProfileSnapshotRegistry.h();
         this.traceStep(22);
         NativeBridge.su(Minecraft.Q$src$Lgg_vape_account_MinecraftSessionWrapper_$1ftnn3u().getUsername());
         if (GuiComponent.getLegacyComponentState() == null) {
            int opaqueBranch = opaqueSeed + 1;
            setOpaqueState(opaqueBranch);
         }
      }
   }

   public static String formatThrowable(Throwable error) {
      StringWriter stackTraceWriter = new StringWriter();
      PrintWriter stackTracePrinter = new PrintWriter(stackTraceWriter);
      error.printStackTrace(stackTracePrinter);
      return "\nException " + error.getClass().getCanonicalName() + " " + stackTraceWriter.toString();
   }

   public FriendManager getFriendManager() {
      return this.friendManager;
   }

   public InventoryFilterPresetRegistry getInventoryFilterPresetRegistry() {
      return this.inventoryFilterPresetRegistry;
   }

   private void logInitError(String phase, Throwable error) {
      StringBuilder message = new StringBuilder();
      message.append(RenderBatchManager.buildInitializationDiagnostics(phase, error));
      message.append(formatThrowable(error));
      logError(message.toString());
      logThrowable(error);
   }

   public static void setOpaqueState(int value) {
      opaqueState = value;
   }

   public void setPendingTickAction(boolean pending) {
      this.pendingTickAction = pending;
   }

   public Mapper getMappings() {
      return this.mapper;
   }

   public static void notifyNativeStackTrace() {
      DescUtils.traceStack();
      NativeBridge.mb(101);
   }

   private static boolean lambda$registerListeners$0(IEvent event) {
      return AttackStrengthTracker.INSTANCE.isHealthPredictionEnabled();
   }

   public SearchManager getSearch() {
      return this.searchManager;
   }

   public EnemyManager getEnemyManager() {
      return this.enemyManager;
   }

   public boolean isForgeRemapInactive() {
      return !this.isForgeRemapActive();
   }

   public NotificationSoundPlayer getNotificationSoundPlayer() {
      return this.notificationSoundPlayer;
   }

   public FontSelector getFontSelector() {
      return this.fontSelector;
   }

   public void setUnclassifiedFlag463(boolean value) {
      this.unclassifiedFlag463 = value;
   }

   public boolean isFabricMinecraftPresent() {
      if (this.isFabricPresentCache == null) {
         String fabricMinecraftClass = "net/minecraft/class_310";
         this.isFabricPresentCache = NativeBridge.gc(fabricMinecraftClass) != null;
      }

      return this.isFabricPresentCache;
   }

   public ItemHelper getItemHelper() {
      return this.itemHelper;
   }

   public AccountInfo getAccountInfo() {
      return this.accountInfo;
   }

   public boolean isForgeRemapActive() {
      return this.isLabyModPresent() ? false : ForgeVersion.MC_26_1.v();
   }

   public static byte[] readResource(String resourceName) {
      String resourcePath = "resources/" + resourceName;
      return NativeBridge.gfb(resourcePath);
   }

   public boolean initAccountInfo() {
      ApiAccessTokenProvider.getAccessToken();

      try {
         ApiResponse<AccountInfoResponse> response = ApiServices.getInstance().getAccountInfo().join();
         if (response != null && response.isSuccessful() && response.getData() != null) {
            AccountInfo initializedAccount = AccountInfo.fromResponse(response.getData());
            if (initializedAccount == null) {
               logError("Failed to initialize account details1: empty account data");
               return false;
            } else {
               this.accountInfo = initializedAccount;
               return true;
            }
         } else {
            String error = response == null ? "empty response" : response.getError();
            logError("Failed to initialize account details1: " + error);
            return false;
         }
      } catch (CompletionException | CancellationException var3) {
         logError("Failed to initialize account details2: " + formatThrowable(var3));
         return false;
      }
   }

   public boolean isFeatureDisabled() {
      return false;
   }

   public ModManager getModManager() {
      return this.modManager;
   }

   public ClientSettings getClientSettings() {
      return this.clientSettings;
   }

   public void initializeManagers() {
      this.glUtils = new GLUtils();
      this.glUtils.initializeVertexBuffer(24, 1);
      this.itemHelper = new ItemHelper();
      this.publicProfileManager = new PublicProfileManager();
      opaquePredicate();
      this.fontSelector = new FontSelector();
      this.valueManager = new ValueManager();
      this.independentSettingsManager = new IndependentSettingsManager();
      this.friendManager = new FriendManager();
      this.onlineFriendManager = new OnlineFriendManager();
      this.enemyManager = new EnemyManager();
      this.macroManager = new MacroManager();
      this.searchManager = new SearchManager();
      this.clientSettings = new ClientSettings();
      this.publicProfileSettings = new PublicProfileSettings();
      this.modManager = new ModManager();
      this.modManager.init();
      this.clientSettings.antiBot = this.modManager.getMod(AntiBot.class);
      this.moduleProfileMetadataCodec = new ModuleProfileMetadataCodec();
      this.traceStep(23);
      this.initPrimaryMappingTasks();
      this.traceStep(24);
      this.itemStackSemanticResolver = new ItemStackSemanticResolver();
      this.itemStackSemanticResolver.loadMappings();

      try {
         this.itemStackSemanticResolver.reportMissingMappings();
      } catch (Throwable var3) {
      }

      PotionRegistry.d();
      this.inventoryFilterPresetRegistry = new InventoryFilterPresetRegistry();
      this.profilesManager = new ProfilesManager();
      this.modManager.initializeAllModules();
      this.notificationManager = new NotificationManager();
      this.tutorialManager = new TutorialManager();
      this.syncThread.loadConfig();
      this.traceStep(25);
      this.modManager.finishModuleInitialization();
      if (this.publicProfileSettings.getSelectedProfile() != null) {
         this.getProfilesManager().setActiveProfile(this.publicProfileSettings.getSelectedProfile());
      } else {
         this.getProfilesManager().getActiveProfile();
      }

      if (this.profilesManager.getActiveProfile() != null) {
         if (INSTANCE.getPublicProfileSettings().autoLoadModuleStates.getEffectiveValue()) {
            this.profilesManager.getActiveProfile().applyEnabledModuleStates();
         }

         this.profilesManager.getActiveProfile().applyLegitEnabledModuleStates();
      }

      this.traceStep(26);
      this.onlineManager = new OnlineManager();
      this.traceStep(27);
      this.notificationSoundPlayer = new NotificationSoundPlayer();
      InputEventDispatcher.getInstance().registerHandlers();
      this.initClientListeners();
      this.showLoadCompleteNotification();
      this.syncThread.clearPendingSave();
      this.getFontSelector().N((FontOption)INSTANCE.getPublicProfileSettings().language.getValue());
      this.syncThread.start();
      this.traceStep(28);
      NativeBridge.dc();
      this.registerEventListeners();

      try {
         OnlineConnectionManager.INSTANCE.initialize();
      } catch (Exception var2) {
         logThrowable(var2);
      }
   }

   public ItemStackSemanticResolver getItemStackResolver() {
      return this.itemStackSemanticResolver;
   }

   public boolean isVanillaMinecraftPresent() {
      if (this.vanillaMinecraftChecked) {
         return this.vanillaMinecraftPresentCache;
      } else {
         this.vanillaMinecraftChecked = true;
         if (!this.forgeAbsent) {
            return false;
         } else if (ForgeVersion.MC_26_1.d()) {
            return false;
         } else {
            String vanillaMinecraftClass = "net/minecraft/client/Minecraft";
            this.vanillaMinecraftPresentCache = NativeBridge.gc(vanillaMinecraftClass) != null;
            return this.vanillaMinecraftPresentCache;
         }
      }
   }

   public static int opaquePredicate() {
      int state = getOpaqueState();
      return state == 0 ? 20 : 0;
   }

   public boolean isUnclassifiedFlag463Set() {
      return this.unclassifiedFlag463;
   }

   public ModuleProfileMetadataCodec getModuleProfileMetadataCodec() {
      return this.moduleProfileMetadataCodec;
   }

   public boolean isLabyModPresent() {
      if (this.isLabyModCache != null) {
         return this.isLabyModCache;
      } else if (!ForgeVersion.MC_1_8_9.L()) {
         this.isLabyModCache = false;
         return false;
      } else {
         try {
            Class.forName("net.laby.launcher.classloading.LabyClassLoader", false, Vape.class.getClassLoader());
            this.isLabyModCache = true;
         } catch (Throwable var2) {
            this.isLabyModCache = false;
         }

         return this.isLabyModCache;
      }
   }

   public int getAccountTier() {
      return this.accountInfo == null ? 0 : (int)this.accountInfo.getUserId();
   }

   public NotificationManager getNotificationManager() {
      return this.notificationManager;
   }

   public TutorialManager getTutorialManager() {
      return this.tutorialManager;
   }

   public PrimaryMappingTaskSet getPrimaryMappingTaskSet() {
      return this.primaryMappingTaskSet;
   }

   public ValueManager getValueManager() {
      return this.valueManager;
   }

   public NativePresenceUpdater getNativePresenceUpdater() {
      return this.nativePresenceUpdater;
   }

   public boolean isTickActionPending() {
      return this.pendingTickAction;
   }

   public void saveAndStop() {
      this.syncThread.markDirty();
      Profile activeProfile;
      if (this.profilesManager != null && (activeProfile = this.profilesManager.getActiveProfileOrNull()) != null) {
         activeProfile.setDirty(true);
      }
   }

   public void exportFramesConfig(String filePath) {
      try {
         String output = this.modManager.buildTranslationTemplate();

         for (Frame frame : gg.vape.module.none.ClientSettings.getAllFrames()) {
            if (!(frame instanceof FloatingValueDropdownLayer) && frame.J$src$Z$1eqdghz()) {
               String frameLine = "frame." + frame.getName().toLowerCase().replace(" ", "_") + "=" + frame.getName();
               output = output + frameLine + "\n";
            }
         }

         FileOutputStream fileOut = new FileOutputStream(filePath);
         fileOut.write(output.getBytes());
      } catch (Exception var6) {
         logThrowable(var6);
         var6.printStackTrace();
      }
   }

   public FontManager getFontManager() {
      return this.fontManager;
   }

   public OnlineManager getOnlineManager() {
      return this.onlineManager;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public static boolean isSupportedVersion() {
      for (int versionId : supportedVersionIds) {
         if (versionId == ForgeVersion.c()) {
            return true;
         }
      }

      return false;
   }

   private static boolean lambda$registerListeners$1(IEvent event) {
      return EventBus.timingEnabled;
   }

   public void initClientListeners() {
      this.primaryMappingTaskSet.X();
      this.primaryMappingTaskSet.d();
      this.clientListenerBootstrap = new ClientListenerBootstrap();
      this.clientListenerBootstrap.registerListeners();
   }

   private void registerEventListeners() {
      EventBus.getInstance().registerListener(PacketDispatchGuard.b);
      EventBus.getInstance().registerListener(new BendableInputDispatcher());
      EventBus.getInstance().registerListener(AttackPacketTimingTracker.INSTANCE);
      EventBus.getInstance().registerListener(PingManager.INSTANCE);
      EventBus.getInstance().registerListener(RotationManager.INSTANCE);
      EventBus.getInstance().registerListener(PlayerMovementTaskManager.INSTANCE);
      EventBus.getInstance().registerListener(OnlineFriendActivityListener.INSTANCE);
      EventBus.getInstance().registerListener(OnlineActivityPanelOptions.INSTANCE);
      EventBus.getInstance().registerListener(AttackStrengthTracker.INSTANCE, Vape::lambda$registerListeners$0);
      EventBus.getInstance().registerListener(new ClientSettingsEventForwarder());
      EventBus.getInstance().registerListener(new WorldChangeEventDispatcher());
      EventBus.getInstance().registerListener(new RenderEntityContextCacheListener());
      EventBus.getInstance().registerListener(new MacroEventListener());
      EventBus.getInstance().registerListener(new InventoryCleanerProfileValueRefreshListener());
      EventBus.getInstance().registerListener(EventTimingOverlayListener.INSTANCE, Vape::lambda$registerListeners$1);
      EventBus.getInstance().registerListener(new VapeShutdownEventListener());
      EventBus.getInstance().registerListener(NameTagsRenderStateTracker.INSTANCE);
      EventBus.getInstance().registerListener(this.notificationManager);
      EventBus.getInstance().registerListener(new AttackCooldownUtil());
      EventBus.getInstance().registerListener(this.modManager);
      if (ForgeVersion.MC_1_8_9.L()) {
         EventBus.getInstance().registerListener(new VapeLifecycleEventListener());
      }

      if (ForgeVersion.MC_1_16_5.d()) {
         EventBus.getInstance().registerListener(new EventNameFormatRewriteService());
      } else {
         EventBus.getInstance().registerListener(new FriendAliasEventListener());
      }

      if (ForgeVersion.MC_1_21_10.d()) {
         EventBus.getInstance().registerListener(new VapeClientEventListener());
      }
   }

   private void showLoadCompleteNotification() {
      gg.vape.module.none.ClientSettings clientSettingsModule = INSTANCE.getModManager().getMod(gg.vape.module.none.ClientSettings.class);
      if (clientSettingsModule.guiBindIndicator.getEffectiveValue()) {
         this.notificationManager.showInfo("Finished Loading", "Press " + clientSettingsModule.getBind().getBindText() + " to open GUI", 5000L);
      }
   }

   public boolean isForgeAbsent() {
      return this.forgeAbsent;
   }

   public ProfilesManager getProfilesManager() {
      return this.profilesManager;
   }

   public void initializeRender() {
      if (!this.renderInitialized) {
         try {
            OpenGlDeviceInfo.collectDeviceInfo();
            if (GuiRenderPrimitives.d()) {
               try {
                  RenderBatchManager.getInstance();
               } catch (Throwable var5) {
                  this.logInitError("RenderEngine initialization", var5);
                  return;
               }
            }

            try {
               this.fontManager = new FontManager();
               this.fontManager.e();
            } catch (Throwable var4) {
               this.logInitError("FontManager initialization", var4);
               return;
            }

            try {
               ImageRenderer.preloadResources();
            } catch (Throwable var3) {
               this.logInitError("DrawTexture pre-cache", var3);
               return;
            }

            try {
               GuiRenderPrimitives.B();
            } catch (Throwable var2) {
               this.logInitError("VapeRender initialization", var2);
               return;
            }

            this.renderInitialized = true;
         } catch (Throwable var6) {
            logThrowable(var6);
         }
      }
   }

   public void traceStep(int step) {
      NativeBridge.trs(step);
   }

   public static void logThrowable(Throwable error) {
      try {
         VapeErrorLogger.getInstance().logThrowable(error);
      } catch (Throwable var2) {
      }

      debugLog(formatThrowable(error));
   }

   public LicenseManager getLicenseManager() {
      return this.licenseManager;
   }

   public GLUtils getGlUtils() {
      return this.glUtils;
   }

   public static void logTimed(String message) {
      NativeBridge.printLog(String.format("[%02d] %s", System.currentTimeMillis(), message + " " + loadTimer.getLastMS()));
      loadTimer.reset();
   }

   private void initPrimaryMappingTasks() {
      int opaqueSeed = opaquePredicate();
      this.primaryMappingTaskSet = new PrimaryMappingTaskSet();
      if (NativeBridge.isBadlion189Runtime()) {
         FutureTask<Void> initializationTask = new FutureTask<>(() -> {
            gg.vape.module.none.ClientSettings.initializeFrames();
            return null;
         });
         Minecraft.v(initializationTask);

         try {
            initializationTask.get(30L, TimeUnit.SECONDS);
         } catch (InterruptedException var6) {
            Thread.currentThread().interrupt();
            initializationTask.cancel(false);
            throw new CompletionException(var6);
         } catch (ExecutionException var7) {
            Throwable cause = var7.getCause();
            throw new CompletionException((Throwable)(cause == null ? var7 : cause));
         } catch (TimeoutException var8) {
            initializationTask.cancel(false);
            throw new CompletionException(var8);
         }
      } else {
         this.primaryMappingTaskSet.L();
         this.primaryMappingTaskSet.d();
         int opaqueBranch = opaqueSeed;
         EventRenderWorldPassExecutorDrain.EXECUTOR.execute(gg.vape.module.none.ClientSettings::initializeFrames);

         try {
            while (!gg.vape.module.none.ClientSettings.framesInitialized) {
               try {
                  Thread.sleep(10L);
                  if (opaqueBranch != 0) {
                     return;
                  }
               } catch (InterruptedException var9) {
                  logThrowable(var9);
                  if (opaqueBranch == 0) {
                     continue;
                  }
                  break;
               }
            }
         } catch (CompletionException var10) {
            CompletionException completionException = var10;

            try {
               throw (CompletionException)rethrow(completionException);
            } catch (CompletionException var5) {
               throw (CompletionException)rethrow(var5);
            }
         }

         this.primaryMappingTaskSet.C();
      }
   }

   public OnlineFriendManager getOnlineFriendManager() {
      return this.onlineFriendManager;
   }

   public IndependentSettingsManager getSettingsManager() {
      return this.independentSettingsManager;
   }

   public static void logError(String message) {
      try {
         VapeErrorLogger.getInstance().logError(message);
      } catch (Throwable var2) {
      }

      NativeBridge.sce(message);
   }

   public static void debugLog(String message) {
      String normalizedMessage = message == null ? "<null>" : message;

      try {
         NativeBridge.sce("DEBUG " + normalizedMessage);
      } catch (Throwable var3) {
         System.err.println("[Vape421] " + normalizedMessage);
      }
   }

   private static <T extends Throwable> T rethrow(T error) {
      return error;
   }

   public SyncThread getSyncThread() {
      return this.syncThread;
   }

   public PublicProfileManager getPublicProfileManager() {
      return this.publicProfileManager;
   }

   public PublicProfileSettings getPublicProfileSettings() {
      return this.publicProfileSettings;
   }

   public static int getOpaqueState() {
      return opaqueState;
   }

   public Vape() {
      int opaqueBranch = opaquePredicate();
      if (opaqueBranch != 0) {
         this.nativePresenceUpdater = new NativePresenceUpdater();
         this.syncThread = new SyncThread(this);
         this.vanillaMinecraftChecked = false;
         this.vanillaMinecraftPresentCache = false;
         this.isFabricPresentCache = null;
         this.renderInitialized = false;
         this.isLabyModCache = null;
         this.traceStep(17);
         INSTANCE = this;
         this.forgeAbsent = NativeBridge.isForgeAbsent();
         this.directoryCleanupCallback = new ClientDirectoryCleanupCallback();
         VapeErrorLogger.getInstance().installUncaughtExceptionHandler();
         VapeErrorLogger.getInstance().syncNativeLogs();
         GuiComponent.setLegacyComponentState(new GuiComponent[4]);
      } else {
         this.nativePresenceUpdater = new NativePresenceUpdater();
         this.syncThread = new SyncThread(this);
         this.vanillaMinecraftChecked = false;
         this.vanillaMinecraftPresentCache = false;
         this.isFabricPresentCache = null;
         this.renderInitialized = false;
         this.isLabyModCache = null;
         this.traceStep(17);
         INSTANCE = this;
         this.forgeAbsent = NativeBridge.isForgeAbsent();
         this.directoryCleanupCallback = new ClientDirectoryCleanupCallback();
         VapeErrorLogger.getInstance().installUncaughtExceptionHandler();
         VapeErrorLogger.getInstance().syncNativeLogs();
      }
   }

   public synchronized String getDecodedAllData(boolean refresh) {
      label28: {
         try {
            if (refresh && this.cachedAllData == null) {
               break label28;
            }
         } catch (CompletionException var5) {
            throw (CompletionException)rethrow(var5);
         }

         try {
            if (this.cachedAllData == null) {
               return "";
            }

            return this.cachedAllData;
         } catch (CompletionException var4) {
            throw (CompletionException)rethrow(var4);
         }
      }

      String encoded = NativeBridge.gp("all");
      byte[] decoded = Base64Util.decodeBase64(encoded);
      this.cachedAllData = new String(decoded);
      return this.cachedAllData;
   }

   public Mapper getMappingsCompat() {
      return this.getMappings();
   }

   public Mapper getMappingsMapperCompat() {
      return this.getMappings();
   }

   static {
      setOpaqueState(66);
   }
}
