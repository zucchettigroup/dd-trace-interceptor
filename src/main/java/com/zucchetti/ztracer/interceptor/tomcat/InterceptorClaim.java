package com.zucchetti.ztracer.interceptor.tomcat;

import com.zucchetti.ztracer.interceptor.InterceptorType;
import javax.annotation.Generated;

public class InterceptorClaim 
{
	private InterceptorType interceptorType;
	private int priority;
	private boolean debugMode;

	@Generated("SparkTools")
	private InterceptorClaim(Builder builder) {
		this.interceptorType = builder.interceptorType;
		this.priority = builder.priority;
		this.debugMode = builder.debugMode;
	}

	public boolean debugModeEnabled() {
		return debugMode;
	}
	public InterceptorType interceptorType() {
		return interceptorType;
	}
	public int priority() {
		return priority;
	}
	
	/**
	 * Creates builder to build {@link InterceptorClaim}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static IInterceptorTypeStage builder() {
		return new Builder();
	}
	@Generated("SparkTools")
	public interface IInterceptorTypeStage {
		public IPriorityStage interceptorType(InterceptorType interceptorType);
	}
	@Generated("SparkTools")
	public interface IPriorityStage {
		public IDebugModeStage priority(int priority);
	}
	@Generated("SparkTools")
	public interface IDebugModeStage {
		public IBuildStage debugMode(boolean debugMode);
	}
	@Generated("SparkTools")
	public interface IBuildStage {
		public InterceptorClaim build();
	}
	/**
	 * Builder to build {@link InterceptorClaim}.
	 */
	@Generated("SparkTools")
	public static final class Builder implements IInterceptorTypeStage, IPriorityStage, IDebugModeStage, IBuildStage {
		private InterceptorType interceptorType;
		private int priority;
		private boolean debugMode;

		private Builder() {
		}

		@Override
		public IPriorityStage interceptorType(InterceptorType interceptorType) {
			this.interceptorType = interceptorType;
			return this;
		}

		@Override
		public IDebugModeStage priority(int priority) {
			this.priority = priority;
			return this;
		}

		@Override
		public IBuildStage debugMode(boolean debugMode) {
			this.debugMode = debugMode;
			return this;
		}

		@Override
		public InterceptorClaim build() {
			return new InterceptorClaim(this);
		}
	}
}
