/**
 * 
 */
package com.smthit.framework.api.utils;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;


/**
 * @author Bean
 * @since 1.0.0
 */
public class WebAppServerUtils {

	public static Server createWebServer(ServerContext context) {
    	Server server = new Server(context.getPort());
    	
    	WebAppContext webAppContext = new WebAppContext(); 
        webAppContext.setContextPath(context.getContextPath());  
        webAppContext.setDescriptor(context.getDescriptor());  
        webAppContext.setResourceBase(context.getResourceBase());  
        webAppContext.setConfigurationDiscovered(context.isConfigurationDiscovered());  
        webAppContext.setParentLoaderPriority(context.isParentLoaderPriority());  
        
        server.setHandler(webAppContext);
        
        return server;
	} 
}
