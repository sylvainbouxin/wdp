package org.whtcorp.wdp;

import java.util.List;

import org.whtcorp.wdp.utils.WebSphereApplicationManagementProxy;

/**
 * @goal application.install
 */
public class ApplicationInstallMojo extends WebSphereAbstractMojo {

    /**
     * @parameter
     */
    private WebSphereTopologyModel websphere_topology;
    // /**
    // * @parameter
    // */
    // private String ear_location;
    /**
     * @parameter
     */
    private List<WebSphereApplicationModel> applications_list;

    @Override
    public void execute() {
        try {
            installApplication();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private void installApplication() throws Exception {

        final WebSphereApplicationManagementProxy applicationManagementProxy = new WebSphereApplicationManagementProxy(getAdminClient());
        getLog().info("------------------------------------------------------------------------");

        for (int i = 0; i < applications_list.size(); i++) {

            final WebSphereApplicationModel application = applications_list.get(i);

            getLog().info("Installing application: " + application.getApplicationName());

            final String installed = applicationManagementProxy.installApplication(application, websphere_topology, getServerConnectionDefinition()
                    .getEar_location(), getLog());

            getLog().info(installed);
        }

        getLog().info("------------------------------------------------------------------------");
    }

}
