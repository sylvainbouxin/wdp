package org.whtcorp.wdp;

import java.util.List;

public class WebSphereApplicationModel {

    private String applicationName;
    private String applicationDescription;
    private WebSphereDataSourceModel data_source;
    private List<WebSphereSharedLibraryBindingModel> shared_libraries_binding;
    private List<WebSphereJNDIModel> resource_binding_list;
    private String ear_location;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(final String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationDescription() {
        return applicationDescription;
    }

    public void setApplicationDescription(final String applicationDescription) {
        this.applicationDescription = applicationDescription;
    }

    public WebSphereDataSourceModel getData_source() {
        return data_source;
    }

    public void setData_source(final WebSphereDataSourceModel data_source) {
        this.data_source = data_source;
    }

    public List<WebSphereJNDIModel> getResource_binding_list() {
        return resource_binding_list;
    }

    public void setResource_binding_list(final List<WebSphereJNDIModel> resource_binding_list) {
        this.resource_binding_list = resource_binding_list;
    }

    public List<WebSphereSharedLibraryBindingModel> getShared_libraries_binding() {
        return shared_libraries_binding;
    }

    public void setShared_libraries_binding(final List<WebSphereSharedLibraryBindingModel> shared_libraries_binding) {
        this.shared_libraries_binding = shared_libraries_binding;
    }

    /**
     * This is the getter for ear_location.
     * @return the ear_location
     */
    public String getEar_location() {
        return ear_location;
    }

    /**
     * This is the setter for ear_location.
     * @param ear_location the ear_location to set
     */
    public void setEar_location(final String ear_location) {
        this.ear_location = ear_location;
    }

}
