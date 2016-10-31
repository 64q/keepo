package com.github._64q.keepo;

import org.apache.commons.cli.Options;

public class CmdLine {
  /**
   * Get command line options
   * 
   * @return
   */
  public static Options getOptions() {
    Options options = new Options();

    options.addOption("s", "stream", true, "streams to monitor");
    options.addOption("h", "help", false, "display the help");

    return options;
  }
}
