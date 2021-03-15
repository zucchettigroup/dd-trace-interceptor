package com.zucchetti.ztracer.interceptor.logger;

public class LoggerFactory
{
	private static final Logger INSTANCE = new SysoLogger();

	private LoggerFactory() {}
	
	public static Logger logger()
	{
		return INSTANCE;
	}
}
