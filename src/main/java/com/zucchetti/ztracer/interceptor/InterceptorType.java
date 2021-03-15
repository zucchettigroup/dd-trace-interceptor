package com.zucchetti.ztracer.interceptor;

public enum InterceptorType 
{
	QUERY_EXTRA_INFO("dc-interceptor-queryextrainfo"),
	NO_OP("dc-interceptor-noop");
	
	private final String parentTagValue;

	private InterceptorType(String tagValue) 
	{
		this.parentTagValue = tagValue;
	}

	public String resolveChildTagValue(String childTagValue)
	{
		return this.parentTagValue + "." + childTagValue;
	}
}
