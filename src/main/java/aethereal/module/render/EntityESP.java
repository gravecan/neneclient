package aethereal.module.render;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.DrawEvent;
import aethereal.module.misc.StreamerMode;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent188;
import aethereal.setting.BooleanSetting;
import aethereal.setting.MultiSelectSetting;
import aethereal.util.UtilComponent252;
import aethereal.util.UtilComponent270;
import aethereal.util.UtilComponent279;
import aethereal.util.UtilComponent284;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector2f;

@ModuleRegister(name = "Entity ESP", description = "Displays information about entities above their head", category = Category.Render)
public class EntityESP extends Module {
   private final MultiSelectSetting multiSelectSettingField1 = new MultiSelectSetting(
      "Tracked Entities",
      new BooleanSetting("Players", true),
      new BooleanSetting("Animals", false),
      new BooleanSetting("Mobs", false),
      new BooleanSetting("Items", false)
   );
   private final List<EntityESP.NestedType355> listField2 = new ArrayList<>();

   public List<EntityESP.NestedType355> getListField2_2250() {
      return this.listField2;
   }

   public EntityESP() {
      this.addSettings(this.multiSelectSettingField1);
   }

   @EventTarget
   public void onDrawEvent_2249(DrawEvent drawEvent) {
      if (drawEvent.checkCondition_1001()) {
         for (Entity entity : minecraft.world.getEntities()) {
            if (entity != minecraft.player) {
               String string;
               if (entity instanceof PlayerEntity) {
                  string = "Players";
               } else if (entity instanceof HostileEntity) {
                  string = "Mobs";
               } else if (!(entity instanceof AnimalEntity) && !(entity instanceof ShulkerEntity) && !(entity instanceof VillagerEntity)) {
                  string = !(entity instanceof ItemEntity) && !(entity instanceof ArrowEntity) ? null : "Items";
               } else {
                  string = "Animals";
               }

               String string1 = string;
               if (string1 != null && this.multiSelectSettingField1.computeValue_2852(string1).getCurrentValue()) {
                  int i = entity instanceof PlayerEntity
                        && NeneClient.getInstance().getProcessors().getFriends().checkCondition_1107(entity.getName().getString())
                     ? RenderComponent183.computeValue_2558(0, 100, 0, 120)
                     : RenderComponent183.computeValue_2558(0, 0, 0, 80);
                  Vec3d vec3d = UtilComponent279.computeValue_3585(entity, drawEvent.getTickNeneClient_0997());
                  Vec3d vec3d1 = vec3d.add(0.0, entity.getHeight() + 0.25F, 0.0);
                  Vector2f vector2f = UtilComponent284.computeValue_3615(vec3d1.getX(), vec3d1.getY(), vec3d1.getZ());
                  if (UtilComponent284.checkCondition_3618(vector2f)) {
                     if (!(entity instanceof ItemEntity) && !(entity instanceof ArrowEntity)) {
                        this.performAction_2252(entity, drawEvent, vector2f, 7.5F, 2.0F, i);
                        this.performAction_2247(entity, drawEvent, UtilComponent284.computeValue_3615(vec3d.x, vec3d.y - 0.25, vec3d.z), 7.5F, 2.0F, i);
                     } else {
                        this.performAction_2248(entity, drawEvent, vector2f, 7.5F, 2.0F, i);
                     }
                  }
               }
            }
         }
      }
   }

   private void performAction_2252(Entity entity, DrawEvent drawEvent, Vector2f vector2f, float f, float g, int i) {
      StreamerMode streamerMode = NeneClient.getInstance().getProcessors().getModules().getStreamerModeField7_0439();
      Object object = entity.getName();
      if (streamerMode.isEnabled() && streamerMode.getBooleanSettingField2_2001().getCurrentValue()) {
         object = Text.literal(streamerMode.computeValue_2004(object.getString())).setStyle(object.getStyle());
      }

      MutableText mutableText = object.copy().setStyle(object.getStyle().withColor(16777215));
      mutableText.getSiblings().replaceAll(text -> text.copy().setStyle(text.getStyle().withColor(16777215)));
      MutableText mutableText1 = (entity.getScoreboardTeam() != null ? entity.getScoreboardTeam().getPrefix().copy().append(mutableText) : mutableText)
         .copy()
         .append(Text.literal(" " + (int)UtilComponent252.NestedType400.computeValue_3409((LivingEntity)entity)).setStyle(Style.EMPTY.withColor(16711680)));
      float h = RenderComponent188.renderComponent187Field5.computeValue_2628(mutableText1, f);
      float j = RenderComponent188.renderComponent187Field5.computeValue_2622(f);
      float k = vector2f.x() - h / 2.0F;
      float l = vector2f.y();
      float m = k - g;
      float n = h + g * 2.0F;
      drawEvent.getRenderComponent184Field1_0998().performAction_2577(drawEvent.getContext_1000().getMatrices(), m, l, n, j, 0.0F, i);
      RenderComponent188.renderComponent187Field5.performAction_2634(drawEvent.getContext_1000().getMatrices(), mutableText1, k, l, f);
      this.performAction_2251(entity, drawEvent, n, m, l, i, j);
   }

   private void performAction_2251(Entity entity, DrawEvent drawEvent, float f, float g, float h, int i, float j) {
      if (entity instanceof PlayerEntity playerEntity) {
         float k = j * 0.3F;
         ItemStack[] itemStacks = new ItemStack[]{
            playerEntity.getMainHandStack(),
            playerEntity.getEquippedStack(EquipmentSlot.HEAD),
            playerEntity.getEquippedStack(EquipmentSlot.CHEST),
            playerEntity.getEquippedStack(EquipmentSlot.LEGS),
            playerEntity.getEquippedStack(EquipmentSlot.FEET),
            playerEntity.getOffHandStack()
         };
         int l = 0;

         for (ItemStack itemStack : itemStacks) {
            if (!itemStack.isEmpty()) {
               l++;
            }
         }

         if (l > 0) {
            float m = g + (f - (l * j + (l - 1) * k)) / 2.0F;
            float n = h - j - k;

            for (ItemStack itemStack1 : itemStacks) {
               if (!itemStack1.isEmpty()) {
                  drawEvent.getRenderComponent184Field1_0998().performAction_2577(drawEvent.getContext_1000().getMatrices(), m, n, j, j, 0.0F, i);
                  drawEvent.getRenderComponent185Field2_0999()
                     .performAction_2592(drawEvent.getContext_1000(), UtilComponent270.computeValue_3541(itemStack1), m, n, 0, 1.0F, j / 16.0F, true);
                  m += j + k;
               }
            }
         }
      }
   }

   private void performAction_2247(Entity entity, DrawEvent drawEvent, Vector2f vector2f, float f, float g, int i) {
      if (entity instanceof LivingEntity livingEntity) {
         ArrayList arrayList1 = new ArrayList();

         for (int j = this.listField2.size() - 1; j >= 0; j--) {
            EntityESP.NestedType355 nestedType355 = this.listField2.get(j);
            if (nestedType355.getIntegerField1_2254() == livingEntity.getId()) {
               if (livingEntity.age < nestedType355.getAge_2256()) {
                  this.listField2.remove(j);
               } else {
                  arrayList1.add(nestedType355);
               }
            }
         }

         ArrayList arrayList;
         if (arrayList1.isEmpty()) {
            arrayList = new ArrayList(livingEntity.getStatusEffects());
         } else {
            arrayList = new ArrayList();

            for (EntityESP.NestedType355 nestedType3551 : arrayList1) {
               for (StatusEffectInstance statusEffectInstance : nestedType3551.getEffects_2255()) {
                  int k = statusEffectInstance.getDuration() - Math.max(0, livingEntity.age - nestedType3551.getAge_2256());
                  if (k > 0) {
                     StatusEffectInstance statusEffectInstance1 = statusEffectInstance.getDuration() > 1000000
                        ? statusEffectInstance
                        : new StatusEffectInstance(statusEffectInstance.getEffectType(), k, statusEffectInstance.getAmplifier());
                     StatusEffectInstance statusEffectInstance2 = null;

                     for (StatusEffectInstance statusEffectInstance3 : arrayList) {
                        if (statusEffectInstance3.getEffectType().equals(statusEffectInstance.getEffectType())) {
                           statusEffectInstance2 = statusEffectInstance3;
                           break;
                        }
                     }

                     if (statusEffectInstance2 == null) {
                        arrayList.add(statusEffectInstance1);
                     } else if (statusEffectInstance1.getAmplifier() > statusEffectInstance2.getAmplifier()
                        || statusEffectInstance1.getAmplifier() == statusEffectInstance2.getAmplifier()
                           && statusEffectInstance1.getDuration() > statusEffectInstance2.getDuration()) {
                        arrayList.remove(statusEffectInstance2);
                        arrayList.add(statusEffectInstance1);
                     }
                  }
               }
            }

            if (arrayList.isEmpty()) {
               arrayList = new ArrayList(livingEntity.getStatusEffects());
            }
         }

         float h = RenderComponent188.renderComponent187Field5.computeValue_2622(f);
         float l = 0.0F;

         for (StatusEffectInstance statusEffectInstance4 : arrayList) {
            int o = statusEffectInstance4.getDuration() / 20;
            l = Math.max(
               l,
               RenderComponent188.renderComponent187Field5
                  .computeValue_2626(
                     Text.translatable(((StatusEffect)statusEffectInstance4.getEffectType().value()).getTranslationKey()).getString()
                        + " "
                        + UtilComponent279.computeValue_3584(statusEffectInstance4.getAmplifier())
                        + (statusEffectInstance4.getDuration() > 1000000 ? " ∞" : " - " + o / 60 + ":" + String.format("%02d", o % 60)),
                     f
                  )
            );
         }

         float m = vector2f.x() - l / 2.0F;
         float n = vector2f.y() + g;
         drawEvent.getRenderComponent184Field1_0998()
            .performAction_2577(drawEvent.getContext_1000().getMatrices(), m - g, n, l + g * 2.0F, arrayList.size() * h, 0.0F, i);
         float p = n;

         for (StatusEffectInstance statusEffectInstance5 : arrayList) {
            String string = statusEffectInstance5.getDuration() > 1000000
               ? " ∞"
               : " - " + statusEffectInstance5.getDuration() / 20 / 60 + ":" + String.format("%02d", statusEffectInstance5.getDuration() / 20 % 60);
            String string1 = Text.translatable(((StatusEffect)statusEffectInstance5.getEffectType().value()).getTranslationKey()).getString()
               + " "
               + UtilComponent279.computeValue_3584(statusEffectInstance5.getAmplifier())
               + string;
            RenderComponent188.renderComponent187Field5
               .performAction_2630(
                  drawEvent.getContext_1000().getMatrices(),
                  string1,
                  vector2f.x() - RenderComponent188.renderComponent187Field5.computeValue_2626(string1, f) / 2.0F,
                  p,
                  f,
                  RenderComponent183.computeValue_2549(((StatusEffect)statusEffectInstance5.getEffectType().value()).getColor(), 1.0F)
               );
            p += h;
         }
      }
   }

   private void performAction_2248(Entity entity, DrawEvent drawEvent, Vector2f vector2f, float f, float g, int i) {
      MutableText mutableText = entity instanceof ItemEntity ? ((ItemEntity)entity).getStack().getName().copy() : entity.getName().copy();
      if (entity instanceof ItemEntity itemEntity && itemEntity.getStack().getCount() > 1) {
         mutableText.append(Text.literal(" x" + itemEntity.getStack().getCount()));
      }

      float l = RenderComponent188.renderComponent187Field5.computeValue_2628(mutableText, f);
      float h = RenderComponent188.renderComponent187Field5.computeValue_2622(f);
      float j = vector2f.x() - l / 2.0F;
      float k = vector2f.y();
      drawEvent.getRenderComponent184Field1_0998().performAction_2577(drawEvent.getContext_1000().getMatrices(), j - g, k, l + g * 2.0F, h, 0.0F, i);
      RenderComponent188.renderComponent187Field5.performAction_2634(drawEvent.getContext_1000().getMatrices(), mutableText, j, k, f);
   }

   public static final class NestedType355 {
      private final List<StatusEffectInstance> effects;
      private final int integerField1;
      private final int age;

      public NestedType355(List<StatusEffectInstance> list, int i, int j) {
         this.effects = list;
         this.integerField1 = i;
         this.age = j;
      }

      public List<StatusEffectInstance> getEffects_2255() {
         return this.effects;
      }

      public int getIntegerField1_2254() {
         return this.integerField1;
      }

      public int getAge_2256() {
         return this.age;
      }
   }
}
