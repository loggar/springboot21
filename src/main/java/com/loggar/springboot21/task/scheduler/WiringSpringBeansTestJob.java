package com.loggar.springboot21.task.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.loggar.springboot21.bean.util.BeanUtilGetBeanUsage;

@Service
public class WiringSpringBeansTestJob {
	/*
	 * This example shows how to inject spring beans into classes not managed by spring
	 */
	@Scheduled(fixedRate = 75000)
	public void doSomething() {
		BeanUtilGetBeanUsage beanUtilGetBeanUsage = new BeanUtilGetBeanUsage();
		beanUtilGetBeanUsage.trig();
	}
}
