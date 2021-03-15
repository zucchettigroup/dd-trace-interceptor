package com.zucchetti.ztracer.interceptor.test;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.zucchetti.ztracer.interceptor.InterceptorsConfigurator;
import com.zucchetti.ztracer.interceptor.TraceInterceptors;

import datadog.trace.api.GlobalTracer;
import datadog.trace.api.Tracer;
import datadog.trace.api.interceptor.TraceInterceptor;

public class InterceptorsConfiguratorTest 
{
	@Test
	public void test()
	{
		// Fixture
		final TraceInterceptor traceInterceptorA = Mockito.mock(TraceInterceptor.class);
		final TraceInterceptor traceInterceptorB = Mockito.mock(TraceInterceptor.class);

		TraceInterceptors interceptors = new TraceInterceptors();
		interceptors.addTraceInterceptor(traceInterceptorA);
		interceptors.addTraceInterceptor(traceInterceptorB);

		try (MockedStatic<GlobalTracer> mockedTracer = mockStatic(datadog.trace.api.GlobalTracer.class)) 
		{
			// Stubbing
			Tracer ddTracer = Mockito.mock(datadog.trace.api.Tracer.class);
			mockedTracer.when(datadog.trace.api.GlobalTracer::get).thenReturn(ddTracer);
			
			// Run Test
			InterceptorsConfigurator.configure(interceptors);

			// Verification
			Mockito.verify(ddTracer, times(1)).addTraceInterceptor(traceInterceptorA);
			Mockito.verify(ddTracer, times(1)).addTraceInterceptor(traceInterceptorB);
		}
	}
}
