package aethereal.module.misc;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.DrawEvent;
import aethereal.event.EventComponent074;
import aethereal.event.EventComponent091;
import aethereal.render.RenderComponent183;
import aethereal.setting.BooleanSetting;
import aethereal.setting.ChoiceSetting;
import aethereal.setting.FloatSetting;
import aethereal.util.UtilComponent250;
import aethereal.util.UtilComponent252;
import aethereal.util.UtilComponent265;
import aethereal.util.UtilComponent270;
import aethereal.util.UtilComponent277;
import aethereal.util.UtilComponent279;
import aethereal.util.UtilComponent280;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

@ModuleRegister(name = "Nuker", description = "Автоматически разрушает блоки в радиусе досягаемости", category = Category.Misc)
public class Nuker extends Module {
   private final ChoiceSetting choiceSettingField1 = new ChoiceSetting("Режим копания территории", "Шахта ФанТайм", "Шахта ФанТайм", "Общий");
   private final FloatSetting floatSettingField2 = new FloatSetting("Дистанция копания", 4.0F, 1.0F, 6.0F, 0.5F);
   private final FloatSetting floatSettingField3 = new FloatSetting("Скорость копания", 1.0F, 1.0F, 5.0F, 1.0F);
   private final BooleanSetting booleanSettingField4 = new BooleanSetting("Не копать под себя", true);
   private BlockPos blockPosField5;

   public Nuker() {
      this.addSettings(this.choiceSettingField1, this.floatSettingField2, this.floatSettingField3, this.booleanSettingField4);
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.blockPosField5 = null;
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.blockPosField5 = null;
   }

   @EventTarget
   public void onEventComponent074_1942(EventComponent074 eventComponent074) {
      MineAssistant mineAssistant = NeneClient.getInstance().getProcessors().getModules().getMineAssistantField75_0430();
      this.blockPosField5 = null;
      ItemStack itemStack = minecraft.player.getMainHandStack();
      if (itemStack.isDamageable() && itemStack.getMaxDamage() - itemStack.getDamage() < 50) {
         UtilComponent265.performAction_3505("Работа прекращена во избежание поломки кирки.");
         this.toggle();
      } else {
         if (this.choiceSettingField1.checkCondition_2850("Шахта ФанТайм") && UtilComponent252.NestedType400.checkCondition_3406()) {
            mineAssistant.performAction_1936();
         }

         boolean bl = Registries.ITEM.getId(itemStack.getItem()).getPath().endsWith("pickaxe");
         int i = this.booleanSettingField4.getCurrentValue()
            ? minecraft.player.getBlockY() + (bl && UtilComponent270.checkCondition_3542(itemStack, "Бульдозер") ? 1 : 0)
            : Integer.MIN_VALUE;
         float f = this.floatSettingField2.getValue();
         Box box = minecraft.player.getBoundingBox().expand(f + 1.0F);
         Vec3d vec3d = minecraft.player.getEyePos();
         Vec3d vec3d1 = minecraft.player.getRotationVec(1.0F);
         double d = 1.7976922776554427E308;
         Direction direction = null;

         for (BlockPos blockPos : BlockPos.iterate(BlockPos.ofFloored(box.minX, box.minY, box.minZ), BlockPos.ofFloored(box.maxX, box.maxY, box.maxZ))) {
            if (blockPos.getY() >= i && this.checkCondition_1944(blockPos, bl, mineAssistant.getBoxField3_1931())) {
               Vec3d vec3d2 = blockPos.toCenterPos();
               Vec3d vec3d3 = vec3d2.subtract(vec3d);
               double e = vec3d3.dotProduct(vec3d1);
               Vec3d vec3d4 = e > 0.0 ? (Vec3d)new Box(blockPos).raycast(vec3d, vec3d2).orElse(null) : null;
               if (vec3d4 != null && vec3d.squaredDistanceTo(vec3d4) <= f * f && this.checkCondition_1945(vec3d, blockPos)) {
                  double g = vec3d3.subtract(vec3d1.multiply(e)).lengthSquared();
                  if (g < d) {
                     d = g;
                     this.blockPosField5 = blockPos.toImmutable();
                     direction = Direction.getFacing(vec3d4.subtract(vec3d2));
                  }
               }
            }
         }

         if (this.blockPosField5 != null) {
            UtilComponent250 utilComponent250 = UtilComponent250.computeValue_3390(vec3d, this.blockPosField5.toCenterPos());
            NeneClient.getInstance()
               .getProcessors()
               .computeValue_0672()
               .performAction_1141(
                  new UtilComponent250(
                     MathHelper.wrapDegrees(utilComponent250.getYaw_3386() + UtilComponent279.computeValue_3582(-3.0F, 3.0F)),
                     MathHelper.clamp(utilComponent250.getPitch_3387() + UtilComponent279.computeValue_3582(-3.0F, 3.0F), -90.0F, 90.0F)
                  ),
                  180.0F,
                  1,
                  1
               );
            if (UtilComponent250.computeValue_3385().computeValue_3389(utilComponent250) <= 20.0) {
               for (int j = 0; j < this.floatSettingField3.getValue().intValue(); j++) {
                  minecraft.interactionManager.updateBlockBreakingProgress(this.blockPosField5, direction);
               }

               minecraft.player.swingHand(Hand.MAIN_HAND);
            }
         }
      }
   }

   @EventTarget
   public void onEventComponent091_1943(EventComponent091 eventComponent091) {
      if (this.blockPosField5 != null) {
         UtilComponent280.performAction_3592(eventComponent091, UtilComponent277.getFloatValueField2_3570(), 5);
      }
   }

   @EventTarget
   public void onDrawEvent_1941(DrawEvent drawEvent) {
      if (drawEvent.checkCondition_1002() && this.blockPosField5 != null) {
         drawEvent.getRenderComponent185Field2_0999()
            .performAction_2594(drawEvent.getStack2_1004(), new Box(this.blockPosField5), RenderComponent183.computeValue_2558(255, 0, 0, 200), 2.0F);
      }
   }

   private boolean checkCondition_1944(BlockPos blockPos, boolean bl, Box box) {
      BlockState blockState = minecraft.world.getBlockState(blockPos);
      return blockState.isAir()
         ? false
         : !this.choiceSettingField1.checkCondition_2850("Шахта ФанТайм")
            || bl && box.contains(blockPos.toCenterPos()) && blockState.calcBlockBreakingDelta(minecraft.player, minecraft.world, blockPos) >= 1.0F;
   }

   private boolean checkCondition_1945(Vec3d vec3d, BlockPos blockPos) {
      Box box = new Box(blockPos).contract(0.05000000385685581);

      for (int i = -1; i < 8; i++) {
         Vec3d vec3d1 = i < 0
            ? box.getCenter()
            : new Vec3d((i & 1) == 0 ? box.minX : box.maxX, (i & 2) == 0 ? box.minY : box.maxY, (i & 4) == 0 ? box.minZ : box.maxZ);
         if (minecraft.world
            .raycast(new RaycastContext(vec3d, vec3d1, ShapeType.COLLIDER, FluidHandling.NONE, minecraft.player))
            .getBlockPos()
            .equals(blockPos)) {
            return true;
         }
      }

      return false;
   }
}
