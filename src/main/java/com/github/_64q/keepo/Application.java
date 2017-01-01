package com.github._64q.keepo;

import org.apache.commons.cli.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Application entrypoint
 * 
 * @author qlebourgeois &lt;contact@qlebourgeois.me&gt;
 */
public final class Application {

  /** Logger */
  private static final Logger LOG = LoggerFactory.getLogger(Application.class);

  private Application() {
    // private constructor
  }

  public static void main(String[] args) {
    LOG.info("launching keepo server");

    CommandLine cmd = CmdLine.parse(args);

    if (cmd.hasOption("h")) {
      CmdLine.printHelp();
    } else {
      SpringBootstrap.getContext("com.github._64q").getBean(KeepoDaemon.class).run(cmd.getArgs());
    }

    LOG.info("exiting keepo server");

    System.exit(0);
  }
}
