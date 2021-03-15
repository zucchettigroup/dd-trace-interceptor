package com.zucchetti.ztracer.interceptor;

import com.zucchetti.ztracer.interceptor.logger.LoggerFactory;

import datadog.trace.api.Tracer;
import datadog.trace.api.interceptor.TraceInterceptor;

/**
 * @see https://docs.datadoghq.com/tracing/setup_overview/custom_instrumentation/java/
 * @author GROMAS
 */
public class InterceptorsConfigurator 
{
	private InterceptorsConfigurator() {}
	
	public static void configure(TraceInterceptors interceptors)
	{
		if(interceptors != null)
		{
			for(TraceInterceptor interceptor : interceptors)
			{
				Tracer tracer = datadog.trace.api.GlobalTracer.get();
				tracer.addTraceInterceptor(interceptor);
				
				LoggerFactory.logger().log("#datadog.trace.api.Tracer[addTraceInterceptor]# type:=%s", interceptor.getClass().getName());
			}
		}
	}
}
