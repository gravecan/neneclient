package aethereal.command;

import aethereal.discord.DiscordComponent056;
import aethereal.discord.RpcErrorCode;

public class CommandComponent013 extends DiscordComponent056 {
   private final RpcErrorCode errorCode;

   public RpcErrorCode getErrorCode_0201() {
      return this.errorCode;
   }

   public CommandComponent013(RpcErrorCode rpcErrorCode, String string) {
      super("RPC error " + rpcErrorCode.getCode_0722() + " (" + rpcErrorCode.name() + "): " + string);
      this.errorCode = rpcErrorCode;
   }
}
