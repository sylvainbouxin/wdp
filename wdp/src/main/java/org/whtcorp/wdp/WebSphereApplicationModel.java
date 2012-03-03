package org.whtcorp.wdp;

import java.util.List;

public class WebSphereApplicationModel {
	
	private String applicationName;
	private String applicationDescription;
	private WebSphereDataSourceModel data_source;

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
}
