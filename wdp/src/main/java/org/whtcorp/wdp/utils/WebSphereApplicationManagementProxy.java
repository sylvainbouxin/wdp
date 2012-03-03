package org.whtcorp.wdp.utils;

import java.io.Serializable;
import java.util.Hashtable;
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
import org.whtcorp.wdp.WebSphereTopologyModel;

import com.ibm.websphere.management.AdminClient;
import com.ibm.websphere.management.NotificationConstants;
import com.ibm.websphere.management.application.AppConstants;
import com.ibm.websphere.management.application.AppManagement;
import com.ibm.websphere.management.application.AppManagementProxy;
import com.ibm.websphere.management.application.AppNotification;

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
        if (application.getShared_library() != null) {
            bindingProperties.put(AppConstants.APPDEPL_RESOURCE_MAPPER_LIBRARY, application.getShared_library().getName());
        }

        applicationPreferences.put(AppConstants.APPDEPL_DFLTBNDG, bindingProperties);
        final String earLocation = ear_location + application.getApplicationName() + ".ear";
        final NotificationFilterSupport myFilter = new NotificationFilterSupport();
        myFilter.enableType(AppConstants.NotificationType);
        myFilter.enableType(NotificationConstants.TYPE_APPMANAGEMENT);
        final CountDownLatch latch = new CountDownLatch(1);
        final EventsNotificationListener listener = new EventsNotificationListener(adminClient, myFilter, "Install: " + application.getApplicationName(),
                AppNotification.INSTALL, latch);
        if (existingApp) {
            logger.info("Updating " + applicationName);
            final Vector applicationInfo = appManagement.getApplicationInfo(applicationName, null, null);

            // [[module, uri, server, ModuleVersion, moduletype, moduletypeDisplay], [bsal-services-webapp, bsal-services-webapp.war,WEB-INF/web.xml,
            // WebSphere:cell=wdd04515Node03Cell,node=wdd04515Node03,server=server1, 14, moduletype.web, Web Module]]
            // [[AppVersion, ModuleVersion, module, EJB, uri, referenceBinding, resRef.type, oracleRef, JNDI, login.config.name, auth.props, resAuth,
            // dataSourceProps], [13, 14, bsal-services-webapp, , bsal-services-webapp.war,WEB-INF/web.xml, config/bsal-services, java.lang.String,
            // AppDeploymentOption.No, config/bsal-services, null, , Container, ], [13, 14, bsal-services-webapp, , bsal-services-webapp.war,WEB-INF/web.xml,
            // jdbc/preferences-datasource, javax.sql.DataSource, AppDeploymentOption.No, preferences-datasource, null, , Container, ]][[AppVersion,
            // ModuleVersion, module, EJB, uri, referenceBinding, resRef.type, oracleRef, JNDI, login.config.name, auth.props, resAuth, dataSourceProps], [13,
            // 14, bsal-services-webapp, , bsal-services-webapp.war,WEB-INF/web.xml, config/bsal-services, java.lang.String, AppDeploymentOption.No,
            // config/bsal-services, null, , Container, ], [13, 14, bsal-services-webapp, , bsal-services-webapp.war,WEB-INF/web.xml,
            // jdbc/preferences-datasource, javax.sql.DataSource, AppDeploymentOption.No, preferences-datasource, null, , Container, ]]
            // appManagement.updateApplication(applicationName, "bsal-services-webapp.war,WEB-INF/web.xml", arg2, "update", arg4, null);
            appManagement.stopApplication(applicationName, null, null);
            // appManagement.redeployApplication(earLocation, applicationName, null, null);
            // final Hashtable options = new Hashtable();
            //
            // options.put(AppConstants.APPDEPL_LOCALE, Locale.getDefault());
            // options.put((AppConstants.APPUPDATE_CONTENTTYPE), AppConstants.APPUPDATE_CONTENT_APP);
            // appManagement.updateApplication(applicationName, null, earLocation, AppConstants.APPUPDATE_UPDATE, options, null);
            uninstallApplication(application.getApplicationName());
            appManagement.installApplication(earLocation, applicationName, applicationPreferences, null);
            latch.await();
            appManagement.setApplicationInfo(applicationName, null, null, applicationInfo);

        } else {
            logger.info("Deploying " + applicationName);
            appManagement.installApplication(earLocation, applicationName, applicationPreferences, null);

        }
        latch.await();

        final String started = startApplication(applicationName);

        if (started.startsWith("INFO")) {
            return "Application: " + applicationName + " installed, but failed to start.";
        } else {
            return "Application: " + applicationName + " installed and started.";
        }
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
