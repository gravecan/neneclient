package aethereal.command;

import aethereal.core.NeneClient;
import aethereal.util.UtilComponent265;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.util.math.Vec3d;

@CommandRegister(name = "gps")
public class GpsCommand extends Command {
   private Vec3d vec3dField1;

   public Vec3d getVec3dField1_0253() {
      return this.vec3dField1;
   }

   @Override
   public void performAction_0094(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
      ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder.then(
                  this.computeValue_0173("off").executes(commandContext -> {
                     if (this.vec3dField1 == null) {
                        UtilComponent265.performAction_3505("GPS tag is currently missing");
                        return 1;
                     } else {
                        this.vec3dField1 = null;
                        UtilComponent265.performAction_3505("GPS tag no longer appears");
                        return 1;
                     }
                  })
               ))
               .then(
                  ((RequiredArgumentBuilder)this.computeValue_0178("x").executes(commandContext -> {
                        UtilComponent265.performAction_3505("Usage: .gps <x> <z>, .gps <x> <y> <z> or .gps off");
                        return 1;
                     }))
                     .then(
                        ((RequiredArgumentBuilder)this.computeValue_0178("y or z")
                              .executes(
                                 commandContext -> {
                                    this.performAction_0255(
                                       new Vec3d(
                                          this.computeValue_0169(commandContext, "x"),
                                          minecraft.player.getY(),
                                          this.computeValue_0169(commandContext, "y or z")
                                       )
                                    );
                                    return 1;
                                 }
                              ))
                           .then(
                              this.computeValue_0178("z")
                                 .executes(
                                    commandContext -> {
                                       this.performAction_0255(
                                          new Vec3d(
                                             this.computeValue_0169(commandContext, "x"),
                                             this.computeValue_0169(commandContext, "y or z"),
                                             this.computeValue_0169(commandContext, "z")
                                          )
                                       );
                                       return 1;
                                    }
                                 )
                           )
                     )
               ))
            .then(this.computeValue_0173("event").executes(commandContext -> {
               NeneClient.getInstance()
                  .getProcessors()
                  .getCommandManagerField13_0666()
                  .getWayCommandField3_0215()
                  .setState290Field3_0119(WayCommand.State290.GPS);
               minecraft.player.networkHandler.sendChatCommand("event delay");
               return 1;
            })))
         .executes(commandContext -> {
            UtilComponent265.performAction_3505("Usage: .gps <x> <z>, .gps <x> <y> <z> or .gps off");
            return 1;
         });
   }

   public void performAction_0255(Vec3d vec3d) {
      this.vec3dField1 = vec3d;
      UtilComponent265.performAction_3505("GPS tag installed:" + (int)vec3d.getX() + ", " + (int)vec3d.getY() + ", " + (int)vec3d.getZ());
   }
}
