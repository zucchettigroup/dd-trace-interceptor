package com.zucchetti.ztracer.interceptor.dns;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class BasicInetAddressDnsResolver implements IDnsResolver 
{
	@Override
	public String resolve(String hostToResolve) 
	{
		try 
		{
			InetAddress address = InetAddress.getByName(hostToResolve); 
			String ipAddress = address.getHostAddress();
			InetAddress addressByHostname = InetAddress.getByName(ipAddress); 
			
			return addressByHostname.getHostName();
		} 
		catch (UnknownHostException e) 
		{
			return IDnsResolver.UNRESOLVABLE;
		}
	}
}
