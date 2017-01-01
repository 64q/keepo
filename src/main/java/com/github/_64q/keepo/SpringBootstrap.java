package com.github._64q.keepo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Utility to bootstrap spring as a singleton
 * 
 * @author qlebourgeois &lt;contact@qlebourgeois.me&gt;
 */
public final class SpringBootstrap {

  /** Spring context instance */
  private static ApplicationContext context;

  private SpringBootstrap() {
    // private constructor
  }

  /**
   * Get spring context (initialize it if not)
   * 
   * @param pkgs base packages for scanning (optional)
   * @return spring context
   */
  public static ApplicationContext getContext(String... pkgs) {
    if (context == null) {
      context = new AnnotationConfigApplicationContext(pkgs);
    }

    return context;
  }

}
