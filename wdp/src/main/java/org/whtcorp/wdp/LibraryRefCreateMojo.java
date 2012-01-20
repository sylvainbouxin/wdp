package org.whtcorp.wdp;

import org.whtcorp.wdp.utils.WebSphereConfigurationServiceProxy;

import java.util.List;

/**
 * @goal library.ref.create
 */

public class LibraryRefCreateMojo extends WebSphereAbstractMojo {

    /**
     * @parameter
     */
    private WebSphereTopologyModel websphere_topology;

    /**
     * @parameter
     */
    private List<WebSphereApplicationModel> applications_list;

    @Override
    public void execute() {

        try {
            createLibraryRef();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createLibraryRef() throws Exception {

        WebSphereConfigurationServiceProxy configurationServiceProxy = new WebSphereConfigurationServiceProxy(getAdminClient(), websphere_topology);
        getLog().info("------------------------------------------------------------------------");

        for (int i = 0; i < applications_list.size(); i++) {

            WebSphereApplicationModel application = (WebSphereApplicationModel) applications_list.get(i);

            getLog().info("Creating the library reference " + application.getData_source().getName() + " for the application: " + application.getData_source().getJndiName());

            configurationServiceProxy.createLibraryReferenceForApplication(application.getApplicationName(), application.getData_source().getJndiName());
        }

        configurationServiceProxy.cleanUpSession();

        getLog().info("------------------------------------------------------------------------");
    }
}
