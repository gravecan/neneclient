package aethereal.notification;

import aethereal.render.Animation;
import aethereal.util.UtilComponent268;
import net.minecraft.item.ItemStack;

public class NotificationComponent181 {
   private final Animation animationField1 = new Animation();
   private final UtilComponent268 utilComponent268Field2 = new UtilComponent268();
   private final Object message;
   private final Object symbol;
   private final int color;
   private int time;

   public void setTime_2511(int i) {
      this.time = i;
   }

   public Animation getAnimationField1_2515() {
      return this.animationField1;
   }

   public UtilComponent268 getUtilComponent268Field2_2512() {
      return this.utilComponent268Field2;
   }

   public Object getMessage_2514() {
      return this.message;
   }

   public Object getSymbol_2510() {
      return this.symbol;
   }

   public int getColor_2516() {
      return this.color;
   }

   public int getTime_2513() {
      return this.time;
   }

   public NotificationComponent181(Object object, int i, Object object1, int j) {
      if (!(object instanceof String) && !(object instanceof ItemStack)) {
         throw new IllegalArgumentException("Icon must be either String or ItemStack");
      }

      this.symbol = object;
      this.color = i;
      this.message = object1;
      this.time = j;
   }

   public NotificationComponent181(Object object, Object object1, int i) {
      this(object, -1, object1, i);
   }
}
