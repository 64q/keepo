package com.github._64q.keepo.chat;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.github._64q.keepo.BaseTest;

public class ChatUtilsTest extends BaseTest {

  private static final String EXAMPLE_LINE =
      ":masquerade_81!masquerade_81@masquerade_81.tmi.twitch.tv" + " PRIVMSG #p4wnyhof"
          + " :@P4wnyhof love you bro! pownyL pownyH pownyPride";

  /**
   * Benchmark on 1 M lines
   */
  @Test(timeout = 7000)
  public void testParseBenchmark1Million() {
    ChatEntry entry;

    for (int i = 0; i < 1000000; i++) {
      entry = ChatUtils.parse(EXAMPLE_LINE).get();

      assertThat(entry.getChannel()).isEqualTo("p4wnyhof");
      assertThat(entry.getUser()).isEqualTo("masquerade_81");
      assertThat(entry.getContent()).contains("love you bro");
    }
  }

}
