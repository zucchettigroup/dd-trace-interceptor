package com.zucchetti.ztracer.interceptor.logger;

public class SysoLogger implements Logger
{
	/**
	 * Log a formatted string using the specified format string and arguments.
	 * 
	 * @see String#format(String, Object...)
	 */
	public void log(String format, Object... args)
	{
		System.out.println(String.format(format, args));
	}
}
