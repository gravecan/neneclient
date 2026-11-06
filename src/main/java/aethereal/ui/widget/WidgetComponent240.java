package aethereal.ui.widget;

import aethereal.config.ThemeInfo;
import aethereal.config.ThemeManager;
import aethereal.core.ClientTickEvent;
import aethereal.core.MinecraftAccess;
import aethereal.core.NeneClient;
import aethereal.event.DrawEvent;
import aethereal.module.misc.StreamerMode;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent186;
import aethereal.render.RenderComponent188;
import aethereal.setting.BooleanSetting;
import aethereal.ui.element.ElementComponent210;
import aethereal.util.UtilComponent252;
import aethereal.util.UtilComponent270;
import aethereal.util.UtilComponent284;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.UseCooldownComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class WidgetComponent240 extends WidgetComponent249 implements MinecraftAccess {
   private final BooleanSetting booleanSettingField1 = new BooleanSetting("Show armor", true);
   private final Map<UUID, WidgetComponent240.NestedType399> mapField2 = new HashMap<>();
   private final List<UUID> listField3 = new ArrayList<>();
   private final UUID[] uUIDArrayField4 = new UUID[2];

   public WidgetComponent240() {
      super(new ElementComponent210("Environment", 0.0F, 0.0F, 0.0F, 0.0F));
      this.getDragInfo_3360().setWidgetComponent249Field1_3016(this);
      this.performAction_3362(this.booleanSettingField1);
   }

   @Override
   public void onDrawEvent_3315(DrawEvent drawEvent) {
      this.getAnimationField3_3363().performAction_2535(0.0F, 1.0F, 0.3F, RenderComponent186.contract372Field7, drawEvent.getTickNeneClient_0997());
      float f = this.computeValue_3367();
      float g = this.getDragInfo_3360().computeValue_3007();
      float h = this.getDragInfo_3360().computeValue_3009();
      Stream stream = this.listField3.stream();
      Map map = this.mapField2;
      List list = stream.<WidgetComponent240.NestedType399>map(uUID -> (WidgetComponent240.NestedType399)map.get(uUID))
         .filter(nestedType3991 -> Objects.nonNull(nestedType3991))
         .toList();
      float i = 14.5F + RenderComponent188.renderComponent187Field5.computeValue_2626("Environment", this.floatValueField8) + 5.0F;

      for (WidgetComponent240.NestedType399 nestedType399 : list) {
         i = Math.max(
            i,
            Math.max(
               20.0F
                  + RenderComponent188.renderComponent187Field5
                     .computeValue_2626(nestedType399.stringField4 + (int)nestedType399.floatValueField6 + "HP", 6.5F)
                  + 34.0F,
               nestedType399.linkedHashMapField2.size() * 15.0F - 2.0F
            )
         );
      }

      this.getDragInfo_3360().performAction_3006(i);
      if (f > 0.0F) {
         this.performAction_3376(drawEvent, "x", "Environment", i, f);
      }

      float j = h + this.floatValueField7 + 3.0F;
      Iterator iterator = list.iterator();

      while (iterator.hasNext()) {
         j += this.computeValue_3327(drawEvent, (WidgetComponent240.NestedType399)iterator.next(), g, j, i, f);
      }

      this.getDragInfo_3360().performAction_3018(Math.max(this.floatValueField7, j - h - 2.0F));
      super.onDrawEvent_3315(drawEvent);
   }

   private float computeValue_3327(DrawEvent drawEvent, WidgetComponent240.NestedType399 nestedType399, float f, float g, float h, float i) {
      float j = g + (12.0F - RenderComponent188.renderComponent187Field5.computeValue_2622(6.5F)) / 2.0F - 0.5F;
      String string = (int)nestedType399.floatValueField6 + "HP";
      this.performAction_3326(drawEvent, f, g, h, 12.0F, 3.0F, i, false);
      drawEvent.getRenderComponent184Field1_0998()
         .performAction_2579(
            drawEvent.getStack_1005(),
            f + 2.0F,
            g + 2.0F,
            8.0F,
            8.0F,
            1.5F,
            RenderComponent183.computeValue_2549(-1, i),
            0.125F,
            0.125F,
            0.125F,
            0.125F,
            UtilComponent284.computeValue_3617(nestedType399.identifierField5)
         );
      RenderComponent188.renderComponent187Field5
         .performAction_2630(
            drawEvent.getStack_1005(),
            string,
            f + h - 3.0F - RenderComponent188.renderComponent187Field5.computeValue_2626(string, 6.5F),
            j,
            6.5F,
            RenderComponent183.computeValue_2549(-1, i)
         );
      float k = f + h - 4.0F - RenderComponent188.renderComponent187Field5.computeValue_2626(string, 6.5F);
      if (this.booleanSettingField1.getCurrentValue()) {
         for (int l = 3; l >= 0; l--) {
            if (!nestedType399.itemStackArrayField3[l].isEmpty()) {
               k -= 7.0F;
               drawEvent.getRenderComponent185Field2_0999()
                  .performAction_2592(
                     drawEvent.getContext_1000(),
                     UtilComponent270.computeValue_3541(nestedType399.itemStackArrayField3[l]),
                     k - 2.0F,
                     g + 2.0F,
                     0,
                     i,
                     0.5F,
                     false
                  );
            }
         }
      }

      RenderComponent188.renderComponent187Field5
         .performAction_2631(
            drawEvent.getStack_1005(), nestedType399.stringField4, f + 12.5F, j, 6.5F, RenderComponent183.computeValue_2549(-1, i), k - 16.5F - f
         );
      if (nestedType399.linkedHashMapField2.isEmpty()) {
         return 14.0F;
      }

      long x = System.currentTimeMillis();
      ArrayList arrayList = new ArrayList<>(nestedType399.linkedHashMapField2.values());
      Collections.reverse(arrayList);

      for (int m = 0; m < arrayList.size(); m++) {
         WidgetComponent240.NestedType398 nestedType398 = (WidgetComponent240.NestedType398)arrayList.get(m);
         float n = f + m * 15.0F;
         float o = g + 12.0F + 2.0F;
         int p = nestedType398.computeValue_3334(x);
         this.performAction_3326(drawEvent, n, o, 13.0F, 13.0F, 2.0F, i, nestedType398.longValueField3 != 0L);
         drawEvent.getRenderComponent185Field2_0999()
            .performAction_2592(
               drawEvent.getContext_1000(), nestedType398.itemStackField1, n + 2.1F, o + 2.1F, 0, i, 0.55F, nestedType398.longValueField3 == 0L
            );
         if (p > 0) {
            String string1 = p > 99 ? "99+" : String.valueOf(p);
            float q = RenderComponent188.renderComponent187Field5.computeValue_2626(string1, 6.5F);
            float r = q + 6.0F;
            float s = n + (13.0F - r) / 2.0F;
            float t = o - 4.0F;
            float u = s + (r - q - 0.3F) / 2.0F;
            float v = t + (8.0F - RenderComponent188.renderComponent187Field5.computeValue_2622(6.5F)) / 2.0F + 1.0F;
            int w = RenderComponent183.computeValue_2549(RenderComponent183.computeValue_2558(255, 60, 60, 255), i);
            this.performAction_3326(drawEvent, s + 1.5F, t + 1.0F, r - 3.0F, 8.0F, 2.0F, i, false);
            RenderComponent188.renderComponent187Field5.performAction_2630(drawEvent.getStack_1005(), string1, u, v, 6.5F, w);
            RenderComponent188.renderComponent187Field5.performAction_2630(drawEvent.getStack_1005(), string1, u + 0.3F, v, 6.5F, w);
         }
      }

      return 29.0F;
   }

   private void performAction_3326(DrawEvent drawEvent, float f, float g, float h, float i, float j, float k, boolean bl) {
      ThemeManager themeManager = NeneClient.getInstance().getProcessors().getThemes();
      int l = RenderComponent183.computeValue_2550(
         themeManager.computeValue_0557(ThemeInfo.BACKGROUND_HUD).computeValue_0547(),
         themeManager.computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547(),
         themeManager.computeValue_0557(ThemeInfo.PRIMARY).computeValue_0548() / 6.0F
      );
      drawEvent.getRenderComponent184Field1_0998()
         .performAction_2562(
            drawEvent.getStack_1005(),
            f,
            g,
            h,
            i,
            j,
            RenderComponent183.computeValue_2549(
               bl ? RenderComponent183.computeValue_2550(l, RenderComponent183.computeValue_2558(255, 60, 60, 255), 0.35F) : l,
               themeManager.computeValue_0557(ThemeInfo.BACKGROUND_HUD).computeValue_0548() * k
            ),
            k
         );
   }

   @Override
   public void performAction_3316(ClientTickEvent clientTickEvent) {
      if (minecraft.world != null && minecraft.player != null) {
         PlayerEntity playerEntity1 = (PlayerEntity)NeneClient.getInstance().getProcessors().getModules().getAuraField3_0402().getLivingEntityField2_1528();
         PlayerEntity playerEntity;
         if (playerEntity1 instanceof PlayerEntity playerEntity2) {
            playerEntity = playerEntity2;
         } else {
            PlayerEntity playerEntity5 = (PlayerEntity)NeneClient.getInstance()
               .getProcessors()
               .getModules()
               .getTriggerBotField4_0418()
               .getLivingEntityField6_1669();
            if (playerEntity5 instanceof PlayerEntity playerEntity3) {
               playerEntity = playerEntity3;
            } else {
               playerEntity = null;
            }
         }

         PlayerEntity playerEntity6 = playerEntity;
         if (playerEntity6 != null && !playerEntity6.getUuid().equals(this.uUIDArrayField4[0])) {
            this.uUIDArrayField4[1] = this.uUIDArrayField4[0];
            this.uUIDArrayField4[0] = playerEntity6.getUuid();
         }

         long l = System.currentTimeMillis();
         List list = minecraft.world
            .getPlayers()
            .stream()
            .filter(
               abstractClientPlayerEntity -> abstractClientPlayerEntity != minecraft.player
                  && abstractClientPlayerEntity.isAlive()
                  && !NeneClient.getInstance().getProcessors().getFriends().checkCondition_1107(abstractClientPlayerEntity.getName().getString())
            )
            .sorted(
               Comparator.<AbstractClientPlayerEntity>comparingInt(playerEntity7 -> {
                     if (playerEntity7.getUuid().equals(this.uUIDArrayField4[0])) {
                        return 0;
                     } else {
                        return playerEntity7.getUuid().equals(this.uUIDArrayField4[1]) ? 1 : 2;
                     }
                  })
                  .thenComparingInt(playerEntity7 -> -computeValue_3330(playerEntity7))
                  .thenComparing(playerEntity7 -> playerEntity7.getName().getString(), (string, string1) -> string.compareToIgnoreCase(string1))
            )
            .limit(2L)
            .toList();
         this.listField3.clear();

         for (PlayerEntity playerEntity4 : list) {
            this.listField3.add(playerEntity4.getUuid());
            this.mapField2.computeIfAbsent(playerEntity4.getUuid(), WidgetComponent240.NestedType399::new).performAction_3337(playerEntity4, l);
         }

         this.mapField2.values().removeIf(nestedType399 -> !this.listField3.contains(nestedType399.uUIDField1) && l - nestedType399.longValueField7 > 30000L);
      }

      this.getAnimationField3_3363()
         .performAction_2528(minecraft.world != null && (!this.listField3.isEmpty() || minecraft.currentScreen instanceof ChatScreen));
      super.performAction_3316(clientTickEvent);
   }

   private static int computeValue_3330(PlayerEntity playerEntity) {
      int i = 0;

      for (int j = 0; j < 4; j++) {
         String string = Registries.ITEM.getId(playerEntity.getEquippedStack(EquipmentSlot.values()[5 - j]).getItem()).getPath();
         int k = i;
         byte b;
         if (string.startsWith("netherite_")) {
            b = 4;
         } else if (string.startsWith("diamond_")) {
            b = 3;
         } else if (string.startsWith("iron_")) {
            b = 2;
         } else {
            b = (byte)(string.startsWith("leather_") ? 1 : 0);
         }

         i = k + b;
      }

      return i;
   }

   static int computeValue_3324(ItemStack itemStack) {
      if (itemStack.isOf(Items.DRIED_KELP)
         || itemStack.isOf(Items.NETHERITE_SCRAP)
         || itemStack.isOf(Items.SNOWBALL)
         || itemStack.isOf(Items.SUGAR)
         || itemStack.isOf(Items.PHANTOM_MEMBRANE)
         || itemStack.isOf(Items.WIND_CHARGE)
         || itemStack.isOf(Items.ENDER_EYE)) {
         return 3;
      } else if (itemStack.contains(DataComponentTypes.FOOD)) {
         return 2;
      } else {
         return !itemStack.isOf(Items.EXPERIENCE_BOTTLE) && !itemStack.isOf(Items.FIRE_CHARGE) ? 0 : 1;
      }
   }

   static int computeValue_3332(ItemStack itemStack) {
      if (itemStack.isOf(Items.ENCHANTED_GOLDEN_APPLE)) {
         return 150;
      } else if (itemStack.isOf(Items.SUGAR) || itemStack.isOf(Items.ENDER_EYE) || itemStack.isOf(Items.FIRE_CHARGE)) {
         return 60;
      } else if (itemStack.isOf(Items.GOLDEN_APPLE)) {
         return 30;
      } else if (itemStack.isOf(Items.DRIED_KELP)) {
         return 25;
      } else {
         return itemStack.isOf(Items.NETHERITE_SCRAP) ? 15 : 0;
      }
   }

   static final class NestedType398 {
      final ItemStack itemStackField1;
      long longValueField2;
      long longValueField3;
      int hand;

      NestedType398(ItemStack itemStack, int i) {
         this.itemStackField1 = itemStack.copy();
         this.itemStackField1.setDamage(0);
         this.itemStackField1.set(DataComponentTypes.USE_COOLDOWN, new UseCooldownComponent(0.0F, Optional.of(Identifier.of("neneclient", "widget"))));
         this.hand = i;
      }

      int computeValue_3334(long l) {
         int i = WidgetComponent240.computeValue_3332(this.itemStackField1);
         return i != 0 && this.longValueField2 != 0L ? Math.max((int)Math.ceil((i * 1000L - (l - this.longValueField2)) / 1000.0), 0) : 0;
      }
   }

   static final class NestedType399 {
      final UUID uUIDField1;
      final LinkedHashMap<Item, WidgetComponent240.NestedType398> linkedHashMapField2 = new LinkedHashMap<>();
      final ItemStack[] itemStackArrayField3 = new ItemStack[6];
      String stringField4 = "";
      Identifier identifierField5;
      float floatValueField6;
      long longValueField7;

      private NestedType399(UUID uUID) {
         this.uUIDField1 = uUID;
         Arrays.fill(this.itemStackArrayField3, ItemStack.EMPTY);
      }

      void performAction_3337(PlayerEntity playerEntity, long l) {
         StreamerMode streamerMode = NeneClient.getInstance().getProcessors().getModules().getStreamerModeField7_0439();
         this.stringField4 = streamerMode.isEnabled() && streamerMode.getBooleanSettingField2_2001().getCurrentValue()
            ? streamerMode.computeValue_2004(playerEntity.getName().getString())
            : playerEntity.getName().getString();
         Identifier identifier;
         if (playerEntity instanceof AbstractClientPlayerEntity abstractClientPlayerEntity) {
            identifier = abstractClientPlayerEntity.getSkin().body().comp_3626();
         } else {
            identifier = DefaultSkinHelper.getSkinTextures(this.uUIDField1).body().comp_3626();
         }

         this.identifierField5 = identifier;
         this.floatValueField6 = UtilComponent252.NestedType400.computeValue_3409(playerEntity);
         this.longValueField7 = l;

         for (int i = 0; i < 4; i++) {
            this.itemStackArrayField3[i] = playerEntity.getEquippedStack(EquipmentSlot.values()[5 - i]);
         }

         ItemStack itemStack1 = playerEntity.getMainHandStack();
         ItemStack itemStack = playerEntity.getOffHandStack();

         for (WidgetComponent240.NestedType398 nestedType398 : this.linkedHashMapField2.values()) {
            if (nestedType398.longValueField3 == 0L
               && nestedType398.itemStackField1.getCount() == 1
               && this.itemStackArrayField3[nestedType398.hand].isOf(nestedType398.itemStackField1.getItem())
               && (nestedType398.hand == 4 ? itemStack1 : itemStack).isEmpty()) {
               nestedType398.longValueField3 = l;
            }
         }

         this.itemStackArrayField3[4] = itemStack1;
         this.itemStackArrayField3[5] = itemStack;

         for (int j = 4; j < 6; j++) {
            if (WidgetComponent240.computeValue_3324(this.itemStackArrayField3[j]) > 0) {
               this.performAction_3338(playerEntity, this.itemStackArrayField3[j], j, l);
            }
         }

         this.linkedHashMapField2.values().removeIf(nestedType3981 -> nestedType3981.longValueField3 != 0L && l - nestedType3981.longValueField3 > 10000L);
      }

      private void performAction_3338(PlayerEntity playerEntity, ItemStack itemStack, int i, long l) {
         WidgetComponent240.NestedType398 nestedType398 = this.linkedHashMapField2.remove(itemStack.getItem());
         if (nestedType398 != null) {
            if (itemStack.getCount() < nestedType398.itemStackField1.getCount()
               && WidgetComponent240.computeValue_3332(itemStack) > 0
               && MinecraftAccess.minecraft
                  .world
                  .getEntitiesByClass(
                     ItemEntity.class, playerEntity.getBoundingBox().expand(2.0), itemEntity -> itemEntity.getStack().isOf(itemStack.getItem())
                  )
                  .isEmpty()) {
               nestedType398.longValueField2 = l;
            }

            nestedType398.itemStackField1.setCount(itemStack.getCount());
            nestedType398.hand = i;
            nestedType398.longValueField3 = 0L;
            this.linkedHashMapField2.put(itemStack.getItem(), nestedType398);
         } else {
            if (this.linkedHashMapField2.size() >= 9) {
               Optional optional = this.linkedHashMapField2
                  .entrySet()
                  .stream()
                  .filter(entry -> WidgetComponent240.computeValue_3324(entry.getValue().itemStackField1) < WidgetComponent240.computeValue_3324(itemStack))
                  .findFirst()
                  .map(entry -> entry.getKey());
               LinkedHashMap linkedHashMap = this.linkedHashMapField2;
               optional.ifPresent(linkedHashMap::remove);
               if (this.linkedHashMapField2.size() >= 9) {
                  return;
               }
            }

            this.linkedHashMapField2.put(itemStack.getItem(), new WidgetComponent240.NestedType398(itemStack, i));
         }
      }
   }
}
