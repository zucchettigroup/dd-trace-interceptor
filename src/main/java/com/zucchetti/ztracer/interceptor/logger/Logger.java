package com.zucchetti.ztracer.interceptor.logger;

public interface Logger 
{
	/**
	 * Log a formatted string using the specified format string and arguments.
	 * 
	 * @see String#format(String, Object...)
	 */
	void log(String format, Object... args);
}
