package aethereal.command;

import aethereal.core.NeneClient;
import aethereal.util.UtilComponent265;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.List;
import net.minecraft.command.CommandSource;

@CommandRegister(name = "warden")
public class WardenCommand extends Command {
   @Override
   public void performAction_0094(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
      List list = NeneClient.getInstance().getProcessors().getModules().getAutoWardenField60_0421().getListField6_1742();
      ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder.then(
                     ((LiteralArgumentBuilder)this.computeValue_0173("add").executes(commandContext -> {
                        UtilComponent265.performAction_3505("Usage: .warden add <anarchy>");
                        return 1;
                     })).then(this.computeValue_0178("anarchy").executes(commandContext -> {
                        int i = this.computeValue_0169(commandContext, "anarchy");
                        if (i < 1 || i > 999) {
                           UtilComponent265.performAction_3505("Anarchy must be from 1 to 999.");
                           return 1;
                        }

                        if (!list.contains(i)) {
                           if (list.size() < 10) {
                              list.add(i);
                              UtilComponent265.performAction_3505("Anarchy" + i + "added.");
                              return 1;
                           } else {
                              UtilComponent265.performAction_3505("You can add a maximum of 10 anarchies.");
                              return 1;
                           }
                        } else {
                           UtilComponent265.performAction_3505("Anarchy" + i + "already on the list.");
                           return 1;
                        }
                     }))
                  ))
                  .then(((LiteralArgumentBuilder)this.computeValue_0173("remove").executes(commandContext -> {
                     UtilComponent265.performAction_3505("Usage: .warden remove <anarchy>");
                     return 1;
                  })).then(this.computeValue_0178("anarchy").executes(commandContext -> {
                     int i = this.computeValue_0169(commandContext, "anarchy");
                     if (!list.remove(Integer.valueOf(i))) {
                        UtilComponent265.performAction_3505("Anarchy" + i + "not found.");
                        return 1;
                     } else {
                        UtilComponent265.performAction_3505("Anarchy" + i + "deleted.");
                        return 1;
                     }
                  }))))
               .then(this.computeValue_0173("list").executes(commandContext -> {
                  if (!list.isEmpty()) {
                     UtilComponent265.performAction_3505("Anarchy (" + list.size() + "): " + list);
                     return 1;
                  } else {
                     UtilComponent265.performAction_3505("The list of anarchies is empty.");
                     return 1;
                  }
               })))
            .then(this.computeValue_0173("clear").executes(commandContext -> {
               list.clear();
               UtilComponent265.performAction_3505("The list of anarchies has been cleared.");
               return 1;
            })))
         .executes(commandContext -> {
            UtilComponent265.performAction_3505("Usage: .warden <add|remove|list|clear>");
            return 1;
         });
   }
}
