package aethereal.command;

import aethereal.core.EventBus;
import aethereal.core.MinecraftAccess;
import aethereal.util.KeyUtil;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.command.CommandSource;

public abstract class Command implements MinecraftAccess {
   protected final String stringField1 = this.getClass().getAnnotation(CommandRegister.class).name();

   public abstract void performAction_0094(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder);

   public final void performAction_0165(CommandDispatcher<CommandSource> commandDispatcher) {
      LiteralArgumentBuilder literalArgumentBuilder = CommandComponent012.computeValue_0199(this.stringField1);
      this.performAction_0094(literalArgumentBuilder);
      commandDispatcher.register(literalArgumentBuilder);
      EventBus.register(this);
   }

   protected LiteralArgumentBuilder<CommandSource> computeValue_0173(String string) {
      return CommandComponent012.computeValue_0199(string);
   }

   protected RequiredArgumentBuilder<CommandSource, String> computeValue_0174(String string) {
      return RequiredArgumentBuilder.argument(string, StringArgumentType.string());
   }

   protected RequiredArgumentBuilder<CommandSource, String> computeValue_0161(String string) {
      return RequiredArgumentBuilder.argument(string, StringArgumentType.greedyString());
   }

   protected RequiredArgumentBuilder<CommandSource, String> computeValue_0163(String string) {
      return RequiredArgumentBuilder.argument(string, stringReader -> {
         int i = stringReader.getCursor();

         while (stringReader.canRead() && stringReader.peek() != ' ') {
            stringReader.skip();
         }

         return stringReader.getString().substring(i, stringReader.getCursor());
      });
   }

   protected RequiredArgumentBuilder<CommandSource, Integer> computeValue_0178(String string) {
      return RequiredArgumentBuilder.argument(string, IntegerArgumentType.integer());
   }

   protected RequiredArgumentBuilder<CommandSource, Float> computeValue_0162(String string) {
      return RequiredArgumentBuilder.argument(string, FloatArgumentType.floatArg());
   }

   protected String computeValue_0170(CommandContext<CommandSource> commandContext, String string) {
      return StringArgumentType.getString(commandContext, string);
   }

   protected int computeValue_0169(CommandContext<CommandSource> commandContext, String string) {
      return IntegerArgumentType.getInteger(commandContext, string);
   }

   protected float computeValue_0168(CommandContext<CommandSource> commandContext, String string) {
      return FloatArgumentType.getFloat(commandContext, string);
   }

   protected SuggestionProvider<CommandSource> computeValue_0164() {
      return (commandContext, suggestionsBuilder) -> {
         if (minecraft.player.networkHandler == null) {
            return suggestionsBuilder.buildFuture();
         }

         Stream stream = minecraft.player
            .networkHandler
            .getPlayerList()
            .stream()
            .map(playerListEntry -> playerListEntry.getProfile().name())
            .filter(
               string -> string != null
                  && string.toLowerCase().startsWith(suggestionsBuilder.getRemainingLowerCase() == null ? "" : suggestionsBuilder.getRemainingLowerCase())
            );
         stream.forEach(string -> suggestionsBuilder.suggest(string));
         return suggestionsBuilder.buildFuture();
      };
   }

   protected SuggestionProvider<CommandSource> computeValue_0159() {
      return (commandContext, suggestionsBuilder) -> {
         for (KeyUtil keyUtil : KeyUtil.values()) {
            if (keyUtil != KeyUtil.UNKNOWN) {
               suggestionsBuilder.suggest(keyUtil.name());
            }
         }

         return suggestionsBuilder.buildFuture();
      };
   }

   protected <T> SuggestionProvider<CommandSource> computeValue_0175(Supplier<Collection<T>> supplier, Function<T, String> function) {
      return (commandContext, suggestionsBuilder) -> {
         String string = suggestionsBuilder.getRemainingLowerCase() == null ? "" : suggestionsBuilder.getRemainingLowerCase();
         Iterator iterator = ((Collection)supplier.get()).iterator();

         while (iterator.hasNext()) {
            String string1 = (String)function.apply(iterator.next());
            if (string1 != null && string1.toLowerCase().startsWith(string)) {
               suggestionsBuilder.suggest(string1);
            }
         }

         return suggestionsBuilder.buildFuture();
      };
   }
}
