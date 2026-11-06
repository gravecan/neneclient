package aethereal.ui.widget;

import aethereal.config.ThemeInfo;
import aethereal.core.ClientTickEvent;
import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.event.DrawEvent;
import aethereal.mixin.MixinContract162;
import aethereal.notification.NotificationComponent181;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent186;
import aethereal.render.RenderComponent188;
import aethereal.setting.BooleanSetting;
import aethereal.ui.element.ElementComponent210;
import aethereal.util.UtilComponent279;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.text.Text;
import org.joml.Vector4f;

public class WidgetComponent245 extends WidgetComponent249 implements MinecraftAccess {
   private final BooleanSetting booleanSettingField1 = new BooleanSetting("Side display", false);
   private final StatusEffectInstance statusEffectInstanceField2 = new StatusEffectInstance(StatusEffects.SPEED, 1200, 0);

   public WidgetComponent245() {
      super(new ElementComponent210("Potions", 0.0F, 0.0F, 0.0F, 0.0F));
      this.getDragInfo_3360().setWidgetComponent249Field1_3016(this);
      this.performAction_3362(this.booleanSettingField1);
   }

   @Override
   public void onDrawEvent_3315(DrawEvent drawEvent) {
      this.getAnimationField3_3363().performAction_2535(0.0F, 1.0F, 0.3F, RenderComponent186.contract372Field7, drawEvent.getTickNeneClient_0997());
      Iterator iterator = this.computeValue_3345().iterator();

      while (iterator.hasNext()) {
         ((MixinContract162)iterator.next())
            .getAnimation()
            .performAction_2535(0.0F, 1.0F, 0.3F, RenderComponent186.contract372Field7, drawEvent.getTickNeneClient_0997());
      }

      if (this.booleanSettingField1.getCurrentValue()) {
         this.onDrawEvent_3342(drawEvent);
      } else {
         this.onDrawEvent_3346(drawEvent);
      }

      this.getDragInfo_3360().setIntegerField6_3004(this.booleanSettingField1.getCurrentValue() ? 2 : 0);
   }

   private void onDrawEvent_3346(DrawEvent drawEvent) {
      float f = this.getDragInfo_3360().computeValue_3007();
      float g = this.getDragInfo_3360().computeValue_3009();
      float h = 14.5F + RenderComponent188.renderComponent187Field5.computeValue_2626("Potion-list", this.floatValueField8) + 5.0F + 2.0F;
      float i = g + this.floatValueField7 + 3.0F;
      boolean bl = false;

      for (MixinContract162 mixinContract162 : this.computeValue_3345()) {
         if (mixinContract162.getAnimation().getFloatValueField4_2530() > 0.0F) {
            bl = true;
            String string = Text.translatable(((StatusEffect)mixinContract162.getEffectType().value()).getTranslationKey()).getString()
               + " "
               + (mixinContract162.getAmplifier() + 1);
            h = Math.max(
               h,
               19.0F
                  + RenderComponent188.renderComponent187Field5.computeValue_2626(string, 6.5F)
                  + 8.0F
                  + RenderComponent188.renderComponent187Field5
                     .computeValue_2626(
                        mixinContract162.getDuration() > 1000000
                           ? "∞"
                           : mixinContract162.getDuration() / 20 / 60 + ":" + String.format("%02d", mixinContract162.getDuration() / 20 % 60),
                        6.5F
                     )
                  + 5.0F
                  + 2.0F
            );
         }
      }

      float q = UtilComponent279.computeValue_3586(this.getDragInfo_3360().getWidth_3005(), h, 0.5F);
      this.getDragInfo_3360().performAction_3006(q);
      if (this.computeValue_3367() > 0.0F) {
         this.performAction_3376(drawEvent, "E", "Potion-list", q, this.computeValue_3367());
      }

      for (MixinContract162 mixinContract1621 : this.computeValue_3345()) {
         float j = mixinContract1621.getAnimation().getFloatValueField4_2530() * this.computeValue_3367();
         if (j > 0.0F) {
            String string1 = Text.translatable(((StatusEffect)mixinContract1621.getEffectType().value()).getTranslationKey()).getString()
               + " "
               + (mixinContract1621.getAmplifier() + 1);
            int k = mixinContract1621.getDuration() / 20;
            String string2 = mixinContract1621.getDuration() > 1000000 ? "∞" : k / 60 + ":" + String.format("%02d", k % 60);
            float l = -8.0F * (1.0F - j);
            float m = -(1.0F - j);
            float n = i + m;
            float o = RenderComponent188.renderComponent187Field5.computeValue_2626(string2, 6.5F);
            float p = n + (11.5F - RenderComponent188.renderComponent187Field5.computeValue_2622(6.5F)) / 2.0F - 0.5F;
            this.performAction_3372(drawEvent, f + l, n, q, 11.5F, false, j);
            this.performAction_3371(drawEvent, f + l + 15.0F, n, 11.5F, j);
            drawEvent.getRenderComponent185Field2_0999()
               .performAction_2593(drawEvent.getContext_1000(), mixinContract1621.getEffectType(), f + l + 5.0F, n + 2.0F, 0.0F, 0.4F, j);
            RenderComponent188.renderComponent187Field5
               .performAction_2630(
                  drawEvent.getStack_1005(),
                  string1,
                  f + l + 19.0F,
                  p,
                  6.5F,
                  RenderComponent183.computeValue_2549(
                     ((StatusEffect)mixinContract1621.getEffectType().value()).getCategory() == StatusEffectCategory.HARMFUL
                        ? RenderComponent183.computeValue_2558(255, 125, 125, 255)
                        : -1,
                     j
                  )
               );
            RenderComponent188.renderComponent187Field5
               .performAction_2630(
                  drawEvent.getStack_1005(), string2, f + l + q - 5.0F - o - 1.0F, p, 6.5F, RenderComponent183.computeValue_2549(-1, 0.55F * j)
               );
            i += 13.5F * j;
         }
      }

      this.getDragInfo_3360().performAction_3018(bl ? i - g - 2.0F : this.floatValueField7);
      super.onDrawEvent_3315(drawEvent);
   }

   private void onDrawEvent_3342(DrawEvent drawEvent) {
      int i = NeneClient.getInstance().getProcessors().getThemes().computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547();
      int j = 0;
      Iterator iterator = this.computeValue_3345().iterator();

      while (iterator.hasNext()) {
         if (((MixinContract162)iterator.next()).getAnimation().getFloatValueField4_2530() > 0.0F) {
            j++;
         }
      }

      float f = (minecraft.getWindow().getScaledHeight() - (j * 26.0F + (j - 1) * 2.0F)) / 2.0F;
      float g = f;
      float h = 0.0F;

      for (MixinContract162 mixinContract162 : this.computeValue_3345()) {
         float k = mixinContract162.getAnimation().getFloatValueField4_2530() * this.computeValue_3367();
         if (k > 0.0F) {
            boolean bl = ((StatusEffect)mixinContract162.getEffectType().value()).getCategory() == StatusEffectCategory.HARMFUL;
            String string = Text.translatable(((StatusEffect)mixinContract162.getEffectType().value()).getTranslationKey()).getString()
               + " "
               + (mixinContract162.getAmplifier() + 1);
            int l = mixinContract162.getDuration() / 20;
            String string1 = mixinContract162.getDuration() > 1000000 ? "∞" : l / 60 + ":" + String.format("%02d", l % 60);
            float m = Math.max(
               RenderComponent188.renderComponent187Field5.computeValue_2626(string, 7.0F),
               RenderComponent188.renderComponent187Field5.computeValue_2626(string1, 6.0F)
            );
            float n = 18.5F + m + 8.0F;
            float o = 3.0F - n * (1.0F - k);
            float p = o + 13.5F + 6.0F;
            this.performAction_3372(drawEvent, o, g, n, 24.0F, true, k);
            drawEvent.getRenderComponent185Field2_0999()
               .performAction_2593(drawEvent.getContext_1000(), mixinContract162.getEffectType(), o + 3.5F, g + 5.75F, 0.0F, 0.6944444F, k);
            RenderComponent188.renderComponent187Field5
               .performAction_2630(
                  drawEvent.getStack_1005(),
                  string,
                  p,
                  g + 3.5F,
                  7.0F,
                  RenderComponent183.computeValue_2549(bl ? RenderComponent183.computeValue_2558(215, 76, 76, 255) : -1, k)
               );
            RenderComponent188.renderComponent187Field5
               .performAction_2630(drawEvent.getStack_1005(), string1, p, g + 13.0F, 6.0F, RenderComponent183.computeValue_2549(-1, 0.55F * k));
            int q = mixinContract162.getInitialDuration();
            float r = q <= 0 ? 1.0F : Math.min(1.0F, mixinContract162.getDuration() / q);
            int s = bl ? RenderComponent183.computeValue_2558(215, 76, 76, 255) : i;
            drawEvent.getRenderComponent184Field1_0998()
               .performAction_2583(
                  drawEvent.getStack_1005(),
                  o + 2.0F,
                  g + 24.0F - 1.5F,
                  n - 4.0F,
                  1.5F,
                  new Vector4f(0.0F, 0.0F, 1.0F, 1.0F),
                  RenderComponent183.computeValue_2549(s, 0.15F * k)
               );
            drawEvent.getRenderComponent184Field1_0998()
               .performAction_2583(
                  drawEvent.getStack_1005(),
                  o + 2.0F,
                  g + 24.0F - 1.5F,
                  (n - 4.0F) * r,
                  1.5F,
                  new Vector4f(0.0F, 0.0F, 1.0F, 1.0F),
                  RenderComponent183.computeValue_2549(s, k)
               );
            h = Math.max(h, n);
            g += 28.0F * k;
         }
      }

      this.getDragInfo_3360().performAction_3015(3.0F);
      this.getDragInfo_3360().performAction_3003(f);
      this.getDragInfo_3360().performAction_3006(h);
      this.getDragInfo_3360().performAction_3018(g - f - 2.0F);
      super.onDrawEvent_3315(drawEvent);
   }

   @Override
   public void performAction_3316(ClientTickEvent clientTickEvent) {
      if (minecraft.player != null) {
         boolean bl = minecraft.currentScreen instanceof ChatScreen;

         for (StatusEffectInstance statusEffectInstance : this.computeValue_3345()) {
            if (!statusEffectInstance.getEffectType().equals(StatusEffects.NIGHT_VISION)) {
               ((MixinContract162)statusEffectInstance)
                  .getAnimation()
                  .performAction_2528(
                     statusEffectInstance == this.statusEffectInstanceField2
                        ? minecraft.currentScreen instanceof ChatScreen
                        : statusEffectInstance.getDuration() > 20
                  );
               if (((MixinContract162)statusEffectInstance).getAnimation().getFloatValueField4_2530() > 0.0) {
                  bl = true;
               }

               if (statusEffectInstance != this.statusEffectInstanceField2
                  && statusEffectInstance.getDuration() == 100
                  && (
                     statusEffectInstance.getEffectType().equals(StatusEffects.STRENGTH)
                        || statusEffectInstance.getEffectType().equals(StatusEffects.SPEED)
                        || statusEffectInstance.getEffectType().equals(StatusEffects.HEALTH_BOOST)
                        || statusEffectInstance.getEffectType().equals(StatusEffects.INVISIBILITY)
                  )) {
                  NeneClient.getInstance()
                     .getProcessors()
                     .getNotificationComponent182Field5_0676()
                     .performAction_2523(
                        new NotificationComponent181(
                           "E",
                           Text.literal("Effect ")
                              .append(
                                 Text.translatable(((StatusEffect)statusEffectInstance.getEffectType().value()).getTranslationKey())
                                    .append(" " + (statusEffectInstance.getAmplifier() + 1))
                                    .styled(
                                       style -> style.withColor(
                                          NeneClient.getInstance().getProcessors().getThemes().computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547()
                                       )
                                    )
                              )
                              .append(Text.literal("ends")),
                           2500
                        )
                     );
               }
            }
         }

         this.getAnimationField3_3363().performAction_2528(bl);
         super.performAction_3316(clientTickEvent);
      }
   }

   private List<StatusEffectInstance> computeValue_3345() {
      ArrayList arrayList = new ArrayList(minecraft.player.getStatusEffects());
      boolean bl = arrayList.stream().allMatch(statusEffectInstance -> statusEffectInstance.getEffectType().equals(StatusEffects.NIGHT_VISION));
      if (this.booleanSettingField1.getCurrentValue()
         && bl
         && (
            minecraft.currentScreen instanceof ChatScreen
               || ((MixinContract162)this.statusEffectInstanceField2).getAnimation().getFloatValueField4_2530() > 0.0F
         )) {
         arrayList.add(this.statusEffectInstanceField2);
      }

      arrayList.sort(Comparator.comparingInt(statusEffectInstance -> {
         if (statusEffectInstance.getEffectType().equals(StatusEffects.STRENGTH)) {
            return 0;
         } else {
            return statusEffectInstance.getEffectType().equals(StatusEffects.WEAKNESS) ? 1 : 2;
         }
      }));
      return arrayList;
   }
}
