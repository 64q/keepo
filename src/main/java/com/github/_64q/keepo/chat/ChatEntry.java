package com.github._64q.keepo.chat;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Twitch chat entry
 * 
 * @author qlebourgeois &lt;contact@qlebourgeois.me&gt;
 */
public class ChatEntry {

  public static final String CHAT_KEY_FMT = "%s::%s::%s";

  private final UUID uuid;
  private final LocalDateTime time;
  private final String user;
  private final String channel;
  private final String content;

  public ChatEntry(LocalDateTime time, String user, String channel, String content) {
    super();

    this.uuid = UUID.randomUUID();
    this.time = time;
    this.user = user;
    this.channel = channel;
    this.content = content;
  }

  public LocalDateTime getTime() {
    return this.time;
  }

  public String getUser() {
    return this.user;
  }

  public String getChannel() {
    return this.channel;
  }

  public String getContent() {
    return this.content;
  }

  /**
   * Return the generated key for this chat entry
   * 
   * <p>
   * Composed of the channel, user and a generated UUID
   * 
   * @return the generated key for the chat entry
   */
  public String getKey() {
    return String.format(CHAT_KEY_FMT, channel, user, uuid.toString());
  }

}
