package org.whtcorp.wdp;

import org.whtcorp.wdp.utils.WebSphereConfigurationServiceProxy;

/**
 * @goal object.type.query
 */
public class ObjectTypesQueryMojo extends WebSphereAbstractMojo {

    /**
     * @parameter
     */
    private WebSphereObjectTypeModel webSphere_object_type;

    @Override
    public void execute() {

        try {
            queryObjectType();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void queryObjectType() throws Exception {

        WebSphereConfigurationServiceProxy configurationServiceProxy = new WebSphereConfigurationServiceProxy(getAdminClient());

        configurationServiceProxy.queryObjectType(webSphere_object_type.getObject_type());
    }

}
