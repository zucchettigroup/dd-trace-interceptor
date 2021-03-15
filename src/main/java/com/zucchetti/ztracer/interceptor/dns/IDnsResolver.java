package com.zucchetti.ztracer.interceptor.dns;

public interface IDnsResolver 
{
	String UNRESOLVABLE = "unresolvable";
	
	String resolve(String hostToResolve);
}
