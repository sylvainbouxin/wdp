package org.whtcorp.wdp;

public class WebSphereAdminClientModel {
	
	private String username;
	private String password;
	private String hostname;
	private String port;
	private String connector_security_enabled;
	private String ssl_trustStore;
	private String ssl_keyStore;
	private String ssl_trustStorePassword;
	private String ssl_keyStorePassword;
	private String ear_location;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getHostname() {
		return hostname;
	}
	
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	
	public String getPort() {
		return port;
	}
	
	public void setPort(String port) {
		this.port = port;
	}
	
	public String getConnector_security_enabled() {
		return connector_security_enabled;
	}
	
	public void setConnector_security_enabled(String connector_security_enabled) {
		this.connector_security_enabled = connector_security_enabled;
	}
	
	public String getSsl_trustStore() {
		return ssl_trustStore;
	}
	
	public void setSsl_trustStore(String ssl_trustStore) {
		this.ssl_trustStore = ssl_trustStore;
	}
	
	public String getSsl_keyStore() {
		return ssl_keyStore;
	}
	
	public void setSsl_keyStore(String ssl_keyStore) {
		this.ssl_keyStore = ssl_keyStore;
	}
	
	public String getSsl_trustStorePassword() {
		return ssl_trustStorePassword;
	}
	
	public void setSsl_trustStorePassword(String ssl_trustStorePassword) {
		this.ssl_trustStorePassword = ssl_trustStorePassword;
	}
	
	public String getSsl_keyStorePassword() {
		return ssl_keyStorePassword;
	}
	
	public void setSsl_keyStorePassword(String ssl_keyStorePassword) {
		this.ssl_keyStorePassword = ssl_keyStorePassword;
	}
	
	public String getEar_location() {
		return ear_location;
	}
	
	public void setEar_location(String ear_location) {
		this.ear_location = ear_location;
	}
}
