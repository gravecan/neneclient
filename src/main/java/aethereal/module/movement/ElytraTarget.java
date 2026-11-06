package aethereal.module.movement;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.EventComponent074;
import aethereal.handler.HandlerComponent108;
import aethereal.module.combat.Aura;
import aethereal.module.combat.CombatComponent165;
import aethereal.util.UtilComponent250;
import aethereal.util.UtilComponent268;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

@ModuleRegister(name = "Elytra Target", description = "Targets enemies while gliding and boosts with rockets from a hotbar slot", category = Category.Movement)
public class ElytraTarget extends Module {
   private final UtilComponent268 utilComponent268Field1 = new UtilComponent268();
   private int restoreSlot = -1;
   private int boostedSlot = -1;

   @EventTarget
   public void onEventComponent074_2032(EventComponent074 eventComponent074) {
      this.performAction_2031();
      Aura aura = NeneClient.getInstance().getProcessors().getModules().getAuraField3_0402();
      if (minecraft.player != null && aura.isEnabled() && minecraft.player.isGliding()) {
         int i = this.computeValue_2033();
         if (i != -1) {
            if (this.utilComponent268Field1.checkCondition_3522(150L) && minecraft.player.getVelocity().length() < 1.5 || aura.integerField16 == 1) {
               int j = minecraft.player.getInventory().getSelectedSlot();
               if (j != i) {
                  this.restoreSlot = j;
                  this.boostedSlot = i;
                  minecraft.player.getInventory().setSelectedSlot(i);
               }

               minecraft.interactionManager.interactItem(minecraft.player, Hand.MAIN_HAND);
               this.utilComponent268Field1.performAction_3524();
               this.restoreSlot = j == i ? -1 : j;
               this.boostedSlot = j == i ? -1 : i;
            }

            LivingEntity livingEntity = aura.getLivingEntityField2_1528();
            if (livingEntity != null) {
               Vec3d vec3d = minecraft.player.getEyePos();
               Vec3d vec3d1 = livingEntity.getBoundingBox().getCenter();
               UtilComponent250 utilComponent250 = UtilComponent250.computeValue_3390(vec3d, vec3d1);
               float f = CombatComponent165.computeValue_1552(minecraft.player.getYaw(), utilComponent250.getYaw_3386(), 1.0F);
               float g = CombatComponent165.computeValue_1552(minecraft.player.getPitch(), utilComponent250.getPitch_3387(), 1.0F);
               HandlerComponent108.getROTATION_1157().performAction_1141(new UtilComponent250(f, g), 180.0F, 1, 1);
            }
         }
      }
   }

   private int computeValue_2033() {
      for (int i = 0; i < 9; i++) {
         if (minecraft.player.getInventory().getStack(i).isOf(Items.FIREWORK_ROCKET)) {
            return i;
         }
      }

      return -1;
   }

   private void performAction_2031() {
      if (this.restoreSlot >= 0) {
         if (minecraft.player != null && minecraft.player.getInventory().getSelectedSlot() == this.boostedSlot) {
            minecraft.player.getInventory().setSelectedSlot(this.restoreSlot);
         }

         this.restoreSlot = -1;
         this.boostedSlot = -1;
      }
   }

   @Override
   public void onDisable() {
      this.performAction_2031();
      super.onDisable();
   }

   private double computeValue_2034() {
      Vec3d vec3d = minecraft.player.getEntityPos();
      Vec3d vec3d1 = vec3d.subtract(0.0, 2.0, 0.0);
      BlockHitResult blockHitResult = minecraft.world.raycast(new RaycastContext(vec3d, vec3d1, ShapeType.COLLIDER, FluidHandling.NONE, minecraft.player));
      return blockHitResult.getType() == Type.MISS ? 2.0 : vec3d.getY() - blockHitResult.getPos().y;
   }
}
