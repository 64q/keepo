package com.github._64q.keepo.util;

import java.util.Optional;

public final class SystemUtils {

  /**
   * Core pool threads
   */
  public static final Integer THREADS_POOL_CORE =
      getSystemPropertyAsInteger("keepo.threadsPoolCore", 5);

  /**
   * Max pool threads
   */
  public static final Integer THREADS_POOL_MAX =
      getSystemPropertyAsInteger("keepo.threadsPoolMax", 10);

  /**
   * Threads queue
   */
  public static final Integer THREADS_POOL_QUEUE =
      getSystemPropertyAsInteger("keepo.threadsPoolQueue", 5);

  /**
   * Benchmark mode
   */
  public static final Boolean BENCHMARK_MODE =
      getSystemPropertyAsBoolean("keepo.benchmarkMode", false);

  private SystemUtils() {
    // private constructor
  }

  /**
   * Returns the system property identified by the key or return the default value
   * 
   * @param key
   * @param defaultValue
   * @return
   */
  public static String getSystemPropertyOrDefault(String key, String defaultValue) {
    Optional<String> prop = Optional.ofNullable(System.getProperty(key));

    if (prop.isPresent()) {
      return prop.get();
    }

    return defaultValue;
  }

  /**
   * Shortcut returning an integer value
   * 
   * @param key
   * @param defaultValue
   * @return
   */
  public static Integer getSystemPropertyAsInteger(String key, Integer defaultValue) {
    return Integer.parseInt(getSystemPropertyOrDefault(key, defaultValue.toString()));
  }

  /**
   * Shortcut returning a boolean value
   * 
   * @param key
   * @param defaultValue
   * @return
   */
  private static Boolean getSystemPropertyAsBoolean(String key, Boolean defaultValue) {
    return Boolean.parseBoolean(getSystemPropertyOrDefault(key, defaultValue.toString()));
  }

}
