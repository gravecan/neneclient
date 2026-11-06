package aethereal.ui.screen;

import aethereal.core.Category;
import aethereal.core.Module;
import aethereal.core.NeneClient;
import java.util.Locale;
import org.joml.Matrix3x2fStack;

/** Local display information and module statistics; no client activation state. */
public final class ProfilePanel {
   private float lastDrawTime = -10.0F;
   private float openTime;
   private float displayedEnabledModules;

   public void render(Matrix3x2fStack matrices, float x, float y, float width, float height,
                      float alpha, int mouseX, int mouseY) {
      if (MenuRenderer.time - this.lastDrawTime > 0.25F) {
         this.openTime = 0.0F;
         this.displayedEnabledModules = 0.0F;
      }
      this.lastDrawTime = MenuRenderer.time;
      this.openTime += MenuRenderer.floatValueField1;

      int accent = MenuRenderer.getAccent();
      float progress = MenuRenderer.easeOutCubic(MenuRenderer.clamp01(this.openTime / 0.35F));
      float opacity = alpha * progress;
      float cardY = y + (1.0F - progress) * 10.0F;
      MenuRenderer.fillRoundedRect(matrices, x, cardY, width, 96.0F, 9.0F,
         MenuRenderer.withAlpha(0xFFFFFF, 0.05F * opacity));
      MenuRenderer.strokeRoundedRect(matrices, x, cardY, width, 96.0F, 9.0F, 0.5F,
         MenuRenderer.withAlpha(0xFFFFFF, 0.09F * opacity));

      String username = MenuRenderer.getDisplayName();
      String initial = username.isEmpty() ? "?" : username.substring(0, 1).toUpperCase(Locale.ROOT);
      MenuRenderer.fillRoundedRect(matrices, x + 14.0F, cardY + 20.0F, 56.0F, 56.0F, 12.0F,
         MenuRenderer.replaceAlpha(accent, 0.95F * opacity));
      MenuRenderer.drawCenteredText(matrices, initial, x + 42.0F, cardY + 48.0F, 22.0F,
         MenuRenderer.withAlpha(0xFFFFFF, opacity));
      MenuRenderer.drawText(matrices, MenuRenderer.ellipsize(username, 12.0F, width - 100.0F),
         x + 86.0F, cardY + 28.0F, 12.0F, MenuRenderer.withAlpha(0xFFFFFF, opacity));
      MenuRenderer.drawText(matrices, "LOCAL PROFILE", x + 86.0F, cardY + 48.0F, 6.0F,
         MenuRenderer.replaceAlpha(accent, opacity));
      MenuRenderer.drawText(matrices, "No client activation required", x + 86.0F, cardY + 66.0F,
         5.5F, MenuRenderer.withAlpha(0xFFFFFF, 0.55F * opacity));

      Category[] categories = Category.values();
      int[] totals = new int[categories.length];
      int[] enabled = new int[categories.length];
      NeneClient client = NeneClient.getInstance();
      if (client != null && client.getProcessors() != null && client.getProcessors().getModules() != null) {
         for (Module module : client.getProcessors().getModules().getEntries()) {
            totals[module.getCategory().ordinal()]++;
            if (module.isEnabled()) {
               enabled[module.getCategory().ordinal()]++;
            }
         }
      }

      int totalModules = 0;
      int enabledModules = 0;
      for (int index = 0; index < categories.length; index++) {
         totalModules += totals[index];
         enabledModules += enabled[index];
      }
      this.displayedEnabledModules = MenuRenderer.approach(this.displayedEnabledModules, enabledModules, 7.0F);
      float statsY = cardY + 104.0F;
      float statWidth = (width - 16.0F) / 3.0F;
      String[] labels = {"ENABLED", "MODULES", "CATEGORIES"};
      String[] values = {String.valueOf(Math.round(this.displayedEnabledModules)),
         String.valueOf(totalModules), String.valueOf(categories.length)};
      for (int index = 0; index < labels.length; index++) {
         float statX = x + index * (statWidth + 8.0F);
         MenuRenderer.fillRoundedRect(matrices, statX, statsY, statWidth, 38.0F, 8.0F,
            MenuRenderer.withAlpha(0xFFFFFF, 0.05F * opacity));
         MenuRenderer.drawText(matrices, values[index], statX + 10.0F, statsY + 15.0F, 12.0F,
            MenuRenderer.withAlpha(index == 0 ? accent : 0xFFFFFF, opacity));
         MenuRenderer.drawText(matrices, labels[index], statX + 10.0F, statsY + 29.0F, 5.5F,
            MenuRenderer.withAlpha(0xFFFFFF, 0.35F * opacity));
      }

      float categoriesY = statsY + 48.0F;
      float rowHeight = Math.max(0.0F, Math.min(16.0F, (y + height - categoriesY) / Math.max(1, categories.length)));
      for (int index = 0; index < categories.length; index++) {
         float rowY = categoriesY + index * rowHeight + rowHeight / 2.0F;
         float barX = x + 74.0F;
         float barWidth = Math.max(0.0F, width - 114.0F);
         MenuRenderer.drawText(matrices, categories[index].name(), x + 2.0F, rowY, 6.0F,
            MenuRenderer.withAlpha(0xB8B3C0, opacity));
         MenuRenderer.fillRoundedRect(matrices, barX, rowY - 2.0F, barWidth, 4.0F, 2.0F,
            MenuRenderer.withAlpha(0xFFFFFF, 0.07F * opacity));
         float fraction = totals[index] == 0 ? 0.0F : (float)enabled[index] / totals[index];
         if (barWidth * fraction > 0.5F) {
            MenuRenderer.fillRoundedRect(matrices, barX, rowY - 2.0F, Math.max(4.0F, barWidth * fraction), 4.0F, 2.0F,
               MenuRenderer.replaceAlpha(accent, opacity));
         }
         MenuRenderer.drawRightAlignedText(matrices, enabled[index] + "/" + totals[index],
            x + width - 2.0F, rowY, 5.5F, MenuRenderer.withAlpha(0xFFFFFF, 0.45F * opacity));
      }
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      return false;
   }
}
