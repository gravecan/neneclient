package aethereal.command;

import aethereal.core.EventTarget;
import aethereal.event.EventComponent074;
import aethereal.util.UtilComponent252;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;

@CommandRegister(name = "rct")
public class RctCommand extends Command {
   private int integerField1 = -1;

   public void setIntegerField1_0311(int i) {
      this.integerField1 = i;
   }

   public int getIntegerField1_0306() {
      return this.integerField1;
   }

   @Override
   public void performAction_0094(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
      ((LiteralArgumentBuilder)literalArgumentBuilder.executes(commandContext -> {
         this.performAction_0307();
         return 1;
      })).then(this.computeValue_0178("number").executes(commandContext -> {
         this.setIntegerField1_0304(this.computeValue_0169(commandContext, "number"));
         return 1;
      }));
   }

   public void performAction_0307() {
      this.setIntegerField1_0304(0);
   }

   public void setIntegerField1_0304(int i) {
      this.integerField1 = i;
   }

   public void performAction_0312() {
      this.integerField1 = -1;
   }

   public boolean checkCondition_0310() {
      return this.integerField1 >= 0;
   }

   @EventTarget
   public void onEventComponent074_0309(EventComponent074 eventComponent074) {
      if (this.checkCondition_0310()) {
         int i;
         if (UtilComponent252.NestedType400.checkCondition_3406()) {
            i = UtilComponent252.NestedType400.computeValue_3414();
         } else {
            i = UtilComponent252.NestedType403.checkCondition_3420() ? UtilComponent252.NestedType403.computeValue_3418() : -1;
         }

         int j = i;
         if (j == -1 && this.integerField1 > 0) {
            if ((UtilComponent252.NestedType400.checkCondition_3406() || UtilComponent252.NestedType403.checkCondition_3420())
               && minecraft.player.age % 20 == 0) {
               minecraft.player.networkHandler.sendChatCommand("an" + this.integerField1);
               this.performAction_0312();
               return;
            }

            return;
         }

         if (minecraft.player.age % 2 == 0) {
            if (j == -1) {
               this.setIntegerField1_0311(-1);
               return;
            }

            if (this.integerField1 == 0) {
               this.setIntegerField1_0311(j);
            }

            minecraft.player.networkHandler.sendChatCommand("hub");
         }
      }
   }
}
