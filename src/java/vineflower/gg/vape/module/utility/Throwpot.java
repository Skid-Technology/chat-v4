package gg.vape.module.utility;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPreTick;
import gg.vape.input.KeyBindingHelper;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.UtilityMod;
import gg.vape.module.utility.invcleaner.ItemDataComparator;
import gg.vape.unmap.ModeOption;
import gg.vape.utils.ItemStackScoreUtil;
import gg.vape.utils.datas.ItemStackData;
import gg.vape.value.BooleanValue;
import gg.vape.value.ModeValue;
import gg.vape.value.NumberValue;
import gg.vape.value.RandomValue;
import gg.vape.wrapper.impl.GameSettings;
import gg.vape.wrapper.impl.InventoryPlayer;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemSplashPotion;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.PotionEffect;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

public class Throwpot extends UtilityMod {
   private final CopyOnWriteArrayList<ItemStackData> itemsToThrow;
   private final NumberValue scrollDelay;
   private final ModeValue modeValue;
   private int savedSlot;
   private final ModeValue typeValue;
   private final ModeOption singleOption;
   private final ModeOption potsOption;
   private final BooleanValue scrollValue;
   private final ModeOption bothOption = new ModeOption("Both");
   private final ModeOption dynamicOption;
   private final BooleanValue throwBowls;
   private boolean active;
   private final BooleanValue randomValue;
   private final RandomValue delayValue;
   private final ModeOption soupOption;

   @Override
   public void onEnable() {
      if (this.active) {
         this.setEnabled(false);
      } else if (!Minecraft.thePlayer().isNull() && !Minecraft.currentScreen().isNotNull()) {
         if (!this.active && this.collectHealingItems()) {
            InventoryPlayer inventory = Minecraft.thePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6();
            this.savedSlot = inventory.v();
            this.active = true;
            this.v(0L, false);
         } else {
            this.itemsToThrow.clear();
            this.setEnabled(false);
         }
      } else {
         this.setEnabled(false);
      }
   }

   private boolean collectHealingItems() {
      ArrayList<Integer> hotbarSlots = new ArrayList<>();

      for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++) {
         hotbarSlots.add(hotbarSlot);
      }

      if (this.randomValue.getEffectiveValue()) {
         Collections.shuffle(hotbarSlots);
      }

      Object[] hotbarContents = Minecraft.thePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().M();
      int queuedHealing = 0;

      for (Integer hotbarSlot : hotbarSlots) {
         ItemStack itemStack = new ItemStack(hotbarContents[hotbarSlot]);
         Item item;
         if (!itemStack.isNull() && !(item = itemStack.getItem()).isNull()) {
            boolean soupEnabled = this.typeValue.getValue().equals(this.soupOption) || this.typeValue.getValue().equals(this.bothOption);
            boolean potsEnabled = this.typeValue.getValue().equals(this.potsOption) || this.typeValue.getValue().equals(this.bothOption);
            if (ItemStackScoreUtil.v(item) && soupEnabled) {
               if (this.modeValue.getValue().equals(this.singleOption)) {
                  this.itemsToThrow.add(new ItemStackData(hotbarSlot, itemStack));
                  break;
               }

               int soupHealing = 8;
               if (queuedHealing + soupHealing + Math.floor(Minecraft.thePlayer().w$src$F$15l9epb()) > Minecraft.thePlayer().I$src$F$14vyvep()) {
                  continue;
               }

               queuedHealing += soupHealing;
               this.itemsToThrow.add(new ItemStackData(hotbarSlot, itemStack));
            }

            if (MappedClasses.Di.isInstance(item.getObject()) && potsEnabled && ItemSplashPotion.isSplashPotion(itemStack)) {
               if (this.modeValue.getValue().equals(this.singleOption) && ItemStackScoreUtil.i(itemStack)) {
                  this.itemsToThrow.add(new ItemStackData(hotbarSlot, itemStack));
                  break;
               }

               ItemSplashPotion splashPotion = new ItemSplashPotion(item.getObject());
               PotionEffect potionEffect = new PotionEffect(splashPotion.getRawPotionEffects(itemStack).get(0));
               int potionHealing = 4 * (potionEffect.L() + 1);
               if (ItemStackScoreUtil.i(itemStack)
                  && !(queuedHealing + potionHealing + Math.floor(Minecraft.thePlayer().w$src$F$15l9epb()) > Minecraft.thePlayer().I$src$F$14vyvep())) {
                  queuedHealing += potionHealing;
                  this.itemsToThrow.add(new ItemStackData(hotbarSlot, itemStack));
               }
            }
         }
      }

      return !this.itemsToThrow.isEmpty();
   }

   @Override
   public void onScheduledAction() {
      if (this.active) {
         try {
            GameSettings gameSettings = Minecraft.gameSettings();
            KeyBinding useKey = gameSettings.b$src$Lgg_vape_wrapper_impl_KeyBinding_$1yi3362();
            KeyBinding dropKey = gameSettings.v$src$Lgg_vape_wrapper_impl_KeyBinding_$11ijh0e();
            this.itemsToThrow.sort(new ItemDataComparator(this.savedSlot));
            boolean restoreUseKey = false;

            for (ItemStackData itemToThrow : this.itemsToThrow) {
               this.selectHotbarSlot(itemToThrow.getSlot());
               if (useKey.isKeyDown() && ItemStackScoreUtil.v(itemToThrow.getItemStack().getItem())) {
                  KeyBindingHelper.updateKeyBinding(useKey, false, false);
                  Thread.sleep(51L);
                  restoreUseKey = true;
               }

               KeyBindingHelper.setPressedAndTick(useKey, true);
               Thread.sleep(51L);
               KeyBindingHelper.updateKeyBinding(useKey, false, false);
               if (this.throwBowls.getEffectiveValue() && ItemStackScoreUtil.v(itemToThrow.getItemStack().getItem())) {
                  KeyBindingHelper.setPressedAndTick(dropKey, true);
                  Thread.sleep(51L);
                  KeyBindingHelper.updateKeyBinding(dropKey, false, false);
               }

               Thread.sleep((long)this.delayValue.getRandomValue());
            }

            this.selectHotbarSlot(this.savedSlot);
            if (restoreUseKey) {
               KeyBindingHelper.setPressedAndTick(useKey, true);
            }
         } catch (Exception var7) {
            this.active = false;
            var7.printStackTrace();
         }

         this.active = false;
      }
   }

   @EventHandler
   public void onTick(EventPreTick event) {
      if (!this.active && this.isEnabled()) {
         this.setEnabled(false);
      }
   }

   private void selectHotbarSlot(int targetSlot) {
      if (!this.scrollValue.getEffectiveValue()) {
         Minecraft.thePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(targetSlot);
      } else {
         int currentSlot = Minecraft.thePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().v();

         while (true) {
            Minecraft.thePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(currentSlot);

            try {
               Thread.sleep(this.scrollDelay.getValue().longValue());
            } catch (InterruptedException var4) {
            }

            if (targetSlot > currentSlot) {
               currentSlot++;
            } else {
               if (targetSlot >= currentSlot) {
                  return;
               }

               currentSlot--;
            }
         }
      }
   }

   public Throwpot() {
      super("Throwpot", Category.INVENTORY, "Throws or consumes healing items automatically or upon pressing keybind");
      this.potsOption = new ModeOption("Pots");
      this.soupOption = new ModeOption("Soup");
      this.typeValue = ModeValue.create(this, "Type", this.bothOption, this.bothOption, this.potsOption, this.soupOption);
      this.dynamicOption = new ModeOption("Dynamic");
      this.singleOption = new ModeOption("Single");
      this.modeValue = ModeValue.create(
         this,
         "Mode",
         "Dynamic - uses only as many items as needed to heal as much as possible without over-healing\nSingle - Always uses one item, regardless of health",
         this.dynamicOption,
         this.dynamicOption,
         this.singleOption
      );
      this.scrollDelay = NumberValue.create(this, "Scroll delay", "#", "ms", 0.0, 100.0, 200.0);
      this.delayValue = RandomValue.createWithIncrement(this, "Delay", "#", "ms", 0.0, 80.0, 115.0, 200.0, 1.0);
      this.scrollValue = BooleanValue.create(this, "Scroll", false);
      this.randomValue = BooleanValue.create(this, "Random", false);
      this.throwBowls = BooleanValue.create(this, "Throw bowls", true, "Throws soup bowls after consuming");
      this.itemsToThrow = new CopyOnWriteArrayList<>();
      this.scrollValue.addDependentValues(this.scrollDelay);
      this.addValue(this.typeValue, this.modeValue, this.delayValue, this.scrollValue, this.scrollDelay, this.randomValue, this.throwBowls);
   }

   @Override
   public void onDisable() {
      this.itemsToThrow.clear();
   }
}
