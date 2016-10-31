package com.github._64q.keepo.util;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtils {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  static {
    MAPPER.setSerializationInclusion(Include.NON_NULL);
  }

  private JsonUtils() {
    // private constructor
  }

  /**
   * Get a JSON representing the object
   * 
   * @param object
   * @return
   */
  public static String toJson(Object object) {
    try {
      return MAPPER.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("unable to write json", e);
    }
  }

  /**
   * Get the object representation of the data
   * 
   * @param data
   * @param clazz
   * @return
   */
  public static <T> T fromJson(String data, Class<T> clazz) {
    try {
      return MAPPER.readValue(data, clazz);
    } catch (IOException e) {
      throw new RuntimeException("unable to read json", e);
    }
  }
}
