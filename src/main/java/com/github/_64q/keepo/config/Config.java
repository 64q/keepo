package com.github._64q.keepo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Bean representing the configuration
 * 
 * @since 1.0
 * @author qlebourgeois &lt;contact@qlebourgeois.me&gt;
 */
@Configuration
@EnableAsync
@PropertySource("classpath:configuration.properties")
public class Config {

  @Value("${keepo.redisAddress}")
  private String redisAddress;

  @Value("${keepo.redisTtl}")
  private String redisTtl;

  @Value("${keepo.ircAddress}")
  private String ircAddress;

  @Value("${keepo.ircUsername}")
  private String ircUsername;

  @Value("${keepo.ircPassword}")
  private String ircPassword;

  /**
   * Returns the redis server address
   * 
   * @return redis server address
   */
  public String getRedisAddress() {
    return this.redisAddress;
  }

  /**
   * Retreive the TTL of redis entries (in seconds)
   * 
   * @return the redis entries ttl
   */
  public Integer getRedisTtl() {
    return Integer.parseInt(this.redisTtl);
  }

  public String getIrcAddress() {
    return this.ircAddress;
  }

  public String getIrcUsername() {
    return this.ircUsername;
  }

  public String getIrcPassword() {
    return this.ircPassword;
  }

}
