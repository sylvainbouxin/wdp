package org.whtcorp.wdp;

import org.whtcorp.wdp.utils.WebSphereConfigurationServiceProxy;

import java.util.List;

/**
 * @goal sharedlib.delete
 */
public class SharedLibraryDeleteMojo extends WebSphereAbstractMojo {

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
            deleteSharedLibrary();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteSharedLibrary() throws Exception {

        WebSphereConfigurationServiceProxy configurationServiceProxy = new WebSphereConfigurationServiceProxy(getAdminClient(), websphere_topology);
        getLog().info("------------------------------------------------------------------------");

        for (int i = 0; i < shared_library_list.size(); i++) {

            WebSphereSharedLibraryModel sharedLibrary = (WebSphereSharedLibraryModel)shared_library_list.get(i);

            getLog().info("Deleting SharedLibrary: " + sharedLibrary.getName());

            configurationServiceProxy.deleteSharedLibrary(sharedLibrary);
        }

        configurationServiceProxy.cleanUpSession();

        getLog().info("------------------------------------------------------------------------");
    }
}
