package aethereal.core;

import aethereal.api.ApiAnnotation001;
import aethereal.api.ApiAnnotation005;
import aethereal.event.DrawEvent;
import aethereal.render.RenderComponent186;
import aethereal.render.RenderComponent192;
import aethereal.render.pipeline.PipelineComponent196;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents.ClientStopping;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents.StartTick;
import net.minecraft.client.MinecraftClient;

public class NeneClient {
   private static NeneClient instance;
   private ProcessorRegistry processors;
   private ClientBackend backend;
   private UserProfile user;
   private static volatile UserProfile pendingUser;
   private static volatile NeneClient activeClient;

   @ApiAnnotation001
   @ApiAnnotation005
   protected void initialize() {
      this.user = new UserProfile("", MinecraftClient.getInstance().getSession().getUsername(), "", "Local", "", "");
      instance = this;
      registerActiveClient(this);
      this.processors = new ProcessorRegistry();
      this.backend = new ClientBackend(false);
      ClientLifecycleEvents.CLIENT_STOPPING.register((ClientStopping)minecraftClient -> this.onClientStopping(minecraftClient));
      ClientTickEvents.START_CLIENT_TICK.register((StartTick)minecraftClient -> EventBus.post(new ClientTickEvent()));
      EventBus.register(this);
      this.processors.initialize();
   }

   @ApiAnnotation001
   @ApiAnnotation005
   protected void shutdown() {
      if (this.processors != null) {
         this.processors.shutdown();
      }
   }

   public static void updateActiveUser(UserProfile userProfile) {
      pendingUser = userProfile;
      NeneClient neneClient = activeClient;
      if (neneClient != null) {
         neneClient.user = userProfile;
      }
   }

   private static void registerActiveClient(NeneClient neneClient) {
      activeClient = neneClient;
      UserProfile userProfile = pendingUser;
      if (userProfile != null) {
         neneClient.user = userProfile;
      }
   }

   public void setProcessors(ProcessorRegistry processorRegistry) {
      this.processors = processorRegistry;
   }

   public void setBackend(ClientBackend clientBackend) {
      this.backend = clientBackend;
   }

   public void setUser(UserProfile userProfile) {
      this.user = userProfile;
   }

   public static NeneClient getInstance() {
      return instance;
   }

   public ProcessorRegistry getProcessors() {
      return this.processors;
   }

   public ClientBackend getBackend() {
      return this.backend;
   }

   public UserProfile getUser() {
      return this.user;
   }

   public NeneClient() {
      this.initialize();
   }

   public void onClientStopping(MinecraftClient minecraftClient) {
      this.shutdown();
   }

   @EventTarget(priority = 0)
   public void onDrawEvent_0643(DrawEvent drawEvent) {
      if (drawEvent.checkCondition_1001()) {
         RenderComponent192.performAction_2676(drawEvent.getContext_1000(), 2);
         if (this.processors != null && this.processors.getModules() != null) {
            for (Module module : this.processors.getModules().getEntries()) {
               module.getEnableAnimation().performAction_2535(0.0F, 1.0F, 0.3F, RenderComponent186.contract372Field9, drawEvent.getTickNeneClient_0997());
               module.getEnableAnimation().performAction_2528(module.isEnabled());
               module.getExpandAnimation().performAction_2535(0.0F, 1.0F, 0.3F, RenderComponent186.contract372Field9, drawEvent.getTickNeneClient_0997());
               module.getExpandAnimation().performAction_2528(module.isBinding());
            }
         }
      }
   }

   @EventTarget(priority = 4)
   public void onDrawEvent_0644(DrawEvent drawEvent) {
      if (drawEvent.checkCondition_1001()) {
         RenderComponent192.performAction_2675(drawEvent.getContext_1000());
         PipelineComponent196.performAction_2728();
      }
   }

   public String computeValue_0645() {
      return null;
   }

   static {
      ObfuscatorRuntime.registerClass(NeneClient.class, 0);
   }
}
