package aethereal.autobuy;

import aethereal.api.ApiAnnotation001;
import aethereal.config.ConfigComponent020;
import aethereal.config.ConfigComponent022;
import aethereal.config.ConfigComponent025;
import aethereal.config.ConfigComponent026;
import aethereal.config.ConfigContract017;
import aethereal.config.ListProcessor;
import aethereal.core.ObfuscatorRuntime;
import aethereal.lib.json.JsonComponent135;
import aethereal.lib.json.JsonComponent136;
import aethereal.module.misc.Collector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;

public class AutobuyComponent009 extends ListProcessor<Collector.NestedType343> {
   @ApiAnnotation001
   @Override
   protected List<Collector.NestedType343> computeValue_0041(String string) {
      if (this.listField3.isEmpty()) {
         this.listField3.addAll(this.computeValue_0070());
      }

      JsonComponent135 jsonComponent135 = new JsonComponent135(string);

      for (int i = 0; i < jsonComponent135.getDelegate_1250(); i++) {
         JsonComponent136 jsonComponent136 = jsonComponent135.computeValue_1252(i);
         this.listField3
            .stream()
            .filter(nestedType343 -> nestedType343.getName_1924().equals(jsonComponent136.computeValue_1260("name")))
            .findFirst()
            .ifPresent(nestedType343 -> this.performAction_0071(jsonComponent136, nestedType343));
      }

      return new ArrayList<>(this.listField3);
   }

   @ApiAnnotation001
   @Override
   protected String computeValue_0042(List<Collector.NestedType343> list) {
      JsonComponent135 jsonComponent135 = new JsonComponent135();

      for (Collector.NestedType343 nestedType343 : list) {
         JsonComponent136 jsonComponent136 = new JsonComponent136();
         if (!(nestedType343 instanceof Collector.NestedType343 nestedType3431)) {
            throw new ClassCastException();
         }

         jsonComponent136.computeValue_1272("name", nestedType3431.getName_1924());
         jsonComponent136.computeValue_1273("active", nestedType3431.isFlagField5_1925());
         jsonComponent136.computeValue_1270("count", nestedType3431.getCount_1909());
         nestedType3431.getConfigComponent022Field2_1911();
         if (nestedType3431.getConfigComponent022Field2_1911() != null) {
            this.performAction_0073(jsonComponent136, "enchantments", nestedType3431.getConfigComponent022Field2_1911().getListField1_0382());
         }

         nestedType3431.getConfigComponent020Field1_1902();
         if (nestedType3431.getConfigComponent020Field1_1902() != null) {
            this.performAction_0073(jsonComponent136, "descriptions", nestedType3431.getConfigComponent020Field1_1902().getListField1_0370());
         }

         jsonComponent135.computeValue_1253(jsonComponent136);
      }

      return jsonComponent135.computeValue_1254(2);
   }

   @ApiAnnotation001
   public void performAction_0071(JsonComponent136 jsonComponent136, Collector.NestedType343 nestedType343) {
      if (jsonComponent136.checkCondition_1258("active")) {
         nestedType343.computeValue_1922(jsonComponent136.checkCondition_1261("active"));
      }

      if (jsonComponent136.checkCondition_1258("count")) {
         nestedType343.setCount_1916(jsonComponent136.computeValue_1266("count"));
      }

      nestedType343.getConfigComponent022Field2_1911();
      if (nestedType343.getConfigComponent022Field2_1911() != null) {
         this.performAction_0069(jsonComponent136, "enchantments", nestedType343.getConfigComponent022Field2_1911().getListField1_0382());
      }

      nestedType343.getConfigComponent020Field1_1902();
      if (nestedType343.getConfigComponent020Field1_1902() != null) {
         this.performAction_0069(jsonComponent136, "descriptions", nestedType343.getConfigComponent020Field1_1902().getListField1_0370());
      }
   }

   @ApiAnnotation001
   private void performAction_0073(JsonComponent136 jsonComponent136, String string, List<? extends ConfigContract017> list) {
      if (!list.isEmpty()) {
         JsonComponent136 jsonComponent1361 = new JsonComponent136();

         for (ConfigContract017 configContract017 : list) {
            if (!(configContract017 instanceof ConfigContract017 configContract0171)) {
               throw new ClassCastException();
            }

            if (!configContract0171.checkCondition_0344()) {
               JsonComponent136 jsonComponent1362 = new JsonComponent136();
               jsonComponent1362.computeValue_1270("level", configContract0171.computeValue_0354());
               jsonComponent1362.computeValue_1272("type", configContract0171.computeValue_0350().name());
               jsonComponent1361.computeValue_1272(configContract0171.computeValue_0346(), jsonComponent1362);
            }
         }

         jsonComponent136.computeValue_1272(string, jsonComponent1361);
      }
   }

   @ApiAnnotation001
   private void performAction_0069(JsonComponent136 jsonComponent136, String string, List<? extends ConfigContract017> list) {
      if (jsonComponent136.checkCondition_1258(string)) {
         JsonComponent136 jsonComponent1361 = jsonComponent136.computeValue_1268(string);
         if (jsonComponent1361 == null) {
            return;
         }

         for (ConfigContract017 configContract017 : list) {
            if (!(configContract017 instanceof ConfigContract017 configContract0171)) {
               throw new ClassCastException();
            }

            if (!configContract0171.checkCondition_0344() && jsonComponent1361.checkCondition_1258(configContract0171.computeValue_0346())) {
               JsonComponent136 jsonComponent1362 = jsonComponent1361.computeValue_1268(configContract0171.computeValue_0346());
               if (jsonComponent1362 != null) {
                  if (jsonComponent1362.checkCondition_1258("level")) {
                     configContract0171.performAction_0345(jsonComponent1362.computeValue_1266("level"));
                  }

                  if (jsonComponent1362.checkCondition_1258("type")) {
                     if (ItemType.valueOf(jsonComponent1362.computeValue_1260("type")) == ItemType.OFF) {
                        configContract0171.performAction_0353(ItemType.OFF);
                     } else {
                        configContract0171.performAction_0353(ItemType.itemTypeField1);
                     }
                  }

                  if (jsonComponent1362.checkCondition_1258("enabled")) {
                     if (jsonComponent1362.checkCondition_1261("enabled")) {
                        configContract0171.performAction_0353(ItemType.itemTypeField1);
                     } else {
                        configContract0171.performAction_0353(ItemType.OFF);
                     }
                  }
               }
            }
         }
      }
   }

   public AutobuyComponent009() {
      this.listField3.addAll(this.computeValue_0070());
   }

   @Override
   protected String computeValue_0040() {
      return "collect.json";
   }

   private List<Collector.NestedType343> computeValue_0070() {
      return Arrays.asList(
         Collector.NestedType343.computeValue_1921(Items.NETHERITE_SWORD, 1, "Netherite Sword")
            .computeValue_1922(true)
            .computeValue_1904(true)
            .computeValue_1918(
               new ConfigComponent022().computeValue_0383().computeValue_0387(Enchantments.SHARPNESS, 7).computeValue_0387(Enchantments.FIRE_ASPECT, 2)
            )
            .computeValue_1917(
               new ConfigComponent020()
                  .computeValue_0371()
                  .computeValue_0374("Poison", 3)
                  .computeValue_0374("Vampirism", 2)
                  .computeValue_0374("Oxidation", 2)
                  .computeValue_0375("Experienced", 3, false)
                  .computeValue_0374("Detection", 3)
            ),
         Collector.NestedType343.computeValue_1921(Items.MACE, 1, "Mace")
            .computeValue_1922(true)
            .computeValue_1904(true)
            .computeValue_1918(
               new ConfigComponent022()
                  .computeValue_0383()
                  .computeValue_0387(Enchantments.SHARPNESS, 7)
                  .computeValue_0387(Enchantments.BREACH, 3)
                  .computeValue_0387(Enchantments.DENSITY, 5)
            )
            .computeValue_1917(new ConfigComponent020().computeValue_0371()),
         Collector.NestedType343.computeValue_1921(Items.TRIDENT, 1, "Trident")
            .computeValue_1922(true)
            .computeValue_1904(true)
            .computeValue_1917(
               new ConfigComponent020()
                  .computeValue_0371()
                  .computeValue_0374("Stupor", 3)
                  .computeValue_0374("Attraction", 2)
                  .computeValue_0374("Scout", 3)
                  .computeValue_0373("Return")
                  .computeValue_0373("Demoman")
            ),
         Collector.NestedType343.computeValue_1921(Items.NETHERITE_HELMET, 1, "Netherite Helm")
            .computeValue_1922(true)
            .computeValue_1904(true)
            .computeValue_1918(
               new ConfigComponent022()
                  .computeValue_0383()
                  .computeValue_0387(Enchantments.PROTECTION, 5)
                  .computeValue_0387(Enchantments.UNBREAKING, 5)
                  .computeValue_0387(Enchantments.RESPIRATION, 3)
                  .computeValue_0386(Enchantments.MENDING)
            ),
         Collector.NestedType343.computeValue_1921(Items.NETHERITE_CHESTPLATE, 1, "Netherite Chestpiece")
            .computeValue_1922(true)
            .computeValue_1904(true)
            .computeValue_1918(
               new ConfigComponent022()
                  .computeValue_0383()
                  .computeValue_0387(Enchantments.PROTECTION, 5)
                  .computeValue_0387(Enchantments.UNBREAKING, 5)
                  .computeValue_0386(Enchantments.MENDING)
            ),
         Collector.NestedType343.computeValue_1921(Items.NETHERITE_LEGGINGS, 1, "Netherite Leggings")
            .computeValue_1922(true)
            .computeValue_1904(true)
            .computeValue_1918(
               new ConfigComponent022()
                  .computeValue_0383()
                  .computeValue_0387(Enchantments.PROTECTION, 5)
                  .computeValue_0387(Enchantments.UNBREAKING, 5)
                  .computeValue_0386(Enchantments.MENDING)
            ),
         Collector.NestedType343.computeValue_1921(Items.NETHERITE_BOOTS, 1, "Netherite Boots")
            .computeValue_1922(true)
            .computeValue_1904(true)
            .computeValue_1918(
               new ConfigComponent022()
                  .computeValue_0383()
                  .computeValue_0387(Enchantments.PROTECTION, 5)
                  .computeValue_0387(Enchantments.UNBREAKING, 5)
                  .computeValue_0387(Enchantments.DEPTH_STRIDER, 3)
                  .computeValue_0386(Enchantments.MENDING)
            ),
         Collector.NestedType343.computeValue_1921(Items.NETHERITE_SCRAP, 8, "Trap")
            .computeValue_1922(true)
            .computeValue_1917(new ConfigComponent020().computeValue_0371().computeValue_0373("Cast: Unbreakable Cage")),
         Collector.NestedType343.computeValue_1921(Items.SUGAR, 12, "Sheer dust")
            .computeValue_1922(true)
            .computeValue_1917(new ConfigComponent020().computeValue_0371().computeValue_0373("Cast: Light Flash")),
         Collector.NestedType343.computeValue_1921(Items.PHANTOM_MEMBRANE, 4, "God's aura")
            .computeValue_1922(true)
            .computeValue_1917(new ConfigComponent020().computeValue_0371().computeValue_0373("Cast: Divine Aura")),
         Collector.NestedType343.computeValue_1921(Items.ENDER_EYE, 16, "Disorientate")
            .computeValue_1922(true)
            .computeValue_1917(new ConfigComponent020().computeValue_0371().computeValue_0373("Cast: Sound Wave")),
         Collector.NestedType343.computeValue_1921(Items.WIND_CHARGE, 32, "Wind Charge").computeValue_1922(true),
         Collector.NestedType343.computeValue_1921(Items.DRIED_KELP, 16, "Plast")
            .computeValue_1922(true)
            .computeValue_1917(new ConfigComponent020().computeValue_0371().computeValue_0373("Cast: Unbreakable Wall")),
         Collector.NestedType343.computeValue_1921(Items.SNOWBALL, 4, "Snowball Freeze")
            .computeValue_1922(true)
            .computeValue_1917(new ConfigComponent020().computeValue_0371().computeValue_0373("Cast: Ice Sphere")),
         Collector.NestedType343.computeValue_1921(Items.ENDER_PEARL, 16, "Perk").computeValue_1922(true),
         Collector.NestedType343.computeValue_1921(Items.TOTEM_OF_UNDYING, 1, "Totem of immortality").computeValue_1922(true),
         Collector.NestedType343.computeValue_1921(Items.CROSSBOW, 1, "Crossbow")
            .computeValue_1922(true)
            .computeValue_1918(
               new ConfigComponent022()
                  .computeValue_0383()
                  .computeValue_0387(Enchantments.QUICK_CHARGE, 3)
                  .computeValue_0386(Enchantments.MENDING)
                  .computeValue_0386(Enchantments.MULTISHOT)
            ),
         Collector.NestedType343.computeValue_1921(Items.GOLDEN_APPLE, 16, "Golden Apple").computeValue_1922(true),
         Collector.NestedType343.computeValue_1921(Items.ENCHANTED_GOLDEN_APPLE, 8, "Enchanted golden yab").computeValue_1922(true),
         Collector.NestedType343.computeValue_1921(Items.GOLDEN_CARROT, 64, "Golden Carrot").computeValue_1922(true),
         Collector.NestedType343.computeValue_1921(Items.CHORUS_FRUIT, 64, "Horus").computeValue_1922(true),
         Collector.NestedType343.computeValue_1921(Items.ELYTRA, 1, "Elites").computeValue_1922(true),
         Collector.NestedType343.computeValue_1921(Items.FIREWORK_ROCKET, 64, "Fireworks").computeValue_1922(true),
         Collector.NestedType343.computeValue_1921(Items.SPLASH_POTION, 1, "Clapperboard")
            .computeValue_1922(true)
            .computeValue_1915(16711680)
            .computeValue_1920(
               new ConfigComponent026()
                  .computeValue_0520(new ConfigComponent025(StatusEffects.SLOWNESS, 10, 200))
                  .computeValue_0520(new ConfigComponent025(StatusEffects.SPEED, 5, 300))
                  .computeValue_0520(new ConfigComponent025(StatusEffects.BLINDNESS, 10, 100))
                  .computeValue_0520(new ConfigComponent025(StatusEffects.GLOWING, 1, 3600))
            ),
         Collector.NestedType343.computeValue_1921(Items.SPLASH_POTION, 1, "Holy water")
            .computeValue_1922(true)
            .computeValue_1915(16777215)
            .computeValue_1920(
               new ConfigComponent026()
                  .computeValue_0520(new ConfigComponent025(StatusEffects.REGENERATION, 2, 900))
                  .computeValue_0520(new ConfigComponent025(StatusEffects.INVISIBILITY, 2, 12000))
                  .computeValue_0520(new ConfigComponent025(StatusEffects.INSTANT_HEALTH, 2, 0))
            ),
         Collector.NestedType343.computeValue_1921(Items.SPLASH_POTION, 1, "Potion of Wrath")
            .computeValue_1922(true)
            .computeValue_1904(true)
            .computeValue_1915(10040115)
            .computeValue_1920(
               new ConfigComponent026()
                  .computeValue_0520(new ConfigComponent025(StatusEffects.STRENGTH, 5, 600))
                  .computeValue_0520(new ConfigComponent025(StatusEffects.SLOWNESS, 4, 600))
            ),
         Collector.NestedType343.computeValue_1921(Items.SPLASH_POTION, 1, "Potion of Palladin")
            .computeValue_1922(true)
            .computeValue_1915(65535)
            .computeValue_1920(
               new ConfigComponent026()
                  .computeValue_0520(new ConfigComponent025(StatusEffects.RESISTANCE, 1, 12000))
                  .computeValue_0520(new ConfigComponent025(StatusEffects.FIRE_RESISTANCE, 1, 12000))
                  .computeValue_0520(new ConfigComponent025(StatusEffects.HEALTH_BOOST, 3, 1200))
                  .computeValue_0520(new ConfigComponent025(StatusEffects.INVISIBILITY, 1, 18000))
            ),
         Collector.NestedType343.computeValue_1921(Items.SPLASH_POTION, 1, "Assassin's Potion")
            .computeValue_1922(true)
            .computeValue_1915(3355443)
            .computeValue_1920(
               new ConfigComponent026()
                  .computeValue_0520(new ConfigComponent025(StatusEffects.STRENGTH, 4, 1200))
                  .computeValue_0520(new ConfigComponent025(StatusEffects.SPEED, 3, 6000))
                  .computeValue_0520(new ConfigComponent025(StatusEffects.HASTE, 1, 1200))
                  .computeValue_0520(new ConfigComponent025(StatusEffects.INSTANT_DAMAGE, 2, 0))
            ),
         Collector.NestedType343.computeValue_1921(Items.SPLASH_POTION, 1, "Potion of Radiation")
            .computeValue_1922(true)
            .computeValue_1915(3329330)
            .computeValue_1920(
               new ConfigComponent026()
                  .computeValue_0520(new ConfigComponent025(StatusEffects.POISON, 2, 300))
                  .computeValue_0520(new ConfigComponent025(StatusEffects.WITHER, 2, 300))
                  .computeValue_0520(new ConfigComponent025(StatusEffects.SLOWNESS, 3, 300))
                  .computeValue_0520(new ConfigComponent025(StatusEffects.HUNGER, 5, 300))
                  .computeValue_0520(new ConfigComponent025(StatusEffects.GLOWING, 1, 300))
            ),
         Collector.NestedType343.computeValue_1921(Items.SPLASH_POTION, 1, "Hypnotic")
            .computeValue_1922(true)
            .computeValue_1904(false)
            .computeValue_1915(4737096)
            .computeValue_1920(
               new ConfigComponent026()
                  .computeValue_0520(new ConfigComponent025(StatusEffects.WEAKNESS, 2, 1800))
                  .computeValue_0520(new ConfigComponent025(StatusEffects.MINING_FATIGUE, 2, 200))
                  .computeValue_0520(new ConfigComponent025(StatusEffects.WITHER, 3, 1800))
                  .computeValue_0520(new ConfigComponent025(StatusEffects.BLINDNESS, 1, 200))
            ),
         Collector.NestedType343.computeValue_1921(Items.POTION, 1, "Potion")
            .computeValue_1922(true)
            .computeValue_1920(
               new ConfigComponent026()
                  .computeValue_0520(new ConfigComponent025(StatusEffects.STRENGTH, 3, 3600))
                  .computeValue_0520(new ConfigComponent025(StatusEffects.SPEED, 3, 3600))
            ),
         Collector.NestedType343.computeValue_1921(Items.POTION, 1, "Potion of Regeneration")
            .computeValue_1922(true)
            .computeValue_1920(
               new ConfigComponent026()
                  .computeValue_0520(new ConfigComponent025(StatusEffects.INSTANT_HEALTH, 2, 0))
                  .computeValue_0520(new ConfigComponent025(StatusEffects.REGENERATION, 1, 900))
            ),
         Collector.NestedType343.computeValue_1921(Items.TIPPED_ARROW, 32, "Blood Arrow")
            .computeValue_1922(true)
            .computeValue_1920(
               new ConfigComponent026()
                  .computeValue_0520(new ConfigComponent025(StatusEffects.WEAKNESS, 3, 60))
                  .computeValue_0520(new ConfigComponent025(StatusEffects.BLINDNESS, 1, 40))
                  .computeValue_0520(new ConfigComponent025(StatusEffects.MINING_FATIGUE, 1, 40))
                  .computeValue_0520(new ConfigComponent025(StatusEffects.NAUSEA, 1, 100))
            ),
         Collector.NestedType343.computeValue_1921(Items.TIPPED_ARROW, 64, "Icing Arrow")
            .computeValue_1922(false)
            .computeValue_1920(
               new ConfigComponent026()
                  .computeValue_0520(new ConfigComponent025(StatusEffects.SLOWNESS, 10, 100))
                  .computeValue_0520(new ConfigComponent025(StatusEffects.MINING_FATIGUE, 3, 40))
            ),
         Collector.NestedType343.computeValue_1921(Items.TIPPED_ARROW, 64, "Tormenting Arrow")
            .computeValue_1922(false)
            .computeValue_1920(
               new ConfigComponent026()
                  .computeValue_0520(new ConfigComponent025(StatusEffects.SLOWNESS, 3, 100))
                  .computeValue_0520(new ConfigComponent025(StatusEffects.WITHER, 3, 100))
                  .computeValue_0520(new ConfigComponent025(StatusEffects.POISON, 3, 100))
            )
      );
   }

   static {
      ObfuscatorRuntime.registerClass(AutobuyComponent009.class, 21);
   }
}
