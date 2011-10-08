package com.harry.core;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * ServiceManager provides a way to get access to all the services.
 * 
 * @author harry
 * 
 */
public class ServiceManager {

	private ServiceMappings serviceMappings;
	private static ServiceManager serviceManger = null;

	public static ServiceManager getInstance(ServletContext context) {
		if (serviceManger == null) {
			WebApplicationContext wac = WebApplicationContextUtils
					.getWebApplicationContext(context);

			serviceManger = (ServiceManager) wac.getBean(ServiceManager.class
					.getName());

		}
		
		return serviceManger;
	}

	public Object getService(String serviceName) {
		return this.serviceMappings.getService(serviceName);
	}

	public Object getService(Class<? extends CommonService> serviceClas) {
		return this.getService(serviceClas.getName());
	}

	public void setServiceMappings(ServiceMappings serviceMappings) {
		this.serviceMappings = serviceMappings;
	}
}
