package aethereal.command;

import aethereal.util.UtilComponent265;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.command.CommandSource;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.PositionAndOnGround;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;

@CommandRegister(name = "vclip")
public class VclipCommand extends Command {
   @Override
   public void performAction_0094(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
      ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder.then(
                  this.computeValue_0173("up").executes(commandContext -> {
                     float f = this.computeValue_0325(true);
                     if (f != 0.0F) {
                        this.performAction_0323(f);
                        UtilComponent265.performAction_3505("You have been successfully raised by Y");
                        return 1;
                     } else {
                        return 1;
                     }
                  })
               ))
               .then(this.computeValue_0173("down").executes(commandContext -> {
                  float f = this.computeValue_0325(false);
                  if (f != 0.0F) {
                     this.performAction_0323(f);
                     UtilComponent265.performAction_3505("You have been successfully lowered by Y");
                     return 1;
                  } else {
                     return 1;
                  }
               })))
            .then(this.computeValue_0162("number").executes(commandContext -> {
               float f = this.computeValue_0168(commandContext, "number");
               this.performAction_0323(f);
               UtilComponent265.performAction_3505("You have been successfully transferred to" + f + "in Y");
               return 1;
            })))
         .executes(commandContext -> {
            UtilComponent265.performAction_3505("Usage: .vclip <number|up|down>");
            return 1;
         });
   }

   private void performAction_0323(float f) {
      double d = minecraft.player.getX();
      double e = minecraft.player.getY() + f;
      double g = minecraft.player.getZ();
      minecraft.player.networkHandler.sendPacket(new PositionAndOnGround(d, e, g, minecraft.player.isOnGround(), minecraft.player.horizontalCollision));
      minecraft.player.setPosition(d, e, g);
   }

   private float computeValue_0325(boolean bl) {
      BlockPos blockPos = minecraft.player.getBlockPos();
      int i = bl ? 25 : -1;
      int j = bl ? 255 : -255;
      int k = bl ? 1 : -1;
      int l = i;

      while (true) {
         int m = l;
         if (m == j) {
            return 0.0F;
         }

         BlockPos blockPos1 = blockPos.add(0, m, 0);
         BlockPos blockPos2 = blockPos.add(0, m + k, 0);
         if (minecraft.world.getBlockState(blockPos1).isAir() && minecraft.world.getBlockState(blockPos2).isAir()) {
            return m + (bl ? 1.0F : -1.0F);
         }

         if (!bl && minecraft.world.getBlockState(blockPos1).isOf(Blocks.BEDROCK)) {
            UtilComponent265.performAction_3505(Formatting.GRAY + "Teleportation to this location is not possible");
            return 0.0F;
         }

         l = m + k;
      }
   }
}
