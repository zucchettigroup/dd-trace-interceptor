package com.zucchetti.ztracer.interceptor.test;

import java.util.Collection;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.zucchetti.ztracer.interceptor.custom.QueryExtraInfoTraceInterceptor;
import com.zucchetti.ztracer.interceptor.dns.IDnsResolver;
import com.zucchetti.ztracer.interceptor.test.utils.TraceUtils;

import datadog.trace.api.interceptor.MutableSpan;

public class QueryExtraInfoTraceInterceptorTest
{
	@Test
	public void test()
	{
		// Fixture
		IDnsResolver dnsResolver = Mockito.mock(IDnsResolver.class);
		QueryExtraInfoTraceInterceptor interceptor = new QueryExtraInfoTraceInterceptor(dnsResolver, Integer.MAX_VALUE);
		Collection<? extends MutableSpan> trace = TraceUtils.newTestTrace();

		// Stubbing
		Mockito.when(dnsResolver.resolve("database-alias")).thenReturn("database-cluster");

		// Run Test
		Collection<? extends MutableSpan> returnedTrace = interceptor.onTraceComplete(trace);
		System.out.println(returnedTrace);

		// Assertions
		org.junit.jupiter.api.Assertions.assertEquals(Integer.MAX_VALUE, interceptor.priority());
	
		Assertions.assertThat(returnedTrace).hasSize(3);

		Condition<MutableSpan> extraTagsCond = new Condition<MutableSpan>(s -> 
		{ 
			Map<String, Object> tags = s.getTags();
			String dbService = (String) tags.get("db.service");
			String dbCluster = (String) tags.get("db.cluster");
			
			return "test-webapp".equals(dbService) && "database-cluster".equals(dbCluster);
		
		}, "Check tags db.service,db.cluster per operation Database Query");
		Assertions.assertThat(returnedTrace).areExactly(2, extraTagsCond);
	}
}
