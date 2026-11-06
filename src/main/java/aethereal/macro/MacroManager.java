package aethereal.macro;

import aethereal.api.ApiAnnotation001;
import aethereal.config.ListProcessor;
import aethereal.core.EventTarget;
import aethereal.core.NeneClient;
import aethereal.core.ObfuscatorRuntime;
import aethereal.event.EventComponent093;
import aethereal.lib.json.JsonComponent135;
import aethereal.lib.json.JsonComponent136;
import aethereal.util.KeyUtil;
import java.util.ArrayList;
import java.util.List;

public class MacroManager extends ListProcessor<Macro> {
   @ApiAnnotation001
   @Override
   protected List<Macro> computeValue_0041(String string) throws Exception {
      JsonComponent135 jsonComponent135 = new JsonComponent135(string);
      ArrayList arrayList = new ArrayList();

      for (int i = 0; i < jsonComponent135.getDelegate_1250(); i++) {
         JsonComponent136 jsonComponent136 = jsonComponent135.computeValue_1252(i);
         arrayList.add(new Macro(jsonComponent136.computeValue_1260("key"), jsonComponent136.computeValue_1260("command")));
      }

      return arrayList;
   }

   @ApiAnnotation001
   @Override
   protected String computeValue_0042(List<Macro> list) throws Exception {
      JsonComponent135 jsonComponent135 = new JsonComponent135();

      for (Macro macro : list) {
         JsonComponent136 jsonComponent136 = new JsonComponent136();
         if (!(macro instanceof Macro macro1)) {
            throw new ClassCastException();
         }

         jsonComponent136.computeValue_1272("key", macro1.getKey_1445());
         jsonComponent136.computeValue_1272("command", macro1.getCommand_1446());
         jsonComponent135.computeValue_1253(jsonComponent136);
      }

      return jsonComponent135.computeValue_1254(2);
   }

   @Override
   protected String computeValue_0040() {
      return "macros.json";
   }

   @EventTarget
   public void onEventComponent093_1450(EventComponent093 eventComponent093) {
      if (eventComponent093.getAction_1070() == 1 && minecraft.currentScreen == null) {
         for (Macro macro : NeneClient.getInstance().getProcessors().getMacros().getEntries()) {
            if (KeyUtil.computeValue_3475(eventComponent093.getKey_1068()) == KeyUtil.computeValue_3476(macro.getKey_1445())) {
               minecraft.player.networkHandler.sendChatMessage(macro.getCommand_1446());
            }
         }
      }
   }

   public List<Macro> computeValue_1451() {
      return new ArrayList<>(this.listField3);
   }

   public void performAction_1452(String string, String string1) {
      this.listField3.add(new Macro(string, string1));
   }

   public void performAction_1449(String string) {
      this.listField3.removeIf(macro -> macro.getKey_1445().equals(string));
   }

   public void getListField3_1453() {
      this.listField3.clear();
   }

   static {
      ObfuscatorRuntime.registerClass(MacroManager.class, 32);
   }
}
