package com.github._64q.keepo.irc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github._64q.keepo.config.KeepoConfig;
import com.github._64q.keepo.util.SystemUtils;

/**
 * A simple IRC client oriented to speak with Twitch IRC.
 * 
 * @author qlebourgeois &lt;contact@qlebourgeois.me&gt;
 */
@Service
public class IrcClient {

  /** Logger */
  private static final Logger LOG = LoggerFactory.getLogger(IrcClient.class);

  // --- components

  @Autowired
  private KeepoConfig config;

  // --- attributes

  /**
   * IRC server address
   */
  private String address;

  /**
   * Username (defaults to __)
   */
  private String username;

  /**
   * OAuth token
   */
  private String password;

  /**
   * List of channels
   */
  private Set<String> chans;

  /**
   * Socket channel
   */
  private Socket socket;

  /** Benchmark mode (without redis) */
  private boolean benchmarkMode = SystemUtils.BENCHMARK_MODE;

  /**
   * Initialize the bean
   */
  @PostConstruct
  public void initialize() {
    // TODO handle parsing of the port contained in the address
    address = config.getIrcAddress();
    username = config.getIrcUsername();
    password = config.getIrcPassword();

    chans = new HashSet<>();

    try {
      socket = new Socket(address, 6667);
    } catch (IOException e) {
      throw new RuntimeException("unable to open socket to twitch irc", e);
    }
  }

  /**
   * Main loop
   */
  public void loop(IrcHandler handler) {
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      String line;

      while ((line = reader.readLine()) != null) {
        logIncoming(line);

        if (!benchmarkMode && !firstLevelHandler(line)) {
          secondLevelHandler(line, handler);
        }
      }
    } catch (IOException e) {
      LOG.error("socket error", e);
    } finally {
      try {
        socket.close();
      } catch (IOException e) {
        LOG.error("socket error", e);
      }
    }
  }

  // --- Sending methods

  /**
   * Send a command in the IRC server
   * 
   * @param command command to send
   */
  protected void send(String command) {
    logOutgoing(command);

    try {
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      out.println(command);
    } catch (IOException e) {
      LOG.error("unable to write in socket, command {} ignored", command, e);
    }
  }

  /**
   * Send the nick command given the username
   */
  public void sendNick() {
    send(IrcUtils.nick(username));
  }

  /**
   * Send the login command given the password (oauth token)
   */
  public void sendLogin() {
    send(IrcUtils.login(password));
  }

  /**
   * Send a join command in the given channel
   * 
   * @param chan the channel to join
   */
  public void sendJoin(String chan) {
    LOG.info("joining channel {}", chan);
    chans.add(chan);
    send(IrcUtils.join(chan));
  }

  public Set<String> getChans() {
    return chans;
  }

  // --- private methods

  /**
   * First level handler of the IRC chat
   * 
   * @param line
   * @return
   */
  private boolean firstLevelHandler(String line) {
    boolean firstLevel = false;

    if (line.startsWith("PING")) {
      send(IrcUtils.ping(line));
      firstLevel = true;
    }

    if (firstLevel) {
      LOG.debug("first level command [ {} ] handled", line);
    }

    return firstLevel;
  }

  /**
   * Delegate to the custom handler, all purely chat related tasks
   * 
   * @param line
   * @param handler
   * @return
   */
  private boolean secondLevelHandler(String line, IrcHandler handler) {
    handler.handle(line);

    return true;
  }

  /**
   * Log incoming message in DEBUG
   * 
   * @param data
   */
  private void logIncoming(String data) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("INCOMING: {}", data);
    }
  }

  /**
   * Log outgoing message in DEBUG
   * 
   * @param data
   */
  private void logOutgoing(String data) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("OUTGOING: {}", data);
    }
  }
}
