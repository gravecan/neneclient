package aethereal.config;

import aethereal.ambience.Ambience;
import aethereal.core.EventBus;
import aethereal.core.EventTarget;
import aethereal.core.MinecraftAccess;
import aethereal.core.Module;
import aethereal.event.EventComponent093;
import aethereal.lib.json.JsonComponent135;
import aethereal.lib.json.JsonComponent136;
import aethereal.module.client.DiscordPresence;
import aethereal.module.client.DropdownGUI;
import aethereal.module.client.NameProtect;
import aethereal.module.client.PanelGUI;
import aethereal.module.client.Screenshare;
import aethereal.module.combat.AimAssistant;
import aethereal.module.combat.AimLock;
import aethereal.module.combat.AntiBot;
import aethereal.module.combat.Aura;
import aethereal.module.combat.AutoArmor;
import aethereal.module.combat.AutoEXP;
import aethereal.module.combat.AutoExplosion;
import aethereal.module.combat.AutoSwap;
import aethereal.module.combat.AutoTotem;
import aethereal.module.combat.HitBoxes;
import aethereal.module.combat.ItemHelper;
import aethereal.module.combat.MaceHelper;
import aethereal.module.combat.NoFriendDamage;
import aethereal.module.combat.NoServerDesync;
import aethereal.module.combat.NoServerPack;
import aethereal.module.combat.NoSlotChange;
import aethereal.module.combat.ProjectileHelper;
import aethereal.module.combat.ShiftTAP;
import aethereal.module.combat.TapeMouse;
import aethereal.module.combat.TriggerBot;
import aethereal.module.combat.Velocity;
import aethereal.module.misc.AncientFarmer;
import aethereal.module.misc.AntiAFK;
import aethereal.module.misc.AppleFarmer;
import aethereal.module.misc.AutoBuy;
import aethereal.module.misc.AutoWarden;
import aethereal.module.misc.ChatHelper;
import aethereal.module.misc.ClanUpgrader;
import aethereal.module.misc.Collector;
import aethereal.module.misc.Communication;
import aethereal.module.misc.FunDeliver;
import aethereal.module.misc.MineAssistant;
import aethereal.module.misc.NoCommands;
import aethereal.module.misc.NoInteract;
import aethereal.module.misc.Nuker;
import aethereal.module.misc.PortalBypass;
import aethereal.module.misc.PotionThrower;
import aethereal.module.misc.ServerAssistant;
import aethereal.module.misc.ServerJoiner;
import aethereal.module.misc.Sounds;
import aethereal.module.misc.StreamerMode;
import aethereal.module.misc.XRay;
import aethereal.module.movement.AirStuck;
import aethereal.module.movement.AutoDodge;
import aethereal.module.movement.ElytraTarget;
import aethereal.module.movement.FastBreak;
import aethereal.module.movement.Fly;
import aethereal.module.movement.FreeCamera;
import aethereal.module.movement.NoCrouch;
import aethereal.module.movement.NoDelay;
import aethereal.module.movement.NoPush;
import aethereal.module.movement.NoSlowDown;
import aethereal.module.movement.SafeWalk;
import aethereal.module.movement.Scaffold;
import aethereal.module.movement.ScreenWalk;
import aethereal.module.movement.Sprint;
import aethereal.module.movement.WallClimb;
import aethereal.module.movement.WaterJump;
import aethereal.module.player.AucReissue;
import aethereal.module.player.AutoAccept;
import aethereal.module.player.AutoAuth;
import aethereal.module.player.AutoEat;
import aethereal.module.player.AutoFish;
import aethereal.module.player.AutoLeave;
import aethereal.module.player.AutoRespawn;
import aethereal.module.player.AutoTool;
import aethereal.module.player.CaptchaSolver;
import aethereal.module.player.ChestStealer;
import aethereal.module.player.ClickAction;
import aethereal.module.player.DeathCoords;
import aethereal.module.player.ElytraHelper;
import aethereal.module.player.FakeLags;
import aethereal.module.player.FastEXP;
import aethereal.module.player.FastLoad;
import aethereal.module.player.ItemScroller;
import aethereal.module.player.LockSlot;
import aethereal.module.player.OpenWalls;
import aethereal.module.player.SoundReducer;
import aethereal.module.player.Structures;
import aethereal.module.player.ThirdPerson;
import aethereal.module.player.UseTracker;
import aethereal.module.player.WindHop;
import aethereal.module.render.AspectRatio;
import aethereal.module.render.BlockESP;
import aethereal.module.render.BoardSpoofer;
import aethereal.module.render.ChinaHat;
import aethereal.module.render.Crosshair;
import aethereal.module.render.EntityBox;
import aethereal.module.render.EntityESP;
import aethereal.module.render.FullBright;
import aethereal.module.render.HandChams;
import aethereal.module.render.Interface;
import aethereal.module.render.ItemPhysic;
import aethereal.module.render.Pointers;
import aethereal.module.render.Predictions;
import aethereal.module.render.Removals;
import aethereal.module.render.SeeInvisibles;
import aethereal.module.render.ShaderESP;
import aethereal.module.render.ShulkerPreview;
import aethereal.module.render.SoundESP;
import aethereal.module.render.SwingAnimation;
import aethereal.module.render.ViewModel;
import aethereal.module.render.WardenESP;
import aethereal.module.visuals.ArrayList;
import aethereal.module.visuals.Coordinates;
import aethereal.module.visuals.CustomSky;
import aethereal.module.visuals.FPSDisplay;
import aethereal.module.visuals.HitFX;
import aethereal.module.visuals.Keystrokes;
import aethereal.module.visuals.NoHurtCam;
import aethereal.module.visuals.StaffList;
import aethereal.module.visuals.TargetHUD;
import aethereal.module.visuals.Watermark;
import aethereal.render.Animations;
import aethereal.setting.KeybindSetting;
import aethereal.setting.Setting;
import aethereal.ui.screen.MainMenuScreen;
import aethereal.ui.screen.ScreenComponent218;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import net.minecraft.text.Text;

public class ModuleManager extends ListProcessor<Module> {
   private Interface interfaceField1;
   private NameProtect nameProtect;
   private NoCommands noCommandsField2;
   private NoFriendDamage noFriendDamage;
   private BlockESP blockESP;
   private Aura auraField3;
   private TriggerBot triggerBotField4;
   private Animations animationsField5;
   private ServerAssistant serverAssistantField6;
   private StreamerMode streamerModeField7;
   private AutoTotem autoTotemField8;
   private ProjectileHelper projectileHelperField9;
   private WindHop windHopField10;
   private MaceHelper maceHelperField11;
   private AimAssistant aimAssistant;
   private AimLock aimLock;
   private AntiBot antiBot;
   private AutoArmor autoArmor;
   private AutoEXP autoEXP;
   private AutoExplosion autoExplosion;
   private AutoSwap autoSwap;
   private HitBoxes hitBoxes;
   private ItemHelper itemHelper;
   private NoServerDesync noServerDesync;
   private NoServerPack noServerPack;
   private NoSlotChange noSlotChange;
   private ShiftTAP shiftTAP;
   private TapeMouse tapeMouse;
   private Velocity velocity;
   private ScreenWalk screenWalkField12;
   private FreeCamera freeCameraField13;
   private AutoDodge autoDodgeField14;
   private NoCrouch noCrouchField15;
   private Sprint sprintField16;
   private NoSlowDown noSlowDownField17;
   private ElytraTarget elytraTargetField18;
   private NoPush noPushField19;
   private SafeWalk safeWalkField20;
   private WaterJump waterJumpField21;
   private AirStuck airStuckField22;
   private NoDelay noDelayField23;
   private Fly flyField24;
   private WallClimb wallClimbField25;
   private Scaffold scaffoldField26;
   private FastBreak fastBreakField27;
   private SwingAnimation swingAnimationField28;
   private SeeInvisibles seeInvisiblesField29;
   private EntityBox entityBoxField30;
   private EntityESP entityESPField31;
   private ShaderESP shaderESPField32;
   private ItemPhysic itemPhysicField33;
   private Removals removalsField34;
   private ViewModel viewModelField35;
   private Crosshair crosshairField36;
   private ShulkerPreview shulkerPreviewField37;
   private ChinaHat chinaHatField38;
   private AspectRatio aspectRatioField39;
   private Predictions predictionsField40;
   private WardenESP wardenESPField41;
   private SoundESP soundESPField42;
   private BoardSpoofer boardSpooferField43;
   private Pointers pointersField44;
   private FullBright fullBrightField45;
   private HandChams handChamsField46;
   private Ambience ambienceField47;
   private AncientFarmer ancientFarmerField48;
   private AntiAFK antiAFKField49;
   private AppleFarmer appleFarmerField50;
   private AucReissue aucReissueField51;
   private AutoAccept autoAcceptField52;
   private AutoAuth autoAuthField53;
   private AutoBuy autoBuyField54;
   private AutoEat autoEatField55;
   private AutoFish autoFishField56;
   private AutoLeave autoLeaveField57;
   private AutoRespawn autoRespawnField58;
   private AutoTool autoToolField59;
   private AutoWarden autoWardenField60;
   private CaptchaSolver captchaSolverField61;
   private ChatHelper as;
   private ChestStealer chestStealerField62;
   private ClanUpgrader clanUpgraderField63;
   private ClickAction clickActionField64;
   private Collector collectorField65;
   private Communication communicationField66;
   private DeathCoords deathCoordsField67;
   private ElytraHelper elytraHelperField68;
   private FakeLags fakeLagsField69;
   private FastEXP fastEXPField70;
   private FastLoad fastLoadField71;
   private FunDeliver funDeliverField72;
   private ItemScroller itemScrollerField73;
   private LockSlot lockSlotField74;
   private MineAssistant mineAssistantField75;
   private NoInteract noInteractField76;
   private Nuker nukerField77;
   private OpenWalls openWallsField78;
   private PortalBypass portalBypassField79;
   private PotionThrower potionThrowerField80;
   private ServerJoiner serverJoinerField81;
   private SoundReducer soundReducerField82;
   private Sounds soundsField83;
   private Structures structuresField84;
   private ThirdPerson thirdPersonField85;
   private UseTracker useTrackerField86;
   private XRay xRayField87;
   private Coordinates visualsCoordinates;
   private FPSDisplay visualsFps;
   private Keystrokes visualsKeystrokes;
   private HitFX visualsHitMarker;
   private NoHurtCam visualsNoHurtCam;
   private CustomSky visualsCustomSky;
   private ArrayList visualsArrayList;
   private StaffList visualsStaffList;
   private TargetHUD visualsTargetHUD;
   private Watermark visualsWatermark;
   private Screenshare screenshare;
   private DiscordPresence discordPresence;
   private DropdownGUI dropdownGUIModule;
   private PanelGUI panelGUIModule;

   public ModuleManager() {
      EventBus.register(this);
   }

   @Override
   public void load() {
      this.interfaceField1 = new Interface();
      this.nameProtect = new NameProtect();
      this.dropdownGUIModule = new DropdownGUI();
      this.panelGUIModule = new PanelGUI();
      this.screenshare = new Screenshare();
      this.discordPresence = new DiscordPresence();
      this.noCommandsField2 = new NoCommands();
      this.noFriendDamage = new NoFriendDamage();
      this.blockESP = new BlockESP();
      this.auraField3 = new Aura();
      this.triggerBotField4 = new TriggerBot();
      this.animationsField5 = new Animations();
      this.serverAssistantField6 = new ServerAssistant();
      this.streamerModeField7 = new StreamerMode();
      this.autoTotemField8 = new AutoTotem();
      this.projectileHelperField9 = new ProjectileHelper();
      this.windHopField10 = new WindHop();
      this.maceHelperField11 = new MaceHelper();
      this.aimAssistant = new AimAssistant();
      this.aimLock = new AimLock();
      this.antiBot = new AntiBot();
      this.autoArmor = new AutoArmor();
      this.autoEXP = new AutoEXP();
      this.autoExplosion = new AutoExplosion();
      this.autoSwap = new AutoSwap();
      this.hitBoxes = new HitBoxes();
      this.itemHelper = new ItemHelper();
      this.noServerDesync = new NoServerDesync();
      this.noServerPack = new NoServerPack();
      this.noSlotChange = new NoSlotChange();
      this.shiftTAP = new ShiftTAP();
      this.tapeMouse = new TapeMouse();
      this.velocity = new Velocity();
      this.screenWalkField12 = new ScreenWalk();
      this.freeCameraField13 = new FreeCamera();
      this.autoDodgeField14 = new AutoDodge();
      this.noCrouchField15 = new NoCrouch();
      this.sprintField16 = new Sprint();
      this.noSlowDownField17 = new NoSlowDown();
      this.elytraTargetField18 = new ElytraTarget();
      this.noPushField19 = new NoPush();
      this.safeWalkField20 = new SafeWalk();
      this.waterJumpField21 = new WaterJump();
      this.airStuckField22 = new AirStuck();
      this.noDelayField23 = new NoDelay();
      this.flyField24 = new Fly();
      this.wallClimbField25 = new WallClimb();
      this.scaffoldField26 = new Scaffold();
      this.fastBreakField27 = new FastBreak();
      this.swingAnimationField28 = new SwingAnimation();
      this.seeInvisiblesField29 = new SeeInvisibles();
      this.entityBoxField30 = new EntityBox();
      this.entityESPField31 = new EntityESP();
      this.shaderESPField32 = new ShaderESP();
      this.itemPhysicField33 = new ItemPhysic();
      this.removalsField34 = new Removals();
      this.viewModelField35 = new ViewModel();
      this.crosshairField36 = new Crosshair();
      this.shulkerPreviewField37 = new ShulkerPreview();
      this.chinaHatField38 = new ChinaHat();
      this.aspectRatioField39 = new AspectRatio();
      this.predictionsField40 = new Predictions();
      this.wardenESPField41 = new WardenESP();
      this.soundESPField42 = new SoundESP();
      this.boardSpooferField43 = new BoardSpoofer();
      this.pointersField44 = new Pointers();
      this.fullBrightField45 = new FullBright();
      this.handChamsField46 = new HandChams();
      this.ambienceField47 = new Ambience();
      this.ancientFarmerField48 = new AncientFarmer();
      this.antiAFKField49 = new AntiAFK();
      this.appleFarmerField50 = new AppleFarmer();
      this.aucReissueField51 = new AucReissue();
      this.autoAcceptField52 = new AutoAccept();
      this.autoAuthField53 = new AutoAuth();
      this.autoBuyField54 = new AutoBuy();
      this.autoEatField55 = new AutoEat();
      this.autoFishField56 = new AutoFish();
      this.autoLeaveField57 = new AutoLeave();
      this.autoRespawnField58 = new AutoRespawn();
      this.autoToolField59 = new AutoTool();
      this.autoWardenField60 = new AutoWarden();
      this.captchaSolverField61 = new CaptchaSolver();
      this.as = new ChatHelper();
      this.chestStealerField62 = new ChestStealer();
      this.clanUpgraderField63 = new ClanUpgrader();
      this.clickActionField64 = new ClickAction();
      this.collectorField65 = new Collector();
      this.communicationField66 = new Communication();
      this.deathCoordsField67 = new DeathCoords();
      this.elytraHelperField68 = new ElytraHelper();
      this.fakeLagsField69 = new FakeLags();
      this.fastEXPField70 = new FastEXP();
      this.fastLoadField71 = new FastLoad();
      this.funDeliverField72 = new FunDeliver();
      this.itemScrollerField73 = new ItemScroller();
      this.lockSlotField74 = new LockSlot();
      this.mineAssistantField75 = new MineAssistant();
      this.noInteractField76 = new NoInteract();
      this.nukerField77 = new Nuker();
      this.openWallsField78 = new OpenWalls();
      this.portalBypassField79 = new PortalBypass();
      this.potionThrowerField80 = new PotionThrower();
      this.serverJoinerField81 = new ServerJoiner();
      this.soundReducerField82 = new SoundReducer();
      this.soundsField83 = new Sounds();
      this.structuresField84 = new Structures();
      this.thirdPersonField85 = new ThirdPerson();
      this.useTrackerField86 = new UseTracker();
      this.xRayField87 = new XRay();
      this.visualsCoordinates = new Coordinates();
      this.visualsFps = new FPSDisplay();
      this.visualsKeystrokes = new Keystrokes();
      this.visualsHitMarker = new HitFX();
      this.visualsNoHurtCam = new NoHurtCam();
      this.visualsCustomSky = new CustomSky();
      this.visualsArrayList = new ArrayList();
      this.visualsStaffList = new StaffList();
      this.visualsTargetHUD = new TargetHUD();
      this.visualsWatermark = new Watermark();
      this.performAction_0506(
         this.interfaceField1,
         this.nameProtect,
         this.dropdownGUIModule,
         this.panelGUIModule,
         this.screenshare,
         this.discordPresence,
         this.noCommandsField2,
         this.noFriendDamage,
         this.blockESP,
         this.auraField3,
         this.triggerBotField4,
         this.animationsField5,
         this.serverAssistantField6,
         this.streamerModeField7,
         this.autoTotemField8,
         this.projectileHelperField9,
         this.windHopField10,
         this.maceHelperField11,
         this.velocity,
         this.aimAssistant,
         this.aimLock,
         this.antiBot,
         this.autoArmor,
         this.autoEXP,
         this.autoExplosion,
         this.autoSwap,
         this.hitBoxes,
         this.itemHelper,
         this.noServerDesync,
         this.noServerPack,
         this.noSlotChange,
         this.shiftTAP,
         this.tapeMouse
      );
      this.performAction_0506(
         this.airStuckField22,
         this.noCrouchField15,
         this.freeCameraField13,
         this.flyField24,
         this.wallClimbField25,
         this.scaffoldField26,
         this.waterJumpField21,
         this.noDelayField23,
         this.safeWalkField20,
         this.noSlowDownField17,
         this.autoDodgeField14,
         this.elytraTargetField18,
         this.sprintField16,
         this.noPushField19,
         this.screenWalkField12,
         this.fastBreakField27
      );
      this.performAction_0506(
         this.seeInvisiblesField29,
         this.wardenESPField41,
         this.soundESPField42,
         this.boardSpooferField43,
         this.crosshairField36,
         this.entityBoxField30,
         this.entityESPField31,
         this.handChamsField46,
         this.shaderESPField32,
         this.itemPhysicField33,
         this.pointersField44,
         this.predictionsField40,
         this.removalsField34,
         this.shulkerPreviewField37,
         this.swingAnimationField28,
         this.viewModelField35,
         this.chinaHatField38,
         this.aspectRatioField39,
         this.fullBrightField45
      );
      this.performAction_0506(
         this.ambienceField47,
         this.ancientFarmerField48,
         this.antiAFKField49,
         this.appleFarmerField50,
         this.aucReissueField51,
         this.autoAcceptField52,
         this.autoAuthField53,
         this.autoBuyField54,
         this.autoEatField55,
         this.autoFishField56,
         this.autoLeaveField57,
         this.autoRespawnField58,
         this.autoToolField59,
         this.autoWardenField60,
         this.captchaSolverField61,
         this.as,
         this.chestStealerField62,
         this.clanUpgraderField63,
         this.clickActionField64,
         this.collectorField65,
         this.communicationField66,
         this.deathCoordsField67,
         this.elytraHelperField68,
         this.fakeLagsField69,
         this.fastEXPField70,
         this.fastLoadField71,
         this.funDeliverField72,
         this.itemScrollerField73,
         this.lockSlotField74,
         this.mineAssistantField75,
         this.noInteractField76,
         this.nukerField77,
         this.openWallsField78,
         this.portalBypassField79,
         this.potionThrowerField80,
         this.serverJoinerField81,
         this.soundReducerField82,
         this.soundsField83,
         this.structuresField84,
         this.thirdPersonField85,
         this.useTrackerField86,
         this.xRayField87
      );
      this.performAction_0506(
         this.visualsCoordinates,
         this.visualsFps,
         this.visualsKeystrokes,
         this.visualsHitMarker,
         this.visualsNoHurtCam,
         this.visualsCustomSky,
         this.visualsArrayList,
         this.visualsStaffList,
         this.visualsTargetHUD,
         this.visualsWatermark
      );
      this.visualsArrayList.setEnabled(true);
      this.visualsStaffList.setEnabled(true);
      this.visualsTargetHUD.setEnabled(true);
      this.visualsWatermark.setEnabled(true);
      super.load();
   }

   public MaceHelper getMaceHelperField11_0410() {
      return this.maceHelperField11;
   }

   public AutoTotem getAutoTotemField8_0407() {
      return this.autoTotemField8;
   }

   public ProjectileHelper getProjectileHelperField9_0415() {
      return this.projectileHelperField9;
   }

   public WindHop getWindHopField10_0480() {
      return this.windHopField10;
   }

   public Interface getInterfaceField1_0490() {
      return this.interfaceField1;
   }

   public NameProtect getNameProtect_0399() {
      return this.nameProtect;
   }

   public NoCommands getNoCommandsField2_0431() {
      return this.noCommandsField2;
   }

   public NoFriendDamage getNoFriendDamage_0411() {
      return this.noFriendDamage;
   }

   public NoFriendDamage getNoFriendDamage_0388() {
      return this.noFriendDamage;
   }

   public BlockESP getBlockESP_0482() {
      return this.blockESP;
   }

   public Aura getAuraField3_0402() {
      return this.auraField3;
   }

   public TriggerBot getTriggerBotField4_0418() {
      return this.triggerBotField4;
   }

   public Animations getAnimationsField5_0502() {
      return this.animationsField5;
   }

   public ServerAssistant getServerAssistantField6_0436() {
      return this.serverAssistantField6;
   }

   public StreamerMode getStreamerModeField7_0439() {
      return this.streamerModeField7;
   }

   public ScreenWalk getScreenWalkField12_0453() {
      return this.screenWalkField12;
   }

   public FreeCamera getFreeCameraField13_0446() {
      return this.freeCameraField13;
   }

   public AutoDodge getAutoDodgeField14_0442() {
      return this.autoDodgeField14;
   }

   public NoCrouch getNoCrouchField15_0447() {
      return this.noCrouchField15;
   }

   public Sprint getSprintField16_0454() {
      return this.sprintField16;
   }

   public NoSlowDown getNoSlowDownField17_0450() {
      return this.noSlowDownField17;
   }

   public ElytraTarget getElytraTargetField18_0443() {
      return this.elytraTargetField18;
   }

   public NoPush getNoPushField19_0449() {
      return this.noPushField19;
   }

   public SafeWalk getSafeWalkField20_0451() {
      return this.safeWalkField20;
   }

   public WaterJump getWaterJumpField21_0456() {
      return this.waterJumpField21;
   }

   public AirStuck getAirStuckField22_0441() {
      return this.airStuckField22;
   }

   public NoDelay getNoDelayField23_0448() {
      return this.noDelayField23;
   }

   public Fly getFlyField24_0445() {
      return this.flyField24;
   }

   public WallClimb getWallClimbField25_0455() {
      return this.wallClimbField25;
   }

   public Scaffold getScaffoldField26_0452() {
      return this.scaffoldField26;
   }

   public FastBreak getFastBreakField27_0444() {
      return this.fastBreakField27;
   }

   public AutoSwap getAutoSwap_0406() {
      return this.autoSwap;
   }

   public SwingAnimation getSwingAnimationField28_0499() {
      return this.swingAnimationField28;
   }

   public SeeInvisibles getSeeInvisiblesField29_0495() {
      return this.seeInvisiblesField29;
   }

   public EntityBox getEntityBoxField30_0486() {
      return this.entityBoxField30;
   }

   public EntityESP getEntityESPField31_0487() {
      return this.entityESPField31;
   }

   public ShaderESP getShaderESPField32_0496() {
      return this.shaderESPField32;
   }

   public ItemPhysic getItemPhysicField33_0491() {
      return this.itemPhysicField33;
   }

   public Removals getRemovalsField34_0494() {
      return this.removalsField34;
   }

   public ViewModel getViewModelField35_0500() {
      return this.viewModelField35;
   }

   public Crosshair getCrosshairField36_0485() {
      return this.crosshairField36;
   }

   public ShulkerPreview getShulkerPreviewField37_0497() {
      return this.shulkerPreviewField37;
   }

   public ChinaHat getChinaHatField38_0484() {
      return this.chinaHatField38;
   }

   public AspectRatio getAspectRatioField39_0481() {
      return this.aspectRatioField39;
   }

   public Predictions getPredictionsField40_0493() {
      return this.predictionsField40;
   }

   public WardenESP getWardenESPField41_0501() {
      return this.wardenESPField41;
   }

   public SoundESP getSoundESPField42_0498() {
      return this.soundESPField42;
   }

   public BoardSpoofer getBoardSpooferField43_0483() {
      return this.boardSpooferField43;
   }

   public Pointers getPointersField44_0492() {
      return this.pointersField44;
   }

   public FullBright getFullBrightField45_0488() {
      return this.fullBrightField45;
   }

   public HandChams getHandChamsField46_0489() {
      return this.handChamsField46;
   }

   public AutoExplosion getAutoExplosion_0405() {
      return this.autoExplosion;
   }

   public BlockESP getBlockESP_0389() {
      return this.blockESP;
   }

   public NoServerDesync getNoServerDesync_0412() {
      return this.noServerDesync;
   }

   public TapeMouse getTapeMouse_0417() {
      return this.tapeMouse;
   }

   public Ambience getAmbienceField47_0398() {
      return this.ambienceField47;
   }

   public AncientFarmer getAncientFarmerField48_0420() {
      return this.ancientFarmerField48;
   }

   public AntiAFK getAntiAFKField49_0423() {
      return this.antiAFKField49;
   }

   public AppleFarmer getAppleFarmerField50_0424() {
      return this.appleFarmerField50;
   }

   public AucReissue getAucReissueField51_0459() {
      return this.aucReissueField51;
   }

   public AutoAccept getAutoAcceptField52_0460() {
      return this.autoAcceptField52;
   }

   public AutoAuth getAutoAuthField53_0461() {
      return this.autoAuthField53;
   }

   public AutoBuy getAutoBuyField54_0425() {
      return this.autoBuyField54;
   }

   public AutoEXP getAutoEXP_0404() {
      return this.autoEXP;
   }

   public AutoEat getAutoEatField55_0462() {
      return this.autoEatField55;
   }

   public AutoFish getAutoFishField56_0463() {
      return this.autoFishField56;
   }

   public AutoLeave getAutoLeaveField57_0464() {
      return this.autoLeaveField57;
   }

   public AutoRespawn getAutoRespawnField58_0465() {
      return this.autoRespawnField58;
   }

   public AutoTool getAutoToolField59_0466() {
      return this.autoToolField59;
   }

   public AutoWarden getAutoWardenField60_0421() {
      return this.autoWardenField60;
   }

   public CaptchaSolver getCaptchaSolverField61_0467() {
      return this.captchaSolverField61;
   }

   public ChatHelper getAs_0426() {
      return this.as;
   }

   public ChestStealer getChestStealerField62_0468() {
      return this.chestStealerField62;
   }

   public ClanUpgrader getClanUpgraderField63_0427() {
      return this.clanUpgraderField63;
   }

   public ClickAction getClickActionField64_0469() {
      return this.clickActionField64;
   }

   public Collector getCollectorField65_0428() {
      return this.collectorField65;
   }

   public Communication getCommunicationField66_0422() {
      return this.communicationField66;
   }

   public DeathCoords getDeathCoordsField67_0470() {
      return this.deathCoordsField67;
   }

   public ElytraHelper getElytraHelperField68_0471() {
      return this.elytraHelperField68;
   }

   public FakeLags getFakeLagsField69_0472() {
      return this.fakeLagsField69;
   }

   public FastEXP getFastEXPField70_0473() {
      return this.fastEXPField70;
   }

   public FastLoad getFastLoadField71_0474() {
      return this.fastLoadField71;
   }

   public FunDeliver getFunDeliverField72_0429() {
      return this.funDeliverField72;
   }

   public ItemHelper getItemHelper_0409() {
      return this.itemHelper;
   }

   public ItemScroller getItemScrollerField73_0475() {
      return this.itemScrollerField73;
   }

   public LockSlot getLockSlotField74_0476() {
      return this.lockSlotField74;
   }

   public MineAssistant getMineAssistantField75_0430() {
      return this.mineAssistantField75;
   }

   public NoInteract getNoInteractField76_0432() {
      return this.noInteractField76;
   }

   public NoServerPack getNoServerPack_0413() {
      return this.noServerPack;
   }

   public NoSlotChange getNoSlotChange_0414() {
      return this.noSlotChange;
   }

   public Nuker getNukerField77_0433() {
      return this.nukerField77;
   }

   public OpenWalls getOpenWallsField78_0477() {
      return this.openWallsField78;
   }

   public PortalBypass getPortalBypassField79_0434() {
      return this.portalBypassField79;
   }

   public PotionThrower getPotionThrowerField80_0435() {
      return this.potionThrowerField80;
   }

   public ServerJoiner getServerJoinerField81_0437() {
      return this.serverJoinerField81;
   }

   public SoundReducer getSoundReducerField82_0478() {
      return this.soundReducerField82;
   }

   public Sounds getSoundsField83_0438() {
      return this.soundsField83;
   }

   public Structures getStructuresField84_0457() {
      return this.structuresField84;
   }

   public ThirdPerson getThirdPersonField85_0479() {
      return this.thirdPersonField85;
   }

   public UseTracker getUseTrackerField86_0458() {
      return this.useTrackerField86;
   }

   public Velocity getVelocity_0419() {
      return this.velocity;
   }

   public XRay getXRayField87_0440() {
      return this.xRayField87;
   }

   public ShiftTAP getShiftTAP_0416() {
      return this.shiftTAP;
   }

   public AimAssistant getAimAssistant_0400() {
      return this.aimAssistant;
   }

   public AntiBot getAntiBot_0401() {
      return this.antiBot;
   }

   public AutoArmor getAutoArmor_0403() {
      return this.autoArmor;
   }

   public HitBoxes getHitBoxes_0408() {
      return this.hitBoxes;
   }

   @Override
   public File getFileField2_0355() {
      return this.fileField1;
   }

   @Override
   protected String computeValue_0040() {
      return "default.json";
   }

   @Override
   protected List<Module> computeValue_0041(String string) {
      if (string != null && !string.isBlank() && !string.trim().startsWith("[")) {
         JsonComponent136 jsonComponent136 = new JsonComponent136(string);
         JsonComponent135 jsonComponent135 = jsonComponent136.computeValue_1267("modules");
         if (jsonComponent135 != null) {
            for (int i = 0; i < jsonComponent135.getDelegate_1250(); i++) {
               try {
                  JsonComponent136 jsonComponent1361 = jsonComponent135.computeValue_1252(i);
                  if (jsonComponent1361 != null) {
                     String string1 = jsonComponent1361.computeValue_1260("name");
                     if (string1 != null) {
                        for (Module module : this.getEntries()) {
                           if (module.getName().equalsIgnoreCase(string1)) {
                              performAction_0504(jsonComponent1361, module);
                              break;
                           }
                        }
                     }
                  }
               } catch (RuntimeException runtimeException) {
                  System.err.println("[XenoClient] Ignoring malformed module entry at index " + i);
               }
            }
         }

         return new java.util.ArrayList<>(this.getEntries());
      } else {
         return null;
      }
   }

   @Override
   protected String computeValue_0042(List<Module> list) {
      JsonComponent135 jsonComponent135 = new JsonComponent135();
      if (list != null) {
         for (Module module : list) {
            if (module != null) {
               JsonComponent136 jsonComponent136 = new JsonComponent136();
               jsonComponent136.computeValue_1272("name", module.getName());
               jsonComponent136.computeValue_1273("activated", module.isEnabled());
               jsonComponent136.computeValue_1270("bind", module.getKeybind());
               JsonComponent136 jsonComponent1361 = new JsonComponent136();

               for (Setting setting : module.getSettings()) {
                  if (setting != null && setting.shouldPersistChanges()) {
                     jsonComponent1361.computeValue_1272(setting.getName(), ConfigComponent018.computeValue_0362(setting));
                  }
               }

               jsonComponent136.computeValue_1272("settings", jsonComponent1361);
               jsonComponent135.computeValue_1253(jsonComponent136);
            }
         }
      }

      JsonComponent136 jsonComponent1362 = new JsonComponent136();
      jsonComponent1362.computeValue_1272("modules", jsonComponent135);
      return jsonComponent1362.computeValue_1263(2);
   }

   public static void performAction_0504(JsonComponent136 jsonComponent136, Module module) {
      if (jsonComponent136.computeValue_1269("activated") instanceof Boolean boolean_) {
         module.setEnabled(boolean_);
      }

      if (jsonComponent136.computeValue_1269("bind") instanceof Number number && number.longValue() >= -1L && number.longValue() <= 348L) {
         module.setKeybind(number.intValue());
      }

      if (jsonComponent136.checkCondition_1258("settings")) {
         JsonComponent136 jsonComponent1361 = jsonComponent136.computeValue_1268("settings");
         if (jsonComponent1361 == null) {
            return;
         }

         for (Setting setting : module.getSettings()) {
            if (jsonComponent1361.checkCondition_1258(setting.getName())) {
               try {
                  if (setting.shouldPersistChanges()) {
                     ConfigComponent018.performAction_0363(setting, jsonComponent1361.computeValue_1269(setting.getName()));
                  }
               } catch (RuntimeException runtimeException) {
                  System.err.println("[XenoClient] Ignoring invalid setting '" + setting.getName() + "' on " + module.getName());
               }
            }
         }
      }
   }

   public void performAction_0395(String string) {
      try {
         File file = this.computeValue_0393();
         Files.createDirectories(file.toPath());
         String string1 = computeValue_0394(string);
         Files.writeString(new File(file, string1 + ".json").toPath(), this.computeValue_0042(this.listField3));
         this.performAction_0505(string1, this.computeValue_0392());
      } catch (IOException iOException) {
         throw new RuntimeException(iOException);
      }
   }

   public boolean checkCondition_0391(String string) {
      try {
         File file = new File(this.computeValue_0393(), computeValue_0394(string) + ".json");
         if (!file.exists()) {
            return false;
         } else {
            List list = this.computeValue_0041(Files.readString(file.toPath()));
            if (list != null) {
               this.listField3.clear();
               this.listField3.addAll(list);
               return true;
            } else {
               return true;
            }
         }
      } catch (Exception exception) {
         return false;
      }
   }

   public boolean checkCondition_0396(String string) {
      String string1 = computeValue_0394(string);
      File file = new File(this.computeValue_0393(), string1 + ".json");
      if (file.exists() && !string.equals(this.computeValue_0040())) {
         boolean bl = file.delete();
         if (bl) {
            this.performAction_0505(string1, null);
         }

         return bl;
      } else {
         return false;
      }
   }

   public File computeValue_0393() {
      return this.getFileField1_0359();
   }

   public String computeValue_0392() {
      try {
         if (MinecraftAccess.minecraft.getCurrentServerEntry() != null) {
            return MinecraftAccess.minecraft.getCurrentServerEntry().address;
         }

         if (MinecraftAccess.minecraft.isInSingleplayer()) {
            return "Singleplayer";
         }
      } catch (Throwable throwable) {
      }

      return "Offline";
   }

   public String computeValue_0390(String string) {
      Properties properties = this.computeValue_0503();
      return properties.getProperty(computeValue_0394(string), "Unknown server");
   }

   private synchronized void performAction_0505(String string, String string1) {
      try {
         Properties properties = this.computeValue_0503();
         if (string1 == null) {
            properties.remove(string);
         } else {
            properties.setProperty(string, string1);
         }

         File file = this.computeValue_0393();
         Files.createDirectories(file.toPath());

         try (FileOutputStream fileOutputStream = new FileOutputStream(new File(file, ".server-index.properties"))) {
            properties.store(fileOutputStream, "Server where each config was last saved");
         }
      } catch (IOException iOException) {
         System.err.println("[XenoClient] Could not update config server metadata: " + iOException.getMessage());
      }
   }

   private synchronized Properties computeValue_0503() {
      Properties properties = new Properties();
      File file = new File(this.computeValue_0393(), ".server-index.properties");
      if (file.isFile()) {
         try (FileInputStream fileInputStream = new FileInputStream(file)) {
            properties.load(fileInputStream);
         } catch (IOException iOException) {
         }
      }

      return properties;
   }

   private static String computeValue_0394(String string) {
      if (string == null) {
         throw new IllegalArgumentException("Config name is required");
      } else {
         String string1 = string.trim().replaceAll("[^a-zA-Z0-9._-]", "_");
         if (!string1.isBlank() && !string1.equals(".") && !string1.equals("..")) {
            return string1;
         } else {
            throw new IllegalArgumentException("Invalid config name");
         }
      }
   }

   @EventTarget
   public void onEventComponent093_0397(EventComponent093 eventComponent093) {
      int i = eventComponent093.getAction_1070();
      int j = eventComponent093.getKey_1068();
      if (j == 344 && i == 1 && Screenshare.isActive_1470()) {
         for (Module module1 : this.getEntries()) {
            if (module1 instanceof Screenshare && module1.isEnabled()) {
               module1.setEnabled(false);
               break;
            }
         }

         Screenshare.performAction_1469();
         MinecraftAccess.minecraft.setScreen(new ScreenComponent218(Text.literal("GUI"), MinecraftAccess.minecraft.currentScreen));
         eventComponent093.performAction_0597(true);
      } else if (j != 344 || i != 1 || MinecraftAccess.minecraft.currentScreen != null && !(MinecraftAccess.minecraft.currentScreen instanceof MainMenuScreen)) {
         for (Module module : this.getEntries()) {
            if (module.getKeybind() != -1 && module.getKeybind() == j && i == 1) {
               module.toggle();
            }

            if (module.isEnabled()) {
               for (Setting setting : module.getSettings()) {
                  if (setting instanceof KeybindSetting keybindSetting
                     && keybindSetting.getVisibility().get()
                     && keybindSetting.getCurrentValue() != -1
                     && keybindSetting.getCurrentValue() == j) {
                     if (i == 1) {
                        keybindSetting.getActionField1_2845().execute();
                     } else if (i == 0 && keybindSetting.getType_2844() == 0) {
                        keybindSetting.getActionField2_2842().execute();
                     }
                  }
               }
            }
         }
      } else {
         MinecraftAccess.minecraft.setScreen(new ScreenComponent218(Text.literal("GUI"), MinecraftAccess.minecraft.currentScreen));
         eventComponent093.performAction_0597(true);
      }
   }

   public void performAction_0506(Module... modules) {
      Collections.addAll(this.listField3, modules);
   }
}
