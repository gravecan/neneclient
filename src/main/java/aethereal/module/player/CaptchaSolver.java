package aethereal.module.player;

import aethereal.core.Category;
import aethereal.core.CoreComponent036;
import aethereal.core.EventTarget;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.core.NeneClient;
import aethereal.event.BackendEvent;
import aethereal.event.EventComponent074;
import aethereal.util.UtilComponent252;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import net.minecraft.block.MapColor;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.map.MapState;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.world.World;

@ModuleRegister(name = "Captcha Solver", description = "Automatically solves captcha when logging into the FunTime server", category = Category.Player)
public class CaptchaSolver extends Module {
   private byte[] byteValueArrayField1;

   @EventTarget
   public void onEventComponent074_2156(EventComponent074 eventComponent074) {
      if (UtilComponent252.NestedType400.checkCondition_3408()
         && NeneClient.getInstance().getBackend().checkCondition_0598()
         && minecraft.crosshairTarget instanceof BlockHitResult blockHitResult) {
         BlockHitResult blockHitResult1 = blockHitResult;
         minecraft.world
            .getEntitiesByClass(
               ItemFrameEntity.class,
               new Box(blockHitResult1.getBlockPos()).expand(0.5),
               itemFrameEntity -> itemFrameEntity.getHeldItemStack().getItem() instanceof FilledMapItem
            )
            .stream()
            .findFirst()
            .ifPresent(this::performAction_2158);
      }
   }

   @EventTarget
   public void onBackendEvent_2155(BackendEvent backendEvent) {
      CoreComponent036 coreComponent036 = backendEvent.getPacket_0974();
      String string;
      if (backendEvent.checkCondition_0972()
         && "captcha".equals(coreComponent036.getStringField1_0656())
         && (string = coreComponent036.getSecurity_0658().computeValue_2474(coreComponent036.getPayload_0657(), "code")) != null) {
         minecraft.player.networkHandler.sendChatMessage(string);
      }
   }

   private void performAction_2158(ItemFrameEntity itemFrameEntity) {
      World world = itemFrameEntity.getEntityWorld();
      Direction direction = itemFrameEntity.getHorizontalFacing();
      boolean bl = direction.getAxis() == Axis.Z;
      boolean bl1 = direction == Direction.NORTH || direction == Direction.EAST;
      List list = world.getEntitiesByClass(
         ItemFrameEntity.class,
         itemFrameEntity.getBoundingBox().expand(16.0),
         itemFrameEntity2 -> itemFrameEntity2.getHorizontalFacing() == direction
            && itemFrameEntity2.getHeldItemStack().getItem() instanceof FilledMapItem
            && FilledMapItem.getMapState(itemFrameEntity2.getHeldItemStack(), world) != null
      );
      int k = Integer.MAX_VALUE;
      int l = Integer.MIN_VALUE;
      int m = Integer.MAX_VALUE;
      int n = Integer.MIN_VALUE;
      Iterator iterator = list.iterator();

      while (iterator.hasNext()) {
         BlockPos blockPos = ((ItemFrameEntity)iterator.next()).getAttachedBlockPos();
         int o = bl ? blockPos.getX() : blockPos.getZ();
         k = Math.min(k, o);
         l = Math.max(l, o);
         m = Math.min(m, blockPos.getY());
         n = Math.max(n, blockPos.getY());
      }

      int u = (l - k + 1) * 128;
      int v = (n - m + 1) * 128;
      BufferedImage bufferedImage = new BufferedImage(u, v, 2);
      Graphics2D graphics2D = bufferedImage.createGraphics();

      for (ItemFrameEntity itemFrameEntity1 : list) {
         ItemStack itemStack = itemFrameEntity1.getHeldItemStack();
         MapState mapState = FilledMapItem.getMapState(itemStack, world);
         if (mapState != null && mapState.colors != null && mapState.colors.length == 16384) {
            BufferedImage bufferedImage1 = new BufferedImage(128, 128, 2);

            for (int p = 0; p < mapState.colors.length; p++) {
               int q = mapState.colors[p] & 255;
               int r = q < 4 ? 0 : MapColor.getRenderColor(q);
               bufferedImage1.setRGB(p % 128, p / 128, r);
            }

            BlockPos blockPos1 = itemFrameEntity1.getAttachedBlockPos();
            int w = bl ? blockPos1.getX() : blockPos1.getZ();
            int i;
            int j;
            if (bl1) {
               i = l;
               j = w;
            } else {
               i = w;
               j = k;
            }

            int x = (i - j) * 128;
            int s = (n - blockPos1.getY()) * 128;
            int t = itemFrameEntity1.getRotation() & 3;
            AffineTransform affineTransform = AffineTransform.getTranslateInstance(x, s);
            affineTransform.rotate(Math.toRadians(t * 90.0), 64.0, 64.0);
            graphics2D.drawImage(bufferedImage1, affineTransform, (ImageObserver)null);
         }
      }

      graphics2D.dispose();
      this.performAction_2157(bufferedImage);
   }

   private void performAction_2157(BufferedImage bufferedImage) {
      try {
         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
         ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
         byte[] b = byteArrayOutputStream.toByteArray();
         if (!Arrays.equals(b, this.byteValueArrayField1)) {
            this.byteValueArrayField1 = b;
            NeneClient.getInstance().getBackend().performAction_0599(false, "captcha", "bytes", Base64.getEncoder().encodeToString(b));
         }
      } catch (Exception exception) {
      }
   }
}
