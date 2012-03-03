package org.whtcorp.wdp;


public class WebSphereApplicationModel {

    private String applicationName;
    private String applicationDescription;
    private WebSphereDataSourceModel data_source;
    private WebSphereSharedLibraryModel shared_library;

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

    public WebSphereSharedLibraryModel getShared_library() {
        return shared_library;
    }

    public void setShared_library(final WebSphereSharedLibraryModel shared_library) {
        this.shared_library = shared_library;
    }
}
