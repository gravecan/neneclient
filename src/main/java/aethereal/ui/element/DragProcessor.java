package aethereal.ui.element;

import aethereal.api.ApiAnnotation001;
import aethereal.config.ConfigComponent018;
import aethereal.config.ListProcessor;
import aethereal.config.SettingsPersistence;
import aethereal.core.EventBus;
import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.ObfuscatorRuntime;
import aethereal.event.ClickEvent;
import aethereal.event.DrawEvent;
import aethereal.lib.json.JsonComponent135;
import aethereal.lib.json.JsonComponent136;
import aethereal.render.Animation;
import aethereal.render.RenderComponent183;
import aethereal.render.RenderComponent186;
import aethereal.setting.Setting;
import aethereal.util.CursorUtil;
import aethereal.util.UtilComponent279;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.screen.ChatScreen;
import org.joml.Vector4f;

public class DragProcessor extends ListProcessor<ElementComponent210> implements MinecraftAccess {
   private final DragProcessor.NestedType383 nestedType383Field1 = new DragProcessor.NestedType383();
   private final DragProcessor.NestedType383 nestedType383Field2 = new DragProcessor.NestedType383();
   private ElementComponent210 elementComponent210Field3 = null;

   public DragProcessor() {
      EventBus.register(this);
   }

   @ApiAnnotation001
   @Override
   protected List<ElementComponent210> computeValue_0041(String string) throws Exception {
      JsonComponent135 jsonComponent135 = new JsonComponent135(string);

      for (int i = 0; i < jsonComponent135.getDelegate_1250(); i++) {
         try {
            JsonComponent136 jsonComponent136 = jsonComponent135.computeValue_1252(i);
            String string1 = jsonComponent136.computeValue_1260("name");

            for (ElementComponent210 elementComponent210 : this.getEntries()) {
               if (elementComponent210.getName_3019().equals(string1)) {
                  float f = jsonComponent136.computeValue_1265("x");
                  float g = jsonComponent136.computeValue_1265("y");
                  float h = jsonComponent136.computeValue_1265("width");
                  float j = jsonComponent136.computeValue_1265("height");
                  if (Float.isFinite(f) && Math.abs(f) <= 20000.0F) {
                     elementComponent210.performAction_3015(f);
                  }

                  if (Float.isFinite(g) && Math.abs(g) <= 20000.0F) {
                     elementComponent210.performAction_3003(g);
                  }

                  if (Float.isFinite(h) && h >= 0.0F && h <= 5000.0F) {
                     elementComponent210.performAction_3006(h);
                  }

                  if (Float.isFinite(j) && j >= 0.0F && j <= 5000.0F) {
                     elementComponent210.performAction_3018(j);
                  }

                  JsonComponent136 jsonComponent1361 = jsonComponent136.computeValue_1268("settings");
                  if (jsonComponent1361 != null && elementComponent210.getWidgetComponent249Field1_3013() != null) {
                     for (Setting setting : elementComponent210.getWidgetComponent249Field1_3013().getListField1_3369()) {
                        try {
                           if (setting.shouldPersistChanges() && jsonComponent1361.checkCondition_1258(setting.getName())) {
                              ConfigComponent018.performAction_0363(setting, jsonComponent1361.computeValue_1269(setting.getName()));
                           }
                        } catch (RuntimeException runtimeException) {
                           System.err.println("[XenoClient] Ignoring invalid HUD setting '" + setting.getName() + "'.");
                        }
                     }
                  }
                  break;
               }
            }
         } catch (RuntimeException runtimeException1) {
            System.err.println("[XenoClient] Ignoring malformed HUD entry at index " + i);
         }
      }

      return new ArrayList<>(this.getEntries());
   }

   @ApiAnnotation001
   @Override
   protected String computeValue_0042(List<ElementComponent210> list) throws Exception {
      JsonComponent135 jsonComponent135 = new JsonComponent135();

      for (ElementComponent210 elementComponent210 : list) {
         JsonComponent136 jsonComponent136 = new JsonComponent136();
         if (!(elementComponent210 instanceof ElementComponent210 elementComponent2101)) {
            throw new ClassCastException();
         }

         jsonComponent136.computeValue_1272("name", elementComponent2101.getName_3019());
         jsonComponent136.computeValue_1270("x", elementComponent2101.getFloatValueField2_3010());
         jsonComponent136.computeValue_1270("y", elementComponent2101.getFloatValueField3_3011());
         jsonComponent136.computeValue_1270("width", elementComponent2101.getWidth_3005());
         jsonComponent136.computeValue_1270("height", elementComponent2101.getHeight_3002());
         JsonComponent136 jsonComponent1361 = new JsonComponent136();
         if (elementComponent2101.getWidgetComponent249Field1_3013() != null) {
            for (Setting setting : elementComponent2101.getWidgetComponent249Field1_3013().getListField1_3369()) {
               if (!(setting instanceof Setting setting1)) {
                  throw new ClassCastException();
               }

               if (setting1.shouldPersistChanges()) {
                  jsonComponent1361.computeValue_1272(setting1.getName(), ConfigComponent018.computeValue_0362(setting1));
               }
            }
         }

         jsonComponent136.computeValue_1272("settings", jsonComponent1361);
         jsonComponent135.computeValue_1253(jsonComponent136);
      }

      return jsonComponent135.computeValue_1254(2);
   }

   public DragProcessor.NestedType383 getNestedType383Field1_2925() {
      return this.nestedType383Field1;
   }

   public DragProcessor.NestedType383 getNestedType383Field2_2920() {
      return this.nestedType383Field2;
   }

   public ElementComponent210 getElementComponent210Field3_2926() {
      return this.elementComponent210Field3;
   }

   @Override
   protected String computeValue_0040() {
      return "drag.json";
   }

   @Override
   public void performAction_0357() {
      SettingsPersistence.performAction_0539();
   }

   @EventTarget
   public void onClickEvent_2921(ClickEvent clickEvent) {
      if (minecraft.currentScreen instanceof ChatScreen) {
         if (clickEvent.checkCondition_0983() && clickEvent.getButton_0980() == 0) {
            boolean bl = false;

            for (ElementComponent210 elementComponent210 : this.getEntries()) {
               if (elementComponent210.getWidgetComponent249Field1_3013() != null && elementComponent210.getWidgetComponent249Field1_3013().isFlagField6_3365()
                  )
                {
                  Vector4f vector4f = elementComponent210.getWidgetComponent249Field1_3013().getVector4fField9_3364();
                  if (UtilComponent279.checkCondition_3576(
                     clickEvent.getMouseX_0979(), clickEvent.getMouseY_0982(), vector4f.x, vector4f.y, vector4f.z, vector4f.w
                  )) {
                     bl = true;
                     break;
                  }
               }
            }

            if (!bl) {
               for (ElementComponent210 elementComponent2102 : this.getEntries()) {
                  if (elementComponent2102.getWidgetComponent249Field1_3013() != null
                     && elementComponent2102.getWidgetComponent249Field1_3013().isFlagField6_3365()
                     && !UtilComponent279.checkCondition_3576(
                        clickEvent.getMouseX_0979(),
                        clickEvent.getMouseY_0982(),
                        elementComponent2102.computeValue_3007(),
                        elementComponent2102.computeValue_3009(),
                        elementComponent2102.getWidth_3005(),
                        elementComponent2102.getHeight_3002()
                     )) {
                     elementComponent2102.getWidgetComponent249Field1_3013().setFlagField6_3368(false);
                  }
               }
            }

            for (ElementComponent210 elementComponent2103 : this.getEntries()) {
               if (elementComponent2103.getIntegerField6_3020() != 2
                  && UtilComponent279.checkCondition_3576(
                     clickEvent.getMouseX_0979(),
                     clickEvent.getMouseY_0982(),
                     elementComponent2103.computeValue_3007(),
                     elementComponent2103.computeValue_3009(),
                     elementComponent2103.getWidth_3005(),
                     elementComponent2103.getHeight_3002()
                  )) {
                  CursorUtil.performAction_3422(CursorUtil.State404.HAND);
                  this.elementComponent210Field3 = elementComponent2103;
                  this.elementComponent210Field3.setDoubleValueField4_3008(clickEvent.getMouseX_0979() - elementComponent2103.computeValue_3007());
                  this.elementComponent210Field3.setDoubleValueField5_3014(clickEvent.getMouseY_0982() - elementComponent2103.computeValue_3009());
                  break;
               }
            }
         } else if (clickEvent.checkCondition_0984() && clickEvent.getButton_0980() == 0) {
            this.performAction_2923();
         } else if (clickEvent.checkCondition_0981() && this.elementComponent210Field3 != null && clickEvent.getButton_0980() == 0) {
            this.performAction_2927(
               (float)(clickEvent.getMouseX_0979() - this.elementComponent210Field3.getDoubleValueField4_3012()),
               (float)(clickEvent.getMouseY_0982() - this.elementComponent210Field3.getDoubleValueField5_3017()),
               this.elementComponent210Field3
            );
         }

         for (ElementComponent210 elementComponent2101 : this.getEntries()) {
            if (clickEvent.getButton_0980() == 1 && clickEvent.checkCondition_0983()) {
               if (UtilComponent279.checkCondition_3576(
                     clickEvent.getMouseX_0979(),
                     clickEvent.getMouseY_0982(),
                     elementComponent2101.computeValue_3007(),
                     elementComponent2101.computeValue_3009(),
                     elementComponent2101.getWidth_3005(),
                     elementComponent2101.getHeight_3002()
                  )
                  && elementComponent2101.getWidgetComponent249Field1_3013() != null) {
                  elementComponent2101.getWidgetComponent249Field1_3013()
                     .setFlagField6_3368(!elementComponent2101.getWidgetComponent249Field1_3013().isFlagField6_3365());
               }
            } else if (clickEvent.getButton_0980() == 0
               && clickEvent.checkCondition_0984()
               && elementComponent2101.getWidgetComponent249Field1_3013() != null
               && elementComponent2101.getWidgetComponent249Field1_3013().isFlagField6_3365()) {
               for (SettingElement settingElement : elementComponent2101.getWidgetComponent249Field1_3013().getListField2_3361()) {
                  settingElement.checkCondition_2915(clickEvent.getMouseX_0979(), clickEvent.getMouseY_0982(), clickEvent.getButton_0980());
               }

               this.performAction_0357();
            }
         }
      }
   }

   @EventTarget(priority = 4)
   public void onDrawEvent_2922(DrawEvent drawEvent) {
      if (drawEvent.checkCondition_1001()) {
         if (minecraft.currentScreen instanceof ChatScreen) {
            if (this.elementComponent210Field3 == null && !this.nestedType383Field1.checkCondition_2934() && !this.nestedType383Field2.checkCondition_2934()) {
               this.performAction_2923();
            }

            this.nestedType383Field1.performAction_2937(this.elementComponent210Field3 != null, drawEvent.getTickNeneClient_0997());
            this.nestedType383Field2.performAction_2937(this.elementComponent210Field3 != null, drawEvent.getTickNeneClient_0997());
            if (!this.nestedType383Field1.checkCondition_2934() && !this.nestedType383Field2.checkCondition_2934()) {
               return;
            }

            this.onDrawEvent_2924(drawEvent);
            return;
         }

         for (ElementComponent210 elementComponent210 : this.getEntries()) {
            if (elementComponent210.getWidgetComponent249Field1_3013() != null) {
               elementComponent210.getWidgetComponent249Field1_3013().setFlagField6_3368(false);
            }
         }

         if (this.elementComponent210Field3 != null) {
            this.performAction_2923();
         }
      }
   }

   private void performAction_2927(float f, float g, ElementComponent210 elementComponent210) {
      int i = elementComponent210.getIntegerField6_3020();
      if (i == 2) {
         this.nestedType383Field1.performAction_2936(null);
         this.nestedType383Field2.performAction_2936(null);
      } else {
         boolean bl = i == 1;
         if (bl) {
            f = elementComponent210.computeValue_3007();
         }

         float h = UtilComponent279.computeValue_3577(
            f,
            0.0F,
            minecraft.getWindow().getFramebufferWidth() / minecraft.getWindow().calculateScaleFactor(2, minecraft.forcesUnicodeFont())
               - elementComponent210.getWidth_3005()
         );
         float j = UtilComponent279.computeValue_3577(
            g,
            0.0F,
            minecraft.getWindow().getFramebufferHeight() / minecraft.getWindow().calculateScaleFactor(2, minecraft.forcesUnicodeFont())
               - elementComponent210.getHeight_3002()
         );
         if (!bl) {
            h = this.computeValue_2928(DragProcessor.State382.state382Field1, h, elementComponent210);
         } else {
            this.nestedType383Field1.performAction_2936(null);
         }

         float k = this.computeValue_2928(DragProcessor.State382.state382Field2, j, elementComponent210);
         elementComponent210.performAction_3015(
            UtilComponent279.computeValue_3577(
               h,
               0.0F,
               minecraft.getWindow().getFramebufferWidth() / minecraft.getWindow().calculateScaleFactor(2, minecraft.forcesUnicodeFont())
                  - elementComponent210.getWidth_3005()
            )
         );
         elementComponent210.performAction_3003(
            UtilComponent279.computeValue_3577(
               k,
               0.0F,
               minecraft.getWindow().getFramebufferHeight() / minecraft.getWindow().calculateScaleFactor(2, minecraft.forcesUnicodeFont())
                  - elementComponent210.getHeight_3002()
            )
         );
      }
   }

   private float computeValue_2928(DragProcessor.State382 state382, float f, ElementComponent210 elementComponent210) {
      float h = state382.computeValue_2932(elementComponent210);
      float[] i = new float[]{f, f + h / 2.0F, f + h};
      DragProcessor.NestedType383 nestedType383 = state382 == DragProcessor.State382.state382Field1 ? this.nestedType383Field1 : this.nestedType383Field2;
      float j = 25.0F;
      Float float1 = null;
      float k = f;

      for (float l : this.computeValue_2929(state382, elementComponent210)) {
         for (int m = 0; m < i.length; m++) {
            float n = Math.abs(i[m] - l);
            if (n < j) {
               j = n;
               float1 = l;
               float g;
               if (m == 0) {
                  g = 0.0F;
               } else {
                  g = m == 1 ? h / 2.0F : h;
               }

               k = l - g;
            }
         }
      }

      if (float1 != null && j < 5.0F) {
         f = k;
         nestedType383.performAction_2936(float1);
      } else {
         nestedType383.performAction_2936(null);
      }

      return f;
   }

   private List<Float> computeValue_2929(DragProcessor.State382 state382, ElementComponent210 elementComponent210) {
      ArrayList arrayList = new ArrayList();
      arrayList.add(0.0F);
      arrayList.add(state382.computeValue_2930() / 2.0F);
      arrayList.add(state382.computeValue_2930());

      for (ElementComponent210 elementComponent2101 : this.getEntries()) {
         if (elementComponent2101 != elementComponent210 && (elementComponent2101.getWidth_3005() != 0.0F || elementComponent2101.getHeight_3002() != 0.0F)) {
            float f = state382.computeValue_2931(elementComponent2101);
            float g = state382.computeValue_2932(elementComponent2101);
            arrayList.add(f);
            arrayList.add(f + g / 2.0F);
            arrayList.add(f + g);
         }
      }

      return arrayList;
   }

   private void onDrawEvent_2924(DrawEvent drawEvent) {
      if (this.nestedType383Field1.checkCondition_2934()) {
         drawEvent.getRenderComponent184Field1_0998()
            .performAction_2574(
               drawEvent.getContext_1000(),
               this.nestedType383Field1.getFloatField2_2935() - 0.5F,
               0.0F,
               0.5F,
               minecraft.getWindow().getFramebufferHeight() / minecraft.getWindow().calculateScaleFactor(2, minecraft.forcesUnicodeFont()),
               RenderComponent183.computeValue_2558(
                  255, 255, 255, (int)(this.nestedType383Field1.getAnimationField1_2938().getFloatValueField4_2530() * 200.0F)
               )
            );
      }

      if (this.nestedType383Field2.checkCondition_2934()) {
         drawEvent.getRenderComponent184Field1_0998()
            .performAction_2574(
               drawEvent.getContext_1000(),
               0.0F,
               this.nestedType383Field2.getFloatField2_2935() - 0.5F,
               minecraft.getWindow().getFramebufferWidth() / minecraft.getWindow().calculateScaleFactor(2, minecraft.forcesUnicodeFont()),
               0.5F,
               RenderComponent183.computeValue_2558(
                  255, 255, 255, (int)(this.nestedType383Field2.getAnimationField1_2938().getFloatValueField4_2530() * 200.0F)
               )
            );
      }
   }

   private void performAction_2923() {
      CursorUtil.performAction_3422(CursorUtil.State404.DEFAULT);
      this.elementComponent210Field3 = null;
      this.nestedType383Field2.performAction_2936(null);
      this.nestedType383Field1.performAction_2936(null);
      this.performAction_0357();
   }

   static {
      ObfuscatorRuntime.registerClass(DragProcessor.class, 26);
   }

   static class NestedType383 {
      private final Animation animationField1 = new Animation();
      private Float floatField2 = null;
      private boolean flagField3 = false;

      public Animation getAnimationField1_2938() {
         return this.animationField1;
      }

      public Float getFloatField2_2935() {
         return this.floatField2;
      }

      public boolean isFlagField3_2933() {
         return this.flagField3;
      }

      void performAction_2936(Float float_) {
         if (float_ == null) {
            this.flagField3 = false;
         } else if (!float_.equals(this.floatField2)) {
            this.floatField2 = float_;
            this.flagField3 = true;
         }
      }

      void performAction_2937(boolean bl, float f) {
         if (this.floatField2 != null) {
            boolean bl1 = bl && this.flagField3;
            this.animationField1.performAction_2528(bl && this.flagField3);
            this.animationField1.performAction_2535(0.0F, 1.0F, 0.3F, RenderComponent186.contract372Field9, f);
            if (!bl1 && this.animationField1.getFloatValueField4_2530() <= 0.0F) {
               this.floatField2 = null;
            }
         }
      }

      boolean checkCondition_2934() {
         return this.floatField2 != null && this.animationField1.getFloatValueField4_2530() > 0.0F;
      }
   }

   enum State382 {
      state382Field1,
      state382Field2;

      float computeValue_2930() {
         return this == state382Field1
            ? MinecraftAccess.minecraft.getWindow().getFramebufferWidth()
               / MinecraftAccess.minecraft.getWindow().calculateScaleFactor(2, MinecraftAccess.minecraft.forcesUnicodeFont())
            : MinecraftAccess.minecraft.getWindow().getFramebufferHeight()
               / MinecraftAccess.minecraft.getWindow().calculateScaleFactor(2, MinecraftAccess.minecraft.forcesUnicodeFont());
      }

      float computeValue_2931(ElementComponent210 elementComponent210) {
         return this == state382Field1 ? elementComponent210.computeValue_3007() : elementComponent210.computeValue_3009();
      }

      float computeValue_2932(ElementComponent210 elementComponent210) {
         return this == state382Field1 ? elementComponent210.getWidth_3005() : elementComponent210.getHeight_3002();
      }
   }
}
