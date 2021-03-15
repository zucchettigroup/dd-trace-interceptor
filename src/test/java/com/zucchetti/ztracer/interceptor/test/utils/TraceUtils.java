package com.zucchetti.ztracer.interceptor.test.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import datadog.trace.api.interceptor.MutableSpan;

public class TraceUtils 
{
	public static Collection<? extends MutableSpan> newTestTrace() 
	{
		List<MutableSpan> trace = new ArrayList<MutableSpan>();
		
		MutableSpan rootSpan = TestMutableSpan.rootSpan()
				.setServiceName("test-webapp")
				.setOperationName("servlet.request")
				.setResourceName("POST /MyWebApp/servlet/?")
				.setTag("junit", 5);
		trace.add(rootSpan);
		
		MutableSpan dbSpanSqlServer = TestMutableSpan.span(rootSpan)
				.setServiceName("test-db")
				.setOperationName("sqlserver.query")
				.setResourceName("select 1")
				.setTag("peer.hostname", "database-alias")
				.setTag("junit", 5);
		trace.add(dbSpanSqlServer);
		
		MutableSpan dbSpanPostgresql = TestMutableSpan.span(rootSpan)
				.setServiceName("test-db")
				.setOperationName("postgresql.query")
				.setResourceName("select 1")
				.setTag("peer.hostname", "database-alias")
				.setTag("junit", 5);
		trace.add(dbSpanPostgresql);
		
		return trace;
	}
}
