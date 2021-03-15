package com.zucchetti.ztracer.interceptor.test;

import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.zucchetti.ztracer.interceptor.InterceptorType;
import com.zucchetti.ztracer.interceptor.TraceInterceptorFactory;
import com.zucchetti.ztracer.interceptor.custom.DebuggableTraceInterceptor;
import com.zucchetti.ztracer.interceptor.custom.QueryExtraInfoTraceInterceptor;
import com.zucchetti.ztracer.interceptor.tomcat.InterceptorClaim;

import datadog.trace.api.interceptor.MutableSpan;
import datadog.trace.api.interceptor.TraceInterceptor;

public class TraceInterceptorFactoryTest 
{
	private static final int FIXED_PRIORITY = 10;

	@Test
	public void traceInterceptorFactoryNullTest()
	{
		// Run Test
		TraceInterceptor traceInterceptor = TraceInterceptorFactory.fromClaim(InterceptorClaim.builder()
				.interceptorType(null)
				.priority(FIXED_PRIORITY)
				.debugMode(false)
				.build());
		// Assertions
		Assertions.assertNull(traceInterceptor);
	}

	@Test
	public void traceInterceptorFactoryNoOpTest()
	{
		// Run Test
		TraceInterceptor traceInterceptor = TraceInterceptorFactory.fromClaim(InterceptorClaim.builder()
				.interceptorType(InterceptorType.NO_OP)
				.priority(FIXED_PRIORITY)
				.debugMode(false)
				.build());
		Collection<? extends MutableSpan> returnedTraceNoOp = traceInterceptor.onTraceComplete(null);
		// Assertions
		Assertions.assertEquals(FIXED_PRIORITY, traceInterceptor.priority());
		Assertions.assertNull(returnedTraceNoOp);
	}

	@Test
	public void traceInterceptorFactoryTest()
	{
		// Run Test
		TraceInterceptor traceInterceptor = TraceInterceptorFactory.fromClaim(InterceptorClaim.builder()
				.interceptorType(InterceptorType.QUERY_EXTRA_INFO)
				.priority(FIXED_PRIORITY)
				.debugMode(false)
				.build());
		// Assertions
		Assertions.assertNotNull(traceInterceptor);
		Assertions.assertTrue(traceInterceptor instanceof QueryExtraInfoTraceInterceptor);
		Assertions.assertEquals(FIXED_PRIORITY, traceInterceptor.priority());
	}

	@Test
	public void debuggableTraceInterceptorFactoryTest()
	{
		// Run Test
		TraceInterceptor traceInterceptor = TraceInterceptorFactory.fromClaim(InterceptorClaim.builder()
				.interceptorType(InterceptorType.QUERY_EXTRA_INFO)
				.priority(FIXED_PRIORITY)
				.debugMode(true)
				.build());
		// Assertions
		Assertions.assertNotNull(traceInterceptor);
		Assertions.assertTrue(traceInterceptor instanceof DebuggableTraceInterceptor);
		Assertions.assertEquals(FIXED_PRIORITY, traceInterceptor.priority());
	}

	@Test
	public void nullClaimTraceInterceptorFactoryTest()
	{
		// Run Test
		TraceInterceptor traceInterceptor = TraceInterceptorFactory.fromClaim(null);
		// Assertions
		Assertions.assertNull(traceInterceptor);
	}

	@Test
	public void claimWithNullInterceptorTypeTraceInterceptorFactoryTest()
	{
		// Run Test
		TraceInterceptor traceInterceptor = TraceInterceptorFactory.fromClaim(InterceptorClaim.builder()
				.interceptorType(null)
				.priority(FIXED_PRIORITY)
				.debugMode(true)
				.build());
		// Assertions
		Assertions.assertNull(traceInterceptor);
	}
}
