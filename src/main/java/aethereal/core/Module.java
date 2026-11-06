package aethereal.core;

import aethereal.config.SettingsPersistence;
import aethereal.module.misc.Sounds;
import aethereal.render.Animation;
import aethereal.setting.Setting;
import aethereal.ui.element.SettingElement;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.ArrayList;
import java.util.List;

public class Module implements MinecraftAccess {
   private boolean enabled;
   private boolean binding;
   private boolean expanded;
   private final List<SettingElement<?>> settingElements = new ArrayList<>();
   private final List<Setting<?>> settings = new ObjectArrayList();
   private final Animation enableAnimation = new Animation();
   private final Animation expandAnimation = new Animation();
   private final Animation hoverAnimation = new Animation();
   private final Animation secondaryAnimation = new Animation();
   private final String name = this.getClass().getAnnotation(ModuleRegister.class).name();
   private final String description = this.getClass().getAnnotation(ModuleRegister.class).description();
   private final Category category = this.getClass().getAnnotation(ModuleRegister.class).category();
   private int keybind = -1;

   public void setBinding(boolean bl) {
      this.binding = bl;
   }

   public void setExpanded(boolean bl) {
      this.expanded = bl;
   }

   public void setKeybind(int i) {
      if (this.keybind != i) {
         this.keybind = i;
         SettingsPersistence.performAction_0527();
      }
   }

   public List<SettingElement<?>> getSettingElements() {
      return this.settingElements;
   }

   public List<Setting<?>> getSettings() {
      return this.settings;
   }

   public Animation getEnableAnimation() {
      return this.enableAnimation;
   }

   public Animation getExpandAnimation() {
      return this.expandAnimation;
   }

   public Animation getHoverAnimation() {
      return this.hoverAnimation;
   }

   public Animation getSecondaryAnimation() {
      return this.secondaryAnimation;
   }

   public String getName() {
      return this.name;
   }

   public String getDescription() {
      return this.description;
   }

   public Category getCategory() {
      return this.category;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public boolean isBinding() {
      return this.binding;
   }

   public boolean isExpanded() {
      return this.expanded;
   }

   public int getKeybind() {
      return this.keybind;
   }

   public final void toggle() {
      this.setEnabled(!this.enabled);
   }

   public final void setEnabled(boolean bl) {
      if (this.enabled != bl) {
         this.enabled = bl;
         if (this.enabled) {
            this.onEnable();
         } else {
            this.onDisable();
         }

         Sounds sounds = NeneClient.getInstance() != null
               && NeneClient.getInstance().getProcessors() != null
               && NeneClient.getInstance().getProcessors().getModules() != null
            ? NeneClient.getInstance().getProcessors().getModules().getSoundsField83_0438()
            : null;
         if (sounds != null) {
            sounds.performAction_2000(this.enabled);
         }

         SettingsPersistence.performAction_0527();
      }
   }

   public final void addSettings(Setting<?>... settings) {
      for (Setting setting : settingsx) {
         this.settings.add(setting);
         SettingElement settingElement = setting.createElement();
         if (settingElement != null) {
            this.settingElements.add(settingElement);
         }
      }
   }

   public void onEnable() {
      EventBus.register(this);
   }

   public void onDisable() {
      EventBus.unregister(this);
   }
}
