package aethereal.command;

import aethereal.config.ThemeInfo;
import aethereal.core.EventTarget;
import aethereal.core.NeneClient;
import aethereal.event.DrawEvent;
import aethereal.event.EventComponent074;
import aethereal.event.PacketEvent;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent188;
import aethereal.util.UtilComponent265;
import aethereal.util.UtilComponent284;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.command.CommandSource;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix3x2f;
import org.joml.Vector2f;

@CommandRegister(name = "way")
public class WayCommand extends Command {
   private final List<WayCommand.NestedType291> listField1 = new ArrayList<>();
   private final List<String> listField2 = new ArrayList<>();
   private WayCommand.State290 state290Field3 = WayCommand.State290.NONE;

   public List<WayCommand.NestedType291> getListField1_0108() {
      return this.listField1;
   }

   public void setState290Field3_0119(WayCommand.State290 state290) {
      this.state290Field3 = state290;
   }

   @Override
   public void performAction_0094(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
      ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder.then(
                           ((LiteralArgumentBuilder)this.computeValue_0173("add").executes(commandContext -> {
                                 UtilComponent265.performAction_3505("Использование: .way add <название> <x> <z> или .way add <название> <x> <y> <z>\"");
                                 return 1;
                              }))
                              .then(
                                 ((RequiredArgumentBuilder)this.computeValue_0163("название").executes(commandContext -> {
                                       UtilComponent265.performAction_3505("Использование: .way add <название> <x> <z> или .way add <название> <x> <y> <z>\"");
                                       return 1;
                                    }))
                                    .then(
                                       ((RequiredArgumentBuilder)this.computeValue_0178("x").executes(commandContext -> {
                                             UtilComponent265.performAction_3505(
                                                "Использование: .way add <название> <x> <z> или .way add <название> <x> <y> <z>\""
                                             );
                                             return 1;
                                          }))
                                          .then(
                                             ((RequiredArgumentBuilder)this.computeValue_0178("y или z")
                                                   .executes(
                                                      commandContext -> {
                                                         this.performAction_0126(
                                                            this.computeValue_0170(commandContext, "название"),
                                                            new Vec3d(
                                                               this.computeValue_0169(commandContext, "x"),
                                                               minecraft.player.getY(),
                                                               this.computeValue_0169(commandContext, "y или z")
                                                            )
                                                         );
                                                         return 1;
                                                      }
                                                   ))
                                                .then(
                                                   this.computeValue_0178("z")
                                                      .executes(
                                                         commandContext -> {
                                                            this.performAction_0126(
                                                               this.computeValue_0170(commandContext, "название"),
                                                               new Vec3d(
                                                                  this.computeValue_0169(commandContext, "x"),
                                                                  this.computeValue_0169(commandContext, "y или z"),
                                                                  this.computeValue_0169(commandContext, "z")
                                                               )
                                                            );
                                                            return 1;
                                                         }
                                                      )
                                                )
                                          )
                                    )
                              )
                        ))
                        .then(this.computeValue_0173("me").executes(commandContext -> {
                           this.performAction_0126("me", minecraft.player.getEntityPos());
                           return 1;
                        })))
                     .then(
                        ((LiteralArgumentBuilder)this.computeValue_0173("remove").executes(commandContext -> {
                              UtilComponent265.performAction_3505("Использование: .way remove <название>");
                              return 1;
                           }))
                           .then(
                              this.computeValue_0163("название")
                                 .suggests(this.computeValue_0175(() -> this.listField1, nestedType291 -> nestedType291.getName_0135()))
                                 .executes(commandContext -> {
                                    String string = this.computeValue_0170(commandContext, "название");
                                    if (!this.checkCondition_0124(string)) {
                                       UtilComponent265.performAction_3505("Метка с именем &c" + string + " &7отсутствует");
                                       return 1;
                                    } else {
                                       this.listField1.removeIf(nestedType291 -> nestedType291.getName_0135().equalsIgnoreCase(string));
                                       UtilComponent265.performAction_3505("Метка с именем &c" + string + " &7успешно удалена");
                                       return 1;
                                    }
                                 })
                           )
                     ))
                  .then(
                     this.computeValue_0173("list")
                        .executes(
                           commandContext -> {
                              if (this.listField1.isEmpty()) {
                                 UtilComponent265.performAction_3505("Список меток не содержит элементов");
                                 return 1;
                              }

                              UtilComponent265.performAction_3505("Список всех меток (" + this.listField1.size() + "):");

                              for (WayCommand.NestedType291 nestedType291 : this.listField1) {
                                 UtilComponent265.performAction_3505(
                                    "— &c" + nestedType291.getName_0135() + " &7[&f" + this.computeValue_0128(nestedType291.getPosition_0134()) + "&7]&f"
                                 );
                              }

                              return 1;
                           }
                        )
                  ))
               .then(this.computeValue_0173("event").executes(commandContext -> {
                  this.state290Field3 = WayCommand.State290.WAY;
                  minecraft.player.networkHandler.sendChatCommand("event delay");
                  return 1;
               })))
            .then(this.computeValue_0173("clear").executes(commandContext -> {
               UtilComponent265.performAction_3505("Количество удалённых меток: " + this.listField1.size());
               this.listField1.clear();
               return 1;
            })))
         .executes(commandContext -> {
            UtilComponent265.performAction_3505("Использование: .way <add|me|remove|list|clear|event>");
            return 1;
         });
   }

   @EventTarget
   public void onPacketEvent_0117(PacketEvent packetEvent) {
      if (this.state290Field3 != WayCommand.State290.NONE && packetEvent.checkCondition_1013()) {
         GameMessageS2CPacket gameMessageS2CPacket = (GameMessageS2CPacket)packetEvent.getPacket_1015();
         if (gameMessageS2CPacket instanceof GameMessageS2CPacket gameMessageS2CPacket1) {
            String string = gameMessageS2CPacket1.content().getString();
            if (string.contains("[Ивенты]")
               || string.contains("Аир-дроп:")
               || string.contains("|| /warp portal")
               || string.contains("|| Координаты:")
               || string.contains("Призван игроком:")
               || string.contains("Уровень лута:")
               || string.contains("Статус:")
               || string.contains("[1]")
               || string.contains("[2]")) {
               packetEvent.performAction_0597(true);
               this.listField2.add(string);
            }
         }
      }
   }

   @EventTarget
   public void onEventComponent074_0116(EventComponent074 eventComponent074) {
      if (this.state290Field3 != WayCommand.State290.NONE && !this.listField2.isEmpty()) {
         boolean bl = false;

         label51:
         for (int i = 0; i < this.listField2.size(); i++) {
            String string = this.listField2.get(i);
            if (string.contains("[1] Маяк убийца")
               || string.contains("[1] Вулкан")
               || string.contains("[1] Метеоритный дождь")
               || string.contains("[1] Гейзер")) {
               for (int j = i; j < this.listField2.size(); j++) {
                  String string1 = this.listField2.get(j);
                  if (string1.contains("|| Координаты:")) {
                     Matcher matcher = Pattern.compile("\\[(-?\\d+) (-?\\d+) (-?\\d+)]").matcher(string1);
                     if (matcher.find()) {
                        Vec3d vec3d = new Vec3d(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));
                        if (this.state290Field3 == WayCommand.State290.GPS) {
                           NeneClient.getInstance().getProcessors().getCommandManagerField13_0666().getGpsCommandField4_0216().performAction_0255(vec3d);
                        } else {
                           this.performAction_0126("Ивент", vec3d);
                        }

                        bl = true;
                     }
                     break label51;
                  }
               }
               break;
            }
         }

         if (!bl) {
            UtilComponent265.performAction_3505("Нет активного события с координатами.");
         }

         this.listField2.clear();
         this.state290Field3 = WayCommand.State290.NONE;
      }
   }

   @EventTarget
   public void onDrawEvent_0115(DrawEvent drawEvent) {
      if (drawEvent.checkCondition_1001()) {
         for (WayCommand.NestedType291 nestedType291 : this.listField1) {
            this.performAction_0121(drawEvent, nestedType291, minecraft.player.getEyePos());
         }

         Vec3d vec3d = NeneClient.getInstance().getProcessors().getCommandManagerField13_0666().getGpsCommandField4_0216().getVec3dField1_0253();
         if (vec3d != null) {
            this.performAction_0122(drawEvent, vec3d, minecraft.player.getEyePos());
         }
      }
   }

   public void performAction_0126(String string, Vec3d vec3d) {
      String string1 = string.length() > 6 ? string.substring(0, 6) : string;
      boolean bl = this.listField1
         .removeIf(nestedType291 -> nestedType291.getName_0135().equalsIgnoreCase(string1) || nestedType291.getPosition_0134().distanceTo(vec3d) <= 5.0);
      this.listField1.add(new WayCommand.NestedType291(string1, vec3d));
      UtilComponent265.performAction_3505("Метка &c" + string1 + (bl ? " &7успешно переставлена: " : " &7успешно добавлена: ") + this.computeValue_0128(vec3d));
   }

   private boolean checkCondition_0124(String string) {
      return this.listField1.stream().anyMatch(nestedType291 -> nestedType291.getName_0135().equalsIgnoreCase(string));
   }

   private String computeValue_0128(Vec3d vec3d) {
      return (int)vec3d.getX() + ", " + (int)vec3d.getY() + ", " + (int)vec3d.getZ();
   }

   private void performAction_0121(DrawEvent drawEvent, WayCommand.NestedType291 nestedType291, Vec3d vec3d) {
      Vector2f vector2f = UtilComponent284.computeValue_3615(
         nestedType291.getPosition_0134().x, nestedType291.getPosition_0134().y, nestedType291.getPosition_0134().z
      );
      if (UtilComponent284.checkCondition_3618(vector2f)) {
         int i = NeneClient.getInstance().getProcessors().getThemes().computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547();
         int j = NeneClient.getInstance().getProcessors().getThemes().computeValue_0557(ThemeInfo.BACKGROUND_HUD).computeValue_0547();
         MutableText mutableText = Text.literal(nestedType291.getName_0135().toUpperCase(Locale.ROOT))
            .append(Text.literal("  /  ").setStyle(Style.EMPTY.withColor(i)))
            .append(Text.literal(String.format(Locale.US, "%.1fм", vec3d.distanceTo(nestedType291.getPosition_0134()))));
         float f = 14.0F + RenderComponent188.renderComponent187Field5.computeValue_2628(mutableText, 6.25F);
         float g = vector2f.x() - f / 2.0F;
         float h = vector2f.y() - 5.75F;
         drawEvent.getRenderComponent184Field1_0998()
            .performAction_2580(
               drawEvent.getStack_1005(),
               g,
               h,
               f,
               11.5F,
               3.5F,
               RenderComponent183.computeValue_2549(
                  j, NeneClient.getInstance().getProcessors().getThemes().computeValue_0557(ThemeInfo.BACKGROUND_HUD).computeValue_0548()
               ),
               1.0F,
               RenderComponent183.computeValue_2549(
                  j, NeneClient.getInstance().getProcessors().getThemes().computeValue_0557(ThemeInfo.BACKGROUND_HUD).computeValue_0548()
               ),
               6.0F
            );
         RenderComponent188.renderComponent187Field1.performAction_2630(drawEvent.getStack_1005(), "F", g + 3.0F, h + 3.0F, 5.5F, i);
         RenderComponent188.renderComponent187Field5
            .performAction_2634(
               drawEvent.getStack_1005(),
               mutableText,
               g + 3.0F + 5.5F + 2.5F,
               h + (11.5F - RenderComponent188.renderComponent187Field5.computeValue_2622(6.25F)) / 2.0F - 0.25F,
               6.25F
            );
      }
   }

   private void performAction_0122(DrawEvent drawEvent, Vec3d vec3d, Vec3d vec3d1) {
      Matrix3x2f matrix3x2f = drawEvent.getStack_1005();
      int i = NeneClient.getInstance().getProcessors().getThemes().computeValue_0557(ThemeInfo.PRIMARY).computeValue_0547();
      double d = vec3d.x - minecraft.player.getX();
      double e = vec3d.z - minecraft.player.getZ();
      float f = (float)Math.toDegrees(Math.atan2(-d, e));
      float g = MathHelper.wrapDegrees(f - minecraft.player.getYaw());
      float h = minecraft.getWindow().getScaledWidth() / 2.0F;
      float j = minecraft.getWindow().getScaledHeight() * 0.25F;
      Matrix3x2f matrix3x2f1 = new Matrix3x2f(matrix3x2f);
      matrix3x2f1.translate(h, j);
      matrix3x2f1.rotate((float)Math.toRadians(g));
      drawEvent.getRenderComponent184Field1_0998()
         .performAction_2585(matrix3x2f1, Identifier.of("neneclient", "pictures/triangle.png"), -7.0F, -7.0F, 14.0F, 14.0F, 0.0F, i);
      MutableText mutableText = Text.literal(String.format(Locale.US, "%.1fм", vec3d1.distanceTo(vec3d)));
      RenderComponent188.renderComponent187Field4
         .performAction_2634(
            matrix3x2f, mutableText, h - RenderComponent188.renderComponent187Field4.computeValue_2628(mutableText, 7.0F) / 2.0F, j + 7.0F + 2.0F, 7.0F
         );
   }

   public static class NestedType291 {
      private final String name;
      private final Vec3d position;

      public NestedType291(String string, Vec3d vec3d) {
         this.name = string;
         this.position = vec3d;
      }

      public String getName_0135() {
         return this.name;
      }

      public Vec3d getPosition_0134() {
         return this.position;
      }
   }

   public enum State290 {
      NONE,
      WAY,
      GPS;
   }
}
