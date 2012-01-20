package org.whtcorp.wdp;

import org.whtcorp.wdp.utils.WebSphereConfigurationServiceProxy;

import java.util.List;

/**
 * @goal sharedlib.create
 */
public class SharedLibraryCreateMojo extends WebSphereAbstractMojo {

    /**
     * @parameter
     */
    private WebSphereTopologyModel websphere_topology;

    /**
     * @parameter
     */
    private List<WebSphereSharedLibraryModel> shared_library_list;

    @Override
    public void execute() {
        
        try {
            createSharedLibrary();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createSharedLibrary() throws Exception {

        WebSphereConfigurationServiceProxy configurationServiceProxy = new WebSphereConfigurationServiceProxy(getAdminClient(), websphere_topology);
        getLog().info("------------------------------------------------------------------------");

        for (int i = 0; i < shared_library_list.size(); i++) {

            WebSphereSharedLibraryModel sharedLibrary = (WebSphereSharedLibraryModel)shared_library_list.get(i);

            getLog().info("Creating SharedLibrary: " + sharedLibrary.getName());

            configurationServiceProxy.createSharedLibrary(sharedLibrary);
        }

        configurationServiceProxy.cleanUpSession();

        getLog().info("------------------------------------------------------------------------");
    }
}
