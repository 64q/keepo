package com.github._64q.keepo.chat;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github._64q.keepo.irc.IrcHandler;
import com.github._64q.keepo.redis.RedisClient;

@Component
public class ChatHandler implements IrcHandler {

  /** Logger */
  private static final Logger LOG = LoggerFactory.getLogger(ChatHandler.class);

  @Autowired
  private RedisClient redisClient;

  @Override
  public void handle(String data) {
    Optional<ChatEntry> entry = ChatUtils.parse(data);

    if (entry.isPresent()) {
      ChatEntry chat = entry.get();

      LOG.info("parsed line [ channel = {}, user = {}, content = {} ]", chat.getChannel(),
          chat.getUser(), chat.getContent());

      redisClient.put(chat);
    }
  }

}
