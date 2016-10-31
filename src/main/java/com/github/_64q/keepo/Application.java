package com.github._64q.keepo;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class Application {

  /** Logger */
  private static final Logger LOG = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args) throws ParseException {
    LOG.info("launching keepo server");

    CommandLine cmd = new BasicParser().parse(CmdLine.getOptions(), args);

    if (cmd.hasOption("h")) {
      printHelp(cmd);
    } else {
      SpringBootstrap.getContext("com.github._64q").getBean(KeepoDaemon.class)
          .run(cmd.getOptionValues("s"));
    }

    LOG.info("exiting keepo server");

    System.exit(0);
  }

  private static void printHelp(CommandLine cmd) {
    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp("keepo", CmdLine.getOptions());
  }
}
