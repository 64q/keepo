package com.github._64q.keepo.redis;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.github._64q.keepo.chat.ChatEntry;
import com.github._64q.keepo.config.KeepoConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Implementation of the standard Redis client
 * 
 * @since 1.0
 * @author qlebourgeois &lt;contact@qlebourgeois.me&gt;
 */
@Service
public class RedisClient {

  /** Logger */
  private static final Logger LOG = LoggerFactory.getLogger(RedisClient.class);

  // --- internal attributes

  /** Jedis Client */
  private JedisPool jedisPool;

  /** cached TTL */
  private int cachedTtl = 0;

  // --- dependencies

  @Autowired
  private ThreadPoolTaskExecutor executor;

  /** Application config */
  @Autowired
  private KeepoConfig config;

  @PostConstruct
  public void initialize() {
    // tune jedis
    JedisPoolConfig poolConfig = new JedisPoolConfig();

    poolConfig.setMaxTotal(executor.getMaxPoolSize() * 2);
    poolConfig.setMaxIdle(executor.getMaxPoolSize() * 2);
    poolConfig.setBlockWhenExhausted(true);

    jedisPool = new JedisPool(poolConfig, config.getRedisAddress());

    cachedTtl = config.getRedisTtl().intValue();

    checkStatus();
  }

  /**
   * Put a new chat entry in the database
   * 
   * <p>
   * This method performs an asynchronous insert into redis
   * 
   * @param entry the entry to insert
   * @return the future result of the insertion
   */
  @Async
  public void put(ChatEntry entry) {
    String key = entry.getKey();
    String content = entry.getContent();

    Jedis instance = jedisPool.getResource();

    try {
      insert(key, content, instance);
    } finally {
      instance.close();
    }
  }

  /**
   * Insert a new entry in the redis backend
   * 
   * @param key
   * @param content
   * @param instance
   */
  private void insert(String key, String content, Jedis instance) {
    String statusCode = instance.setex(key, cachedTtl, content);

    if ("OK".equals(statusCode)) {
      if (LOG.isTraceEnabled()) {
        LOG.trace("insert successful {} for key = {}", statusCode, key);
      }
    } else {
      if (LOG.isWarnEnabled()) {
        LOG.warn("insert failed {} for key = {}", statusCode, key);
      }
    }
  }

  /**
   * Check if the Redis server is alive
   */
  private void checkStatus() {
    Jedis jedis = jedisPool.getResource();

    String result = jedis.ping();

    jedis.close();

    if (!"PONG".equals(result)) {
      throw new RuntimeException("unable to get a ping response from the redis server");
    }
  }

}
