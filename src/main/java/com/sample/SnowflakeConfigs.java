package com.sample;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("snowflake")
public class SnowflakeConfigs {

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
