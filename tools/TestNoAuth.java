import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import javax.tools.*;

/** Tests the edited menu/profile logic against minimal stubs, NOT a Minecraft integration test. */
public class TestNoAuth {
    public static void main(String[] args) throws Exception {
        Path root = Path.of(args[0]), scratch = Path.of(args[1]);
        if (Files.exists(scratch)) throw new IllegalArgumentException("Use a new scratch directory.");
        Path sources = scratch.resolve("sources"), classes = scratch.resolve("classes");
        Files.createDirectories(sources); Files.createDirectories(classes);
        Map<String,String> stubs = new LinkedHashMap<>();
        stubs.put("net/minecraft/client/gui/screen/Screen.java", "package net.minecraft.client.gui.screen; public class Screen {}");
        stubs.put("net/minecraft/client/gui/screen/TitleScreen.java", "package net.minecraft.client.gui.screen; public class TitleScreen extends Screen {}");
        stubs.put("net/minecraft/client/MinecraftClient.java", "package net.minecraft.client; import net.minecraft.client.gui.screen.Screen; public class MinecraftClient { private static final MinecraftClient INSTANCE=new MinecraftClient(); public Screen currentScreen; public static MinecraftClient getInstance(){return INSTANCE;} public void setScreen(Screen screen){currentScreen=screen;} }");
        stubs.put("org/spongepowered/asm/mixin/Mixin.java", "package org.spongepowered.asm.mixin; public @interface Mixin {Class<?>[] value();}");
        stubs.put("org/spongepowered/asm/mixin/injection/At.java", "package org.spongepowered.asm.mixin.injection; public @interface At {String value();}");
        stubs.put("org/spongepowered/asm/mixin/injection/Inject.java", "package org.spongepowered.asm.mixin.injection; public @interface Inject {String[] method(); At at(); boolean cancellable() default false;}");
        stubs.put("org/spongepowered/asm/mixin/injection/callback/CallbackInfo.java", "package org.spongepowered.asm.mixin.injection.callback; public class CallbackInfo {public boolean cancelled; public void cancel(){cancelled=true;}}");
        stubs.put("aethereal/module/client/Screenshare.java", "package aethereal.module.client; public class Screenshare {public static boolean active; public static boolean isActive_1470(){return active;}}");
        stubs.put("aethereal/ui/screen/MainMenuScreen.java", "package aethereal.ui.screen; public class MainMenuScreen extends net.minecraft.client.gui.screen.Screen {}");
        stubs.put("org/joml/Matrix3x2fStack.java", "package org.joml; public class Matrix3x2fStack {}");
        stubs.put("aethereal/core/Category.java", "package aethereal.core; public enum Category {Combat, Movement}");
        stubs.put("aethereal/core/Module.java", "package aethereal.core; public class Module {public Category getCategory(){return Category.Combat;} public boolean isEnabled(){return true;}}");
        stubs.put("aethereal/core/NeneClient.java", "package aethereal.core; public class NeneClient {public static NeneClient getInstance(){return new NeneClient();} public Registry getProcessors(){return new Registry();} public static class Registry {public Manager getModules(){return new Manager();}} public static class Manager {public java.util.List<Module> getEntries(){return java.util.List.of(new Module());}}}");
        stubs.put("aethereal/ui/screen/MenuRenderer.java", """
            package aethereal.ui.screen;
            public class MenuRenderer {
                public static float time=1, floatValueField1=.016f;
                public static java.util.List<String> labels=new java.util.ArrayList<>();
                public static int getAccent(){return 123;}
                public static float clamp01(float value){return Math.max(0,Math.min(1,value));}
                public static float easeOutCubic(float value){return value;}
                public static float approach(float value,float target,float speed){return target;}
                public static int withAlpha(int color,float alpha){return color;}
                public static int replaceAlpha(int color,float alpha){return color;}
                public static String getDisplayName(){return "LocalPlayer";}
                public static void fillRoundedRect(Object... args){}
                public static void strokeRoundedRect(Object... args){}
                public static void drawCenteredText(Object... args){}
                public static void drawRightAlignedText(Object... args){}
                public static void drawText(Object... args){labels.add((String)args[1]);}
                public static String ellipsize(String text,float size,float width){return text;}
            }
            """);
        stubs.put("NoAuthBehaviorTest.java", """
            import net.minecraft.client.MinecraftClient;
            import net.minecraft.client.gui.screen.Screen;
            import aethereal.module.client.Screenshare;
            import aethereal.ui.screen.*;
            import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
            public class NoAuthBehaviorTest {
                static class Hook extends platform.inject.mixin.TitleScreenMixin {}
                static void check(boolean condition,String message){if(!condition)throw new AssertionError(message);}
                static CallbackInfo invoke() throws Exception {
                    var method=platform.inject.mixin.TitleScreenMixin.class.getDeclaredMethod("init",CallbackInfo.class);
                    method.setAccessible(true); var callback=new CallbackInfo(); method.invoke(new Hook(),callback); return callback;
                }
                public static void main(String[] args) throws Exception {
                    var client=MinecraftClient.getInstance(); client.currentScreen=new Screen(); Screenshare.active=false;
                    var callback=invoke(); check(callback.cancelled && client.currentScreen instanceof MainMenuScreen,"No-activation menu redirect");
                    var existing=client.currentScreen; callback=invoke(); check(!callback.cancelled && client.currentScreen==existing,"Existing menu preserved");
                    Screenshare.active=true; var vanilla=new Screen(); client.currentScreen=vanilla; callback=invoke();
                    check(!callback.cancelled && client.currentScreen==vanilla,"Screenshare preserved");
                    var profile=new ProfilePanel(); profile.render(new org.joml.Matrix3x2fStack(),0,0,320,300,1,0,0);
                    check(MenuRenderer.labels.contains("LOCAL PROFILE") && MenuRenderer.labels.contains("LocalPlayer"),"Local profile renders");
                    check(!profile.mouseClicked(0,0,0),"Removed key controls consume no clicks");
                    check(MenuRenderer.labels.stream().noneMatch(label->label.contains("LICENSE")||label.contains("HWID")),"No activation details rendered");
                    System.out.println("6 isolated menu/profile checks passed (minimal stubs).");
                }
            }
            """);
        List<Path> inputs = new ArrayList<>();
        for (var entry : stubs.entrySet()) {
            Path file = sources.resolve(entry.getKey()); Files.createDirectories(file.getParent());
            Files.writeString(file,entry.getValue()); inputs.add(file);
        }
        inputs.add(root.resolve("src/main/java/platform/inject/mixin/TitleScreenMixin.java"));
        inputs.add(root.resolve("src/main/java/aethereal/ui/screen/ProfilePanel.java"));
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        try (var manager = compiler.getStandardFileManager(null,Locale.ROOT,StandardCharsets.UTF_8)) {
            boolean ok = compiler.getTask(null,manager,null,List.of("--release","21","-d",classes.toString()),null,manager.getJavaFileObjectsFromPaths(inputs)).call();
            if (!ok) throw new IllegalStateException("Isolated edited-class compilation failed");
        }
        try (URLClassLoader loader = new URLClassLoader(new URL[]{classes.toUri().toURL()},ClassLoader.getPlatformClassLoader())) {
            loader.loadClass("NoAuthBehaviorTest").getMethod("main",String[].class).invoke(null,(Object)new String[0]);
        }
        Files.write(root.resolve("reports/no-auth-behavior-tests.txt"),List.of(
            "Edited TitleScreenMixin and ProfilePanel compile with minimal stubs.",
            "6 isolated behavior checks passed: direct menu, existing menu, screenshare, local profile, removed key controls, absent activation labels.",
            "Not a full build or Minecraft runtime integration test."));
    }
}
