package org.whtcorp.wdp;

import java.util.List;

public class WebSphereApplicationModel {
	
	private String applicationName;
	private String applicationDescription;
	private WebSphereDataSourceModel data_source;
    private WebSphereSharedLibraryModel shared_library;

	public String getApplicationName() {
		return applicationName;
	}
	
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	
	public String getApplicationDescription() {
		return applicationDescription;
	}
	
	public void setApplicationDescription(String applicationDescription) {
		this.applicationDescription = applicationDescription;
	}

    public WebSphereDataSourceModel getData_source() {
        return data_source;
    }

    public void setData_source(WebSphereDataSourceModel data_source) {
        this.data_source = data_source;
    }

    public WebSphereSharedLibraryModel getShared_library() {
        return shared_library;
    }

    public void setShared_library(WebSphereSharedLibraryModel shared_library) {
        this.shared_library = shared_library;
    }
}
