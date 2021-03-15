package com.zucchetti.ztracer.interceptor.custom;

import java.util.Collection;

import datadog.trace.api.interceptor.MutableSpan;
import datadog.trace.api.interceptor.TraceInterceptor;

public class NoOpTraceInterceptor implements TraceInterceptor 
{
	private final int priority;
	
	public NoOpTraceInterceptor(int priority)
	{
		this.priority = priority;
	}

	@Override
	public Collection<? extends MutableSpan> onTraceComplete(Collection<? extends MutableSpan> trace) 
	{
		return trace;
	}

	@Override
	public int priority() 
	{
		return this.priority;
	}
}
