package com.github._64q.keepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.github._64q.keepo.chat.ChatClient;

/**
 * Infinite daemon to fetch messages from twitch chat
 * 
 * @author qlebourgeois &lt;contact@qlebourgeois.me&gt;
 */
@Component
public class KeepoDaemon {

  @Autowired
  private ChatClient chatClient;

  public int run(String... chans) {
    Assert.notEmpty(chans, "you must provide channels");

    // testing
    chatClient.addChans(chans);

    // start the main loop
    chatClient.loop();

    return 0;
  }

}
