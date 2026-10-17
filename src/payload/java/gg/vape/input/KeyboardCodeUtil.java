package gg.vape.input;

import gg.vape.runtime.NativeBridge;
import gg.vape.wrapper.impl.ForgeVersion;
import org.lwjgl.input.Keyboard;

public class KeyboardCodeUtil {
   static int[] legacyToVirtualKeyMap = new int[10000];
   private static final String GRAVE_KEY_NAME = "Grave";

   public static int resolveModifierKey(int virtualKey, int keyMetadata) {
      int scanCode = (keyMetadata & 0xFF0000) >> 16;
      boolean extendedKey = (keyMetadata & 16777216) != 0;
      if (extendedKey) {
         int var5;
         switch (virtualKey) {
            case 16:
               var5 = NativeBridge.mvk(scanCode, 3);
               break;
            case 17:
               var5 = 163;
               break;
            case 18:
               var5 = 165;
               break;
            default:
               var5 = virtualKey;
         }

         return var5;
      } else {
         int resolvedKey;
         switch (virtualKey) {
            case 16:
               resolvedKey = NativeBridge.mvk(scanCode, 3);
               break;
            case 17:
               resolvedKey = 162;
               break;
            case 18:
               resolvedKey = 164;
               break;
            default:
               resolvedKey = virtualKey;
         }

         return resolvedKey;
      }
   }

   public static int convertLegacyKeyCode(int legacyKeyCode) {
      return legacyKeyCode > legacyToVirtualKeyMap.length - 1 ? 0 : legacyToVirtualKeyMap[legacyKeyCode];
   }

   public static String getVirtualKeyName(int virtualKey) {
      if (virtualKey == 192) {
         return GRAVE_KEY_NAME;
      } else {
         int scanCode = NativeBridge.mvk(virtualKey, 0);
         switch (virtualKey) {
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 45:
            case 46:
            case 111:
            case 144:
               scanCode |= 256;
            default:
               return NativeBridge.gkn(scanCode << 16);
         }
      }
   }

   public static int decodeExtendedMouseButton(long buttonMetadata) {
      long buttonMask = buttonMetadata & -65536L;
      if (buttonMask == 131072L) {
         return 3;
      } else {
         return buttonMask == 65536L ? 4 : 5;
      }
   }

   public static void disableLegacyRepeatEvents() {
      if (ForgeVersion.MC_1_16_5.v() && Keyboard.areRepeatEventsEnabled()) {
         Keyboard.enableRepeatEvents(false);
      }
   }

   static {
      legacyToVirtualKeyMap[1] = 27;
      legacyToVirtualKeyMap[2] = 49;
      legacyToVirtualKeyMap[3] = 50;
      legacyToVirtualKeyMap[4] = 51;
      legacyToVirtualKeyMap[5] = 52;
      legacyToVirtualKeyMap[6] = 53;
      legacyToVirtualKeyMap[7] = 54;
      legacyToVirtualKeyMap[8] = 55;
      legacyToVirtualKeyMap[9] = 56;
      legacyToVirtualKeyMap[10] = 57;
      legacyToVirtualKeyMap[11] = 48;
      legacyToVirtualKeyMap[12] = 189;
      legacyToVirtualKeyMap[13] = 187;
      legacyToVirtualKeyMap[14] = 8;
      legacyToVirtualKeyMap[15] = 9;
      legacyToVirtualKeyMap[16] = 81;
      legacyToVirtualKeyMap[17] = 87;
      legacyToVirtualKeyMap[18] = 69;
      legacyToVirtualKeyMap[19] = 82;
      legacyToVirtualKeyMap[20] = 84;
      legacyToVirtualKeyMap[21] = 89;
      legacyToVirtualKeyMap[22] = 85;
      legacyToVirtualKeyMap[23] = 73;
      legacyToVirtualKeyMap[24] = 79;
      legacyToVirtualKeyMap[25] = 80;
      legacyToVirtualKeyMap[26] = 219;
      legacyToVirtualKeyMap[27] = 221;
      legacyToVirtualKeyMap[28] = 13;
      legacyToVirtualKeyMap[29] = 162;
      legacyToVirtualKeyMap[30] = 65;
      legacyToVirtualKeyMap[31] = 83;
      legacyToVirtualKeyMap[32] = 68;
      legacyToVirtualKeyMap[33] = 70;
      legacyToVirtualKeyMap[34] = 71;
      legacyToVirtualKeyMap[35] = 72;
      legacyToVirtualKeyMap[36] = 74;
      legacyToVirtualKeyMap[37] = 75;
      legacyToVirtualKeyMap[38] = 76;
      legacyToVirtualKeyMap[39] = 186;
      legacyToVirtualKeyMap[40] = 222;
      legacyToVirtualKeyMap[41] = 192;
      legacyToVirtualKeyMap[42] = 160;
      legacyToVirtualKeyMap[43] = 220;
      legacyToVirtualKeyMap[44] = 90;
      legacyToVirtualKeyMap[45] = 88;
      legacyToVirtualKeyMap[46] = 67;
      legacyToVirtualKeyMap[47] = 86;
      legacyToVirtualKeyMap[48] = 66;
      legacyToVirtualKeyMap[49] = 78;
      legacyToVirtualKeyMap[50] = 77;
      legacyToVirtualKeyMap[51] = 188;
      legacyToVirtualKeyMap[52] = 190;
      legacyToVirtualKeyMap[53] = 191;
      legacyToVirtualKeyMap[54] = 161;
      legacyToVirtualKeyMap[55] = 106;
      legacyToVirtualKeyMap[56] = 164;
      legacyToVirtualKeyMap[57] = 32;
      legacyToVirtualKeyMap[58] = 20;
      legacyToVirtualKeyMap[59] = 112;
      legacyToVirtualKeyMap[60] = 113;
      legacyToVirtualKeyMap[61] = 114;
      legacyToVirtualKeyMap[62] = 115;
      legacyToVirtualKeyMap[63] = 116;
      legacyToVirtualKeyMap[64] = 117;
      legacyToVirtualKeyMap[65] = 118;
      legacyToVirtualKeyMap[66] = 119;
      legacyToVirtualKeyMap[67] = 120;
      legacyToVirtualKeyMap[68] = 121;
      legacyToVirtualKeyMap[69] = 144;
      legacyToVirtualKeyMap[70] = 145;
      legacyToVirtualKeyMap[71] = 103;
      legacyToVirtualKeyMap[72] = 104;
      legacyToVirtualKeyMap[73] = 105;
      legacyToVirtualKeyMap[74] = 109;
      legacyToVirtualKeyMap[75] = 100;
      legacyToVirtualKeyMap[76] = 101;
      legacyToVirtualKeyMap[77] = 102;
      legacyToVirtualKeyMap[78] = 107;
      legacyToVirtualKeyMap[79] = 97;
      legacyToVirtualKeyMap[80] = 98;
      legacyToVirtualKeyMap[81] = 99;
      legacyToVirtualKeyMap[82] = 96;
      legacyToVirtualKeyMap[83] = 110;
      legacyToVirtualKeyMap[87] = 122;
      legacyToVirtualKeyMap[88] = 123;
      legacyToVirtualKeyMap[100] = 124;
      legacyToVirtualKeyMap[101] = 125;
      legacyToVirtualKeyMap[102] = 126;
      legacyToVirtualKeyMap[103] = 127;
      legacyToVirtualKeyMap[104] = 128;
      legacyToVirtualKeyMap[105] = 129;
      legacyToVirtualKeyMap[112] = 21;
      legacyToVirtualKeyMap[113] = 130;
      legacyToVirtualKeyMap[121] = 28;
      legacyToVirtualKeyMap[123] = 29;
      legacyToVirtualKeyMap[141] = 146;
      legacyToVirtualKeyMap[146] = 186;
      legacyToVirtualKeyMap[156] = 13;
      legacyToVirtualKeyMap[157] = 163;
      legacyToVirtualKeyMap[179] = 188;
      legacyToVirtualKeyMap[184] = 165;
      legacyToVirtualKeyMap[197] = 19;
      legacyToVirtualKeyMap[199] = 36;
      legacyToVirtualKeyMap[200] = 38;
      legacyToVirtualKeyMap[201] = 33;
      legacyToVirtualKeyMap[203] = 37;
      legacyToVirtualKeyMap[205] = 39;
      legacyToVirtualKeyMap[207] = 35;
      legacyToVirtualKeyMap[208] = 40;
      legacyToVirtualKeyMap[209] = 34;
      legacyToVirtualKeyMap[210] = 45;
      legacyToVirtualKeyMap[211] = 46;
      legacyToVirtualKeyMap[218] = 12;
   }
}
