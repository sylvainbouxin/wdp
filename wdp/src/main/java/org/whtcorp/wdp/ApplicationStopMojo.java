package org.whtcorp.wdp;

import java.util.List;
import org.whtcorp.wdp.utils.WebSphereApplicationManagementProxy;

/**
 * @goal application.stop
 */
public class ApplicationStopMojo extends WebSphereAbstractMojo {

	/**
	 * @parameter
	 */
	private List<WebSphereApplicationModel> applications_list;

    @Override
	public void execute() {
		
		try {
			stopApplications();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void stopApplications() throws Exception {
		
		WebSphereApplicationManagementProxy applicationManagementProxy = new WebSphereApplicationManagementProxy(getAdminClient());
		getLog().info("------------------------------------------------------------------------");
		
		for(int i = 0; i < applications_list.size(); i++){
			
			WebSphereApplicationModel application = (WebSphereApplicationModel)applications_list.get(i);
			String stopped = applicationManagementProxy.stopApplication(application.getApplicationName());
			
			if(stopped == null) {
				getLog().info("The application: " + application.getApplicationName() + " appears to be already stopped.");
			} else if (stopped.startsWith("INFO")) {
				getLog().info(stopped);
			} else{
				getLog().info("The application: " + application.getApplicationName() + " was stopped.");
			}
		}
		
		getLog().info("------------------------------------------------------------------------");
	}
}
