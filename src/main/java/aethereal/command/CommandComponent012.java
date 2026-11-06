package aethereal.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.CommandContextBuilder;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;

public class CommandComponent012 extends LiteralCommandNode<CommandSource> {
   public CommandComponent012(LiteralCommandNode<CommandSource> literalCommandNode) {
      super(
         literalCommandNode.getLiteral(),
         literalCommandNode.getCommand(),
         literalCommandNode.getRequirement(),
         literalCommandNode.getRedirect(),
         literalCommandNode.getRedirectModifier(),
         literalCommandNode.isFork()
      );
      literalCommandNode.getChildren().forEach(this::addChild);
   }

   public void parse(StringReader stringReader, CommandContextBuilder<CommandSource> commandContextBuilder) throws CommandSyntaxException {
      int i = stringReader.getCursor();
      String string = this.getLiteral();

      while (stringReader.canRead() && stringReader.peek() != ' ') {
         stringReader.skip();
      }

      String string1 = stringReader.getString().substring(i, stringReader.getCursor());
      boolean bl = string1.equalsIgnoreCase(string);
      boolean bl1 = !stringReader.canRead() && string.toLowerCase(Locale.ROOT).startsWith(string1.toLowerCase(Locale.ROOT));
      if (!bl && !bl1) {
         stringReader.setCursor(i);
         throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.literalIncorrect().createWithContext(stringReader, string);
      }

      commandContextBuilder.withNode(this, StringRange.between(i, stringReader.getCursor()));
   }

   public CompletableFuture<Suggestions> listSuggestions(CommandContext<CommandSource> commandContext, SuggestionsBuilder suggestionsBuilder) {
      String string = this.getLiteral();
      String string1 = suggestionsBuilder.getRemaining();
      if (string.toLowerCase(Locale.ROOT).startsWith(string1.toLowerCase(Locale.ROOT))) {
         String string2 = string1 + string.substring(string1.length());
         return suggestionsBuilder.suggest(string2).buildFuture();
      } else {
         return Suggestions.empty();
      }
   }

   public Collection<? extends CommandNode<CommandSource>> getRelevantNodes(StringReader stringReader) {
      return computeValue_0198(this, stringReader);
   }

   static Collection<? extends CommandNode<CommandSource>> computeValue_0198(CommandNode<CommandSource> commandNode, StringReader stringReader) {
      int i = stringReader.getCursor();

      while (stringReader.canRead() && stringReader.peek() != ' ') {
         stringReader.skip();
      }

      String string = stringReader.getString().substring(i, stringReader.getCursor());
      stringReader.setCursor(i);

      for (CommandNode commandNode1 : commandNode.getChildren()) {
         if (commandNode1 instanceof LiteralCommandNode && commandNode1.getName().equalsIgnoreCase(string)) {
            return Collections.singleton(commandNode1);
         }
      }

      return commandNode.getChildren();
   }

   public static LiteralArgumentBuilder<CommandSource> computeValue_0199(String string) {
      return new LiteralArgumentBuilder<CommandSource>(string) {
         public LiteralCommandNode<CommandSource> build() {
            return new CommandComponent012(super.build());
         }
      };
   }

   public static final class NestedType294 extends RootCommandNode<CommandSource> {
      public Collection<? extends CommandNode<CommandSource>> getRelevantNodes(StringReader stringReader) {
         return CommandComponent012.computeValue_0198(this, stringReader);
      }
   }
}
