package org.whtcorp.wdp.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.management.NotificationFilterSupport;
import javax.management.NotificationListener;
import javax.management.ObjectName;

import org.apache.maven.plugin.logging.Log;
import org.whtcorp.wdp.WebSphereApplicationModel;
import org.whtcorp.wdp.WebSphereJNDIModel;
import org.whtcorp.wdp.WebSphereSharedLibraryBindingModel;
import org.whtcorp.wdp.WebSphereTopologyModel;

import com.ibm.websphere.management.AdminClient;
import com.ibm.websphere.management.NotificationConstants;
import com.ibm.websphere.management.application.AppConstants;
import com.ibm.websphere.management.application.AppManagement;
import com.ibm.websphere.management.application.AppManagementProxy;
import com.ibm.websphere.management.application.AppNotification;
import com.ibm.websphere.management.application.client.AppDeploymentException;
import com.ibm.websphere.management.application.client.WASDeploymentTask;
import com.ibm.ws.management.application.client.MapResRefToEJB;
import com.ibm.ws.management.application.client.MapResRefToEJBHelper;
import com.ibm.ws.management.application.client.MapWebModToVH;

public class WebSphereApplicationManagementProxy {

    private final AdminClient adminClient;
    private final AppManagement appManagement;
    private final Hashtable applicationPreferences;

    public WebSphereApplicationManagementProxy(final AdminClient adminClient) throws Exception {

        this.adminClient = adminClient;
        appManagement = AppManagementProxy.getJMXProxyForClient(adminClient);
        applicationPreferences = new Hashtable<String, Serializable>();
    }

    public String startApplication(final String applicationName) throws Exception {

        queryAndGetApplicationManagementBean();

        if (appManagement.checkIfAppExists(applicationName, applicationPreferences, null)) {
            return appManagement.startApplication(applicationName, applicationPreferences, null);
        } else {
            return "INFO --- This application is not deployed on this server ---";
        }
    }

    public String stopApplication(final String applicationName) throws Exception {

        queryAndGetApplicationManagementBean();
        if (appManagement.checkIfAppExists(applicationName, applicationPreferences, null)) {
            return appManagement.stopApplication(applicationName, applicationPreferences, null);
        } else {
            return "INFO --- This application is not deployed on this server ---";
        }
    }

    public String installApplication(final WebSphereApplicationModel application, final WebSphereTopologyModel websphere_topology, final String ear_location,
            final Log logger) throws Exception {

        queryAndGetApplicationManagementBean();
        String targetTopology;

        if (websphere_topology.getWebsphere_cluster_name() != null) {
            targetTopology = "WebSphere:cell=" + websphere_topology.getWebsphere_cell_name() + ",cluster=" + websphere_topology.getWebsphere_cluster_name()
                    + ",node=" + websphere_topology.getWebsphere_node_name() + ",server=" + websphere_topology.getWebsphere_server_name();
        } else {
            targetTopology = "WebSphere:cell=" + websphere_topology.getWebsphere_cell_name() + ",node=" + websphere_topology.getWebsphere_node_name()
                    + ",server=" + websphere_topology.getWebsphere_server_name();
        }

        final Hashtable<String, String> module2server = new Hashtable<String, String>();
        module2server.put("*", targetTopology);
        applicationPreferences.put(AppConstants.APPDEPL_MODULE_TO_SERVER, module2server);
        final String applicationName = application.getApplicationName();
        final boolean existingApp = appManagement.checkIfAppExists(applicationName, applicationPreferences, null);

        final Properties bindingProperties = new Properties();
        bindingProperties.put(AppConstants.APPDEPL_DFLTBNDG_VHOST, "default_host");
        // TO REMOVE if (application.getShared_library() != null) {
        // bindingProperties.put(AppConstants.APPDEPL_RESOURCE_MAPPER_LIBRARY, application.getShared_library().getName());
        // }

        applicationPreferences.put(AppConstants.APPDEPL_DFLTBNDG, bindingProperties);
        final String earLocation = ear_location + application.getApplicationName() + ".ear";
        final NotificationFilterSupport myFilter = new NotificationFilterSupport();
        myFilter.enableType(AppConstants.NotificationType);
        myFilter.enableType(NotificationConstants.TYPE_APPMANAGEMENT);
        final CountDownLatch latch = new CountDownLatch(1);
        final EventsNotificationListener listener = new EventsNotificationListener(adminClient, myFilter, "Install: " + application.getApplicationName(),
                AppNotification.INSTALL, latch);
        if (existingApp) {
            // logger.info("Updating " + applicationName);
            appManagement.stopApplication(applicationName, null, null);

            uninstallApplication(application.getApplicationName());

        }
        appManagement.installApplication(earLocation, applicationName, applicationPreferences, null);
        latch.await();
        final Vector applicationInfoNew = appManagement.getApplicationInfo(applicationName, null, null);
        mapVirtualHost(applicationInfoNew);
        if ((application.getResource_binding_list() != null) && (application.getResource_binding_list().size() > 0)) {
            mapResourceBindings(applicationInfoNew, application.getResource_binding_list());
        }
        if ((application.getShared_libraries_binding() != null) && (application.getShared_libraries_binding().size() > 0)) {
            mapSharedLibs(applicationInfoNew, application.getShared_libraries_binding());
        }
        appManagement.setApplicationInfo(applicationName, null, null, applicationInfoNew);

        final String started = startApplication(applicationName);

        if (started.startsWith("INFO")) {
            return "Application: " + applicationName + " installed, but failed to start.";
        } else {
            return "Application: " + applicationName + " installed and started.";
        }
    }

    private void mapResourceBindings(final Vector appInfos, final List<WebSphereJNDIModel> resource_binding_list) {
        for (int i = 0; i < appInfos.size(); i++) {
            if (appInfos.get(i) instanceof MapResRefToEJB) {
                final MapResRefToEJB resRef = (MapResRefToEJB) appInfos.get(i);
                try {
                    updateMapResRefTaskData(resRef, resource_binding_list);
                } catch (final AppDeploymentException e) {
                    e.printStackTrace();
                }
                // System.out.println(resRef);
            }
        }
    }

    private void mapVirtualHost(final Vector appInfos) throws AppDeploymentException {
        for (int i = 0; i < appInfos.size(); i++) {
            if (appInfos.get(i) instanceof MapWebModToVH) {
                final MapWebModToVH mapWebModToVH = (MapWebModToVH) appInfos.get(i);
                final String[][] myTaskData = new String[mapWebModToVH.getTaskData().length][mapWebModToVH.getColumnNames().length];
                for (int cpt = 0; cpt < mapWebModToVH.getTaskData().length; cpt++) {
                    for (int j = 0; j < mapWebModToVH.getColumnNames().length; j++) {
                        myTaskData[cpt][j] = mapWebModToVH.getTaskData()[cpt][j];
                    }
                }
                myTaskData[mapWebModToVH.getTaskData().length - 1][2] = "default_host";
                mapWebModToVH.setTaskData(myTaskData);
                System.out.println();
            }
        }
    }

    private void mapSharedLibs(final Vector applicationInfo, final List<WebSphereSharedLibraryBindingModel> sharedLibsBinding) throws AppDeploymentException {
        for (final Object task : applicationInfo) {
            if ((task instanceof WASDeploymentTask) && ((WASDeploymentTask) task).getName().equals(AppConstants.MapSharedLibForModTask)) {
                final WASDeploymentTask mapSharedLibsFormModsTask = ((WASDeploymentTask) task);

                String sharedLibName = null;
                // final boolean isSharedClassLoader = true;
                //
                // final String s1 = "WebSphere:";
                // final String s2 = "name=" + sharedLibName;
                // final String s3 = ",isSharedClassloader=" + isSharedClassLoader;
                // final String finalString = s1 + s2 + s3;
                // System.out.println(finalString);

                int module_index = -1;
                int uri_index = -1;
                int sharedLibName_index = -1;
                final String[][] myTaskData = new String[mapSharedLibsFormModsTask.getTaskData().length][mapSharedLibsFormModsTask.getColumnNames().length];
                for (int i = 0; i < mapSharedLibsFormModsTask.getTaskData().length; i++) {
                    if (i == 0) {
                        for (int j = 0; j < mapSharedLibsFormModsTask.getColumnNames().length; j++) {
                            // Column names
                            myTaskData[i][j] = mapSharedLibsFormModsTask.getTaskData()[i][j];
                            if (mapSharedLibsFormModsTask.getTaskData()[i][j].equals(AppConstants.APPDEPL_MODULE)) {
                                module_index = j;
                            }
                            if (mapSharedLibsFormModsTask.getTaskData()[i][j].equals(AppConstants.APPDEPL_URI)) {
                                uri_index = j;
                            }
                            if (mapSharedLibsFormModsTask.getTaskData()[i][j].equals(AppConstants.APPDEPL_SHAREDLIB_NAME)) {
                                sharedLibName_index = j;
                            }

                        }
                    } else {
                        for (int j = 0; j < mapSharedLibsFormModsTask.getColumnNames().length; j++) {
                            final String module = mapSharedLibsFormModsTask.getTaskData()[i][module_index];
                            final String uri = mapSharedLibsFormModsTask.getTaskData()[i][uri_index];
                            final String shareLibName = mapSharedLibsFormModsTask.getTaskData()[i][sharedLibName_index];
                            myTaskData[i][module_index] = module;
                            myTaskData[i][uri_index] = uri;
                            // Iterate over sharedLibBindings
                            for (final WebSphereSharedLibraryBindingModel sharedLibBinding : sharedLibsBinding) {
                                if (sharedLibBinding.getModule().equals(module)) {
                                    if ((sharedLibName == null) || sharedLibName.isEmpty()) {
                                        sharedLibName = "WebSphere:name=" + sharedLibBinding.getName() + ",isSharedClassloader="
                                                + sharedLibBinding.isSharedClassLoader();
                                    } else {
                                        if (sharedLibName.startsWith("WebSphere:name")) {
                                            sharedLibName = sharedLibName + "+WebSphere:name=" + sharedLibBinding.getName() + ",isSharedClassloader="
                                                    + sharedLibBinding.isSharedClassLoader();
                                        }
                                    }
                                }
                            }
                            myTaskData[i][sharedLibName_index] = sharedLibName;
                        }
                    }
                }
                mapSharedLibsFormModsTask.setTaskData(myTaskData);
            }
        }
    }

    private void updateMapResRefTaskData(final MapResRefToEJB resRef, final List<WebSphereJNDIModel> resource_binding_list) throws AppDeploymentException {
        final List<String> map = new ArrayList<String>();
        final MapResRefToEJBHelper help = new MapResRefToEJBHelper();
        if ((resRef == null) || (resRef.getTaskData() == null)) {
            return;
        }
        if (resRef.getTaskData().length <= 1) {
            return;
        }
        final String[][] myTaskData = new String[resRef.getTaskData().length][resRef.getColumnNames().length];
        // String[] myTaskData = new String[resRef.getTaskData().length];
        int ref_binding_index = -1;
        int jndi_index = -1;
        for (int i = 0; i < resRef.getTaskData().length; i++) {
            if (i == 0) {
                for (int j = 0; j < resRef.getColumnNames().length; j++) {
                    // Column names
                    myTaskData[i][j] = resRef.getTaskData()[i][j];
                    if (resRef.getTaskData()[i][j].equals(AppConstants.APPDEPL_REFERENCE_BINDING)) {
                        ref_binding_index = j;
                    }
                    if (resRef.getTaskData()[i][j].equals(AppConstants.APPDEPL_JNDI)) {
                        jndi_index = j;
                    }

                }
            } else {
                for (int j = 0; j < resRef.getColumnNames().length; j++) {
                    if (j != jndi_index) {
                        myTaskData[i][j] = resRef.getTaskData()[i][j];
                    } else {
                        for (final WebSphereJNDIModel resourceBinding : resource_binding_list) {
                            if (resRef.getTaskData()[i][ref_binding_index].equals(resourceBinding.getBindingIdentifier())) {
                                myTaskData[i][jndi_index] = resourceBinding.getValue();
                            }
                        }
                    }
                }
            }
        }
        resRef.setTaskData(myTaskData);
    }

    public String uninstallApplication(final String applicationName) throws Exception {

        queryAndGetApplicationManagementBean();

        if (appManagement.checkIfAppExists(applicationName, applicationPreferences, null)) {

            final NotificationFilterSupport myFilter = new NotificationFilterSupport();
            myFilter.enableType(AppConstants.NotificationType);
            final CountDownLatch latch = new CountDownLatch(1);
            final NotificationListener listener = new EventsNotificationListener(adminClient, myFilter, "Install: " + applicationName,
                    AppNotification.UNINSTALL, latch);

            appManagement.uninstallApplication(applicationName, applicationPreferences, null);

            latch.await(10, TimeUnit.SECONDS);
            return "Application: " + applicationName + " un-installed.";
        } else {
            return "Application: " + applicationName + " is not installed on this server.";
        }
    }

    private void queryAndGetApplicationManagementBean() throws Exception {

        applicationPreferences.put(AppConstants.APPDEPL_LOCALE, Locale.getDefault());

        final ObjectName on = new ObjectName("WebSphere:type=AppManagement,*");
        final ObjectName appmgmtON = (ObjectName) adminClient.queryNames(on, null).iterator().next();
    }
}
