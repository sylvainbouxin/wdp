package org.whtcorp.wdp;

public class WebSphereJNDIModel {
	
	private String bindingIdentifier;
	private String name;
	private String value;
	
	public String getBindingIdentifier() {
		return bindingIdentifier;
	}
	
	public void setBindingIdentifier(String bindingIdentifier) {
		this.bindingIdentifier = bindingIdentifier;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
