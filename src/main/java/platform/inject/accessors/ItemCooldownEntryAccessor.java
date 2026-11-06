package platform.inject.accessors;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "net/minecraft/entity/player/ItemCooldownManager$Entry")
public interface ItemCooldownEntryAccessor {
   @Accessor("comp_3084")
   int getEndTick();
}
