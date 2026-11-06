package aethereal.command;

import aethereal.util.UtilComponent265;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.command.CommandSource;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.PositionAndOnGround;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

@CommandRegister(name = "hclip")
public class HclipCommand extends Command {
   @Override
   public void performAction_0094(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
      ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder.then(
                  this.computeValue_0173("forward").executes(commandContext -> {
                     Vec3d vec3d = computeValue_0261().normalize();
                     float f = this.computeValue_0263(vec3d);
                     if (f != 0.0F) {
                        this.performAction_0264(vec3d, f);
                        UtilComponent265.performAction_3505("You have been successfully moved forward horizontally");
                        return 1;
                     } else {
                        return 1;
                     }
                  })
               ))
               .then(this.computeValue_0173("back").executes(commandContext -> {
                  Vec3d vec3d = computeValue_0261().normalize().negate();
                  float f = this.computeValue_0263(vec3d);
                  if (f != 0.0F) {
                     this.performAction_0264(vec3d, f);
                     UtilComponent265.performAction_3505("You have been successfully moved back horizontally");
                     return 1;
                  } else {
                     return 1;
                  }
               })))
            .then(this.computeValue_0162("number").executes(commandContext -> {
               float f = this.computeValue_0168(commandContext, "number");
               Vec3d vec3d = computeValue_0261().normalize();
               this.performAction_0264(vec3d, f);
               UtilComponent265.performAction_3505("You have successfully moved to" + f + "blocks horizontally");
               return 1;
            })))
         .executes(commandContext -> {
            UtilComponent265.performAction_3505("Usage: .hclip <number|forward|back>");
            return 1;
         });
   }

   private static Vec3d computeValue_0261() {
      return Vec3d.fromPolar(0.0F, minecraft.player != null ? minecraft.player.getYaw() : 0.0F);
   }

   private void performAction_0264(Vec3d vec3d, float f) {
      Vec3d vec3d1 = vec3d.multiply(f);
      double d = minecraft.player.getX() + vec3d1.x;
      double e = minecraft.player.getY();
      double g = minecraft.player.getZ() + vec3d1.z;
      minecraft.player.networkHandler.sendPacket(new PositionAndOnGround(d, e, g, minecraft.player.isOnGround(), minecraft.player.horizontalCollision));
      minecraft.player.setPosition(d, e, g);
   }

   private float computeValue_0263(Vec3d vec3d) {
      Vec3d vec3d1 = vec3d.normalize();
      Vec3d vec3d2 = minecraft.player.getEntityPos();

      for (int i = 1; i <= 255; i++) {
         BlockPos blockPos = BlockPos.ofFloored(vec3d2.add(vec3d1.multiply(i)));
         BlockPos blockPos1 = BlockPos.ofFloored(vec3d2.add(vec3d1.multiply(i + 1)));
         if (minecraft.world.getBlockState(blockPos).isAir() && minecraft.world.getBlockState(blockPos1).isAir()) {
            return i + 1;
         }

         if (minecraft.world.getBlockState(blockPos).isOf(Blocks.BEDROCK)) {
            UtilComponent265.performAction_3505(Formatting.GRAY + "Teleportation to this location is not possible");
            return 0.0F;
         }
      }

      return 0.0F;
   }
}
