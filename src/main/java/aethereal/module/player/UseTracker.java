package aethereal.module.player;

import aethereal.config.ThemeInfo;
import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.EventComponent074;
import aethereal.event.PacketEvent;
import aethereal.event.PotionEvent;
import aethereal.module.render.EntityESP;
import aethereal.notification.NotificationComponent181;
import aethereal.setting.BooleanSetting;
import aethereal.setting.MultiSelectSetting;
import aethereal.util.UtilComponent252;
import aethereal.util.UtilComponent265;
import aethereal.util.UtilComponent279;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.network.packet.s2c.play.EntityAttributesS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

@ModuleRegister(name = "Use Tracker", description = "Отслеживает выбранные использования и уведомляет о них", category = Category.Player)
public class UseTracker extends Module {
   private final MultiSelectSetting multiSelectSettingField1 = new MultiSelectSetting(
      "Отслеживать использования", new BooleanSetting("Тотема", true), new BooleanSetting("Зелья", true), new BooleanSetting("Предмета", true)
   );

   public UseTracker() {
      this.addSettings(this.multiSelectSettingField1);
   }

   @EventTarget
   public void onEventComponent074_2111(EventComponent074 eventComponent074) {
      if (this.multiSelectSettingField1.computeValue_2852("Предмета").getCurrentValue()) {
         for (Entity entity : minecraft.world.getEntities()) {
            if (entity instanceof PlayerEntity playerEntity && playerEntity != minecraft.player) {
               ItemStack itemStack = playerEntity.getActiveItem();
               if ((itemStack.getItem() instanceof PotionItem || itemStack.get(DataComponentTypes.FOOD) != null || itemStack.getItem() == Items.MILK_BUCKET)
                  && playerEntity.getItemUseTimeLeft() == 1) {
                  String string = itemStack.getItem() instanceof PotionItem ? "&a" : "&c";
                  if (itemStack.isOf(Items.MILK_BUCKET)) {
                     NeneClient.getInstance()
                        .getProcessors()
                        .getModules()
                        .getEntityESPField31_0487()
                        .getListField2_2250()
                        .removeIf(nestedType355 -> nestedType355.getIntegerField1_2254() == playerEntity.getId());
                  }

                  UtilComponent265.performAction_3508(
                     "[" + this.getName() + "]",
                     playerEntity.getName().getString() + " использовал \"" + string + itemStack.getItem().getName().getString() + "&7\""
                  );
                  NeneClient.getInstance()
                     .getProcessors()
                     .getNotificationComponent182Field5_0676()
                     .performAction_2523(
                        new NotificationComponent181(
                           itemStack.copy(), playerEntity.getName().getString() + " использовал " + itemStack.getItem().getName().getString(), 1500
                        )
                     );
               }
            }
         }
      }
   }

   @EventTarget
   public void onPotionEvent_2113(PotionEvent potionEvent) {
      if (this.multiSelectSettingField1.computeValue_2852("Зелья").getCurrentValue()
         && potionEvent.getType_1017() == PotionEvent.State317.PARTICLES
         && minecraft.world != null) {
         for (UseTracker.State354 state354 : UseTracker.State354.values()) {
            for (int i : state354.getThrowColor_2122()) {
               if ((i & 16777215) == (potionEvent.getData_1018() & 16777215)) {
                  BlockPos blockPos = potionEvent.getPos_1016();
                  Vec3d vec3d = blockPos.toCenterPos();
                  Box box = new Box(
                     blockPos.getX() - 4, blockPos.getY() - 4, blockPos.getZ() - 4, blockPos.getX() + 5, blockPos.getY() + 5, blockPos.getZ() + 5
                  );

                  for (PlayerEntity playerEntity : minecraft.world.getEntitiesByClass(PlayerEntity.class, box, playerEntity1 -> playerEntity1.isAlive())) {
                     Box box1 = playerEntity.getBoundingBox();
                     double d = 1.0
                        - Math.sqrt(
                              Math.pow(vec3d.x - MathHelper.clamp(vec3d.x, box1.minX, box1.maxX), 2.0)
                                 + Math.pow(vec3d.y - MathHelper.clamp(vec3d.y, box1.minY, box1.maxY), 2.0)
                                 + Math.pow(vec3d.getZ() - MathHelper.clamp(vec3d.getZ(), box1.minZ, box1.maxZ), 2.0)
                           )
                           / 4.0;
                     if (d > 0.0) {
                        ArrayList arrayList = new ArrayList();
                        if (playerEntity != minecraft.player) {
                           UtilComponent265.computeValue_3506(
                              "[" + this.getName() + "]",
                              UtilComponent265.computeValue_3504(playerEntity.getName().getString() + " получил эффекты от \"")
                                 .append(state354.computeValue_2119())
                                 .append(UtilComponent265.computeValue_3504("\""))
                           );
                           UtilComponent265.performAction_3508("[" + this.getName() + "]", "- Успешность: &a" + (int)(d * 100.0) + "%");
                        }

                        MutableText mutableText;
                        if (playerEntity == minecraft.player) {
                           mutableText = Text.literal("Вы получили эффекты от ")
                              .styled(
                                 style -> style.withColor(
                                    NeneClient.getInstance().getProcessors().getThemes().computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547()
                                 )
                              )
                              .append(state354.computeValue_2119())
                              .append(UtilComponent265.computeValue_3504(" &7(" + (int)(d * 100.0) + "%)"));
                        } else {
                           mutableText = UtilComponent265.computeValue_3504(playerEntity.getName().getString() + " получил эффекты от ")
                              .append(state354.computeValue_2119())
                              .append(UtilComponent265.computeValue_3504(" &7(" + (int)(d * 100.0) + "%)"));
                        }

                        NeneClient.getInstance()
                           .getProcessors()
                           .getNotificationComponent182Field5_0676()
                           .performAction_2523(new NotificationComponent181("o", mutableText, 2000));

                        for (Entry entry : state354.getEffects_2120()) {
                           int j = Math.max(0, MathHelper.floor(((int[])entry.getValue())[0] * d + 0.5));
                           int k = ((int[])entry.getValue())[1];
                           if (j > 20) {
                              int l = j / 20;
                              if (playerEntity != minecraft.player) {
                                 UtilComponent265.performAction_3508(
                                    "[" + this.getName() + "]",
                                    "- &c"
                                       + ((StatusEffect)((RegistryEntry)entry.getKey()).value()).getName().getString()
                                       + " "
                                       + UtilComponent279.computeValue_3584(k)
                                       + " &7("
                                       + l / 60
                                       + ":"
                                       + String.format("%02d", l % 60)
                                       + ")"
                                 );
                              }

                              if (((RegistryEntry)entry.getKey()).equals(StatusEffects.BLINDNESS)
                                 || ((RegistryEntry)entry.getKey()).equals(StatusEffects.STRENGTH)
                                 || ((RegistryEntry)entry.getKey()).equals(StatusEffects.SLOWNESS)
                                 || ((RegistryEntry)entry.getKey()).equals(StatusEffects.WITHER)
                                 || ((RegistryEntry)entry.getKey()).equals(StatusEffects.POISON)
                                 || ((RegistryEntry)entry.getKey()).equals(StatusEffects.WEAKNESS)
                                 || ((RegistryEntry)entry.getKey()).equals(StatusEffects.REGENERATION)
                                 || ((RegistryEntry)entry.getKey()).equals(StatusEffects.HEALTH_BOOST)
                                 || ((RegistryEntry)entry.getKey()).equals(StatusEffects.RESISTANCE)) {
                                 arrayList.add(new StatusEffectInstance((RegistryEntry)entry.getKey(), j, k));
                              }
                           }
                        }

                        if (!arrayList.isEmpty()) {
                           NeneClient.getInstance()
                              .getProcessors()
                              .getModules()
                              .getEntityESPField31_0487()
                              .getListField2_2250()
                              .add(new EntityESP.NestedType355(List.copyOf(arrayList), playerEntity.getId(), playerEntity.age));
                        }
                     }
                  }

                  return;
               }
            }
         }
      }
   }

   @EventTarget
   public void onPacketEvent_2112(PacketEvent packetEvent) {
      if (packetEvent.checkCondition_1013()) {
         EntityAttributesS2CPacket entityAttributesS2CPacket = (EntityAttributesS2CPacket)packetEvent.getPacket_1015();
         if (entityAttributesS2CPacket instanceof EntityAttributesS2CPacket entityAttributesS2CPacket1) {
            for (net.minecraft.network.packet.s2c.play.EntityAttributesS2CPacket.Entry entry : entityAttributesS2CPacket1.getEntries()) {
               if (entry.attribute().getKey().toString().contains("minecraft:movement_speed")) {
                  for (EntityAttributeModifier entityAttributeModifier : entry.modifiers()) {
                     if (minecraft.world.getEntityById(entityAttributesS2CPacket1.getEntityId()) instanceof PlayerEntity
                        && entityAttributeModifier.id().toString().equals("minecraft:effect.speed")
                        && entityAttributeModifier.value() <= 0.40000001199465773
                        && entityAttributeModifier.operation() == Operation.ADD_MULTIPLIED_TOTAL) {
                        NeneClient.getInstance()
                           .getProcessors()
                           .getModules()
                           .getEntityESPField31_0487()
                           .getListField2_2250()
                           .removeIf(nestedType355 -> nestedType355.getIntegerField1_2254() == entityAttributesS2CPacket1.getEntityId());
                     }
                  }
               }
            }
         }

         EntityStatusS2CPacket entityStatusS2CPacket = (EntityStatusS2CPacket)packetEvent.getPacket_1015();
         if (entityStatusS2CPacket instanceof EntityStatusS2CPacket entityStatusS2CPacket1) {
            ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity)entityStatusS2CPacket1.getEntity(minecraft.world);
            if (clientPlayerEntity instanceof LivingEntity) {
               ClientPlayerEntity clientPlayerEntity1 = clientPlayerEntity;
               if (entityStatusS2CPacket1.getStatus() == 35) {
                  NeneClient.getInstance()
                     .getProcessors()
                     .getModules()
                     .getEntityESPField31_0487()
                     .getListField2_2250()
                     .removeIf(nestedType355 -> nestedType355.getIntegerField1_2254() == clientPlayerEntity1.getId());
                  if (this.multiSelectSettingField1.computeValue_2852("Тотема").getCurrentValue()) {
                     ItemStack itemStack = clientPlayerEntity1.getMainHandStack().getItem() == Items.TOTEM_OF_UNDYING
                        ? clientPlayerEntity1.getMainHandStack()
                        : (clientPlayerEntity1.getOffHandStack().getItem() == Items.TOTEM_OF_UNDYING ? clientPlayerEntity1.getOffHandStack() : null);
                     if (itemStack != null) {
                        String string = UtilComponent252.NestedType400.checkCondition_3406()
                           ? UtilComponent252.NestedType400.computeValue_3404(itemStack)
                           : itemStack.getName().getString();
                        UtilComponent265.performAction_3508(
                           "[" + this.getName() + "]",
                           (clientPlayerEntity1 == minecraft.player ? "Вы потеряли " : clientPlayerEntity1.getName().getString() + " потерял ")
                              + string
                              + ", зачарован: "
                              + (!string.startsWith("Талисман") && !itemStack.hasGlint() ? "&c●&7" : "&a●&7")
                        );
                     }
                  }
               }
            }
         }
      }
   }

   public enum State354 {
      POPPER_POTION(
         List.of(
            Map.entry(StatusEffects.SLOWNESS, new int[]{200, 9}),
            Map.entry(StatusEffects.SPEED, new int[]{300, 4}),
            Map.entry(StatusEffects.BLINDNESS, new int[]{100, 9}),
            Map.entry(StatusEffects.GLOWING, new int[]{3600, 0})
         ),
         "[★] Хлопушка",
         new int[]{16738740},
         new int[]{16711765, 16727869, 16743972, 16760076, 14410269, 9628759, 4846994, 65484}
      ),
      HOLY_WATER(
         List.of(
            Map.entry(StatusEffects.REGENERATION, new int[]{900, 1}),
            Map.entry(StatusEffects.INVISIBILITY, new int[]{12000, 1}),
            Map.entry(StatusEffects.INSTANT_HEALTH, new int[]{0, 2})
         ),
         "[★] Святая вода",
         new int[]{16777215},
         new int[]{16777163, 16777148, 16777132, 16777117, 16777102, 16777087, 16776815, 16776800, 16776785, 16776769, 16776754}
      ),
      RAGE_POTION(
         List.of(Map.entry(StatusEffects.STRENGTH, new int[]{600, 4}), Map.entry(StatusEffects.SLOWNESS, new int[]{600, 3})),
         "[★] Зелье Гнева",
         new int[]{10040115},
         new int[]{9109504, 10620416, 12131328, 13707520, 15218432, 16729344, 16732928, 16736512, 16740352, 16743936, 16747520}
      ),
      PALLADIN_POTION(
         List.of(
            Map.entry(StatusEffects.RESISTANCE, new int[]{12000, 0}),
            Map.entry(StatusEffects.FIRE_RESISTANCE, new int[]{12000, 0}),
            Map.entry(StatusEffects.HEALTH_BOOST, new int[]{1200, 2}),
            Map.entry(StatusEffects.INVISIBILITY, new int[]{18000, 2})
         ),
         "[★] Зелье Палладина",
         new int[]{65535},
         new int[]{
            13762395,
            14090092,
            14417789,
            14745486,
            15007648,
            15335345,
            15663042,
            15990739,
            15663042,
            15335345,
            15007648,
            14745486,
            14417789,
            14090092,
            13762395
         }
      ),
      ASSASSIN_POTION(
         List.of(
            Map.entry(StatusEffects.STRENGTH, new int[]{1200, 3}),
            Map.entry(StatusEffects.SPEED, new int[]{6000, 2}),
            Map.entry(StatusEffects.HASTE, new int[]{1200, 0}),
            Map.entry(StatusEffects.INSTANT_DAMAGE, new int[]{0, 1})
         ),
         "[★] Зелье Ассасина",
         new int[]{3355443},
         new int[]{4277061, 4603456, 4929850, 5256245, 5516848, 5843242, 6169637, 6496032, 6822427, 7148821, 7409424, 7735819, 8062213, 8388608}
      ),
      RADIATION_POTION(
         List.of(
            Map.entry(StatusEffects.POISON, new int[]{1200, 1}),
            Map.entry(StatusEffects.WITHER, new int[]{1200, 1}),
            Map.entry(StatusEffects.SLOWNESS, new int[]{1800, 2}),
            Map.entry(StatusEffects.HUNGER, new int[]{1200, 4}),
            Map.entry(StatusEffects.GLOWING, new int[]{2400, 0})
         ),
         "[★] Зелье Радиации",
         new int[]{3329330},
         new int[]{16774970, 16250192, 15659878, 15135100, 14545043, 14020265, 13429951, 12905919, 12382378, 11858836, 11269759, 10746217, 10222676, 9699134}
      ),
      SLEEPING_PILL(
         List.of(
            Map.entry(StatusEffects.WEAKNESS, new int[]{1800, 1}),
            Map.entry(StatusEffects.MINING_FATIGUE, new int[]{200, 1}),
            Map.entry(StatusEffects.WITHER, new int[]{1800, 2}),
            Map.entry(StatusEffects.BLINDNESS, new int[]{200, 0})
         ),
         "[★] Снотворное",
         new int[]{255, 4737096},
         new int[]{4132250, 3219615, 2306725, 1394090, 481455, 812728, 2322884, 3833041, 5408733, 6918889}
      );

      private final List<Entry<RegistryEntry<StatusEffect>, int[]>> effects;
      private final String displayName;
      private final int[] throwColor;
      private final int[] nameColors;

      State354(final List list, final String string1, final int[] j, final int[] k) {
         this.effects = list;
         this.displayName = string1;
         this.throwColor = j;
         this.nameColors = k;
      }

      public List<Entry<RegistryEntry<StatusEffect>, int[]>> getEffects_2120() {
         return this.effects;
      }

      public String getDisplayName_2121() {
         return this.displayName;
      }

      public int[] getThrowColor_2122() {
         return this.throwColor;
      }

      public int[] getNameColors_2123() {
         return this.nameColors;
      }

      public MutableText computeValue_2119() {
         int i = this.displayName.indexOf(32) + 1;
         MutableText mutableText = Text.literal("");

         for (int j = 0; j < this.displayName.length(); j++) {
            int k = j < i ? this.nameColors[0] : this.nameColors[Math.min(j - i, this.nameColors.length - 1)];
            mutableText.append(Text.literal(String.valueOf(this.displayName.charAt(j))).setStyle(Style.EMPTY.withColor(k).withBold(true)));
         }

         return mutableText;
      }
   }
}
