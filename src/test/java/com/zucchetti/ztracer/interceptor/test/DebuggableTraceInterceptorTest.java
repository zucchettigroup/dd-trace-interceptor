package com.zucchetti.ztracer.interceptor.test;

import java.util.Collection;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.zucchetti.ztracer.interceptor.InterceptorType;
import com.zucchetti.ztracer.interceptor.custom.DebuggableTraceInterceptor;
import com.zucchetti.ztracer.interceptor.test.utils.TraceUtils;

import datadog.trace.api.interceptor.MutableSpan;
import datadog.trace.api.interceptor.TraceInterceptor;

public class DebuggableTraceInterceptorTest
{
	@Test
	public void test()
	{
		// Fixture
		TraceInterceptor mockedInterceptor = Mockito.mock(TraceInterceptor.class);
		DebuggableTraceInterceptor debugInterceptor = new DebuggableTraceInterceptor(mockedInterceptor, InterceptorType.QUERY_EXTRA_INFO);
		final Collection<? extends MutableSpan> inputTrace = TraceUtils.newTestTrace();
		// Stubbing
		Mockito.when(mockedInterceptor.priority()).thenReturn(Integer.MAX_VALUE);
		Mockito.when(mockedInterceptor.onTraceComplete(Mockito.anyCollection())).thenAnswer(traceTestAnswer(inputTrace));
		// Run test
		Collection<? extends MutableSpan> returnedTrace = debugInterceptor.onTraceComplete(inputTrace);

		// Assertions
		org.junit.jupiter.api.Assertions.assertEquals(Integer.MAX_VALUE, debugInterceptor.priority());
		Assertions.assertThat(returnedTrace).hasSize(3);

		Condition<MutableSpan> debuggableTagsCond = new Condition<MutableSpan>(s -> 
		{ 
			Map<String, Object> tags = s.getTags();
			Integer spancount = (Integer) tags.get("dc-interceptor-queryextrainfo.spancount");
			Long execTime = (Long) tags.get("dc-interceptor-queryextrainfo.exectime");
			String threadName = (String) tags.get("dc-interceptor-queryextrainfo.thread");

			return s.getLocalRootSpan() == s &&
					Thread.currentThread().getName().equals(threadName) && spancount == 3 && execTime >= 0;

		}, "Check debug tag on local root span");
		Assertions.assertThat(returnedTrace).areExactly(1, debuggableTagsCond);

	}

	private Answer<Collection<? extends MutableSpan>> traceTestAnswer(final Collection<? extends MutableSpan> inputTrace) 
	{
		return new Answer<Collection<? extends MutableSpan>>() 
		{
			@Override
			public Collection<? extends MutableSpan> answer(InvocationOnMock invocation) throws Throwable 
			{
				return inputTrace;
			}
		};
	}
}
