package aethereal.command;

import aethereal.core.NeneClient;
import aethereal.friend.Friend;
import aethereal.friend.FriendManager;
import aethereal.util.UtilComponent265;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.command.CommandSource;

@CommandRegister(name = "friend")
public class FriendCommand extends Command {
   @Override
   public void performAction_0094(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
      FriendManager friendManager = NeneClient.getInstance().getProcessors().getFriends();
      LiteralArgumentBuilder literalArgumentBuilder1 = (LiteralArgumentBuilder)literalArgumentBuilder.then(
         ((LiteralArgumentBuilder)this.computeValue_0173("add").executes(commandContext -> {
            UtilComponent265.performAction_3505("Usage: .friend add <nickname>");
            return 1;
         })).then(this.computeValue_0174("Nick").suggests(this.computeValue_0164()).executes(commandContext -> {
            String string = this.computeValue_0170(commandContext, "Nick");
            if (friendManager.checkCondition_1107(string)) {
               UtilComponent265.performAction_3505("Friend" + string + "is already on your friends list.");
               return 1;
            } else {
               friendManager.performAction_1106(string);
               friendManager.save();
               UtilComponent265.performAction_3505("Friend" + string + "was successfully added to your friends list.");
               return 1;
            }
         }))
      );
      LiteralArgumentBuilder literalArgumentBuilder2 = (LiteralArgumentBuilder)this.computeValue_0173("remove").executes(commandContext -> {
         UtilComponent265.performAction_3505("Usage: .friend remove <nickname>");
         return 1;
      });
      RequiredArgumentBuilder requiredArgumentBuilder = this.computeValue_0174("Nick");
      literalArgumentBuilder1.then(
            literalArgumentBuilder2.then(
               requiredArgumentBuilder.suggests(this.computeValue_0175(friendManager::computeValue_1108, friend -> friend.getName_1103()))
                  .executes(commandContext -> {
                     String string = this.computeValue_0170(commandContext, "Nick");
                     if (!friendManager.checkCondition_1107(string)) {
                        UtilComponent265.performAction_3505("Friend" + string + "not found in friends list.");
                        return 1;
                     } else {
                        friendManager.performAction_1111(string);
                        friendManager.save();
                        UtilComponent265.performAction_3505("Friend" + string + "was successfully removed from your friends list.");
                        return 1;
                     }
                  })
            )
         )
         .then(this.computeValue_0173("list").executes(commandContext -> {
            if (friendManager.computeValue_1108().isEmpty()) {
               UtilComponent265.performAction_3505("The friends list is empty.");
               return 1;
            }

            UtilComponent265.performAction_3505("Friends list (" + friendManager.computeValue_1108().size() + "):");

            for (Friend friend : friendManager.computeValue_1108()) {
               UtilComponent265.performAction_3505("  - " + friend.getName_1103());
            }

            return 1;
         }))
         .then(this.computeValue_0173("clear").executes(commandContext -> {
            UtilComponent265.performAction_3505("Friends were successfully removed from the list:" + friendManager.computeValue_1108().size());
            friendManager.getListField3_1110();
            friendManager.save();
            return 1;
         }))
         .executes(commandContext -> {
            UtilComponent265.performAction_3505("Usage: .friend <add|remove|list|clear>");
            return 1;
         });
   }
}
