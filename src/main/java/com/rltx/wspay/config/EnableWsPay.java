package com.rltx.wspay.config;

import com.rltx.framework.registry.annoation.EnableFrameworkRegistryClient;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE})
@Documented
@EnableFrameworkRegistryClient(value="WsCloudPay",mainModule="WsCloudPay")
@EnableWsPayEvent
public @interface EnableWsPay {}
