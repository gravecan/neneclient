package org.json;

public class JSONParserConfiguration extends ParserConfiguration {
   private boolean overwriteDuplicateKey = false;
   private boolean strictMode;

   protected JSONParserConfiguration clone() {
      JSONParserConfiguration clone = new JSONParserConfiguration();
      clone.overwriteDuplicateKey = this.overwriteDuplicateKey;
      clone.maxNestingDepth = this.maxNestingDepth;
      return clone;
   }

   public JSONParserConfiguration withMaxNestingDepth(int maxNestingDepth) {
      JSONParserConfiguration clone = this.clone();
      clone.maxNestingDepth = maxNestingDepth;
      return clone;
   }

   public JSONParserConfiguration withOverwriteDuplicateKey(boolean overwriteDuplicateKey) {
      JSONParserConfiguration clone = this.clone();
      clone.overwriteDuplicateKey = overwriteDuplicateKey;
      return clone;
   }

   public JSONParserConfiguration withStrictMode() {
      return this.withStrictMode(true);
   }

   public JSONParserConfiguration withStrictMode(boolean mode) {
      JSONParserConfiguration clone = this.clone();
      clone.strictMode = mode;
      return clone;
   }

   public boolean isOverwriteDuplicateKey() {
      return this.overwriteDuplicateKey;
   }

   public boolean isStrictMode() {
      return this.strictMode;
   }
}
