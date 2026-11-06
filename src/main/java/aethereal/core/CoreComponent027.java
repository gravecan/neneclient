package aethereal.core;

import aethereal.api.ApiAnnotation002;
import aethereal.lib.log4j.Log4jComponent144;
import aethereal.lib.log4j.Log4jComponent150;
import aethereal.lib.log4j.Log4jContract145;
import aethereal.lib.log4j.Log4jContract148;
import aethereal.util.UtilComponent285;
import java.net.URL;
import java.security.Permission;
import java.util.Collection;
import org.osgi.annotation.bundle.Header;
import org.osgi.annotation.bundle.Headers;
import org.osgi.framework.AdaptPermission;
import org.osgi.framework.AdminPermission;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.SynchronousBundleListener;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;

@Headers({@Header(name = "Bundle-Activator", value = "${@class}"), @Header(name = "Bundle-ActivationPolicy", value = "lazy")})
@ApiAnnotation002
public class CoreComponent027 implements BundleActivator, SynchronousBundleListener {
   private static final SecurityManager securityManagerField1 = System.getSecurityManager();
   private static final Log4jComponent144 log4jComponent144Field2 = Log4jComponent150.getLog4jComponent144Field1_1323();
   private boolean flagField3;

   private static void performAction_0578(Permission permission) {
      if (securityManagerField1 != null) {
         securityManagerField1.checkPermission(permission);
      }
   }

   private void performAction_0579(Bundle bundle) {
      if (bundle.getState() != 1) {
         try {
            performAction_0578(new AdminPermission(bundle, "resource"));
            performAction_0578(new AdaptPermission(BundleWiring.class.getName(), bundle, "adapt"));
            BundleContext bundleContext = bundle.getBundleContext();
            if (bundleContext == null) {
               log4jComponent144Field2.performAction_1316(
                  "Bundle {} has no context (state={}), skipping loading provider", bundle.getSymbolicName(), this.computeValue_0577(bundle.getState())
               );
            } else {
               this.performAction_0580(bundleContext, (BundleWiring)bundle.adapt(BundleWiring.class));
            }
         } catch (SecurityException securityException) {
            log4jComponent144Field2.performAction_1316("Cannot access bundle [{}] contents. Ignoring.", bundle.getSymbolicName(), securityException);
         } catch (Exception exception) {
            log4jComponent144Field2.performAction_1314("Problem checking bundle {} for Log4j 2 provider.", bundle.getSymbolicName(), exception);
         }
      }
   }

   private String computeValue_0577(int i) {
      switch (i) {
         case 1:
            return "UNINSTALLED";
         case 2:
            return "INSTALLED";
         case 4:
            return "RESOLVED";
         case 8:
            return "STARTING";
         case 16:
            return "STOPPING";
         case 32:
            return "ACTIVE";
         default:
            return Integer.toString(i);
      }
   }

   private void performAction_0580(BundleContext bundleContext, BundleWiring bundleWiring) {
      try {
         Collection collection = bundleContext.getServiceReferences(Log4jContract148.class, "(APIVersion>=2.6.0)");
         Log4jContract148 log4jContract148 = null;

         for (ServiceReference serviceReference : collection) {
            Log4jContract148 log4jContract1481 = (Log4jContract148)bundleContext.getService(serviceReference);
            if (log4jContract148 == null || log4jContract1481.computeValue_1320() > log4jContract148.computeValue_1320()) {
               log4jContract148 = log4jContract1481;
            }
         }

         if (log4jContract148 != null) {
            UtilComponent285.performAction_3620(log4jContract148);
         }
      } catch (InvalidSyntaxException invalidSyntaxException) {
         log4jComponent144Field2.performAction_1315("Invalid service filter: (APIVersion>=2.6.0)", invalidSyntaxException);
      }

      for (URL uRL : bundleWiring.findEntries("META-INF", "log4j-provider.properties", 0)) {
         UtilComponent285.performAction_3622(uRL, bundleWiring.getClassLoader());
      }
   }

   public void start(BundleContext bundleContext) throws Exception {
      UtilComponent285.reentrantLockField2.lock();
      this.flagField3 = true;
      BundleWiring bundleWiring = (BundleWiring)bundleContext.getBundle().adapt(BundleWiring.class);

      for (BundleWire bundleWire : bundleWiring.getRequiredWires(Log4jContract145.class.getName())) {
         this.performAction_0580(bundleContext, bundleWire.getProviderWiring());
      }

      bundleContext.addBundleListener(this);
      Bundle[] bundles = bundleContext.getBundles();

      for (Bundle bundle : bundles) {
         this.performAction_0579(bundle);
      }

      this.performAction_0576();
   }

   private void performAction_0576() {
      if (this.flagField3 && !UtilComponent285.listField1.isEmpty()) {
         UtilComponent285.reentrantLockField2.unlock();
         this.flagField3 = false;
      }
   }

   public void stop(BundleContext bundleContext) throws Exception {
      bundleContext.removeBundleListener(this);
      this.performAction_0576();
   }

   public void performAction_0581(BundleEvent bundleEvent) {
      this.bundleChanged(bundleEvent);
   }

   public void bundleChanged(BundleEvent bundleEvent) {
      switch (bundleEvent.getType()) {
         case 2:
            this.performAction_0579(bundleEvent.getBundle());
            this.performAction_0576();
      }
   }
}
