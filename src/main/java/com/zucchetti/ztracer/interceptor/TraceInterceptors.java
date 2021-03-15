package com.zucchetti.ztracer.interceptor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import datadog.trace.api.interceptor.TraceInterceptor;

public class TraceInterceptors implements Iterable<TraceInterceptor>
{
	private final List<TraceInterceptor> interceptors;

	public TraceInterceptors() 
	{
		this.interceptors = new ArrayList<TraceInterceptor>();
	}
	
	public void addTraceInterceptor(TraceInterceptor interceptor)
	{
		this.interceptors.add(interceptor);
	}

	@Override
	public Iterator<TraceInterceptor> iterator() 
	{
		return this.interceptors.iterator();
	}
	
	public int size()
	{
		return interceptors.size();
	}
}
