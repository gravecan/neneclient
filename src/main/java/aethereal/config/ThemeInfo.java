package aethereal.config;

public enum ThemeInfo {
   PRIMARY(new Theme("primary", 133, 156, 255, 75), new Theme("primary", 133, 156, 255, 75)),
   BACKGROUND_HUD(new Theme("background_hud", 11, 11, 22, 170), new Theme("background_hud", 11, 11, 22, 170)),
   BACKGROUND_GUI(new Theme("background_gui", 8, 8, 8, 255), new Theme("background_gui", 253, 254, 255, 255)),
   OUTLINE_SMALL(new Theme("outline_small", 255, 255, 255, 5), new Theme("outline_small", 17, 18, 22, 5)),
   OUTLINE_MEDIUM(new Theme("outline_medium", 255, 255, 255, 10), new Theme("outline_medium", 17, 18, 22, 5)),
   TEXT(new Theme("typography_text", 255, 255, 255, 255), new Theme("typography_text", 17, 18, 22, 255)),
   TEXT_DISABLED(new Theme("typography_disabled", 67, 70, 81, 255), new Theme("typography_disabled", 160, 174, 192, 255));

   private final Theme dark;
   private final Theme light;

   ThemeInfo(Theme theme, Theme theme1) {
      this.dark = theme;
      this.light = theme1;
   }

   public Theme computeValue_0335(ThemeType themeType) {
      return themeType == ThemeType.LIGHT ? this.light : this.dark;
   }

   public Theme getLight_0334() {
      return this.light;
   }
}
