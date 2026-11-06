package aethereal.core;

import aethereal.autobuy.AutoBuyManager;
import aethereal.autobuy.AutobuyComponent008;
import aethereal.autobuy.AutobuyComponent009;
import aethereal.command.CommandManager;
import aethereal.config.ModuleManager;
import aethereal.config.PersistentProcessor;
import aethereal.config.ResourcePackProcessor;
import aethereal.config.SettingsPersistence;
import aethereal.config.ThemeManager;
import aethereal.discord.DiscordComponent057;
import aethereal.friend.FriendManager;
import aethereal.handler.HandlerComponent106;
import aethereal.handler.HandlerComponent108;
import aethereal.handler.RotationProcessor;
import aethereal.macro.MacroManager;
import aethereal.network.ProxyManager;
import aethereal.notification.NotificationComponent182;
import aethereal.render.RenderComponent184;
import aethereal.render.RenderComponent185;
import aethereal.staff.StaffManager;
import aethereal.ui.element.DragProcessor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProcessorRegistry implements MinecraftAccess {
   private final List<PersistentProcessor> listField1;
   private final ProxyManager proxyManagerField2;
   private final RenderComponent184 renderComponent184Field3;
   private final RenderComponent185 renderComponent185Field4;
   private final NotificationComponent182 notificationComponent182Field5;
   private final ThemeManager themeManagerField6;
   private final DragProcessor dragProcessorField7;
   private final ModuleManager moduleManagerField8;
   private final MacroManager macroManagerField9;
   private final FriendManager friendManagerField10;
   private final StaffManager staffManagerField11;
   private final AutoBuyManager autoBuyManagerField12;
   private final CommandManager commandManagerField13;
   private final HandlerComponent106 handlerComponent106Field14 = new HandlerComponent106();
   private final AutobuyComponent008 autobuyComponent008Field15 = new AutobuyComponent008();
   private final AutobuyComponent009 autobuyComponent009Field16 = new AutobuyComponent009();
   private final DiscordComponent057 discordComponent057Field17 = new DiscordComponent057();
   private final ResourcePackProcessor resourcePackProcessorField18 = new ResourcePackProcessor();

   public void initialize() {
      Collections.addAll(
         this.listField1,
         this.proxyManagerField2,
         this.renderComponent184Field3,
         this.renderComponent185Field4,
         this.themeManagerField6,
         this.macroManagerField9,
         this.friendManagerField10,
         this.staffManagerField11,
         this.autoBuyManagerField12,
         this.notificationComponent182Field5,
         this.moduleManagerField8,
         this.dragProcessorField7,
         this.commandManagerField13
      );
      this.listField1.forEach(persistentProcessor -> {
         try {
            persistentProcessor.load();
         } catch (Throwable throwable) {
            throwable.printStackTrace();
         }
      });
      SettingsPersistence.performAction_0533(this.moduleManagerField8, this.themeManagerField6, this.dragProcessorField7);
   }

   public ProcessorRegistry() {
      NeneClient.getInstance().setProcessors(this);
      this.listField1 = new ArrayList<>();
      this.proxyManagerField2 = new ProxyManager();
      this.renderComponent184Field3 = new RenderComponent184();
      this.renderComponent185Field4 = new RenderComponent185();
      this.notificationComponent182Field5 = new NotificationComponent182();
      this.themeManagerField6 = new ThemeManager();
      this.dragProcessorField7 = new DragProcessor();
      this.moduleManagerField8 = new ModuleManager();
      this.macroManagerField9 = new MacroManager();
      this.friendManagerField10 = new FriendManager();
      this.staffManagerField11 = new StaffManager();
      this.autoBuyManagerField12 = new AutoBuyManager();
      this.commandManagerField13 = new CommandManager();
   }

   public DragProcessor getDragProcessorField7_0680() {
      return this.dragProcessorField7;
   }

   public HandlerComponent106 getHandlerComponent106Field14_0673() {
      return this.handlerComponent106Field14;
   }

   public AutobuyComponent008 getAutobuyComponent008Field15_0664() {
      return this.autobuyComponent008Field15;
   }

   public AutobuyComponent009 getAutobuyComponent009Field16_0665() {
      return this.autobuyComponent009Field16;
   }

   public DiscordComponent057 getDiscordComponent057Field17_0670() {
      return this.discordComponent057Field17;
   }

   public ResourcePackProcessor getResourcePackProcessorField18_0668() {
      return this.resourcePackProcessorField18;
   }

   public RotationProcessor computeValue_0672() {
      return HandlerComponent108.getROTATION_1157();
   }

   public ModuleManager getModules() {
      return this.moduleManagerField8;
   }

   public List<PersistentProcessor> getListField1_0660() {
      return this.listField1;
   }

   public RenderComponent184 getRenderComponent184Field3_0677() {
      return this.renderComponent184Field3;
   }

   public ProxyManager getProxyManagerField2_0675() {
      return this.proxyManagerField2;
   }

   public ThemeManager getThemes() {
      return this.themeManagerField6;
   }

   public MacroManager getMacros() {
      return this.macroManagerField9;
   }

   public FriendManager getFriends() {
      return this.friendManagerField10;
   }

   public StaffManager getStaff() {
      return this.staffManagerField11;
   }

   public AutoBuyManager getAutoBuyManagerField12_0663() {
      return this.autoBuyManagerField12;
   }

   public CommandManager getCommandManagerField13_0666() {
      return this.commandManagerField13;
   }

   public RenderComponent185 getRenderComponent185Field4_0678() {
      return this.renderComponent185Field4;
   }

   public NotificationComponent182 getNotificationComponent182Field5_0676() {
      return this.notificationComponent182Field5;
   }

   public void shutdown() {
      SettingsPersistence.performAction_0541();
      this.listField1.forEach(persistentProcessor -> {
         try {
            persistentProcessor.save();
         } catch (Throwable throwable) {
         }
      });
   }
}
