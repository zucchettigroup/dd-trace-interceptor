package com.zucchetti.ztracer.interceptor.test.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import datadog.trace.api.interceptor.MutableSpan;

public class TestMutableSpan implements MutableSpan 
{
	private final MutableSpan localRootSpan;
	private final Map<String, Object> tags;
	private final boolean isRootSpan;

	private String serviceName;
	private CharSequence operationName;
	private CharSequence resourceName;
	
	public static MutableSpan rootSpan()
	{
		return new TestMutableSpan(null, true, null);
	}
	
	public static MutableSpan span(MutableSpan localRootSpan)
	{
		return new TestMutableSpan(localRootSpan, false, null);
	}

	private TestMutableSpan(MutableSpan localRootSpan, boolean isRootSpan) 
	{
		this(localRootSpan, isRootSpan, null);
	}
	
	private TestMutableSpan(MutableSpan localRootSpan, boolean isRootSpan, Map<String, Object> tags) 
	{
		this.localRootSpan = localRootSpan;
		this.isRootSpan = isRootSpan;
		this.tags = tags == null? new HashMap<String, Object>() : tags;
	}
	
	@Override
	public CharSequence getOperationName() 
	{
		return this.operationName;
	}

	@Override
	public MutableSpan setOperationName(CharSequence operationName) 
	{
		this.operationName = operationName;
		return this;
	}

	@Override
	public String getServiceName() 
	{
		return this.serviceName;
	}

	@Override
	public MutableSpan setServiceName(String serviceName) 
	{
		this.serviceName = serviceName;
		return this;
	}

	@Override
	public CharSequence getResourceName() 
	{
		return this.resourceName;
	}

	@Override
	public MutableSpan setResourceName(CharSequence resourceName) 
	{
		this.resourceName = resourceName;
		return this;
	}

	@Override
	public Map<String, Object> getTags()
	{
		return this.tags;
	}

	@Override
	public MutableSpan setTag(String tag, String value)
	{
		this.tags.put(tag, value);
		return this;
	}

	@Override
	public MutableSpan setTag(String tag, boolean value) 
	{
		this.tags.put(tag, value);
		return this;
	}

	@Override
	public MutableSpan setTag(String tag, Number value)
	{
		this.tags.put(tag, value);
		return this;
	}

	@Override
	public MutableSpan getRootSpan() 
	{
		if(isRootSpan)
		{
			return this;
		}
		return this.localRootSpan;
	}

	@Override
	public MutableSpan getLocalRootSpan() 
	{
		if(isRootSpan)
		{
			return this;
		}
		return this.localRootSpan;
	}
	
	@Override
	public Integer getSamplingPriority() 
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public MutableSpan setSamplingPriority(int newPriority) 
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public String getSpanType() 
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public MutableSpan setSpanType(CharSequence type) 
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public MutableSpan setMetric(CharSequence metric, int value) 
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public MutableSpan setMetric(CharSequence metric, long value)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public MutableSpan setMetric(CharSequence metric, double value) 
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Boolean isError() 
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public MutableSpan setError(boolean value)
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public long getStartTime() 
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public long getDurationNano() 
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
		builder.append("hashCode", this.hashCode());
		builder.append("localRootSpan", getLocalRootSpan() != null ? getLocalRootSpan().hashCode() : null);
		builder.append("tags", tags);
		builder.append("isRootSpan", isRootSpan);
		builder.append("serviceName", serviceName);
		builder.append("operationName", operationName);
		builder.append("resourceName", resourceName);
		return builder.toString();
	}
}
