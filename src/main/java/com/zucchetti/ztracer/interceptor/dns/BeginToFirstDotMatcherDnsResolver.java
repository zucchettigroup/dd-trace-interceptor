package com.zucchetti.ztracer.interceptor.dns;

class BeginToFirstDotMatcherDnsResolver implements IDnsResolver 
{
	private final IDnsResolver dnsResolverDelegate;
	
	public BeginToFirstDotMatcherDnsResolver(IDnsResolver dnsResolverDelegate) 
	{
		this.dnsResolverDelegate = dnsResolverDelegate;
	}
	
	@Override
	public String resolve(String hostToResolve) 
	{
		int firstDotIndex = -1;
		String resolvedHostname = this.dnsResolverDelegate.resolve(hostToResolve);
		if((firstDotIndex = resolvedHostname.indexOf(".")) != -1)
		{
			return resolvedHostname.substring(0, firstDotIndex);
		}
		return resolvedHostname;
	}
}
