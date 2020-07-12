package com.rltx.wspay.config;

import com.rltx.wspay.bindbankcard.consumer.BindSelfBankCardConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WsPayConsumerConfiguration {
  @Bean
  public BindSelfBankCardConsumer bindSelfBankCardConsumer() {
    return new BindSelfBankCardConsumer();
  }

}
