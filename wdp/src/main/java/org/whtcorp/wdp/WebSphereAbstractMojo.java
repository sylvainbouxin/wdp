package org.whtcorp.wdp;

import java.util.Properties;

import org.apache.maven.plugin.AbstractMojo;

import com.ibm.websphere.management.AdminClient;
import com.ibm.websphere.management.AdminClientFactory;

public abstract class WebSphereAbstractMojo extends AbstractMojo {

    /**
     * @parameter
     */
    private WebSphereAdminClientModel serverConnectionDefinition;
    private AdminClient adminClient;

    private void createAdminClientConnection() {

        final Properties props = new Properties();

        props.setProperty(AdminClient.CACHE_DISABLED, "false");
        props.setProperty(AdminClient.CONNECTOR_AUTO_ACCEPT_SIGNER, "true");
        props.setProperty(AdminClient.CONNECTOR_HOST, serverConnectionDefinition.getHostname());
        props.setProperty(AdminClient.CONNECTOR_PORT, serverConnectionDefinition.getPort());
        props.setProperty(AdminClient.CONNECTOR_TYPE, AdminClient.CONNECTOR_TYPE_SOAP);
        props.setProperty(AdminClient.PASSWORD, serverConnectionDefinition.getPassword());
        props.setProperty(AdminClient.USERNAME, serverConnectionDefinition.getUsername());

        if (serverConnectionDefinition.getConnector_security_enabled() != null) {
            props.setProperty(AdminClient.CONNECTOR_SECURITY_ENABLED, serverConnectionDefinition.getConnector_security_enabled());
        } else {
            props.setProperty(AdminClient.CONNECTOR_SECURITY_ENABLED, "true");
        }

        if (serverConnectionDefinition.getSsl_trustStore() != null) {
            props.setProperty("javax.net.ssl.trustStore", serverConnectionDefinition.getSsl_trustStore());
            props.setProperty("javax.net.ssl.keyStore", serverConnectionDefinition.getSsl_keyStore());
            props.setProperty("javax.net.ssl.trustStorePassword", serverConnectionDefinition.getSsl_trustStorePassword());
            props.setProperty("javax.net.ssl.keyStorePassword", serverConnectionDefinition.getSsl_keyStorePassword());
        }

        try {
            setAdminClient(AdminClientFactory.createAdminClient(props));
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public AdminClient getAdminClient() {
        createAdminClientConnection();
        return adminClient;
    }

    private void setAdminClient(final AdminClient adminClient) {
        this.adminClient = adminClient;
    }

    /**
     * This is the getter for serverConnectionDefinition.
     * @return the serverConnectionDefinition
     */
    public WebSphereAdminClientModel getServerConnectionDefinition() {
        return serverConnectionDefinition;
    }

}
