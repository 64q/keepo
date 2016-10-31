package com.github._64q.keepo.irc;

/**
 * IRC handler to implement
 * 
 * @author qlebourgeois &lt;contact@qlebourgeois.me&gt;
 */
public interface IrcHandler {
  /**
   * Handle an incoming data payload
   * 
   * @param data data to handle
   */
  public void handle(String data);
}
