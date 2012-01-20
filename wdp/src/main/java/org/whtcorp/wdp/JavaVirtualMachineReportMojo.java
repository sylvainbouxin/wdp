package org.whtcorp.wdp;

import org.whtcorp.wdp.utils.JavaVirtualMachineProxy;

/**
 * @goal jvm.report
 */
public class JavaVirtualMachineReportMojo extends WebSphereAbstractMojo {
	
	private JavaVirtualMachineProxy jvm;
	
	public void execute() {
		try {
			printJVMMemoryUsage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	private void printJVMMemoryUsage() throws Exception {

		jvm = new JavaVirtualMachineProxy(getAdminClient());

		getLog().info("------------------------------------------------------------------------");
		getLog().info("Java Vendor: " + jvm.getJavaVendor());
		getLog().info("Java Version: " + jvm.getJavaVersion());
		getLog().info("Node: " + jvm.getNode());
		getLog().info("Free Memory: " + jvm.getFreeMemory());
		getLog().info("HeapSize: " + jvm.getHeapSize());
		getLog().info("Max Heap Dumps on Disk: " + jvm.getMaxHeapDumpsOnDisk());
		getLog().info("Max Memory: " + jvm.getMaxMemory());
		getLog().info("Max System Dumps on Disk: " + jvm.getMaxSystemDumpsOnDisk());
		getLog().info("------------------------------------------------------------------------");
	}
}
