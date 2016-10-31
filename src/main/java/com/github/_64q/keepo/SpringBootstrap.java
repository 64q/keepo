package com.github._64q.keepo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Utility to bootstrap spring
 * 
 * @author qlebourgeois &lt;contact@qlebourgeois.me&gt;
 */
public class SpringBootstrap {

  private static ApplicationContext context;

  private SpringBootstrap() {
    // private constructor
  }

  /**
   * Get spring context (initialize it if not)
   * 
   * @param pkgs base packages for scanning
   * @return spring context
   */
  public static ApplicationContext getContext(String... pkgs) {
    if (context == null) {
      context = new AnnotationConfigApplicationContext(pkgs);
    }

    return context;
  }

  /**
   * Short method call to spring context
   * 
   * @return spring context
   */
  public static ApplicationContext getContext() {
    if (context == null) {
      throw new UnsupportedOperationException("please initialize spring context before using it");
    }

    return context;
  }
}
