package com.github._64q.keepo.redis;

import java.util.concurrent.Future;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.github._64q.keepo.chat.ChatEntry;
import com.github._64q.keepo.config.Config;

import redis.clients.jedis.Jedis;

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

  /** Jedis Client */
  private Jedis client;

  @Autowired
  private Config config;

  @PostConstruct
  public void initialize() {
    client = new Jedis(config.getRedisAddress());
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
  public Future<String> put(ChatEntry entry) {
    String key = entry.getKey();
    String content = entry.getContent();

    String statusCode = client.setex(key, config.getRedisTtl().intValue(), content);

    if ("OK".equals(statusCode)) {
      if (LOG.isTraceEnabled()) {
        LOG.trace("insert successful {}", statusCode);
      }
    } else {
      if (LOG.isWarnEnabled()) {
        LOG.warn("insert failed, status code: {}", statusCode);
      }
    }

    return new AsyncResult<String>(statusCode);
  }

}
