package com.github._64q.keepo.irc;

/**
 * Utilities class that stores method to converse with an IRC server
 * 
 * @author qlebourgeois &lt;contact@qlebourgeois.me&gt;
 */
public final class IrcUtils {

  private static final String PASS_CMD = "PASS oauth:%s";
  private static final String NICK_CMD = "NICK %s";
  private static final String JOIN_CMD = "JOIN %s";

  private IrcUtils() {
    // private constructor
  }

  /**
   * Response to a ping message
   * 
   * @param line
   * @return
   */
  public static String ping(String line) {
    return "PONG :tmi.twitch.tv";
  }

  /**
   * Nick command
   * 
   * @param username
   * @return
   */
  public static String nick(String username) {
    return String.format(NICK_CMD, username);
  }

  /**
   * Login command
   * 
   * @param password
   * @return
   */
  public static String login(String password) {
    return String.format(PASS_CMD, password);
  }

  /**
   * Join command
   * 
   * @param chan
   * @return
   */
  public static String join(String chan) {
    return String.format(JOIN_CMD, chan);
  }
}
