package com.zucchetti.ztracer.interceptor;

import com.zucchetti.ztracer.interceptor.custom.DebuggableTraceInterceptor;
import com.zucchetti.ztracer.interceptor.custom.NoOpTraceInterceptor;
import com.zucchetti.ztracer.interceptor.custom.QueryExtraInfoTraceInterceptor;
import com.zucchetti.ztracer.interceptor.dns.DnsResolverFactory;
import com.zucchetti.ztracer.interceptor.tomcat.InterceptorClaim;

import datadog.trace.api.interceptor.TraceInterceptor;

public class TraceInterceptorFactory 
{
	private TraceInterceptorFactory() {}
	
	public static TraceInterceptor fromClaim(InterceptorClaim interceptorClaim)
	{
		TraceInterceptor interceptor = null;
		if(interceptorClaim != null)
		{
			int priority = interceptorClaim.priority();
			InterceptorType interceptorType = interceptorClaim.interceptorType();
			if(interceptorType != null)
			{
				interceptor = selectInterceptorImpl(priority, interceptorType);
				interceptor = decorateWithDebuggableIfRequired(interceptorClaim, interceptor, interceptorType);
			}
		}
		return interceptor;
	}

	private static TraceInterceptor decorateWithDebuggableIfRequired(InterceptorClaim interceptorClaim, TraceInterceptor interceptor, InterceptorType interceptorType) 
	{
		if(interceptorClaim.debugModeEnabled())
		{
			interceptor = new DebuggableTraceInterceptor(interceptor, interceptorType);
		}
		return interceptor;
	}

	private static TraceInterceptor selectInterceptorImpl(int priority, InterceptorType interceptorType) 
	{
		TraceInterceptor interceptor = null;
		switch (interceptorType) 
		{
		case QUERY_EXTRA_INFO:
			interceptor = new QueryExtraInfoTraceInterceptor(DnsResolverFactory.newDefaultResolver(), priority);
			break;

		default:
			interceptor = new NoOpTraceInterceptor(priority);
			break;
		}
		return interceptor;
	}
}
