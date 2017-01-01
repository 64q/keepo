package com.github._64q.keepo.chat;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatUtils {

  /** Logger */
  private static final Logger LOG = LoggerFactory.getLogger(ChatUtils.class);

  /**
   * Twitch IRC chat standard line
   * 
   * <p>
   * Only 3 groups are currently used by the line parser : user, channel and content
   */
  public static final Pattern LINE_PATTERN =
      Pattern.compile("^:(?<user>.*)!(.*) ([A-Z]+) #(?<channel>.*) :(?<content>.*)$");

  private ChatUtils() {
    // private constructor
  }

  /**
   * Parse an IRC chat line
   * 
   * @param line the line to be parsed
   * @return the corresponding {@link ChatEntry}
   */
  public static Optional<ChatEntry> parse(String line) {
    Matcher matcher = LINE_PATTERN.matcher(line);

    if (matcher.matches()) {
      String user = matcher.group("user");
      String channel = matcher.group("channel");
      String content = matcher.group("content");

      return Optional.of(new ChatEntry(LocalDateTime.now(), user, channel, content));
    } else {
      LOG.warn("impossible to parse given line {}", line);
    }

    return Optional.empty();
  }
}
