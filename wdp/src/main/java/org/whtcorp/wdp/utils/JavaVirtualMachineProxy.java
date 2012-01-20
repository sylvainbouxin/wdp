package org.whtcorp.wdp.utils;

import javax.management.ObjectName;
import com.ibm.websphere.management.AdminClient;

public class JavaVirtualMachineProxy {
	
	private ObjectName mBean;
	private AdminClient adminClient;

	public JavaVirtualMachineProxy(AdminClient adminClient) throws Exception {
		this.adminClient = adminClient;
		queryAndGetJVMBean();
	}

	private void queryAndGetJVMBean() throws Exception {

		ObjectName on = new ObjectName ("WebSphere:type=JVM,*");
		mBean = (ObjectName) this.adminClient.queryNames (on, null).iterator().next();
	}

	public String getJavaVendor() throws Exception {
		return (String) adminClient.getAttribute(mBean, "javaVendor");
	}
	
	public String getJavaVersion() throws Exception {
		return (String) adminClient.getAttribute(mBean, "javaVersion");
	}
	
	public String getNode() throws Exception {
		return (String) adminClient.getAttribute(mBean, "node");
	}

	public String getFreeMemory() throws Exception {
		return (String) adminClient.getAttribute(mBean, "freeMemory");
	}
	
	public String getHeapSize() throws Exception {
		return (String) adminClient.getAttribute(mBean, "heapSize");
	}

	public Integer getMaxHeapDumpsOnDisk() throws Exception {
		return (Integer) adminClient.getAttribute(mBean, "maxHeapDumpsOnDisk");
	}
	
	public String getMaxMemory() throws Exception {
		return (String) adminClient.getAttribute(mBean, "maxMemory");
	}

	public Integer getMaxSystemDumpsOnDisk() throws Exception {
		return (Integer) adminClient.getAttribute(mBean, "maxSystemDumpsOnDisk");
	}
}
