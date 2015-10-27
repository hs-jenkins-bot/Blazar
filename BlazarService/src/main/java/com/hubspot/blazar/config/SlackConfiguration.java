package com.hubspot.blazar.config;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SlackConfiguration {

  @NotNull
  @NotEmpty
  private final String token;
  @NotNull
  @NotEmpty
  private final String room;
  @NotNull
  @NotEmpty
  private String url;

  @JsonCreator
  public SlackConfiguration(@JsonProperty("token") String token,
                            @JsonProperty("room") String room,
                            @JsonProperty("url") String url) {
    this.token = token;
    this.room = room;
    this.url = url;
  }

  public String getToken() {
    return token;
  }

  public String getRoom() {
    return room;
  }

  public String getUrl() {
    return url;
  }
}
