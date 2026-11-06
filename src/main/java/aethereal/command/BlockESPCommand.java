package aethereal.command;

import aethereal.render.RenderComponent183;
import aethereal.util.UtilComponent265;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.command.CommandSource;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

@CommandRegister(name = "blockesp")
public class BlockESPCommand extends Command {
   private final List<BlockESPCommand.NestedType289> listField1 = new CopyOnWriteArrayList<>();

   public List<BlockESPCommand.NestedType289> getListField1_0079() {
      return this.listField1;
   }

   @Override
   public void performAction_0094(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
      ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder.then(
                     ((LiteralArgumentBuilder)this.computeValue_0173("add").executes(commandContext -> {
                           UtilComponent265.performAction_3505("Usage: .blockesp add <block> [color]");
                           return 1;
                        }))
                        .then(
                           ((RequiredArgumentBuilder)this.computeValue_0163("block")
                                 .suggests(
                                    (commandContext, suggestionsBuilder) -> {
                                       Stream stream = Registries.BLOCK
                                          .stream()
                                          .filter(block -> block != Blocks.AIR)
                                          .map(block -> Registries.BLOCK.getId(block).getPath())
                                          .filter(string -> string.startsWith(suggestionsBuilder.getRemainingLowerCase()))
                                          .limit(20L);
                                       stream.forEach(object -> suggestionsBuilder.suggest((String)object));
                                       return suggestionsBuilder.buildFuture();
                                    }
                                 )
                                 .executes(commandContext -> this.computeValue_0096(this.computeValue_0170(commandContext, "block"), (String)null)))
                              .then(
                                 this.computeValue_0163("color")
                                    .suggests(
                                       (commandContext, suggestionsBuilder) -> {
                                          Stream stream = Arrays.stream(BlockESPCommand.EspColor.values())
                                             .map(espColor -> espColor.name().toLowerCase())
                                             .filter(string -> string.startsWith(suggestionsBuilder.getRemainingLowerCase()));
                                          stream.forEach(object -> suggestionsBuilder.suggest((String)object));
                                          return suggestionsBuilder.buildFuture();
                                       }
                                    )
                                    .executes(
                                       commandContext -> this.computeValue_0096(
                                          this.computeValue_0170(commandContext, "block"), this.computeValue_0170(commandContext, "color")
                                       )
                                    )
                              )
                        )
                  ))
                  .then(
                     ((LiteralArgumentBuilder)this.computeValue_0173("remove").executes(commandContext -> {
                           UtilComponent265.performAction_3505("Usage: .blockesp remove <block>");
                           return 1;
                        }))
                        .then(
                           this.computeValue_0163("block")
                              .suggests(this.computeValue_0175(() -> this.listField1, nestedType289 -> Registries.BLOCK.getId(nestedType289.block).getPath()))
                              .executes(commandContext -> {
                                 String string = this.computeValue_0170(commandContext, "block");
                                 Identifier identifier = Identifier.tryParse(string.toLowerCase());
                                 Block block = identifier != null ? (Block)Registries.BLOCK.get(identifier) : null;
                                 if (block != null && this.listField1.removeIf(nestedType289 -> nestedType289.block == block)) {
                                    UtilComponent265.performAction_3505("Block &c" + string + "&7successfully deleted");
                                    return 1;
                                 } else {
                                    UtilComponent265.performAction_3505("Block named &c" + string + "&7missing");
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
                              UtilComponent265.performAction_3505("Block list contains no elements");
                              return 1;
                           }

                           UtilComponent265.performAction_3505("List of all blocks (" + this.listField1.size() + "):");

                           for (BlockESPCommand.NestedType289 nestedType289 : this.listField1) {
                              UtilComponent265.performAction_3505(
                                 "— &c"
                                    + Registries.BLOCK.getId(nestedType289.block).getPath()
                                    + (nestedType289.color != -1 ? " &7(" + this.computeValue_0091(nestedType289.color) + ")" : "")
                              );
                           }

                           return 1;
                        }
                     )
               ))
            .then(this.computeValue_0173("clear").executes(commandContext -> {
               UtilComponent265.performAction_3505("Number of removed blocks:" + this.listField1.size());
               this.listField1.clear();
               return 1;
            })))
         .executes(commandContext -> {
            UtilComponent265.performAction_3505("Usage: .blockesp <add|remove|list|clear>");
            return 1;
         });
   }

   private int computeValue_0096(String string, String string1) {
      Identifier identifier = Identifier.tryParse(string.toLowerCase());
      Block block = identifier != null ? (Block)Registries.BLOCK.get(identifier) : null;
      if (block != null && block != Blocks.AIR) {
         this.listField1.removeIf(nestedType289 -> nestedType289.block == block);
         this.listField1.add(new BlockESPCommand.NestedType289(block, string1 != null ? BlockESPCommand.EspColor.computeValue_0104(string1) : -1));
         UtilComponent265.performAction_3505("Block &c" + string + "&7successfully added" + (string1 != null ? " (&c" + string1 + "&7)" : ""));
         return 1;
      } else {
         UtilComponent265.performAction_3505("Block named &c" + string + "&7not found");
         return 1;
      }
   }

   private String computeValue_0091(int i) {
      for (BlockESPCommand.EspColor espColor : BlockESPCommand.EspColor.values()) {
         if (RenderComponent183.computeValue_2558(espColor.integerField1, espColor.integerField2, espColor.integerField3, 140) == i) {
            return espColor.name().toLowerCase();
         }
      }

      return "custom";
   }

   public enum EspColor {
      RED(255, 0, 0),
      GREEN(0, 255, 0),
      BLUE(0, 0, 255),
      YELLOW(255, 255, 0),
      PURPLE(128, 0, 128),
      ORANGE(255, 165, 0),
      PINK(255, 192, 203),
      CYAN(0, 255, 255),
      WHITE(255, 255, 255),
      BLACK(0, 0, 0),
      GRAY(128, 128, 128),
      BROWN(165, 42, 42),
      LIME(50, 205, 50),
      MAGENTA(255, 0, 255),
      GOLD(255, 215, 0),
      CORAL(255, 127, 80),
      TURQUOISE(64, 224, 208),
      CRIMSON(220, 20, 60),
      EMERALD(80, 200, 120);

      final int integerField1;
      final int integerField2;
      final int integerField3;

      EspColor(final int j, final int k, final int l) {
         this.integerField1 = j;
         this.integerField2 = k;
         this.integerField3 = l;
      }

      static int computeValue_0104(String string) {
         try {
            BlockESPCommand.EspColor espColor = valueOf(string.toUpperCase());
            return RenderComponent183.computeValue_2558(espColor.integerField1, espColor.integerField2, espColor.integerField3, 140);
         } catch (IllegalArgumentException illegalArgumentException) {
            return -1;
         }
      }
   }

   public static class NestedType289 {
      final Block block;
      final int color;

      public NestedType289(Block block, int i) {
         this.block = blockx;
         this.color = i;
      }

      public Block getBlock_0106() {
         return this.block;
      }

      public int getColor_0105() {
         return this.color;
      }
   }
}
