package com.zucchetti.ztracer.interceptor.test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentMatcher;
import org.mockito.MockedStatic;

import com.zucchetti.ztracer.interceptor.InterceptorsConfigurator;
import com.zucchetti.ztracer.interceptor.TraceInterceptors;
import com.zucchetti.ztracer.interceptor.custom.DebuggableTraceInterceptor;
import com.zucchetti.ztracer.interceptor.custom.QueryExtraInfoTraceInterceptor;
import com.zucchetti.ztracer.interceptor.tomcat.TraceInterceptorLifecycleListener;

import datadog.trace.api.interceptor.TraceInterceptor;

/**
 * @see https://javadoc.io/static/org.mockito/mockito-core/3.5.10/org/mockito/Mockito.html#static_mocks
 * @see https://github.com/mockito/mockito/issues/2027
 */
public class TraceInterceptorLifecycleListenerTest 
{
	@ParameterizedTest
	@CsvSource({ "true,true,1",
		         "false,true,0",
		         "true,false,1",
		         "false,false,0"})
	public void lifecycleBeforeInitTest(boolean queryExtraInfo, boolean debug, int expectedInterceptors)
	{
		// Fixture
		TraceInterceptorLifecycleListener listener = new TraceInterceptorLifecycleListener();
		listener.setQueryExtraInfo(queryExtraInfo);
		listener.setDebugMode(debug);
		
		// Stubbing
		LifecycleEvent mockedLifecycleEvent = mock(LifecycleEvent.class);
		when(mockedLifecycleEvent.getType()).thenReturn(Lifecycle.BEFORE_INIT_EVENT);
		
		// Run Test
		try (MockedStatic<InterceptorsConfigurator> mockedConfigurator = mockStatic(InterceptorsConfigurator.class)) 
		{
			listener.lifecycleEvent(mockedLifecycleEvent);
			
			mockedConfigurator.verify(() -> InterceptorsConfigurator.configure(argThat(TraceInterceptorsMatcher.matcher(listener, expectedInterceptors))), times(1));
		}
	}
	
	@Test
	public void lifecycleNoBeforeInitTest()
	{
		// Fixture
		TraceInterceptorLifecycleListener listener = new TraceInterceptorLifecycleListener();
		listener.setQueryExtraInfo(true);
		listener.setDebugMode(true);
		
		// Stubbing
		LifecycleEvent mockedLifecycleEvent = mock(LifecycleEvent.class);
		when(mockedLifecycleEvent.getType()).thenReturn(Lifecycle.AFTER_INIT_EVENT);
		
		// Run Test
		try (MockedStatic<InterceptorsConfigurator> mockedConfigurator = mockStatic(InterceptorsConfigurator.class)) 
		{
			listener.lifecycleEvent(mockedLifecycleEvent);
			
			mockedConfigurator.verify(() -> InterceptorsConfigurator.configure(any()), never());
		}
	}

	static class TraceInterceptorsMatcher implements ArgumentMatcher<TraceInterceptors> 
	{
		private final boolean queryExtraInfo;
		private final boolean debugMode;
		private final int items;
		
		public static TraceInterceptorsMatcher matcher(TraceInterceptorLifecycleListener listener, int expectedInterceptor)
		{
			return new TraceInterceptorsMatcher(expectedInterceptor, listener.getQueryExtraInfo(), listener.getDebugMode());
		}
		
		private TraceInterceptorsMatcher(int items, boolean queryExtraInfo, boolean debugMode) 
		{
			this.items = items;
			this.queryExtraInfo = queryExtraInfo;
			this.debugMode = debugMode;
		}

		@Override
		public boolean matches(TraceInterceptors argument) 
		{
			if(argument.size() == this.items)
			{
				if(this.items == 0)
				{
					return true;
				}
				TraceInterceptor traceInterceptor = argument.iterator().next();
				if(this.debugMode && traceInterceptor instanceof DebuggableTraceInterceptor)
				{
					return true;
				}
				else if(this.queryExtraInfo && traceInterceptor instanceof QueryExtraInfoTraceInterceptor)
				{
					return true;
				}
			}
			return false;
		}
	}
}
