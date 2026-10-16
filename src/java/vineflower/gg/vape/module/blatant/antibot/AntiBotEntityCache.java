package gg.vape.module.blatant.antibot;

import gg.vape.mapping.MappedClasses;
import gg.vape.unmap.TextComponentBase;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.ITextComponent;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.NetHandlerPlayClientImpl;
import gg.vape.wrapper.impl.PlayerInfo;
import gg.vape.wrapper.impl.ScorePlayerTeam;
import gg.vape.wrapper.impl.ScorePlayerTeamTextComponent;
import gg.vape.wrapper.impl.TextFormatting;

public class AntiBotEntityCache {
   private static final Integer NO_TEAM_COLOR = 16777215;

   private String sanitizeName(String name) {
      if (name == null) {
         return null;
      } else {
         StringBuilder sanitizedName = new StringBuilder();

         for (int index = 0; index < name.length(); index++) {
            char character = name.charAt(index);
            if (character >= 'a' && character <= 'z' || character >= 'A' && character <= 'Z' || character >= '0' && character <= '9' || character == '_') {
               sanitizedName.append(character);
            }
         }

         return sanitizedName.toString();
      }
   }

   public Integer getTeamColor(EntityPlayer player) {
      NetHandlerPlayClientImpl connection = Minecraft.N();
      if (connection.isNull()) {
         return null;
      } else {
         String playerName = player.getName();
         Integer teamColor = null;

         for (Object playerInfoObject : connection.getPlayerInfoMap()) {
            PlayerInfo playerInfo = new PlayerInfo(playerInfoObject);
            if (!playerInfo.isNull()) {
               ITextComponent displayName = playerInfo.R();
               if (displayName.isNotNull()) {
                  Object displayText = displayName.getFormattedText();
                  if (displayText == null || !((String)displayText).contains(playerName)) {
                     continue;
                  }

                  Integer formattedColor = this.findColorFromInfo(playerInfo, playerName);
                  if (formattedColor != null) {
                     return formattedColor;
                  }
               }

               if (teamColor == null && !playerInfo.v().isNull() && playerInfo.v().getName().equals(playerName)) {
                  try {
                     ScorePlayerTeam scoreTeam = playerInfo.X();
                     Integer formattingColor;
                     TextFormatting formatting;
                     if (scoreTeam.isNotNull()
                        && (formatting = scoreTeam.getColor()) != null
                        && (formattingColor = formatting.getColor()) != null
                        && !formattingColor.equals(NO_TEAM_COLOR)) {
                        teamColor = formattingColor;
                     }
                  } catch (Exception var13) {
                  }
               }
            }
         }

         return teamColor;
      }
   }

   public boolean hasSameTeamColor(EntityPlayer firstPlayer, EntityPlayer secondPlayer) {
      Integer firstColor = this.getTeamColor(firstPlayer);
      Integer secondColor = this.getTeamColor(secondPlayer);
      return firstColor != null && secondColor != null ? firstColor.equals(secondColor) : false;
   }

   private String extractText(ITextComponent component) {
      try {
         Object componentValue = component.F();
         if (((Wrapper)componentValue).isNotNull() && ((Wrapper)componentValue).isInstance(MappedClasses.qT)) {
            ScorePlayerTeamTextComponent teamComponent = new ScorePlayerTeamTextComponent(((Wrapper)componentValue).getObject());
            return teamComponent.Y();
         }
      } catch (Exception var5) {
      }

      try {
         Object componentValue = component.getFormattedText();
         if (componentValue != null) {
            return this.sanitizeName((String)componentValue);
         }
      } catch (Exception var4) {
      }

      return null;
   }

   private Integer findColorInComponent(ITextComponent component, String playerName) {
      if (component.isNull()) {
         return null;
      } else {
         String componentText = this.extractText(component);
         Integer componentColor;
         if (componentText != null && this.namesMatch(componentText, playerName) && (componentColor = this.extractColorFromStyle(component)) != null) {
            return componentColor;
         } else {
            try {
               for (ITextComponent sibling : component.G()) {
                  Integer siblingColor = this.findColorInComponent(sibling, playerName);
                  if (siblingColor != null) {
                     return siblingColor;
                  }
               }
            } catch (Exception var9) {
            }

            return null;
         }
      }
   }

   private Integer findColorFromInfo(PlayerInfo playerInfo, String playerName) {
      ITextComponent displayName = playerInfo.R();
      return displayName.isNull() ? null : this.findColorInComponent(displayName, playerName);
   }

   private Integer extractColorFromStyle(ITextComponent component) {
      try {
         TextComponentBase style = component.J();
         if (style.isNull()) {
            return null;
         }

         String styleText = style.getObject().toString();
         if (styleText.contains("color=")) {
            int colorStart = styleText.indexOf("color=") + 6;
            int colorEnd = styleText.indexOf(",", colorStart);
            if (colorEnd == -1) {
               colorEnd = styleText.indexOf("}", colorStart);
            }

            if (colorEnd > colorStart) {
               String colorName = styleText.substring(colorStart, colorEnd);
               return this.parseColor(colorName);
            }
         }
      } catch (Exception var7) {
      }

      return null;
   }

   private boolean namesMatch(String componentName, String playerName) {
      if (componentName == null || playerName == null) {
         return false;
      } else if (componentName.equals(playerName)) {
         return true;
      } else {
         String sanitizedName = this.sanitizeName(componentName);
         return sanitizedName.equals(playerName);
      }
   }

   private Integer parseColor(String colorName) {
      if (colorName == null || colorName.isEmpty()) {
         return null;
      } else if (colorName.startsWith("#")) {
         try {
            return Integer.parseInt(colorName.substring(1), 16);
         } catch (NumberFormatException var3) {
            return null;
         }
      } else {
         TextFormatting formatting = TextFormatting.fromName(colorName);
         return formatting != null ? formatting.getColor() : null;
      }
   }
}
