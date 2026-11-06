package aethereal.notification;

import aethereal.api.ApiAnnotation001;
import aethereal.config.PersistentProcessor;
import aethereal.core.EventBus;
import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.ObfuscatorRuntime;
import aethereal.event.DrawEvent;
import aethereal.event.EventComponent074;
import aethereal.render.RenderComponent186;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.screen.ChatScreen;

public class NotificationComponent182 extends PersistentProcessor implements MinecraftAccess {
   private final NotificationComponent181 notificationComponent181Field1 = new NotificationComponent181("o", "Example of notification display", 0);
   private final List<NotificationComponent181> listField2 = new ArrayList<>();

   public NotificationComponent182() {
      EventBus.register(this);
   }

   @ApiAnnotation001
   @Override
   public void load() {
   }

   public NotificationComponent181 getNotificationComponent181Field1_2521() {
      return this.notificationComponent181Field1;
   }

   public List<NotificationComponent181> getListField2_2520() {
      return this.listField2;
   }

   @Override
   public void save() {
   }

   @EventTarget
   public void onDrawEvent_2518(DrawEvent drawEvent) {
      if (drawEvent.checkCondition_1001() && !this.getListField2_2520().isEmpty()) {
         for (NotificationComponent181 notificationComponent181 : this.getListField2_2520()) {
            notificationComponent181.getAnimationField1_2515()
               .performAction_2535(0.0F, 1.0F, 0.3F, RenderComponent186.contract372Field7, drawEvent.getTickNeneClient_0997());
         }
      }
   }

   @EventTarget
   public void onEventComponent074_2519(EventComponent074 eventComponent074) {
      ArrayList arrayList = new ArrayList<>(this.getListField2_2520());
      arrayList.remove(this.notificationComponent181Field1);
      boolean bl = minecraft.currentScreen instanceof ChatScreen && arrayList.isEmpty();
      if (!this.getListField2_2520().contains(this.notificationComponent181Field1)) {
         this.getListField2_2520().add(this.notificationComponent181Field1);
      }

      for (NotificationComponent181 notificationComponent181 : new ArrayList<>(this.getListField2_2520())) {
         if (notificationComponent181 == this.notificationComponent181Field1) {
            notificationComponent181.getAnimationField1_2515().performAction_2528(bl);
            if (!bl && notificationComponent181.getAnimationField1_2515().getFloatValueField4_2530() == 0.0F) {
               this.getListField2_2520().remove(this.notificationComponent181Field1);
            }
         } else {
            boolean bl1 = notificationComponent181.getUtilComponent268Field2_2512().checkCondition_3522(notificationComponent181.getTime_2513() - 100);
            notificationComponent181.getAnimationField1_2515().performAction_2528(!bl1);
            if (bl1 && notificationComponent181.getAnimationField1_2515().getFloatValueField4_2530() == 0.0F) {
               this.getListField2_2520().remove(notificationComponent181);
            }
         }
      }
   }

   public void performAction_2523(NotificationComponent181 notificationComponent181) {
      int i = notificationComponent181.getTime_2513();
      notificationComponent181.setTime_2511(
         i
            + (int)this.getListField2_2520()
                  .stream()
                  .filter(notificationComponent1811 -> notificationComponent1811.getTime_2513() >= i)
                  .filter(notificationComponent1811 -> (notificationComponent1811.getTime_2513() - i) % 50 == 0)
                  .count()
               * 50
      );
      this.getListField2_2520().add(notificationComponent181);
   }

   static {
      ObfuscatorRuntime.registerClass(NotificationComponent182.class, 34);
   }
}
