package aethereal.module.misc;

import aethereal.core.Category;
import aethereal.core.Module;
import aethereal.core.ModuleRegister;
import aethereal.setting.ChoiceSetting;
import aethereal.setting.FloatSetting;
import java.io.BufferedInputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.FloatControl.Type;
import net.minecraft.util.Identifier;

@ModuleRegister(name = "Sounds", description = "Plays selected sounds during certain game events", category = Category.Misc)
public class Sounds extends Module {
   private final ChoiceSetting choiceSettingField1 = new ChoiceSetting("Sound to play", "Type 1", "Type 1", "Type 2", "Type 3", "Type 4")
      .withOnChange(string -> this.performAction_1998(this.computeValue_1997(true)));
   private final FloatSetting floatSettingField2 = new FloatSetting("Play Volume", 0.25F, 0.0F, 1.0F, 0.05F);
   private final ExecutorService executorServiceField3 = Executors.newSingleThreadExecutor(runnable -> {
      Thread thread = new Thread(runnable, "ClientSound-Thread");
      thread.setDaemon(true);
      return thread;
   });

   public FloatSetting getFloatSettingField2_1995() {
      return this.floatSettingField2;
   }

   public Sounds() {
      this.addSettings(this.choiceSettingField1, this.floatSettingField2);
   }

   public void performAction_2000(boolean bl) {
      if (this.isEnabled()) {
         this.performAction_1998(this.computeValue_1997(bl));
      }
   }

   public void performAction_1998(String string) {
      if (string != null && !string.isEmpty() && minecraft.getResourceManager() != null) {
         this.executorServiceField3
            .execute(
               () -> {
                  try {
                     AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                        new BufferedInputStream(minecraft.getResourceManager().open(Identifier.of("neneclient", "sounds/" + string)))
                     );
                     Clip clip = AudioSystem.getClip();
                     clip.open(audioInputStream);
                     if (clip.isControlSupported(Type.MASTER_GAIN)) {
                        FloatControl floatControl = (FloatControl)clip.getControl(Type.MASTER_GAIN);
                        floatControl.setValue(
                           Math.max(
                              floatControl.getMinimum(),
                              Math.min(floatControl.getMaximum(), (float)(20.0 * Math.log10(Math.max(this.floatSettingField2.getCurrentValue(), 1.0E-4F))))
                           )
                        );
                     }

                     clip.start();
                     clip.addLineListener(lineEvent -> {
                        if (lineEvent.getType() == javax.sound.sampled.LineEvent.Type.STOP) {
                           clip.close();
                        }
                     });
                  } catch (Exception exception) {
                     exception.printStackTrace();
                  }
               }
            );
      }
   }

   public String computeValue_1997(boolean bl) {
      switch ((String)this.choiceSettingField1.getCurrentValue()) {
         case "Type 1":
            return bl ? "enable.wav" : "disable.wav";
         case "Type 2":
            return bl ? "enable1.wav" : "disable1.wav";
         case "Type 3":
            return bl ? "enable2.wav" : "disable2.wav";
         case "Type 4":
            return bl ? "enable3.wav" : "disable3.wav";
         default:
            return null;
      }
   }
}
