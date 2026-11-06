package aethereal.module.render;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.event.DrawEvent;
import aethereal.event.EventComponent071;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent188;
import aethereal.setting.BooleanSetting;
import aethereal.setting.MultiSelectSetting;
import aethereal.util.UtilComponent268;
import aethereal.util.UtilComponent284;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector2f;

@ModuleRegister(name = "Sound ESP", description = "Displays the location where the selected sound was played", category = Category.Render)
public class SoundESP extends Module {
   private final MultiSelectSetting multiSelectSettingField1 = new MultiSelectSetting(
      "Track sounds", new BooleanSetting("Trident", true), new BooleanSetting("Fireworks", true), new BooleanSetting("Explosions", true)
   );
   private final List<SoundESP.NestedType359> listField2 = new ArrayList<>();

   public SoundESP() {
      this.addSettings(this.multiSelectSettingField1);
   }

   @EventTarget
   public void onEventComponent071_2302(EventComponent071 eventComponent071) {
      String string = eventComponent071.getSound_0970().getId().getPath();
      if (string.contains("entity.firework_rocket.launch") && this.multiSelectSettingField1.computeValue_2852("Fireworks").getCurrentValue()
         || string.contains("entity.generic.explode") && this.multiSelectSettingField1.computeValue_2852("Explosions").getCurrentValue()
         || string.contains("item.trident.return") && this.multiSelectSettingField1.computeValue_2852("Trident").getCurrentValue()) {
         boolean bl = false;

         for (SoundESP.NestedType359 nestedType359 : this.listField2) {
            if (Math.abs(nestedType359.getSound_2305().getX() - eventComponent071.getSound_0970().getX()) <= 0.5
               && Math.abs(nestedType359.getSound_2305().getY() - eventComponent071.getSound_0970().getY()) <= 0.5
               && Math.abs(nestedType359.getSound_2305().getZ() - eventComponent071.getSound_0970().getZ()) <= 0.5) {
               bl = true;
               break;
            }
         }

         if (!bl) {
            this.listField2.add(new SoundESP.NestedType359(eventComponent071.getSound_0970()));
         }
      }
   }

   @EventTarget
   public void onDrawEvent_2303(DrawEvent drawEvent) {
      if (drawEvent.checkCondition_1001()) {
         int i = RenderComponent183.computeValue_2558(0, 0, 0, 100);

         for (SoundESP.NestedType359 nestedType359 : this.listField2) {
            if (nestedType359.getUtilComponent268Field1_2304().checkCondition_3522(5500L)) {
               this.listField2.remove(nestedType359);
            } else {
               Vector2f vector2f = UtilComponent284.computeValue_3615(
                  nestedType359.getSound_2305().getX(), nestedType359.getSound_2305().getY(), nestedType359.getSound_2305().getZ()
               );
               if (UtilComponent284.checkCondition_3618(vector2f)) {
                  String string1 = nestedType359.getSound_2305().getId().getPath();
                  String string;
                  if (string1.contains("firework_rocket")) {
                     string = "Fireworks";
                  } else if (string1.contains("explode")) {
                     string = "Explosions";
                  } else {
                     string = string1.contains("trident") ? "Trident" : "Sound";
                  }

                  String string2 = string;
                  int j = (int)minecraft.player
                     .getEntityPos()
                     .distanceTo(new Vec3d(nestedType359.getSound_2305().getX(), nestedType359.getSound_2305().getY(), nestedType359.getSound_2305().getZ()));
                  int k = (int)(nestedType359.getUtilComponent268Field1_2304().computeValue_3521() / 1000L);
                  MutableText mutableText = Text.literal(string2 + " [" + j + "m/" + k + "sec]");
                  float f = RenderComponent188.renderComponent187Field5.computeValue_2628(mutableText, 7.5F);
                  float g = RenderComponent188.renderComponent187Field5.computeValue_2622(7.5F);
                  float h = vector2f.x() - f / 2.0F;
                  float l = vector2f.y();
                  drawEvent.getRenderComponent184Field1_0998().performAction_2577(drawEvent.getContext_1000().getMatrices(), h - 2.0F, l, f + 4.0F, g, 0.0F, i);
                  RenderComponent188.renderComponent187Field5.performAction_2634(drawEvent.getContext_1000().getMatrices(), mutableText, h, l, 7.5F);
               }
            }
         }
      }
   }

   public static class NestedType359 {
      public final UtilComponent268 utilComponent268Field1 = new UtilComponent268();
      public final SoundInstance sound;

      public UtilComponent268 getUtilComponent268Field1_2304() {
         return this.utilComponent268Field1;
      }

      public SoundInstance getSound_2305() {
         return this.sound;
      }

      public NestedType359(SoundInstance soundInstance) {
         this.sound = soundInstance;
         this.utilComponent268Field1.performAction_3524();
      }
   }
}
