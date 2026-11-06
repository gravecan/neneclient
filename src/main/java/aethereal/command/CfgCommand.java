package aethereal.command;

import aethereal.config.ModuleManager;
import aethereal.core.Module;
import aethereal.core.NeneClient;
import aethereal.setting.Setting;
import aethereal.util.UtilComponent265;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.io.File;
import java.util.Arrays;
import java.util.stream.Stream;
import net.minecraft.command.CommandSource;
import net.minecraft.util.Util;

@CommandRegister(name = "cfg")
public class CfgCommand extends Command {
   @Override
   public void performAction_0094(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
      ModuleManager moduleManager = NeneClient.getInstance().getProcessors().getModules();
      ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder.then(
                           ((LiteralArgumentBuilder)this.computeValue_0173("save").executes(commandContext -> {
                              UtilComponent265.performAction_3505("Usage: .cfg save <name>");
                              return 1;
                           })).then(this.computeValue_0174("Name").executes(commandContext -> {
                              String string = this.computeValue_0170(commandContext, "Name");
                              moduleManager.performAction_0395(string);
                              UtilComponent265.performAction_3505("Config" + string + "was successfully saved.");
                              return 1;
                           }))
                        ))
                        .then(((LiteralArgumentBuilder)this.computeValue_0173("load").executes(commandContext -> {
                           UtilComponent265.performAction_3505("Usage: .cfg load <name>");
                           return 1;
                        })).then(this.computeValue_0174("Name").suggests(this.computeValue_0229()).executes(commandContext -> {
                           String string = this.computeValue_0170(commandContext, "Name");
                           if (moduleManager.checkCondition_0391(string)) {
                              UtilComponent265.performAction_3505("Config" + string + "was successfully loaded.");
                              return 1;
                           } else {
                              UtilComponent265.performAction_3505("Config" + string + "not found.");
                              return 1;
                           }
                        }))))
                     .then(this.computeValue_0173("list").executes(commandContext -> {
                        File file = moduleManager.computeValue_0393();
                        File[] files = file.listFiles((file2, string) -> string.endsWith(".json"));
                        if (files != null && files.length != 0) {
                           UtilComponent265.performAction_3505("List of configs (" + files.length + "):");

                           for (File file1 : files) {
                              UtilComponent265.performAction_3505("  - " + file1.getName());
                           }

                           return 1;
                        } else {
                           UtilComponent265.performAction_3505("The list of configs is empty.");
                           return 1;
                        }
                     })))
                  .then(this.computeValue_0173("reset").executes(commandContext -> {
                     for (Module module : moduleManager.getEntries()) {
                        module.setEnabled(false);
                        module.setKeybind(-1);

                        for (Setting setting : module.getSettings()) {
                           if (setting.getDefaultValue() != null) {
                              performAction_0234(setting);
                           }
                        }
                     }

                     UtilComponent265.performAction_3505("All modules have been reset to their default state.");
                     return 1;
                  })))
               .then(((LiteralArgumentBuilder)this.computeValue_0173("remove").executes(commandContext -> {
                  UtilComponent265.performAction_3505("Usage: .cfg remove <name>");
                  return 1;
               })).then(this.computeValue_0174("Name").suggests(this.computeValue_0229()).executes(commandContext -> {
                  String string = this.computeValue_0170(commandContext, "Name");
                  if (moduleManager.checkCondition_0396(string)) {
                     UtilComponent265.performAction_3505("Config" + string + "was successfully removed.");
                     return 1;
                  } else {
                     UtilComponent265.performAction_3505("Config" + string + "not found.");
                     return 1;
                  }
               }))))
            .then(this.computeValue_0173("dir").executes(commandContext -> {
               Util.getOperatingSystem().open(moduleManager.getFileField2_0355());
               return 1;
            })))
         .executes(commandContext -> {
            UtilComponent265.performAction_3505("Usage: .cfg <load|save|list|reset|remove|dir>");
            return 1;
         });
   }

   private SuggestionProvider<CommandSource> computeValue_0229() {
      return (commandContext, suggestionsBuilder) -> {
         ModuleManager moduleManager = NeneClient.getInstance().getProcessors().getModules();
         File file = moduleManager.computeValue_0393();
         File[] files;
         if (file.exists() && (files = file.listFiles((file1, string) -> string.endsWith(".json"))) != null) {
            Stream stream = Arrays.stream(files).map(file1 -> file1.getName()).map(string -> string.substring(0, string.length() - 5));
            stream.forEach(object -> suggestionsBuilder.suggest((String)object));
         }

         return suggestionsBuilder.buildFuture();
      };
   }

   private static <T> void performAction_0234(Setting<?> setting) {
      setting.setValue(setting.getDefaultValue());
   }
}
