package org.whtcorp.wdp;

import java.util.List;

import org.whtcorp.wdp.utils.WebSphereApplicationManagementProxy;
import org.whtcorp.wdp.utils.WebSphereConfigurationServiceProxy;

/**
 * @goal application.install
 */
public class ApplicationInstallMojo extends WebSphereAbstractMojo {

    /**
     * @parameter
     */
    private WebSphereTopologyModel websphere_topology;
    /**
     * @parameter
     */
    private String ear_location;
    /**
     * @parameter
     */
    private List<WebSphereApplicationModel> applications_list;

    @Override
    public void execute() {
        try {
            installApplication();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void installApplication() throws Exception {

        WebSphereApplicationManagementProxy applicationManagementProxy = new WebSphereApplicationManagementProxy(getAdminClient());
        getLog().info("------------------------------------------------------------------------");

        for (int i = 0; i < applications_list.size(); i++) {

            WebSphereApplicationModel application = (WebSphereApplicationModel) applications_list.get(i);

            getLog().info("Installing application: " + application.getApplicationName());

            String installed = applicationManagementProxy.installApplication(application.getApplicationName(), websphere_topology, ear_location);

            getLog().info(installed);
        }

        getLog().info("------------------------------------------------------------------------");
    }

}
