package com.harry.core;

import java.util.Map;

/**
 * Maps Services
 * 
 * @author harry
 * 
 */
public class ServiceMappings {

	private Map<String, CommonService> serviceMappings;

	
	// --------------------------
	// getters & setters
	// --------------------------
	public Map<String, CommonService> getServiceMappings() {
		return serviceMappings;
	}

	public void setServiceMappings(Map<String, CommonService> serviceMappings) {
		this.serviceMappings = serviceMappings;
	}

	public Object getService(String serviceName) {
		return this.serviceMappings.get(serviceName);
	}
}
