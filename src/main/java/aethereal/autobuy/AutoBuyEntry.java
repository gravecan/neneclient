package aethereal.autobuy;

import aethereal.config.ConfigComponent015;
import aethereal.config.ConfigComponent016;
import aethereal.config.ConfigComponent020;
import aethereal.config.ConfigComponent022;
import aethereal.config.ConfigComponent024;
import aethereal.config.ConfigComponent025;
import aethereal.config.ConfigComponent026;
import aethereal.render.Animation;
import com.google.common.collect.HashMultimap;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public enum AutoBuyEntry {
   POPPER_POTION(
      "[★] Firecracker",
      Items.SPLASH_POTION,
      new ConfigComponent026()
         .computeValue_0520(new ConfigComponent025(StatusEffects.SLOWNESS, 10, 200))
         .computeValue_0520(new ConfigComponent025(StatusEffects.SPEED, 5, 300))
         .computeValue_0520(new ConfigComponent025(StatusEffects.BLINDNESS, 10, 100))
         .computeValue_0520(new ConfigComponent025(StatusEffects.GLOWING, 1, 3600)),
      16711680
   ),
   HOLY_WATER(
      "[★] Holy water",
      Items.SPLASH_POTION,
      new ConfigComponent026()
         .computeValue_0520(new ConfigComponent025(StatusEffects.REGENERATION, 2, 900))
         .computeValue_0520(new ConfigComponent025(StatusEffects.INVISIBILITY, 2, 12000))
         .computeValue_0520(new ConfigComponent025(StatusEffects.INSTANT_HEALTH, 2, 0)),
      16777215
   ),
   RAGE_POTION(
      "[★] Potion of Wrath",
      Items.SPLASH_POTION,
      new ConfigComponent026()
         .computeValue_0520(new ConfigComponent025(StatusEffects.STRENGTH, 5, 600))
         .computeValue_0520(new ConfigComponent025(StatusEffects.SLOWNESS, 4, 600)),
      10040115
   ),
   PALLADIN_POTION(
      "[★] Palladin's Potion",
      Items.SPLASH_POTION,
      new ConfigComponent026()
         .computeValue_0520(new ConfigComponent025(StatusEffects.RESISTANCE, 1, 12000))
         .computeValue_0520(new ConfigComponent025(StatusEffects.FIRE_RESISTANCE, 1, 12000))
         .computeValue_0520(new ConfigComponent025(StatusEffects.HEALTH_BOOST, 3, 1200))
         .computeValue_0520(new ConfigComponent025(StatusEffects.INVISIBILITY, 1, 18000)),
      65535
   ),
   ASSASSIN_POTION(
      "[★] Assassin's Potion",
      Items.SPLASH_POTION,
      new ConfigComponent026()
         .computeValue_0520(new ConfigComponent025(StatusEffects.STRENGTH, 4, 1200))
         .computeValue_0520(new ConfigComponent025(StatusEffects.SPEED, 3, 6000))
         .computeValue_0520(new ConfigComponent025(StatusEffects.HASTE, 1, 1200))
         .computeValue_0520(new ConfigComponent025(StatusEffects.INSTANT_DAMAGE, 2, 0)),
      3355443
   ),
   RADIATION_POTION(
      "[★] Radiation Potion",
      Items.SPLASH_POTION,
      new ConfigComponent026()
         .computeValue_0520(new ConfigComponent025(StatusEffects.POISON, 2, 300))
         .computeValue_0520(new ConfigComponent025(StatusEffects.WITHER, 2, 300))
         .computeValue_0520(new ConfigComponent025(StatusEffects.SLOWNESS, 3, 300))
         .computeValue_0520(new ConfigComponent025(StatusEffects.HUNGER, 5, 300))
         .computeValue_0520(new ConfigComponent025(StatusEffects.GLOWING, 1, 300)),
      3329330
   ),
   SLEEPING_PILL(
      "[★] Sleeping pills",
      Items.SPLASH_POTION,
      new ConfigComponent026()
         .computeValue_0520(new ConfigComponent025(StatusEffects.WEAKNESS, 2, 1800))
         .computeValue_0520(new ConfigComponent025(StatusEffects.MINING_FATIGUE, 2, 200))
         .computeValue_0520(new ConfigComponent025(StatusEffects.WITHER, 3, 1800))
         .computeValue_0520(new ConfigComponent025(StatusEffects.BLINDNESS, 1, 200)),
      4737096
   ),
   TALISMAN_CRUSHER(
      "[★] Crusher Talisman",
      Items.TOTEM_OF_UNDYING,
      new ConfigComponent016()
         .computeValue_0341(new ConfigComponent015(EntityAttributes.MAX_HEALTH, 4.0, Operation.ADD_VALUE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.ATTACK_DAMAGE, 3.0, Operation.ADD_VALUE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.ARMOR_TOUGHNESS, 2.0, Operation.ADD_VALUE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.ARMOR, 2.0, Operation.ADD_VALUE))
   ),
   TALISMAN_DISCORD(
      "[★] Talisman of Discord",
      Items.TOTEM_OF_UNDYING,
      new ConfigComponent016()
         .computeValue_0341(new ConfigComponent015(EntityAttributes.ATTACK_DAMAGE, 4.0, Operation.ADD_VALUE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.MAX_HEALTH, 2.0, Operation.ADD_VALUE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.MOVEMENT_SPEED, 0.10000001817743467, Operation.ADD_MULTIPLIED_BASE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.ATTACK_SPEED, 0.10000001817743467, Operation.ADD_MULTIPLIED_BASE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.ARMOR, -3.0, Operation.ADD_VALUE))
   ),
   TALISMAN_TYRANT(
      "[★] Tyrant Talisman",
      Items.TOTEM_OF_UNDYING,
      new ConfigComponent016()
         .computeValue_0341(new ConfigComponent015(EntityAttributes.ATTACK_DAMAGE, 2.0, Operation.ADD_VALUE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.ARMOR, 2.0, Operation.ADD_VALUE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.MAX_HEALTH, -4.0, Operation.ADD_VALUE))
   ),
   TALISMAN_RAGE(
      "[★] Talisman of Fury",
      Items.TOTEM_OF_UNDYING,
      new ConfigComponent016()
         .computeValue_0341(new ConfigComponent015(EntityAttributes.ATTACK_DAMAGE, 5.0, Operation.ADD_VALUE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.MAX_HEALTH, -4.0, Operation.ADD_VALUE))
   ),
   TALISMAN_WHIRLWIND(
      "[★] Vortex Talisman",
      Items.TOTEM_OF_UNDYING,
      new ConfigComponent016()
         .computeValue_0341(new ConfigComponent015(EntityAttributes.MAX_HEALTH, 2.0, Operation.ADD_VALUE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.MOVEMENT_SPEED, 0.1500000347540806, Operation.ADD_MULTIPLIED_BASE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.ATTACK_SPEED, 0.1500000347540806, Operation.ADD_MULTIPLIED_BASE))
   ),
   TALISMAN_GLOOM(
      "[★] Talisman of Darkness",
      Items.TOTEM_OF_UNDYING,
      new ConfigComponent016()
         .computeValue_0341(new ConfigComponent015(EntityAttributes.ARMOR, 1.5, Operation.ADD_VALUE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.MAX_HEALTH, 1.5, Operation.ADD_VALUE))
   ),
   TALISMAN_DEMON(
      "[★] Demon Talisman",
      Items.TOTEM_OF_UNDYING,
      new ConfigComponent016()
         .computeValue_0341(new ConfigComponent015(EntityAttributes.ATTACK_DAMAGE, 2.5, Operation.ADD_VALUE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.ATTACK_SPEED, 0.10000001817743467, Operation.ADD_MULTIPLIED_BASE))
   ),
   TALISMAN_PUNISHER(
      "[★] Punisher Talisman xxx",
      Items.TOTEM_OF_UNDYING,
      new ConfigComponent016()
         .computeValue_0341(new ConfigComponent015(EntityAttributes.ATTACK_DAMAGE, 7.0, Operation.ADD_VALUE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.MAX_HEALTH, -4.0, Operation.ADD_VALUE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.MOVEMENT_SPEED, 0.10000001817743467, Operation.ADD_MULTIPLIED_BASE))
   ),
   SPHERE_CHAOS(
      "[★] Sphere of Chaos",
      Items.PLAYER_HEAD,
      new ConfigComponent016()
         .computeValue_0341(new ConfigComponent015(EntityAttributes.MAX_HEALTH, -4.0, Operation.ADD_VALUE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.ARMOR, 1.5, Operation.ADD_VALUE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.ATTACK_DAMAGE, 2.5, Operation.ADD_VALUE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.MOVEMENT_SPEED, 0.07000004566537438, Operation.ADD_MULTIPLIED_BASE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.ATTACK_SPEED, 0.13000000024761527, Operation.ADD_MULTIPLIED_BASE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.GRAVITY, 0.09000001839414021, Operation.ADD_MULTIPLIED_BASE)),
      "ewogICJ0aW1lc3RhbXAiIDogMTc1MDI3ODY0MTkwMCwKICAicHJvZmlsZUlkIiA6ICIxNzRjZmRiNGEzY2I0M2I1YmZjZGU0MjRjM2JiMmM2ZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJtYXJhZWwxOCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9lN2E3YWU3Y2RjZjYxNmU4YjdhNDIyMWE2MjFiMjQzNTc1M2M2MGVkNmEyNThlYTA2MGRhZTMwMDJmZmU5ZTI4IiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0="
   ),
   SPHERE_SATYR(
      "[★] Sphere of Satyr",
      Items.PLAYER_HEAD,
      new ConfigComponent016()
         .computeValue_0341(new ConfigComponent015(EntityAttributes.ATTACK_DAMAGE, 2.0, Operation.ADD_VALUE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.JUMP_STRENGTH, -0.10000000768432185, Operation.ADD_MULTIPLIED_BASE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.ATTACK_SPEED, 0.1500000347540806, Operation.ADD_MULTIPLIED_BASE)),
      "ewogICJ0aW1lc3RhbXAiIDogMTc1MDI3ODYwODUyOCwKICAicHJvZmlsZUlkIiA6ICJkMTQ4NjFiM2UwZmM0Njk5OTFlMTcyNTllMzdiZjZhZCIsCiAgInByb2ZpbGVOYW1lIiA6ICJyYXhpdG9jbCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS83NzFhOWE0OThiNGZhNWVjNDkzNjJmOWJjODhlZGE0ZjUyYjA0ZGU0OWQ3NWFhM2NhMzMyYTFmZWExYWEwZTU3IiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0="
   ),
   SPHERE_BEAST(
      "[★] Sphere of the Bestia",
      Items.PLAYER_HEAD,
      new ConfigComponent016()
         .computeValue_0341(new ConfigComponent015(EntityAttributes.ARMOR, 1.0, Operation.ADD_VALUE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.MAX_HEALTH, 4.0, Operation.ADD_VALUE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.MOVEMENT_SPEED, 0.10000001817743467, Operation.ADD_MULTIPLIED_BASE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.ATTACK_SPEED, 0.10000001817743467, Operation.ADD_MULTIPLIED_BASE)),
      "ewogICJ0aW1lc3RhbXAiIDogMTc1MDM0MzgzNDkzMCwKICAicHJvZmlsZUlkIiA6ICI1MzUzNWIxN2M0ZDY0NWQ0YWUwY2U2ZjM4Zjk0NTFjYSIsCiAgInByb2ZpbGVOYW1lIiA6ICJVYml2aXMiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTQxMWFjMTczODFiOWZjZTliYWIzYzcyYWZkYjdmMTk4NTcwZGFmNDczMmJkODExZDMxYzIyN2Q4MGZhMzliMSIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9"
   ),
   SPHERE_ARES(
      "[★] Ares Orb",
      Items.PLAYER_HEAD,
      new ConfigComponent016()
         .computeValue_0341(new ConfigComponent015(EntityAttributes.ATTACK_DAMAGE, 6.0, Operation.ADD_VALUE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.ARMOR, -2.0, Operation.ADD_VALUE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.MAX_HEALTH, -2.0, Operation.ADD_VALUE)),
      "ewogICJ0aW1lc3RhbXAiIDogMTc1MDM0Mzc3NDI1NSwKICAicHJvZmlsZUlkIiA6ICJhYWMxYjA2OWNkMjE0NWE2ODNlNzQxNzE4MDcxMGU4MiIsCiAgInByb2ZpbGVOYW1lIiA6ICJqdXNhbXUiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzE2YWRjNmJhZmNiNTdmZDcwN2RlZTdkZDZhNzM2ZmUxMjY3MTFkNTNhMWZkNmNlNzg5ZGE0MWIzYmUxM2YyYSIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9"
   ),
   SPHERE_HYDRA(
      "[★] Hydra Sphere",
      Items.PLAYER_HEAD,
      new ConfigComponent016()
         .computeValue_0341(new ConfigComponent015(EntityAttributes.MAX_HEALTH, 4.0, Operation.ADD_VALUE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.ARMOR, 2.0, Operation.ADD_VALUE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.SUBMERGED_MINING_SPEED, 0.5, Operation.ADD_MULTIPLIED_BASE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.OXYGEN_BONUS, 0.5, Operation.ADD_MULTIPLIED_BASE)),
      "ewogICJ0aW1lc3RhbXAiIDogMTc1MDI3ODUzMjE4MywKICAicHJvZmlsZUlkIiA6ICI1OGZmZWI5NTMxNGQ0ODcwYTQwYjVjYjQyZDRlYTU5OCIsCiAgInByb2ZpbGVOYW1lIiA6ICJTa2luREJuZXQiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2UzYzExOGQ2OTZkOTEwZTU0ZGUwMmNhNGQ4MDc1NDNmOWIxOGMwMDhjOTgzOGQyZmY2OTM3NzYyMmZiMWQzMiIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9"
   ),
   SPHERE_ICARUS(
      "[★] Icarus Sphere",
      Items.PLAYER_HEAD,
      new ConfigComponent016()
         .computeValue_0341(new ConfigComponent015(EntityAttributes.ATTACK_DAMAGE, 2.0, Operation.ADD_VALUE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.MAX_HEALTH, 2.0, Operation.ADD_VALUE)),
      "ewogICJ0aW1lc3RhbXAiIDogMTc1MDI3ODU4MjQ5MSwKICAicHJvZmlsZUlkIiA6ICJhZWNkODIxZTQyYzE0ZDJlOThmNTA1OTg1MWI5OWMzNyIsCiAgInByb2ZpbGVOYW1lIiA6ICJSb2RyaVgyMDc1IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2M2ODAzZTZkNTY2N2EyZDYxMDYyOGJjM2IzMmY4NjNjZGE0OTVjNDY1NjE2ZGU2NTVjYjMyOTkzM2I2MWFmNzciLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ=="
   ),
   SPHERE_ERIS(
      "[★] Eris Sphere",
      Items.PLAYER_HEAD,
      new ConfigComponent016()
         .computeValue_0341(new ConfigComponent015(EntityAttributes.LUCK, 1.0, Operation.ADD_VALUE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.MAX_HEALTH, 2.0, Operation.ADD_VALUE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.BLOCK_INTERACTION_RANGE, 1.0, Operation.ADD_VALUE)),
      "ewogICJ0aW1lc3RhbXAiIDogMTc1MDM0Mzg2MTE4NywKICAicHJvZmlsZUlkIiA6ICJlZGUyYzdhMGFjNjM0MTNiYjA5ZDNmMGJlZTllYzhlYyIsCiAgInByb2ZpbGVOYW1lIiA6ICJ0aGVEZXZKYWRlIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzZlNGUyZjEwNDdmM2VjNmU5ZTQ1OTE4NDczOWUzM2I3YzFmYzYzYWQ4MjAyYmRhYjlmMDI0NTA4YWRkMjNlNWIiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ=="
   ),
   SPHERE_TITAN(
      "[★] Titan Sphere",
      Items.PLAYER_HEAD,
      new ConfigComponent016()
         .computeValue_0341(new ConfigComponent015(EntityAttributes.ARMOR, 2.5, Operation.ADD_VALUE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.ARMOR_TOUGHNESS, 2.5, Operation.ADD_VALUE))
         .computeValue_0341(new ConfigComponent015(EntityAttributes.MOVEMENT_SPEED, -0.15000007945819008, Operation.ADD_MULTIPLIED_BASE)),
      "ewogICJ0aW1lc3RhbXAiIDogMTc1MDM1NDQ1NTE5MiwKICAicHJvZmlsZUlkIiA6ICJkOTcwYzEzZTM4YWI0NzlhOTY1OGM1ZDQ1MjZkMTM0YiIsCiAgInByb2ZpbGVOYW1lIiA6ICJDcmltcHlMYWNlODUxMjciLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODFlOTY5ODQ1OGI3ODQxYzk2YWU0ZjI0ZWM4NGFlMDE3MjQxMDA2NDFjNTY0ZTJhN2IxODVmNDA2ZThlZDIzIiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0="
   ),
   TRAP("[★] Trap", Items.NETHERITE_SCRAP, new ConfigComponent020().computeValue_0373("Cast: Unbreakable Cage")),
   CLEAR_DUST("[★] Obvious dust", Items.SUGAR, new ConfigComponent020().computeValue_0373("Cast: Light Flash")),
   SNOWBALL("[★] Snowball freezing", Items.SNOWBALL, new ConfigComponent020().computeValue_0373("Cast: Ice Sphere")),
   GOD_AURA("[★] God's aura", Items.PHANTOM_MEMBRANE, new ConfigComponent020().computeValue_0373("Cast: Divine Aura")),
   DISORIENTATION("[★] Disorientation", Items.ENDER_EYE, new ConfigComponent020().computeValue_0373("Cast: Sound Wave")),
   STRATUM("[★] Plast", Items.DRIED_KELP, new ConfigComponent020().computeValue_0373("Cast: Unbreakable Wall")),
   CRUSHER_SWORD(
      "xxx Sword of the Crusher xxx",
      Items.NETHERITE_SWORD,
      new ConfigComponent022().computeValue_0380(Enchantments.KNOCKBACK),
      new ConfigComponent020()
         .computeValue_0373("[★] Original item")
         .computeValue_0374("Experienced", 3)
         .computeValue_0374("Vampirism", 2)
         .computeValue_0374("Oxidation", 2)
         .computeValue_0374("Poison", 3)
         .computeValue_0374("Detection", 3)
   ),
   CRUSHER_MACE(
      "xxx Mace of the Crusher xxx",
      Items.MACE,
      new ConfigComponent020()
         .computeValue_0373("[★] Original item")
         .computeValue_0374("Experienced", 3)
         .computeValue_0374("Vampirism", 2)
         .computeValue_0374("Oxidation", 2)
         .computeValue_0374("Poison", 3)
         .computeValue_0374("Detection", 3)
   ),
   CRUSHER_TRIDENT(
      "xxx Crusher's Trident xxx",
      Items.TRIDENT,
      new ConfigComponent020()
         .computeValue_0373("[★] Original item")
         .computeValue_0374("Scout", 3)
         .computeValue_0374("Stupor", 3)
         .computeValue_0374("Attraction", 2)
         .computeValue_0373("Return")
         .computeValue_0373("Demoman")
   ),
   CRUSHER_PICKAXE(
      "xxx Crusher Pickaxe xxx",
      Items.NETHERITE_PICKAXE,
      new ConfigComponent020()
         .computeValue_0373("[★] Original item")
         .computeValue_0374("Bulldozer", 2)
         .computeValue_0374("Experienced", 3)
         .computeValue_0373("Magnet")
         .computeValue_0373("Auto-Smelting")
         .computeValue_0373("Web")
         .computeValue_0373("Pinger")
   ),
   CRUSHER_CROSSBOW("xxx Crusher Crossbow xxx", Items.CROSSBOW, new ConfigComponent020().computeValue_0373("[★] Original item")),
   CRUSHER_HELMET(
      "xxx Crusher Helmet xxx",
      Items.NETHERITE_HELMET,
      new ConfigComponent022()
         .computeValue_0387(Enchantments.PROTECTION, 5)
         .computeValue_0387(Enchantments.FIRE_PROTECTION, 5)
         .computeValue_0387(Enchantments.BLAST_PROTECTION, 5)
         .computeValue_0387(Enchantments.PROJECTILE_PROTECTION, 5)
         .computeValue_0387(Enchantments.UNBREAKING, 5)
         .computeValue_0387(Enchantments.MENDING, 1)
         .computeValue_0387(Enchantments.AQUA_AFFINITY, 1)
         .computeValue_0387(Enchantments.RESPIRATION, 3)
         .computeValue_0380(Enchantments.THORNS)
   ),
   CRUSHER_CHESTPLATE(
      "xxx Crusher Chest xxx",
      Items.NETHERITE_CHESTPLATE,
      new ConfigComponent022()
         .computeValue_0387(Enchantments.PROTECTION, 5)
         .computeValue_0387(Enchantments.FIRE_PROTECTION, 5)
         .computeValue_0387(Enchantments.BLAST_PROTECTION, 5)
         .computeValue_0387(Enchantments.PROJECTILE_PROTECTION, 5)
         .computeValue_0387(Enchantments.UNBREAKING, 5)
         .computeValue_0387(Enchantments.MENDING, 1)
         .computeValue_0380(Enchantments.THORNS)
   ),
   CRUSHER_LEGGINGS(
      "xxx Crusher Leggings xxx",
      Items.NETHERITE_LEGGINGS,
      new ConfigComponent022()
         .computeValue_0387(Enchantments.PROTECTION, 5)
         .computeValue_0387(Enchantments.FIRE_PROTECTION, 5)
         .computeValue_0387(Enchantments.BLAST_PROTECTION, 5)
         .computeValue_0387(Enchantments.PROJECTILE_PROTECTION, 5)
         .computeValue_0387(Enchantments.UNBREAKING, 5)
         .computeValue_0387(Enchantments.MENDING, 1)
         .computeValue_0380(Enchantments.THORNS)
   ),
   CRUSHER_BOOTS(
      "xxx Crusher Boots xxx",
      Items.NETHERITE_BOOTS,
      new ConfigComponent022()
         .computeValue_0387(Enchantments.PROTECTION, 5)
         .computeValue_0387(Enchantments.FIRE_PROTECTION, 5)
         .computeValue_0387(Enchantments.BLAST_PROTECTION, 5)
         .computeValue_0387(Enchantments.PROJECTILE_PROTECTION, 5)
         .computeValue_0387(Enchantments.UNBREAKING, 5)
         .computeValue_0387(Enchantments.MENDING, 1)
         .computeValue_0387(Enchantments.FEATHER_FALLING, 4)
         .computeValue_0387(Enchantments.SOUL_SPEED, 3)
         .computeValue_0387(Enchantments.DEPTH_STRIDER, 3)
         .computeValue_0380(Enchantments.THORNS)
   ),
   PICK_SPHERES("[★] Master key to the Spheres", Items.TRIPWIRE_HOOK, new ConfigComponent020().computeValue_0373("With Spheres")),
   GOD_TOUCH("[★] God's touch", Items.GOLDEN_PICKAXE, new ConfigComponent020().computeValue_0373("Can get spawner")),
   POWER_STRIKE("[★] Powerful blow", Items.GOLDEN_PICKAXE, new ConfigComponent020().computeValue_0373("Can destroy the thigh")),
   MIST_COMMON("Normal mist", Items.CAMPFIRE, new ConfigComponent020().computeValue_0373("Loot level: Normal")),
   MIST_RICH("Rich mist", Items.CAMPFIRE, new ConfigComponent020().computeValue_0373("Loot Level: Rich")),
   MIST_LEGENDARY("Legendary Mist", Items.SOUL_CAMPFIRE, new ConfigComponent020().computeValue_0373("Loot level: Legendary")),
   SKIN_INEVITABLE("[★] Inevitable skin", Items.PAPER, new ConfigComponent020().computeValue_0373("get the Inevitable skin")),
   SKIN_DRAGON("[★] Dragon skin", Items.PAPER, new ConfigComponent020().computeValue_0373("get a Dragon skin")),
   CHUNK_LOADER_1("[★] Chunk loader [1x1]", Items.STRUCTURE_BLOCK, new ConfigComponent020().computeValue_0373("loadable area (1x1)")),
   CHUNK_LOADER_3("[★] Chunk loader [3x3]", Items.STRUCTURE_BLOCK, new ConfigComponent020().computeValue_0373("loadable area (3x3)")),
   CHUNK_LOADER_5("[★] Chunk loader [5x5]", Items.STRUCTURE_BLOCK, new ConfigComponent020().computeValue_0373("loadable area (5x5)")),
   REGION_25("[★] Region 25x25", Items.CHAIN_COMMAND_BLOCK, new ConfigComponent020().computeValue_0373("Size: 25x25 blocks")),
   AIRDROP("[★] Airdrop", Items.REDSTONE_TORCH, new ConfigComponent020().computeValue_0373("Airdrop summons")),
   BLOCK_DAMAGER("[★] Block damage dealer", Items.JIGSAW, new ConfigComponent020().computeValue_0373("Cast: Damage")),
   FLY_MODIFIER("[⚡] Flight modifier", Items.FEATHER, new ConfigComponent020().computeValue_0373("Access to /fly")),
   FIX_MODIFIER("[⚡] Repair modifier", Items.AMETHYST_SHARD, new ConfigComponent020().computeValue_0373("Access to /fix")),
   PRIVILEGE_KEY("[★] Key to the case with Privileges", Items.TRIAL_KEY, new ConfigComponent020().computeValue_0373("Unlocks: Perk Case")),
   TOKEN_KEY("[★] Key to the Token case", Items.TRIAL_KEY, new ConfigComponent020().computeValue_0373("Unlocks: Token Case")),
   WHITE_TNT(
      "[★] White", Items.TNT, new ConfigComponent020().computeValue_0373("10 times stronger than usual").computeValue_0369("capable of exploding obsidian")
   ),
   BLACK_TNT("[★] Black", Items.TNT, new ConfigComponent020().computeValue_0373("capable of exploding obsidian")),
   BLOOD_ARROW(
      "Blood Arrow",
      Items.TIPPED_ARROW,
      new ConfigComponent026()
         .computeValue_0520(new ConfigComponent025(StatusEffects.WEAKNESS, 3, 60))
         .computeValue_0520(new ConfigComponent025(StatusEffects.BLINDNESS, 1, 40))
         .computeValue_0520(new ConfigComponent025(StatusEffects.MINING_FATIGUE, 1, 40))
         .computeValue_0520(new ConfigComponent025(StatusEffects.NAUSEA, 1, 100))
   ),
   FROST_ARROW(
      "Icing Arrow",
      Items.TIPPED_ARROW,
      new ConfigComponent026()
         .computeValue_0520(new ConfigComponent025(StatusEffects.SLOWNESS, 10, 100))
         .computeValue_0520(new ConfigComponent025(StatusEffects.MINING_FATIGUE, 3, 40))
   ),
   AGONY_ARROW(
      "Tormenting Arrow",
      Items.TIPPED_ARROW,
      new ConfigComponent026()
         .computeValue_0520(new ConfigComponent025(StatusEffects.SLOWNESS, 3, 100))
         .computeValue_0520(new ConfigComponent025(StatusEffects.WITHER, 3, 100))
         .computeValue_0520(new ConfigComponent025(StatusEffects.POISON, 3, 100))
   ),
   GOLDEN_APPLE("Golden Apple", Items.GOLDEN_APPLE),
   ENCHANTED_GOLDEN_APPLE("Charge", Items.ENCHANTED_GOLDEN_APPLE),
   SPAWNER("Spawner", Items.SPAWNER),
   EMERALD_ORE("Emerald Ore", Items.EMERALD_ORE),
   DRAGON_HEAD("Dragon Head", Items.DRAGON_HEAD),
   DRAGON_EGG("Dragon Egg", Items.DRAGON_EGG),
   ELYTRA("Elites", Items.ELYTRA),
   BEACON("Lighthouse", Items.BEACON),
   VILLAGER_SPAWN_EGG("Peasant Summon Egg", Items.VILLAGER_SPAWN_EGG),
   ENDERMAN_SPAWN_EGG("Enderman Summon Egg", Items.ENDERMAN_SPAWN_EGG),
   NETHERITE_UPGRADE("Netherite finish", Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
   DIAMOND("Diamond", Items.DIAMOND),
   NETHER_STAR("Nether's Star", Items.NETHER_STAR),
   ANCIENT_DEBRIS("Ancient Debris", Items.ANCIENT_DEBRIS),
   NETHERITE_INGOT("Netherite Ingot", Items.NETHERITE_INGOT);

   private final String displayName;
   private final Object profile;
   private final Item item;
   private final ConfigComponent016 attributeProcessor;
   private final ConfigComponent022 enchantmentProcessor;
   private final ConfigComponent020 descriptionProcessor;
   private final ConfigComponent024 nbtProcessor;
   private final ConfigComponent026 potionProcessor;
   private final Animation animationField1 = new Animation();
   private double doubleValueField2 = 1.0;
   private boolean flagField3 = false;

   public String getDisplayName_0029() {
      return this.displayName;
   }

   public Object getProfile_0030() {
      return this.profile;
   }

   public Item getItem_0031() {
      return this.item;
   }

   public ConfigComponent016 getAttributeProcessor_0032() {
      return this.attributeProcessor;
   }

   public ConfigComponent022 getEnchantmentProcessor_0033() {
      return this.enchantmentProcessor;
   }

   public ConfigComponent020 getDescriptionProcessor_0034() {
      return this.descriptionProcessor;
   }

   public ConfigComponent024 getNbtProcessor_0035() {
      return this.nbtProcessor;
   }

   public ConfigComponent026 getPotionProcessor_0036() {
      return this.potionProcessor;
   }

   public Animation getAnimationField1_0037() {
      return this.animationField1;
   }

   public void setDoubleValueField2_0026(double d) {
      this.doubleValueField2 = d;
   }

   public double getDoubleValueField2_0038() {
      return this.doubleValueField2;
   }

   public void setFlagField3_0028(boolean bl) {
      this.flagField3 = bl;
   }

   public boolean isFlagField3_0039() {
      return this.flagField3;
   }

   AutoBuyEntry(String string1, Item item) {
      this(string1, itemx, null, null, null, null, null, null);
   }

   AutoBuyEntry(String string1, Item item, ConfigComponent026 configComponent026) {
      this(string1, itemx, null, null, null, null, configComponent026, null);
   }

   AutoBuyEntry(String string1, Item item, ConfigComponent016 configComponent016) {
      this(string1, itemx, configComponent016, null, null, null, null, null);
   }

   AutoBuyEntry(String string1, Item item, ConfigComponent016 configComponent016, Object object) {
      this(string1, itemx, configComponent016, null, null, null, null, object);
   }

   AutoBuyEntry(String string1, Item item, ConfigComponent020 configComponent020) {
      this(string1, itemx, null, null, configComponent020, null, null, null);
   }

   AutoBuyEntry(String string1, Item item, ConfigComponent022 configComponent022, ConfigComponent020 configComponent020) {
      this(string1, itemx, null, configComponent022, configComponent020, null, null, null);
   }

   AutoBuyEntry(String string1, Item item, ConfigComponent022 configComponent022) {
      this(string1, itemx, null, configComponent022, null, null, null, null);
   }

   AutoBuyEntry(String string1, Item item, ConfigComponent026 configComponent026, Object object) {
      this(string1, itemx, null, null, null, null, configComponent026, object);
   }

   AutoBuyEntry(
      String string1,
      Item item,
      ConfigComponent016 configComponent016,
      ConfigComponent022 configComponent022,
      ConfigComponent020 configComponent020,
      ConfigComponent024 configComponent024,
      ConfigComponent026 configComponent026,
      Object object
   ) {
      this.displayName = string1;
      this.item = itemx;
      this.attributeProcessor = configComponent016;
      this.enchantmentProcessor = configComponent022;
      this.descriptionProcessor = configComponent020;
      this.nbtProcessor = configComponent024;
      this.potionProcessor = configComponent026;
      this.profile = object;
   }

   public ItemStack computeValue_0025() {
      ItemStack itemStack = new ItemStack(this.item);
      if (this.profile instanceof Integer && this.potionProcessor != null) {
         ComponentType componentType = DataComponentTypes.POTION_CONTENTS;
         Optional optional1 = Optional.empty();
         Optional optional;
         if (this.profile instanceof Integer integer) {
            optional = Optional.of(integer);
         } else {
            optional = Optional.empty();
         }

         itemStack.set(
            componentType,
            new PotionContentsComponent(
               optional1,
               optional,
               this.potionProcessor
                  .getListField1_0519()
                  .stream()
                  .map(
                     configComponent025 -> new StatusEffectInstance(
                        configComponent025.getEffect_0515(), configComponent025.getRequiredDuration_0518(), configComponent025.getRequiredLevel_0513() - 1
                     )
                  )
                  .toList(),
               Optional.empty()
            )
         );
      }

      if (this.profile instanceof String string) {
         String string1 = string;
         HashMultimap hashMultimap = HashMultimap.create();
         hashMultimap.put("textures", new Property("textures", string1));
         PropertyMap propertyMap = new PropertyMap(hashMultimap);
         GameProfile gameProfile = new GameProfile(UUID.nameUUIDFromBytes(string.getBytes()), this.name().toLowerCase(Locale.ROOT), propertyMap);
         itemStack.set(DataComponentTypes.PROFILE, ProfileComponent.ofStatic(gameProfile));
      }

      return itemStack;
   }

   public boolean checkCondition_0027(ItemStack itemStack) {
      return itemStack.isOf(this.item)
         && (this.attributeProcessor == null || this.attributeProcessor.checkCondition_0078(itemStack))
         && (this.enchantmentProcessor == null || this.enchantmentProcessor.checkCondition_0078(itemStack))
         && (this.descriptionProcessor == null || this.descriptionProcessor.checkCondition_0078(itemStack))
         && (this.nbtProcessor == null || this.nbtProcessor.checkCondition_0078(itemStack))
         && (this.potionProcessor == null || this.potionProcessor.checkCondition_0078(itemStack));
   }
}
