package platform.inject.accessors;

import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClickSlotC2SPacket.class)
public interface ClickSlotC2SPacketAccessor {
   @Accessor("comp_3842")
   int getSyncId();

   @Accessor("comp_3844")
   short getSlot();

   @Accessor("comp_3844")
   @Mutable
   void setSlot(short s);
}
