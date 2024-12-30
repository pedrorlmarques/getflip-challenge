package com.example.configuration;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggingAspect {

	private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

	@Pointcut("within(@com.example.common.UseCase *)")
	public void useCase() {
	}

	@Pointcut("execution(public * *(..))")
	public void publicMethod() {
	}

	@Pointcut("publicMethod() && useCase()")
	public void publicMethodInsideAUseCase() {
	}

	@Around("publicMethodInsideAUseCase()")
	public Object aroundServiceMethodAdvice(final ProceedingJoinPoint pjp) throws Throwable {
		try {
			log.info("Executing use case: {}#{} with parameters: {}", pjp.getTarget()
			                                                             .getClass()
			                                                             .getSimpleName(), pjp.getSignature()
			                                                                                  .getName(), Arrays.toString(pjp.getArgs()));
			return pjp.proceed();
		} finally {
			log.info("Finished executing use case {}#{}", pjp.getTarget()
			                                                 .getClass()
			                                                 .getSimpleName(), pjp.getSignature()
			                                                                      .getName());
		}
	}
}
