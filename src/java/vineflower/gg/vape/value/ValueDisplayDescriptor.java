package gg.vape.value;

public class ValueDisplayDescriptor {
   private final Value<?, ?> value;
   private final String customName;
   private final ValueDisplayNameMode nameMode;

   private ValueDisplayDescriptor(Value<?, ?> value, String customName, ValueDisplayNameMode nameMode) {
      this.value = value;
      this.customName = customName;
      this.nameMode = nameMode;
   }

   public static ValueDisplayDescriptor fullName(Value<?, ?> value) {
      return new ValueDisplayDescriptor(value, null, ValueDisplayNameMode.FULL);
   }

   private static String createAbbreviation(String name) {
      if (name != null && !name.isEmpty()) {
         StringBuilder abbreviation = new StringBuilder();
         boolean wordStart = true;

         for (int index = 0; index < name.length(); index++) {
            char character = name.charAt(index);
            if (character != ' ' && character != '-' && character != '_') {
               if (index > 0 && Character.isUpperCase(character) && Character.isLowerCase(name.charAt(index - 1))) {
                  wordStart = true;
               }

               if (wordStart && Character.isLetterOrDigit(character)) {
                  abbreviation.append(Character.toUpperCase(character));
                  wordStart = false;
               }
            } else {
               wordStart = true;
            }
         }

         return abbreviation.toString();
      } else {
         return "";
      }
   }

   public String getDisplayName() {
      switch (this.nameMode) {
         case CUSTOM:
            return this.customName;
         case SIMPLE:
            return createAbbreviation(this.value.getName());
         default:
            return this.value.getName();
      }
   }

   public static ValueDisplayDescriptor abbreviatedName(Value<?, ?> value) {
      return new ValueDisplayDescriptor(value, null, ValueDisplayNameMode.SIMPLE);
   }

   public static ValueDisplayDescriptor customName(Value<?, ?> value, String customName) {
      return new ValueDisplayDescriptor(value, customName, ValueDisplayNameMode.CUSTOM);
   }

   public String getFullName() {
      return this.value.getName();
   }

   public Value<?, ?> getValue() {
      return this.value;
   }

   public boolean usesShorterName() {
      return this.nameMode == ValueDisplayNameMode.FULL ? false : this.getDisplayName().length() < this.getFullName().length();
   }
}
