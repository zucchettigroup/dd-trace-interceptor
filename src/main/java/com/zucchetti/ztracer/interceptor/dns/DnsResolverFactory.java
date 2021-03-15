package com.zucchetti.ztracer.interceptor.dns;

import com.zucchetti.ztracer.interceptor.OnlyForTesting;

public class DnsResolverFactory 
{
	private DnsResolverFactory() {}
	
	public static IDnsResolver newDefaultResolver()
	{
		return new CachedDnsResolved(new BeginToFirstDotMatcherDnsResolver(new BasicInetAddressDnsResolver()));
	}
	
	@OnlyForTesting
	public static IDnsResolver newResolver(IDnsResolver delegate)
	{
		return new CachedDnsResolved(delegate);
	}
	
	@OnlyForTesting
	public static IDnsResolver newResolverNoCache()
	{
		return new BeginToFirstDotMatcherDnsResolver(new BasicInetAddressDnsResolver());
	}
	
	@OnlyForTesting
	public static IDnsResolver newBeginToFirstDotMatcherResolver(IDnsResolver delegate)
	{
		return new BeginToFirstDotMatcherDnsResolver(delegate);
	}
	
	@OnlyForTesting
	public static IDnsResolver newBasicInetAddressResolver()
	{
		return new BasicInetAddressDnsResolver();
	}
}
