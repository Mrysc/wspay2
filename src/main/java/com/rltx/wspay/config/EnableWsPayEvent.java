package com.rltx.wspay.config;


import com.wl.framework.event.annotation.EnableFrameworkEvent;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE})
@Documented
@EnableFrameworkEvent("WsCloudPay")
@Import({WsPayConsumerConfiguration.class})
public @interface EnableWsPayEvent {}
