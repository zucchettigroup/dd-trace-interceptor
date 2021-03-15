package com.zucchetti.ztracer.interceptor.dns;

import java.util.concurrent.ConcurrentHashMap;

class CachedDnsResolved implements IDnsResolver 
{
	private final IDnsResolver dnsResolverDelegate;
	private final ConcurrentHashMap<String, String> hostsCache;

	public CachedDnsResolved(IDnsResolver dnsResolverDelegate) 
	{
		this.dnsResolverDelegate = dnsResolverDelegate;
		this.hostsCache = new ConcurrentHashMap<String, String>();
	}

	@Override
	public String resolve(String hostToResolve) 
	{
		String resolvedHost = null;
		if(hostToResolve != null && !hostToResolve.isEmpty())
		{
			if((resolvedHost = this.hostsCache.get(hostToResolve)) == null)
			{
				resolvedHost = dnsResolverDelegate.resolve(hostToResolve);
				this.hostsCache.putIfAbsent(hostToResolve, resolvedHost);
			}
		}
		return resolvedHost;
	}
}
