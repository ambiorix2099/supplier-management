package com.sample.infrastructure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.downgoon.snowflake.Snowflake;

@Configuration
@ConfigurationProperties("snowflake")
public class SnowflakeConfigs {

  @Bean
  public Snowflake snowflake(SnowflakeConfigs configs) {
    return new Snowflake(configs.getDataCenter(), configs.getWorkerId());
  }

  private int dataCenter;
  private int workerId;

  public SnowflakeConfigs() {
  }

  public SnowflakeConfigs(int dataCenter, int workerId) {
    this.dataCenter = dataCenter;
    this.workerId = workerId;
  }

  public int getDataCenter() {
    return dataCenter;
  }

  public void setDataCenter(int dataCenter) {
    this.dataCenter = dataCenter;
  }

  public int getWorkerId() {
    return workerId;
  }

  public void setWorkerId(int workerId) {
    this.workerId = workerId;
  }
}
