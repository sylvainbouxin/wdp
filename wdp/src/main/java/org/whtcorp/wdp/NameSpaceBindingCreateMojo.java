package org.whtcorp.wdp;

import org.whtcorp.wdp.utils.WebSphereConfigurationServiceProxy;

import java.util.List;

/**
 * @goal namespace.binding.create
 */
public class NameSpaceBindingCreateMojo extends WebSphereAbstractMojo {

    /**
     * @parameter
     */
    private WebSphereTopologyModel websphere_topology;

    /**
     * @parameter
     */
    private List<WebSphereStringNameSpaceBindingModel> name_space_binding_list;

    @Override
    public void execute() {

        try {
            createNameSpaceBinding();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createNameSpaceBinding() throws Exception {

        WebSphereConfigurationServiceProxy configurationServiceProxy = new WebSphereConfigurationServiceProxy(getAdminClient(), websphere_topology);
        getLog().info("------------------------------------------------------------------------");

        for (int i = 0; i < name_space_binding_list.size(); i++) {

            WebSphereStringNameSpaceBindingModel namespaceBinding = (WebSphereStringNameSpaceBindingModel)name_space_binding_list.get(i);

            getLog().info("Creating StringNameSpaceBinding: " + namespaceBinding.getName());

            configurationServiceProxy.createStringNameSpaceBinding(namespaceBinding);
        }

        configurationServiceProxy.cleanUpSession();

        getLog().info("------------------------------------------------------------------------");
    }
}
