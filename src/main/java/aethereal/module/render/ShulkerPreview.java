package aethereal.module.render;

import aethereal.core.Category;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.ContainerEvent;
import aethereal.event.DrawEvent;
import aethereal.render.RenderComponent183;
import aethereal.util.UtilComponent284;
import java.util.List;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import org.joml.Matrix3x2fStack;
import org.joml.Vector2f;
import platform.inject.accessors.HandledScreenAccessor;

@ModuleRegister(
   name = "Shulker Preview",
   description = "Shows the contents of shulkers in the inventory by holding ALT (and on the ground, without holding it if the anti-cheat is weak)",
   category = Category.Render
)
public class ShulkerPreview extends Module {
   private final Vector2f vector2fField1 = new Vector2f(175.0F, 70.0F);

   @EventTarget
   public void onContainerEvent_2298(ContainerEvent containerEvent) {
      Slot slot;
      if (containerEvent.getPhase_0988() == ContainerEvent.Phase.POST
         && (slot = ((HandledScreenAccessor)containerEvent.getScreen_0992()).getFocusedSlot()) != null
         && slot.getStack() != null
         && slot.hasStack()) {
         ItemStack itemStack = slot.getStack();
         if (itemStack.get(DataComponentTypes.CONTAINER) != null) {
            BlockItem blockItem = (BlockItem)itemStack.getItem();
            if (blockItem instanceof BlockItem blockItem1 && blockItem1.getBlock() instanceof ShulkerBoxBlock) {
               this.performAction_2301(
                  containerEvent.getContext_0991(),
                  itemStack,
                  ((ContainerComponent)itemStack.get(DataComponentTypes.CONTAINER)).stream().toList(),
                  containerEvent.getMouseX_0986() + 8,
                  containerEvent.getMouseY_0994() - this.vector2fField1.y() - 16.0F,
                  1.0F,
                  true
               );
            }
         }
      }
   }

   @EventTarget
   public void onDrawEvent_2299(DrawEvent drawEvent) {
      if (drawEvent.checkCondition_1001()) {
         for (ItemEntity itemEntity : minecraft.world.getEntitiesByClass(ItemEntity.class, minecraft.player.getBoundingBox().expand(64.0), itemEntity1 -> true)) {
            BlockItem blockItem = (BlockItem)itemEntity.getStack().getItem();
            ItemStack itemStack;
            if (blockItem instanceof BlockItem blockItem1
               && blockItem1.getBlock() instanceof ShulkerBoxBlock
               && (itemStack = itemEntity.getStack()) != null
               && !itemStack.isEmpty()) {
               Vector2f vector2f = UtilComponent284.computeValue_3615(
                  itemEntity.lastX + (itemEntity.getX() - itemEntity.lastX) * minecraft.getRenderTickCounter().getTickProgress(false),
                  itemEntity.lastY + (itemEntity.getY() - itemEntity.lastY) * minecraft.getRenderTickCounter().getTickProgress(false) + 0.5,
                  itemEntity.lastZ + (itemEntity.getZ() - itemEntity.lastZ) * minecraft.getRenderTickCounter().getTickProgress(false)
               );
               ContainerComponent containerComponent = (ContainerComponent)itemStack.get(DataComponentTypes.CONTAINER);
               if (containerComponent != null && !containerComponent.stream().toList().isEmpty()) {
                  this.performAction_2301(
                     drawEvent.getContext_1000(),
                     itemStack,
                     containerComponent.stream().toList(),
                     vector2f.x() - this.vector2fField1.x() * 0.5F / 2.0F,
                     vector2f.y() - this.vector2fField1.y() * 0.5F,
                     0.5F,
                     false
                  );
               }
            }
         }
      }
   }

   private void performAction_2301(DrawContext drawContext, ItemStack itemStack, List<ItemStack> list, float f, float g, float h, boolean bl) {
      Matrix3x2fStack matrix3x2fStack = drawContext.getMatrices();
      matrix3x2fStack.pushMatrix();
      matrix3x2fStack.translate(f, g);
      matrix3x2fStack.scale(h, h);
      drawContext.drawTexture(
         RenderPipelines.GUI_TEXTURED,
         Identifier.of("neneclient", "pictures/minecraft/3x9.png"),
         0,
         0,
         0.0F,
         0.0F,
         (int)this.vector2fField1.x(),
         (int)this.vector2fField1.y(),
         256,
         256,
         RenderComponent183.computeValue_2556(((BlockItem)itemStack.getItem()).getBlock().getDefaultMapColor().color, 255)
      );

      for (int i = 0; i < Math.min(list.size(), 27); i++) {
         ItemStack itemStack1 = (ItemStack)list.get(i);
         if (itemStack1 != null && !itemStack1.isEmpty()) {
            int j = 9 + i % 9 * 18 + 1;
            int k = 9 + i / 9 * 18 + 1;
            NeneClient.getInstance().getProcessors().getRenderComponent185Field4_0678().performAction_2592(drawContext, itemStack1, j, k, 0, 1.0F, 0.75F, bl);
         }
      }

      matrix3x2fStack.popMatrix();
   }
}
