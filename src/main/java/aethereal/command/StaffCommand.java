package aethereal.command;

import aethereal.core.NeneClient;
import aethereal.staff.StaffEntry;
import aethereal.staff.StaffManager;
import aethereal.util.UtilComponent265;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.command.CommandSource;

@CommandRegister(name = "staff")
public class StaffCommand extends Command {
   @Override
   public void performAction_0094(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
      StaffManager staffManager = NeneClient.getInstance().getProcessors().getStaff();
      LiteralArgumentBuilder literalArgumentBuilder1 = (LiteralArgumentBuilder)literalArgumentBuilder.then(
         ((LiteralArgumentBuilder)this.computeValue_0173("add").executes(commandContext -> {
            UtilComponent265.performAction_3505("Usage: .staff add <nickname>");
            return 1;
         })).then(this.computeValue_0174("Nick").suggests(this.computeValue_0164()).executes(commandContext -> {
            String string = this.computeValue_0170(commandContext, "Nick");
            if (staffManager.checkCondition_2876(string)) {
               UtilComponent265.performAction_3505("Staff" + string + "is already on the staff list.");
               return 1;
            } else {
               staffManager.performAction_2875(string);
               staffManager.save();
               UtilComponent265.performAction_3505("Staff" + string + "was successfully added to the staff list.");
               return 1;
            }
         }))
      );
      LiteralArgumentBuilder literalArgumentBuilder2 = (LiteralArgumentBuilder)this.computeValue_0173("remove").executes(commandContext -> {
         UtilComponent265.performAction_3505("Usage: .staff remove <nickname>");
         return 1;
      });
      RequiredArgumentBuilder requiredArgumentBuilder = this.computeValue_0174("Nick");
      literalArgumentBuilder1.then(
            literalArgumentBuilder2.then(
               requiredArgumentBuilder.suggests(this.computeValue_0175(staffManager::computeValue_2878, staffEntry -> staffEntry.getName_2870()))
                  .executes(commandContext -> {
                     String string = this.computeValue_0170(commandContext, "Nick");
                     if (!staffManager.checkCondition_2876(string)) {
                        UtilComponent265.performAction_3505("Staff" + string + "not found in the staff list.");
                        return 1;
                     } else {
                        staffManager.performAction_2883(string);
                        staffManager.save();
                        UtilComponent265.performAction_3505("Staff" + string + "was successfully removed from the staff list.");
                        return 1;
                     }
                  })
            )
         )
         .then(this.computeValue_0173("list").executes(commandContext -> {
            if (staffManager.computeValue_2878().isEmpty()) {
               UtilComponent265.performAction_3505("The staff list is empty.");
               return 1;
            }

            UtilComponent265.performAction_3505("List of staff (" + staffManager.computeValue_2878().size() + "):");

            for (StaffEntry staffEntry : staffManager.computeValue_2878()) {
               UtilComponent265.performAction_3505("  - " + staffEntry.getName_2870());
            }

            return 1;
         }))
         .then(this.computeValue_0173("clear").executes(commandContext -> {
            UtilComponent265.performAction_3505("Staff was successfully removed from the list:" + staffManager.computeValue_2878().size());
            staffManager.getListField3_2882();
            staffManager.save();
            return 1;
         }))
         .executes(commandContext -> {
            UtilComponent265.performAction_3505("Usage: .staff <add|remove|list|clear>");
            return 1;
         });
   }
}
