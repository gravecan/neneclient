package aethereal.command;

import aethereal.config.ModuleManager;
import aethereal.core.Module;
import aethereal.core.NeneClient;
import aethereal.setting.KeybindSetting;
import aethereal.util.KeyUtil;
import aethereal.util.UtilComponent265;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.command.CommandSource;

@CommandRegister(name = "bind")
public class BindCommand extends Command {
   @Override
   public void performAction_0094(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
      ModuleManager moduleManager = NeneClient.getInstance().getProcessors().getModules();
      LiteralArgumentBuilder literalArgumentBuilder1 = (LiteralArgumentBuilder)this.computeValue_0173("add").executes(commandContext -> {
         UtilComponent265.performAction_3505("Usage: .bind add <module name> <key>");
         return 1;
      });
      RequiredArgumentBuilder requiredArgumentBuilder = this.computeValue_0161("arguments");
      ((LiteralArgumentBuilder)literalArgumentBuilder.then(
            literalArgumentBuilder1.then(
               requiredArgumentBuilder.suggests(this.computeValue_0175(moduleManager::getEntries, module -> module.getName()))
                  .executes(
                     commandContext -> {
                        String string = this.computeValue_0170(commandContext, "arguments");
                        if (string != null && !string.trim().isEmpty()) {
                           String[] strings = string.trim().split("\\s+");
                           if (strings.length < 2) {
                              UtilComponent265.performAction_3505("Usage: .bind add <module name> <key>");
                              return 1;
                           } else {
                              String string1 = strings[strings.length - 1];
                              String string2 = string.substring(0, string.length() - string1.length()).trim();
                              KeyUtil keyUtil = KeyUtil.computeValue_3476(string1);
                              if (keyUtil == KeyUtil.UNKNOWN) {
                                 UtilComponent265.performAction_3505("Key" + string1 + "not found.");
                                 return 1;
                              } else {
                                 Module module = moduleManager.getEntries()
                                    .stream()
                                    .filter(module1 -> module1.getName().equalsIgnoreCase(string2))
                                    .findFirst()
                                    .orElse(null);
                                 if (module == null) {
                                    UtilComponent265.performAction_3505("Module" + string2 + "not found.");
                                    return 1;
                                 } else {
                                    module.setKeybind(keyUtil.getCode_3474());
                                    moduleManager.save();
                                    UtilComponent265.performAction_3505(
                                       "Module" + string2 + "was successfully bound to the key" + keyUtil.getLabel_3477() + "."
                                    );
                                    return 1;
                                 }
                              }
                           }
                        } else {
                           UtilComponent265.performAction_3505("Usage: .bind add <module name> <key>");
                           return 1;
                        }
                     }
                  )
            )
         ))
         .then(
            this.computeValue_0173("list")
               .executes(
                  commandContext -> {
                     List list = moduleManager.getEntries()
                        .stream()
                        .filter(
                           module -> module.getKeybind() != -1
                              || module.getSettings()
                                 .stream()
                                 .anyMatch(setting -> setting instanceof KeybindSetting keybindSetting && keybindSetting.getCurrentValue() != -1)
                        )
                        .toList();
                     if (list.isEmpty()) {
                        UtilComponent265.performAction_3505("The list of modules with key bindings is empty.");
                        return 1;
                     } else {
                        UtilComponent265.performAction_3505("List of modules with key bindings (" + list.size() + "):");
                        list.forEach(
                           module -> {
                              String string = module.getSettings()
                                 .stream()
                                 .filter(setting -> setting instanceof KeybindSetting keybindSetting && keybindSetting.getCurrentValue() != -1)
                                 .map(
                                    setting -> setting.getName()
                                       + " &8→&7 "
                                       + KeyUtil.computeValue_3475(((KeybindSetting)setting).getCurrentValue()).getLabel_3477()
                                 )
                                 .collect(Collectors.joining(", "));
                              UtilComponent265.performAction_3505(
                                 " &c"
                                    + module.getName()
                                    + (module.getKeybind() != -1 ? " &8→&7 " + KeyUtil.computeValue_3475(module.getKeybind()).getLabel_3477() : "")
                                    + (string.isEmpty() ? "" : " &8[&7" + string + "&8]")
                              );
                           }
                        );
                        return 1;
                     }
                  }
               )
         )
         .then(this.computeValue_0173("clear").executes(commandContext -> {
            for (Module module : moduleManager.getEntries()) {
               module.setKeybind(-1);
            }

            moduleManager.save();
            UtilComponent265.performAction_3505("Bound keys were successfully cleared for all modules.");
            return 1;
         }))
         .then(((LiteralArgumentBuilder)this.computeValue_0173("remove").executes(commandContext -> {
            UtilComponent265.performAction_3505("Usage: .bind remove <key>");
            return 1;
         })).then(this.computeValue_0163("key").suggests(this.computeValue_0159()).executes(commandContext -> {
            String string = this.computeValue_0170(commandContext, "key");
            KeyUtil keyUtil = KeyUtil.computeValue_3476(string);
            if (keyUtil == KeyUtil.UNKNOWN) {
               UtilComponent265.performAction_3505("Key" + string + "not found.");
               return 1;
            } else {
               List list = moduleManager.getEntries().stream().filter(module -> module.getKeybind() == keyUtil.getCode_3474()).toList();
               if (list.isEmpty()) {
                  UtilComponent265.performAction_3505("No modules bound to a key" + keyUtil.getLabel_3477() + ".");
                  return 1;
               } else {
                  list.forEach(module -> module.setKeybind(-1));
                  moduleManager.save();
                  UtilComponent265.performAction_3505("Key" + keyUtil.getLabel_3477() + "was successfully removed from" + list.size() + "modules.");
                  return 1;
               }
            }
         })))
         .executes(commandContext -> {
            UtilComponent265.performAction_3505("Usage: .bind <add|list|clear|remove>");
            return 1;
         });
   }
}
