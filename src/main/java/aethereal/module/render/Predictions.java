package aethereal.module.render;

import aethereal.config.ThemeInfo;
import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.DrawEvent;
import aethereal.render.Animation;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent186;
import aethereal.render.RenderComponent188;
import aethereal.setting.BooleanSetting;
import aethereal.setting.MultiSelectSetting;
import aethereal.util.UtilComponent284;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.TridentItem;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import org.joml.Matrix3f;
import org.joml.Matrix3x2fStack;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;
import platform.inject.accessors.TridentEntityAccessor;

@ModuleRegister(name = "Predictions", description = "Predicts and displays the flight path of a trident, arrows and potions", category = Category.Render)
public class Predictions extends Module {
   private final MultiSelectSetting multiSelectSettingField1 = new MultiSelectSetting(
      "Tracked Items",
      new BooleanSetting("Arrows", true),
      new BooleanSetting("Tridents", true),
      new BooleanSetting("Ender pearl", true),
      new BooleanSetting("Potions", true)
   );
   private final BooleanSetting booleanSettingField2 = new BooleanSetting("Rainbow color", false);
   private final Map<Integer, Predictions.NestedType357> mapField3 = new HashMap<>();

   public Predictions() {
      this.addSettings(this.multiSelectSettingField1, this.booleanSettingField2);
   }

   private boolean checkCondition_2264(Entity entity) {
      return entity.getX() != entity.lastX || entity.getY() != entity.lastY || entity.getZ() != entity.lastZ;
   }

   @EventTarget
   public void onDrawEvent_2266(DrawEvent drawEvent) {
      if (minecraft.player != null && minecraft.world != null) {
         if (drawEvent.checkCondition_1002()) {
            HashSet hashSet = new HashSet();
            Box box = minecraft.player.getBoundingBox().expand((Integer)minecraft.options.getViewDistance().getValue() * 16);
            if (this.multiSelectSettingField1.computeValue_2852("Arrows").getCurrentValue()) {
               minecraft.world
                  .getEntitiesByClass(ArrowEntity.class, box, arrowEntity -> this.checkCondition_2264(arrowEntity))
                  .forEach(arrowEntity -> this.performAction_2275(drawEvent, arrowEntity, Items.ARROW.getDefaultStack(), hashSet));
            }

            if (this.multiSelectSettingField1.computeValue_2852("Tridents").getCurrentValue()) {
               minecraft.world
                  .getEntitiesByClass(
                     TridentEntity.class,
                     box,
                     tridentEntity -> ((TridentEntityAccessor)tridentEntity).getReturnTimer() <= 0 && this.checkCondition_2264(tridentEntity)
                  )
                  .forEach(tridentEntity -> this.performAction_2275(drawEvent, tridentEntity, Items.TRIDENT.getDefaultStack(), hashSet));
            }

            if (this.multiSelectSettingField1.computeValue_2852("Ender pearl").getCurrentValue()) {
               minecraft.world
                  .getEntitiesByClass(EnderPearlEntity.class, box, enderPearlEntity -> this.checkCondition_2264(enderPearlEntity))
                  .forEach(enderPearlEntity -> this.performAction_2275(drawEvent, enderPearlEntity, Items.ENDER_PEARL.getDefaultStack(), hashSet));
            }

            if (this.multiSelectSettingField1.computeValue_2852("Potions").getCurrentValue()) {
               minecraft.world
                  .getEntitiesByClass(PotionEntity.class, box, potionEntity -> this.checkCondition_2264(potionEntity))
                  .forEach(potionEntity -> this.performAction_2275(drawEvent, potionEntity, potionEntity.getStack(), hashSet));
            }

            this.onDrawEvent_2268(drawEvent);
            this.mapField3.entrySet().removeIf(entry -> {
               if (hashSet.contains(entry.getKey())) {
                  return false;
               }

               Animation animation = entry.getValue().getAnim_2290();
               animation.performAction_2528(false);
               animation.performAction_2535(0.0F, 1.0F, 0.25F, RenderComponent186.contract372Field19, drawEvent.getTickNeneClient_0997());
               return animation.getFloatValueField4_2530() <= 0.0F;
            });
         }

         if (drawEvent.checkCondition_1001()) {
            this.mapField3.values().forEach(nestedType357 -> this.performAction_2270(drawEvent, nestedType357));
         }
      }
   }

   private void performAction_2275(DrawEvent drawEvent, Entity entity, ItemStack itemStack, Set<Integer> set) {
      List list = this.computeValue_2278(entity);
      if (list.size() >= 2) {
         set.add(entity.getId());
         Predictions.NestedType357 nestedType357 = this.mapField3.get(entity.getId());
         Animation animation = nestedType357 != null ? nestedType357.getAnim_2290() : new Animation();
         animation.performAction_2528(true);
         animation.performAction_2535(0.0F, 1.0F, 0.25F, RenderComponent186.contract372Field19, drawEvent.getTickNeneClient_0997());
         float f = animation.getFloatValueField4_2530();
         int i = NeneClient.getInstance().getProcessors().getThemes().computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547();
         float g = (float)(entity.getUuid().getLeastSignificantBits() & 65535L) / 65535.0F;
         int j = list.size() - 1;

         for (int k = 0; k < j; k++) {
            float h = Math.max(0.0F, Math.min(1.0F, f * j - k));
            if (h <= 0.0F) {
               break;
            }

            float l = ((g + (float)(((Vec3d)list.get(k)).x * 0.05000000070627959 + ((Vec3d)list.get(k)).z * 0.05000000070627959)) % 1.0F + 1.0F) % 1.0F;
            int m = this.booleanSettingField2.getCurrentValue() ? 0xFF000000 | Color.HSBtoRGB(l, 1.0F, 1.0F) & 16777215 : i;
            int n = (int)(255.0F * (0.3F + 0.7F * (1.0F - k / j)) * h * f);
            drawEvent.getRenderComponent185Field2_0999()
               .performAction_2596(drawEvent.getStack2_1004(), (Vec3d)list.get(k), (Vec3d)list.get(k + 1), null, m & 16777215 | n << 24, 3.0F);
         }

         this.mapField3.put(entity.getId(), new Predictions.NestedType357((Vec3d)list.getLast(), j, itemStack, animation));
      }
   }

   private void performAction_2270(DrawEvent drawEvent, Predictions.NestedType357 nestedType357) {
      float f = nestedType357.getAnim_2290().getFloatValueField4_2530();
      if (!(f <= 0.0F)) {
         Vector2f vector2f = UtilComponent284.computeValue_3615(
            nestedType357.getImpact_2292().x, nestedType357.getImpact_2292().y, nestedType357.getImpact_2292().z
         );
         if (UtilComponent284.checkCondition_3618(vector2f)) {
            float g = RenderComponent188.renderComponent187Field5.computeValue_2622(7.25F);
            String string = String.format(Locale.US, "%.1fs", nestedType357.getTicks_2291() / 20.0F);
            float h = 6.0F + g + RenderComponent188.renderComponent187Field5.computeValue_2626(string, 7.25F);
            float i = g + 4.0F;
            float j = vector2f.x() - h / 2.0F;
            float k = vector2f.y() - i / 2.0F;
            Matrix3x2fStack matrix3x2fStack = drawEvent.getContext_1000().getMatrices();
            matrix3x2fStack.pushMatrix();
            matrix3x2fStack.translate(vector2f.x(), vector2f.y());
            matrix3x2fStack.scale(0.8F + f * 0.2F, 0.8F + f * 0.2F);
            matrix3x2fStack.translate(-vector2f.x(), -vector2f.y());
            drawEvent.getRenderComponent184Field1_0998()
               .performAction_2577(matrix3x2fStack, j, k, h + 1.0F, i, 2.0F, RenderComponent183.computeValue_2558(0, 0, 0, (int)(130.0F * f)));
            drawEvent.getRenderComponent185Field2_0999()
               .performAction_2592(drawEvent.getContext_1000(), nestedType357.getItem_2289(), j + 2.0F, k + 2.0F - 0.25F, 0, f, g / 16.0F, false);
            RenderComponent188.renderComponent187Field5
               .performAction_2630(matrix3x2fStack, string, j + 4.0F + g, k + 2.0F, 7.25F, RenderComponent183.computeValue_2549(-1, f));
            matrix3x2fStack.popMatrix();
         }
      }
   }

   private List<Vec3d> computeValue_2278(Entity entity) {
      Vec3d vec3d = entity.getVelocity();
      Vec3d vec3d1 = entity.getEntityPos();
      boolean bl = entity instanceof ThrownEntity;
      double d = entity instanceof PotionEntity ? 0.05000000070627959 : 0.030000000582077163;
      ArrayList arrayList = new ArrayList();
      arrayList.add(vec3d1);

      for (int i = 0;
         i < 140
            && vec3d.lengthSquared() >= 1.0000000000139336E-6
            && vec3d1.getY() >= minecraft.world.getBottomY()
            && vec3d1.getY() <= minecraft.world.getBottomY() + minecraft.world.getHeight();
         i++
      ) {
         double e = minecraft.world.getFluidState(BlockPos.ofFloored(vec3d1)).isIn(FluidTags.WATER)
            ? (bl ? 0.7999999144424994 : 0.6000000001891753)
            : 0.990000120151185;
         if (bl) {
            vec3d = new Vec3d(vec3d.x * e, (vec3d.y - d) * e, vec3d.z * e);
         }

         Vec3d vec3d2 = vec3d1.add(vec3d);
         BlockHitResult blockHitResult = minecraft.world.raycast(new RaycastContext(vec3d1, vec3d2, ShapeType.COLLIDER, FluidHandling.NONE, entity));
         if (blockHitResult.getType() != Type.MISS) {
            arrayList.add(blockHitResult.getPos());
            return arrayList;
         }

         vec3d1 = vec3d2;
         if (!bl) {
            vec3d = new Vec3d(vec3d.x * e, vec3d.y * e - 0.05000000070627959, vec3d.z * e);
         }

         arrayList.add(vec3d1);
      }

      return arrayList;
   }

   private void onDrawEvent_2268(DrawEvent drawEvent) {
      ItemStack itemStack = minecraft.player.getStackInHand(Hand.MAIN_HAND);
      Item item = itemStack.getItem();
      Item item1 = minecraft.player.getStackInHand(Hand.OFF_HAND).getItem();
      float f = 0.0F;
      boolean bl = false;
      boolean bl1 = item == Items.SPLASH_POTION || item == Items.LINGERING_POTION || item1 == Items.SPLASH_POTION || item1 == Items.LINGERING_POTION;
      if (item instanceof BowItem && this.multiSelectSettingField1.computeValue_2852("Arrows").getCurrentValue()) {
         float g = this.computeValue_2284();
         if (g < 0.1F) {
            return;
         }

         f = g * 3.0F;
      } else if (item instanceof CrossbowItem && this.multiSelectSettingField1.computeValue_2852("Arrows").getCurrentValue()) {
         f = 3.0F;
      } else if (item instanceof TridentItem && this.multiSelectSettingField1.computeValue_2852("Tridents").getCurrentValue()) {
         f = 2.5F;
      } else if ((item == Items.ENDER_PEARL || item1 == Items.ENDER_PEARL) && this.multiSelectSettingField1.computeValue_2852("Ender pearl").getCurrentValue()) {
         f = 1.5F;
         bl = true;
      } else if (bl1 && this.multiSelectSettingField1.computeValue_2852("Potions").getCurrentValue()) {
         f = 0.5F;
         bl = true;
      }

      if (f != 0.0F) {
         float[] k = item instanceof CrossbowItem && this.checkCondition_2267(itemStack) ? new float[]{-10.0F, 0.0F, 10.0F} : new float[]{0.0F};

         for (float h : k) {
            Predictions.NestedType356 nestedType356 = this.computeValue_2269(
               f, bl, bl1 ? -20.0F : 0.0F, bl1 ? 0.05000000070627959 : 0.030000000582077163, h, drawEvent.getTickNeneClient_0997(), drawEvent.getStack2_1004()
            );
            if (nestedType356.getPath_2287().size() >= 2) {
               int i = NeneClient.getInstance().getProcessors().getThemes().computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547();
               List list = nestedType356.getPath_2287();

               for (int j = 0; j < list.size() - 1; j++) {
                  drawEvent.getRenderComponent185Field2_0999()
                     .performAction_2596(
                        drawEvent.getStack2_1004(), (Vec3d)list.get(j), (Vec3d)list.get(j + 1), null, RenderComponent183.computeValue_2556(i, 170), 3.0F
                     );
               }

               if (nestedType356.getHitEntity_2285() != null) {
                  drawEvent.getRenderComponent185Field2_0999()
                     .performAction_2594(
                        drawEvent.getStack2_1004(),
                        nestedType356.getHitEntity_2285().getBoundingBox(),
                        RenderComponent183.computeValue_2558(255, 100, 100, 200),
                        1.0F
                     );
               } else if (nestedType356.getHitBlock_2288() != null && nestedType356.getHitSide_2286() != null) {
                  this.performAction_2276(
                     drawEvent, (Vec3d)list.getLast(), 0.33F, RenderComponent183.computeValue_2558(255, 255, 255, 200), nestedType356.getHitSide_2286()
                  );
               }
            }
         }
      }
   }

   private void performAction_2276(DrawEvent drawEvent, Vec3d vec3d, double d, int i, Direction direction) {
      Axis axis = direction.getAxis();
      Vec3d vec3d1 = axis == Axis.Y ? new Vec3d(1.0, 0.0, 0.0) : new Vec3d(0.0, 1.0, 0.0);
      Vec3d vec3d2 = axis != Axis.X && axis == Axis.Z ? new Vec3d(1.0, 0.0, 0.0) : new Vec3d(0.0, 0.0, 1.0);
      Vec3d vec3d3 = vec3d2;
      double e = 0.7853983338895167;
      double f = d / Math.cos(e / 2.0);

      for (int j = 0; j < 8; j++) {
         double g = e * j;
         double h = e * (j + 1);
         double k = g + e / 2.0;
         drawEvent.getRenderComponent185Field2_0999()
            .performAction_2596(
               drawEvent.getStack2_1004(),
               this.computeValue_2283(vec3d, vec3d1, vec3d3, d, g),
               this.computeValue_2283(vec3d, vec3d1, vec3d3, d, h),
               this.computeValue_2283(vec3d, vec3d1, vec3d3, f, k),
               i,
               1.5F
            );
      }
   }

   private Vec3d computeValue_2283(Vec3d vec3d, Vec3d vec3d1, Vec3d vec3d2, double d, double e) {
      return vec3d.add(vec3d1.multiply(Math.cos(e) * d)).add(vec3d2.multiply(Math.sin(e) * d));
   }

   private boolean checkCondition_2267(ItemStack itemStack) {
      return ((ItemEnchantmentsComponent)itemStack.getOrDefault(DataComponentTypes.ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT))
            .getLevel(minecraft.world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(Enchantments.MULTISHOT))
         > 0;
   }

   private float computeValue_2284() {
      ItemStack itemStack = minecraft.player.getActiveItem();
      if (minecraft.player.isUsingItem() && itemStack.getItem() instanceof BowItem) {
         int i = itemStack.getItem().getMaxUseTime(itemStack, minecraft.player) - minecraft.player.getItemUseTimeLeft();
         float f = i / 20.0F;
         return Math.min((f * f + f * 2.0F) / 3.0F, 1.0F);
      } else {
         return 0.0F;
      }
   }

   private Predictions.NestedType356 computeValue_2269(float f, boolean bl, float g, double d, float h, float i, MatrixStack matrixStack) {
      float j = minecraft.player.getPitch(i);
      float k = minecraft.player.getYaw(i);
      Quaternionf quaternionf = minecraft.gameRenderer.getCamera().getRotation();
      if (matrixStack != null && quaternionf != null) {
         try {
            Matrix3f matrix3f = new Matrix3f(matrixStack.peek().getPositionMatrix());
            Quaternionf quaternionf1 = new Quaternionf(quaternionf).conjugate().mul(new Quaternionf().setFromNormalized(matrix3f));
            Vector3f vector3f = new Vector3f(0.0F, 0.0F, -1.0F).rotate(new Quaternionf(quaternionf1).conjugate());
            j = (float)Math.toDegrees(-Math.atan2(vector3f.y, Math.sqrt(vector3f.x * vector3f.x + vector3f.z * vector3f.z)));
            k = (float)Math.toDegrees(Math.atan2(-vector3f.x, vector3f.z));
         } catch (Exception exception) {
         }
      }

      double o = Math.toRadians(j);
      double p = Math.toRadians(k);
      Vec3d vec3d = new Vec3d(-Math.sin(p) * Math.cos(o), -Math.sin(Math.toRadians(minecraft.player.getPitch(i) + g)), Math.cos(p) * Math.cos(o)).normalize();
      if (h != 0.0F) {
         Vec3d vec3d1 = new Vec3d(0.0, 1.0, 0.0).crossProduct(vec3d);
         Vec3d vec3d2 = vec3d.crossProduct(vec3d1.lengthSquared() < 9.999996190428959E-11 ? new Vec3d(Math.cos(p), 0.0, Math.sin(p)) : vec3d1.normalize())
            .normalize();
         double e = Math.toRadians(h);
         double m = Math.cos(e);
         double n = Math.sin(e);
         vec3d = vec3d.multiply(m).add(vec3d2.crossProduct(vec3d).multiply(n)).add(vec3d2.multiply(vec3d2.dotProduct(vec3d) * (1.0 - m)));
      }

      Vec3d vec3d4 = vec3d.multiply(f);
      Vec3d vec3d5 = minecraft.player.getCameraPosVec(i);
      ArrayList arrayList = new ArrayList();
      arrayList.add(vec3d5);

      for (int l = 0;
         l < 130
            && vec3d4.lengthSquared() >= 1.0000000000139336E-6
            && vec3d5.getY() >= minecraft.world.getBottomY()
            && vec3d5.getY() <= minecraft.world.getBottomY() + minecraft.world.getHeight();
         l++
      ) {
         double q = minecraft.world.getFluidState(BlockPos.ofFloored(vec3d5)).isIn(FluidTags.WATER)
            ? (bl ? 0.7999999144424994 : 0.6000000001891753)
            : 0.990000120151185;
         if (bl) {
            vec3d4 = new Vec3d(vec3d4.x * q, (vec3d4.y - d) * q, vec3d4.z * q);
         }

         Vec3d vec3d6 = vec3d5.add(vec3d4);
         BlockHitResult blockHitResult = minecraft.world.raycast(new RaycastContext(vec3d5, vec3d6, ShapeType.COLLIDER, FluidHandling.NONE, minecraft.player));
         Vec3d vec3d3 = blockHitResult.getType() != Type.MISS ? blockHitResult.getPos() : vec3d6;
         Entity entity = this.computeValue_2282(vec3d5, vec3d3);
         if (entity != null) {
            arrayList.add(entity.getBoundingBox().expand(0.30000001176381136).raycast(vec3d5, vec3d3).orElse(vec3d3));
            return new Predictions.NestedType356(arrayList, null, entity, null);
         }

         if (blockHitResult.getType() != Type.MISS) {
            arrayList.add(blockHitResult.getPos());
            return new Predictions.NestedType356(arrayList, blockHitResult.getBlockPos(), null, blockHitResult.getSide());
         }

         vec3d5 = vec3d6;
         if (!bl) {
            vec3d4 = new Vec3d(vec3d4.x * q, vec3d4.y * q - 0.05000000070627959, vec3d4.z * q);
         }

         arrayList.add(vec3d5);
      }

      return new Predictions.NestedType356(arrayList, null, null, null);
   }

   private Entity computeValue_2282(Vec3d vec3d, Vec3d vec3d1) {
      Entity entity = null;
      double d = 1.7976922776554332E308;

      for (Entity entity1 : minecraft.world.getOtherEntities(minecraft.player, new Box(vec3d, vec3d1).expand(1.0))) {
         if (entity1.isAlive() && !entity1.isSpectator() && entity1 instanceof LivingEntity) {
            Optional optional = entity1.getBoundingBox().expand(0.30000001176381136).raycast(vec3d, vec3d1);
            if (optional.isPresent()) {
               double e = vec3d.squaredDistanceTo((Vec3d)optional.get());
               if (e < d) {
                  d = e;
                  entity = entity1;
               }
            }
         }
      }

      return entity;
   }

   static final class NestedType356 {
      private final List<Vec3d> path;
      private final BlockPos hitBlock;
      private final Entity hitEntity;
      private final Direction hitSide;

      NestedType356(List<Vec3d> list, BlockPos blockPos, Entity entity, Direction direction) {
         this.path = list;
         this.hitBlock = blockPos;
         this.hitEntity = entity;
         this.hitSide = direction;
      }

      public List<Vec3d> getPath_2287() {
         return this.path;
      }

      public BlockPos getHitBlock_2288() {
         return this.hitBlock;
      }

      public Entity getHitEntity_2285() {
         return this.hitEntity;
      }

      public Direction getHitSide_2286() {
         return this.hitSide;
      }
   }

   static final class NestedType357 {
      private final Vec3d impact;
      private final int ticks;
      private final ItemStack item;
      private final Animation anim;

      NestedType357(Vec3d vec3d, int i, ItemStack itemStack, Animation animation) {
         this.impact = vec3d;
         this.ticks = i;
         this.item = itemStack;
         this.anim = animation;
      }

      public Vec3d getImpact_2292() {
         return this.impact;
      }

      public int getTicks_2291() {
         return this.ticks;
      }

      public ItemStack getItem_2289() {
         return this.item;
      }

      public Animation getAnim_2290() {
         return this.anim;
      }
   }
}
