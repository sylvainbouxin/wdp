package org.whtcorp.wdp;

import java.util.List;

import org.whtcorp.wdp.utils.WebSphereApplicationManagementProxy;


/**
 * @goal application.start
 */
public class ApplicationStartMojo extends WebSphereAbstractMojo {

	/**
	 * @parameter
	 */
	private List<WebSphereApplicationModel> applications_list;

    @Override
	public void execute() {
		
		try {
			startApplications();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void startApplications() throws Exception {
		
		WebSphereApplicationManagementProxy applicationManagementProxy = new WebSphereApplicationManagementProxy(getAdminClient());
		getLog().info("------------------------------------------------------------------------");
		
		for(int i = 0; i < applications_list.size(); i++){
		
			WebSphereApplicationModel application = (WebSphereApplicationModel)applications_list.get(i);
			String started = applicationManagementProxy.startApplication(application.getApplicationName());
			
			if(started == null) {
				getLog().info("The application: " + application.getApplicationName() + " appears to be already started.");
			} else if (started.startsWith("INFO")) {
				getLog().info(started);
			} else {
				getLog().info("The application: " + application.getApplicationName() + " was started.");
			}
		}
		
		getLog().info("------------------------------------------------------------------------");
	}

}
