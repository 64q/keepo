package com.github._64q.keepo.config;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.github._64q.keepo.util.SystemUtils;

@Configuration
@EnableAsync
@Import(KeepoConfig.class)
@PropertySource("classpath:configuration.properties")
public class AppConfig implements AsyncConfigurer {

  /** Logger */
  private static final Logger LOG = LoggerFactory.getLogger(AppConfig.class);

  @Bean
  @Override
  public Executor getAsyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

    // tuning the thread pool
    executor.setCorePoolSize(SystemUtils.THREADS_POOL_CORE);
    executor.setMaxPoolSize(SystemUtils.THREADS_POOL_MAX);
    executor.setQueueCapacity(SystemUtils.THREADS_POOL_QUEUE);
    executor.setThreadNamePrefix("RedisExecutor-");
    executor.initialize();

    return executor;
  }

  @Override
  public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
    return new AsyncUncaughtExceptionHandler() {
      @Override
      public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        LOG.error("caught exception in async method {}", method.getName(), ex);
      }
    };
  }

}
