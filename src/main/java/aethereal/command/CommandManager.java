package aethereal.command;

import aethereal.api.ApiAnnotation001;
import aethereal.config.PersistentProcessor;
import aethereal.core.ObfuscatorRuntime;
import aethereal.lib.log4j.Log4jComponent146;
import aethereal.lib.log4j.Log4jComponent147;
import aethereal.util.UtilComponent265;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.ParsedCommandNode;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientCommandSource;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.command.CommandSource;
import net.minecraft.command.permission.PermissionPredicate;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class CommandManager extends PersistentProcessor {
   private static final Log4jComponent147 log4jComponent147Field1 = Log4jComponent146.computeValue_1319(CommandManager.class);
   private final List<Command> listField2 = new ArrayList<>();
   private final WayCommand wayCommandField3 = new WayCommand();
   private final GpsCommand gpsCommandField4 = new GpsCommand();
   private final LayoutCommand layoutCommandField5 = new LayoutCommand();
   private final RctCommand rctCommandField6 = new RctCommand();
   private final BlockESPCommand blockESPCommandField7 = new BlockESPCommand();
   private final String stringField8 = ".";
   private final CommandDispatcher<CommandSource> commandDispatcherField9 = new CommandDispatcher(new CommandComponent012.NestedType294());
   private final ClientCommandSource clientCommandSourceField10 = new ClientCommandSource(
      (ClientPlayNetworkHandler)null, MinecraftClient.getInstance(), PermissionPredicate.ALL
   );

   @ApiAnnotation001
   @Override
   public void load() {
      this.performAction_0224(
         this.wayCommandField3,
         this.gpsCommandField4,
         this.layoutCommandField5,
         this.rctCommandField6,
         this.blockESPCommandField7,
         new AhCommand(),
         new MacrosCommand(),
         new FriendCommand(),
         new StaffCommand(),
         new CfgCommand(),
         new BindCommand(),
         new VclipCommand(),
         new HclipCommand(),
         new CccCommand()
      );
   }

   public CommandDispatcher<CommandSource> getCommandDispatcherField9_0219() {
      return this.commandDispatcherField9;
   }

   public List<Command> getListField2_0213() {
      return this.listField2;
   }

   public WayCommand getWayCommandField3_0215() {
      return this.wayCommandField3;
   }

   public GpsCommand getGpsCommandField4_0216() {
      return this.gpsCommandField4;
   }

   public LayoutCommand getLayoutCommandField5_0217() {
      return this.layoutCommandField5;
   }

   public RctCommand getRctCommandField6_0218() {
      return this.rctCommandField6;
   }

   public BlockESPCommand getBlockESPCommandField7_0214() {
      return this.blockESPCommandField7;
   }

   public ClientCommandSource getClientCommandSourceField10_0221() {
      return this.clientCommandSourceField10;
   }

   public String computeValue_0220() {
      return ".";
   }

   @Override
   public void save() {
   }

   public void performAction_0224(Command... commands) {
      for (Command command : commands) {
         this.listField2.add(command);
         command.performAction_0165(this.commandDispatcherField9);
      }
   }

   public void performAction_0223(String string, CallbackInfo callbackInfo) {
      if (string != null && !string.isEmpty() && string.startsWith(this.computeValue_0220())) {
         callbackInfo.cancel();
         String string1 = string.substring(this.computeValue_0220().length()).trim();
         if (string1.isEmpty()) {
            UtilComponent265.performAction_3505("&cEnter the command. " + this.computeValue_0220() + "&c and press Tab to view all commands");
         } else {
            try {
               ParseResults parseResults = this.commandDispatcherField9.parse(string1, this.clientCommandSourceField10);
               String string2 = string1.split("\\s+")[0];
               CommandNode commandNode = this.commandDispatcherField9.getRoot().getChild(string2);
               if (commandNode == null) {
                  for (CommandNode commandNode1 : this.commandDispatcherField9.getRoot().getChildren()) {
                     if (commandNode1.getName().equalsIgnoreCase(string2)) {
                        commandNode = commandNode1;
                        break;
                     }
                  }
               }

               if (commandNode == null) {
                  UtilComponent265.performAction_3505("&cCommand &f" + string2 + "&c not found! " + this.computeValue_0220() + "&c for list of commands");
                  return;
               }

               for (ParsedCommandNode parsedCommandNode : parseResults.getContext().getNodes()) {
                  if (parsedCommandNode.getNode() instanceof LiteralCommandNode literalCommandNode) {
                     int i = parsedCommandNode.getRange().getLength();
                     if (i != literalCommandNode.getLiteral().length()) {
                        UtilComponent265.performAction_3505("&cCommand &f" + string2 + "&c not found! " + this.computeValue_0220() + "&c for list of commands");
                        return;
                     }
                  }
               }

               if (parseResults.getReader().canRead()) {
                  if (!parseResults.getExceptions().isEmpty()) {
                     for (CommandSyntaxException commandSyntaxException1 : parseResults.getExceptions().values()) {
                        UtilComponent265.performAction_3505("&c" + commandSyntaxException1.getMessage());
                     }
                  } else {
                     UtilComponent265.performAction_3505("&cUnknown argument or invalid command syntax");
                  }

                  return;
               }

               this.commandDispatcherField9.execute(parseResults);
            } catch (CommandSyntaxException commandSyntaxException) {
               UtilComponent265.performAction_3505("&c" + commandSyntaxException.getMessage());
            } catch (Exception exception) {
               UtilComponent265.performAction_3505("&cError executing command:" + exception.getMessage());
            }
         }
      }
   }

   public static <T> RequiredArgumentBuilder<CommandSource, T> computeValue_0222(String string, ArgumentType<T> argumentType) {
      return RequiredArgumentBuilder.argument(string, argumentType);
   }

   static {
      ObfuscatorRuntime.registerClass(CommandManager.class, 22);
   }
}
