package com.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import xyz.downgoon.snowflake.Snowflake;

@SpringBootApplication
@EnableConfigurationProperties
public class SupplyManagementApplication {

  public static void main(String[] args) {
    SpringApplication.run(SupplyManagementApplication.class, args);
  }

  @Bean
  public Snowflake snowflake(SnowflakeConfigs configs) {
    return new Snowflake(configs.getDataCenter(), configs.getWorkerId());
  }
}
