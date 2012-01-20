package org.whtcorp.wdp;

import java.util.List;

import org.whtcorp.wdp.utils.WebSphereApplicationManagementProxy;

/**
 * @goal application.uninstall
 */
public class ApplicationUninstallMojo extends WebSphereAbstractMojo {

	/**
	 * @parameter
	 */
	private List<WebSphereApplicationModel> applications_list;

    @Override
	public void execute() {
		
		try {
			uninstallApplication();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void uninstallApplication() throws Exception {
		
		WebSphereApplicationManagementProxy applicationManagementProxy = new WebSphereApplicationManagementProxy(getAdminClient());
		getLog().info("------------------------------------------------------------------------");
		
		for(int i = 0; i < applications_list.size(); i++){
			
			WebSphereApplicationModel application = (WebSphereApplicationModel)applications_list.get(i);
			String uninstalled = applicationManagementProxy.uninstallApplication(application.getApplicationName());

			getLog().info(uninstalled);
		}
		
		getLog().info("------------------------------------------------------------------------");
	}

}
