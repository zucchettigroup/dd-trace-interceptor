package com.zucchetti.ztracer.interceptor.custom;

import java.util.Collection;

import com.zucchetti.ztracer.interceptor.dns.IDnsResolver;

import datadog.trace.api.interceptor.MutableSpan;
import datadog.trace.api.interceptor.TraceInterceptor;

public class QueryExtraInfoTraceInterceptor implements TraceInterceptor 
{
	private static final String TAG_DB_CLUSTER = "db.cluster";
	private static final String TAG_DB_HOSTNAME = "peer.hostname";
	private static final String TAG_DB_CALLER_SERVICE = "db.service";
	
	private static final String OP_POSTGRESQL = "postgresql.query";
	private static final String OP_SQLSERVER = "sqlserver.query";

	private final IDnsResolver dnsResolver;
	private final int priority;
	
	public QueryExtraInfoTraceInterceptor(IDnsResolver dnsResolver, int priority)
	{
		this.dnsResolver = dnsResolver;
		this.priority = priority;
	}

	@Override
	public Collection<? extends MutableSpan> onTraceComplete(Collection<? extends MutableSpan> trace) 
	{
		trace.forEach(s -> 
		{
			// NOTA: We need to call the toString() method because s.getOperationName() return a datadog.trace.bootstrap.instrumentation.api.UTF8BytesString instance.
			// see: https://github.com/DataDog/dd-trace-java/blob/0b018f976a72c61f910abec4da9fe6e9fc4c7bdd/internal-api/src/main/java/datadog/trace/bootstrap/instrumentation/api/UTF8BytesString.java
			if(isDatabaseQuery(s.getOperationName().toString()))
			{
				// Finché tutte le root span non avranno il tag "servlet.context" utilizzare il seguente tag per le aggregazioni
				s.setTag(TAG_DB_CALLER_SERVICE, s.getLocalRootSpan().getServiceName());
				s.setTag(TAG_DB_CLUSTER, this.dnsResolver.resolve((String) s.getTags().get(TAG_DB_HOSTNAME)));
			}
		});
		return trace;
	}

	private boolean isDatabaseQuery(String spanOpNameAsString) 
	{
		return OP_SQLSERVER.equals(spanOpNameAsString) || OP_POSTGRESQL.equals(spanOpNameAsString);
	}

	@Override
	public int priority() 
	{
		return this.priority;
	}
}
