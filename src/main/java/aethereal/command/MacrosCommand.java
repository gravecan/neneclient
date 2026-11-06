package aethereal.command;

import aethereal.core.NeneClient;
import aethereal.macro.Macro;
import aethereal.macro.MacroManager;
import aethereal.util.KeyUtil;
import aethereal.util.UtilComponent265;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import java.util.List;
import net.minecraft.command.CommandSource;

@CommandRegister(name = "macros")
public class MacrosCommand extends Command {
   @Override
   public void performAction_0094(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
      MacroManager macroManager = NeneClient.getInstance().getProcessors().getMacros();
      LiteralArgumentBuilder literalArgumentBuilder1 = (LiteralArgumentBuilder)literalArgumentBuilder.then(
         ((LiteralArgumentBuilder)this.computeValue_0173("add").executes(commandContext -> {
            UtilComponent265.performAction_3505("Usage: .macros add <key> <command>");
            return 1;
         })).then(this.computeValue_0163("key").suggests(this.computeValue_0159()).then(this.computeValue_0161("team").executes(commandContext -> {
            String string = this.computeValue_0170(commandContext, "key");
            String string1 = this.computeValue_0170(commandContext, "team");
            KeyUtil keyUtil = KeyUtil.computeValue_3476(string);
            if (keyUtil == KeyUtil.UNKNOWN) {
               UtilComponent265.performAction_3505("Key" + string + "not found.");
               return 1;
            } else {
               macroManager.performAction_1452(string, string1);
               macroManager.save();
               UtilComponent265.performAction_3505("Macro" + string1 + "was successfully added to the key" + keyUtil.getLabel_3477() + ".");
               return 1;
            }
         })))
      );
      LiteralArgumentBuilder literalArgumentBuilder2 = (LiteralArgumentBuilder)this.computeValue_0173("remove")
         .executes(
            commandContext -> {
               List list = macroManager.computeValue_1451();
               if (list.isEmpty()) {
                  UtilComponent265.performAction_3505("The list of macros is empty.");
                  return 1;
               }

               UtilComponent265.performAction_3505("Available commands for deletion (" + list.size() + "):");

               for (Macro macro : list) {
                  UtilComponent265.performAction_3505(
                     "  " + macro.getCommand_1446() + "(key:" + KeyUtil.computeValue_3476(macro.getKey_1445()).getLabel_3477() + ")"
                  );
               }

               UtilComponent265.performAction_3505("Usage: .macros remove <command>");
               return 1;
            }
         );
      RequiredArgumentBuilder requiredArgumentBuilder = this.computeValue_0161("team");
      literalArgumentBuilder1.then(
            literalArgumentBuilder2.then(
               requiredArgumentBuilder.suggests(this.computeValue_0175(macroManager::computeValue_1451, macro -> macro.getCommand_1446()))
                  .executes(
                     commandContext -> {
                        String string = this.computeValue_0170(commandContext, "team");
                        Macro macro = macroManager.computeValue_1451()
                           .stream()
                           .filter(macro1 -> macro1.getCommand_1446().equals(string))
                           .findFirst()
                           .orElse(null);
                        if (macro == null) {
                           UtilComponent265.performAction_3505("Macro with command" + string + "not found in the macro list.");
                           return 1;
                        } else {
                           macroManager.performAction_1449(macro.getKey_1445());
                           macroManager.save();
                           UtilComponent265.performAction_3505(
                              "Macro" + string + "was successfully removed from the key" + KeyUtil.computeValue_3476(macro.getKey_1445()).getLabel_3477() + "."
                           );
                           return 1;
                        }
                     }
                  )
            )
         )
         .then(this.computeValue_0173("list").executes(commandContext -> {
            List list = macroManager.computeValue_1451();
            if (list.isEmpty()) {
               UtilComponent265.performAction_3505("The list of macros is empty.");
               return 1;
            }

            UtilComponent265.performAction_3505("List of macros (" + list.size() + "):");

            for (Macro macro : list) {
               UtilComponent265.performAction_3505("  " + KeyUtil.computeValue_3476(macro.getKey_1445()).getLabel_3477() + ": " + macro.getCommand_1446());
            }

            return 1;
         }))
         .then(this.computeValue_0173("clear").executes(commandContext -> {
            UtilComponent265.performAction_3505("Macros were successfully removed from the list:" + macroManager.computeValue_1451().size());
            macroManager.getListField3_1453();
            macroManager.save();
            return 1;
         }))
         .executes(commandContext -> {
            UtilComponent265.performAction_3505("Usage: .macros <add|remove|list|clear>");
            return 1;
         });
   }
}
