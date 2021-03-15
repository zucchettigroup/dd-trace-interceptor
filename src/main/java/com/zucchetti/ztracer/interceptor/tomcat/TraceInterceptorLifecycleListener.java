package com.zucchetti.ztracer.interceptor.tomcat;

import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;

import com.zucchetti.ztracer.interceptor.InterceptorType;
import com.zucchetti.ztracer.interceptor.InterceptorsConfigurator;
import com.zucchetti.ztracer.interceptor.TraceInterceptorFactory;
import com.zucchetti.ztracer.interceptor.TraceInterceptors;
import com.zucchetti.ztracer.interceptor.logger.LoggerFactory;

import datadog.trace.api.interceptor.TraceInterceptor;

/**
 * ELENCO STATI DISPONIBILI: 
    INITIALIZING(false, Lifecycle.BEFORE_INIT_EVENT),
    INITIALIZED(false, Lifecycle.AFTER_INIT_EVENT),
    STARTING_PREP(false, Lifecycle.BEFORE_START_EVENT),
    STARTING(true, Lifecycle.START_EVENT),
    STARTED(true, Lifecycle.AFTER_START_EVENT),
    STOPPING_PREP(true, Lifecycle.BEFORE_STOP_EVENT),
    STOPPING(false, Lifecycle.STOP_EVENT),
    STOPPED(false, Lifecycle.AFTER_STOP_EVENT),
    DESTROYING(false, Lifecycle.BEFORE_DESTROY_EVENT),
    DESTROYED(false, Lifecycle.AFTER_DESTROY_EVENT)
    
 * @author GROMAS
 *
 */
public class TraceInterceptorLifecycleListener implements LifecycleListener
{
	private boolean debugMode = false;
	private boolean queryExtraInfo = false;
	// some high unique number so this interceptor is last
	private final static int DEFAULT_INTERCEPTOR_LAST_POSITION = Integer.MAX_VALUE;

	@Override
	public void lifecycleEvent(LifecycleEvent event) 
	{
        if (Lifecycle.BEFORE_INIT_EVENT.equals(event.getType())) 
        {
        	onBeforeInit();
        }
	}

	private void onBeforeInit() 
	{
		LoggerFactory.logger().log("#DDTraceInterceptorLifecycleListener[onBeforeInit]# queryExtraInfo:=%s debugMode:=%s", this.queryExtraInfo, this.debugMode);
		
		TraceInterceptors interceptors = new TraceInterceptors();
		
		if(this.queryExtraInfo)
		{
			TraceInterceptor queryExtraInfoInterceptor = TraceInterceptorFactory.fromClaim(InterceptorClaim.builder()
					.interceptorType(InterceptorType.QUERY_EXTRA_INFO)
					.priority(DEFAULT_INTERCEPTOR_LAST_POSITION)
					.debugMode(this.debugMode)
					.build());
			if(queryExtraInfoInterceptor != null)
			{
				interceptors.addTraceInterceptor(queryExtraInfoInterceptor);
			}
		}
		
		InterceptorsConfigurator.configure(interceptors);
	}
	
	public boolean getQueryExtraInfo()
	{
		return queryExtraInfo;
	}
	public void setQueryExtraInfo(boolean queryExtraInfo)
	{
		this.queryExtraInfo = queryExtraInfo;
	}
    public boolean getDebugMode() 
    {
        return debugMode;
    }
    public void setDebugMode(boolean debugMode) 
    {
        this.debugMode = debugMode;
    }
}
