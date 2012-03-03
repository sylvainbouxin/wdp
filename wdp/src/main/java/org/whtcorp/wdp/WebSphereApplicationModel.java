package org.whtcorp.wdp;

import java.util.List;

public class WebSphereApplicationModel {

    /**
     * This attribute represent the name of the application. The name of the ear that has to be deployedfile must correspond to that name followed by .ear This
     * attribute is required.
     */
    private String applicationName;
    /**
     * This optional attribute represents the application description.
     */
    private String applicationDescription;
    /**
     * This optional attribute represents the data source that has to be linked to the application.
     */
    private WebSphereDataSourceModel data_source;
    /**
     * This optional List of {@link WebSphereSharedLibraryBindingModel} defines all the shared libraries that have to be linked with the application.
     */
    private List<WebSphereSharedLibraryBindingModel> shared_libraries_binding;
    /**
     * This optional List of {@link WebSphereJNDIModel} defines all the resources (JNDI) that have to be linked with the application.
     */
    private List<WebSphereJNDIModel> resource_binding_list;
    /**
     * This attribute defines the location of the ear file on the remote server.
     */
    private String ear_location;

    /**
     * This is the getter for applicationName.
     * @return the applicationName
     */
    public String getApplicationName() {
        return applicationName;
    }

    /**
     * This is the setter for applicationName.
     * @param applicationName the applicationName to set
     */
    public void setApplicationName(final String applicationName) {
        this.applicationName = applicationName;
    }

    /**
     * This is the getter for applicationDescription.
     * @return the applicationDescription
     */
    public String getApplicationDescription() {
        return applicationDescription;
    }

    /**
     * This is the setter for applicationDescription.
     * @param applicationDescription the applicationDescription to set
     */
    public void setApplicationDescription(final String applicationDescription) {
        this.applicationDescription = applicationDescription;
    }

    /**
     * This is the getter for data_source.
     * @return the data_source
     */
    public WebSphereDataSourceModel getData_source() {
        return data_source;
    }

    /**
     * This is the setter for data_source.
     * @param data_source the data_source to set
     */
    public void setData_source(final WebSphereDataSourceModel data_source) {
        this.data_source = data_source;
    }

    /**
     * This is the getter for shared_libraries_binding.
     * @return the shared_libraries_binding
     */
    public List<WebSphereSharedLibraryBindingModel> getShared_libraries_binding() {
        return shared_libraries_binding;
    }

    /**
     * This is the setter for shared_libraries_binding.
     * @param shared_libraries_binding the shared_libraries_binding to set
     */
    public void setShared_libraries_binding(final List<WebSphereSharedLibraryBindingModel> shared_libraries_binding) {
        this.shared_libraries_binding = shared_libraries_binding;
    }

    /**
     * This is the getter for resource_binding_list.
     * @return the resource_binding_list
     */
    public List<WebSphereJNDIModel> getResource_binding_list() {
        return resource_binding_list;
    }

    /**
     * This is the setter for resource_binding_list.
     * @param resource_binding_list the resource_binding_list to set
     */
    public void setResource_binding_list(final List<WebSphereJNDIModel> resource_binding_list) {
        this.resource_binding_list = resource_binding_list;
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
