package com.github._64q.keepo.chat;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.github._64q.keepo.irc.IrcClient;

@Component
public class ChatClient {

  /** Logger */
  private static final Logger LOG = LoggerFactory.getLogger(ChatClient.class);

  private static final String CHANNEL_FMT = "#%s";

  @Autowired
  private IrcClient ircClient;

  /**
   * IRC handler (uses {@link ChatHandler})
   */
  @Autowired
  private ChatHandler handler;

  @PostConstruct
  public void initalize() {
    ircClient.sendLogin();
    ircClient.sendNick();

    LOG.info("connection done");
  }

  public void addChan(String chan) {
    ircClient.sendJoin(String.format(CHANNEL_FMT, chan));
  }

  public void addChans(String... chans) {
    Arrays.asList(chans).forEach(c -> ircClient.sendJoin(String.format(CHANNEL_FMT, c)));
  }

  public void loop() {
    Assert.notEmpty(ircClient.getChans());

    ircClient.loop(handler);
  }
}
