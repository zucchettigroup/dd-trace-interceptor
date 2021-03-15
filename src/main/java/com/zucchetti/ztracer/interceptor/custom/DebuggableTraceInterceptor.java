package com.zucchetti.ztracer.interceptor.custom;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;

import com.zucchetti.ztracer.interceptor.InterceptorType;

import datadog.trace.api.interceptor.MutableSpan;
import datadog.trace.api.interceptor.TraceInterceptor;

public class DebuggableTraceInterceptor implements TraceInterceptor 
{
	private static final String SPANCOUNT = "spancount";
	private static final String EXECTIME = "exectime";
	private static final String THREAD = "thread";
	
	private final TraceInterceptor delegate;
	private final InterceptorType interceptorType;

	public DebuggableTraceInterceptor(TraceInterceptor delegate, InterceptorType interceptorType) 
	{
		this.delegate = delegate;
		this.interceptorType = interceptorType;
	}

	@Override
	public Collection<? extends MutableSpan> onTraceComplete(Collection<? extends MutableSpan> trace) 
	{
		Instant start = Instant.now();
		
		Collection<? extends MutableSpan> traceResult = delegate.onTraceComplete(trace);
		
		Instant stop = Instant.now();
		long timeElapsed = Duration.between(start, stop).toMillis();
		
		traceResult.stream()
				   .filter(s -> { return s.getLocalRootSpan() == s; })
		           .forEach(s -> {
		        	   s.setTag(this.interceptorType.resolveChildTagValue(THREAD), Thread.currentThread().getName()); 
		               s.setTag(this.interceptorType.resolveChildTagValue(EXECTIME), timeElapsed);
		               s.setTag(this.interceptorType.resolveChildTagValue(SPANCOUNT), trace.size()); 
		               });
		
		return traceResult;
	}

	@Override
	public int priority() 
	{
		return this.delegate.priority();
	}
}
