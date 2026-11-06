package aethereal.module.movement;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.EventComponent074;
import aethereal.event.PacketEvent;
import aethereal.handler.HandlerComponent108;
import aethereal.util.UtilComponent250;
import aethereal.util.UtilComponent270;
import aethereal.util.UtilComponent279;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.BundlePacket;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

@ModuleRegister(name = "Auto Dodge", description = "Automatically avoids selected targets", category = Category.Movement)
public class AutoDodge extends Module {
   private final Map<Integer, AutoDodge.NestedType348> mapField1 = new HashMap<>();
   int integerField2 = 0;

   @Override
   public void onDisable() {
      this.mapField1.clear();
      super.onDisable();
   }

   @EventTarget
   public void onEventComponent074_2020(EventComponent074 eventComponent074) {
      Iterator iterator = this.mapField1.entrySet().iterator();

      while (iterator.hasNext()) {
         if (minecraft.world.getEntityById((Integer)((Entry)iterator.next()).getKey()) == null) {
            iterator.remove();
         }
      }

      Box box = minecraft.player.getBoundingBox().expand(2.0);

      for (PotionEntity potionEntity : minecraft.world
         .getEntitiesByClass(
            PotionEntity.class, minecraft.player.getBoundingBox().expand((Integer)minecraft.options.getViewDistance().getValue() * 16), potionEntity1 -> true
         )) {
         AutoDodge.NestedType348 nestedType348 = this.mapField1.get(potionEntity.getId());
         if (nestedType348 != null) {
            boolean bl = checkCondition_2023(potionEntity, box);
            int i = 0xFF000000 | nestedType348.color & 16777215;
            if (!bl || NeneClient.getInstance().getProcessors().getFriends().checkCondition_1107(nestedType348.nick)) {
               return;
            }

            if (i != -13447886 && i != -16776961 || !(minecraft.player.distanceTo(potionEntity) > 2.300000381469741) || this.integerField2 < 0) {
               return;
            }

            ItemStack itemStack = Items.DRIED_KELP.getDefaultStack();
            if (!minecraft.player.getItemCooldownManager().isCoolingDown(itemStack) && UtilComponent270.computeValue_3526(Items.DRIED_KELP) != -1) {
               if (HandlerComponent108.getUSEABLE_1160().getListField1_1222().isEmpty()) {
                  if (UtilComponent250.computeValue_3385().computeValue_3389(new UtilComponent250(minecraft.player.getYaw(), minecraft.player.getPitch()))
                     < 20.0) {
                     this.integerField2++;
                  }

                  if (this.integerField2 >= 2) {
                     this.integerField2 = 5;
                     HandlerComponent108.getUSEABLE_1160().performAction_1225(Items.DRIED_KELP.getDefaultStack());
                  }
               }

               UtilComponent250 utilComponent250 = UtilComponent250.computeValue_3390(minecraft.player.getEyePos(), potionEntity.getEyePos());
               HandlerComponent108.getROTATION_1157()
                  .performAction_1141(
                     new UtilComponent250(
                        utilComponent250.getYaw_3386() + UtilComponent279.computeValue_3582(-3.0F, 3.0F),
                        utilComponent250.getPitch_3387() + UtilComponent279.computeValue_3582(-3.0F, 3.0F)
                     ),
                     180.0F,
                     1,
                     1
                  );
               break;
            }
         }
      }

      this.integerField2++;
   }

   @EventTarget
   public void onPacketEvent_2021(PacketEvent packetEvent) {
      if (packetEvent.checkCondition_1013() && minecraft.player != null && minecraft.world != null) {
         this.performAction_2025(packetEvent.getPacket_1015());
         if (packetEvent.checkCondition_1013()) {
            GameMessageS2CPacket gameMessageS2CPacket = (GameMessageS2CPacket)packetEvent.getPacket_1015();
            if (gameMessageS2CPacket instanceof GameMessageS2CPacket gameMessageS2CPacket1
               && gameMessageS2CPacket1.content().getString().equals("This item does not work on this anarchy")) {
               this.integerField2 = -50;
            }
         }
      }
   }

   private void performAction_2025(Packet<?> packet) {
      if (packet instanceof BundlePacket bundlePacket) {
         for (Packet packet1 : bundlePacket.getPackets()) {
            this.performAction_2025(packet1);
         }
      } else {
         if (packet instanceof EntitySpawnS2CPacket entitySpawnS2CPacket) {
            if (entitySpawnS2CPacket.getEntityType() != EntityType.SPLASH_POTION && entitySpawnS2CPacket.getEntityType() != EntityType.LINGERING_POTION) {
               return;
            }

            Map map = this.computeValue_2018();
            Vec3d vec3d = new Vec3d(entitySpawnS2CPacket.getX(), entitySpawnS2CPacket.getY(), entitySpawnS2CPacket.getZ());
            Vec3d vec3d1 = entitySpawnS2CPacket.getVelocity();
            double d = 1.7976922776554304E308;
            String string = null;
            int i = 0;

            for (Entry entry : map.entrySet()) {
               AutoDodge.NestedType347 nestedType347 = (AutoDodge.NestedType347)entry.getValue();
               double e = vec3d.distanceTo(nestedType347.position);
               if (e <= 25.0) {
                  if (nestedType347.position.y - vec3d.y > 2.0) {
                     if (new Vec3d(vec3d.x - nestedType347.position.x, 0.0, vec3d.z - nestedType347.position.z).length() < 15.0 && e < d) {
                        d = e;
                        i = nestedType347.color;
                        string = (String)entry.getKey();
                     }
                  } else if ((
                        vec3d1.lengthSquared() <= 9.99999773128142E-7
                           || vec3d1.normalize().dotProduct(nestedType347.lookDirection.normalize()) > 0.10000000396251493
                     )
                     && e < d) {
                     d = e;
                     i = nestedType347.color;
                     string = (String)entry.getKey();
                  }
               }
            }

            if (string != null) {
               this.integerField2 = 0;
               this.mapField1.put(entitySpawnS2CPacket.getEntityId(), new AutoDodge.NestedType348(i, string));
            }
         }
      }
   }

   private Map<String, AutoDodge.NestedType347> computeValue_2018() {
      HashMap hashMap = new HashMap();

      for (AbstractClientPlayerEntity abstractClientPlayerEntity : minecraft.world.getPlayers()) {
         ItemStack itemStack = abstractClientPlayerEntity.getMainHandStack();
         ItemStack itemStack1 = abstractClientPlayerEntity.getOffHandStack();
         int i = itemStack.getItem() == Items.SPLASH_POTION
            ? computeValue_2019(itemStack)
            : (itemStack1.getItem() == Items.SPLASH_POTION ? computeValue_2019(itemStack1) : -1);
         if (i >= 0 && abstractClientPlayerEntity != minecraft.player && minecraft.player.squaredDistanceTo(abstractClientPlayerEntity) <= 400.0) {
            hashMap.put(
               abstractClientPlayerEntity.getName().getString(),
               new AutoDodge.NestedType347(i, abstractClientPlayerEntity.getEntityPos(), abstractClientPlayerEntity.getRotationVec(1.0F))
            );
         }
      }

      return hashMap;
   }

   private static boolean checkCondition_2023(PotionEntity potionEntity, Box box) {
      Vec3d vec3d = potionEntity.getVelocity();
      Vec3d vec3d1 = potionEntity.getEntityPos();

      for (int i = 0;
         i < 70
            && vec3d.lengthSquared() >= 9.99999773128142E-7
            && vec3d1.y >= minecraft.world.getBottomY()
            && vec3d1.y <= minecraft.world.getBottomY() + minecraft.world.getHeight();
         i++
      ) {
         double d = minecraft.world.getFluidState(BlockPos.ofFloored(vec3d1)).isIn(FluidTags.WATER) ? 0.8000000016738433 : 0.9900000205305426;
         vec3d = new Vec3d(vec3d.x * d, (vec3d.y - 0.0500000024232657) * d, vec3d.z * d);
         Vec3d vec3d2 = vec3d1.add(vec3d);
         RaycastContext raycastContext = new RaycastContext(vec3d1, vec3d2, ShapeType.COLLIDER, FluidHandling.NONE, potionEntity);
         BlockHitResult blockHitResult = minecraft.world.raycast(raycastContext);
         if (blockHitResult.getType() == Type.BLOCK) {
            return checkCondition_2024(box, vec3d1, blockHitResult.getPos());
         }

         if (checkCondition_2024(box, vec3d1, vec3d2)) {
            return true;
         }

         vec3d1 = vec3d2;
      }

      return false;
   }

   private static boolean checkCondition_2024(Box box, Vec3d vec3d, Vec3d vec3d1) {
      return new Box(
            Math.min(vec3d.x, vec3d1.x),
            Math.min(vec3d.y, vec3d1.y),
            Math.min(vec3d.z, vec3d1.z),
            Math.max(vec3d.x, vec3d1.x),
            Math.max(vec3d.y, vec3d1.y),
            Math.max(vec3d.z, vec3d1.z)
         )
         .expand(0.11999995180429479)
         .intersects(box);
   }

   private static int computeValue_2019(ItemStack itemStack) {
      PotionContentsComponent potionContentsComponent = (PotionContentsComponent)itemStack.get(DataComponentTypes.POTION_CONTENTS);
      return potionContentsComponent != null ? potionContentsComponent.getColor() & 16777215 : 0;
   }

   static final class NestedType347 {
      final int color;
      final Vec3d position;
      final Vec3d lookDirection;

      NestedType347(int i, Vec3d vec3d, Vec3d vec3d1) {
         this.color = i;
         this.position = vec3d;
         this.lookDirection = vec3d1;
      }

      public int getColor_2027() {
         return this.color;
      }

      public Vec3d getPosition_2026() {
         return this.position;
      }

      public Vec3d getLookDirection_2028() {
         return this.lookDirection;
      }
   }

   static final class NestedType348 {
      final int color;
      final String nick;

      NestedType348(int i, String string) {
         this.color = i;
         this.nick = string;
      }

      public int getColor_2029() {
         return this.color;
      }

      public String getNick_2030() {
         return this.nick;
      }
   }
}
