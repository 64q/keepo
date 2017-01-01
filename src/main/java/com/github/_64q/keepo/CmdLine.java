package com.github._64q.keepo;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Command line arguments
 * 
 * @author qlebourgeois &lt;contact@qlebourgeois.me&gt;
 */
public final class CmdLine {

  private CmdLine() {
    // private constructor
  }

  public static void printHelp() {
    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp("keepo [options] <streams>", CmdLine.getOptions());
  }

  /**
   * Get command line options
   * 
   * @return
   */
  public static Options getOptions() {
    Options options = new Options();

    options.addOption("h", "help", false, "display this help");

    return options;
  }

  /**
   * Parse the given command line
   * 
   * @param args
   * @return
   */
  public static CommandLine parse(String[] args) {
    try {
      return new BasicParser().parse(CmdLine.getOptions(), args);
    } catch (ParseException e) {
      throw new RuntimeException("unable to parse command line", e);
    }
  }
}
